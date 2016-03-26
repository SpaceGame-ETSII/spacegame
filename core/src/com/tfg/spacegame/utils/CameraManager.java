package com.tfg.spacegame.utils;


public class CameraManager {

    private static ShakeEffect shakeEffect;

    public static void loadShakeEffect(float duration){
        shakeEffect = new ShakeEffect(duration,ShakeEffect.NORMAL_SHAKE);
    }

    public static void updateShakeEffect(float delta){
        shakeEffect.shake(delta);
    }

    public static void startShakeEffect(){
        shakeEffect.start();
    }
}
