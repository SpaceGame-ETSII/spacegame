package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
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

    //Representa el tiempo que dura el efecto visual de pulsado sobre una opción
    private float timeUntilExit;

    public Texture background;

    public DemoMenuScreen(final SpaceGame game) {
        this.game = game;

        background = AssetsManager.loadTexture("background");

        //Creamos los botones para el menú principal
        allEnemies = new Button("button", new CampaignScreen(game), 290, 315, "allEnemies");
        colorEnemies = new Button("button", new CampaignScreen(game), 290, 255, "colorEnemies");
        greenEnemy = new Button("button", new CampaignScreen(game), 290, 195, "greenEnemy");
        orangeEnemy = new Button("button", new CampaignScreen(game), 290, 135, "orangeEnemy");
        purpleEnemy = new Button("button", new CampaignScreen(game), 290, 75, "purpleEnemy");

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
        FontManager.drawText(SpaceGame.batch, FontManager.text, "titleDemo", 229, 420);

        // Delegamos el render de los botones
        allEnemies.render(SpaceGame.batch);
        colorEnemies.render(SpaceGame.batch);
        greenEnemy.render(SpaceGame.batch);
        orangeEnemy.render(SpaceGame.batch);
        purpleEnemy.render(SpaceGame.batch);

        SpaceGame.batch.end();

        this.update(delta);
    }

    public void update(float delta) {
        //Si se ha tocado algún botón, lo marcamos como pulsado
        if (Gdx.input.justTouched()) {

            Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            v = SpaceGame.camera.unproject(v);

            if (allEnemies.press(v.x, v.y) ||
                    colorEnemies.press(v.x, v.y) ||
                    greenEnemy.press(v.x, v.y) ||
                    orangeEnemy.press(v.x, v.y) ||
                    purpleEnemy.press(v.x, v.y)) {
                timeUntilExit=0.5f;
            }
        }

        //Actualizamos los botones si fueron pulsados previamente
        updateButton(delta, allEnemies);
        updateButton(delta, colorEnemies);
        updateButton(delta, greenEnemy);
        updateButton(delta, orangeEnemy);
        updateButton(delta, purpleEnemy);
    }

    //Si se ha acabado el tiempo de la pulsación, hacemos la función del botón correspondiente
    public void updateButton(float delta, Button button){
        if (timeUntilExit <= 0 && button.isPressed()) {

            //Si el screen del botón no es nulo, nos vamos ahí. Si lo es, significa que es el exit y debemos salir
            if (button.getScreen() != null) {
                game.setScreen(button.getScreen());
            } else {
                Gdx.app.exit();
            }

            dispose();
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
    }
}
