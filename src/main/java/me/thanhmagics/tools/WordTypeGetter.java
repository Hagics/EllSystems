package me.thanhmagics.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedHashSet;
import java.util.Set;

public class WordTypeGetter {


    public static String getWordTypeVdict(String word) {
        StringBuilder rs = new StringBuilder();
        try {
            String url = "https://vdict.com/{word},7,0,0.html".replace("{word}",word.toLowerCase());
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();
            String html = doc.html();
            String[] types = html.split("<div class=\"word-type mb-2\">");
            for (int i = 0; i < types.length; i++) {
                String type = types[i].split("</div>")[0];
                if (type.length() > 15) continue;
                if (type.contains(":") || type.contains("<")) continue;
                if (!rs.isEmpty()) rs.append(",");
                rs.append(type);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs.toString();
    }

    public static String getWordTypeFormatted(String word) {
        Set<String> wordTypes = new LinkedHashSet<>();

        try {
            String url = "https://dictionary.cambridge.org/dictionary/english/" + word.toLowerCase();
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .timeout(10000)
                    .get();

            Elements posElements = doc.select(".pos.dpos");

            for (Element element : posElements) {
                String wordType = element.text().trim();
                if (!wordType.isEmpty() && !wordType.equals("                 ")) {
                    wordTypes.add(wordType);
                }
            }

        } catch (Exception e) {
            return "Error: Could not fetch word types for '" + word + "'";
        }

        return formatWordTypesWithStyle(word, wordTypes);
    }

    private static String formatWordTypesWithStyle(String word, Set<String> wordTypes) {
        if (wordTypes.isEmpty()) {
            return "No word types found for '" + word + "'";
        }
        return String.join("," , wordTypes);
    }


    public static void main(String[] args) {
        System.out.println(getWordTypeVdict("authorized"));
    }
}