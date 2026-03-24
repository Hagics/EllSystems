package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;

public class T4 {
    public static void main(String[] args) {
        LinkedList<String> l1 = DataHandling.getData("10kw-3.4.txt");
        LinkedList<String> rs = new LinkedList<>();
        int i=0,min=1200,max = 2000;
        for (String s : l1) {
            i++;
            if (i < min || i > max) continue;
            String type = s.split(":")[0].split(" ")[1];
            String o = s.split(":")[0].split(" ")[0];
            if (type.equals("verb,noun")) {
                String meaning = s.split(":")[1];
                if (meaning.contains(". ")) {
                    String verb = meaning.split("\\. ")[0];
                    String noun = meaning.split("\\. ")[1];
                    rs.add(o + " noun,verb:" + noun + ". " + verb + ":" +
                            s.split(":")[2] + ":" + (s.split(":").length > 2 ? s.split(":")[3] : ""));
                } else {
                    rs.add(o + " noun,verb:" + meaning + ":" +
                            s.split(":")[2] + ":" + (s.split(":").length > 2 ? s.split(":")[3] : ""));
                }
            } else {
                rs.add(s);
            }
        }
        for (String s : rs) System.out.println(s);
    }
}
