package com.experiment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Created by juanchen on 2017/1/17.
 * 实现子类           第4章
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingWay2 extends CalGuessRating {
	 @Override
	    protected void calAvgValue(List<Rating> list) {
						 
		 for (Rating r : list) {
			 double tmp = (double) r.getTimeStamp();
			 Map<Integer, Double> map = scoreMatrix.get(r.getUserId());
			 if (map == null) {
	                map = new HashMap<>();
	            }
			 map.put(r.getItemId(), tmp);
			 scoreMatrix.put(r.getUserId(), map);
		 }
	      
	    }

	    @Override
	    protected void calUserSimilarity(int i, int j, List<Rating> list)  {
	        Set<Integer> itemSet = calAllIncludeSet(i, j, list);
	        double sum1 = 0, sum2 = 0, sum3 = 0,wij = 0;
	        for (Integer itemId : itemSet) {
	        	wij += Math.abs((scoreMatrix.get(i).get(itemId)-scoreMatrix.get(j).get(itemId)))/3600.0/24.0;
	            sum1 +=	((ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
	                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j)));
	            sum2 += ((ratingMatrix.get(i).get(itemId) - avgMap.get(i)) *
	                    (ratingMatrix.get(i).get(itemId) - avgMap.get(i)));
	            sum3 += ((ratingMatrix.get(j).get(itemId) - avgMap.get(j)) *
	                    (ratingMatrix.get(j).get(itemId) - avgMap.get(j)));
//	        	if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId) &&
//	                    ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
//	        		sum1 ++;
//	            if(ratingMatrix.containsKey(i) && ratingMatrix.get(i).containsKey(itemId))
//	                sum2 ++;
//	            if(ratingMatrix.containsKey(j) && ratingMatrix.get(j).containsKey(itemId))
//	                sum3++;
	        	
	        }
	        Map<Integer, Double> map = similarMatrix.get(i);
	        if (map == null) {
	            map = new HashMap<>();
	        }
	        double result = 0;
	        if (itemSet.size() > 0) {
                result =  (0.75*Math.pow(Math.E, -0.00001*wij)+0.25)*sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));
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
