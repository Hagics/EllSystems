package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.*;

public class T13 {
    public static void main(String[] args) {
        List<String> words = DataHandling.getData("progressing\\special-words\\pv-0.2.txt");
        List<String> list = new LinkedList<>();
        for (String s : words) {
            if (s.contains(":")) {
                list.add(s.split(":")[0]);
            } else {
                list.add(s);
            }
        }
        for (String s : list) {
            System.out.println(s);
        }
    }
}
