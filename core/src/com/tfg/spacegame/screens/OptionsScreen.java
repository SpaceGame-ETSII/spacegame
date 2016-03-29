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

import java.awt.*;

public class OptionsScreen implements Screen {

    private final SpaceGame game;

    //Variables para almacenar los botones de la pantalla
    private Button music;
    private Button effect;
    private Button back;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public Texture background;

    public OptionsScreen(final SpaceGame game) {
        this.game = game;

        background = AssetsManager.loadTexture("background2");
        AudioManager.playMusic("menu", true);

        //Creamos los botones para la pantalla de opciones
        music = new Button("buttonMusic", 200, 265, null, true);
        effect = new Button("buttonEffect", 260, 265, null, true);
        back = new Button("arrow_back", 750, 430, null, true);

        //Inicializamos el timer de espera para el efecto en los botones
        timeUntilExit = 0.5f;
    }

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpaceGame.camera.update();
        SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

        SpaceGame.batch.begin();

        //Pintamos el fondo y el título de la pantalla
        SpaceGame.batch.draw(background, 0,0);
        FontManager.drawTitle("optionsTitle", 280, 420);

        //Pintamos las opciones para el audio del juego
        FontManager.drawText("audio",100,300);

        //Delegamos el render de los botones
        music.render();
        effect.render();
        back.render();

        SpaceGame.batch.end();

        this.update(delta);
	}

    public void update(float delta) {
        //Si se ha tocado algún botón, lo marcamos como pulsado
        if (Gdx.input.justTouched()) {

            Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            v = SpaceGame.camera.unproject(v);

            if (music.press(v.x, v.y) || effect.press(v.x, v.y) || back.press(v.x, v.y)) {
                //Reiniciamos el contador en caso de haberse pulsado un botón
                timeUntilExit=0.5f;
            }
        }

        //Si el contador es cero, comprobamos si hay algún botón pulsado y actuamos en consecuencia
        if (timeUntilExit <= 0) {
            if (music.isPressed()) {
                System.out.println("Antes de hacer el set: " + AudioManager.getVolumeMusic());
                AudioManager.setVolumeMusic(0.0f);
                game.setScreen(new OptionsScreen(game));
            } else if (effect.isPressed()) {
                AudioManager.setVolumeEffect(0.0f);
                game.setScreen(new OptionsScreen(game));
            } else if (back.isPressed()){
               game.setScreen(new MainMenuScreen(game));
            }
        } else {
            timeUntilExit -= delta;
        }
    }

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
        music.dispose();
        effect.dispose();
        back.dispose();
	}
}
