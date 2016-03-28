package com.tfg.spacegame.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private final static float VOLUME = 0.5f;

    private static Music music;
    private static Map<String, Sound> sounds;

    public static void loadSounds() {
        sounds = new HashMap<String, Sound>();
        sounds.put("arcade_shock_effect", AssetsManager.loadSound("arcade_shock_effect"));
        sounds.put("button_backward", AssetsManager.loadSound("button_backward"));
        sounds.put("button_forward", AssetsManager.loadSound("button_forward"));
        sounds.put("new_record", AssetsManager.loadSound("new_record"));
    }

    public static void playMusic(String name, boolean isLooping) {
        music = AssetsManager.loadMusic(name);
        music.setVolume(VOLUME);
        music.play();
        music.setLooping(isLooping);
    }

    public static void playSound(String name) {
        sounds.get(name).play();
    }

    public static void playMusic() {
        if (music != null)
            music.play();
    }

    public static boolean isPlaying() {
        return music.isPlaying();
    }

    public static void pauseMusic() {
        if (music.isPlaying())
            music.pause();
    }

    public static void stopMusic() {
        music.stop();
    }

    public static void dispose() {
        music.dispose();
        sounds.clear();
    }

}
