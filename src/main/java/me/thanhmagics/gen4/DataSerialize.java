package me.thanhmagics.gen4;

import java.io.*;
import java.util.Scanner;

public class DataSerialize {

    public static Object decode(String id) {
        File file = new File(PracticeSeason.PATH + "serialized\\" + id + ".dat");
        if (!file.exists()) {
            System.out.println("File Not Found!");
            return null;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                return objectInputStream.readObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void encode(Object object,String id) {
        try {
            long start = System.currentTimeMillis();
            System.out.println("progressing...");
            FileOutputStream fileOutputStream = new FileOutputStream(PracticeSeason.PATH + "serialized\\" + id + ".dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(object);
            System.out.println("successful in " + (System.currentTimeMillis() - start) + "ms");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
