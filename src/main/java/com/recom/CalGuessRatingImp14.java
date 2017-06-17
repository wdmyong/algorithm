package com.recom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juanchen on 2017/3/18.
 * 实现子类           4.2(新版)
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp14 extends CalGuessRating {
	 @Override
	    protected void calAvgValue(List<Rating> list) {
		   CalUserVector14.cal(list);
		   System.out.println("CalGuessRatingImp14");
	    }

	    @Override
	    protected void calUserSimilarity(int i, int j, List<Rating> list)  {
	        Set<Integer> itemSet = CalUserVector14.calIncludeSet(i, j);
	        double sum1 = 0, sum2 = 0, sum3 = 0,wij = 0;
	        for (Integer itemId : itemSet) {
				// wij指的是gaptuv
				wij = CalUserVector14.calGap(i, j, itemSet);
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
	        map.put(j, ( (0.75*Math.pow(Math.E, -0.0000001*wij)+0.25)*sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
	        similarMatrix.put(i, map);
	        Map<Integer, Double> map1 = similarMatrix.get(j);
	        if (map1 == null) {
	            map1 = new HashMap<>();
	        }
	        map1.put(i, ((0.75*Math.pow(Math.E, -0.0000001*wij)+0.25)*sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3))));
	        similarMatrix.put(j, map1);
	    }

}
