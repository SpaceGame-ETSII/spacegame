package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.tfg.spacegame.SpaceGame;

public class ArcadeScreen implements Screen {

    final SpaceGame game;

    public ArcadeScreen(final SpaceGame gam) {
        game = gam;
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

        game.text.draw(game.batch, "MODO ARCADE", 350, 400);

        game.batch.end();
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
        // TODO Auto-generated method stub
	}

}
