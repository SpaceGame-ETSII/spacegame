package com.tfg.spacegame.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {

    private static Music music;
    private static Music sound;

    public static void playMusic(String name, boolean isLooping) {
        music = AssetsManager.loadMusic(name);
        music.play();
        music.setLooping(isLooping);
    }

    public static void playSound(String name) {
        sound = AssetsManager.loadSound(name);
        sound.play();
    }

    public static void playMusic() {
        if (music != null)
            music.play();
    }

    public static void pauseMusic() {
        if (music.isPlaying())
            music.pause();
    }

    public static void stopMusic() {
        music.stop();
    }

}
