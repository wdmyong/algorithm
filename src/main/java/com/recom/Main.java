package com.recom;

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
        List<Rating> testList = readData("/Users/duanyong/Documents/ml-100k/ua.test", map);
        List<Rating> trainList = readData("/Users/duanyong/Documents/ml-100k/ua.base", map);
        dealWithData(testList, trainList);
    }

    // 读取数据集
    private static List<Rating> readData(String path, Map<Integer, Long> map) throws Exception{
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String readLine = "";
        List<Rating> ratingList = new ArrayList<>();
        int i = 0;
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
                calGuessRating = new CalGuessRatingImp1();
                break;
            case 2:
                calGuessRating = new CalGuessRatingImp2();
                break;
            case 3:
                calGuessRating = new CalGuessRatingImp3();
                break;
            case 4:
                calGuessRating = new CalGuessRatingImp4();
                break;
            case 5:
                calGuessRating = new CalGuessRatingImp5();
                break;
            case 6:
                calGuessRating = new CalGuessRatingImp6();
                break;
            case 7:
                calGuessRating = new CalGuessRatingImp7();
                break;
            case 8:
                calGuessRating = new CalGuessRatingImp8();
                break;
            case 9:
                calGuessRating = new CalGuessRatingImp9();
                break;
            case 11:
                calGuessRating = new CalGuessRatingImp11();
                break;
            case 12:
                calGuessRating = new CalGuessRatingImp12();
                break;
            case 13:
                calGuessRating = new CalGuessRatingImp13();
                break;
            case 14:
                calGuessRating = new CalGuessRatingImp14();
                break;
            case 15:
                calGuessRating = new CalGuessRatingImp15();
                break;
            case 17:
                calGuessRating = new CalGuessRatingImp17();
                break;
            case 18:
                calGuessRating = new CalGuessRatingImp18();
                break;
            case 19:
                calGuessRating = new CalGuessRatingImp19();
                break;
            case 20:
                calGuessRating = new CalGuessRatingImp20();
                break;
            case 21:
                calGuessRating = new CalGuessRatingImp21();
                break;
        }
        return  calGuessRating.calGuessRating(testList, trainList);
    }
}
