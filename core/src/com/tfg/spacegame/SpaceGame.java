package com.tfg.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.screens.MainMenuScreen;

public class SpaceGame extends Game {

	public SpriteBatch batch;
	public BitmapFont font;
	public Texture background;
	public OrthographicCamera camera;
	public static int width = 800;
	public static int height = 480;

	@Override
	public void create () {
		AssetsManager.load();

		batch = new SpriteBatch();
		font = new BitmapFont();
		background = AssetsManager.loadTexture("background");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);

		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
