package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class AssetsManager {

    //Mapa que tendrá la asociación del nombre de las texturas con la imagen concreta de dicha textura
    private static ArrayMap<String, String> assetsReferences;

    private static String particlesFolder = "particleEffects/";
    private static String enemiesFolder = "textures/enemies/";
    private static String weaponsFolder = "textures/weapons/";
    private static String levelScriptsFolder = "levelScripts/";
    private static String shipsFolder = "textures/ships/";
    private static String othersFolder = "textures/others/";
    private static String settingsFolder = "settings/";

    public static void load() {
        assetsReferences = new ArrayMap<String, String>();

        //Texturas referentes a enemies
        assetsReferences.put("tipo3", enemiesFolder+"tipo3.png");
        assetsReferences.put("type4", enemiesFolder+"tipo4.png");
        assetsReferences.put("type4_shield", enemiesFolder+"tipo4_shield.png");
        assetsReferences.put("type4_body", enemiesFolder+"tipo4_body.png");
        assetsReferences.put("enemigo_basico_tipo5", enemiesFolder+"enemigo_basico_tipo5.png");
        assetsReferences.put("enemy", enemiesFolder+"enemigo.png");
        assetsReferences.put("yellow_enemy", enemiesFolder+"amarillo.png");
        assetsReferences.put("red_enemy", enemiesFolder+"rojo.png");
        assetsReferences.put("blue_enemy", enemiesFolder+"azul.png");
        assetsReferences.put("orange_enemy", enemiesFolder+"enemigo_naranja_canon_principal.png");
        assetsReferences.put("orange_enemy_body", enemiesFolder+"enemigo_naranja_cuerpo.png");
        assetsReferences.put("orange_enemy_shield", enemiesFolder+"enemigo_naranja_escudo.png");
        assetsReferences.put("orange_enemy_cannon", enemiesFolder+"enemigo_naranja_canon_secundario.png");
        assetsReferences.put("green_body", enemiesFolder+"verde_body.png");
        assetsReferences.put("green_shield", enemiesFolder+"verde_shield.png");

        //Texturas referentes a others
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

        //Texturas referentes a ships
        assetsReferences.put("ship", shipsFolder+"nave.png");
        assetsReferences.put("ship_red", shipsFolder+"nave_roja.png");
        assetsReferences.put("ship_blue", shipsFolder+"nave_azul.png");
        assetsReferences.put("ship_yellow", shipsFolder+"nave_amarilla.png");
        assetsReferences.put("ship_green", shipsFolder+"nave_verde.png");
        assetsReferences.put("ship_orange", shipsFolder+"nave_naranja.png");
        assetsReferences.put("ship_purple", shipsFolder+"nave_morada.png");
        assetsReferences.put("cockpit", shipsFolder+"cabina.png");
        assetsReferences.put("cockpit_damage1", shipsFolder+"cabina_dano1.png");
        assetsReferences.put("cockpit_damage2", shipsFolder+"cabina_dano2.png");
        assetsReferences.put("cockpit_damage3", shipsFolder+"cabina_dano3.png");
        assetsReferences.put("cockpit_damage4", shipsFolder+"cabina_dano4.png");

        //Texturas referentes a weapons
        assetsReferences.put("basic_shoot", weaponsFolder+"disparo.png");
        assetsReferences.put("bigshoot_shoot", weaponsFolder+"disparo_enemigo_basico_tipo5.png");
        assetsReferences.put("red_shoot", weaponsFolder+"disparo_rojo.png");
        assetsReferences.put("blue_shoot", weaponsFolder+"disparo_azul.png");
        assetsReferences.put("yellow_shoot", weaponsFolder+"disparo_amarillo.png");
        assetsReferences.put("purple_shoot", weaponsFolder+"disparo_morado.png");
        assetsReferences.put("orange_shoot", weaponsFolder+"disparo_naranja.png");
        assetsReferences.put("green_shoot", weaponsFolder+"disparo_verde.png");

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
        assetsReferences.put("bigshoot_shoot_effect", particlesFolder + "basico_grande_efecto");
        assetsReferences.put("warning_shoot_type5_effect", particlesFolder + "aviso_disparo_tipo5");
        assetsReferences.put("basic_effect_shoot", particlesFolder + "basico_efecto_disparo");
        assetsReferences.put("basic_effect_shock", particlesFolder + "basico_efecto_choque");
        assetsReferences.put("basic_shoot_effect", particlesFolder + "basico_efecto");
        assetsReferences.put("propulsion_ship_effect", particlesFolder + "propulsion_nave");
        assetsReferences.put("basic_destroyed", particlesFolder + "basico_derrotado");
        assetsReferences.put("basic_type5_destroyed", particlesFolder + "basico_tipo5_derrotado");
        assetsReferences.put("red_shoot_effect", particlesFolder + "arma_roja");
        assetsReferences.put("blue_propulsion_effect", particlesFolder + "propulsion_arma_azul");
        assetsReferences.put("blue_shoot_effect_shock", particlesFolder + "arma_azul_efecto_choque");
        assetsReferences.put("blue_shoot_effect_shoot", particlesFolder + "arma_azul_efecto_disparo");
        assetsReferences.put("red_shoot_effect_shoot", particlesFolder + "arma_roja_efecto_disparo");
        assetsReferences.put("red_effect_shock", particlesFolder + "arma_roja_efecto_choque");
        assetsReferences.put("green_propulsion_effect", particlesFolder + "propulsion_arma_verde");
        assetsReferences.put("green_shoot_effect_shock", particlesFolder + "arma_verde_efecto_choque");
        assetsReferences.put("green_shoot_effect_shoot", particlesFolder + "arma_verde_efecto_disparo");
        assetsReferences.put("green_shoot_effect", particlesFolder + "fuego_verde");
        assetsReferences.put("red_destroyed", particlesFolder + "rojo_derrotado");
        assetsReferences.put("blue_destroyed", particlesFolder + "azul_derrotado");
        assetsReferences.put("green_destroyed", particlesFolder + "verde_derrotado");
        assetsReferences.put("yellow_shoot_effect", particlesFolder + "arma_amarilla");
        assetsReferences.put("yellow_enemy_defeated", particlesFolder + "enemigo_amarillo_derrotado");
        assetsReferences.put("purple_shoot_effect", particlesFolder + "arma_morada");
        assetsReferences.put("purple_shoot_effect_shoot", particlesFolder + "arma_morada_efecto_disparo");
        assetsReferences.put("purple_effect_shock", particlesFolder + "arma_morada_efecto_choque");
        assetsReferences.put("orange_shoot_effect", particlesFolder + "arma_naranja");
        assetsReferences.put("orange_shoot_effect", particlesFolder + "arma_naranja");
        assetsReferences.put("orange_shoot_effect_shock", particlesFolder + "arma_naranja_efecto_choque");
        assetsReferences.put("orange_shoot_effect_shoot", particlesFolder + "arma_naranja_efecto_disparo");

        //Assets referentes a los scripts de niveles
        assetsReferences.put("allEnemies", levelScriptsFolder + "allEnemies");
        assetsReferences.put("colorEnemies", levelScriptsFolder + "colorEnemies");
        assetsReferences.put("greenEnemy", levelScriptsFolder + "greenEnemy");
        assetsReferences.put("orangeEnemy", levelScriptsFolder + "orangeEnemy");
        assetsReferences.put("purpleEnemy", levelScriptsFolder + "purpleEnemy");

        //Asset referente a la localización
        assetsReferences.put("bundle","localization/bundle");

        //Assets referentes a los archivos de configuracón
        assetsReferences.put("shapeEntities", settingsFolder + "shapeEntities");
    }

    //Se llamará a este método cada vez que se pretenda cargar una textura
    public static Texture loadTexture(String textureName) {
        return new Texture(Gdx.files.internal(assetsReferences.get(textureName)));
    }

    //Se llamará a este método cada vez que se pretenda cargar un efecto de partículas
    public static ParticleEffect loadParticleEffect(String particleName) {
        ParticleEffect particle = new ParticleEffect();
        particle.load(Gdx.files.internal(assetsReferences.get(particleName)), Gdx.files.internal(""));
        return particle;
    }

    //Se llamará a este método cada vez que se pretenda cargar script del juego
    public static FileHandle loadScript(String scriptName){
        return Gdx.files.internal(assetsReferences.get(scriptName));
    }


    //Se llamará a este método cuando se vaya a cargar el bundle
    public static I18NBundle loadBundle(){
        return I18NBundle.createBundle(Gdx.files.internal(assetsReferences.get("bundle")), Locale.getDefault());
    }

    public static FileHandle loadSetting(String settingsName){
        return Gdx.files.internal(assetsReferences.get(settingsName));
    }

}
