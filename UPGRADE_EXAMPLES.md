# Code Upgrade Examples - Before & After

## Example 1: Hardcoded Path → Dynamic Path

### Before:
```java
public static final String PATH = "D:\\IdeaProjects\\EllSystems\\src\\main\\resources\\";
```

### After:
```java
public static final String PATH = Paths.get(System.getProperty("user.dir"), 
    "src", "main", "resources").toString() + File.separator;
```

**Benefits**: Works on any OS and any machine, no hardcoded paths

---

## Example 2: Poor Error Handling → Proper Error Handling

### Before:
```java
public static int getMenuInput() {
    return scanner.nextInt();
}
```

### After:
```java
private static int getMenuInput() {
    try {
        return scanner.nextInt();
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error reading menu input", e);
        scanner.nextLine(); // Clear input buffer
        return -1;
    }
}
```

**Benefits**: Graceful error handling, proper logging, input buffer cleared

---

## Example 3: Deprecated Reflection → Modern Reflection

### Before:
```java
Renderer rde = clazz.newInstance();
```

### After:
```java
Renderer rde = clazz.getDeclaredConstructor().newInstance();
```

**Benefits**: Not deprecated, better security, cleaner API

---

## Example 4: System.out → Java Logger

### Before:
```java
System.out.println("Enter Loader ID...");
String input = scanner.next();
wordLoader = WorldLoader.getLoaderByID(input);
if (wordLoader == null) {
    System.out.println("Cannot find WorldLoader!, please try again!");
}
```

### After:
```java
println(154, "Enter Loader ID...");
String input = scanner.next();
wordLoader = WorldLoader.getLoaderByID(input);
if (wordLoader == null) {
    println(196, "Cannot find WorldLoader!, please try again!");
    attempts++;
} else {
    loader = input;
}
```

**Benefits**: Proper logging, better control, consistency

---

## Example 5: Resource Leak → Proper Resource Management

### Before:
```java
public void runPractice(List<EWord5> words, RandomSystem5 randomSystem5) {
    this.words = words;
    Scanner scanner = new Scanner(System.in);
    int last = -1;
    while (true) {
        // ... code without closing scanner
    }
}
```

### After:
```java
public void runPractice(List<EWord5> words, RandomSystem5 randomSystem5) {
    this.words = words;
    Scanner scanner = new Scanner(System.in);
    int last = -1;
    try {
        while (true) {
            // ... code
        }
    } finally {
        scanner.close();
    }
}
```

**Benefits**: No resource leaks, proper cleanup

---

## Example 6: WorldLoader Scanner Not Closed → Try-with-Resources

### Before:
```java
public static LinkedList<EWord5> init(File file,int min,int max) {
    WorldLoader loader = getLoaderByFile(file);
    EllSystem5.loader = loader.id();
    LinkedList<EWord5> rs = new LinkedList<>();
    try {
        Scanner scanner = new Scanner(file);
        int index = 0;
        while (scanner.hasNextLine()) {
            // ... code
        }
    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    }
    return rs;
}
```

### After:
```java
public static LinkedList<EWord5> init(File file,int min,int max) {
    WorldLoader loader = getLoaderByFile(file);
    EllSystem5.loader = loader.id();
    LinkedList<EWord5> rs = new LinkedList<>();
    try (Scanner scanner = new Scanner(file)) {
        int index = 0;
        while (scanner.hasNextLine()) {
            // ... code
        }
    } catch (FileNotFoundException e) {
        LOGGER.log(Level.SEVERE, "File not found: " + file.getPath(), e);
        throw new RuntimeException(e);
    }
    return rs;
}
```

**Benefits**: Automatic resource cleanup, improved logging

---

## Example 7: God Method → Extracted Methods

### Before:
```java
public static void main(String[] args) {
    println(...); // Menu display
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
        // ... more code
    } else if (selected == 2) {
        // ... even more code
    } else {
        System.out.println("Cannot verify Command!");
    }
}
```

### After:
```java
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

private static void handleNewSeason() {
    try {
        int selected = getSelected(scanner);
        file = getFile(scanner);
        if (file == null) {
            LOGGER.warning("File selection cancelled");
            return;
        }
        initMinMax(scanner);
        List<EWord5> words = WorldLoader.init(file, min, max);
        
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
```

**Benefits**: Single Responsibility Principle, easier to test, clearer intent

---

## Example 8: Magic Numbers → Named Constants

### Before:
```java
public boolean onAnswer(EWord5 word, String answer) {
    if (word instanceof F1Word f1Word) {
        boolean isCorrect = isMeaningEqual(word.meaning,answer);
        for (int i = 0; i < 15; i++) {  // Magic number!
            System.out.println(" ");
        }
        if (isCorrect) {
            // ...
        }
        for (int i = 0; i < f1Word.getExamples().size(); i++) {
            if (i > 5) break;  // Magic number!
            println(250, f1Word.getExamples().get(i));
        }
        for (int i = 0; i < 5; i++) {  // Magic number!
            System.out.println(" ");
        }
        return isCorrect;
    }
}
```

### After:
```java
private static final int MAX_EXAMPLES = 5;
private static final int BLANK_LINES = 15;

public boolean onAnswer(EWord5 word, String answer) {
    if (word instanceof F1Word f1Word) {
        boolean isCorrect = isMeaningEqual(word.meaning, answer);
        printBlankLines(BLANK_LINES);
        
        if (isCorrect) {
            // ...
        }
        printExamples(f1Word);
        printBlankLines(5);
        return isCorrect;
    }
}

private void printExamples(F1Word f1Word) {
    int exampleCount = 0;
    for (String example : f1Word.getExamples()) {
        if (exampleCount >= MAX_EXAMPLES) break;
        println(250, example);
        exampleCount++;
    }
}

private void printBlankLines(int count) {
    for (int i = 0; i < count; i++) {
        System.out.println(" ");
    }
}
```

**Benefits**: Self-documenting code, easier to maintain and modify

---

## Example 9: Broken Infinite Loop → Loop with MAX_ATTEMPTS

### Before:
```java
public static WorldLoader initLoader() {
    WorldLoader wordLoader = null;
    while (wordLoader == null) {
        println(154, "Enter Loader ID...");
        String input = scanner.next();
        wordLoader = WorldLoader.getLoaderByID(input);
        if (wordLoader == null) {
            System.out.println("Cannot find WorldLoader!, please try again!");
        }
        loader = input;
    }
    System.out.println("Init Successfully!");
    return wordLoader;
}
```

### After:
```java
public static WorldLoader initLoader() {
    WorldLoader wordLoader = null;
    int attempts = 0;
    final int MAX_ATTEMPTS = 5;
    
    while (wordLoader == null && attempts < MAX_ATTEMPTS) {
        println(154, "Enter Loader ID...");
        String input = scanner.next();
        wordLoader = WorldLoader.getLoaderByID(input);
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
```

**Benefits**: Prevents infinite loops, better user experience, proper error reporting

---

## Example 10: String Manipulation Cleanup

### Before:
```java
String meaning = line.split(":")[1];
if (meaning.startsWith(" ")) {
    StringBuilder sb = new StringBuilder(meaning);
    sb.deleteCharAt(0);
    meaning = sb.toString();
}
```

### After:
```java
String meaning = line.split(":")[1];
if (meaning.startsWith(" ")) {
    meaning = meaning.substring(1);
}
```

**Benefits**: Cleaner, more efficient, more readable

---

## Summary of Patterns Used

1. **Extract Method**: Break large methods into smaller, focused methods
2. **Fail-Fast**: Add validation early and fail with clear messages
3. **Resource Management**: Use try-with-resources for automatic cleanup
4. **Constants**: Replace magic numbers with named constants
5. **Logging Framework**: Use proper Java logging instead of println
6. **Error Handling**: Comprehensive try-catch with proper logging
7. **Modern Java**: Use switch expressions and pattern matching
8. **SOLID Principles**: Single Responsibility, Open/Closed, etc.

