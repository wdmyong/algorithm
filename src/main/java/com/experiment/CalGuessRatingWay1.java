package com.experiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类           第3章（真正实现）
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingWay1 extends CalGuessRating {

    @Override
    protected void calAvgValue(List<Rating> list) {
        // 用户自己最大响应时间
        Map<Integer, Long> maxRatingTimeMap = new HashMap<>();
        // 用户自己最小响应时间
        Map<Integer, Long> minRatingTimeMap = new HashMap<>();
        for (Rating r : list) {
            if (maxRatingTimeMap.get(r.getUserId()) == null || maxRatingTimeMap.get(r.getUserId()) < r.getTimeActive()) {
                maxRatingTimeMap.put(r.getUserId(), r.getTimeActive());
            }
            if (minRatingTimeMap.get(r.getUserId()) == null || minRatingTimeMap.get(r.getUserId()) > r.getTimeActive()) {
                minRatingTimeMap.put(r.getUserId(), r.getTimeActive());
            }
        }

        // 用户评分项目数量，user对item的计算度量均值
        Map<Integer, Integer> userRatingNum = new HashMap<>();
        for (Rating r : list) {
            double tmp1 = (double) (r.getTimeActive() - minRatingTimeMap.get(r.getUserId())) /
                    (double)(maxRatingTimeMap.get(r.getUserId()) - minRatingTimeMap.get(r.getUserId()));
            double tmp = 0.25 * Math.cos(tmp1 * 2 * Math.PI) + 0.75;
            Map<Integer, Double> map = scoreMatrix.get(r.getUserId());
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(r.getItemId(), tmp);
            scoreMatrix.put(r.getUserId(), map);
            if (userRatingNum.containsKey(r.getUserId())) {
                userRatingNum.put(r.getUserId(), userRatingNum.get(r.getUserId()) + 1);
            } else {
                userRatingNum.put(r.getUserId(), 1);
            }
            if (avgScoreMap.containsKey(r.getUserId())) {
                avgScoreMap.put(r.getUserId(), avgScoreMap.get(r.getUserId()) + tmp);
            } else {
                avgScoreMap.put(r.getUserId(), tmp);
            }
        }
        for (Integer userId : avgScoreMap.keySet()) {
            avgScoreMap.put(userId, avgScoreMap.get(userId) / userRatingNum.get(userId));
        }
    }

    @Override
    protected void calUserSimilarity(int i, int j, List<Rating> list)  {
        Set<Integer> itemSet = calAllIncludeSet(i, j, list);
        double sum1 = 0, sum2 = 0, sum3 = 0;
        for (Integer itemId : itemSet) {
            sum1 += (ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j));
            sum2 += (ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
                    (ratingMatrix.get(i).get(itemId) - avgMap.get(i));
            sum3 += (ratingMatrix.get(j).get(itemId) - avgMap.get(j)) *
                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j));
        }
        Map<Integer, Double> map = similarMatrix.get(i);
        if (map == null) {
            map = new HashMap<>();
        }
        double result = 0;
        if (itemSet.size() > 0) {
            result = sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));
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

    @Override
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
                    sum1 += sortUserMap.get(userId) * ratingMatrix.get(userId).get(rating.getItemId()) * 
                            scoreMatrix.get(userId).get(rating.getItemId());
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
}
