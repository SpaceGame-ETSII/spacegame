package com.tfg.spacegame.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObjects.Enemy;
import com.tfg.spacegame.GameObjects.Inventary;
import com.tfg.spacegame.GameObjects.Ship;
import com.tfg.spacegame.GameObjects.Shoot;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.Utils.GameState;
import com.tfg.spacegame.Utils.SimpleDirectionGestureDetector;

public class GameScreen implements Screen {
    final SpaceGame game;
    
    Ship ship;
    Enemy enemy;
    Shoot shoot;
    Inventary inventary;

    GameState state;

    int scrollingPosition;

    int scrollingSpeed;
    
    public GameScreen(final SpaceGame gam) {
        this.game = gam;

        scrollingPosition = 0;
        scrollingSpeed = 120;

        state = GameState.READY;

        //Creamos los objetos de juego
        ship = new Ship();
        enemy = new Enemy(SpaceGame.width, SpaceGame.height/2 - 40/2);
        shoot = new Shoot(ship);
        inventary = new Inventary();

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

            public void onDown() {}
            public void onUp() {}

        }));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();

        game.batch.draw(game.background, scrollingPosition,0);
        game.batch.draw(game.background, game.background.getWidth()+scrollingPosition,0);

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

        game.batch.end();
    }

    private void renderLose(float delta) {
        game.font.draw(game.batch, "Game Over", 370, 240);

        if (Gdx.input.justTouched()) {
            state = GameState.READY;
            ship = new Ship();
        }

    }

    private void renderReady(float delta) {
        game.font.draw(game.batch, "Tap to start", 370, 240);

        if (Gdx.input.justTouched())
            state = GameState.START;
    }

    private void renderStart(float delta) {
        game.font.draw(game.batch, ship.getVitality() + "", 100, 100);

        ship.render(game.batch);
        if (!enemy.isDefeated)
            enemy.render(game.batch);
        if (shoot.isShooted)
            shoot.render(game.batch);

        if (ship.getVitality() <= 0)
            state = GameState.LOSE;

        updateLogic(delta);
    }

    private void renderPause(float delta) {
        inventary.render(game.batch);

        //Se hará una cosa u otra si el inventario está cerrándose o no
        if (inventary.isClosing()) {
            inventary.updateClosing(delta);

            //Si el inventario ya no está cerrándose, volvemos a la partida
            if (!inventary.isClosing())
                state = GameState.START;
        } else {
            inventary.update(delta);
        }
    }

    public void updateLogic(float delta) {
        //Actualizamos la posición del scrolling
        scrollingPosition -= delta * scrollingSpeed;
        if(scrollingPosition <= -game.background.getWidth())
            scrollingPosition = 0;

        //Creamos un vector que almacenará las posiciones relativas de la cámara
        Vector3 v = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
        v = game.camera.unproject(v);

        //Realizamos la lógica de los objetos en juego
        ship.update(delta, v.x, v.y);
        shoot.update(delta);
        enemy.update(delta);

        //Se realizará cuando el disparo dé en el enemigo
        if (!enemy.isDefeated && shoot.isOverlapingWith(enemy)) {
            shoot.restart();
            enemy.defeat();
        }

        //Se realizará cuando el enemigo golpée al jugador
        if (ship.isOverlapingWith(enemy) && !ship.isUndamagable())
            ship.receiveDamage();
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
        enemy.dispose();
        shoot.dispose();
    }

}