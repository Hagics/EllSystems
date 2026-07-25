package me.thanhmagics.testing.s3;

import me.thanhmagics.testing.DataHandling;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;

public class T5 { //anti duplication

    public static void main(String[] args) {
        List<String> words = DataHandling.getData("progressing\\Project26\\M1.txt");
        List<String> rs = new ArrayList<>();
        List<String> existed = new ArrayList<>();
        for (String w : words) {
            if (w.contains(":")) {
                String key = w.split(":")[0].toLowerCase();
                if (existed.contains(key)) continue;
                existed.add(key);
                rs.add(w);
            } else {
                if (w.contains(" ")) {
                    String key = w.split(" ")[0];
                    if (existed.contains(key)) continue;
                    existed.add(key);
                    rs.add(w);
                } else {
                    if (existed.contains(w)) continue;
                    existed.add(w);
                    rs.add(w);
                }
            }
        }
        for (String result : rs) {
            System.out.println(result);
        }
    }
}
