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
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShapeLoader;
import com.tfg.spacegame.utils.ShapeRendererManager;
import com.tfg.spacegame.utils.TouchManager;

public class SpaceGame extends Game {

	// Objeto encargado de la renderización del juego
	public static SpriteBatch batch;
	// Con esto vamos a crear un entorno ortonormal 2d y añadirlo al spritebatch
	public static OrthographicCamera camera;

	// Ancho y alto de la pantalla para la camara ortonormal
	public static int width = 800;
	public static int height = 480;

	private FreeTypeFontGenerator generator;
	private FreeTypeFontGenerator.FreeTypeFontParameter parameter;

	public static BitmapFont text;
	public static BitmapFont title;
	// Objeto encargado de la internacionalización del juego
	public static I18NBundle bundle;

	// Objeto encargado de obtener las shapes
	private static ShapeLoader shapeLoader;

	@Override
	public void create () {
		AssetsManager.load();
		TouchManager.initialize();
		ShapeRendererManager.initialize();
		shapeLoader = ShapeLoader.initialize("shapeEntities");

		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);

		this.setScreen(new MainMenuScreen(this));

		generator = new FreeTypeFontGenerator(Gdx.files.internal("pirulen.ttf"));
		parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = width/ 44;
		text = generator.generateFont(parameter);
		parameter.size = width / 20;
		title = generator.generateFont(parameter);
		generator.dispose();

		// Cargamos el bundle desde su correspondiente método del asset manager
		bundle = AssetsManager.loadBundle();
	}

	public void render() {
		super.render();
	}

	public static void drawText(BitmapFont font ,String string, float x, float y){
		font.draw(batch,bundle.get(string),x,y);
	}

	/**
	 * Método que se encarga de obtener los vertices que forman una shape concreta
	 */
	public static float[] loadShape(String entity){
		return shapeLoader.getVertices(entity);
	}

	public void dispose() {
		batch.dispose();
		text.dispose();
		title.dispose();
	}
}
