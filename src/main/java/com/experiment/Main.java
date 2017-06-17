package com.experiment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yongduan on 2017/1/14.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        File file = new File("/Users/duanyong/Documents/ml-100k/u.item");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readLine = "";
        Map<Integer, Long> map = new HashMap<>();
        while ((readLine = bufferedReader.readLine()) != null) {
            String[] array = readLine.split("\\|");
            Integer itemId = Integer.parseInt(array[0]);
            Long releaseTime = TimeUtil.getLongValue1(array[2]);
            map.put(itemId, releaseTime);
            if (releaseTime == -820569600L) {
                //System.out.println(array[2]);
            }
        }
        bufferedReader.close();
        fileReader.close();
        List<Rating> testList = readData("/Users/duanyong/Documents/ml-100k/ub.test", map);
        List<Rating> trainList = readData("/Users/duanyong/Documents/ml-100k/ub.base", map);
        dealWithData(testList, trainList);
    }

    // 读取数据集
    private static List<Rating> readData(String path, Map<Integer, Long> map) throws Exception{
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readLine = "";
        List<Rating> ratingList = new ArrayList<>();
        while ((readLine = bufferedReader.readLine()) != null) {
            String[] array = readLine.split("\t");
            int userId = Integer.parseInt(array[0]);
            int itemId = Integer.parseInt(array[1]);
            double score = Double.parseDouble(array[2]);
            long commentTime = Long.parseLong(array[3]);
            long releaseTime = map.get(itemId);
            // 训练集中时间不正常的数据过滤掉
            if (commentTime - releaseTime < 0 && !path.contains("test")) {
                //System.out.println(i++);
                continue;
            }
            //commentTime = commentTime / 3600 / 24 / 30;
            //releaseTime = releaseTime / 3600 / 24 / 30;
            //System.out.println((commentTime - releaseTime)/ 3600 / 24 / 30);
            Rating rating = new Rating(userId, itemId, score, commentTime, releaseTime, (commentTime - releaseTime));
            ratingList.add(rating);
            if ((commentTime - releaseTime) / 3600 / 24 / 30 == 660) {
                //System.out.println(releaseTime);
            }
        }
        bufferedReader.close();
        fileReader.close();
        return ratingList;
    }
    private static void dealWithData(List<Rating> testList, List<Rating> trainList) {
        int method = 21;
        double result = guessRating(testList, trainList, method);
        System.out.println("MAE with method" + method + " :\t" + result);
    }

    private static double guessRating(List<Rating> testList, List<Rating> trainList, int method) {
        CalGuessRating calGuessRating = null;
        switch (method) {
            case 1:
                calGuessRating = new CalGuessRatingCos();
                break;
            case 2:
                calGuessRating = new CalGuessRatingPearson();
                break;
            case 11:
                calGuessRating = new CalGuessRatingWay1();
                break;
            case 12:
                calGuessRating = new CalGuessRatingWay1Modify();
                break;
            case 21:
                calGuessRating = new CalGuessRatingWay2();
                break;
            case 31:
                calGuessRating = new CalGuessRatingWay3();
                break;
            case 32:
                calGuessRating = new CalGuessRatingWay3Standard();
                break;
            case 33:
                calGuessRating = new CalGuessRatingWay3Modify();
                break;
        }
        return  calGuessRating.calGuessRating(testList, trainList);
    }
    
    public static void fu(String a) {
        
    }
    public static void fun(String b) {
        fu(b);
        fu(new String("ac"));
    }
}
