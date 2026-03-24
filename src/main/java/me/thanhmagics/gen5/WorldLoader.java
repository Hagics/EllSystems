package me.thanhmagics.gen5;

import me.thanhmagics.gen5.loaders.F1Loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class WorldLoader {

    public abstract EWord5 initWord(String line);
    public abstract String toString(EWord5 eWord5);
    public abstract List<String> acceptable();
    public abstract String id();

    public static Class<? extends WorldLoader>[] loaders = new Class[] {
            F1Loader.class
    };

    public static WorldLoader getLoaderByID(String id) {
        for (Class<? extends  WorldLoader> clazz : loaders) {
            try {
                WorldLoader worldLoader = clazz.newInstance();
                if (worldLoader.id().equalsIgnoreCase(id)) {
                    return worldLoader;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static WorldLoader getLoaderByFile(File file) {
        WorldLoader loader = null;
        for (Class<? extends  WorldLoader> clazz : loaders) {
            try {
                WorldLoader worldLoader = clazz.newInstance();
                if (worldLoader.acceptable().contains(file.getName())) {
                    loader = worldLoader;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (loader == null) {
            if (EllSystem5.loader != null) {
                loader = WorldLoader.getLoaderByID(EllSystem5.loader);
            }
            if (loader == null) {
                loader = EllSystem5.initLoader();
            }
        }
        return loader;
    }

    public static LinkedList<EWord5> init(File file,int min,int max) {
        WorldLoader loader = getLoaderByFile(file);
        EllSystem5.loader = loader.id();
        LinkedList<EWord5> rs = new LinkedList<>();
        try {
            Scanner scanner = new Scanner(file);
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
            throw new RuntimeException(e);
        }
        return rs;
    }

}
