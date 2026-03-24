package me.thanhmagics.gen5;

import me.thanhmagics.gen4.DataSerialize;
import me.thanhmagics.gen5.properties.SystemProperty;
import me.thanhmagics.gen5.properties.TestingProperty;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static me.thanhmagics.gen5.Renderer.println;

public class EllSystem5 {

    public static final String PATH = "D:\\IdeaProjects\\EllSystems\\src\\main\\resources\\";
    public static int min = 0, max = Integer.MAX_VALUE;
    public static SystemProperty data = null;
    public static File file;
    public static Scanner scanner = new Scanner(System.in);
    public static Renderer renderer;
    public static PracticeType currentType;
    public static String fileName, loader = null;

    public static void main(String[] args) {
        println(new StringColor(252,"(=========)"), new StringColor(51," EllSystem Gen5 "), new StringColor(252,"(=========)"));
        println(new StringColor(46,"[0] "),new StringColor(226, "Start New Season"));
        println(new StringColor(46,"[1] "),new StringColor(226, "Using Serialized Data"));
        println(new StringColor(46,"[2] "),new StringColor(226, "Start Advanced Practice Season"));
        println(252, "(====================================)");
        int selected = scanner.nextInt();
        if (selected == 0) {
            selected = getSelected(scanner);
            file = getFile(scanner);
            initMinMax(scanner);
            List<EWord5> words = WorldLoader.init(file,min,max);
            if (selected == 0) {
                currentType = PracticeType.VNE;
            } else if (selected == 1) {
                currentType = PracticeType.ENL;
            } else if (selected == 2) {
            } else if (selected == 3) {}
            renderer = Renderer.getValidRenderer(currentType);
            if (renderer == null) {
                throw new RuntimeException("Render cannot be null!");
            }
            renderer.runPractice(words);
        } else if (selected == 1) {
            System.out.println("Enter Dat File UID ...");
            String line = scanner.next();
            Object o = DataSerialize.decode(line);
            if (o instanceof SystemProperty) {
                data = (SystemProperty) o;
                min = data.min;
                max = data.max;
                file = data.file;
                fileName = data.fileName;
                currentType = data.type;
                System.out.println("Data Serialize Debugging:");
                System.out.println(" - min: " + data.min);
                System.out.println(" - max: " + data.max);
                System.out.println(" - file: " + data.file.getPath());
                System.out.println(" - fName: " + data.fileName);
                System.out.println(" - type: " + data.type);
                System.out.println(" - size: " + data.words.size());
                System.out.println("--------------------------");
                System.out.println(" ");
                Renderer renderer = Renderer.getValidRenderer(currentType);
                if (renderer == null) {
                    throw new RuntimeException("Render cannot be null!");
                }
                EllSystem5.renderer = renderer;
                renderer.runPractice(data.words, new RandomSystem5(data.property));
            } else if (o instanceof TestingProperty property) {
                currentType = property.type;
                fileName = property.fileName;
                file = property.file;
                min = property.min;
                max = property.max;
                System.out.println("Data Serialize Debugging:");
                System.out.println(" - fell: " + property.fell.size());
                System.out.println(" - last: " + property.last);
                System.out.println(" - index: " + property.index + "+1");
                System.out.println(" - fName: " + property.fileName);
                System.out.println(" - type: " + property.type);
                System.out.println(" - size: " + property.words.size());
                System.out.println("--------------------------");
                System.out.println(" ");
                Renderer.runTesting(property);
            }
        } else if (selected == 2) {
            selected = getSelected(scanner);
            file = getFile(scanner);
            initMinMax(scanner);
            List<EWord5> words = WorldLoader.init(file,min,max);
            if (selected == 0) {
                currentType = PracticeType.VNE;
                renderer = Renderer.getValidRenderer(currentType);
            } else if (selected == 1) {
                currentType = PracticeType.ENL;
                renderer = Renderer.getValidRenderer(currentType);
            } else if (selected == 2) {
            } else if (selected == 3) {}
            renderer.runTesting(words);
        } else {
            System.out.println("Cannot verify Command!");
        }

    }

    public static boolean updateSetting(String arg, RandomSystem5 randomSystem, List<EWord5> words, int last) {
        try {
            if (arg.startsWith("-sv")) {
                String id = arg.split("-sv ")[1];
                if (data == null) {
                    data = new SystemProperty(words,currentType,max,min,file,randomSystem.getProperty(),fileName, loader);
                }
                DataSerialize.encode(data, id);
                return true;
            } else if (arg.startsWith("-sa")) {
                String content = arg.split("-sa ")[1];
                randomSystem.getProperty().skipAt = Integer.parseInt(content);
                randomSystem.update();
                return true;
            } else if (arg.startsWith("-paw")) {
                if (arg.startsWith("-pawf")) {
                    WorldLoader worldLoader = WorldLoader.getLoaderByFile(file);
                    for (EWord5 word : words) {
                        System.out.println(worldLoader.toString(word));
                    }
                } else {
                    for (EWord5 word : words) {
                        System.out.println(word.origin);
                    }
                }
                System.out.println("------------");
                System.out.println("Roll size: " + randomSystem.getProperty().inRoll.size());
                System.out.println("------------");
                return true;
            } else if (arg.startsWith("-rlw")) {
                if (arg.startsWith("-rlw2")) {
                    LinkedList<EWord5> ws = WorldLoader.init(file,min,max);
                    for (EWord5 word : words) {
                        word.reload(ws);
                    }
                    return true;
                }
                renderer.words = WorldLoader.init(file, min, max);
                return true;
            } else if (arg.startsWith("-caf")) {
                try {
                    randomSystem.getProperty().correctAtFirst = Integer.parseInt(arg.split("-caf ")[1]);
                } catch (Exception e) {
                    System.out.println("Cannot verify number!");
                }
                return true;
            } else if (arg.startsWith("-1")) {
                randomSystem.report(last,true, false);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static WorldLoader initLoader() {
        WorldLoader worldLoader = null;
        while (worldLoader == null) {
            println(154, "Enter Loader ID...");
            String input = scanner.next();
            worldLoader = WorldLoader.getLoaderByID(input);
            if (worldLoader == null) {
                System.out.println("Cannot find WorldLoader!, please try again!");
            }
            loader = input;
        }
        System.out.println("Init Successfully!");
        return worldLoader;
    }

    public static Renderer initRenderer() {
        Renderer rd = Renderer.getValidRendererByID(EllSystem5.currentType.name().toLowerCase());
        if (rd != null) return rd;
        while (rd == null) {
            println(154, "Enter Renderer ID...");
            String input = scanner.next();
            rd = Renderer.getValidRendererByID(input);
            if (rd == null) {
                System.out.println("Cannot find Renderer!, please try again!");
            }
        }
        System.out.println("Init Successfully!");
        return rd;
    }

    private static int getSelected(Scanner scanner) {
        println(252, "(====================================)");
        println(new StringColor(51,"[0] "), new StringColor(190,"VNE Testing"));
        println(new StringColor(51,"[1] "), new StringColor(190,"ENL Testing"));
        println(new StringColor(51,"[2] "), new StringColor(190,"IPA Testing"));
        println(new StringColor(51,"[3] "), new StringColor(190,"WTP Testing"));
        println(252, "(====================================)");
        return scanner.nextInt();
    }

    private static void initMinMax(Scanner scanner) {
        println(154,"Enter Limitation (min-max)");
        String mm = scanner.next();
        if (mm.equalsIgnoreCase("max")) {
            System.out.println("Init Min-Max Successfully!");
            return;
        }
        if (!mm.contains("-")) {
            System.out.println("Cannot verify command!");
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
        System.out.println("Init Min-Max Successfully! (min=" + min + ",max=" + max + ")");
    }

    private static File getFile(Scanner scanner) {
        println(154,"Enter Dataset name...");
        fileName = scanner.next();
        fileName = fileName.contains(".txt") ? fileName : fileName + ".txt";
        file = getFile1();
        if (!file.exists()) {
            System.out.println("File not found!");
            getFile(scanner);
        }
        return file;
//        InputStream input = EllSystem5.class.getClassLoader().getResourceAsStream((fileName.contains(".txt") ? fileName : fileName + ".txt"));
//
//        File tempFile = null;
//        try {
//            tempFile = File.createTempFile(fileName, ".txt");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        tempFile.deleteOnExit();
//        try (FileOutputStream out = new FileOutputStream(tempFile)) {
//            out.write(input.readAllBytes());
//        } catch (NullPointerException e) {
//            file = new File(PATH + "serialized\\" + (fileName.contains(".txt") ? fileName : fileName + ".txt"));
//            if (!file.exists()) {
//                file = getFile1();
////                if (f == null) {
////                    println(196, "File Not Found!");
////                    System.exit(1);
////                }
//            }
//            return file;
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        file = tempFile;
//        return tempFile;
    }

    private static File getFile1() {
        return new File(PATH + fileName);
    }

}