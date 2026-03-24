package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.tools.WordTypeGetter;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class DP6 {
    public static void main(String[] args) {
        File file = new File(EllSystem5.PATH + "progressing\\10kw-dp7.txt");
        File file1 = new File(EllSystem5.PATH + "progressing\\10kw-dp8.txt");
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            LinkedList<String> lines = new LinkedList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            LinkedList<String> rs = new LinkedList<>();

            for (String l : lines) {
                String w = l.split(":")[0];
                String word = w;
                if (w.contains(" ")) {
                    word = w.split(" ")[0];
                }
                StringBuilder sb = new StringBuilder();
                sb.append(word).append(" ").append(WordTypeGetter.getWordTypeFormatted(word));
                sb.append(":");
                boolean b = false;
                for (int i = 1; i < l.split(":").length; i++) {
                    if (b) {
                        sb.append(":");
                    }
                    b = true;
                    sb.append(l.split(":")[i]);
                }
                System.out.println(sb);
                rs.add(sb.toString());
            }

            FileWriter fileWriter = new FileWriter(file1);
            for (String s : rs) {
                fileWriter.write(s);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
