package me.thanhmagics.testing.s1;

import me.thanhmagics.gen4.PracticeSeason;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DataProgressing {

    public static void main(String[] args) {
        File file = new File(PracticeSeason.PATH + "progressing/others/cdt.txt");
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
        StringBuilder sb = new StringBuilder();
        int limit = 900, i = 0, min = 602;
        for (String s : str) {
            if (i >= min && i <= limit) {
                sb.append("{\"").append(s.split(":")[0]).append("\",\"").append(s.split(":")[1]).append("\"}");
            }
            i++;
        }
        System.out.println(sb);
//        for (String s : str) {
//            String string = s.split(":")[1];
//            if (string.contains(", ")) {
//                String[] strings = string.split(", ");
//                for (String s1 : strings) {
//                    if (!exits.contains(s1)) {
//                        exits.add(s1);
//                    } else {
//                        System.out.println(s1);
//                    }
//                }
//            } else {
//                if (!exits.contains(string)) {
//                    exits.add(string);
//                } else {
//                    System.out.println(string);
//                }
//            }
//        }
    }
}
