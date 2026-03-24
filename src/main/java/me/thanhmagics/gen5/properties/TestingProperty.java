package me.thanhmagics.gen5.properties;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class TestingProperty implements Serializable {

    public List<EWord5> words;
    public List<EWord5> fell;
    public int index;
    public EWord5 last;
    public String fileName,loader;
    public PracticeType type;

    public File file;
    public int min,max;

    public TestingProperty(List<EWord5> words, EWord5 last, int index, List<EWord5> fell, String fileName, PracticeType type, File file, int min, int max, String loader) {
        this.words = words;
        this.last = last;
        this.index = index;
        this.fell = fell;
        this.fileName = fileName;
        this.type = type;
        this.file = file;
        this.min = min;
        this.max = max;
        this.loader = loader;
    }
}
