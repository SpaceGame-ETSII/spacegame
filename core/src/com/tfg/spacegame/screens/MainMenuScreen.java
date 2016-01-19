package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class MainMenuScreen implements Screen {
    GameObject campaing;
    GameObject arcade;
    GameObject multi;
    GameObject options;
    GameObject exit;

    final SpaceGame game;

    public MainMenuScreen(final SpaceGame gam) {
        create();
        game = gam;
    }

    public void create(){
        campaing = new GameObject("button",255,250);
        arcade = new GameObject("button",255,190);
        multi = new GameObject("button",255,130);
        options = new GameObject("button",255,70);
        exit = new GameObject("button",255,10);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        game.batch.setProjectionMatrix(game.camera.combined);

        game.batch.begin();
        // Pintamos el fondo
        game.batch.draw(game.background, 0,0);
        // Pintamos cada uno de los botones
        campaing.render(game.batch);
        arcade.render(game.batch);
        multi.render(game.batch);
        options.render(game.batch);
        exit.render(game.batch);
        // Pintamos los títulos de los botontes
        game.font.draw(game.batch, "SPACE GAME", 350, 400);
        game.font.draw(game.batch, "Modo Campaña", 345, 345);
        game.font.draw(game.batch, "Modo Arcade", 350, 285);
        game.font.draw(game.batch, "Modo Multijugador", 340, 225);
        game.font.draw(game.batch, "Opciones", 365, 165);
        game.font.draw(game.batch, "Salir", 380, 105);

        game.batch.end();

        /*if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }*/
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
        campaing.dispose();
        arcade.dispose();
        multi.dispose();
        arcade.dispose();
        exit.dispose();
	}

}
