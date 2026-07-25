package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class T8 { //reconfig file
    public static void main(String[] args) {
        LinkedList<String> raw = DataHandling.getData("10kwl-OFA-raw.txt");
        LinkedList<String> target = DataHandling.getData("10kwl-OFA.txt");
        LinkedList<String> result = new LinkedList<>();
        for (String word : target)  {
            for (String s : raw) {
                String w2 = s.split(" ")[0];
                if (w2.equalsIgnoreCase(word)) {
                    result.add(s);
                    break;
                }
            }
        }
        for (String s : result) {
            System.out.println(s);
        }
    }
}
