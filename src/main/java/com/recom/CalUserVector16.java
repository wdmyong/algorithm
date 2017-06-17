package com.recom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

/**
 * Created by yongduan on 2017/3/11.
 */
public class CalUserVector16 {

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
            tmp.put(rating.getItemId(), rating.getTimeActive());
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
        calB(0, 5000000);
        System.out.println("line2:");
        calA(4000000, 200000000);
        calB(4000000, 200000000);
        System.out.println("line3:");
        calA(150000000, 500000000);
        calB(150000000, 500000000);
        System.out.println("line4:");
        calA(400000000, maxValue);
    }

    private static long calA(long a, long b) {
        long c = calC(a, b);
        int k = 1;
        long sum = 0;
        for (Map.Entry<Integer, Map<Integer, Long>> entry1 : timeMatrix.entrySet()) {
            for (Map.Entry<Integer, Long> entry2 : entry1.getValue().entrySet()) {
                if (entry2.getValue() <= c) {
                    sum += entry2.getValue();
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
                if (entry2.getValue() <= c) {
                    sum += entry2.getValue();
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
        if (a == 400000000) {
            System.out.println("C: " + b);
            return b;
        }
        int k = 0;
        long sum = 0;
        long result = 0;
        if (k > 0 ) {
            result = sum / k;
        }
        System.out.println("C: " + result);
        return result;
    }

    public static double calCal(long x, int line) {
        if (line == 1) {
            long B = 3041324; //calB(0, 2000000);
            long c = 0; //calC(0, 2000000);
            if (x > c && x <= B) {
                return calTmp(x, c, B);
            } else {
                return 0;
            }
        }

        if (line == 2) {
            long A = 35592573; //calA(1500000, 6000000);
            long B = 126038055;//calB(1500000, 6000000);
            long c = 70703256; //calC(1500000, 6000000);
            if (x > c && x <= B) {
                return calTmp(x, c, B);
            } else if (x > A & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 3) {
            long A = 81616391; //calA(5000000, 9000000);
            long B = 359919156; //calB(5000000, 9000000);
            long c = 273877019; //calC(5000000, 9000000);
            if (x > c && x <= B) {
                return calTmp(x, c, B);
            } else if (x > A & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 4) {
            long A = 265572962; //calA(8000000, maxValue);
            long c = 893286550; //calC(8000000, maxValue);
            if (x > A & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }
        return 0.0;
    }

    private static double calTmp(long x, long c, long y) {
        long tmp = (x - c) * (x - c);
        tmp = tmp / y;
        return exp(-tmp);
    }

    // inter为空进不来的
    public static double calGap(int i, int j, Set<Integer> inter) {
        double result = 0;
        for (Integer tmp : inter) {
            double temp = (calCal(timeMatrix.get(i).get(tmp), 1) - calCal(timeMatrix.get(j).get(tmp), 1))
                    * (calCal(timeMatrix.get(i).get(tmp), 1) - calCal(timeMatrix.get(j).get(tmp), 1));
            temp += (calCal(timeMatrix.get(i).get(tmp), 2) - calCal(timeMatrix.get(j).get(tmp), 2))
                    * (calCal(timeMatrix.get(i).get(tmp), 2) - calCal(timeMatrix.get(j).get(tmp), 2));
            temp += (calCal(timeMatrix.get(i).get(tmp), 3) - calCal(timeMatrix.get(j).get(tmp), 3))
                    * (calCal(timeMatrix.get(i).get(tmp), 3) - calCal(timeMatrix.get(j).get(tmp), 3));
            temp += (calCal(timeMatrix.get(i).get(tmp), 4) - calCal(timeMatrix.get(j).get(tmp), 4))
                    * (calCal(timeMatrix.get(i).get(tmp), 4) - calCal(timeMatrix.get(j).get(tmp), 4));
            temp = sqrt(temp) * (timeMatrix.get(i).get(tmp) - timeMatrix.get(j).get(tmp));
            result += temp;
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
