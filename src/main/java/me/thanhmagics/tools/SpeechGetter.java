package me.thanhmagics.tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpeechGetter {

    private static final String BASE_URL = "https://www.oxfordlearnersdictionaries.com";

    public static void main(String[] args) throws Exception {
        String word = "reputation";

        String html = fetchWordPage(word);
        while (html == null) {
            System.out.println("Không tìm thấy từ '" + word + "' trên Oxford Learner's Dictionaries. (1)");
            html = fetchWordPage(word + "_1");
        }

        String usUrl = extractAudioUrl(html, "pron-us");
        String ukUrl = extractAudioUrl(html, "pron-uk");

        String audioUrl = (usUrl != null) ? usUrl : ukUrl;

        if (audioUrl == null) {
            System.out.println("Từ '" + word + "' không có file phát âm trên trang.");
            return;
        }

        System.out.println("Audio URL: " + audioUrl);

        Path savePath = Path.of("src\\main\\resources\\speech\\" + word + ".mp3");
        downloadFile(audioUrl, savePath);

        System.out.println("Đã lưu file phát âm tại: " + savePath.toAbsolutePath());
    }

    public static String getSpeech(String word) {
        try {
            String html = fetchWordPage(word);
            int i = 1;
            while (html == null) {
                System.out.println("Không tìm thấy từ '" + word + "' trên Oxford Learner's Dictionaries. " + i);
                html = fetchWordPage(word + "_" + i);
                i++;
                if (i > 4) {
                    return word;
                }
            }

            String usUrl = extractAudioUrl(html, "pron-us");
            String ukUrl = extractAudioUrl(html, "pron-uk");

            String audioUrl = (usUrl != null) ? usUrl : ukUrl;

            if (audioUrl == null) {
                System.out.println("Từ '" + word + "' không có file phát âm trên trang.");
                return null;
            }

            System.out.println("Audio URL: " + audioUrl);

            Path savePath = Path.of("src\\main\\resources\\speech\\" + word + ".mp3");
            downloadFile(audioUrl, savePath);

            System.out.println("Đã lưu file phát âm tại: " + savePath.toAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String fetchWordPage(String word) throws IOException, InterruptedException {
        String url = BASE_URL + "/definition/english/" + word.trim().toLowerCase();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            return null;
        }

        return response.body();
    }

    private static String extractAudioUrl(String html, String accentClass) {
        Pattern pattern = Pattern.compile(
                "class=\"[^\"]*" + Pattern.quote(accentClass) + "[^\"]*\"\\s+data-src-mp3=\"([^\"]+)\""
        );
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            String path = matcher.group(1);
            return path.startsWith("http") ? path : BASE_URL + path;
        }
        return null;
    }

    private static void downloadFile(String fileUrl, Path savePath) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fileUrl))
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0 Safari/537.36")
                .GET()
                .build();

        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

        try (InputStream in = response.body()) {
            Files.copy(in, savePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
