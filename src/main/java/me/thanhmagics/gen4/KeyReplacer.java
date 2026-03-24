package me.thanhmagics.gen4;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyReplacer implements NativeKeyListener {


    private final Map<String,String> replaces = new HashMap<>();

    public static void reg() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new KeyReplacer());
        } catch (NativeHookException e) {
            throw new RuntimeException(e);
        }
    }


    public KeyReplacer() {
        replaces.put("ow","ə");
        replaces.put("ae","æ");
        replaces.put("o7","ʌ");
        replaces.put("ơ","ʌ");
        replaces.put("sh","ʃ");
        replaces.put("th","ð");
        replaces.put("ng","ŋ");
        replaces.put("oo","ɔ");
    }

    public static StringBuilder typedText = new StringBuilder();
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
        char keyChar = e.getKeyChar();
        typedText.append(keyChar);
        check();
    }

    public void check() {
        if (replaces.containsKey(typedText.toString())) {
            int i = typedText.toString().length();
            Robot robot = null;
            try {
                robot = new Robot();
            } catch (AWTException ex) {
                throw new RuntimeException(ex);
            }
            for (int j = 0; j < i; j++) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }
            String text = replaces.get(typedText.toString());
            clipboard.setContents(new StringSelection(text), null);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            typedText = new StringBuilder();
        } else {
            if (typedText.length() > 1) {
                typedText = new StringBuilder(String.valueOf(typedText.charAt(typedText.length() - 1)));
                check();
            }
        }
    }

}
