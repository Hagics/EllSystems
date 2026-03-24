package me.thanhmagics.gen4;

import java.io.Serializable;
import java.util.List;

public class SeasonWord implements Serializable {

    private int index;

    private List<String> clause = null;

    private String original,pronoun,meaning,type;

    public SeasonWord(String original, String pronoun, String meaning, String type,int index,List<String> clause) {
        this.original = original;
        this.pronoun = pronoun;
        this.meaning = meaning;
        this.index = index;
        if (type != null) {
            this.type = type.replace(".", "").replace(",", "");
        }
        this.clause = clause;
    }

    public List<String> getClause() {
        return clause;
    }

    public void setClause(List<String> clause) {
        this.clause = clause;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getPronoun(boolean reformat) {
        if (reformat) {
            return pronoun.replace("ː", ":")
//                    .replace("ə", "ow")
                 //   .replace("æ", "ae")
                //    .replace("ʌ", "o7")
                    .replace("ʊ", "u")
                    .replace("ɑ", "a")
                    .replace("ɜ", "3")
                 //   .replace("ɔ", "oo")
                    .replace("ɪ", "i")
                    .replace("ɒ", "o")
                    .replace("dʒ", "d3")
                    .replace("ɡ", "g")
                 //   .replace("ʃ", "sh")
                 //   .replace("ð", "th")
                    .replace("ʒ", "3")
                  //  .replace("ŋ", "ng")
                    .replace("θ","0")
                    .replace("ˌ", "")
                    .replace("/", "")
                    .replace("(r)", "/r")
                    .replace("ˈ", "'");
        }
        return pronoun;
    }

    public void setPronoun(String pronoun) {
        this.pronoun = pronoun;
    }

    @Override
    public String toString() {
        return original + " " + type + ":" + meaning + ":" + pronoun + ":";

    }
}
