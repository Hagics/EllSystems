package me.thanhmagics.gen5;

import me.thanhmagics.gen4.DataSerialize;
import me.thanhmagics.gen5.properties.SystemProperty;
import me.thanhmagics.gen5.properties.TestingProperty;
import me.thanhmagics.gen5.renderers.ENLRenderer;
import me.thanhmagics.gen5.renderers.VNERenderer;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

public abstract class Renderer {

    public abstract List<String> acceptable();

    public abstract void onSpawning(EWord5 word);

    public abstract boolean onAnswer(EWord5 word, String answer);

    public abstract String id();


    public abstract PracticeType type();

    public List<EWord5> words;

    public void runPractice(List<EWord5> words) {
        RandomSystem5 randomSystem5 = new RandomSystem5(words.size());
        EllSystem5.data = new SystemProperty(words, EllSystem5.currentType, EllSystem5.max, EllSystem5.min, EllSystem5.file, randomSystem5.getProperty(), EllSystem5.fileName, EllSystem5.loader);
        runPractice(words, randomSystem5);
    }

    public void runPractice(List<EWord5> words, RandomSystem5 randomSystem5) {
        this.words = words;
        Scanner scanner = new Scanner(System.in);
        int last = -1;
        while (true) {
            int selected = randomSystem5.generate();
            EWord5 selectedWord = this.words.get(selected);
            if (selectedWord == null) throw new RuntimeException("Selected Word Is Null!");
//            if (!(selectedWord instanceof EWord5 eWord5)) throw new RuntimeException();
            onSpawning(selectedWord);
            String answer = scanner.nextLine();
            if (answer.equals("+1")) {
                randomSystem5.report(selected, true, true);
            } else {
                while (EllSystem5.updateSetting(answer, randomSystem5, this.words, last)) {
                    answer = scanner.nextLine();
                }
                randomSystem5.report(selected, onAnswer(selectedWord, answer), true);
            }
            last = selected;
        }
    }

    public void runTesting(List<EWord5> words, int index, List<EWord5> fell, EWord5 last) {
        this.words = words;
        Scanner scanner = new Scanner(System.in);
        for (int j = index; j < words.size(); j++) {
            EWord5 word = words.get(j);
            onSpawning(word);
            String answer = scanner.nextLine();
            if (answer.equals("-1")) {
                if (last != null) fell.remove(last);
                answer = scanner.nextLine();
            }
            if (answer.startsWith("-sv")) {
                try {
                    String uid = answer.split(" ")[1];
                    TestingProperty property = new TestingProperty(words, last, j, fell, EllSystem5.fileName, type(),
                            EllSystem5.file, EllSystem5.min, EllSystem5.max, EllSystem5.loader);
                    DataSerialize.encode(property, uid);
                    System.out.println("-----------------");
                    System.out.println(" ");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                answer = scanner.nextLine();
            }
            if (!onAnswer(word, answer)) {
                fell.add(word);
            }
            last = word;
            System.out.println("(" + (j + 1) + "/" + words.size() + ") (F: " + fell.size() + ")");
            System.out.println(" ");
            System.out.println(" ");
        }
        this.words = fell;
        RandomSystem5 randomSystem5 = new RandomSystem5(fell.size());
        EllSystem5.data = new SystemProperty(fell, EllSystem5.currentType, EllSystem5.max, EllSystem5.min, EllSystem5.file, randomSystem5.getProperty(), EllSystem5.fileName, EllSystem5.loader);
        System.out.println("Fell: (" + fell.size() + "/" + words.size() + ")");
        System.out.println(" ");
        runPractice(fell,randomSystem5);
    }

    public void runTesting(List<EWord5> words) {
        Collections.shuffle(words);
        runTesting(words, 0, new ArrayList<>(), null);
    }

    public static void runTesting(TestingProperty property) {
        Renderer renderer = getValidRenderer(property.type);
        renderer.runTesting(property.words, property.index, property.fell, property.last);
    }

    private static Class<? extends Renderer>[] renderers = new Class[]{
            VNERenderer.class, ENLRenderer.class
    };

    public static Renderer getValidRenderer(PracticeType practiceType) {
        for (Class<? extends Renderer> clazz : renderers) {
            try {
                Renderer rde = clazz.newInstance();
                if (rde.acceptable().contains(EllSystem5.fileName)) {
//                if (rde.acceptable().contains("10kw-3.4.txt")) {
                    if (rde.type().equals(practiceType)) return rde;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return EllSystem5.initRenderer();
    }

    public static Renderer getValidRendererByID(String id) {
        for (Class<? extends Renderer> clazz : renderers) {
            try {
                Renderer rde = clazz.newInstance();
                if (rde.id().equalsIgnoreCase(id)) {
                    return rde;
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private static final String PREFIX = "\u001B[";
    private static final String SUFFIX = "\u001B[0m";

    public static void print(int index, String text) {
        System.out.print(PREFIX + "38;5;" + index + "m" + text + SUFFIX);
    }

    public static void println(StringColor... texts) {
        for (StringColor stringColor : texts) {
            print(stringColor.index, stringColor.text);
        }
        System.out.println("");
    }

    public static void println(int index, String text) {
        System.out.println(PREFIX + "38;5;" + index + "m" + text + SUFFIX);
    }

    public static String stringConverter(String input) {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalizedString).replaceAll("");
        return result.replace("đ", "d");
    }

    public static boolean isMeaningEqual(String meaning, String result) {
        meaning = stringConverter(meaning).toLowerCase().replace(" ", "");
        result = stringConverter(result).toLowerCase().replace(" ", "");
        List<String> elements = new ArrayList<>();
        if (meaning.contains(".")) {
            if (!result.contains(".")) return false;
            String[] types = meaning.split("\\.");
            String[] rsTypes = result.split("\\.");
            if (types.length != rsTypes.length) return false;
            for (int i = 0; i < types.length; i++) {
                String type = types[i];
                String rsType = rsTypes[i];
                elements.addAll(Arrays.asList(type.split(",")));
                int passed = 0;
                for (String s : rsType.split(",")) {
                    if (!elements.contains(s)) return false;
                    passed++;
                }
                if (elements.size() > passed) return false;
                elements.clear();
            }
        } else {
            elements.addAll(Arrays.asList(meaning.split(",")));
            int passed = 0;
            for (String s : result.split(",")) {
                if (!elements.contains(s)) return false;
                passed++;
            }
            if (elements.size() > passed) return false;
        }
        return true;
    }
}