package me.thanhmagics.gen5.renderers;

import me.thanhmagics.gen5.EWord5;
import me.thanhmagics.gen5.PracticeType;
import me.thanhmagics.gen5.Renderer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixingRenderer extends Renderer {

    public static final int mixingRate = 1; //for ENL

    private Map<EWord5, Integer> VNEspawned = new HashMap<>();

    @Override
    public List<String> acceptable() {
        return List.of();
    }

    @Override
    public void onSpawning(EWord5 word) {
        if (VNEspawned.containsKey(word)) {
            VNEspawned.put(word, VNEspawned.get(word) + 1);
        } else {
            VNEspawned.put(word, 1);
        }
    }

    @Override
    public boolean onAnswer(EWord5 word, String answer) {
        return false;
    }

    @Override
    public String id() {
        return "mixing";
    }

    @Override
    public PracticeType type() {
        return PracticeType.MIXING;
    }
}
