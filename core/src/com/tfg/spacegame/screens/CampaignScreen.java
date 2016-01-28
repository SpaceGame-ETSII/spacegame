package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Inventary;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.*;

public class CampaignScreen implements Screen{

    final SpaceGame game;

    //Objetos interactuables de la pantalla
    private Ship ship;
    private Inventary inventary;

    //Estado en el que se encuentra el juego
    private GameState state;

    //Variables para el diálogo de salida del modo camapaña
    private GameObject exit;
    private GameObject exitConfirm;
    private GameObject exitCancel;
    private GameObject ventana;

    //Indican el estado de la ventana de diálogo para salir del juego
    private boolean isDialogin=false;
    private boolean isConfirm=false;
    private boolean isCancelled=false;

    //Ayudan con la posición de la ventana cuando se abre y se cierra el inventario
    private int scrollingPosition;
    private static final int SCROLLING_SPEED = 120;

    public CampaignScreen(final SpaceGame game) {

        this.game = game;
        scrollingPosition = 0;
        state = GameState.READY;

        //Creamos los objetos de juego
        ship = new Ship();
        EnemiesManager.load();
        ShootsManager.load();
        inventary = new Inventary();

        //Creamos los objetos para el diálgo de salida del modo campaña
        exit = new GameObject("buttonExit",750,430);
        ventana = new GameObject("ventana",200,120);
        exitCancel = new GameObject("buttonCancel",425,200);
        exitConfirm = new GameObject("buttonConfirm",325,200);

        //Preparamos un listener que si se desliza el dedo a la derecha se abre el inventario
        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            public void onRight() {
                if (state.equals(GameState.START)) {
                    inventary.restart();
                    state = GameState.PAUSE;
                }
            }

            public void onLeft() {
                if (state.equals(GameState.PAUSE) && !inventary.isClosing())
                    inventary.setIsClosing(true);
            }

            public void onDown() {
            }

            public void onUp() {
            }

        }));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        SpaceGame.batch.setProjectionMatrix(game.camera.combined);

        SpaceGame.batch.begin();

        SpaceGame.batch.draw(game.background, scrollingPosition, 0);
        SpaceGame.batch.draw(game.background, game.background.getWidth() + scrollingPosition, 0);

        switch (state) {
            case LOSE:
                this.renderLose(delta);
                break;
            case PAUSE:
                this.renderPause(delta);
                break;
            case READY:
                this.renderReady(delta);
                break;
            case START:
                this.renderStart(delta);
                break;
            case WIN:
                break;
            default:
                break;
        }

        SpaceGame.batch.end();
    }

    private void renderLose(float delta) {
        SpaceGame.font.draw(SpaceGame.batch, "Game Over", 370, 240);

        if (Gdx.input.justTouched()) {
            state = GameState.READY;
            ship = new Ship();
        }

    }

    private void renderReady(float delta) {
        SpaceGame.font.draw(SpaceGame.batch, "Tap to start", 370, 240);

        if (Gdx.input.justTouched())
            state = GameState.START;
    }

    private void renderStart(float delta) {
        ship.render(game.batch);
        EnemiesManager.render();
        ShootsManager.render();

        if (ship.isDefeated())
            state = GameState.LOSE;

        updateLogic(delta);
    }

    private void renderPause(float delta) {
        inventary.render(SpaceGame.batch);
        ship.render(SpaceGame.batch);

        //En función de si estamos en el diálogo para salir o no veremos la ventana para salir del modo campaña
        if (isDialogin){
            ventana.render(SpaceGame.batch);
            SpaceGame.font.draw(SpaceGame.batch, "¿Desea salir del modo campaña?", 300, 320);
            exitCancel.render(SpaceGame.batch);
            exitConfirm.render(SpaceGame.batch);

            if (isConfirm) {
                isDialogin = false;
                isConfirm = false;
                game.setScreen(new MainMenuScreen(game));
            }

            if (isCancelled) {
                isDialogin = false;
                isCancelled = false;
            }
        }else{
            exit.render(SpaceGame.batch);

            //Se hará una cosa u otra si el inventario está cerrándose o no
            if (inventary.isClosing()) {
                inventary.updateClosing(delta, ship);

                //Si el inventario ya no está cerrándose, volvemos a la partida
                if (!inventary.isClosing())
                    state = GameState.START;
            } else {
                //Creamos un vector que almacenará las posiciones relativas de la cámara
                Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                v = game.camera.unproject(v);

                inventary.update(delta, ship, v.x, v.y);
            }
        }

        //Comprobamos sobre que botón pulsa el usuario y actualizamos las variables del diálgo en consecuencia
        if (Gdx.input.isTouched()) {
            Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            v = game.camera.unproject(v);
            if (exit.isOverlapingWith(v.x, v.y)) {
                isDialogin=true;
                isCancelled=false;
                isConfirm=false;
            }
            if (exitConfirm.isOverlapingWith(v.x,v.y)) {
                isConfirm=true;
            }
            if (exitCancel.isOverlapingWith(v.x,v.y)) {
                isCancelled=true;
            }

        }

    }

    public void updateLogic(float delta) {

        //Actualizamos la posición del scrolling
        scrollingPosition -= delta * SCROLLING_SPEED;
        if(scrollingPosition <= -game.background.getWidth())
            scrollingPosition = 0;

        //Creamos +un vector que almacenará las posiciones relativas de la cámara
        Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        v = game.camera.unproject(v);

        //Realizamos la lógica de los objetos en juego
        ship.update(delta, v.x, v.y);
        EnemiesManager.update(delta);
        ShootsManager.update(delta, ship);

        // Si tocamos la pantalla disparamos
        // El disparo puede hacerse de dos formas
        // 1. Sin multituouch el disparo solo se realizará si pulsamos por delante del primer tercio de la pantalla
        // 2. Con multitouch el disparo se realizará en cualquier parte de la pantalla
        if((Gdx.input.isTouched(1) || (Gdx.input.isTouched(0) && Gdx.input.getX() > SpaceGame.width/3))){
            ship.shoot();
        }

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        ship.dispose();
        for(Enemy enemy: EnemiesManager.enemies)
            enemy.dispose();
        for(Shoot shoot : ShootsManager.shoots)
            shoot.dispose();
        inventary.dispose();
        exit.dispose();
        exitCancel.dispose();
        exitConfirm.dispose();
        ventana.dispose();
    }
}

