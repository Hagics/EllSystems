package me.thanhmagics.gen5.loaders;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.EllSystem5;
import me.thanhmagics.gen5.WordLoader;
import me.thanhmagics.gen5.ewords.F1Word;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class F1Loader extends WordLoader {

    private static final Logger LOGGER = Logger.getLogger(F1Loader.class.getName());

    @Override
    public EWord5 initWord(String line) {

        try {
            String meaning = line.split(":")[1];
            if (meaning.startsWith(" ")) {
                meaning = meaning.substring(1);
            }
            F1Word f1Word = getF1Word(line, meaning);
            EllSystem5.database.addWord(f1Word.origin.toLowerCase());
            int level = EllSystem5.database.find(f1Word.origin.toLowerCase());
            if (level > EllSystem5.maxLevel) return null;
            return f1Word;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error parsing line: " + line, e);
        }
        return null;
    }

    @NotNull
    private static F1Word getF1Word(String line, String meaning) {
        String[] parts = line.split(":");
        String origin = parts[0].split(" ")[0];
        String wordType = parts[0].split(" ")[1].replace(".", "");
        
        F1Word f1Word = new F1Word(origin, meaning);
        f1Word.setWordType(wordType);
        
        if (parts.length > 2) {
            f1Word.setIpa(parts[2].replace("/", "").replace("ˌ", ""));
        }
        
        try {
            if (parts.length > 3 && !parts[3].isEmpty()) {
                f1Word.setExamples(new ArrayList<>(Arrays.asList(parts[3].split(" <AND> "))));
            }
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "Could not parse examples for: " + origin, e);
        }
        return f1Word;
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
        return List.of("progressing/others/f1.txt", "progressing/10kw-series/s2/10kw.txt","10kw-3.4.txt","10kwl-4.7.txt", "10kwl-52.7.txt", "M1.txt");
    }

    @Override
    public String id() {
        return "f1";
    }
}
