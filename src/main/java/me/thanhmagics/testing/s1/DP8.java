package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class DP8 {
    public static void main(String[] args) {
        File file = new File(EllSystem5.PATH + "progressing\\10kw-dp9.txt");
        File output = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw.txt");
        try {
            Scanner scanner = new Scanner(file);
            LinkedList<String> rs = new LinkedList<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    String a = line.split(":")[0].split(" ")[0];
                    String meaning = line.split(":")[1];
                    if (a.equalsIgnoreCase(meaning)) {
                        System.out.println(line);
                    } else {
                        rs.add(line);
                    }
                } catch (Exception e) {
                    System.out.println(line);
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
