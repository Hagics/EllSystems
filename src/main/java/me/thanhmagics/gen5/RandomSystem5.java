package me.thanhmagics.gen5;

import me.thanhmagics.gen5.properties.RandomSystemProperty;

import java.util.*;

public class RandomSystem5 {

    private RandomSystemProperty property = new RandomSystemProperty();

    private final BalancedRandom random = new BalancedRandom(property.antiDuplication);

    public RandomSystem5(int listSize) {
        property.listSize = listSize;
    }

    public RandomSystem5(RandomSystemProperty randomSystemProperty) {
        this.property = randomSystemProperty;
    }


    public void update() {
        int oldIndex = property.index;
        if (property.rolled == 0 || property.rolled % property.updateAt == 0) {
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
            Renderer.println(242, "------------------------------------------------");
            Renderer.println(242, "updated index from " + oldIndex + " to " + property.index + "!");
            Renderer.println(242, "------------------------------------------------");
        }
    }

//    private boolean verifyInteger(int i) {
//        for (Integer integer : property.history) {
//            if (integer.equals(i)) return false;
//        }
//        property.history.addLast(i);
//        if (property.history.size() > property.antiDuplication) {
//            property.history.removeFirst();
//        }
//        return true;
//    }

//    private LinkedList<Integer> tempHistory = new LinkedList<>();
//    private int lastIRS;
//    private boolean verifyInteger1(int i) {
//        for (Integer integer : tempHistory) {
//            if (integer.equals(i)) return false;
//        }
//        tempHistory.addLast(i);
//        return true;
//    }
//
    public int generate() {
        if (property.inRoll.isEmpty()) update();
        property.rolled++;
        while (true) {
            if (property.inRoll.isEmpty()) {
                System.out.println("Season Completed!");
                System.exit(0);
            }
//            if (property.inRoll.size() <= property.antiDuplication) {
//                ArrayList<Integer> list = new ArrayList<>(property.inRoll);
//                Collections.shuffle(list);
//                return list.getFirst();
//            }
            int selected = random.generate(property.inRoll);
//            if (verifyInteger(selected) ) {
//                return selected;
//            } else if (property.inRoll.size() <= property.antiDuplication) {
//                return selected;
//            }
            return selected;
        }
    }

    public void report(int i,boolean isCorrect, boolean update) {
        if (i < 0) return;
        if (isCorrect) {
            int correct = property.corrected.get(i);
            if (correct == 0) {
                correct += property.correctAtFirst;
            }
            property.corrected.replace(i,correct + 1);
            property.incorrect.replace(i,0);
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
