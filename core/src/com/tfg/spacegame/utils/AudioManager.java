package com.tfg.spacegame.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static float volumeMusic = 0.3f;
    private static float volumeEffect = 0.7f;

    private static Music music;
    private static Map<String, Sound> sounds;

    public static void loadSounds() {
        sounds = new HashMap<String, Sound>();
        sounds.put("arcade_shock_effect", AssetsManager.loadSound("arcade_shock_effect"));
        sounds.put("button_backward", AssetsManager.loadSound("button_backward"));
        sounds.put("button_forward", AssetsManager.loadSound("button_forward"));
        sounds.put("new_record", AssetsManager.loadSound("new_record"));
    }

    public static float getVolumeMusic() {
        return music.getVolume();
    }

    public static float getVolumeEffect() {
        return volumeEffect;
    }

    public static void setVolumeMusic(float volumeMusic) {
        AudioManager.volumeMusic = volumeMusic;
        music.setVolume(volumeMusic);
    }

    public static void setVolumeEffect(float volumeEffect) {
        AudioManager.volumeEffect = volumeEffect;
    }

    public static void playMusic(String name, boolean isLooping) {
        music = AssetsManager.loadMusic(name);
        music.setVolume(volumeMusic);
        music.play();
        music.setLooping(isLooping);
    }

    public static void playSound(String name) {
        Sound s = sounds.get(name);
        long id = s.play();

        s.setVolume(id,volumeEffect);
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
