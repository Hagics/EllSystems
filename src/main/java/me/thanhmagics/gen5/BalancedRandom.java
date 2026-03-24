package me.thanhmagics.gen5;

import java.util.*;

public class BalancedRandom {

    private final Random random = new Random((long) (Math.random() * Math.tan(Math.random())));

    private double bias = 0.05;
    private int antiDuplication;
    private int index = 0;

    public BalancedRandom(int antiDuplication) {
        this.antiDuplication = antiDuplication;
    }

    private final LinkedList<Integer> history = new LinkedList<>();
    private final Map<Integer,Integer> lastAppear = new HashMap<>();

    public int generate(List<Integer> inRoll) {
        List<Integer> ir = handleList(inRoll);
        LinkedHashMap<Integer, double[]> probability = new LinkedHashMap<>();
        double cn = 0, cDefault = (double) 1 / ir.size();
        for (int j = 0; j < ir.size(); j++) {
            int i = ir.get(j);
            double c = cDefault + (bias * (index - lastAppear.getOrDefault(i,0)));
            probability.putLast(j,new double[] {(cn + c), i});
            cn += c;
        }
        index++;
        List<Integer> keySet = probability.keySet().stream().toList();
        double luckyNumber = Integer.MIN_VALUE;
        try {
            luckyNumber = random.nextDouble(cn);
            for (int i = 0; i < keySet.size(); i++) {
                double[] d1 = probability.get(keySet.get(i));
                if (d1[0] >= luckyNumber) {
                    updateHistory((int) d1[1]);
                    return (int) d1[1];
                }
            }
        } catch (Exception e) {
            System.out.println("[DEBUG]: " + history.size());
        }
        System.out.println("[DEBUG]: " + luckyNumber + ", " + cn + ", " + Arrays.toString(keySet.toArray()) + ", " + inRoll.size());
//        System.out.println("[DEBUG]: " + Arrays.toString(probability.values().toArray()));
        StringBuilder debug = new StringBuilder("[DEBUG]: ");
        for (Integer i : keySet) {
            double[] doubles = probability.get(i);
            debug.append("[").append(i).append(",").append(doubles[0]).append(",").append(doubles[1]).append("] ");
        }
        System.out.println(debug);
        return ir.getLast();
    }

    private List<Integer> handleList(List<Integer> inRoll) {
        List<Integer> ir = new ArrayList<>(inRoll);
        if (ir.size() <= antiDuplication) {
            antiDuplication = ir.size() - 1;
            while (history.size() >= antiDuplication && antiDuplication > 0) {
                history.removeFirst();
            }
        }
        for (Integer i : history)
            ir.remove(i);
        if (ir.isEmpty()) {
            history.removeFirst();
            return handleList(inRoll);
        }
        return ir;
    }


    private void updateHistory(int i) {
        if (antiDuplication == 0) {
            history.clear();
            return;
        }
        if (history.size() >= antiDuplication)
            history.removeFirst();
        history.addLast(i);
        lastAppear.remove(i);
        lastAppear.put(i,index);
    }
}
