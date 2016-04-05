package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.tfg.spacegame.BasicScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AudioManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ScreenManager;

public class MainMenuScreen extends BasicScreen {

    private final SpaceGame game;

    //Representan las opciones a elegir en el Menú
    private Button campaign;
    private Button arcade;
    private Button multiplayer;
    private Button options;
    private Button exit;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public MainMenuScreen(final SpaceGame game) {
        this.game = game;

        //SpaceGame.changeToLandscape();

        AudioManager.loadSounds();
        if (!AudioManager.isPlaying())
            AudioManager.playMusic("menu", true);

        //Creamos los botones para el menú principal
        campaign = new Button("button", 260, 315, "campaignTitle", true);
        arcade = new Button("button", 260, 255, "arcadeTitle", true);
        multiplayer = new Button("button", 260, 195, "multiplayerTitle",true);
        options = new Button("button", 260, 135, "optionsTitle",true);
        exit = new Button("button", 260, 75, "exitTitle",true);

        //Inicializamos el timer de espera para el efecto en los botones
        timeUntilExit = 0.5f;
    }

    @Override
    public void mainRender(float delta) {
        // Pintamos el título del juego
        FontManager.drawTitle("titleGame", 229, 420);

        // Delegamos el render de los botones
        campaign.render();
        arcade.render();
        multiplayer.render();
        options.render();
        exit.render();
    }

    public void update(float delta) {
        campaign.update();
        arcade.update();
        multiplayer.update();
        options.update();
        exit.update();

        //Si se acaba de tocar algún botón, reiniciamos el contador y paramos la música
        if (Gdx.input.justTouched()) {
            if (campaign.isPressed() ||
                    arcade.isPressed() ||
                    multiplayer.isPressed() ||
                    options.isPressed() ||
                    exit.isPressed()) {
                timeUntilExit=0.5f;

                if (!campaign.isPressed())
                    AudioManager.stopMusic();
            }
        }

        //Si el contador es cero, comprobamos si hay algún botón pulsado y actuamos en consecuencia
        if (timeUntilExit <= 0) {
            if (campaign.isPressed()) {
                ScreenManager.changeScreen(game,DemoMenuScreen.class);
            } else if (arcade.isPressed()) {
                ScreenManager.changeScreen(game,ArcadeScreen.class);
            } else if (multiplayer.isPressed()) {
                ScreenManager.changeScreen(game,MultiplayerMenuScreen.class);
            } else if (options.isPressed()) {
                ScreenManager.changeScreen(game,OptionsScreen.class);
            } else if (exit.isPressed()) {
                Gdx.app.exit();
            }
        } else {
            timeUntilExit -= delta;
        }
    }

	@Override
	public void dispose() {
        campaign.dispose();
        arcade.dispose();
        multiplayer.dispose();
        options.dispose();
        exit.dispose();
	}

}
