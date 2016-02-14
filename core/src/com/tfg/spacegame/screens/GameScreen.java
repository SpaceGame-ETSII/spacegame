package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.CollissionsManager;
import com.tfg.spacegame.utils.EnemiesManager;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.utils.enums.GameState;

/**
 * Created by gaems-dev on 13/02/16.
 */
public abstract class GameScreen implements Screen{

    //Estado en el que se encuentra el juego
    public GameState state;

    public GameScreen(){
        EnemiesManager.load();
        ShootsManager.load();
        CollissionsManager.load();

        state = GameState.READY;
    }

    public abstract void renderPause(float delta);
    public abstract void renderReady(float delta);
    public abstract void renderStart(float delta);
    public abstract void renderWin(float delta);
    public abstract void renderLose(float delta);

    public abstract void renderDefault(float delta);


    public abstract void updatePause(float delta);
    public abstract void updateReady(float delta);
    public abstract void updateStart(float delta);
    public abstract void updateWin(float delta);
    public abstract void updateLose(float delta);

    public abstract void updateDefault(float delta);

    public abstract void disposeScreen();

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpaceGame.camera.update();
        SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

        SpaceGame.batch.begin();

        renderDefault(delta);
        updateDefault(delta);

        switch (state) {
            case LOSE:
                renderLose(delta);
                updateLose(delta);
                break;
            case PAUSE:
                renderPause(delta);
                updatePause(delta);
                break;
            case READY:
                renderReady(delta);
                updateReady(delta);
                break;
            case START:
                renderStart(delta);
                updateStart(delta);
                break;
            case WIN:
                renderWin(delta);
                updateWin(delta);
                break;
            default:
                throw new IllegalArgumentException("Estado de juego no válido");
        }

        SpaceGame.batch.end();
    }

    @Override
    public void dispose() {}
    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}