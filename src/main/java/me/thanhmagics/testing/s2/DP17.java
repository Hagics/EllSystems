package me.thanhmagics.testing.s2;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;
import java.util.Scanner;

public class DP17 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing/10kw-series/s2/10kw-3.3.txt");
        LinkedList<String> rs = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);
        int i = 0;
        for (String s : data) {
            String a = s.split(":")[0].toLowerCase();
            String c = s.split(":")[2];
            if (s.split(":").length > 3)
                c += ":" + s.split(":")[3];
            if (a.split(" ")[1].startsWith(",")) {
                a = a.split(" ")[0] + " " + a.split(" ")[1].substring(1);
            }
            if (a.contains(".")) {
                a = a.replace("v.","verb").replace("n.","noun").replace("adj.","adjective").replace("adv.","adverb");
            }
            String b = s.split(":")[1].replace("làm ","");
            StringBuilder sb = new StringBuilder(b);
            if (b.endsWith(".")) {
                System.out.println(a + ": "+ b);
                String in = scanner.nextLine();
                sb.deleteCharAt(sb.length() - 1);
                sb.append(in);
                System.out.println(sb.toString());
                System.out.println(" --- ");
                i++;//adorn, alright, being, blur, boat, cake
            }
            rs.add(a + ":" + sb + ":" + c);
        }
        System.out.println(i);
        DataHandling.saveData("10kw-3.4.txt",rs);
    }
}
