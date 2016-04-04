package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.AudioManager;
import com.tfg.spacegame.utils.BackgroundManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ScreenManager;

public class DemoMenuScreen implements Screen {

    private final SpaceGame game;

    //Representan las opciones a elegir en el Menú
    private Button allEnemies;
    private Button colorEnemies;
    private Button greenEnemy;
    private Button orangeEnemy;
    private Button purpleEnemy;
    private Button back;

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public DemoMenuScreen(final SpaceGame game) {
        this.game = game;

        //Creamos los botones para el menú principal
        allEnemies = new Button("button", 260, 315, "allEnemies", true);
        colorEnemies = new Button("button", 260, 255, "colorEnemies", true);
        greenEnemy = new Button("button", 260, 195, "greenEnemy", true);
        orangeEnemy = new Button("button", 260, 135, "orangeEnemy", true);
        purpleEnemy = new Button("button", 260, 75, "purpleEnemy", true);
        back = new Button("arrow_back", 750, 430, null, true);

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

        // Pintamos el título del juego
        FontManager.drawText("titleDemo", 229, 420);

        // Delegamos el render de los botones
        allEnemies.render();
        colorEnemies.render();
        greenEnemy.render();
        orangeEnemy.render();
        purpleEnemy.render();
        back.render();

        SpaceGame.batch.end();

        this.update(delta);
    }

    public void update(float delta) {
        allEnemies.update();
        colorEnemies.update();
        greenEnemy.update();
        orangeEnemy.update();
        purpleEnemy.update();
        back.update();

        //Si se acaba de tocar algún botón, reiniciamos el contador
        if (Gdx.input.justTouched()) {
            if (allEnemies.isPressed() ||
                    colorEnemies.isPressed() ||
                    greenEnemy.isPressed() ||
                    orangeEnemy.isPressed() ||
                    purpleEnemy.isPressed() ||
                    back.isPressed()) {
                timeUntilExit=0.5f;
                if (!back.isPressed())
                    AudioManager.stopMusic();
            }
        }

        //Si el contador es cero, comprobamos si hay algún botón pulsado y actuamos en consecuencia
        if (timeUntilExit <= 0) {
            if (allEnemies.isPressed()) {
                ScreenManager.changeScreen(game, CampaignScreen.class, allEnemies.getContent());
            } else if (colorEnemies.isPressed()) {
                ScreenManager.changeScreen(game, CampaignScreen.class, colorEnemies.getContent());
            } else if (greenEnemy.isPressed()) {
                ScreenManager.changeScreen(game, CampaignScreen.class, greenEnemy.getContent());
            } else if (orangeEnemy.isPressed()) {
                ScreenManager.changeScreen(game, CampaignScreen.class, orangeEnemy.getContent());
            } else if (purpleEnemy.isPressed()) {
                ScreenManager.changeScreen(game, CampaignScreen.class, purpleEnemy.getContent());
            } else if (back.isPressed()){
                ScreenManager.changeScreen(game, MainMenuScreen.class);
            }
        } else {
            timeUntilExit -= delta;
        }
    }

    @Override
    public void show() {
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
        allEnemies.dispose();
        colorEnemies.dispose();
        greenEnemy.dispose();
        orangeEnemy.dispose();
        purpleEnemy.dispose();
        back.dispose();
    }
}
