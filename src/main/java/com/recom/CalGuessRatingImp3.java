package com.recom;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类          cos
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp3 extends CalGuessRating {

    @Override
    protected void calAvgValue(List<Rating> list) {}

    @Override
    protected void calUserSimilarity(int i, int j, List<Rating> list)  {
        Set<Integer> itemSet = calAllIncludeSet(i, j, list);
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
        double result = 0;
        if (sum1 > 0 && sum2 > 0) {
            result = tmp / Math.sqrt(sum1*sum2);
        }
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

    // 计算余弦的时间并集
    @Override
    protected Set<Integer> calAllIncludeSet(Integer i, Integer j, List<Rating> list) {
        Set<Integer> itemSet = new HashSet<>();
        for (Rating rating : list) {
            if (rating.getUserId() == i || rating.getUserId() == j) {
                itemSet.add(rating.getItemId());
            }
        }
        return itemSet;
    }
}
