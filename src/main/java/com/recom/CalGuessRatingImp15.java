package com.recom;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by juanchen on 2017/3/12.
 * 实现子类           4.2(新版)
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
public class CalGuessRatingImp15 extends CalGuessRating {
	 @Override
	    protected void calAvgValue(List<Rating> list) {
		   CalUserVector15.cal(list);
		   calScoreMatrix(list);
		   System.out.println("CalGuessRatingImp15");
	    }

	private void calScoreMatrix(List<Rating> list) {
		for (Rating rating : list) {
			long activeTime = rating.getTimeActive() / 3600 / 24;
			long maxTime = CalUserVector15.MAX_MAP.get(rating.getUserId());
			long minTime = CalUserVector15.MIN_MAP.get(rating.getUserId());
			double fz = CalUserVector15.calGap(activeTime, minTime) + 1.0;
			double fm = CalUserVector15.calGap(maxTime, minTime) + 1.0;
			Map<Integer, Double> map = scoreMatrix.get(rating.getUserId());
			if (map == null) {
				map = new HashMap<>();
			}
			double tmp = 0.75 * Math.cos(fz / fm * 2 * Math.PI) + 0.25;
			map.put(rating.getItemId(), tmp);
			scoreMatrix.put(rating.getUserId(), map);
		}
	}

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
		double result = tmp / Math.sqrt(sum1*sum2);
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
					Double d = 1.0;
					if (scoreMatrix.containsKey(userId) && scoreMatrix.get(userId).containsKey(rating.getItemId())) {
						d = scoreMatrix.get(userId).get(rating.getItemId());
					}
					sum1 += sortUserMap.get(userId) * ratingMatrix.get(userId).get(rating.getItemId()) * d;
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
