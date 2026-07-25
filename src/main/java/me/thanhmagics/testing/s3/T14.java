package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.Collections;
import java.util.List;

public class T14 {
    public static void main(String[] args) {
        List<String> words = DataHandling.getData("collacation2.txt");
        Collections.shuffle(words);
        for (String s : words) {
            System.out.println(s);
        }

    }
}
