package com.recom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类                pearson
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp4 extends CalGuessRating {

    @Override
    protected void calAvgValue(List<Rating> list) {}

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
}
