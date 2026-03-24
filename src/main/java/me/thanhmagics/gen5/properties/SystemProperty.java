package me.thanhmagics.gen5.properties;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class SystemProperty implements Serializable {

    public List<EWord5> words;
    public RandomSystemProperty property;

    public File file;
    public int min,max;
    public PracticeType type;
    public String fileName, loader;

    public SystemProperty(List<EWord5> words, PracticeType type, int max, int min, File file, RandomSystemProperty property, String fileName, String loader) {
        this.words = words;
        this.type = type;
        this.max = max;
        this.min = min;
        this.file = file;
        this.property = property;
        this.fileName = fileName;
        this.loader = loader;
    }
}
