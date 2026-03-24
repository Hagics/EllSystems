package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;

public class DP10 {
    public static void main(String[] args) {
        File file = new File(EllSystem5.PATH + "progressing/others/f1.txt");
        try {
            Scanner scanner;
            LinkedList<String> rs = new LinkedList<>();
            LinkedList<String> f1 = new LinkedList<>();
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line.split(" ")[0]);
                f1.add(line.split(" ")[0]);
            }
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!f1.contains(line.split(" ")[0])) {
                    rs.add(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
