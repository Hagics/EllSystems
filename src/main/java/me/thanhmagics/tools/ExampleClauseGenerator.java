package me.thanhmagics.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ExampleClauseGenerator {
    public static synchronized String get(String original) {
        StringBuilder sb = new StringBuilder();

        try {
            String BASE_URL = "https://dictionary.cambridge.org/dictionary/english/";
            Document doc = Jsoup.connect(BASE_URL + original.toLowerCase())
                    .userAgent("Mozilla/5.0")
                    .get();
            Elements exampleBlocks = doc.select("div.def-block .examp");
            int i = 0;
            boolean isFirst = true;
            for (Element example : exampleBlocks) {
                String exampleText = example.text().trim();
                if (!exampleText.isEmpty() && i <= 3 && exampleText.length() < 70) {
                    if (!isFirst) {
                        sb.append(" <AND> ");
                    }
                    sb.append(exampleText);
                    isFirst = false;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
