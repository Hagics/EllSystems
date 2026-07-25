package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T2 {
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("M1.txt",
                new DataHandling.StringHandler() {
                    @Override
                    public String handle(String string) {
                        return string;
                    }
                });
        for (String s : s1) {
            String[] part = s.split(":");
            StringBuilder sb = new StringBuilder();
            if (part.length > 3) {
                sb.append(part[0]).append(":").append(part[1]).append(":").append(part[2]);
            } else {
                sb.append(s);
            }
            System.out.println(sb);
        }
    }
}
