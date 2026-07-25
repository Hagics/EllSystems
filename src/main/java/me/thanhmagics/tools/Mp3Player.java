package me.thanhmagics.tools;

import javazoom.jl.player.Player;

import javax.sound.sampled.*;
import java.io.File;
import java.io.FileInputStream;

public class Mp3Player {
    public static void main(String[] args) throws Exception {
        playAsync("accidental");
    }

    public static void playAsync(String word) {
        new Thread(() -> playSync(word)).start();
    }

    public static void playSync(String word) {
        try (FileInputStream fis = new FileInputStream("src\\main\\resources\\speech\\" + word + ".mp3")) {
            Player player = new Player(fis);
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
