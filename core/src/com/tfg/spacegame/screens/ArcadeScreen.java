package com.tfg.spacegame.screens;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.gameObjects.Button;
import com.tfg.spacegame.gameObjects.arcadeMode.ArcadeShip;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.enums.GameState;

public class ArcadeScreen extends GameScreen {

	private final SpaceGame game;

	//Velocidad del scrolling del fondo
	private static final int SCROLLING_SPEED = 100;
	//Tiempo que bloquearemos el cambio de capa cada vez que se realice un cambio
	private static final float MAX_TIME_TO_BLOCK = 1;
	//Tiempo máximo hasta que actualicemos lastMeasureY
	private static final float TIME_TO_MEASURE_Y = 0.2f;
	//Mínimo alpha para las transparencias de los obstacles
	private static final float MIN_ALPHA = 0.3f;
	//Indica el escalado que tendrán los objetos en la capa baja
	private static final float BOTTOM_SCALE = 0.5f;

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
	public static int layer;

	//Acumula el tiempo que está vivo el jugador en la partida
	private float timeAlive;

	private ShakeEffect shakeEffect;

	//Botón que nos permitirá salir del juego
	private Button exit;


	public ArcadeScreen(final SpaceGame game) {
		this.game = game;

		//Convertimos la pantalla en modo portrait
		SpaceGame.changeToPortrait();

		scrollingPosition = 0;
		background = AssetsManager.loadTexture("background2");
		shakeEffect = new ShakeEffect(1f, ShakeEffect.NORMAL_SHAKE);
		exit = new Button("buttonExit", SpaceGame.width - 50, SpaceGame.height - 50, null, true);

		this.initialize();
	}

	private void initialize() {
		ship = new ArcadeShip();
		state = GameState.READY;
		ObstacleManager.load();
		timeToMeasureY = 0;
		lastMeasureY = Gdx.input.getAccelerometerY();
		timeToBlock = 0;
		layer = 1;
		timeAlive = 0;
	}

	@Override
	public void renderEveryState(float delta) {
		//Pintamos el fondo, que necesitará pintarse dos veces para el scrolling
		SpaceGame.batch.draw(new TextureRegion(background), 0, scrollingPosition,
								game.width / 2 , background.getHeight() / 2,
								background.getWidth(), background.getHeight(), 1, 1, 90);
		SpaceGame.batch.draw(new TextureRegion(background), 0, background.getWidth() + scrollingPosition,
								game.width / 2 , background.getHeight() / 2,
								background.getWidth(), background.getHeight(), 1, 1, 90);
	}

	@Override
	public void updateEveryState(float delta) {
		//Si el estado no está en pausa, avanzamos el fondo y actualizamos la vibración de la pantalla
		if (!state.equals(GameState.PAUSE)) {
			shakeEffect.shake(delta);

			//Actualizamos la posición del scrolling
			scrollingPosition -= delta * SCROLLING_SPEED;
			if (scrollingPosition <= -background.getWidth())
				scrollingPosition = 0;
		}
	}

	@Override
	public void renderReady(float delta) {
		FontManager.drawText("tapToStart", 100, 100);
	}

	@Override
	public void updateReady(float delta) {
		if (Gdx.input.justTouched()) {
			state = GameState.START;
			AudioManager.playMusic("arcade", true);
		}
	}

	@Override
	public void renderStart(float delta) {
		//Primero realizamos el cálculo del alpha según el escalado actual de la nave
		float alpha = (ship.getLogicShape().getScaleX() - BOTTOM_SCALE) / (1 - BOTTOM_SCALE);
		alpha *= (1 - MIN_ALPHA);
		alpha += MIN_ALPHA;

		//Pintamos naves y obstáculos según orden y el alpha correspondiente para cada uno
		ObstacleManager.renderBottom((1 + MIN_ALPHA) - alpha);
		ship.render();
		ObstacleManager.renderTop(alpha);

		this.renderTime();
	}

	@Override
	public void updateStart(float delta) {
		//Actualizamos nave, obstáculos y las capas
		ship.update(delta);
		ObstacleManager.update(delta);

		//Hasta que no pase un segundo, no podemos pasar de capa
		if (timeAlive > 1) {
			this.updateLayers(delta);
		}

		//Actualizamos el tiempo
		timeAlive += delta;

		//Por último, comprobamos si hay colisión con la nave
		//Si no hay colisión, comprobamos si se ha tocado la pantalla para entrar en pause
		if (ObstacleManager.existsCollision(ship, layer)) {
			ship.defeat();
			state = GameState.LOSE;
			Gdx.input.vibrate(300);
			shakeEffect.start();
			AudioManager.stopMusic();
			AudioManager.playSound("arcade_shock_effect");
		} else if (Gdx.input.justTouched()) {
			state = GameState.PAUSE;
			AudioManager.pauseMusic();
		}
	}

	@Override
	public void renderPause(float delta) {
		FontManager.drawText("pause", SpaceGame.height / 2);
		ship.render();
		this.renderTime();
		exit.render();
	}

	@Override
	public void updatePause(float delta) {
		if (Gdx.input.justTouched()) {

			Vector3 v = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			v = SpaceGame.camera.unproject(v);

			if (exit.press(v.x, v.y)) {
				game.setScreen(new MainMenuScreen(game));
			} else {
				state = GameState.START;
				AudioManager.playMusic();
			}
		}
	}

	@Override
	public void renderWin(float delta) {

	}

	@Override
	public void updateWin(float delta) {

	}

	@Override
	public void renderLose(float delta) {
		renderStart(delta);
		FontManager.drawText("gameOver", 120, 100);
	}

	@Override
	public void updateLose(float delta) {
		ship.update(delta);

		//Deberemos volver a tocar la pantalla para reiniciar la partida
		if (Gdx.input.justTouched()) {
			this.initialize();
		}
	}

	public void updateLayers(float delta) {
		//Comprobamos si ha pasado el tiempo de bloqueo para poder cambiar de capa
		//Si no ha pasado el tiempo, significará que estamos en la transición de un cambio de capa
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
					ship.setScale(BOTTOM_SCALE, BOTTOM_SCALE);
				else
					ship.setScale(1, 1);

				//Cambiamos finalmente la capa en la que estamos y volvemos a medir el acelerómetro Y
				layer *= -1;
				lastMeasureY = Gdx.input.getAccelerometerY();
			} else {
				//Si no ha terminado el tiempo de bloqueo, transicionamos la posición de la nave a la nueva capa
				ship.setScale(ship.getLogicShape().getScaleX() + (BOTTOM_SCALE * delta * layer * -1),
							  ship.getLogicShape().getScaleY() + (BOTTOM_SCALE * delta * layer * -1));
			}
		}
	}

	private void renderTime() {
		FontManager.drawText("time", ": " + ((int) timeAlive), 10, 790);
	}

	@Override
	public void disposeScreen() {
		super.dispose();
		ship.dispose();
		background.dispose();
	}
}

