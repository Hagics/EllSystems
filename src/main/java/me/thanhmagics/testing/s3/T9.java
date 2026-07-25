package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T9 { //print word by min-max
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("10kwl-4.7.txt");
        int min = 1200;
        int max = 10000;
        for (int i = min; i < Math.min(max,s1.size()); i++) {
            System.out.println(s1.get(i));
        }
    }
}
