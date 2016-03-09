package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.gameObjects.arcadeMode.ArcadeShip;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.ObstacleManager;
import com.tfg.spacegame.utils.enums.GameState;

public class ArcadeScreen extends GameScreen {

	private final SpaceGame game;

	private static final int SCROLLING_SPEED = 120;
	private static final float MAX_TIME_TO_BLOCK = 1;
	private static final float TIME_TO_MEASURE_Y = 0.2f;

	//Objetos interactuables de la pantalla
	private ArcadeShip ship;

	//Fondo que se mostrará
	private Texture background;

	//Permiten la posición del fondo para realizar el scrolling
	private int scrollingPosition;

	//Guarda la última medición del acelerómetro Y, en intervalos de MAX_TIME_TO_MEASURE_Y
	private float lastMeasureY;

	//Contador para que indica cada cuánto actualizaremos lastMeasureY
	private float timeToMeasureY;

	//Contador que indica si estamos en el cambio de capa, y por tanto hay que bloquear otra posibilidad de cambio
	private float timeToBlock;

	//Indica la capa en la que se está, indicando '-1' la capa de abajo y '1' la capa de arriba
	private int layer;

	public ArcadeScreen(final SpaceGame game) {
		this.game = game;

		scrollingPosition = 0;
		ship = new ArcadeShip();
		background = AssetsManager.loadTexture("background");

		this.initialize();

		//Convertimos la pantalla en modo portrait
		SpaceGame.changeToPortrait();
	}

	private void initialize() {
		state = GameState.READY;
		ObstacleManager.load();
		timeToMeasureY = 0;
		lastMeasureY = Gdx.input.getAccelerometerY();
		layer = 1;
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
		ObstacleManager.render();

		FontManager.draw("" + Gdx.input.getAccelerometerY(), 200, 200);
	}

	@Override
	public void updateStart(float delta) {
		ship.update(delta);
		ObstacleManager.update(delta);

		//Comprobamos si ha pasado el tiempo de bloqueo para poder cambiar de capa
		//Si no ha pasado el tiempo, significará que estamos en medio de un cambio de capa
		if (timeToBlock <= 0) {

			//Si la distancia entre la medición actual y la última del acelerómetro Y es mayor a 3
			//significa que por tanto bloqueamos el cambio de capa
			if (Math.abs(Gdx.input.getAccelerometerY() - lastMeasureY) > 3)
				timeToBlock = MAX_TIME_TO_BLOCK;

			//Actualizamos el tiempo para poder volver a medir el acelerómetro Y
			timeToMeasureY += delta;

			//Si ya se ha cumplido el tiempo de la última medición, volvemos a medir y reseteamos el contador
			if (timeToMeasureY > TIME_TO_MEASURE_Y) {
				lastMeasureY = Gdx.input.getAccelerometerY();
				timeToMeasureY = 0;
			}
		} else {
			//Entraremos por aquí si estamos realizando el cambio de capa

			//Actualizamos el tiempo de bloqueo
			timeToBlock -= delta;

			//Comprobamos si el tiempo de bloqueo ha terminado, y si es así, significa que ha terminado la transición de cambio
			if (timeToBlock <= 0) {

				//Recolocamos la nave en su posición final dependiendo de la capa destino
				if (layer == 1)
					ship.setScale(0.5f, 0.5f);
				else
					ship.setScale(1, 1);

				//Cambiamos finalmente la capa en la que estamos y volvemos a medir el acelerómetro Y
				layer *= -1;
				lastMeasureY = Gdx.input.getAccelerometerY();
			} else {
				//Si no ha terminado el tiempo de bloqueo, transicionamos la posición de la nave a la nueva capa
				ship.setScale(ship.getLogicShape().getScaleX() + (0.5f * delta * layer * -1),
						      ship.getLogicShape().getScaleY() + (0.5f * delta * layer * -1));
			}
		}

		if (ObstacleManager.existsCollision(ship))
			state = GameState.LOSE;
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
		FontManager.drawText("gameOver", 80, 100);
	}

	@Override
	public void updateLose(float delta) {
		if (Gdx.input.justTouched()) {
			this.initialize();
		}
	}

	@Override
	public void disposeScreen() {
		super.dispose();
	}
}

