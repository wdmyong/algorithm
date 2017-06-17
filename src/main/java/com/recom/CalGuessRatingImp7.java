package com.recom;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类           4.1改成pearson（CalGuessRatingImp5）+ 传统方法(CalGuessRatingImp3)
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp7 extends CalGuessRating {

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
        Set<Integer> allSet = calAllSet(i, j, list);
        double sum1 = 0, sum2 = 0, sum3 = 0, sum11 =0, sum22 = 0, tmp = 0;
        for (Integer itemId : itemSet) {
            sum1 += (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i)) *
                    (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j));
            sum2 += (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i)) *
                    (scoreMatrix.get(i).get(itemId) - avgScoreMap.get(i));
            sum3 += (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j)) *
                    (scoreMatrix.get(j).get(itemId) - avgScoreMap.get(j));
        }
        for (Integer itemId : allSet) {
            if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId) &&
                    ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
                tmp ++;
            if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId))
                sum11 ++;
            if(ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
                sum22++;
        }
        double result = tmp / Math.sqrt(sum11*sum22);
        Map<Integer, Double> map = similarMatrix.get(i);
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(j, (result + sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
        similarMatrix.put(i, map);
        Map<Integer, Double> map1 = similarMatrix.get(j);
        if (map1 == null) {
            map1 = new HashMap<>();
        }
        map1.put(i, (result + sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
        similarMatrix.put(j, map1);
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
