package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.tools.WordTypeGetter;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class DP4 {
    public static void main(String[] args) {
        try {
            File input = new File(EllSystem5.PATH + "progressing\\10kw-dp8.txt");
            File output = new File(EllSystem5.PATH + "progressing\\10kw-dp9.txt");
            Scanner scanner = new Scanner(input);
            LinkedList<String> rs = new LinkedList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("No word types found for")) {
                    String first = line.split(":")[0].split(" ")[0];
                    StringBuilder sb = new StringBuilder();
                    sb.append(first).append(" ").append(WordTypeGetter.getWordTypeVdict(first));
                    for (int i = 1; i < line.split(":").length; i++) {
                        sb.append(":");
                        sb.append(line.split(":")[i]);
                    }
                    System.out.println(sb);
                    rs.add(sb.toString());
                } else {
                    rs.add(line);
                }
            }
            FileWriter writer = new FileWriter(output);
            for (String s : rs) {
                writer.write(s);
                writer.write("\n");
            }
            writer.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
