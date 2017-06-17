package com.recom;

import java.util.*;

/**
 * Created by yongduan on 2017/1/14.
 * 实现子类   4.3基于(CalGuessRatingImp2)写的，不用了，重写
 * 根据什么条件计算相似性，重写calAvgValue
 * 计算相似性的具体方法，重写calUserSimilarity
 */
@Deprecated
public class CalGuessRatingImp6 extends CalGuessRating {

    // 项目id对应的评分数量
    private Map<Integer, Integer> itemMap = new HashMap<>();

    private Map<Integer, Double> midMap = new HashMap<>();

    @Override
    protected void calAvgValue(List<Rating> list) {
        // 用户自己最大响应时间
        Map<Integer, Long> maxRatingTimeMap = new HashMap<>();
        // 用户自己最小响应时间
        Map<Integer, Long> minRatingTimeMap = new HashMap<>();
        Map<Integer, List<Double>> timeMap = new HashMap<>();
        for (Rating r : list) {
            if (maxRatingTimeMap.get(r.getUserId()) == null || maxRatingTimeMap.get(r.getUserId()) < r.getTimeActive()) {
                maxRatingTimeMap.put(r.getUserId(), r.getTimeActive());
            }
            if (minRatingTimeMap.get(r.getUserId()) == null || minRatingTimeMap.get(r.getUserId()) > r.getTimeActive()) {
                minRatingTimeMap.put(r.getUserId(), r.getTimeActive());
            }
            List<Double> timeList = timeMap.get(r.getUserId());
            if (timeList == null) {
                timeList = new ArrayList<>();
            }
            timeList.add((double)r.getTimeActive());
            timeMap.put(r.getUserId(), timeList);
        }
        for (Integer userId : timeMap.keySet()) {
            List<Double> timeList = timeMap.get(userId);
            Collections.sort(timeList);
            midMap.put(userId, timeList.get(timeList.size() / 2));
        }

        // 用户评分项目数量，user对item的计算度量均值
        Map<Integer, Integer> userRatingNum = new HashMap<>();
        for (Rating r : list) {
            if (itemMap.containsKey(r.getItemId())) {
                itemMap.put(r.getItemId(), itemMap.get(r.getItemId()) + 1);
            } else {
                itemMap.put(r.getItemId(), 1);
            }
            double tmp1 = (double) (r.getTimeActive() - minRatingTimeMap.get(r.getUserId())) /
                    (double)(maxRatingTimeMap.get(r.getUserId()) - minRatingTimeMap.get(r.getUserId()));
            double tmp = 0.5 * Math.cos(tmp1 * 2 * Math.PI) + 0.5;
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
                    sum1 += sortUserMap.get(userId) * ratingMatrix.get(userId).get(rating.getItemId());
                    sum2 += sortUserMap.get(userId);
                }
            }
            if (sum2 > 0) {
                double f1 = 1.0 / (1.0 + itemMap.get(rating.getItemId()));
                double f2 = (double)itemMap.get(rating.getItemId()) / (1.0 + itemMap.get(rating.getItemId()));
                double guessRating = fun(midMap.get(rating.getUserId())) * f1 + (sum1 / sum2) * f2;
                rating.setGuessRating(guessRating);
            } else {
                rating.setGuessRating(avgMap.get(rating.getUserId()));
            }
            result += Math.abs(rating.getRating() - rating.getGuessRating());
        }
        result = result / testList.size();
        System.out.println(topN + "\t" + result);
        return result;
    }

    private double fun(double x) {
        double tmp = 1 + Math.exp(-x);
        double res = 2.0 / tmp;
        res = 2.0 - res;
        return res;
    }
}
