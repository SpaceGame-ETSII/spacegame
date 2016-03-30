package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.gameObjects.OptionButton;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.AudioManager;
import com.tfg.spacegame.utils.DialogBox;
import com.tfg.spacegame.utils.FontManager;

public class OptionsScreen implements Screen {

    private final SpaceGame game;

    //Objetos interactuables de la pantalla
    private OptionButton music;
    private OptionButton effect;
    private Button back;
    private Button resetArcadePuntuation;
    private DialogBox menuResetDialog;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public Texture background;

    public OptionsScreen(final SpaceGame game) {
        this.game = game;

        background = AssetsManager.loadTexture("background2");
        AudioManager.playMusic("menu", true);

        //Creamos los botones para la pantalla de opciones
        music = new OptionButton("buttonMusic", "buttonMusicCancel",200, 265);
        effect = new OptionButton("buttonEffect", "buttonEffectCancel",260, 265);
        back = new Button("arrow_back", 750, 430, null, true);
        resetArcadePuntuation = new Button("button", 100, 200, "resetArcadePuntuation",true);

        if (AudioManager.getVolumeMusic()==0.0f){
            music.setDesactivated(true);
        }else {
            music.setDesactivated(false);
        }

        if (AudioManager.getVolumeEffect()==0.0f){
            effect.setDesactivated(true);
        }else {
            effect.setDesactivated(false);
        }

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
        resetArcadePuntuation.render();

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
            /*Si el botón para la música ha sido pulsado y estaba desactivado, muteamos la música; en caso contario
              volvemos a poner el volumen en su estado natural*/
            if (music.isPressed() && music.isDesactivated()) {
                AudioManager.setVolumeMusic(0.0f);
            } else if (music.isPressed() && !music.isDesactivated()){
                AudioManager.setVolumeMusic(0.3f);
            }

            /*Si el botón para los efectos de sonido ha sido pulsado y estaba desactivado, muteamos los efectos de
              sonido; en caso contario volvemos a poner el volumen en su estado natural*/
            if (effect.isPressed() && effect.isDesactivated()) {
                AudioManager.setVolumeEffect(0.0f);
            } else if (effect.isPressed() && !effect.isDesactivated()){
                AudioManager.setVolumeEffect(0.7f);
            }

            if (back.isPressed()){
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
        resetArcadePuntuation.dispose();
	}
}
