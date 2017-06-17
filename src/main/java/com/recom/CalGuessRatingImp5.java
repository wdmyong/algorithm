package com.recom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类           4.1改成pearson（好方法）
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp5 extends CalGuessRating {

    @Override
    protected void calAvgValue(List<Rating> list) {
        Map<Integer, Integer> userRatingNum = new HashMap<>();
        for (Rating rating : list) {
            Map<Integer, Double> map = scoreMatrix.get(rating.getUserId());
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(rating.getItemId(), (double)rating.getTimeActive());
            scoreMatrix.put(rating.getUserId(), map);
            if (userRatingNum.containsKey(rating.getUserId())) {
                userRatingNum.put(rating.getUserId(), userRatingNum.get(rating.getUserId()) + 1);
            } else {
                userRatingNum.put(rating.getUserId(), 1);
            }
            if (avgScoreMap.containsKey(rating.getUserId())) {
                avgScoreMap.put(rating.getUserId(), avgScoreMap.get(rating.getUserId()) + (double)rating.getTimeActive());
            } else {
                avgScoreMap.put(rating.getUserId(), (double)rating.getTimeActive());
            }
        }
        for (Integer userId : avgScoreMap.keySet()) {
            avgScoreMap.put(userId, avgScoreMap.get(userId) / userRatingNum.get(userId));
        }
    }

    @Override
    protected void calUserSimilarity(int i, int j, List<Rating> list) {
        Set<Integer> itemSet = calAllIncludeSet(i, j, list);
        double sum1 = 0, sum2 = 0, sum3 = 0;
        for (Integer itemId : itemSet) {
            sum1 += (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i)) *
                    (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j));
            sum2 += (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i)) *
                    (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i));
            sum3 += (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j)) *
                    (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j));
        }
        Map<Integer, Double> map = similarMatrix.get(i);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(j, (sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
        similarMatrix.put(i, map);
        Map<Integer, Double> map1 = similarMatrix.get(j);
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        map1.put(i, (sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
        similarMatrix.put(j, map1);
    }
}
