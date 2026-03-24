package me.thanhmagics.testing.s1;

import me.thanhmagics.gen4.PracticeSeason;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DataProgressing2 {
    public static void main(String[] args) {
        File file = new File(PracticeSeason.PATH + "progressing/10kw-series/s2/10kw-2.txt");
        LinkedList<String> str = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() < 2) continue;
                str.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
//        Collections.shuffle(str);
        List<String> exits = new ArrayList<>();
//        for (String s : str) {
//            System.out.println(s);
//        }
        Collections.sort(str);
        for (String s : str) {
            if (!exits.contains(s)) {
                System.out.println(s);
                exits.add(s);
            }
        }
        try {
            FileWriter writer = new FileWriter(file);
            for (String s : exits) {
                writer.write(s);
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
