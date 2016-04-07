package com.tfg.spacegame.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.tfg.spacegame.screens.ArcadeScreen;
import com.tfg.spacegame.screens.CampaignScreen;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.enums.GameState;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private static float volumeMusic = 0.3f;
    private static float volumeEffect = 0.7f;

    private static Music music;
    private static Map<String, Sound> sounds;

    private static boolean isLoaded = false;
    private static String currentMusic = "";

    public static void loadSounds() {
        sounds = new HashMap<String, Sound>();

        sounds.put("arcade_shock_effect", AssetsManager.loadSound("arcade_shock_effect"));
        sounds.put("button_backward", AssetsManager.loadSound("button_backward"));
        sounds.put("button_forward", AssetsManager.loadSound("button_forward"));
        sounds.put("inventary", AssetsManager.loadSound("inventary"));
        sounds.put("new_record", AssetsManager.loadSound("new_record"));
        sounds.put("pause", AssetsManager.loadSound("pause"));

        isLoaded = true;
    }

    public static void update() {
        String newMusic = "no_music";

        if (ScreenManager.isCurrentScreenEqualsTo(ArcadeScreen.class)) {
            if (ScreenManager.isCurrentStateEqualsTo(GameState.READY)) {
                newMusic = "arcade";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.START)) {
                newMusic = "arcade";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.PAUSE)) {
                newMusic = "pause";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.WIN)) {

            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.LOSE)) {

            }
        } else if (ScreenManager.isCurrentScreenEqualsTo(CampaignScreen.class)) {
            if (ScreenManager.isCurrentStateEqualsTo(GameState.READY)) {
                newMusic = "campaign";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.START)) {
                newMusic = "campaign";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.PAUSE)) {
                newMusic = "pause";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.WIN)) {
                newMusic = "campaign_win";
            } else if (ScreenManager.isCurrentStateEqualsTo(GameState.LOSE)) {

            }
        } else if (ScreenManager.isCurrentScreenEqualsTo(MultiplayerScreen.class)) {

        } else {
            newMusic = "menu";
        }

        if (newMusic.equals("pause")) {
            pauseMusic();
        } else if (newMusic.equals("no_music")) {
            stopMusic();
        } else if (newMusic.equals(currentMusic)) {
            playMusic();
        } else if (!newMusic.equals(currentMusic)) {
            AudioManager.playMusic(newMusic, true);
            currentMusic = newMusic;
        }
    }

    public static float getVolumeMusic() {
        return music.getVolume();
    }

    public static float getVolumeEffect() {
        return volumeEffect;
    }

    public static void setVolumeMusic(float volumeMusic) {
        AudioManager.volumeMusic = volumeMusic;
        if (music != null)
            music.setVolume(volumeMusic);
    }

    public static void setVolumeEffect(float volumeEffect) {
        AudioManager.volumeEffect = volumeEffect;
    }

    public static void playSound(String name) {
        if (!isLoaded)
            loadSounds();

        Sound sound = sounds.get(name);
        long id = sound.play();

        sound.setVolume(id, volumeEffect);
    }

    private static void playMusic(String name, boolean isLooping) {
        if (music != null)
            stopMusic();

        music = AssetsManager.loadMusic(name);

        music.setVolume(volumeMusic);
        music.play();
        music.setLooping(isLooping);
    }

    private static void playMusic() {
        if (music != null && !isPlaying())
            music.play();
    }

    private static boolean isPlaying() {
        boolean res = false;
        if (music != null)
            res = music.isPlaying();
        return res;
    }

    public static void pauseMusic() {
        if (music.isPlaying())
            music.pause();
    }

    private static void stopMusic() {
        if (isPlaying())
            music.stop();
    }

    public static void dispose() {
        music.dispose();
        sounds.clear();
    }

}
