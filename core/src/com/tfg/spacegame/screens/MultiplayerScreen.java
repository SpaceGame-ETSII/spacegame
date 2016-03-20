package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ExternalFilesManager;
import com.tfg.spacegame.utils.FontManager;
import com.tfg.spacegame.utils.appwarp.WarpController;
import com.tfg.spacegame.utils.appwarp.WarpListener;
import com.tfg.spacegame.utils.enums.GameState;

public class MultiplayerScreen extends GameScreen implements WarpListener, InputProcessor{


    private final SpaceGame game;

	private String message;

	private String userName;

	private PlayerShip 	playerShip;
	private EnemyShip 	enemyShip;

    public MultiplayerScreen(final SpaceGame gam) {
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);

        game = gam;
		state = GameState.PAUSE;

		message = "";

		playerShip 	= new PlayerShip();
		enemyShip 	= new EnemyShip();

		WarpController.getInstance().setListener(this);

		userName = ExternalFilesManager.loadMultiplayerSettings();

		if(userName.equals("")){
			Gdx.input.getTextInput(new Input.TextInputListener() {
				@Override
				public void input(String text) {
					userName = text;
					WarpController.getInstance().startConnection(userName);
					ExternalFilesManager.saveMultiplayerSettings(userName);
				}

				@Override
				public void canceled() {

				}
			}, FontManager.getFromBundle("askName") , "","");
		}else{
			WarpController.getInstance().startConnection(userName);
		}
    }

	@Override
	public void renderEveryState(float delta) {
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
		WarpController.getInstance().disconnect();
		playerShip.dispose();
		enemyShip.dispose();
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

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Input.Keys.BACK){
			WarpController.getInstance().disconnect();
			game.setScreen(new MainMenuScreen(game));
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
