package com.recom;

import java.util.ArrayList;
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
public class CalUserVector15 {

    // 用户对用户，两个用户共有项目各自的评分时间- 发布时间都放进去
    private static Map<Integer, Map<Integer, List<Long>>> map = new HashMap<>();
    // 评分时间-发布时间矩阵(用户对项目的评分时间-发布时间)
    private static Map<Integer, Map<Integer, Long>> timeMatrix = new HashMap<>();

    public static Map<Integer, Long> MAX_MAP = new HashMap<>();
    public static Map<Integer, Long> MIN_MAP = new HashMap<>();

    // 每个用户的评分集合(用户所有评分的项目集合)
    private static Map<Integer, Set<Integer>> userMap = new HashMap<>();

    private static long maxValue = 0L;

    public static void cal(List<Rating> ratingList) {
        for (Rating rating : ratingList) {
            long m = rating.getTimeActive() / 24 / 3600;
            if (MAX_MAP.containsKey(rating.getUserId())) {
                if (MAX_MAP.get(rating.getUserId()) < m) {
                    MAX_MAP.put(rating.getUserId(), m);
                }
            } else {
                MAX_MAP.put(rating.getUserId(), m);
            }
            if (MIN_MAP.containsKey(rating.getUserId())) {
                if (MIN_MAP.get(rating.getUserId()) > m) {
                    MIN_MAP.put(rating.getUserId(), m);
                }
            } else {
                MIN_MAP.put(rating.getUserId(), m);
            }
            Map<Integer, Long> tmp = timeMatrix.get(rating.getUserId());
            if (tmp == null) {
                tmp = new HashMap<>();
            }
            tmp.put(rating.getItemId(), m);
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
                        Map<Integer, List<Long>> tmpMap = map.get(i);
                        if (tmpMap == null) {
                            tmpMap = new HashMap<>();
                        }
                        List<Long> tmpList = tmpMap.get(j);
                        if (tmpList == null) {
                            tmpList = new ArrayList<>();
                        }
                        //  把用户i、j 的评分时间-发布时间都放进去
                        tmpList.add(timeMatrix.get(i).get(integer));
                        tmpList.add(timeMatrix.get(j).get(integer));
                        if (timeMatrix.get(i).get(integer) > maxValue) {
                            maxValue = timeMatrix.get(i).get(integer);
                        }
                        if (timeMatrix.get(j).get(integer) > maxValue) {
                            maxValue = timeMatrix.get(j).get(integer);
                        }
                        tmpMap.put(j, tmpList);
                        map.put(i, tmpMap);
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
        for (int i = 1; i < 943; i++) {
            for (int j = i + 1; j <= 943; j++) {
                if (map.containsKey(i)) {
                    List<Long> list = map.get(i).get(j);
                    if (list != null && list.size() > 0) {
                        for (Long t : list) {
                            if (t <= c) {
                                sum += t;
                                k++;
                            }
                        }
                    }
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
        for (int i = 1; i < 943; i++) {
            for (int j = i + 1; j <= 943; j++) {
                if (map.containsKey(i)) {
                    List<Long> list = map.get(i).get(j);
                    if (list != null && list.size() > 0) {
                        for (Long t : list) {
                            if (t > c && t <= b) {
                                sum += t;
                                k++;
                            }
                        }
                    }
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
        for (int i = 1; i < 943; i++) {
            for (int j = i + 1; j <= 943; j++) {
                if (map.containsKey(i)) {
                    List<Long> list = map.get(i).get(j);
                    if (list != null && list.size() > 0) {
                        for (Long t : list) {
                            if (t >= a && t <= b) {
                                sum += t;
                                k++;
                            }
                        }
                    }
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

    public static double calGap(long v1, long v2) {
        double temp = (calCal(v1, 1) - calCal(v2, 1)) * (calCal(v1, 1) - calCal(v2, 1));
        temp += (calCal(v1, 2) - calCal(v2, 2)) * (calCal(v1, 2) - calCal(v2, 2));
        temp += (calCal(v1, 3) - calCal(v2, 3)) * (calCal(v1, 3) - calCal(v2, 3));
        temp += (calCal(v1, 4) - calCal(v2, 4)) * (calCal(v1, 4) - calCal(v2, 4));
        temp = sqrt(temp) * abs(v1 - v2);
        return temp;
    }

    public static double calCal(long x, int line) {
        if (line == 1) {
            long B = 35; //calB(0, 2000000);
            long c = 0; //calC(0, 2000000);
            long b = 5000000 / 3600 / 24;
            if (x > c && x <= b) {
                return calTmp(x, c, B);
            } else {
                return 0;
            }
        }

        if (line == 2) {
            long A = 411; //calA(1500000, 6000000);
            long B = 1458;//calB(1500000, 6000000);
            long c = 817; //calC(1500000, 6000000);
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
            long A = 944; //calA(5000000, 9000000);
            long B = 4167; //calB(5000000, 9000000);
            long c = 3170; //calC(5000000, 9000000);
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
            long A = 3073; //calA(8000000, maxValue);
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
            temp = sqrt(temp) * abs(timeMatrix.get(i).get(tmp) - timeMatrix.get(j).get(tmp));
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
