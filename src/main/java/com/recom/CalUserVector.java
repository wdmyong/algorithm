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
public class CalUserVector {

    // 用户对用户，对应于每部电影的评分差值(List里面放的是你一开始 要的每两个用户的评分差值)
    private static Map<Integer, Map<Integer, List<Long>>> map = new HashMap<>();
    // 评分时间矩阵(用户对项目的评分时间)
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
            tmp.put(rating.getItemId(), rating.getTimeStamp());
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
                        //  这里目前存放的是差值
                        // long res = abs(timeMatrix.get(i).get(integer) - timeMatrix.get(j).get(integer));
                        long res = abs(timeMatrix.get(i).get(integer) - timeMatrix.get(j).get(integer));
                        tmpList.add(res);
                        if (res > maxValue) {
                            maxValue = res;
                        }
                        tmpMap.put(j, tmpList);
                        map.put(i, tmpMap);
                    }
                }
            }
        }
        System.out.println("最大值：" + maxValue);
        System.out.println("line1:");
        calB(0, 2000000);
        System.out.println("line2:");
        calA(1500000, 6000000);
        calB(1500000, 6000000);
        System.out.println("line3:");
        calA(5000000, 9000000);
        calB(5000000, 9000000);
        System.out.println("line4:");
        calA(8000000, maxValue);
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
        if (a == 8000000) {
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

    public static double calCal(long x, int line) {
        if (line == 1) {
            long B = 896981; //calB(0, 2000000);
            long c = 0; //calC(0, 2000000);
            if (x > c && x <= B) {
                long tmp = (x - c) * (x - c);
                tmp = tmp / B;
                return exp(tmp);
            } else {
                return 0;
            }
        }

        if (line == 2) {
            long A = 1684801; //calA(1500000, 6000000);
            long B = 4754777;//calB(1500000, 6000000);
            long c = 3663602; //calC(1500000, 6000000);
            if (x > c && x <= B) {
                return calTmp(x, c, B);
            } else if (x > A & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 3) {
            long A = 3077200; //calA(5000000, 9000000);
            long B = 7923721; //calB(5000000, 9000000);
            long c = 6898060; //calC(5000000, 9000000);
            if (x > c && x <= B) {
                return calTmp(x, c, B);
            } else if (x > A & x <= c){
                return calTmp(x, c, A);
            } else {
                return 0;
            }
        }

        if (line == 4) {
            long A = 6007619; //calA(8000000, maxValue);
            long c = 18561354; //calC(8000000, maxValue);
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
