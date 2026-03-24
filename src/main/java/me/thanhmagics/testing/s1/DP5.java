package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;

public class DP5 {
    public static void main(String[] args) {
        File f1 = new File(EllSystem5.PATH + "progressing\\10kw-original.txt");
        File f2 = new File(EllSystem5.PATH + "progressing\\10kw-rw.txt");
        File f3 = new File(EllSystem5.PATH + "progressing\\10kw-dp5.txt");
        LinkedList<String> strings = new LinkedList<>(),s2 = new LinkedList<>(),rs = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(f1);
            while (scanner.hasNextLine()) {
                strings.add(scanner.nextLine());
            }
            scanner = new Scanner(f2);
            while (scanner.hasNextLine()) {
                s2.add(scanner.nextLine());
            }
            scanner = new Scanner(System.in);
            for (String s : strings) {
                boolean found = false;
                for (String str : s2) {
                    if (str.split(" ")[0].equalsIgnoreCase(s)) {
                        rs.add(str);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    rs.add(s);
                }
            }
            FileWriter fileWriter = new FileWriter(f3);
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
