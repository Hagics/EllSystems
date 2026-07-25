package me.thanhmagics.testing.s4;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class T8 {
    //charge noun,verb:phí. buộc tội
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing\\Project26\\M2-2.txt");
        LinkedList<String> result = new LinkedList<>();
        for (String d : data) {
            if (!d.contains(". ")) {
                result.add(d);
            } else {
                String[] parts = d.split("\\. ");
                Map<String,String> meaningByWordType = new LinkedHashMap<>();
                String original = (parts[0].split(" ")[0]);
                parts[0] = parts[0].substring(parts[0].indexOf(" ") + 1);
                for (String part : parts) {
                    meaningByWordType.put(part.split(":")[0], part.split(":")[1]);
                }
                StringBuilder wordType = new StringBuilder();
                StringBuilder meaning = new StringBuilder();
                int i = 0;
                for (String key : meaningByWordType.keySet()) {
                    if (i != 0) {
                        wordType.append(",");
                        meaning.append(". ");
                    }
                    i++;
                    wordType.append(key);
                    meaning.append(meaningByWordType.get(key));
                }
                result.add(original + " " + wordType + ":" + meaning);
            }
        }
        for (String r : result) {
            System.out.println(r);
        }
    }
}
