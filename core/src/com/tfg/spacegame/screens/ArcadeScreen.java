package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.enums.GameState;

public class ArcadeScreen extends GameScreen {

	private final SpaceGame game;

	public ArcadeScreen(final SpaceGame game) {
		this.game = game;
		state = GameState.READY;

		//Convertimos la pantalla en modo apaisado
		int newWidth = game.height;
		int newHeight = game.width;

		game.width = newWidth;
		game.height = newHeight;

		Gdx.graphics.setDisplayMode(newWidth, newHeight, true);

		game.camera = new OrthographicCamera();
		game.camera.setToOrtho(false, SpaceGame.width, SpaceGame.height);
	}

	@Override
	public void renderEveryState(float delta) {
		FontManager.drawText("MODO ARCADE", 350, 400);
	}

	@Override
	public void updateEveryState(float delta) {

	}

	@Override
	public void renderReady(float delta) {

	}

	@Override
	public void updateReady(float delta) {

	}

	@Override
	public void renderStart(float delta) {

	}

	@Override
	public void updateStart(float delta) {

	}

	@Override
	public void renderPause(float delta) {

	}

	@Override
	public void updatePause(float delta) {

	}

	@Override
	public void renderWin(float delta) {

	}

	@Override
	public void updateWin(float delta) {

	}

	@Override
	public void renderLose(float delta) {

	}

	@Override
	public void updateLose(float delta) {

	}

	@Override
	public void disposeScreen() {
		super.dispose();
	}
}

