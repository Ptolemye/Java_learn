package com.ddhen.base;

import java.util.*;

public class Demo {
    public static void main(String[] args) {
        HashMap<Integer, String> map = new TreeMap<>();
        map.put(1,"23123");
        map.put(3,"321321");
        map.put(4,"2312312");
        map.put(5,"3231");
        Set<Integer> a = map.keySet();
        for(Integer i : a){
            System.out.println(i);
        }

        System.out.println(map);
        System.out.println(map.size());
    }

}
