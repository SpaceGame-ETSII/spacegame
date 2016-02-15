package com.tfg.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.tfg.spacegame.screens.MainMenuScreen;
import com.tfg.spacegame.utils.AssetsManager;

public class SpaceGame extends Game {

	public static SpriteBatch batch;
	public static OrthographicCamera camera;
	public static int width = 800;
	public static int height = 480;
	private static Vector3 touchPos;
	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	public static BitmapFont text;
	public static BitmapFont title;
	public static I18NBundle bundle;

	@Override
	public void create () {
		AssetsManager.load();

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);

		touchPos = new Vector3();

		this.setScreen(new MainMenuScreen(this));

		generator = new FreeTypeFontGenerator(Gdx.files.internal("pirulen.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = width/ 44;
		text = generator.generateFont(parameter);
		parameter.size = width / 20;
		title = generator.generateFont(parameter);
		generator.dispose();

		bundle = AssetsManager.loadBundle();
	}

	public void render() {
		super.render();
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

	public static void drawText(BitmapFont font ,String string, float x, float y){
		font.draw(batch,bundle.get(string),x,y);
	}

	public void dispose() {
		batch.dispose();
		text.dispose();
		title.dispose();
	}
}
