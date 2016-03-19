package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.appwarp.WarpController;
import com.tfg.spacegame.utils.appwarp.WarpListener;
import com.tfg.spacegame.utils.enums.GameState;

public class MultiplayerScreen extends GameScreen implements WarpListener{

    private final SpaceGame game;

	private String message;

	private String userName;

	private PlayerShip 	playerShip;
	private EnemyShip 	enemyShip;

    public MultiplayerScreen(final SpaceGame gam) {
        game = gam;
		state = GameState.PAUSE;

		playerShip 	= new PlayerShip();
		enemyShip 	= new EnemyShip();

		userName = "1312321312";

		WarpController.getInstance().setListener(this);
		WarpController.getInstance().startConnection(userName);

		message = "Starting connection to server\nName: "+userName;

    }

	@Override
	public void renderEveryState(float delta) {
		Gdx.input.setOnscreenKeyboardVisible(true);
		FontManager.text.draw(game.batch,message, 50, SpaceGame.height);

		FontManager.text.draw(game.batch,state.toString(), 600, SpaceGame.height/2);
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

	}

	@Override
	public void onWaitingStarted(String message) {
		this.message+="\n"+message;
	}

	@Override
	public void onError(String message) {
		this.message+="\n"+message;
	}

	@Override
	public void onGameStarted(String message) {
		this.message+="\n"+message;
		state = GameState.START;
	}

	@Override
	public void onConnectedWithServer(String message) {
		this.message+="\n"+message;
	}

	@Override
	public void onJoinedToRoom(String message) {
		this.message+="\n"+message;
		state = GameState.READY;
	}

	@Override
	public void onGetLiveRoomInfoDone(String message) {
		this.message+="\n"+message;
	}

	@Override
	public void onUserJoinedRoom(String message) {
		this.message+="\n"+message;
	}

	@Override
	public void onGameFinished(int code, boolean isRemote) {
		this.message+="\n"+message;
	}

	@Override
	public void onGameUpdateReceived(String message) {
	}
}
