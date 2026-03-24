package me.thanhmagics.gen4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PracticeSeason {

    public static final String PATH = "D:\\IdeaProjects\\EllSystems\\src\\main\\resources\\";

    public static final Scanner input = new Scanner(System.in);

    public List<SeasonWord> words;

    public SeasonProperty property;

    public int min = 0,max = Integer.MAX_VALUE;

    public int learned = 0;

    public boolean wordTypeSeason = false, pronounceSeason = false;

    public RandomSystem randomSystem = new RandomSystem(this);

    public long start = System.currentTimeMillis();

    public File dataFile;

    public synchronized void start() {
        System.out.println("(========) EllSystem 4.0 By ThanhMagics (========)");
        System.out.println("[0] Start New Season");
        System.out.println("[1] Used Serialized Data");
        System.out.println("[2] Advance Practice");
        System.out.println("[3] Word's Type Practice");
        System.out.println("[4] Word's Type Advance Practice");
        System.out.println("[5] Word's Pronounce Practice");
        System.out.println("[6] Word's Pronounce Advance Practice");
        System.out.println("[7] Phrasal Verb Practice");
        System.out.println("(================================================)");
        int code = Integer.parseInt(input.nextLine());
        if (code == 0) {
            System.out.println("Enter file name...");
            initFileID();
            initLimit();
            initWord();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
        } else if (code == 1) {
            System.out.println("Please Enter File ID To Start...");
            String fileName = input.nextLine();
            property = (SeasonProperty) DataSerialize.decode(fileName);
            if (property == null)
                throw new RuntimeException("Property Not Found!");
            words = property.words;
            randomSystem.index = property.index;
            randomSystem.inRoll = property.inRoll;
            randomSystem.corrected = property.corrected;
            randomSystem.rolled = property.rolled;
            start = property.start;
            learned = property.learned;
            if (property.type == 1) {
                wordTypeSeason = true;
            } else if (property.type == 2) {
                pronounceSeason = true;
                KeyReplacer.reg();
            }
        } else if (code == 2) {
            System.out.println("Enter File Name");
            initFileID();
            initLimit();
            initWord();
            List<SeasonWord> fell = new LinkedList<>();
            Collections.shuffle(words);
            SeasonWord last = null;
            int index = 0;
            for (SeasonWord word : words) {
                println(96,word.getMeaning() + " (" + word.getType() + ")");
                String rs = input.nextLine();
                if (!updateSetting(rs))
                    continue;
                if (rs.equals("-1")) {
                    if (last != null) {
                        fell.remove(last);
                    }
                    rs = input.nextLine();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println(" ");
                }
                if (rs.equalsIgnoreCase(word.getOriginal())) {
                    println(32,rs);
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    println(92,word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")");
                } else {
                    println(91,rs);
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    println(93,word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")");
                    fell.add(word);
                }
                last = word;
                index++;
                System.out.println(" ");
                System.out.println(index + "/" + words.size());
                System.out.println(" ");
                System.out.println(" ");
            }
            System.out.println(" ");
            System.out.println("------------------------");
            System.out.println(fell.size() + "/" + words.size());
            System.out.println("------------------------");
            System.out.println(" ");
            this.words = fell;
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System.currentTimeMillis(),learned);
        } else if (code == 3) {
            System.out.println("Enter File Name");
            initFileID();
            initLimit();
            initWord();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
            wordTypeSeason = true;
        } else if (code == 4) {
            System.out.println("Enter File Name");
            initFileID();
            initLimit();
            initWord();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
            wordTypeSeason = true;
            List<SeasonWord> fell = new LinkedList<>();
            Collections.shuffle(words);
            SeasonWord last = null;
            int index = 0;
            for (SeasonWord word : words) {
                System.out.println(word.getOriginal());
                String rs = input.nextLine();
                if (!updateSetting(rs))
                    continue;
                if (rs.equals("-1")) {
                    if (last != null) {
                        fell.remove(last);
                    }
                    rs = input.nextLine();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println(" ");
                }
                if (rs.equalsIgnoreCase(word.getType())) {
                    print(94,word.getType() + " ");
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(92, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                } else {
                    print(94,word.getType() + " ");
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(93, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                    fell.add(word);
                }
                last = word;
                index++;
                System.out.println(" ");
                System.out.println(index + "/" + words.size());
                System.out.println(" ");
                System.out.println(" ");
            }
            System.out.println(" ");
            System.out.println("------------------------");
            System.out.println(fell.size() + "/" + words.size());
            System.out.println("------------------------");
            System.out.println(" ");
            this.words = fell;
        } else if (code == 5) {
            System.out.println("Enter File Name");
            initFileID();
            initLimit();
            initWord();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
            pronounceSeason = true;
            KeyReplacer.reg();
        } else if (code == 6) {
            System.out.println("Enter File Name");
            initFileID();
            initLimit();
            initWord();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
            pronounceSeason = true;
            List<SeasonWord> fell = new LinkedList<>();
            Collections.shuffle(words);
            SeasonWord last = null;
            int index = 0;
            KeyReplacer.reg();
            for (SeasonWord word : words) {
                System.out.println(word.getOriginal() + "\u001B[94m " + word.getType() + "\u001B[0m" + "\u001B[92m (" + word.getMeaning() + ")\u001B[0m");
                String rs = input.nextLine();
                if (!updateSetting(rs))
                    continue;
                if (rs.equals("-1")) {
                    if (last != null) {
                        fell.remove(last);
                    }
                    rs = input.nextLine();
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println(" ");
                }
                if (rs.equalsIgnoreCase(word.getPronoun(true))) {
                    println(32,rs);
                    println(95,word.getPronoun(true));
                    println(93, word.getPronoun(false));
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    KeyReplacer.typedText = new StringBuilder();
                } else {
                    println(91,rs);
                    println(95,word.getPronoun(true));
                    println(93, word.getPronoun(false));
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    KeyReplacer.typedText = new StringBuilder();
                    fell.add(word);
                }
                last = word;
                index++;
                System.out.println(" ");
                System.out.println(index + "/" + words.size());
                System.out.println(" ");
                System.out.println(" ");
            }
            System.out.println(" ");
            System.out.println("------------------------");
            System.out.println(fell.size() + "/" + words.size());
            System.out.println("------------------------");
            System.out.println(" ");
            this.words = fell;
        } else if (code == 7) {
            System.out.println("Enter file name...");
            initFileID();
            initLimit();
            initWord2();
            property = new SeasonProperty(words,0,new HashMap<>(),new LinkedList<>(),System
                    .currentTimeMillis(),learned);
            while (true) {
                SeasonWord word = randomSystem.get();
                System.out.println("\u001B[94m" + word.getMeaning() + "\u001B[0m");
                String rs = input.nextLine();
                if (!updateSetting(rs))
                    continue;
                for (int i = 0; i < 15; i++) {
                    System.out.println(" ");
                }
                String answer = word.getOriginal();
                if (rs.equalsIgnoreCase(answer)) {
                    randomSystem.report(word,true);
                    println(32,rs);
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                } else {
                    randomSystem.report(word,false);
                    println(91,rs);
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                }
                printExamples(word);
                for (int i = 0; i < 4; i++) {
                    System.out.println(" ");
                }
            }
        }
        System.out.println("Successful!");
        start = System.currentTimeMillis();
        simplePractice();
    }

    public void simplePractice() {
        while (true) {
            SeasonWord word = randomSystem.get();
            if (wordTypeSeason) {
                System.out.println(word.getOriginal() + "\u001B[94m " + word.getMeaning() + "\u001B[0m");
            } else if (pronounceSeason) {
                System.out.println(word.getOriginal() + "\u001B[94m " + word.getType() + "\u001B[0m" + "\u001B[92m (" + word.getMeaning() + ")\u001B[0m");
            } else {
                System.out.println(word.getMeaning() + "\u001B[94m (" + word.getType() + ")\u001B[0m");
            }
            String rs = input.nextLine();
            if (!updateSetting(rs))
                continue;
            for (int i = 0; i < 15; i++) {
                System.out.println(" ");
            }
            String answer = wordTypeSeason ? word.getType() : pronounceSeason ? word.getPronoun(true) : word.getOriginal();
            if (rs.equals(answer)) {
                randomSystem.report(word,true);
                println(32,rs);
                if (pronounceSeason) {
                    println(95,word.getPronoun(true));
                    println(93, word.getPronoun(false));
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    KeyReplacer.typedText = new StringBuilder();
                } else if (wordTypeSeason) {
                    print(94,word.getType() + " ");
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(92, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                } else {
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(92, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                }
            } else {
                randomSystem.report(word,false);
                println(91,rs);
                if (pronounceSeason) {
                    println(95,word.getPronoun(true));
                    println(93, word.getPronoun(false));
                    print(95,word.getOriginal());
                    print(96," " + word.getMeaning() + "\n");
                    KeyReplacer.typedText = new StringBuilder();
                } else if (wordTypeSeason) {
                    print(94,word.getType() + " ");
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(93, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                } else {
                    print(95, word.getOriginal());
                    print(96, " " + word.getMeaning() + "\n");
                    println(93, word.getPronoun(false) + "\u001B[94m (" + word.getType() + ")" + (wordTypeSeason ? " " + word.getPronoun(true) : ""));
                }
            }
            printExamples(word);
            for (int i = 0; i < 4; i++) {
                System.out.println(" ");
            }
        }
    }

    public void printExamples(SeasonWord word) {
        for (String clause : word.getClause()) {
            if (clause.length() < 80) {
                println(37, clause);
            }
        }
    }

    public void initFileID() {
        String fileName = input.nextLine();
        dataFile = new File(PATH + fileName + ".txt");
        if (!dataFile.exists()) {
            System.out.println("File Not Found!");
            System.exit(0);
        }
    }

    public boolean updateSetting(String arg) {
        try {
            if (arg.startsWith("-sv")) {
                String id = arg.split("-sv ")[1];
                randomSystem.updateProperty();
                DataSerialize.encode(property, id);
            } else if (arg.startsWith("-rf")) {
                String content = arg.split("-rf ")[1];
                randomSystem.refresh = Integer.parseInt(content);
            } else if (arg.startsWith("-sa")) {
                String content = arg.split("-sa ")[1];
                randomSystem.skipAt = Integer.parseInt(content);
                randomSystem.update();
            } else if (arg.startsWith("-rs")) {
                String content = arg.split("-rs ")[1];
                randomSystem.rollSize = Integer.parseInt(content);
            } else if (arg.startsWith("-pfm")) {
                randomSystem.printPerformance();
            } else if (arg.startsWith("-pw")) {
                for (SeasonWord word : words) {
                    System.out.println(word.toString());
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            println(91,"update fell!");
        }
        return false;
    }

    public static void println(int color,String text) {
        System.out.println("\u001B[" + color + "m" + text + "\u001B[0m");
    }

    public static void print(int color,String text) {
        System.out.print("\u001B[" + color + "m" + text + "\u001B[0m");
    }

    public void initLimit() {
        System.out.println("Please Enter Limitation (min-max)");
        String string = input.nextLine();
        this.min = Integer.parseInt(string.split("-")[0]);
        this.max = Integer.parseInt(string.split("-")[1]);;
    }

    public void initWord2() {
        List<SeasonWord> init = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(dataFile);
            int j = 0, i = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(":");
                if (i >= min && i < max) {
                    init.add(new SeasonWord(line[0], null, line[1].substring(1), null, j, Collections.singletonList(line[2])));
                    j++;
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.words = init;
    }


    public void initWord() {
        List<SeasonWord> init = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(dataFile);
            int i = 0,j = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (i >= min && i < max) {
                    SeasonWord word = getSeasonWord(line, j);
                    init.add(word);
                    j++;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.words = init;
    }

    private static SeasonWord getSeasonWord(String line,int index) {
        String o = line.split(":")[0];
        String m = line.split(":")[1];
        String p = line.split(":")[2];
        StringBuilder type = new StringBuilder();
        type.append(o.split(" ")[1]);
        try {
            type.append(o.split(" ")[2]);
        } catch (Exception ignored) {}
        List<String> examples = new LinkedList<>();
        try {
            String e = line.split(":")[3];
            examples.addAll(Arrays.asList(e.split(" <AND> ")));
        } catch (Exception ignored) {}
        return new SeasonWord(o.split(" ")[0],p,m,type.toString()
                .replace("nv","vn"),index,examples);
    }


}
