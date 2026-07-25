package me.thanhmagics.testing.s4;

import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.testing.DataHandling;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

public class T10 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing\\word-handing\\pn.txt");
        Collections.sort(data);
        for (String s : data) {
            System.out.println(s);
        }
    }
}
