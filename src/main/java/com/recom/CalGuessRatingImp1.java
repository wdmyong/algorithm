package com.recom;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类         一开始的4.1
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
@Deprecated
public class CalGuessRatingImp1 extends CalGuessRating {

    @Override
    protected void calAvgValue(List<Rating> list) {
        for (Rating rating : list) {
            Map<Integer, Double> map = scoreMatrix.get(rating.getUserId());
            if (map == null) {
                map = new HashMap<>();
            }
            map.put(rating.getItemId(), (double)rating.getTimeActive());
            scoreMatrix.put(rating.getUserId(), map);
        }
    }

    @Override
    protected void calUserSimilarity(int i, int j, List<Rating> list) {
        Set<Integer> itemSet = calAllIncludeSet(i, j, list);
        double sum = 0;
        for (Integer itemId : itemSet) {
            double tmp = scoreMatrix.get(i).get(itemId) - scoreMatrix.get(j).get(itemId);
            sum += tmp * tmp;
        }
        double result = 1.0 / (1.0 + Math.sqrt(sum));
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
}
