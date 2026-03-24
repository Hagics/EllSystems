package me.thanhmagics.gen5.loaders;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.WorldLoader;
import me.thanhmagics.gen5.ewords.F1Word;

import java.util.*;

public class F1Loader extends WorldLoader {
    @Override
    public EWord5 initWord(String line) {
        try {
            F1Word f1Word = new F1Word(line.split(":")[0].split(" ")[0], line.split(":")[1]);
            f1Word.setWordType(line.split(":")[0].split(" ")[1].replace(".", ""));
            f1Word.setIpa(line.split(":")[2].replace("/", "").replace("ˌ", ""));
            try {
                if (line.split(":").length >= 2 && !line.split(":")[2].isEmpty()) {
                    f1Word.setExamples(new ArrayList<>(Arrays.asList(line.split(":")[3].split(" <AND> "))));
                }
            } catch (Exception ignored) {
            }
            return f1Word;
        } catch (Exception e) {
            System.out.println("ERR: " + line);
        }
        return null;
    }

    @Override
    public String toString(EWord5 eWord5) {
        if (eWord5 instanceof F1Word f1Word) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(f1Word.origin);
            stringBuilder.append(" ");
            stringBuilder.append(f1Word.getWordType());
            stringBuilder.append(":");
            stringBuilder.append(f1Word.meaning);
            stringBuilder.append(":");
            stringBuilder.append(f1Word.getIpa());
            stringBuilder.append(":");
            for (int i = 0; i < f1Word.getExamples().size(); i++) {
                String ex = f1Word.getExamples().get(i);
                stringBuilder.append(ex);
                if ((i + 1) < f1Word.getExamples().size()) {
                    stringBuilder.append(" <AND> ");
                }
            }
            return stringBuilder.toString();
        }
        return null;
    }

    @Override
    public List<String> acceptable() {
        return List.of("progressing/others/f1.txt", "progressing/10kw-series/s2/10kw.txt","10kw-3.4.txt","10kwl-4.7.txt");
    }

    @Override
    public String id() {
        return "f1";
    }
}
