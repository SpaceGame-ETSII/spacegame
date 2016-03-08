package com.tfg.spacegame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;
import com.tfg.spacegame.screens.CampaignScreen;
import com.tfg.spacegame.screens.MainMenuScreen;
import com.tfg.spacegame.utils.*;

public class SpaceGame extends Game {

	// Objeto encargado de la renderización del juego
	public static SpriteBatch batch;
	// Con esto vamos a crear un entorno ortonormal 2d y añadirlo al spritebatch
	public static OrthographicCamera camera;
	// Sirve para permitir cambiar la orientación de la pantalla
	public static Platform platform;

	// Ancho y alto de la pantalla para la camara ortonormal
	public static int width = 800;
	public static int height = 480;

	// Objeto encargado de obtener las shapes
	private static ShapeLoader shapeLoader;

	public SpaceGame(Platform platform) {
		this.platform = platform;
	}

	@Override
	public void create () {
		AssetsManager.load();
		TouchManager.initialize();
		ShapeRendererManager.initialize();
		FontManager.initialize(width);
		shapeLoader = ShapeLoader.initialize("shapeEntities");

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);

		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	//Convierte la pantalla en modo portrait
	public static void changeToPortrait() {
		platform.setOrientation("portrait");

		int newWidth = height;
		int newHeight = width;

		width = newWidth;
		height = newHeight;

		Gdx.graphics.setDisplayMode(newWidth, newHeight, true);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);
	}

	/**
	 * Método que se encarga de obtener los vertices que forman una shape concreta
	 */
	public static float[] loadShape(String entity){
		return shapeLoader.getVertices(entity);
	}

	public void dispose() {
		batch.dispose();
	}
}
