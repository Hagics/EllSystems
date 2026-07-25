package me.thanhmagics.gen5.loaders;


import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.WordLoader;
import me.thanhmagics.gen5.ewords.PVWord;

import java.util.List;

public class PVLoader extends WordLoader {

    @Override
    public EWord5 initWord(String line) {
        String origin = line.split(":")[0].toLowerCase();
        String meaning = line.split(":")[1].toLowerCase();
        if (meaning.startsWith(" ")) {
            meaning = meaning.substring(1);
        }
        return new PVWord(origin, meaning);
    }

    @Override
    public String toString(EWord5 eWord5) {
        return eWord5.origin + ": " + eWord5.meaning;
    }

    @Override
    public List<String> acceptable() {
        return List.of("pv-0.2.txt","pv-0.2L.txt");
    }

    @Override
    public String id() {
        return "pv";
    }
}
