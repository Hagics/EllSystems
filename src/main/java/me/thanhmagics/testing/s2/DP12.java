package me.thanhmagics.testing.s2;

import me.thanhmagics.gen5.EllSystem5;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DP12 {
    private static final String BASE_URL = "https://vdict.com/{apb}%5E,1,0,0,{page}.html";

    public static String getHtml(String alphabet, int page) {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL.replace("{apb}",alphabet).replace("{page}",String.valueOf(page))))
                .GET()
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return response.body();
    }
    public static void main(String[] args) {
        List<String> alphabets = new ArrayList<>(List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q",
                    "r","s","t","u","v","w","x","y","z"));
        File output = new File(EllSystem5.PATH + "progressing/others/f2.txt");
        LinkedList<String> rs = new LinkedList<>();
        AtomicInteger in = new AtomicInteger(0);
        for (String w : alphabets) {
            new Thread(() -> {
                in.incrementAndGet();
                for (int i = 0; i < 1000; i++) {
                    String html = getHtml(w,i);
                    String[] words = html.split("word-item");
                    if (words.length < 10) break;
                    for (int j = 2; j < html.split("word-item").length; j++) {
                        String word = html.split("word-item")[j].split("</a>")[0].split("html\">")[1];
                        System.out.println(word);
                        rs.add(word);
                    }
                }
                in.decrementAndGet();
            }).start();
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while (in.get() != 0) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
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
