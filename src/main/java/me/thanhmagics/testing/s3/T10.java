package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T10 {
    public static void main(String[] args) {
        LinkedList<String> raw = DataHandling.getData("data\\dt2.txt");
        for (String s : raw) {
            StringBuilder sb = new StringBuilder(s);
            sb.deleteCharAt(sb.length() -1 );
            System.out.println(sb);
        }
    }
}
