package com.experiment;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 抽象父类
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public abstract class CalGuessRating {

    // 相似性是对称的，格式为 Map<小id, Map<大id,值>>
    protected Map<Integer, Map<Integer, Double>> similarMatrix = new HashMap<>();

    // 评分矩阵
    protected Map<Integer, Map<Integer, Double>> ratingMatrix = new HashMap<>();
    // user对item的计算度量值矩阵(可以是评分，也可以是自己定义的其它)
    protected Map<Integer, Map<Integer, Double>> scoreMatrix = new HashMap<>();
    // user对item的计算度量均值
    protected Map<Integer, Double> avgScoreMap = new HashMap<>();
    // user对item的评分均值
    protected Map<Integer, Double> avgMap = new HashMap<>();
    protected double defaultRating = 3;

    public double calGuessRating(List<Rating> testList, List<Rating> list) {
        calAvgValue(list);
        calSimilarMatrix(list);
        for (int k = 1; k <= 500; k++) {
            calGuessScore(testList, k);
        }
        return 0;
    }

    // 根据scoreMatrix计算相似矩阵
    protected void calSimilarMatrix(List<Rating> list) {
        Set<Integer> userIdSet = new HashSet<>();
        int line = 0;
        for (Rating rating : list) {
            line++;
            if (line % 1000 == 0) System.out.println(line);
            userIdSet.add(rating.getUserId());
            Map<Integer, Double> map = ratingMatrix.get(rating.getUserId());
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(rating.getItemId(), rating.getRating());
            ratingMatrix.put(rating.getUserId(), map);
            // user对item的评分均值计算
            Map<Integer, Integer> userRatingNum = new HashMap<>();
            for (Rating r : list) {
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
            }
            for (Integer userId : avgMap.keySet()) {
                avgMap.put(userId, avgMap.get(userId) / userRatingNum.get(userId));
            }
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

    // 计算两个用户之间相似性
    protected abstract void calUserSimilarity(int i, int j, List<Rating> list);

    protected abstract void calAvgValue(List<Rating> list);

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
            if (sum2 > 0) {
                rating.setGuessRating(sum1 / sum2);
            } else {
                rating.setGuessRating(avgMap.get(rating.getUserId()));
            }
            result += Math.abs(rating.getRating() - rating.getGuessRating());
        }
        result = result / testList.size();
        System.out.println(result);
        //System.out.println(topN + "\t" + result);
        return result;
    }

    // 计算两个用户i，j的交集item
    protected Set<Integer> calAllIncludeSet(Integer i, Integer j, List<Rating> list) {
        Set<Integer> itemSet1 = new HashSet<>();
        Set<Integer> itemSet2 = new HashSet<>();
        for (Rating rating : list) {
            if (rating.getUserId() == i) {
                itemSet1.add(rating.getItemId());
            }
            if (rating.getUserId() == j) {
                itemSet2.add(rating.getItemId());
            }
        }
        itemSet1.retainAll(itemSet2);
        return itemSet1;
    }
}
