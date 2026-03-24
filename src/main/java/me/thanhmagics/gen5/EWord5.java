package me.thanhmagics.gen5;

import java.io.Serializable;
import java.util.List;

public abstract class EWord5 implements Serializable {

    public String origin, meaning;

    public abstract void reload(List<? extends EWord5> words);

    public EWord5(String origin, String meaning) {
        this.origin = origin;
        this.meaning = meaning;
    }

}
