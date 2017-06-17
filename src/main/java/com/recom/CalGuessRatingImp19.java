package com.recom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juanchen on 2017/3/12.
 * 实现子类           第5章
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp19 extends CalGuessRating {
	 @Override
	    protected void calAvgValue(List<Rating> list) {
		   CalUserVector19.cal(list);
		   System.out.println("CalGuessRatingImp19");
	    }

	    @Override
	    protected void calUserSimilarity(int i, int j, List<Rating> list)  {
	        Set<Integer> itemSet = CalUserVector19.calIncludeSet(i, j);
	        
	        double sum1 = 0, sum2 = 0, sum3 = 0,wij = 1.0;
	        // wij指的是gaptuv
	        if (itemSet.size() != 0) {
	            wij = CalUserVector19.calGap(i, j, itemSet);
            }
	        
	        for (Integer itemId : itemSet) {
	            sum1 +=	((ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
	                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j)));
	            sum2 += ((ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
	                    (ratingMatrix.get(i).get(itemId) - avgMap.get(i)));
	            sum3 += ((ratingMatrix.get(j).get(itemId) - avgMap.get(j)) *
	                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j)));
	        }
	        Map<Integer, Double> map = similarMatrix.get(i);
	        if (map == null) {
	            map = new HashMap<>();
	        }
	        if (wij == 0) {
	            //System.out.println(wij);
            }
	        //System.out.println(wij);
	        double my = (0.75*Math.pow(Math.E, -wij) + 0.25);
	        double res = 0;
	        if (itemSet.size() != 0) {
                res = ( my * sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3)));
            }
	        map.put(j, res);
	        similarMatrix.put(i, map);
	        Map<Integer, Double> map1 = similarMatrix.get(j);
	        if (map1 == null) {
	            map1 = new HashMap<>();
	        }
	        map1.put(i, res);
	        similarMatrix.put(j, map1);
	    }

}
