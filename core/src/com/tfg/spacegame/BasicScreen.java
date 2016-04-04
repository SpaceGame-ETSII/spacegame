package com.tfg.spacegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tfg.spacegame.utils.BackgroundManager;

public abstract class BasicScreen implements Screen, InputProcessor {

    @Override
    public void render(float delta) {
        // Limpiamos la pantalla con un color negro no transparente
        Gdx.gl.glClearColor(0, 0, 0, 1);
        // Además limpiamos el buffer de la pantalla
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Actualizamos la camara y actualizamos la matriz de proyección del sprite batch
        SpaceGame.camera.update();
        SpaceGame.batch.setProjectionMatrix(SpaceGame.camera.combined);

        // Iniciamos el proceso de renderización del spritebatch
        SpaceGame.batch.begin();

        // Actualizamos y mostramos los fondos
        BackgroundManager.render();
        BackgroundManager.update(delta);

        //Actualizamos el contenido general del juego
        this.update1(delta);

        // Terminamos el proceso de renderizado
        SpaceGame.batch.end();
    }

    protected void update1(float delta) {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }
    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    @Override
    public void show() { }
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void hide() { }
    @Override
    public void dispose() { }
}
