package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.gameObjects.arcadeMode.ArcadeShip;
import com.tfg.spacegame.gameObjects.campaignMode.CampaignShip;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.enums.GameState;

public class ArcadeScreen extends GameScreen {

	private final SpaceGame game;

	//Permiten la posición del fondo para realizar el scrolling
	private int scrollingPosition;
	private static final int SCROLLING_SPEED = 120;

	//Objetos interactuables de la pantalla
	private ArcadeShip ship;
	//Fondo que se mostrará
	public Texture background;

	public ArcadeScreen(final SpaceGame game) {
		this.game = game;

		state = GameState.READY;
		scrollingPosition = 0;
		ship = new ArcadeShip();
		background = AssetsManager.loadTexture("background");

		//Convertimos la pantalla en modo portrait
		SpaceGame.changeToPortrait();
	}

	@Override
	public void renderEveryState(float delta) {
		SpaceGame.batch.draw(new TextureRegion(background), 0, scrollingPosition,
								game.width / 2 , background.getHeight() / 2,
								background.getWidth(), background.getHeight(), 1, 1, 90);

		SpaceGame.batch.draw(new TextureRegion(background), 0, background.getWidth() + scrollingPosition,
								game.width / 2 , background.getHeight() / 2,
								background.getWidth(), background.getHeight(), 1, 1, 90);
	}

	@Override
	public void updateEveryState(float delta) {
		//Actualizamos la posición del scrolling
		scrollingPosition -= delta * SCROLLING_SPEED;
		if(scrollingPosition <= -background.getWidth())
			scrollingPosition = 0;
	}

	@Override
	public void renderReady(float delta) {
		FontManager.drawText("tapToStart", 80, 100);
	}

	@Override
	public void updateReady(float delta) {
		if (Gdx.input.justTouched()) {
			state = GameState.START;
		}
	}

	@Override
	public void renderStart(float delta) {
		ship.render(SpaceGame.batch);
	}

	@Override
	public void updateStart(float delta) {
		ship.update(delta);
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

