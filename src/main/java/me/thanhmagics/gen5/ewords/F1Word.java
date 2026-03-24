package me.thanhmagics.gen5.ewords;

import me.thanhmagics.gen5.EWord5;

import java.util.ArrayList;
import java.util.List;

public class F1Word extends EWord5 {

    @Override
    public void reload(List<? extends EWord5> words) {
        List<F1Word> ws = (List<F1Word>) words;
        for (F1Word w : ws) {
            if (w.origin.equalsIgnoreCase(origin)) {
                this.meaning = w.meaning;
                this.ipa = w.ipa;
                this.examples = w.examples;
                this.wordType = w.wordType;
                break;
            }
        }
    }

    public F1Word(String origin, String meaning) {
        super(origin, meaning);
    }

    private String ipa = null;
    private List<String> examples = new ArrayList<>();
    private String wordType = null;

    public String getIpa() {
        return ipa;
    }

    public void setIpa(String ipa) {
        this.ipa = ipa;
    }

    public List<String> getExamples() {
        return examples;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }
}
