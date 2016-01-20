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
        assetsReferences.put("inventary", "inventario.png");
        assetsReferences.put("red", "rojo.png");
        assetsReferences.put("yellow", "amarillo.png");
        assetsReferences.put("blue", "azul.png");
        assetsReferences.put("arrow", "flecha.png");
        assetsReferences.put("slot", "slot.png");
        assetsReferences.put("ship_red", "nave_roja.png");
        assetsReferences.put("ship_blue", "nave_azul.png");
        assetsReferences.put("ship_yellow", "nave_amarilla.png");
        assetsReferences.put("ship_green", "nave_verde.png");
        assetsReferences.put("ship_orange", "nave_naranja.png");
        assetsReferences.put("ship_purple", "nave_morada.png");

        assetsReferences.put("red_selected", "particleEffects/rojo_seleccionado");
        assetsReferences.put("blue_selected", "particleEffects/azul_seleccionado");
        assetsReferences.put("yellow_selected", "particleEffects/amarillo_seleccionado");
        assetsReferences.put("red_element", "particleEffects/rojo_elemento");
        assetsReferences.put("blue_element", "particleEffects/azul_elemento");
        assetsReferences.put("yellow_element", "particleEffects/amarillo_elemento");
    }

    public static Texture loadTexture(String textureName) {
        return new Texture(Gdx.files.internal(assetsReferences.get(textureName)));
    }

}
