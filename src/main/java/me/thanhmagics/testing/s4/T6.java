package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class T6 {
    public static void main(String[] args) {
        LinkedList<String> rs = new LinkedList<>();
        LinkedList<String> s2 = DataHandling.getData("M3.txt", new DataHandling.StringHandler() {
            @Override
            public String handle(String string) {
                rs.add(string.split(" ")[0]);
                return string;
            }
        });

        for (String s : rs) System.out.println(s);
    }
}
