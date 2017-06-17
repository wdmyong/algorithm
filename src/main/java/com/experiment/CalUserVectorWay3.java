package com.experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

/**
 * Created by yongduan on 2017/3/11.
 */
public class CalUserVectorWay3 {

    // 评分时间-发布时间矩阵(用户对项目的评分时间-发布时间)
    private static Map<Integer, Map<Integer, Long>> timeMatrix = new HashMap<>();
    // 每个用户的评分集合(用户所有评分的项目集合)
    private static Map<Integer, Set<Integer>> userMap = new HashMap<>();

    private static long maxValue = 0L;

    public static void cal(List<Rating> ratingList) {
        for (Rating rating : ratingList) {
            Map<Integer, Long> tmp = timeMatrix.get(rating.getUserId());
            if (tmp == null) {
                tmp = new HashMap<>();
            }
            tmp.put(rating.getItemId(), rating.getTimeActive() / 24 / 3600);
            timeMatrix.put(rating.getUserId(), tmp);
            Set<Integer> tmpSet = userMap.get(rating.getUserId());
            if (tmpSet == null) {
                tmpSet = new HashSet<>();
            }
            tmpSet.add(rating.getItemId());
            userMap.put(rating.getUserId(), tmpSet);
        }
        for (int i = 1; i < 943; i++) {
            for (int j = i + 1; j <= 943; j++) {
                Set<Integer> inter = calIncludeSet(i, j);
                if (inter != null && inter.size() > 0) {
                    for (Integer integer : inter) {
                        if (timeMatrix.get(i).get(integer) > maxValue) {
                            maxValue = timeMatrix.get(i).get(integer);
                        }
                        if (timeMatrix.get(j).get(integer) > maxValue) {
                            maxValue = timeMatrix.get(j).get(integer);
                        }
                    }
                }
            }
        }
        System.out.println("最大值：" + maxValue);
        System.out.println("line1:");
        calB(0, 5000000 / 3600 / 24);
        System.out.println("line2:");
        calA(4000000 / 3600 / 24, 200000000 / 3600 / 24);
        calB(4000000 / 3600 / 24, 200000000 / 3600 / 24);
        System.out.println("line3:");
        calA(150000000 / 3600 / 24, 500000000 / 3600 / 24);
        calB(150000000 / 3600 / 24, 500000000 / 3600 / 24);
        System.out.println("line4:");
        calA(400000000 / 3600 / 24, maxValue);
    }

    private static long calA(long a, long b) {
        long c = calC(a, b);
        int k = 1;
        long sum = 0;
        for (Map.Entry<Integer, Map<Integer, Long>> entry1 : timeMatrix.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry1.getValue().entrySet()) {
                if (entry2.getValue() <= c) {
                    sum += (c - entry2.getValue());
                    k++;
                }
            }
        }
        long result = 0;
        if (k > 0 ) {
            result = sum / k;
        }
        System.out.println("A: " + result);
        return result;
    }
    private static long calB(long a, long b) {
        long c = calC(a, b);
        int k = 0;
        long sum = 0;
        for (Map.Entry<Integer, Map<Integer, Long>> entry1 : timeMatrix.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry1.getValue().entrySet()) {
                if (entry2.getValue() >= c && entry2.getValue() <= b) {
                    sum += (entry2.getValue() - c);
                    k++;
                }
            }
        }
        long result = 0;
        if (k > 0 ) {
            result = sum / k;
        }
        System.out.println("B: " + result);
        return result;
    }
    private static long calC(long a, long b) {
        if (a == 0) {
            System.out.println("C: 0");
            return 0;
        }
        if (a == 400000000 / 3600 / 24) {
            System.out.println("C: " + b);
            return b;
        }
        int k = 0;
        long sum = 0;
        for (Map.Entry<Integer, Map<Integer, Long>> entry1 : timeMatrix.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry1.getValue().entrySet()) {
                if (entry2.getValue() >= a && entry2.getValue() <= b) {
                    sum += entry2.getValue();
                    k++;
                }
            }
        }
        long result = 0;
        if (k > 0 ) {
            result = sum / k;
        }
        System.out.println("C: " + result);
        return result;
    }

    public static double calCal(long x, int line) {
        if (line == 1) {
            long B = 32; //calB(0, 2000000);
            long c = 0; //calC(0, 2000000);
            long b = 5000000 / 3600 / 24;
            if (x > c && x <= b) {
                return calTmp(x, c, B);
            } else {
                return 0;
            }
        }

        if (line == 2) {
            long A = 468; //calA(1500000, 6000000);
            long B = 586;//calB(1500000, 6000000);
            long c = 888; //calC(1500000, 6000000);
            long a = 4000000 / 3600 / 24;
            long b = 200000000 / 3600 / 24;
            if (x > c && x <= b) {
                return calTmp(x, c, B);
            } else if (x > a & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 3) {
            long A = 2027; //calA(5000000, 9000000);
            long B = 1134; //calB(5000000, 9000000);
            long c = 3011; //calC(5000000, 9000000);
            long a = 150000000 / 3600 / 24;
            long b = 500000000 / 3600 / 24;
            if (x > c && x <= b) {
                return calTmp(x, c, B);
            } else if (x > a & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 4) {
            long A = 7350; //calA(8000000, maxValue);
            long c = 10338; //calC(8000000, maxValue);
            long a = 400000000 / 3600 / 24;
            if (x > a & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }
        return 0.0;
    }

    private static double calTmp(long x, long c, long y) {
        double tmp = 1.0d * (x - c) * (x - c);
        tmp = 1.0d * tmp / (y);
        double res = exp(-tmp);
        if (res == 0) {
            //System.out.println("" + x + "\t" + c + "\t" + y + "\t" + tmp);
        }
        return res;
    }

    // inter为空进不来的
    public static double calGap(int i, int j, Set<Integer> inter) {
        double result = 0, t1 = 0, t2 = 0, t3 = 0, t4 = 0;
        for (Integer tmp : inter) {
            t1 = calCal(timeMatrix.get(i).get(tmp), 1) - calCal(timeMatrix.get(j).get(tmp), 1);
            t2 = calCal(timeMatrix.get(i).get(tmp), 2) - calCal(timeMatrix.get(j).get(tmp), 2);
            t3 = calCal(timeMatrix.get(i).get(tmp), 3) - calCal(timeMatrix.get(j).get(tmp), 3);
            t4 = calCal(timeMatrix.get(i).get(tmp), 4) - calCal(timeMatrix.get(j).get(tmp), 4);
            double temp = t1 * t1;
            temp += t2 * t2;
            temp += t3 * t3;
            temp += t4 * t4;
            temp = sqrt(temp) * abs(timeMatrix.get(i).get(tmp) - timeMatrix.get(j).get(tmp));
            result += temp;
        }
        result = result / (1.0d * inter.size());
        if(result == 0) {
            //System.out.println("" + t1 + "\t" + t2 + "\t" + t3 + "\t" + t4);
        }
        return result;
    }

    public static Set<Integer> calIncludeSet(Integer i, Integer j) {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        if (userMap.get(i) != null) {
            set1.addAll(userMap.get(i));
        }
        if (userMap.get(j) != null) {
            set2.addAll(userMap.get(j));
        }
        set1.retainAll(set2);
        return set1;
    }
}
