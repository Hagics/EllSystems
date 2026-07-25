package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class T12 {
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("10kwsl-52.txt");
        LinkedList<String> s2 = DataHandling.getData("10kwl-52.7.txt");
        int smax = 300;
        Map<String, String> meaning = new HashMap<>();
        for (int i = 0; i < Math.min(smax,s2.size()); i++) {
            meaning.put(s2.get(i).split(" ")[0], s2.get(i).split(" ")[1] + s2.get(i).split(":")[1] + ":" + s2.get(i).split(":")[2]);
        }
        for (int i = 0; i < Math.min(smax,s1.size()); i++) {
            String str = s1.get(i);
            String w = str.split(" ")[0];
            if (meaning.containsKey(w)) {
                System.out.println(str.split(" ")[0] + " " + meaning.get(w));
            } else {
                System.out.println(str);
            }
        }
    }
}
