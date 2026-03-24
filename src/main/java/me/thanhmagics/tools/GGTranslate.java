package me.thanhmagics.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class GGTranslate {
    public static String translate(String text) throws Exception {
        String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx&sl="
                + "en" + "&tl=" + "vi" + "&dt=t&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String result = response.toString();
        int startIndex = result.indexOf("\"") + 1;
        int endIndex = result.indexOf("\"", startIndex);
        return result.substring(startIndex, endIndex);
    }
}
