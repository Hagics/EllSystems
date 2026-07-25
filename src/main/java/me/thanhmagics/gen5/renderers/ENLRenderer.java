package me.thanhmagics.gen5.renderers;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;
import me.thanhmagics.gen5.Renderer;
import me.thanhmagics.gen5.StringColor;
import me.thanhmagics.gen5.ewords.F1Word;
import me.thanhmagics.gen5.ewords.PVWord;
import me.thanhmagics.tools.Mp3Player;

import java.util.List;
import java.util.logging.Logger;

public class ENLRenderer extends Renderer {

    private static final Logger LOGGER = Logger.getLogger(ENLRenderer.class.getName());
    private static final int MAX_EXAMPLES = 5;
    private static final int BLANK_LINES = 15;

    @Override
    public List<String> acceptable() {
        return List.of("progressing/others/f1.txt","10kw-3.4.txt","M1.txt");
    }

    @Override
    public void onSpawning(EWord5 word) {
        if (word instanceof F1Word f1Word) {
            println(new StringColor(231, f1Word.meaning), new StringColor(51," (" + f1Word.getWordType() + ")"));
        } else if (word instanceof PVWord pvWord) {
            println(new StringColor(231, pvWord.meaning));
        } else {
            println(231, word.origin);
        }
    }

    @Override
    public boolean onAnswer(EWord5 word, String answer) {
        if (word instanceof F1Word f1Word) {
            String meaning = stringConverter(f1Word.origin).replace(" ","");
            boolean isCorrect = meaning.equalsIgnoreCase(answer.replace(" ",""));
            printBlankLines(BLANK_LINES);
            
            if (isCorrect) {
                println(118, answer);
                println(new StringColor(118, f1Word.origin + " "), new StringColor(15, f1Word.meaning));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "), new StringColor(14, f1Word.getIpa() + " "));
            } else {
                println(1, answer);
                println(new StringColor(190, f1Word.origin + " "), new StringColor(15, f1Word.meaning));
                println(new StringColor(99, "(" + f1Word.getWordType() + ") "), new StringColor(14, f1Word.getIpa() + " "));
            }
            
            printExamples(f1Word);
            printBlankLines(5);
            return isCorrect;
        } else if (word instanceof PVWord pvWord) {
            String meaning = stringConverter(pvWord.origin).replace(" ","");
            boolean isCorrect = meaning.equalsIgnoreCase(answer.replace(" ",""));
            printBlankLines(BLANK_LINES);

            if (isCorrect) {
                println(118, answer);
                println(new StringColor(118, pvWord.origin + " "), new StringColor(15, pvWord.meaning));
            } else {
                println(1, answer);
                println(new StringColor(190, pvWord.origin + " "), new StringColor(15, pvWord.meaning));
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
        return "enl";
    }

    @Override
    public PracticeType type() {
        return PracticeType.ENL;
    }

}
