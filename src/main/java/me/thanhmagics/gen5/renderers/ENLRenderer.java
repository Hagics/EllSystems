package me.thanhmagics.gen5.renderers;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;
import me.thanhmagics.gen5.Renderer;
import me.thanhmagics.gen5.StringColor;
import me.thanhmagics.gen5.ewords.F1Word;

import java.util.List;

public class ENLRenderer extends Renderer {
    @Override
    public List<String> acceptable() {
        return List.of("progressing/others/f1.txt","10kw-3.4.txt");
    }

    @Override
    public void onSpawning(EWord5 word) {
        if (word instanceof F1Word f1Word) {
            println(new StringColor(231, f1Word.meaning) ,new StringColor(51," (" + f1Word.getWordType() + ")"));
        }
    }

    @Override
    public boolean onAnswer(EWord5 word, String answer) {
        if (word instanceof F1Word f1Word) {
            String meaning = stringConverter(f1Word.origin);
            boolean isCorrect = meaning.equalsIgnoreCase(answer);
            for (int i = 0; i < 15; i++) {
                System.out.println(" ");
            }
            if (isCorrect) {
                println(118,answer);
                println(new StringColor(118,f1Word.origin + " "),new StringColor(15,f1Word.meaning));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "),new StringColor(14,f1Word.getIpa() + " "));
            } else {
                println(1,answer);
                println(new StringColor(190,f1Word.origin + " "),new StringColor(15,f1Word.meaning));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "),new StringColor(14,f1Word.getIpa() + " "));
            }
            for (int i = 0; i < f1Word.getExamples().size(); i++) {
                if (i > 5) break;
                println(250, f1Word.getExamples().get(i));
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(" ");
            }
            return isCorrect;
        }
        println(242,"Cannot Verify Word...");
        return false;
    }

    @Override
    public String id() {
        return "enl";
    }

    @Override
    public PracticeType type() {
        return PracticeType.ENL;
    }


}
