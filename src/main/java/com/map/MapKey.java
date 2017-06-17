package com.map;

import java.util.HashMap;
import java.util.Map;

public class MapKey {

    private int a;
    private int b;

    public MapKey(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int hashCode() {
        return a + b;
    }

    @Override
    public boolean equals(Object obj) {
        MapKey oMapKey = (MapKey)obj;
        if (this.a == oMapKey.a && this.b == oMapKey.b) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Map<MapKey, Integer> map = new HashMap<>();
        MapKey mk1 = new MapKey(1, 12);
        MapKey mk2 = new MapKey(3, 10);
        MapKey mk3 = new MapKey(1, 12);
        map.put(mk1, 1);
        map.put(mk2, 2);
        map.put(mk3, 3);
        System.out.println(map.size());
        for (MapKey key : map.keySet()) {
            System.out.println("key: " + key + "value: " + map.get(key));
        }
        System.out.println("-------------");
        for (Map.Entry<MapKey, Integer> entry : map.entrySet()) {
            System.out.println("key: " + entry.getKey() + "value: " + entry.getValue());
        }
    }
}
