package me.thanhmagics.gen5.properties;

import java.io.Serializable;
import java.util.*;

public class RandomSystemProperty implements Serializable {

    public int listSize = 0;
    public int rollSize = 20;
    public int skipAt = 3;
    public int antiDuplication = 5;
    public int correctAtFirst = 1;
    public int incorrectReset = 2;
    public int updateAt = 5;

    public int rolled = 0;
    public int index = 0;
    public final Map<Integer,Integer> corrected = new HashMap<>();
    public final Map<Integer,Integer> incorrect = new HashMap<>();
    public final List<Integer> inRoll = new ArrayList<>();

}
