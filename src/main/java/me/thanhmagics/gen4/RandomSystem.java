package me.thanhmagics.gen4;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class RandomSystem {

    public int index = 0,rollSize = 30;

    public int refresh = 10,rolled = 0;

    public int skipAt = 3;

    public Map<SeasonWord,Integer> corrected = new ConcurrentHashMap<>();

    public List<SeasonWord> inRoll = new ArrayList<>();

    public List<SeasonWord> history = new LinkedList<>();

    public Random random = new Random(System.currentTimeMillis());

    public PracticeSeason instance;

    public RandomSystem(PracticeSeason instance) {
        this.instance = instance;
    }

    public synchronized SeasonWord get() {
        update();
        while (true) {
            SeasonWord seasonWord = balancedGenerate();
            if (history.size() > 3 && inRoll.size() > 3) {
                if (history.get(history.size() - 1).equals(seasonWord)) continue;
                if (history.get(history.size() - 2).equals(seasonWord)) continue;
                if (history.get(history.size() - 3).equals(seasonWord)) continue;
                if (history.get(history.size() - 4).equals(seasonWord)) continue;
            }
            if (seasonWord == null) {
                System.out.println("Completed Practice Season!");
                System.out.println("Closing program...");
                System.exit(0);
            }
            history.add(seasonWord);
            if (history.size() > 1000) history.clear();
            System.gc();
            return seasonWord;
        }

    }

    private SeasonWord balancedGenerate() {
        List<SeasonWord> cloner = new ArrayList<>(inRoll);
        Collections.shuffle(cloner);
        return cloner.remove(0);
//        int i = random.nextInt(inRoll.size());
//        SeasonWord seasonWord = inRoll.get(i);
//        if (seasonWord == null) seasonWord = inRoll.get(inRoll.size() - 1);
//        return seasonWord;
    }

    public void update() {
        if (rolled % refresh == 0 || rolled == 0) {
            int old = index;
            List<SeasonWord> willRemove = new ArrayList<>();
            for (SeasonWord word : inRoll) {
                if (corrected.containsKey(word) && corrected.get(word) >= skipAt) {
                    willRemove.add(word);
                    instance.learned++;
                }
            }
            inRoll.removeAll(willRemove);
            int r = (rollSize - inRoll.size()) + index;
            if (r >= instance.words.size()) r = instance.words.size();
            while (index != r) {
                inRoll.add(instance.words.get(index));
                index++;
            }
            PracticeSeason.println(90, "------------------------------------------------");
            PracticeSeason.println(90, "updated index from " + old + " to " + index + "!");
            PracticeSeason.println(90, "------------------------------------------------");
        }
        rolled++;
    }

    public void report(SeasonWord word,boolean isCorrect) {
        if (!corrected.containsKey(word))
            corrected.put(word,0);
        int old = corrected.get(word);
        corrected.replace(word,old,isCorrect ? old + 1 : old);
    }

    public void printPerformance() {
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("------------------");
        System.out.println("rolled: " + rolled);
        System.out.println("learned: " + instance.learned);
        System.out.println("rate: " + ((double) (instance.learned / rolled)) + " (wpr)");
        System.out.println("------------------");
        System.out.println(" ");
    }

    public void updateProperty() {
        instance.property.index = index;
        instance.property.inRoll = inRoll;
        instance.property.corrected = corrected;
        instance.property.learned = instance.learned;
        instance.property.start = instance.start;
        instance.property.rolled = rolled;
        instance.property.type = instance.wordTypeSeason ? 1 : instance.pronounceSeason ? 2 : 0;
    }
}
