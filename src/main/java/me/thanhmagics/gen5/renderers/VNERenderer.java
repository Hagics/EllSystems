package me.thanhmagics.gen5.renderers;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;
import me.thanhmagics.gen5.Renderer;
import me.thanhmagics.gen5.StringColor;
import me.thanhmagics.gen5.ewords.F1Word;
import me.thanhmagics.gen5.ewords.PVWord;
import me.thanhmagics.tools.Mp3Player;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VNERenderer extends Renderer {

    private static final Logger LOGGER = Logger.getLogger(VNERenderer.class.getName());
    private static final int MAX_EXAMPLES = 5;
    private static final int BLANK_LINES = 15;

    @Override
    public List<String> acceptable() {
        return List.of("progressing/others/f1.txt","10kw-3.4.txt","M1.txt");
    }

    @Override
    public void onSpawning(EWord5 word) {
        println(231, word.origin);
    }

    @Override
    public boolean onAnswer(EWord5 word, String answer) {
//        Mp3Player.playAsync(word.origin.toLowerCase());
        if (word instanceof F1Word f1Word) {
            boolean isCorrect = isMeaningEqual(word.meaning, answer);
            printBlankLines(BLANK_LINES);
            
            if (isCorrect) {
                println(118, answer);
                println(new StringColor(118, f1Word.meaning + " "), new StringColor(207, f1Word.origin));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "), new StringColor(14, f1Word.getIpa() + " "));
            } else {
                println(1, answer);
                println(new StringColor(190, f1Word.meaning + " "), new StringColor(207, f1Word.origin));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "), new StringColor(14, f1Word.getIpa() + " "));
            }
            
            printExamples(f1Word);
            printBlankLines(5);
            return isCorrect;
        } else if (word instanceof PVWord pvWord) {
            boolean isCorrect = isMeaningEqual(word.meaning, answer);
            printBlankLines(BLANK_LINES);

            if (isCorrect) {
                println(118, answer);
                println(new StringColor(118, pvWord.meaning + " "), new StringColor(207, pvWord.origin));
            } else {
                println(1, answer);
                println(new StringColor(190, pvWord.meaning + " "), new StringColor(207, pvWord.origin));
            }

            printBlankLines(5);
            return isCorrect;
        }
        println(242, "Cannot Verify Word...");
        return false;
    }

    private void printExamples(F1Word f1Word) {
        int exampleCount = 0;
        for (String example : f1Word.getExamples()) {
            if (exampleCount >= MAX_EXAMPLES) break;
            println(250, example);
            exampleCount++;
        }
    }

    private void printBlankLines(int count) {
        for (int i = 0; i < count; i++) {
            System.out.println(" ");
        }
    }

    @Override
    public String id() {
        return "vne";
    }

    @Override
    public PracticeType type() {
        return PracticeType.VNE;
    }

}
