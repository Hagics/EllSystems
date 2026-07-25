package me.thanhmagics.gen5;

import me.thanhmagics.gen5.loaders.F1Loader;
import me.thanhmagics.gen5.loaders.PVLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class WordLoader {

    private static final Logger LOGGER = Logger.getLogger(WordLoader.class.getName());

    public abstract EWord5 initWord(String line);
    public abstract String toString(EWord5 eWord5);
    public abstract List<String> acceptable();
    public abstract String id();

    public static Class<? extends WordLoader>[] loaders = new Class[] {
            F1Loader.class, PVLoader.class
    };

    public static WordLoader getLoaderByID(String id) {
        for (Class<? extends WordLoader> clazz : loaders) {
            try {
                WordLoader wordLoader = clazz.getDeclaredConstructor().newInstance();
                if (wordLoader.id().equalsIgnoreCase(id)) {
                    return wordLoader;
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error instantiating loader: " + clazz.getName(), e);
            }
        }
        return null;
    }

    public static WordLoader getLoaderByFile(File file) {
        WordLoader loader = null;
        for (Class<? extends WordLoader> clazz : loaders) {
            try {
                WordLoader wordLoader = clazz.getDeclaredConstructor().newInstance();
                if (wordLoader.acceptable().contains(file.getName())) {
                    loader = wordLoader;
                    break;
                }
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error instantiating loader: " + clazz.getName(), e);
            }
        }
        if (loader == null) {
            if (EllSystem5.loader != null) {
                loader = WordLoader.getLoaderByID(EllSystem5.loader);
            }
            if (loader == null) {
                loader = EllSystem5.initLoader();
            }
        }
        return loader;
    }

    public static LinkedList<EWord5> init(File file,int min,int max) {
        WordLoader loader = getLoaderByFile(file);
        EllSystem5.loader = loader.id();
        LinkedList<EWord5> rs = new LinkedList<>();
        try (Scanner scanner = new Scanner(file)) {
            int index = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (index >= min && index < max) {
                    EWord5 word5 = loader.initWord(line);
                    if (word5 != null) rs.add(word5);
                }
                index++;
            }
        } catch (FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, "File not found: " + file.getPath(), e);
            throw new RuntimeException(e);
        }
        return rs;
    }

}
