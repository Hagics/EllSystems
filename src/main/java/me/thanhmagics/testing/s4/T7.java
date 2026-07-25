package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;
import me.thanhmagics.tools.SpeechGetter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class T7 {

    public static AtomicInteger index = new AtomicInteger(0);

    public static List<String> nulll = new ArrayList<>();

    public static boolean done = false;

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                LinkedList<String> data = DataHandling.getData("M2.txt", new DataHandling.StringHandler() {
                    @Override
                    public String handle(String string) {
                        return string.split(" ")[0];
                    }
                });
                int id = index.getAndIncrement();
                while (id < data.size()) {
                    String s = SpeechGetter.getSpeech(data.get(id));
                    if (s != null) nulll.add(s);
                    id = index.getAndIncrement();
                }
                done = true;
            }).start();
        }
        while (true) {
            if (done) {
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Done");
                for (String s : nulll) {
                    System.out.println(s);
                }
                break;
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
