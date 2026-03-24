package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class T6 { //u

    public static void main(String[] args) {
        List<String> w1 = DataHandling.getData("F0-24.txt");
        List<String> w2 = DataHandling.getData("10kwl-0.24.txt");
        Map<String,String> ww1 = new HashMap<>();
        for (String w : w2) {
            String word = w.split(" ")[0];
            if (!ww1.containsKey(word)) {
                ww1.put(word, w);
            }
        }
        for (String w : w1) {
            String word = w.split(" ")[0];
            ww1.remove(word);
        }
        ww1.forEach((k,v) -> {
            System.out.println(v);
        });
    }
}
