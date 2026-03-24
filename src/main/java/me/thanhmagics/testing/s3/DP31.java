package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class DP31 {

    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing/others/pv.txt");
        LinkedList<String> rs = new LinkedList<>();
        for (String s : data) {
            if (s.isEmpty()) continue;
            rs.add(s.split("\\. ")[1]);
        }
        DataHandling.saveData("progressing/others/pv.txt",rs);
    }
}
