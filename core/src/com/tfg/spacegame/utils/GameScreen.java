package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.enums.GameState;

public abstract class GameScreen implements Screen{

    //Estado en el que se encuentra el juego
    public GameState state;

    /**
     * Al inicializar
     */
    public GameScreen(){
        EnemiesManager.load();
        ShootsManager.load();
        CollissionsManager.load();

        state = GameState.READY;
    }

    /**
     * Esta sección está pensada para lo que se deba mostrar sin importar el estado del juego
     * Es decir, siempre
     */
    public abstract void renderEveryState(float delta);

    /**
     * La sección de Update para la renderización que no pertenece a ningún estado del juego, por lo que se hace siempre
     */
    public abstract void updateEveryState(float delta);

    /**
     * En esta sección renderizamos lo que se deba mostrar cuando el estado de juego sea de READY
     */
    public abstract void renderReady(float delta);

    /**
     * Asociada a la renderización, también tenemos la sección de Update si el juego está en el estado READY
     */
    public abstract void updateReady(float delta);

    /**
     * En esta sección renderizamos lo que se deba mostrar cuando el estado de juego sea de START
     */
    public abstract void renderStart(float delta);

    /**
     * Asociada a la renderización, también tenemos la sección de Update si el juego está en el estado START
     */
    public abstract void updateStart(float delta);

    /**
     * En esta sección renderizamos lo que se deba mostrar cuando el estado de juego sea de PAUSE
     */
    public abstract void renderPause(float delta);

    /**
     * Asociada a la renderización, también tenemos la sección de Update si el juego está en el estado PAUSE
     */
    public abstract void updatePause(float delta);

    /**
     * En esta sección renderizamos lo que se deba mostrar cuando el estado de juego sea de WIN
     */
    public abstract void renderWin(float delta);

    /**
     * Asociada a la renderización, también tenemos la sección de Update si el juego está en el estado WIN
     */
    public abstract void updateWin(float delta);

    /**
     * En esta sección renderizamos lo que se deba mostrar cuando el estado de juego sea de LOSE
     */
    public abstract void renderLose(float delta);

    /**
     * Asociada a la renderización, también tenemos la sección de Update si el juego está en el estado LOSE
     */
    public abstract void updateLose(float delta);

    /**
     * Hacemos el dispose de los objetos aquí. También hay que llamar al super.dispose para terminar de hacerlo
     * correctamente
     */
    public abstract void disposeScreen();

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

        // Lo que haya que hacer sin importar los estados lo hacemos
        renderEveryState(delta);
        updateEveryState(delta);

        // Ahora dependiendo de cada estado se hace llamar a su render y update correspiente
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

        // Terminamos el proceso de renderizado
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