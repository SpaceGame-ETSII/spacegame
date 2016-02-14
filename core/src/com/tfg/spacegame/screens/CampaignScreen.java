package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.*;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.enums.GameState;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.utils.SimpleDirectionGestureDetector;

public class CampaignScreen implements Screen{

    final SpaceGame game;

    //Objetos interactuables de la pantalla
    private Ship ship;
    private Inventary inventary;

    //Estado en el que se encuentra el juego
    private GameState state;

    private DialogBox menuExitDialog;

    //Ayudan con la posición de la ventana cuando se abre y se cierra el inventario
    private int scrollingPosition;
    private static final int SCROLLING_SPEED = 120;

    //Se usará para recoger las coordenadas con respecto a la cámara
    private Vector3 coordinates;

    //Servirá para comprobar si la nave puede moverse según la posición del dedo
    private boolean canShipMove;

    public CampaignScreen(final SpaceGame game) {

        this.game = game;
        scrollingPosition = 0;
        state = GameState.READY;

        //Creamos los objetos de juego
        ship = new Ship();
        inventary = new Inventary();
        EnemiesManager.load();
        ShootsManager.load();
        CollissionsManager.load();

        menuExitDialog = new DialogBox();
        //Creamos los objetos para el diálgo de salida del modo campaña
        menuExitDialog.addElement("window", new GameObject("ventana", 200, 120));
        menuExitDialog.addElement("exit", new Button("buttonExit", this, 750, 430));
        menuExitDialog.addElement("cancel", new Button("buttonCancel", this, 425, 200));
        menuExitDialog.addElement("confirm", new Button("buttonConfirm", this, 325, 200));

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
        ShootsManager.render();
        EnemiesManager.render();

        if (ship.isDefeated())
            state = GameState.LOSE;

        updateLogic(delta);
    }

    private void renderPause(float delta) {
        inventary.render(SpaceGame.batch);
        ship.render(SpaceGame.batch);

        //En función de si estamos en el diálogo para salir o no veremos la ventana para salir del modo campaña
        if (menuExitDialog.isDialogIn()){
            menuExitDialog.renderElement("window");
            SpaceGame.font.draw(SpaceGame.batch, "¿Desea salir del modo campaña?", 300, 320);
            menuExitDialog.renderElement("confirm");
            menuExitDialog.renderElement("cancel");

            if (menuExitDialog.getElementButton("confirm").isPressed()) {
                menuExitDialog.setDialogIn(false);
                menuExitDialog.getElementButton("confirm").setPressed(false);
                game.setScreen(new MainMenuScreen(game));
            }

            if (menuExitDialog.getElementButton("cancel").isPressed()) {
                menuExitDialog.setDialogIn(false);
                menuExitDialog.getElementButton("cancel").setPressed(false);
            }
        }else{
            menuExitDialog.renderElement("exit");

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
            Vector3 v = SpaceGame.getTouchPos(0);
            if (menuExitDialog.getElementButton("exit").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.setDialogIn(true);
                menuExitDialog.getElementButton("cancel").setPressed(false);
                menuExitDialog.getElementButton("confirm").setPressed(false);
            }
            if (menuExitDialog.getElementButton("confirm").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.getElementButton("confirm").setPressed(true);
            }
            if (menuExitDialog.getElementButton("cancel").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.getElementButton("cancel").setPressed(true);

            }
        }

    }

    public void updateLogic(float delta) {

        //Actualizamos la posición del scrolling
        scrollingPosition -= delta * SCROLLING_SPEED;
        if(scrollingPosition <= -game.background.getWidth())
            scrollingPosition = 0;

        //Creamos un vector que almacenará las posiciones relativas de la cámara
        coordinates = SpaceGame.getTouchPos(0);

        //Comprobamos si la posición del dedo corresponde al lugar donde se puede mover la nave
        if (Gdx.input.isTouched() && coordinates.x < (ship.getX() + ship.getWidth()))
            canShipMove = true;
        else
            canShipMove = false;

        ship.update(delta, coordinates.x, coordinates.y, canShipMove);

        // Si tocamos la pantalla disparamos
        // El disparo puede hacerse de dos formas
        // 1. Sin multituouch el disparo solo se realizará si pulsamos por delante del primer tercio de la pantalla
        // 2. Con multitouch el disparo se realizará en cualquier parte de la pantalla
        if((Gdx.input.isTouched(1) || (Gdx.input.isTouched(0) && Gdx.input.getX() > SpaceGame.width/3)) && Gdx.input.justTouched()) {

            //Si la nave pudo moverse, significa que para disparar tenemos que comprobar el segundo dedo
            if (canShipMove) {
                coordinates = SpaceGame.getTouchPos(1);
            }

            ship.shoot(coordinates.x, coordinates.y);
        }

        //Realizamos la lógica de los objetos en juego
        CollissionsManager.update(delta, ship);
        EnemiesManager.update(delta, ship);
        ShootsManager.update(delta, ship);
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
        menuExitDialog.dispose();
    }
}

