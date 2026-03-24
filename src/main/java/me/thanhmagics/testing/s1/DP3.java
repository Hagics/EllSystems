package me.thanhmagics.testing.s1;

import java.util.*;

public class DP3 {
    public static void main(String[] args) {
        Map<Integer,StringBuilder> map = new LinkedHashMap<>();
        int[] intervals = new int[] {0,1,3,6,10};
        for (int i = 1; i <= 125; i++) {
            map.put(i,new StringBuilder());
        }
        int seasons = 110;
        for (int i = 1; i <= seasons; i++) {
            for (int j = 0; j < intervals.length; j++) {
                map.get(i + intervals[j]).append("S").append(i).append("-R").append(j).append(" ");
            }
        }
        map.forEach((k,v) -> {
            System.out.println(k + ": " + v.toString());
        });
    }
}
