package me.thanhmagics.gen4;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SeasonProperty implements Serializable {

    public List<SeasonWord> words;

    public List<SeasonWord> inRoll;

    public Map<SeasonWord,Integer> corrected;

    public long start;

    public int index,learned,rolled,type;

    public SeasonProperty(List<SeasonWord> words, int index, Map<SeasonWord, Integer> corrected, List<SeasonWord> inRoll,long start,int learned) {
        this.words = words;
        this.index = index;
        this.corrected = corrected;
        this.inRoll = inRoll;
        this.start = start;
        this.learned = learned;
    }
}
