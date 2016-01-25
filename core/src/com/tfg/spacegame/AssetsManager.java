package com.tfg.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ArrayMap;

public class AssetsManager {

    //Mapa que tendrá la asociación del nombre de las texturas con la imagen concreta de dicha textura
    private static ArrayMap<String, String> assetsReferences;
    private static String particlesFolder = "particleEffects";

    public static void load() {
        assetsReferences = new ArrayMap<String, String>();

        //Assets referentes a las imágenes
        assetsReferences.put("background", "fondo.png");
        assetsReferences.put("ship", "nave.png");
        assetsReferences.put("shoot", "disparo.png");
        assetsReferences.put("enemy", "enemigo.png");
        assetsReferences.put("inventary", "inventario.png");
        assetsReferences.put("red", "rojo.png");
        assetsReferences.put("yellow", "amarillo.png");
        assetsReferences.put("blue", "azul.png");
        assetsReferences.put("button", "button.jpg");
        assetsReferences.put("buttonExit", "button_exit.jpg");
        assetsReferences.put("buttonCancel", "button_cancel.png");
        assetsReferences.put("buttonConfirm", "button_confirm.png");
        assetsReferences.put("ventana", "ventana.png");
        assetsReferences.put("arrow", "flecha.png");
        assetsReferences.put("slot", "slot.png");
        assetsReferences.put("ship_red", "nave_roja.png");
        assetsReferences.put("ship_blue", "nave_azul.png");
        assetsReferences.put("ship_yellow", "nave_amarilla.png");
        assetsReferences.put("ship_green", "nave_verde.png");
        assetsReferences.put("ship_orange", "nave_naranja.png");
        assetsReferences.put("ship_purple", "nave_morada.png");

        //Assets referentes a los efectos de partículas
        assetsReferences.put("red_selected", particlesFolder + "/rojo_seleccionado");
        assetsReferences.put("blue_selected", particlesFolder + "/azul_seleccionado");
        assetsReferences.put("yellow_selected", particlesFolder + "/amarillo_seleccionado");
        assetsReferences.put("red_element", particlesFolder + "/rojo_elemento");
        assetsReferences.put("blue_element", particlesFolder + "/azul_elemento");
        assetsReferences.put("yellow_element", particlesFolder + "/amarillo_elemento");
        assetsReferences.put("red_equipped", particlesFolder + "/rojo_equipado");
        assetsReferences.put("blue_equipped", particlesFolder + "/azul_equipado");
        assetsReferences.put("yellow_equipped", particlesFolder + "/amarillo_equipado");
    }

    //Se llamará a este método cada vez que se pretenda cargar una textura
    public static Texture loadTexture(String textureName) {
        return new Texture(Gdx.files.internal(assetsReferences.get(textureName)));
    }

    public static ParticleEffect loadParticleEffect(String particleName) {
        ParticleEffect particle = new ParticleEffect();
        particle.load(Gdx.files.internal(assetsReferences.get(particleName)), Gdx.files.internal(""));
        return particle;
    }

}
