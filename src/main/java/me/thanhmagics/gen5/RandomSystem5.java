package me.thanhmagics.gen5;

import me.thanhmagics.gen5.properties.RandomSystemProperty;
import me.thanhmagics.gen5.properties.SystemProperty;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RandomSystem5 {

    private SystemProperty systemProperty;
    private RandomSystemProperty property;

    private BalancedRandom random;

    public RandomSystem5(SystemProperty systemProperty) {
        if (systemProperty == null) {
            throw new NullPointerException("SystemProperty cannot be null!");
        }
        this.systemProperty = systemProperty;
        this.property = systemProperty.property;
        random = new BalancedRandom(property.antiDuplication);
        this.oldIndex = property.index;
        property.listSize = systemProperty.words.size();
        oldInroll = property.rollSize;
    }

    private int oldIndex;
    private int oldInroll;

    public void update() {
        List<Integer> willRemove = new ArrayList<>();
        for (Integer word : property.inRoll) {
            if (property.corrected.containsKey(word) && property.corrected.get(word) >= property.skipAt) {
                willRemove.add(word);
            }
        }
        property.inRoll.removeAll(willRemove);
        int r = (property.rollSize - property.inRoll.size()) + property.index;
        while (property.index != r && property.index < property.listSize) {
            property.inRoll.add(property.index);
            property.corrected.put(property.index, 0);
            property.incorrect.put(property.index, 0);
            property.index++;
        }
        if (property.rolled == 0 || property.rolled % property.updateAt == 0) {
            Renderer.println(242, "------------------------------------------------");
            if (property.index == systemProperty.words.size()) {
                Renderer.println(242, "updated index from " + oldIndex + " to " + property.index + "!" +
                        "(-" + (oldInroll - property.inRoll.size()) + "/" + property.inRoll.size() + ")");
                oldInroll = property.inRoll.size();
            } else {
                Renderer.println(242, "updated index from " + oldIndex + " to " + property.index + "!");
            }
            Renderer.println(242, "------------------------------------------------");
            oldIndex = property.index;
        }
    }

    public int generate() {
        if (property.inRoll.isEmpty()) update();
        property.rolled++;
        while (true) {
            if (property.inRoll.isEmpty()) {
                Renderer.println(118, "Season Completed!");
                System.exit(0);
            }
            int selected = random.generate(property.inRoll);
            return selected;
        }
    }

    public void report(int i, boolean isCorrect, boolean update) {
        if (i < 0) return;
        FocusEnhancer.refresh();
        if (isCorrect) {
            int correct = property.corrected.get(i);
            if (correct == 0 && property.incorrect.get(i) == 0) {
                correct += property.correctAtFirst;
            }
            property.corrected.replace(i, correct + 1);
            property.incorrect.replace(i, 0);
        } else {
            int ic = property.incorrect.get(i);
            if (ic >= property.incorrectReset) {
                property.corrected.replace(i, 0);
                property.incorrect.replace(i, 0);
            } else {
                property.incorrect.replace(i, ic + 1);
            }
        }
        if (update)
            update();
    }

    public RandomSystemProperty getProperty() {
        return property;
    }

}
