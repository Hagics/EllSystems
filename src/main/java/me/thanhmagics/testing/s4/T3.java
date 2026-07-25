package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T3 { //different check

    public static void main(String[] args) {
        LinkedList<String> s1 = DataHandling.getData("M1.txt",
                new DataHandling.StringHandler() {
                    @Override
                    public String handle(String string) {
                        return string;
                    }
                });
        LinkedList<String> s2 = DataHandling.getData("progressing/Project26/M1i.txt",
                new DataHandling.StringHandler() {
                    @Override
                    public String handle(String string) {
                        return string;
                    }
                });

        for (int i = 0; i < s2.size(); i++) {
            String s = s1.get(i);
            String ss = s2.get(i);
            if (!s.equalsIgnoreCase(ss)) {
                System.out.println(s + " {=} " + ss);
            }
        }
    }
}
