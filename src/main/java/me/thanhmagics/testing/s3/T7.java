package me.thanhmagics.testing.s3;

import me.thanhmagics.gen5.BalancedRandom;

import java.util.*;

public class T7 { //Random System Testing
    public static void main(String[] args) {
//        LinkedHashMap<Integer,String> testing = new LinkedHashMap<>();
//        testing.putLast(1,"a");
//        testing.putLast(2,"b");
//        testing.putLast(3,"c");
//        for (Integer i : testing.keySet())
//            System.out.println(i);
//        Random r = new Random();
//        System.out.println(r.nextDouble(10));
        Map<Integer,Integer> appeared = new HashMap<>();
        BalancedRandom balancedRandom = new BalancedRandom(6);
        List<Integer> inRoll = List.of(1,2,3,4,5,6,7,8,9,10);
        for (int i = 0; i < 100000; i++) {
            int j = balancedRandom.generate(inRoll);
            System.out.println(j);
            if (!appeared.containsKey(j)) appeared.put(j,0);
            int old = appeared.get(j);
            appeared.replace(j, old + 1);
        }
        Map<Integer,Integer> appeared2 = new HashMap<>();
        List<Integer> cloner = new ArrayList<>(inRoll);
        Collections.shuffle(cloner);
        for (int i = 0; i < 100000; i++) {
            int j = cloner.getFirst();
            System.out.println(j);
            if (!appeared2.containsKey(j)) appeared2.put(j,0);
            int old = appeared2.get(j);
            appeared2.replace(j, old + 1);
            Collections.shuffle(cloner);
        }
        System.out.println("=========== report =========="); //83
        for (int i : appeared.keySet()) {
            int value = appeared.get(i);
            System.out.println(i + ": " + value);
        }
        System.out.println("=========================="); //244
        for (int i : appeared2.keySet()) {
            int value = appeared2.get(i);
            System.out.println(i + ": " + value);
        }
    }
}
