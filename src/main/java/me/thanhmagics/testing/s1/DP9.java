package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.tools.PronounGetter;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class DP9 {
    public static void main(String[] args) {
        File file = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw.txt");
        File output = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw.txt");
        try {
            Scanner scanner = new Scanner(file);
            LinkedList<String> rs = new LinkedList<>();
            boolean stop = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                    if (line.contains("dame noun:phu nhân")) {
                        stop = true;
                    }
                    if (stop) continue;
                StringBuilder sb = new StringBuilder();
                sb.append(line.split(":")[0]).append(":").append(line.split(":")[1]).append(":");
                sb.append(PronounGetter.get(line.split(":")[0].split(" ")[0]));
                if (line.split(":").length > 2 && !line.split(":")[2].isEmpty()) {
                    sb.append(":");
                    sb.append(line.split(":")[2]);
                }
                System.out.println(sb);
                rs.add(sb.toString());
            }
//            FileWriter writer = new FileWriter(output);
//            for (String s : rs) {
//                writer.write(s);
//                writer.write("\n");
//            }
//            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
