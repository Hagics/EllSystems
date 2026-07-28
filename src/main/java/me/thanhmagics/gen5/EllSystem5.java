package me.thanhmagics.gen5;

import me.thanhmagics.gen4.DataSerialize;
import me.thanhmagics.gen5.properties.SystemProperty;
import me.thanhmagics.gen5.properties.TestingProperty;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static me.thanhmagics.gen5.Renderer.println;

public class EllSystem5 {

    private static final Logger LOGGER = Logger.getLogger(EllSystem5.class.getName());
    
    public static final String PATH = Paths.get(System.getProperty("user.dir"), 
        "src", "main", "resources") + File.separator;
    
    public static int min = 0, max = Integer.MAX_VALUE, maxLevel = Integer.MAX_VALUE;
    public static SystemProperty data = null;
    public static File file;
    public static Scanner scanner = new Scanner(System.in);
    public static Renderer renderer;
    public static PracticeType currentType;
    public static String fileName, loader = null;
    public static Database database = new Database("database.db");

    public static void main(String[] args) {
        try {
            displayMainMenu();
            int selected = getMenuInput();
            
            switch (selected) {
                case 0 -> handleNewSeason();
                case 1 -> handleSerializedData();
                case 2 -> handleAdvancedPractice();
                default -> LOGGER.warning("Invalid command: " + selected);
            }
        } finally {
            closeResources();
        }
    }

    private static void displayMainMenu() {
        println(new StringColor(252,"(=========)"), new StringColor(51," EllSystem Gen5 "), new StringColor(252,"(=========)"));
        println(new StringColor(46,"[0] "),new StringColor(226, "Start New Season"));
        println(new StringColor(46,"[1] "),new StringColor(226, "Using Serialized Data"));
        println(new StringColor(46,"[2] "),new StringColor(226, "Start Advanced Practice Season"));
        println(252, "(====================================)");
    }

    private static int getMenuInput() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error reading menu input", e);
            scanner.nextLine();
            return -1;
        }
    }

    private static void handleNewSeason() {
        try {
            int selected = getSelected(scanner);
            file = getFile(scanner);
            if (file == null) {
                LOGGER.warning("File selection cancelled");
                return;
            }
            initMinMax(scanner);
            initMaxLevel(scanner);
            List<EWord5> words = WordLoader.init(file, min, max);
            
            currentType = switch (selected) {
                case 0 -> PracticeType.VNE;
                case 1 -> PracticeType.ENL;
                case 2 -> PracticeType.IPA;
                default -> null;
            };
            
            if (currentType == null) {
                LOGGER.warning("Invalid practice type selected");
                return;
            }
            
            renderer = Renderer.getValidRenderer(currentType);
            if (renderer == null) {
                throw new RuntimeException("Renderer cannot be null!");
            }
            renderer.runPractice(words);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in new season", e);
        }
    }

    private static void handleSerializedData() {
        try {
            println(154, "Enter Dat File UID ...");
            String line = scanner.next();
            Object o = DataSerialize.decode(line);
            
            if (o instanceof SystemProperty systemProperty) {
                handleSystemProperty(systemProperty);
            } else if (o instanceof TestingProperty testingProperty) {
                handleTestingProperty(testingProperty);
            } else {
                LOGGER.warning("Unknown serialized data type");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error handling serialized data", e);
        }
    }

    private static void handleSystemProperty(SystemProperty systemProperty) {
        data = systemProperty;
        min = data.min;
        max = data.max;
        file = data.file;
        fileName = data.fileName;
        currentType = data.type;
        
        logDebugInfo("System Property", data);
        
        Renderer renderer = Renderer.getValidRenderer(currentType);
        if (renderer == null) {
            throw new RuntimeException("Renderer cannot be null!");
        }
        EllSystem5.renderer = renderer;
        renderer.runPractice(data.words, new RandomSystem5(data));
    }

    private static void handleTestingProperty(TestingProperty property) {
        currentType = property.type;
        fileName = property.fileName;
        file = property.file;
        min = property.min;
        max = property.max;
        
        logDebugInfo("Testing Property", property);
        Renderer.runTesting(property);
    }

    private static void logDebugInfo(String title, Object property) {
        println(250, "Data Serialize Debugging: " + title);
        if (property instanceof SystemProperty sp) {
            println(250, " - min: " + sp.min);
            println(250, " - max: " + sp.max);
            println(250, " - file: " + sp.file.getPath());
            println(250, " - id: " + sp.fileName);
            println(250, " - type: " + sp.type);
            println(250, " - size: " + sp.words.size());
            println(250, " - index: " + sp.property.index);
            println(250, " - SA: " + sp.property.skipAt);
        } else if (property instanceof TestingProperty tp) {
            println(250, " - fell: " + tp.fell.size());
            println(250, " - last: " + tp.last);
            println(250, " - index: " + tp.index);
            println(250, " - id: " + tp.fileName);
            println(250, " - type: " + tp.type);
            println(250, " - size: " + tp.words.size());


        }
        println(250, "--------------------------");
    }

    private static void handleAdvancedPractice() {
        try {
            int selected = getSelected(scanner);
            file = getFile(scanner);
            if (file == null) {
                LOGGER.warning("File selection cancelled");
                return;
            }
            initMinMax(scanner);
            initMaxLevel(scanner);
            List<EWord5> words = WordLoader.init(file, min, max);
            
            currentType = switch (selected) {
                case 0 -> PracticeType.VNE;
                case 1 -> PracticeType.ENL;
                case 2 -> PracticeType.IPA;
                default -> null;
            };
            
            if (currentType == null) {
                LOGGER.warning("Invalid practice type selected");
                return;
            }
            
            renderer = Renderer.getValidRenderer(currentType);
            if (renderer == null) {
                throw new RuntimeException("Renderer cannot be null!");
            }
            renderer.runTesting(words);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error in advanced practice", e);
        }

    }

    public static boolean updateSetting(String arg, RandomSystem5 randomSystem, List<EWord5> words, int last) {
        if (arg == null || arg.trim().isEmpty()) {
            return false;
        }
        
        try {
            if (arg.startsWith("-sv")) {
                return handleSaveCommand(arg, randomSystem, words);
            } else if (arg.startsWith("-sa")) {
                return handleSkipCommand(arg, randomSystem);
            } else if (arg.startsWith("-paw")) {
                return handlePrintWordsCommand(arg, words, randomSystem);
            } else if (arg.startsWith("-rlw")) {
                return handleReloadWordsCommand(arg);
            } else if (arg.startsWith("-caf")) {
                return handleCorrectAtFirstCommand(arg, randomSystem);
            } else if (arg.startsWith("-1")) {
                randomSystem.report(last, true, false);
                return true;
            } else if (arg.startsWith("-info")) {
                return handleInfoCommand(randomSystem);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating setting: " + arg, e);
        }
        return false;
    }

    private static boolean handleSaveCommand(String arg, RandomSystem5 randomSystem, List<EWord5> words) {
        try {
            String id = arg.split("-sv ")[1];
            if (data == null) {
                data = new SystemProperty(words, currentType, max, min, file, 
                    randomSystem.getProperty(), fileName, loader);
            }
            DataSerialize.encode(data, id);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error saving data", e);
            return false;
        }
    }

    private static boolean handleSkipCommand(String arg, RandomSystem5 randomSystem) {
        try {
            String content = arg.split("-sa ")[1];
            randomSystem.getProperty().skipAt = Integer.parseInt(content);
            randomSystem.update();
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error in skip command", e);
            return false;
        }
    }

    private static boolean handlePrintWordsCommand(String arg, List<EWord5> words, RandomSystem5 randomSystem) {
        try {
            if (arg.startsWith("-pawf")) {
                WordLoader wordLoader = WordLoader.getLoaderByFile(file);
                for (EWord5 word : words) {
                    println(250, wordLoader.toString(word));
                }
            } else {
                for (EWord5 word : words) {
                    println(250, word.origin);
                }
            }
            println(250, "------------");
            println(250, "Roll size: " + randomSystem.getProperty().inRoll.size());
            println(250, "------------");
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error printing words", e);
            return false;
        }
    }

    private static boolean handleReloadWordsCommand(String arg) {
        try {
            if (arg.startsWith("-rlw2")) {
                LinkedList<EWord5> ws = WordLoader.init(file, min, max);
                for (EWord5 word : renderer.words) {
                    word.reload(ws);
                }
            } else {
                renderer.words = WordLoader.init(file, min, max);
            }
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error reloading words", e);
            return false;
        }
    }

    private static boolean handleCorrectAtFirstCommand(String arg, RandomSystem5 randomSystem) {
        try {
            randomSystem.getProperty().correctAtFirst = Integer.parseInt(arg.split("-caf ")[1]);
            return true;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Cannot verify number", e);
            return false;
        }
    }

    private static boolean handleInfoCommand(RandomSystem5 randomSystem) {
        println(250, "<-------> Info <------->");
        println(250, " - index: " + randomSystem.getProperty().index);
        println(250, " - id: " + fileName);
        println(250, " - type: " + currentType);
        println(250, " - size: " + (renderer != null ? renderer.words.size() : 0));
        println(250, " - in-roll: " + randomSystem.getProperty().inRoll.size());
        println(250, " - SA: " + randomSystem.getProperty().skipAt);
        println(250, "========================");
        return true;
    }

    public static WordLoader initLoader() {
        WordLoader wordLoader = null;
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;
        
        while (wordLoader == null && attempts < MAX_ATTEMPTS) {
            println(154, "Enter Loader ID...");
            String input = scanner.next();
            wordLoader = WordLoader.getLoaderByID(input);
            if (wordLoader == null) {
                println(196, "Cannot find WorldLoader!, please try again!");
                attempts++;
            } else {
                loader = input;
            }
        }
        
        if (wordLoader == null) {
            LOGGER.severe("Failed to initialize loader after " + MAX_ATTEMPTS + " attempts");
            throw new RuntimeException("Could not initialize WorldLoader");
        }
        
        println(118, "Init Successfully!");
        return wordLoader;
    }

    public static Renderer initRenderer() {
        Renderer rd = Renderer.getValidRendererByID(currentType.name().toLowerCase());
        if (rd != null) return rd;
        
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;
        
        while (rd == null && attempts < MAX_ATTEMPTS) {
            println(154, "Enter Renderer ID...");
            String input = scanner.next();
            rd = Renderer.getValidRendererByID(input);
            if (rd == null) {
                println(196, "Cannot find Renderer!, please try again!");
                attempts++;
            }
        }
        
        if (rd == null) {
            LOGGER.severe("Failed to initialize renderer after " + MAX_ATTEMPTS + " attempts");
            throw new RuntimeException("Could not initialize Renderer");
        }
        
        println(118, "Init Successfully!");
        return rd;
    }

    private static int getSelected(Scanner scanner) {
        println(252, "(====================================)");
        println(new StringColor(51,"[0] "), new StringColor(190,"VNE Testing"));
        println(new StringColor(51,"[1] "), new StringColor(190,"ENL Testing"));
        println(new StringColor(51,"[2] "), new StringColor(190,"IPA Testing"));
        println(new StringColor(51,"[3] "), new StringColor(190,"WTP Testing"));
        println(252, "(====================================)");
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error reading selection", e);
            scanner.nextLine();
            return -1;
        }
    }

    private static void initMinMax(Scanner scanner) {
        println(154,"Enter Limitation (min-max)");
        try {
            String mm = scanner.next();
            if (mm.equalsIgnoreCase("max")) {
                println(118, "Init Min-Max Successfully!");
                return;
            }
            if (!mm.contains("-")) {
                println(196, "Cannot verify command!");
                initMinMax(scanner);
                return;
            }
            min = Integer.parseInt(mm.split("-")[0]);
            try {
                max = Integer.parseInt(mm.split("-")[1]);
            } catch (Exception e) {
                if (mm.split("-")[1].equalsIgnoreCase("max")) {
                    max = Integer.MAX_VALUE;
                }
            }
            println(118, "Init Min-Max Successfully! (min=" + min + ",max=" + max + ")");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error initializing min-max", e);
            initMinMax(scanner);
        }
    }

    private static void initMaxLevel(Scanner scanner) {
        println(154,"Enter Max Level...");
        try {
            String ml = scanner.next();
            if (ml.equalsIgnoreCase("max")) {
                println(118, "Init Max Level Successfully!");
                return;
            }
            maxLevel = Integer.parseInt(ml);
            println(118, "Init Max Level Successfully! (maxLevel=" + maxLevel + ")");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error initializing max level", e);
            initMaxLevel(scanner);
        }
    }

    private static File getFile(Scanner scanner) {
        println(154,"Enter Dataset name...");
        fileName = scanner.next();
        fileName = fileName.contains(".txt") ? fileName : fileName + ".txt";
        file = new File(PATH + fileName);
        
        if (!file.exists()) {
            println(196, "File not found!");
            return getFile(scanner);
        }
        return file;
    }

    private static void closeResources() {
        try {
            if (scanner != null) {
                scanner.close();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error closing scanner", e);
        }
    }

}