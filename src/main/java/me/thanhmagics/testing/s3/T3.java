package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T3 {
    public static void main(String[] args) {
        LinkedList<String> l1 = DataHandling.getData("10kw-3.4.txt");
        int i = 0;
        int min = 1200;
        int max = 2000;
        for (String s : l1) {
            i++;
            if (i < min || i > max) continue;
            if (s.split(":")[0].split(" ")[1].contains(",")) {
                if (s.split(":")[1].contains(",") && s.split(":")[1].contains(".")) {
                    System.out.println(s);
                }
            }
        }
    }
}
