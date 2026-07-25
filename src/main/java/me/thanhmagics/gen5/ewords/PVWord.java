package me.thanhmagics.gen5.ewords;

import me.thanhmagics.gen5.EWord5;

import java.util.List;

public class PVWord extends EWord5 {

    public PVWord(String origin, String meaning) {
        super(origin, meaning);
    }

    @Override
    public void reload(List<? extends EWord5> words) {
        for (EWord5 w : words) {
            if (w.origin.equalsIgnoreCase(origin)) {
                this.meaning = w.meaning;
                break;
            }
        }
    }
}
