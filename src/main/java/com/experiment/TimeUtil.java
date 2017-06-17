package com.experiment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeUtil {

    public static void main(String[] args) {}

    private static Map<String, String> MAP = new HashMap<>();
    static {
        MAP.put("Jan", "01");
        MAP.put("Feb", "02");
        MAP.put("Mar", "03");
        MAP.put("Apr", "04");
        MAP.put("May", "05");
        MAP.put("Jun", "06");
        MAP.put("Jul", "07");
        MAP.put("Aug", "08");
        MAP.put("Sep", "09");
        MAP.put("Oct", "10");
        MAP.put("Nov", "11");
        MAP.put("Dec", "12");
    }
    public static final String FORMAT_DAY_FULL = "yyyy-MM-dd";

    public static long getLongValue1(String time) {

        if (time == null || time.length() == 0
                || time.trim().length() == 0) {
            return 0;
        }
        for (String key : MAP.keySet()) {
            if (time.contains(key)) {
                time = time.replace(key, MAP.get(key));
                break;
            }
        }
        try {
        //System.out.println(time);
        int pos = time.indexOf("-");
        if (pos < 4) {
            String[] array = time.split("-");
            time = "";
            for (int i = 2; i >= 0; i--) {
                if (i == 0) {
                    time += array[i];
                } else {
                    time += array[i] + "-";
                }
            }
        }
        int year = Integer.parseInt(time.substring(0, 4));
        if (year < 1970) {
            return 0;
        }} catch (Exception e){
            System.out.print("");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DAY_FULL);
        Date data;
        try {
            data = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
        return data.getTime() / 1000;
    }

}
