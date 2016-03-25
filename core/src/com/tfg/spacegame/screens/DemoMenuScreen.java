package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;

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

    public Texture background;

    public DemoMenuScreen(final SpaceGame game) {
        this.game = game;

        background = AssetsManager.loadTexture("background2");

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

        // Pintamos el fondo y el título del juego
        SpaceGame.batch.draw(background, 0,0);
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
        //Si se ha tocado algún botón, lo marcamos como pulsado
        if (Gdx.input.justTouched()) {

            Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            v = SpaceGame.camera.unproject(v);

            if (allEnemies.press(v.x, v.y) ||
                    colorEnemies.press(v.x, v.y) ||
                    greenEnemy.press(v.x, v.y) ||
                    orangeEnemy.press(v.x, v.y) ||
                    purpleEnemy.press(v.x, v.y) ||
                    back.press(v.x, v.y)) {
                //Reiniciamos el contador en caso de haberse pulsado un botón
                timeUntilExit=0.5f;
            }
        }

        //Si el contador es cero, comprobamos si hay algún botón pulsado y actuamos en consecuencia
        if (timeUntilExit <= 0) {
            if (allEnemies.isPressed()) {
                game.setScreen(new CampaignScreen(game, allEnemies.getContent()));
            } else if (colorEnemies.isPressed()) {
                game.setScreen(new CampaignScreen(game, colorEnemies.getContent()));
            } else if (greenEnemy.isPressed()) {
                game.setScreen(new CampaignScreen(game, greenEnemy.getContent()));
            } else if (orangeEnemy.isPressed()) {
                game.setScreen(new CampaignScreen(game, orangeEnemy.getContent()));
            } else if (purpleEnemy.isPressed()) {
                game.setScreen(new CampaignScreen(game, purpleEnemy.getContent()));
            } else if (back.isPressed()){
                game.setScreen(new MainMenuScreen(game));
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
