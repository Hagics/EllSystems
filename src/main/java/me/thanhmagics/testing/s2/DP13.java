package me.thanhmagics.testing.s2;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class DP13 {
    public static void main(String[] args) {
        File input = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw.txt");
        File output = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw-2.txt");
        File merge = new File(EllSystem5.PATH + "progressing/others/f1.txt");
        LinkedList<String> rs = new LinkedList<>();
        LinkedList<String> f1 = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(input);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                f1.add(line.split(" ")[0].toLowerCase());
                rs.add(line);
            }
            scanner = new Scanner(merge);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!f1.contains(line.split(" ")[0].toLowerCase())) {
                    rs.add(line);
                    f1.add(line.split(" ")[0].toLowerCase());
                }
            }
            FileWriter fileWriter = new FileWriter(output);
            for (String s : rs) {
                fileWriter.write(s);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
