package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T9 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing\\word-handing\\p2.txt");
        for (String s : data) {
        }
        for (int i = 0; i < data.size() ; i++) {
            String s = data.get(i);
            try {
                Integer.parseInt(s.split("\t")[0]);
                System.out.println(s.split("\t")[1] + ":" + s.split("\t")[3].toLowerCase() + ":" + s.split("\t")[2].toLowerCase());
            } catch (Exception e) {

            }
        }
    }
}
