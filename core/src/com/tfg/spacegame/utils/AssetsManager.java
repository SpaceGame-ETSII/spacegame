package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

public class AssetsManager {

    //Mapa que tendrá la asociación del nombre de las texturas con la imagen concreta de dicha textura
    private static ArrayMap<String, String> assetsReferences;

    private final static String PARTICLES_FOLDER = "particleEffects/";
    private final static String ENEMIES_FOLDER = "textures/enemies/";
    private final static String OBSTACLES_FOLDER = "textures/obstacles/";
    private final static String WEAPONS_FOLDER = "textures/shoots/";
    private final static String LEVEL_SCRIPTS_FOLDER = "levelScripts/";
    private final static String SHIPS_FOLDER = "textures/ships/";
    private final static String BACKGROUNDS_FOLDER = "textures/backgrounds/";
    private final static String OTHERS_FOLDER = "textures/others/";
    private final static String POWER_UPS_FOLDER = "textures/powerUps/";
    private final static String MUSICS_FOLDER = "audio/musics/";
    private final static String SOUNDS_FOLDER = "audio/sounds/";
    private final static String SETTINGS_FOLDER = "settings/";

    public static void load() {
        assetsReferences = new ArrayMap<String, String>();

        //Texturas referentes a enemies
        assetsReferences.put("tipo3", ENEMIES_FOLDER +"tipo3.png");
        assetsReferences.put("type4", ENEMIES_FOLDER +"tipo4.png");
        assetsReferences.put("type4_shield", ENEMIES_FOLDER +"tipo4_shield.png");
        assetsReferences.put("type4_body", ENEMIES_FOLDER +"tipo4_body.png");
        assetsReferences.put("enemigo_basico_tipo5", ENEMIES_FOLDER +"enemigo_basico_tipo5.png");
        assetsReferences.put("enemy", ENEMIES_FOLDER +"enemigo.png");
        assetsReferences.put("yellow_enemy", ENEMIES_FOLDER +"amarillo.png");
        assetsReferences.put("red_enemy", ENEMIES_FOLDER +"rojo.png");
        assetsReferences.put("blue_enemy", ENEMIES_FOLDER +"azul.png");
        assetsReferences.put("orange_enemy", ENEMIES_FOLDER +"enemigo_naranja_canon_principal.png");
        assetsReferences.put("orange_enemy_body", ENEMIES_FOLDER +"enemigo_naranja_cuerpo.png");
        assetsReferences.put("orange_enemy_body_aux_up", ENEMIES_FOLDER +"enemigo_naranja_cuerpo_aux_superior.png");
        assetsReferences.put("orange_enemy_body_aux_bottom", ENEMIES_FOLDER +"enemigo_naranja_cuerpo_aux_inferior.png");
        assetsReferences.put("orange_enemy_shield", ENEMIES_FOLDER +"enemigo_naranja_escudo.png");
        assetsReferences.put("orange_enemy_cannon", ENEMIES_FOLDER +"enemigo_naranja_canon_secundario.png");
        assetsReferences.put("green_body", ENEMIES_FOLDER +"verde_body.png");
        assetsReferences.put("green_shield", ENEMIES_FOLDER +"verde_shield.png");
        assetsReferences.put("purple_body", ENEMIES_FOLDER +"enemigo_morado_cuerpo.png");
        assetsReferences.put("purple_eye_1", ENEMIES_FOLDER +"enemigo_morado_ojo1.png");
        assetsReferences.put("purple_eye_2", ENEMIES_FOLDER +"enemigo_morado_ojo2.png");
        assetsReferences.put("purple_eye_3", ENEMIES_FOLDER +"enemigo_morado_ojo3.png");
        assetsReferences.put("purple_eye_4", ENEMIES_FOLDER +"enemigo_morado_ojo4.png");
        assetsReferences.put("purple_eye_center", ENEMIES_FOLDER +"enemigo_morado_centro.png");

        //Texturas referentes a obstacles
        assetsReferences.put("big_obstacle", OBSTACLES_FOLDER +"meteorito_grande.png");
        assetsReferences.put("medium_obstacle", OBSTACLES_FOLDER +"meteorito_mediano.png");
        assetsReferences.put("small_obstacle", OBSTACLES_FOLDER +"meteorito_pequeno.png");

        //Texturas referentes a backgrounds
        assetsReferences.put("background", BACKGROUNDS_FOLDER +"fondo.jpg");
        assetsReferences.put("background2", BACKGROUNDS_FOLDER +"fondo2.png");
        assetsReferences.put("background3", BACKGROUNDS_FOLDER +"fondo3.png");

        //Texturas referentes a others
        assetsReferences.put("inventary", OTHERS_FOLDER +"inventario.png");
        assetsReferences.put("red", OTHERS_FOLDER +"rojo.png");
        assetsReferences.put("yellow", OTHERS_FOLDER +"amarillo.png");
        assetsReferences.put("blue", OTHERS_FOLDER +"azul.png");
        assetsReferences.put("button", OTHERS_FOLDER +"button.png");
        assetsReferences.put("buttonExit", OTHERS_FOLDER +"button_exit.jpg");
        assetsReferences.put("buttonCancel", OTHERS_FOLDER +"button_cancel.png");
        assetsReferences.put("buttonConfirm", OTHERS_FOLDER +"button_confirm.png");
        assetsReferences.put("ventana", OTHERS_FOLDER +"ventana.png");
        assetsReferences.put("arrow", OTHERS_FOLDER +"flecha.png");
        assetsReferences.put("slot", OTHERS_FOLDER +"slot.png");
        assetsReferences.put("arrow_back", OTHERS_FOLDER +"flecha_atras.png");
        assetsReferences.put("buttonMusic", OTHERS_FOLDER +"boton_musica.png");
        assetsReferences.put("buttonMusicCancel", OTHERS_FOLDER +"boton_musica_cancelado.png");
        assetsReferences.put("buttonEffect", OTHERS_FOLDER +"boton_efecto.png");
        assetsReferences.put("buttonEffectCancel", OTHERS_FOLDER +"boton_efecto_cancelado.png");

        //Texturas referentes a ships
        assetsReferences.put("ship", SHIPS_FOLDER +"nave.png");
        assetsReferences.put("arcadeShip", SHIPS_FOLDER +"nave_arcade.png");
        assetsReferences.put("ship_red", SHIPS_FOLDER +"nave_roja.png");
        assetsReferences.put("ship_blue", SHIPS_FOLDER +"nave_azul.png");
        assetsReferences.put("ship_yellow", SHIPS_FOLDER +"nave_amarilla.png");
        assetsReferences.put("ship_green", SHIPS_FOLDER +"nave_verde.png");
        assetsReferences.put("ship_orange", SHIPS_FOLDER +"nave_naranja.png");
        assetsReferences.put("ship_purple", SHIPS_FOLDER +"nave_morada.png");
        assetsReferences.put("cockpit", SHIPS_FOLDER +"cabina.png");
        assetsReferences.put("cockpit_damage1", SHIPS_FOLDER +"cabina_dano1.png");
        assetsReferences.put("cockpit_damage2", SHIPS_FOLDER +"cabina_dano2.png");
        assetsReferences.put("cockpit_damage3", SHIPS_FOLDER +"cabina_dano3.png");
        assetsReferences.put("cockpit_damage4", SHIPS_FOLDER +"cabina_dano4.png");
        assetsReferences.put("playerShip", SHIPS_FOLDER +"nave_multi_p1.png");
        assetsReferences.put("rivalShip", SHIPS_FOLDER +"nave_multi_p2.png");

        // Texturas referentes a powerUps
        assetsReferences.put("shieldEnemy", POWER_UPS_FOLDER +"shieldEnemy.png");
        assetsReferences.put("shieldPlayer", POWER_UPS_FOLDER +"shieldPlayer.png");
        assetsReferences.put("regLifeEnemy", POWER_UPS_FOLDER +"lifeEnemy.png");
        assetsReferences.put("regLifePlayer", POWER_UPS_FOLDER +"lifePlayer.png");
        assetsReferences.put("burstEnemy", POWER_UPS_FOLDER +"burstEnemy.png");
        assetsReferences.put("burstPlayer", POWER_UPS_FOLDER +"burstPlayer.png");

        //Texturas referentes a weapons
        assetsReferences.put("basic_shoot", WEAPONS_FOLDER +"disparo.png");
        assetsReferences.put("bigshoot_shoot", WEAPONS_FOLDER +"disparo_enemigo_basico_tipo5.png");
        assetsReferences.put("red_shoot", WEAPONS_FOLDER +"disparo_rojo.png");
        assetsReferences.put("blue_shoot", WEAPONS_FOLDER +"disparo_azul.png");
        assetsReferences.put("yellow_shoot", WEAPONS_FOLDER +"disparo_amarillo.png");
        assetsReferences.put("purple_shoot", WEAPONS_FOLDER +"disparo_morado.png");
        assetsReferences.put("orange_shoot", WEAPONS_FOLDER +"disparo_naranja.png");
        assetsReferences.put("green_shoot", WEAPONS_FOLDER +"disparo_verde.png");

        //Assets referentes a los efectos de partículas
        assetsReferences.put("red_selected", PARTICLES_FOLDER + "rojo_seleccionado");
        assetsReferences.put("blue_selected", PARTICLES_FOLDER + "azul_seleccionado");
        assetsReferences.put("yellow_selected", PARTICLES_FOLDER + "amarillo_seleccionado");
        assetsReferences.put("red_element", PARTICLES_FOLDER + "rojo_elemento");
        assetsReferences.put("blue_element", PARTICLES_FOLDER + "azul_elemento");
        assetsReferences.put("yellow_element", PARTICLES_FOLDER + "amarillo_elemento");
        assetsReferences.put("red_equipped", PARTICLES_FOLDER + "rojo_equipado");
        assetsReferences.put("blue_equipped", PARTICLES_FOLDER + "azul_equipado");
        assetsReferences.put("yellow_equipped", PARTICLES_FOLDER + "amarillo_equipado");
        assetsReferences.put("bigshoot_shoot_effect", PARTICLES_FOLDER + "basico_grande_efecto");
        assetsReferences.put("warning_shoot_type5_effect", PARTICLES_FOLDER + "aviso_disparo_tipo5");
        assetsReferences.put("basic_effect_shoot", PARTICLES_FOLDER + "basico_efecto_disparo");
        assetsReferences.put("basic_effect_shock", PARTICLES_FOLDER + "basico_efecto_choque");
        assetsReferences.put("basic_shoot_effect", PARTICLES_FOLDER + "basico_efecto");
        assetsReferences.put("propulsion_ship_effect", PARTICLES_FOLDER + "propulsion_nave");
        assetsReferences.put("basic_destroyed", PARTICLES_FOLDER + "basico_derrotado");
        assetsReferences.put("basic_type5_destroyed", PARTICLES_FOLDER + "basico_tipo5_derrotado");
        assetsReferences.put("red_shoot_effect", PARTICLES_FOLDER + "arma_roja");
        assetsReferences.put("blue_propulsion_effect", PARTICLES_FOLDER + "propulsion_arma_azul");
        assetsReferences.put("blue_shoot_effect_shock", PARTICLES_FOLDER + "arma_azul_efecto_choque");
        assetsReferences.put("blue_shoot_effect_shoot", PARTICLES_FOLDER + "arma_azul_efecto_disparo");
        assetsReferences.put("red_shoot_effect_shoot", PARTICLES_FOLDER + "arma_roja_efecto_disparo");
        assetsReferences.put("red_effect_shock", PARTICLES_FOLDER + "arma_roja_efecto_choque");
        assetsReferences.put("green_propulsion_effect", PARTICLES_FOLDER + "propulsion_arma_verde");
        assetsReferences.put("green_shoot_effect_shock", PARTICLES_FOLDER + "arma_verde_efecto_choque");
        assetsReferences.put("green_shoot_effect_shoot", PARTICLES_FOLDER + "arma_verde_efecto_disparo");
        assetsReferences.put("green_shoot_effect", PARTICLES_FOLDER + "fuego_verde");
        assetsReferences.put("red_destroyed", PARTICLES_FOLDER + "rojo_derrotado");
        assetsReferences.put("blue_destroyed", PARTICLES_FOLDER + "azul_derrotado");
        assetsReferences.put("green_destroyed", PARTICLES_FOLDER + "verde_derrotado");
        assetsReferences.put("yellow_shoot_effect", PARTICLES_FOLDER + "arma_amarilla");
        assetsReferences.put("yellow_enemy_defeated", PARTICLES_FOLDER + "enemigo_amarillo_derrotado");
        assetsReferences.put("purple_shoot_effect", PARTICLES_FOLDER + "arma_morada");
        assetsReferences.put("purple_shoot_effect_shoot", PARTICLES_FOLDER + "arma_morada_efecto_disparo");
        assetsReferences.put("purple_effect_shock", PARTICLES_FOLDER + "arma_morada_efecto_choque");
        assetsReferences.put("orange_shoot_effect", PARTICLES_FOLDER + "arma_naranja");
        assetsReferences.put("orange_shoot_effect_shock", PARTICLES_FOLDER + "arma_naranja_efecto_choque");
        assetsReferences.put("orange_shoot_effect_shoot", PARTICLES_FOLDER + "arma_naranja_efecto_disparo");
        assetsReferences.put("purple_destroyed", PARTICLES_FOLDER + "morado_derrotado");
        assetsReferences.put("purple_eye_warning", PARTICLES_FOLDER + "morado_ojo_aviso");
        assetsReferences.put("orange_enemy_defeated", PARTICLES_FOLDER + "enemigo_naranja_derrotado");
        assetsReferences.put("orange_main_cannon_charging", PARTICLES_FOLDER + "enemigo_naranja_efecto_cannon_principal");
        assetsReferences.put("orange_secondary_cannon_disabled", PARTICLES_FOLDER + "enemigo_naranja_cannon_secundario_inhabilitado");
        assetsReferences.put("ship_defeated", PARTICLES_FOLDER + "nave_derrotada");
        assetsReferences.put("ship_shock_effect", PARTICLES_FOLDER + "nave_arcade_choque");

        //Assets referentes a la música
        assetsReferences.put("music/menu", MUSICS_FOLDER + "menu.mp3");
        assetsReferences.put("music/campaign", MUSICS_FOLDER + "campaign.mp3");
        assetsReferences.put("music/arcade", MUSICS_FOLDER + "arcade.mp3");
        assetsReferences.put("music/campaign_win", MUSICS_FOLDER + "campaign_win.wav");

        //Assets referentes a sonidos
        assetsReferences.put("sound/arcade_shock_effect", SOUNDS_FOLDER + "arcade_shock_effect.wav");
        assetsReferences.put("sound/button_forward", SOUNDS_FOLDER + "button_forward.wav");
        assetsReferences.put("sound/button_backward", SOUNDS_FOLDER + "button_backward.wav");
        assetsReferences.put("sound/inventary", SOUNDS_FOLDER + "inventary.wav");
        assetsReferences.put("sound/new_record", SOUNDS_FOLDER + "new_record.wav");
        assetsReferences.put("sound/pause", SOUNDS_FOLDER + "pause.wav");

        //Assets referentes a los scripts de niveles
        assetsReferences.put("allEnemies", LEVEL_SCRIPTS_FOLDER + "allEnemies");
        assetsReferences.put("colorEnemies", LEVEL_SCRIPTS_FOLDER + "colorEnemies");
        assetsReferences.put("greenEnemy", LEVEL_SCRIPTS_FOLDER + "greenEnemy");
        assetsReferences.put("orangeEnemy", LEVEL_SCRIPTS_FOLDER + "orangeEnemy");
        assetsReferences.put("purpleEnemy", LEVEL_SCRIPTS_FOLDER + "purpleEnemy");

        //Asset referente a la localización
        assetsReferences.put("bundle","localization/bundle");

        //Assets referentes a los archivos de configuracón
        assetsReferences.put("shapeEntities", SETTINGS_FOLDER + "shapeEntities");
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

    public static Music loadMusic(String musicName) {
        return Gdx.audio.newMusic(Gdx.files.internal(assetsReferences.get("music/" + musicName)));
    }

    public static Sound loadSound(String soundName) {
        return Gdx.audio.newSound(Gdx.files.internal(assetsReferences.get("sound/" + soundName)));
    }

    //Se llamará a este método cada vez que se pretenda cargar archivo de texto plano del juego
    public static FileHandle loadFile(String scriptName){
        return Gdx.files.internal(assetsReferences.get(scriptName));
    }

    //Se llamará a este método cuando se vaya a cargar el bundle que nos servirá para la localización
    public static I18NBundle loadBundle(){
        return I18NBundle.createBundle(Gdx.files.internal(assetsReferences.get("bundle")), Locale.getDefault());
    }

}
