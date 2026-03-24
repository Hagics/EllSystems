package me.thanhmagics.testing.s2;

import me.thanhmagics.testing.DataHandling;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DP14 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing/10kw-series/s2/10kw-2.txt");
        LinkedList<String> exits = new LinkedList<>();
        Map<String,String> d = new HashMap<>();
        for (String s : data) {
            String word = s.split(" ")[0].toLowerCase();
            if (!exits.contains(word)) {
                exits.add(word);
                d.put(word,s);
            }
        }
        LinkedList<String> rs = new LinkedList<>();
        for (String s : exits) {
            System.out.println(d.get(s));
            rs.add(d.get(s));
        }
        System.out.println(exits.size() + " - " + data.size());
        DataHandling.saveData("progressing/10kw-series/s2/10kw-3.txt",rs);
    }
}
