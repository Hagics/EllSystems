package me.thanhmagics.testing.s1;

import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.gen5.Renderer;
import me.thanhmagics.gen5.StringColor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class DP11 {
    public static void main(String[] args) {
        File input = new File(EllSystem5.PATH + "progressing/10kw-series/s2/10kw-1.txt");
        File output = new File(EllSystem5.PATH + "10kws.txt");
        try {
            Scanner scanner = new Scanner(input);
            LinkedList<String> lines = new LinkedList<>();
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            LinkedList<String> sorted = new LinkedList<>();
            Scanner inScanner = new Scanner(System.in);
            String last = null;
            for (String s : lines) {
                String word = s.split(" ")[0];
                String meaning = s.split(":")[1];
                Renderer.println(new StringColor(201,word + " "), new StringColor(119,meaning));
                String ip = inScanner.nextLine();
                if (ip.isEmpty()) {
                    sorted.add(s);
                    Renderer.println(242,"Added: " + word);
                    System.out.println(" ");
                }
                if (ip.equals("-1") && last != null) {
                    sorted.add(last);
                    Renderer.println(242,"Added: " + last);
                    System.out.println(" ");
                    ip = inScanner.nextLine();
                    if (ip.isEmpty()) {
                        sorted.add(s);
                        Renderer.println(242,"Added: " + word);
                        System.out.println(" ");
                    }
                }
                if (ip.equals("1")) {
                    sorted.remove(last);
                    Renderer.println(242,"Removed: " + last);
                    System.out.println(" ");
                    ip = inScanner.nextLine();
                    if (ip.isEmpty()) {
                        sorted.add(s);
                        Renderer.println(242,"Added: " + word);
                        System.out.println(" ");
                    }
                }
                last = s;
            }
            FileWriter fileWriter = new FileWriter(output);
            for (String rs : sorted) {
                fileWriter.write(rs);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
