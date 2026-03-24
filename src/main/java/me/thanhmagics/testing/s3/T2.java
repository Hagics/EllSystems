package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class T2 {
    public static void main(String[] args) {
        LinkedList<String> l2 = DataHandling.getData("progressing/10kw-series/s2/10kw-3.2.txt");
        LinkedList<String> l1 = DataHandling.getData("10kw-3.4.txt");
        List<String> strings = new ArrayList<>();
        for (String s : l1) {
            strings.add(s.split(" ")[0].toLowerCase());
        }
        for (String s : l2) {
            if (strings.contains(s.split(" ")[0].toLowerCase())) continue;
            System.out.println(s);
        }
    }
}
