package me.thanhmagics.testing.s3;

import me.thanhmagics.gen4.EllSystem4;
import me.thanhmagics.gen5.Renderer;
import me.thanhmagics.testing.DataHandling;

import java.util.*;

public class T1 { //generate word details for child-file
    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("st\\merge\\0-2400.VNE.txt");
        LinkedList<String> origin = DataHandling.getData("10kw-3.4.txt");
        Map<String,String> kf = new LinkedHashMap<>();
        for (String s : origin) {
            kf.put(s.split(" ")[0],s);
        }
        LinkedList<String> rs = new LinkedList<>();
        for (String s : s1) {
            rs.add(kf.get(s));
        }
        for (String s : rs)
            System.out.println(s);
    }

}
