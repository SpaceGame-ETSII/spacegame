package com.tfg.spacegame.utils;

import com.badlogic.gdx.audio.Music;

public class AudioManager {

    private static Music music;

    public static void playMusic(String name, boolean isLooping) {
        music = AssetsManager.loadMusic(name);
        music.play();
        music.setLooping(isLooping);
    }

    public static void stopMusic() {
        music.stop();
    }

}
