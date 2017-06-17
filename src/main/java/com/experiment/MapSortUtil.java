package com.experiment;

import java.util.*;

public class MapSortUtil {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return -1 * (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    public static  void main(String[] args) {
        Map<Integer, Double> map = new HashMap<>();
        map.put(1, 1.1);
        map.put(3, 1.2);
        map.put(2, 1.3);
        map = sortByValue(map);
        for (Integer i : map.keySet()) {
            System.out.println(i + "\t" + map.get(i));
        }
    }
}
