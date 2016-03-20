package com.tfg.spacegame.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.appwarp.WarpController;
import com.tfg.spacegame.utils.appwarp.WarpListener;
import com.tfg.spacegame.utils.enums.GameState;

public class MultiplayerScreen extends GameScreen implements WarpListener{
    final SpaceGame game;

    private PlayerShip  playerShip;
    private EnemyShip   enemyShip;

    private Texture background;

    public MultiplayerScreen(final SpaceGame game, String roomId, boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");

        state = GameState.PAUSE;

        playerShip  = new PlayerShip();
        enemyShip   = new EnemyShip();

        if(roomId.equals(""))
            WarpController.createInstance(WarpController.MultiplayerOptions.QUICK_GAME);
        else{
            if(createRoom)
                WarpController.createInstance(WarpController.MultiplayerOptions.CREATE_GAME);
            else
                WarpController.createInstance(WarpController.MultiplayerOptions.JOIN_GAME);
        }

        WarpController.getInstance().setListener(this);

        String username = randomUserName();

        WarpController.getInstance().startConnection(username,roomId);
    }

    private String randomUserName(){
        String result = "";
        for(int i= 0; i < 7; i++){
            result+=MathUtils.random.nextInt(26) + 'a';
        }
        return result;
    }

    @Override
    public void renderEveryState(float delta) {
        SpaceGame.batch.draw(background, 0,0);
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
        System.out.println(message);
    }

    @Override
    public void onError(String message) {
        System.out.println(message);
    }

    @Override
    public void onDidntFoundRoom(String message) {
        game.setScreen(new MultiplayerMenuScreen(game));
    }

    @Override
    public void onGameStarted(String message) {
        System.out.println(message);
    }

    @Override
    public void onConnectedWithServer(String message) {
        System.out.println(message);
    }

    @Override
    public void onJoinedToRoom(String message) {
        System.out.println(message);
    }

    @Override
    public void onGetLiveRoomInfoDone(String message) {
        System.out.println(message);
    }

    @Override
    public void onUserJoinedRoom(String message) {
        System.out.println(message);
    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {
    }

    @Override
    public void onGameUpdateReceived(String message) {
        System.out.println(message);
    }
}
