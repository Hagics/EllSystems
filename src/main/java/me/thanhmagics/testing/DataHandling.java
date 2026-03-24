package me.thanhmagics.testing;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Scanner;

public class DataHandling {



    public static void saveData(String path, Collection<String> rs) {
        File file = new File(EllSystem5.PATH + path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            for (String s : rs) {
                fileWriter.write(s);
                fileWriter.write("\n");
            }
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LinkedList<String> getData(String path) {
        LinkedList<String> rs = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(new File(EllSystem5.PATH + path));
            while (scanner.hasNextLine()) {
                rs.add(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


}
