package me.thanhmagics.testing.s2;

import me.thanhmagics.testing.DataHandling;

import java.util.*;

public class DP15 {
    public static void main(String[] args) {
        LinkedList<String> data = DataHandling.getData("progressing/10kw-series/s2/10kw-3.2.txt");
        LinkedList<String> rs = new LinkedList<>();
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
            String b = str.split(":")[1];
            String c = str.split(":")[2];
            if (str.split(":").length > 3)
                c += ":" + str.split(":")[3];
            if (a.contains(",")) {
                System.out.println(str);
                int j = a.split(",").length;
                int k = b.split("\\.").length;
                if (j == k) {
                    String[] result = new String[11];
                    result[0] = initWT(a,b,"noun");
                    result[1] = initWT(a,b,"verb");
                    result[2] = initWT(a,b,"adjective");
                    result[3] = initWT(a,b,"adverb");
                    result[4] = initWT(a,b,"modal");
                    result[5] = initWT(a,b,"preposition");
                    result[6] = initWT(a,b,"conjunction");
                    result[7] = initWT(a,b,"determiner");
                    result[8] = initWT(a,b,"pronoun");
                    result[9] = initWT(a,b,"article");
                    result[10] = initWT(a,b,"exclamation");
                    StringBuilder sb = new StringBuilder();
                    for (String s : result) {
                        if (s == null) continue;
                        sb.append(s).append(". ");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.deleteCharAt(sb.length() - 2);
                    rs.add(a.split(" ")[0] + " " + sort(a) + ":" + sb + ":" + c);
                }
            } else {
                rs.add(str);
            }
        }
        for (int i = 1540; i < data.size(); i++) {
            rs.add(data.get(i));
        }
        DataHandling.saveData("progressing/10kw-series/s2/10kw-3.3.txt",rs);
    }

    public static String sort(String a) {
        StringBuilder builder = new StringBuilder();
        String b = a.split(" ")[1];
        if (b.contains("noun")) {
            builder.append("noun");
        }
        if (b.contains("verb")) {
            builder.append(",").append("verb");
        }
        if (b.contains("adjective")) {
            builder.append(",").append("adjective");
        }
        if (b.contains("adverb")) {
            builder.append(",").append("adverb");
        }
        if (b.contains("modal")) {
            builder.append(",").append("modal");
        }
        if (b.contains("preposition")) {
            builder.append(",").append("preposition");
        }
        if (b.contains("conjunction")) {
            builder.append(",").append("conjunction");
        }
        if (b.contains("determiner")) {
            builder.append(",").append("determiner");
        }
        if (b.contains("pronoun")) {
            builder.append(",").append("pronoun");
        }
        if (b.contains("article")) {
            builder.append(",").append("article");
        }
        if (b.contains("exclamation")) {
            builder.append(",").append("exclamation");
        }
        return builder.toString();

    }

    private static String initWT(String a,String b,String t) {
        int i = 0;
        for (String s : a.split(" ")[1].split(",")) {
            if (s.equalsIgnoreCase(t)) {
                String rs = b.split("\\. ")[i];
                return rs;
            }
            i++;
        }
        return null;
    }
}
