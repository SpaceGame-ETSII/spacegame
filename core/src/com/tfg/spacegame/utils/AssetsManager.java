package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ArrayMap;

public class AssetsManager {

    //Mapa que tendrá la asociación del nombre de las texturas con la imagen concreta de dicha textura
    private static ArrayMap<String, String> assetsReferences;

    private static String particlesFolder = "particleEffects/";
    private static String enemiesFolder = "textures/enemies/";
    private static String weaponsFolder = "textures/weapons/";
    private static String levelScriptsFolder = "levelscripts/";
    private static String shipsFolder = "textures/ships/";
    private static String othersFolder = "textures/others/";


    public static void load() {
        assetsReferences = new ArrayMap<String, String>();

        //Assets referentes a las imágenes

        assetsReferences.put("type4", enemiesFolder+"tipo4.png");
        assetsReferences.put("type4_shield", enemiesFolder+"tipo4_shield.png");
        assetsReferences.put("enemy", enemiesFolder+"enemigo.png");

        assetsReferences.put("background", othersFolder+"fondo.png");
        assetsReferences.put("inventary", othersFolder+"inventario.png");
        assetsReferences.put("red", othersFolder+"rojo.png");
        assetsReferences.put("yellow", othersFolder+"amarillo.png");
        assetsReferences.put("blue", othersFolder+"azul.png");
        assetsReferences.put("button", othersFolder+"button.jpg");
        assetsReferences.put("buttonExit", othersFolder+"button_exit.jpg");
        assetsReferences.put("buttonCancel", othersFolder+"button_cancel.png");
        assetsReferences.put("buttonConfirm", othersFolder+"button_confirm.png");
        assetsReferences.put("ventana", othersFolder+"ventana.png");
        assetsReferences.put("arrow", othersFolder+"flecha.png");
        assetsReferences.put("slot", othersFolder+"slot.png");

        assetsReferences.put("ship", shipsFolder+"nave.png");
        assetsReferences.put("ship_red", shipsFolder+"nave_roja.png");
        assetsReferences.put("ship_blue", shipsFolder+"nave_azul.png");
        assetsReferences.put("ship_yellow", shipsFolder+"nave_amarilla.png");
        assetsReferences.put("ship_green", shipsFolder+"nave_verde.png");
        assetsReferences.put("ship_orange", shipsFolder+"nave_naranja.png");
        assetsReferences.put("ship_purple", shipsFolder+"nave_morada.png");

        assetsReferences.put("shoot", weaponsFolder+"disparo.png");

        //Assets referentes a los efectos de partículas
        assetsReferences.put("red_selected", particlesFolder + "rojo_seleccionado");
        assetsReferences.put("blue_selected", particlesFolder + "azul_seleccionado");
        assetsReferences.put("yellow_selected", particlesFolder + "amarillo_seleccionado");
        assetsReferences.put("red_element", particlesFolder + "rojo_elemento");
        assetsReferences.put("blue_element", particlesFolder + "azul_elemento");
        assetsReferences.put("yellow_element", particlesFolder + "amarillo_elemento");
        assetsReferences.put("red_equipped", particlesFolder + "rojo_equipado");
        assetsReferences.put("blue_equipped", particlesFolder + "azul_equipado");
        assetsReferences.put("yellow_equipped", particlesFolder + "amarillo_equipado");
        assetsReferences.put("shootEffect", particlesFolder + "shootEffect");

        assetsReferences.put("scriptTest", levelScriptsFolder + "scriptTest");
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

    public static FileHandle loadScript(String scriptName){
        return Gdx.files.internal(assetsReferences.get(scriptName));
    }

}
