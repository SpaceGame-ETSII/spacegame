package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.appwarp.WarpController;
import com.tfg.spacegame.utils.appwarp.WarpListener;
import com.tfg.spacegame.utils.enums.GameState;
import com.tfg.spacegame.utils.enums.TypePowerUp;

import java.awt.*;

public class MultiplayerScreen extends GameScreen implements WarpListener{
    final SpaceGame game;

    private PlayerShip  playerShip;
    private EnemyShip   enemyShip;
    public static float enemyYposition;
    private final float MAX_TIME_TO_START_GAME = 5f;

    private Texture background;

    private String infoMessage;

    private float timeToStartGame;
    private boolean changeToStartGame;

    private boolean backToMainMenu;

    public MultiplayerScreen(final SpaceGame game, String roomId, boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");

        state = GameState.READY;

        infoMessage = FontManager.getFromBundle("connectServer");

        timeToStartGame = 0;
        changeToStartGame = false;

        backToMainMenu = false;

        playerShip  = new PlayerShip();
        enemyShip   = new EnemyShip();

        EnemiesManager.loadMultiplayerEnemies(enemyShip);
        CollissionsManager.load();
        ShootsManager.load();
        CameraManager.loadShakeEffect(1f);

        enemyYposition = enemyShip.getY();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

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
        FontManager.text.draw(SpaceGame.batch,infoMessage,SpaceGame.width/3,SpaceGame.height/2);
    }

    @Override
    public void updateReady(float delta) {
        if(changeToStartGame){
            if(timeToStartGame > 0){
                infoMessage = FontManager.getFromBundle("startGame")+"  "+(int)timeToStartGame;
                timeToStartGame-=delta;
            }else{
                timeToStartGame = 0;
                state = GameState.START;
            }
        }
    }

    @Override
    public void renderStart(float delta) {
        playerShip.render();

        EnemiesManager.render();
        ShootsManager.render();
    }

    @Override
    public void updateStart(float delta) {
        EnemiesManager.enemies.first().update(delta);
        CollissionsManager.update(delta, playerShip);
        ShootsManager.update(delta, playerShip);
        CameraManager.updateShakeEffect(delta);

        // Obtenemos un vector de coordenadas si está en la zona de movimiento de la nave
        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(playerShip.getX() + playerShip.getWidth());

        boolean canShipMove = false;
        if(!coordinates.equals(Vector3.Zero))
            canShipMove = true;

        playerShip.update(delta, coordinates.y, canShipMove);

        coordinates = TouchManager.getAnyXTouchGreaterThan(playerShip.getX() + playerShip.getWidth());

        if(!coordinates.equals(Vector3.Zero) && Gdx.input.justTouched()){
            if(playerShip.burstPowerUp.isOverlapingWith(coordinates.x,coordinates.y) && !playerShip.burstPowerUp.isTouched()){
                playerShip.burstPowerUp.setTouched();
                WarpController.getInstance().sendGameUpdate(TypePowerUp.BURST.toString());
            }else if(playerShip.regLifePowerUp.isOverlapingWith(coordinates.x,coordinates.y)  && !playerShip.regLifePowerUp.isTouched()){
                playerShip.regLifePowerUp.setTouched();
                WarpController.getInstance().sendGameUpdate(TypePowerUp.REGLIFE.toString());
            }else {
                playerShip.shoot(coordinates.x, coordinates.y);
                WarpController.getInstance().sendGameUpdate("SHOOT");
            }
        }

        WarpController.getInstance().sendGameUpdate(""+playerShip.getCenter().y);

        if(backToMainMenu)
            game.setScreen(new MainMenuScreen(game));
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
        playerShip.dispose();
        enemyShip.dispose();
    }

    @Override
    public void onWaitingStarted(String message) {

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
        timeToStartGame = MAX_TIME_TO_START_GAME;
        changeToStartGame = true;
    }

    @Override
    public void onConnectedWithServer(String message) {
        System.out.println(message);
        infoMessage = FontManager.getFromBundle("onConnectDone");
    }

    @Override
    public void onJoinedToRoom(String message) {
        System.out.println(message);
        infoMessage = FontManager.getFromBundle("onWaitPlayer");
    }

    @Override
    public void onGetLiveRoomInfoDone(String message) {
    }

    @Override
    public void onUserJoinedRoom(String message) {
        infoMessage = FontManager.getFromBundle("onPlayerConnected");
    }

    @Override
    public void onGameFinished(int code, boolean isRemote) {
    }

    @Override
    public void onGameUpdateReceived(String message) {
        if(message.equals(TypePowerUp.REGLIFE.toString())){
            enemyShip.regLifePowerUp.setTouched();
        }else if(message.equals(TypePowerUp.BURST.toString())){
            enemyShip.burstPowerUp.setTouched();
        }else if (message.equals("SHOOT")){
            enemyShip.shoot();
        }else if (message.equals("LEAVE")){
            state = GameState.WIN;
        }else{
            enemyYposition = Float.parseFloat(message);
        }
    }

    @Override
    public void onUserLeaveRoom() {
        WarpController.getInstance().sendGameUpdate("LEAVE");
        WarpController.getInstance().disconnect();
        backToMainMenu=true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            WarpController.getInstance().leaveRoom();
        }
        if(keycode == Input.Keys.A){
            WarpController.getInstance().leaveRoom();
        }
        return false;
    }
}
