package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class T5 {
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("progressing\\Project26\\M2.txt");
        Map<String,String> detailByWord = new HashMap<>();
        LinkedList<String> s2 = DataHandling.getData("10kwl-OFA.txt", new DataHandling.StringHandler() {
            @Override
            public String handle(String string) {
                detailByWord.put(string.split(" ")[0],string);
                return string;
            }
        });
        LinkedList<String> rs = new LinkedList<>();
        for (String s : s1) {
            if (!detailByWord.containsKey(s)) {
                throw new NullPointerException();
            }
            rs.add(detailByWord.get(s));
        }
        for (String s : rs) {
            System.out.println(s);
        }
    }
}
