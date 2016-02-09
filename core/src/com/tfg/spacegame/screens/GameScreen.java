package com.tfg.spacegame.screens;

import com.badlogic.gdx.Screen;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.enums.GameState;


public class GameScreen implements Screen{


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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
