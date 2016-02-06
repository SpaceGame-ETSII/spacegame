package com.tfg.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.screens.MainMenuScreen;
import com.tfg.spacegame.utils.AssetsManager;

public class SpaceGame extends Game {

	public static SpriteBatch batch;
	public static BitmapFont font;
	public Texture background;
	public static OrthographicCamera camera;
	public static int width = 800;
	public static int height = 480;

	private static Vector3 touchPos;

	@Override
	public void create () {
		AssetsManager.load();

		batch = new SpriteBatch();
		font = new BitmapFont();
		background = AssetsManager.loadTexture("background");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);

		this.setScreen(new MainMenuScreen(this));

		touchPos = new Vector3();
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public static Vector3 getTouchPos(int point){
		if(point == 0){
			touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
		}else{
			touchPos.set(Gdx.input.getX(point),Gdx.input.getY(point),0);
		}
		touchPos = camera.unproject(touchPos);

		return touchPos;
	}
}
