package com.recom;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 抽象父类
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public abstract class CalGuessRatingImp10 extends CalGuessRating {

    // item被评分的数量 map
    protected Map<Integer, Double> itemMap = new HashMap<>();

    // user响应时间 map
    protected Map<Integer, List<Double>> userMap = new HashMap<>();

    // userMap中位数
    protected Map<Integer, Double> midMap = new HashMap<>();

    @Override
    public double calGuessRating(List<Rating> testList, List<Rating> list) {
        calAvgValue(list);
        calSimilarMatrix(list);
        for (int k = 1; k <= 500; k++) {
            calGuessScore(testList, k);
        }
        return 0;
    }

    // 根据scoreMatrix计算相似矩阵
    @Override
    protected void calSimilarMatrix(List<Rating> list) {
        Set<Integer> userIdSet = new HashSet<>();
        Map<Integer, Integer> userRatingNum = new HashMap<>();
        for (Rating rating : list) {
            if (itemMap.containsKey(rating.getItemId())) {
                itemMap.put(rating.getItemId(), itemMap.get(rating.getItemId()) + 1);
            } else {
                itemMap.put(rating.getItemId(), 1.0);
            }
            List<Double> timeList = null;
            if (userMap.containsKey(rating.getUserId())) {
                timeList = userMap.get(rating.getUserId());
                timeList.add((double)rating.getTimeActive());
            } else {
                timeList = new ArrayList<>();
                timeList.add((double)rating.getTimeActive());
            }
            userMap.put(rating.getUserId(), timeList);
            userIdSet.add(rating.getUserId());
            Map<Integer, Double> map = ratingMatrix.get(rating.getUserId());
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(rating.getItemId(), rating.getRating());
            ratingMatrix.put(rating.getUserId(), map);
            // user对item的评分均值计算
            Rating r = rating;
            //for (Rating r : list) {
                if (userRatingNum.containsKey(r.getUserId())) {
                    userRatingNum.put(r.getUserId(), userRatingNum.get(r.getUserId()) + 1);
                } else {
                    userRatingNum.put(r.getUserId(), 1);
                }
                if (avgMap.containsKey(r.getUserId())) {
                    avgMap.put(r.getUserId(), avgMap.get(r.getUserId()) + r.getRating());
                } else {
                    avgMap.put(r.getUserId(), r.getRating());
                }
            //}
        }

        // 排序，取userMap中位数
        for (Integer i : userMap.keySet()) {
            List<Double> timeList = userMap.get(i);
            Collections.sort(timeList, new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    if (o1 > o2) return 1;
                    else if (o1 < o2) return -1;
                    return 0;
                }
            });
            midMap.put(i, timeList.get(timeList.size() / 2));
        }

        // 计算平均值
        for (Integer userId : avgMap.keySet()) {
            avgMap.put(userId, avgMap.get(userId) / userRatingNum.get(userId));
        }
        List<Integer> userIdList = new ArrayList<>(userIdSet);
        // 计算每两个用户之间的相似度，对称的，节约一半时间
        for (int i = 0; i < userIdList.size() - 1; i++) {
            for (int j = i + 1; j < userIdList.size(); j++) {
                if (i % 50 == 0 && j % 50 == 0) {
                    //System.out.println(i + "\t" + j);
                }
                calUserSimilarity(userIdList.get(i), userIdList.get(j), list);
            }
        }
        // 对每个用户相似的用户进行排序(按相似度的结果反序)
        for (Integer userId : similarMatrix.keySet()) {
            similarMatrix.put(userId, MapSortUtil.sortByValue(similarMatrix.get(userId)));
        }
    }

    // 填充自己用的方法，，从别的地方拷就行
    @Override
    protected void calUserSimilarity(int i, int j, List<Rating> list) {
        // cos计算要并集，拷过来这里变了，，其它的拷过来不用变
        Set<Integer> itemSet = calAllSet(i, j, list);
        double sum1 = 0;
        double sum2 = 0;
        double tmp =0;
        for (Integer itemId : itemSet) {
            if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId) &&
                    ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
                tmp ++;
            if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId))
                sum1 ++;
            if(ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
                sum2++;
        }
        double result = tmp / Math.sqrt(sum1*sum2);
        Map<Integer, Double> map = similarMatrix.get(i);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(j, result);
        similarMatrix.put(i, map);
        Map<Integer, Double> map1 = similarMatrix.get(j);
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        map1.put(i, result);
        similarMatrix.put(j, map1);
    }

    // 填充自己用的方法，，从别的地方拷就行
    @Override
    protected void calAvgValue(List<Rating> list) {

    }

    protected double calGuessScore(List<Rating> testList, int topN) {
        double result = 0;
        for (Rating rating : testList) {
            double sum1 = 0, sum2 = 0;;
            int i = 1;
            Map<Integer, Double> sortUserMap = similarMatrix.get(rating.getUserId());
            for (Integer userId : sortUserMap.keySet()) {
                if (i++ > topN) {
                    break;
                }
                if (ratingMatrix.get(userId).containsKey(rating.getItemId())) {
                    sum1 += sortUserMap.get(userId) * ratingMatrix.get(userId).get(rating.getItemId());
                    sum2 += sortUserMap.get(userId);
                }
            }
            double f1 = 2.0 - 2.0 * (1.0 / (1.0 + Math.exp(-1.0 * midMap.get(rating.getUserId()))));
            double f2 = 1.0 / (itemMap.get(rating.getItemId()) + 1.0);
            double f3 = itemMap.get(rating.getItemId()) / (itemMap.get(rating.getItemId()) + 1.0);

            if (sum2 > 0) {
                rating.setGuessRating(f1 * f2 + f3 * (sum1 / sum2));
            } else {
                rating.setGuessRating(avgMap.get(rating.getUserId()));
            }
            result += Math.abs(rating.getRating() - rating.getGuessRating());
        }
        result = result / testList.size();
        System.out.println(topN + "\t" + result);
        return result;
    }

    private Set<Integer> calAllSet(Integer i, Integer j, List<Rating> list) {
        Set<Integer> itemSet = new HashSet<>();
        for (Rating rating : list) {
            if (rating.getUserId() == i || rating.getUserId() == j) {
                itemSet.add(rating.getItemId());
            }
        }
        return itemSet;
    }
}
