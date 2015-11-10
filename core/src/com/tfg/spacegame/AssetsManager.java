package com.tfg.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ArrayMap;

public class AssetsManager {

    public static ArrayMap<String, String> assetsReferences;

    public static void load() {
        assetsReferences = new ArrayMap<String, String>();
        assetsReferences.put("background", "fondo.png");
        assetsReferences.put("ship", "nave.png");
        assetsReferences.put("shoot", "disparo.png");
        assetsReferences.put("enemy", "enemigo.png");
    }

    public static Texture loadTexture(String textureName) {
        return new Texture(Gdx.files.internal(assetsReferences.get(textureName)));
    }

}
