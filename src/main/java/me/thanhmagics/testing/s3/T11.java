package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class T11 { //merger
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("10kwl-52.7.txt");
        LinkedList<String> s2 = DataHandling.getData("10kwl-4.7.txt");
        Map<String,String> s2ByWord = new HashMap<>();
        int min = 0;
        int max = 3000;
        int j = 0;
        for (String s : s2) {
            j++;
            if (j > max) break;
            String key = s.split(" ")[0];
            String suffix = s.split(":")[2];
            s2ByWord.put(key,suffix);
        }
         min = 0;
         max = 300;
        for (int i = min; i < Math.min(max,s1.size()); i++) {
            System.out.println(s1.get(i) + ":" + s2ByWord.get(s1.get(i).split(" ")[0]));
        }
    }
}
