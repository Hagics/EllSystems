package me.thanhmagics.testing.s2;

import me.thanhmagics.testing.DataHandling;

import java.util.LinkedList;
import java.util.List;

public class DP16 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing/10kw-series/s2/10kw-3.2.txt");
        LinkedList<String> rs = new LinkedList<>();
        List<String> exits = new LinkedList<>();
        for (int i = 0; i < 1540; i++) {
            String str = data.get(i);
            String a = str.split(":")[0].toLowerCase().replace(",suffix","").replace("number","noun");
            if (a.contains(".")) {
                a = a.replace("n.", "noun")
                        .replace("v.", "verb").replace("adj.", "adjective").replace("adv.", "adverb");
            } else if (!a.contains("adjective")) {
                a = a.replace(",adj",",adjective").replace(",adv",",adverb").replace(",n",",noun")
                        .replace(" adj"," adjective").replace(" adv"," adverb").replace(" n"," noun")
                        .replace("nounoun","noun").replace("adverberb","adverb");
            }
            String b = a.split(" ")[1];
            for (String s : b.split(",")) {
                if (!exits.contains(s)) {
                    exits.add(s);
                    System.out.println(s + " - " + str);
                }
            }
        }

    }
}
