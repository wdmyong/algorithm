package com.recom;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 时间工具类
 *
 * @author Wang Baomin
 * @since 2016/9/14
 */
public class TimeUtil {

    public static void main(String[] args) {
        System.out.println(1.0/0.0);
        double sum1 = 0, sum2 = 0, sum3 = 0;
        System.out.println(sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3)) * 2);
        String[] sss = {
                "ED498660-4B50-E511-8449-D4AE5263BD92世茂城",
                "AC95FE85-9365-E511-9211-D4AE5263BD92新希望皇冠国际",
                "62C3093F-0DEC-E511-8B1C-D4AE5263BD92湟普国际湟座",
                "F9BE0DD7-B3F4-E511-8B1C-D4AE5263BD92西派国际",
                "9E0101D9-8512-E611-8B1C-D4AE5263BD92千禧河畔国际社区",
                "99EB8F41-5516-E611-8B1C-D4AE5263BD92荣盛香榭南庭",
                "1D80E18D-5B16-E611-8B1C-D4AE5263BD92荣盛香榭兰庭",
                "6244D8D2-E21B-E611-8B1C-D4AE5263BD92朗基御今缘",
                "09151A9B-6D1D-E611-8B1C-D4AE5263BD92华固一品",
                "918B2BBB-E131-E611-8B1C-D4AE5263BD92炜岸城米拉公寓",
                "992C12B3-C932-E611-8B1C-D4AE5263BD92蓝花楹",
                "9CCB744E-7233-E611-8B1C-D4AE5263BD92南湖逸家",
                "1A6F3601-1C38-E611-8B1C-D4AE5263BD92华汇天地",
                "8853919C-703E-E611-8B1C-D4AE5263BD92东原城",
                "CF1EBDC6-2A4E-E611-8B1C-D4AE5263BD92中德英伦世邦",
                "8BE08611-1C52-E611-8B1C-D4AE5263BD92金茂悦龙山",
                "008B85B8-876E-E611-8B1C-D4AE5263BD92佳年华广场",
                "1192B5FB-8F75-E611-8B1C-D4AE5263BD92成都ICC凯旋门",
                "E94B3CAF-9A83-E611-8B1C-D4AE5263BD92恒大中央广场",
                "819935FD-5784-E611-8B1C-D4AE5263BD92金科东方雅郡",
                "D212520A-5884-E611-8B1C-D4AE5263BD92炜岸城",
                "45604F34-2585-E611-8B1C-D4AE5263BD92成都后花园",
                "B691C141-2585-E611-8B1C-D4AE5263BD92领地",
                "2DD49A54-2585-E611-8B1C-D4AE5263BD92鼎犀名城",
                "E635325F-2585-E611-8B1C-D4AE5263BD92中冶中央公园",
                "DF484469-2585-E611-8B1C-D4AE5263BD92中铁天宏康郡",
                "9F00449D-2585-E611-8B1C-D4AE5263BD92蓝润光华春天",
                "61AA438E-2985-E611-8B1C-D4AE5263BD92鑫苑鑫都汇",
                "EDB23AFC-0486-E611-8B1C-D4AE5263BD92隆鑫印象东方",
                "E6027D5E-B786-E611-8B1C-D4AE5263BD92中德麓府",
                "29E12F36-BC86-E611-8B1C-D4AE5263BD92鲁能城",
                "A209DCE2-3D8D-E611-8B1C-D4AE5263BD92南湖世纪",
                "65EDE464-B491-E611-8B1C-D4AE5263BD92龙光君悦华庭",
                "891CF301-B691-E611-8B1C-D4AE5263BD92佳兆业广场",
                "2EE54059-A9FB-41DB-A69C-3C40748B7968华都云景台",
                "8D7B7684-693C-4C4E-9843-315D7FE2FC15四川煤田光华之心",
                "73DB788B-310E-444F-96B3-3984EAE21E34绿地４６８",
                "12F62DAD-A482-4AEC-8BCA-75410306FB5E锦汇城",
                "884C6680-5A8B-4616-939E-DACB3B8C14C1西岸翠景",
                "E8277838-74AC-4434-BA02-EF857DBF4B67锦巷蘭台",
                "7F1A108B-DCFB-49C3-82E0-754B5257257F金河谷"
        };
        int len = 0;
        for (String tmp : sss) {
            len += tmp.length();
        }
        System.out.println(len);
        String s = "01747C65-A5A3-E511-8B1C-D4AE5263BD92[:]中粮大道[;]01F787A2-ADB2-E511-8B1C-D4AE5263BD92[:]金泰丽湾[,]985DCCDE-7900-E611-8B1C-D4AE5263BD92[:]天房璟悦府[;]14291C7D-7F00-E611-8B1C-D4AE5263BD92[:]首创暖山[;]781C1C8A-7F00-E611-8B1C-D4AE5263BD92[:]首创城[;]CF7DAF03-8F07-E611-8B1C-D4AE5263BD92[:]路劲太阳城时光里[;]776F72B8-D01C-E611-8B1C-D4AE5263BD92[:]华润像树湾[;]09FFD315-E131-E611-8B1C-D4AE5263BD92[:]中骏柏景湾[;]C9250327-E131-E611-8B1C-D4AE5263BD92[:]大唐印象[;]500167E7-FE47-E611-8B1C-D4AE5263BD92[:]中国铁建国际城[;]6A05DEF5-D173-E611-8B1C-D4AE5263BD92[:]唐望府[;]2A4B9005-D273-E611-8B1C-D4AE5263BD92[:]和泓四季公馆[;]D0E7AA9F-221D-40FE-8F0E-8CFC43775DAF[:]雅颂居[;]8CBF9D19-EBAB-4166-8504-29AA7BDD0994[:]富贾花园[;]92B99127-A7D3-439B-97DB-9DE55320366C[:]华润橡树湾[;]EACF8A54-4B04-4D22-AB73-2E79DA449D5D[:]保利香颂湖";
        System.out.println("赵博");
        System.out.println(s.length());
        String[] s1 = s.split("\\[;]");
        for (String tmp : s1) {
            for (String t : tmp.split("\\[:]")) {
                System.out.print(t + "\t");
            }
            System.out.println();
        }
        long tmp = 893286550L* 893286550L;
        System.out.println(893286550L* 893286550L);
        System.out.println(Math.exp(tmp));
        Map<String, String> variables = new HashMap<String, String>(6);
        variables.put("1", "1");
        variables.put("2", "1");
        variables.put("3", "1");
        variables.put("4", "1");
        variables.put("5", "1");
        variables.put("6", "4");
        System.out.print(variables.get("6"));

        Set<String> stringSet = new HashSet<>();
        stringSet.add("a");
        stringSet.add("b");
        Iterator<String> iterator = stringSet.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        if (stringSet == null) {
            System.out.println("null");
        } else {
            System.out.println(stringSet.size());
        }
    }

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

        if (StringUtils.isBlank(time)) {
            return 0;
        }
        //System.out.println(time + "\taaa");
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
