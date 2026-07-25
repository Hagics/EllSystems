package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T1 {

    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("progressing\\Project26\\M1i-lite.txt",
        new DataHandling.StringHandler() {
            @Override
            public String handle(String string) {
                return string.split(" ")[0];
            }
        });
        for (String s : s1) {
            System.out.println(s);
        }
    }
}
