package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.AudioManager;
import com.tfg.spacegame.utils.FontManager;

public class MainMenuScreen implements Screen {

    private final SpaceGame game;

    //Representan las opciones a elegir en el Menú
    private Button campaign;
    private Button arcade;
    private Button multiplayer;
    private Button options;
    private Button exit;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public Texture background;

    public MainMenuScreen(final SpaceGame game) {
        this.game = game;

        SpaceGame.changeToLandscape();

        background = AssetsManager.loadTexture("background2");
        AudioManager.loadSounds();
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
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpaceGame.camera.update();
        SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

        SpaceGame.batch.begin();

        // Pintamos el fondo y el título del juego
        SpaceGame.batch.draw(background, 0,0);
        FontManager.drawTitle("titleGame", 229, 420);

        // Delegamos el render de los botones
        campaign.render();
        arcade.render();
        multiplayer.render();
        options.render();
        exit.render();

        SpaceGame.batch.end();

        this.update(delta);
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
                AudioManager.stopMusic();
            }
        }

        //Si el contador es cero, comprobamos si hay algún botón pulsado y actuamos en consecuencia
        if (timeUntilExit <= 0) {
            if (campaign.isPressed()) {
                game.setScreen(new DemoMenuScreen(game));
            } else if (arcade.isPressed()) {
                game.setScreen(new ArcadeScreen(game));
            } else if (multiplayer.isPressed()) {
                game.setScreen(new MultiplayerMenuScreen(game));
            } else if (options.isPressed()) {
                game.setScreen(new OptionsScreen(game));
            } else if (exit.isPressed()) {
                Gdx.app.exit();
            }
        } else {
            timeUntilExit -= delta;
        }
    }

    @Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
