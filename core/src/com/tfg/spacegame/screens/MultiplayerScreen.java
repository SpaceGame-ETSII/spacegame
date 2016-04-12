package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.BurstPowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.RegLifePowerUp;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.enums.GameState;

public class MultiplayerScreen extends GameScreen{
    final SpaceGame game;

    public static PlayerShip playerShip;
    public static EnemyShip   enemyShip;
    public static float enemyYposition;
    private final float MAX_TIME_TO_START_GAME = 5f;
    private final float MAX_TIME_TO_LEFT_GAME = 1f;

    private Texture background;

    private String infoMessage;

    private float timeToStartGame;
    private float timeToLeftGame;

    private BurstPowerUp playerBurstPowerUp;
    private RegLifePowerUp playerRegLifePowerUp;

    private BurstPowerUp    enemyBurstPowerUp;
    private RegLifePowerUp  enemyRegLifePowerUp;

    private boolean abandonPlayer;
    private boolean abandonEnemy;
    private boolean leaveTheRoom;
    private MultiplayerMessage outcomeMessage;
    private MultiplayerMessage incomeEnemyMessage;
    private MultiplayerMessage incomePlayerMessage;

    public MultiplayerScreen(final SpaceGame game, String roomId, Boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");
        outcomeMessage  = new MultiplayerMessage();
        incomeEnemyMessage = new MultiplayerMessage();
        incomePlayerMessage = new MultiplayerMessage();
        state = GameState.READY;

        infoMessage = FontManager.getFromBundle("connectServer");

        timeToStartGame = MAX_TIME_TO_START_GAME;
        timeToLeftGame = MAX_TIME_TO_LEFT_GAME;

        leaveTheRoom = false;

        playerShip  = new PlayerShip();
        enemyShip   = new EnemyShip();

        abandonEnemy = false;
        abandonPlayer = false;

        playerBurstPowerUp = new BurstPowerUp("burstPlayer", SpaceGame.width/3, 5);
        playerRegLifePowerUp = new RegLifePowerUp("regLifePlayer", SpaceGame.width/2, 5);

        enemyBurstPowerUp    = new BurstPowerUp("burstEnemy",SpaceGame.width/3,SpaceGame.height - 55);
        enemyRegLifePowerUp  = new RegLifePowerUp("regLifeEnemy",SpaceGame.width/2,SpaceGame.height-55);

        CollissionsManager.load();
        ShootsManager.load();
        CameraManager.loadShakeEffect(1f,CameraManager.NORMAL_SHAKE);

        enemyYposition = enemyShip.getY();

        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);

        SpaceGame.googleServices.startQuickGame();

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
        if(SpaceGame.googleServices.canMultiplayerGameStart())
            FontManager.text.draw(SpaceGame.batch,infoMessage,SpaceGame.width/3,SpaceGame.height/2);
    }

    @Override
    public void updateReady(float delta) {
        if(SpaceGame.googleServices.canMultiplayerGameStart()){
            if(timeToStartGame > 0){
                infoMessage = FontManager.getFromBundle("startGame")+"  "+(int)timeToStartGame;
                timeToStartGame-=delta;
            }else {
                timeToStartGame = 0;
                state = GameState.START;
            }
        }

    }

    @Override
    public void renderStart(float delta) {
        playerShip.render();
        enemyShip.render();

        ShootsManager.render();

        playerBurstPowerUp.render();
        playerRegLifePowerUp.render();

        enemyBurstPowerUp.render();
        enemyRegLifePowerUp.render();
    }

    @Override
    public void updateStart(float delta) {

        enemyShip.update(delta);

        CollissionsManager.update();
        ShootsManager.update(delta, playerShip);
        CameraManager.update(delta);

        // Obtenemos un vector de coordenadas si está en la zona de movimiento de la nave
        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(playerShip.getX() + playerShip.getWidth());

        boolean canShipMove = false;
        if(!coordinates.equals(Vector3.Zero))
            canShipMove = true;

        playerShip.update(delta, coordinates.y, canShipMove);

        coordinates = TouchManager.getAnyXTouchGreaterThan(playerShip.getX() + playerShip.getWidth());

        if(!coordinates.equals(Vector3.Zero) && Gdx.input.justTouched()){
            if(playerBurstPowerUp.isOverlapingWith(coordinates.x,coordinates.y) && !playerBurstPowerUp.isTouched()){
                outcomeMessage.setBurstOperation();
                playerBurstPowerUp.setTouched();
            }else if(playerRegLifePowerUp.isOverlapingWith(coordinates.x,coordinates.y)  && !playerRegLifePowerUp.isTouched()){
                outcomeMessage.setRefLifeOperation();
                playerRegLifePowerUp.setTouched();
            }else {
                outcomeMessage.setShootOperation();
                playerShip.shoot();
            }
        }

        outcomeMessage.setPositionY(playerShip.getCenter().y);

        if(playerBurstPowerUp.isTouched())
            playerBurstPowerUp.act(delta, playerShip);

        if(playerRegLifePowerUp.isTouched())
            playerRegLifePowerUp.act(delta, playerShip);

        if(enemyBurstPowerUp.isTouched())
            enemyBurstPowerUp.act(delta, enemyShip);

        if(enemyRegLifePowerUp.isTouched())
            enemyRegLifePowerUp.act(delta, enemyShip);

        if(playerShip.isDefeated()){
            outcomeMessage.setLeaveOperation();
            state = GameState.LOSE;
            if(!playerShip.isDefeated())
                abandonPlayer = true;
        }

        SpaceGame.googleServices.sendMessageToOponent(outcomeMessage.getForSendMessage());
        outcomeMessage.resetOperations();
        updateEnemy();
    }

    public void updateEnemy() {
        incomeEnemyMessage.setPropertiesFromMessage(SpaceGame.googleServices.getMessageFromOponent());

        enemyYposition = incomeEnemyMessage.getPositionY();
        if(incomeEnemyMessage.isShootOperationActive()){
            enemyShip.shoot();
        }
        if(incomeEnemyMessage.isBurstOperationActive()){
            enemyBurstPowerUp.setTouched();
        }
        if(incomeEnemyMessage.isRefLifeOperationActive()){
            enemyRegLifePowerUp.setTouched();
        }
        if(incomeEnemyMessage.isLeaveOperationActive()){
            if (!enemyShip.isDefeated()) {
                abandonEnemy = true;
            }
            state = GameState.WIN;
        }
        incomeEnemyMessage.resetOperations();
    }

    @Override
    public void renderPause(float delta) {

    }

    @Override
    public void updatePause(float delta) {

    }

    @Override
    public void renderWin(float delta) {
        if(abandonEnemy)
            FontManager.draw(FontManager.getFromBundle("multiplayerGameEnemyAbandon"),SpaceGame.height/2 + 50);
        FontManager.draw(FontManager.getFromBundle("multiplayerGameWon"),SpaceGame.height/2);

        if(timeToLeftGame <= 0)
            FontManager.draw(FontManager.getFromBundle("multiplayerGameExit"),SpaceGame.height/2 - 50);
    }

    @Override
    public void updateWin(float delta) {
        if(timeToLeftGame > 0){
            timeToLeftGame-=delta;
        }else {
            if(Gdx.input.justTouched()){
                SpaceGame.googleServices.leaveRoom();
                ScreenManager.changeScreen(game, MainMenuScreen.class);
            }
        }
    }

    @Override
    public void renderLose(float delta) {
        if(abandonPlayer)
            FontManager.draw(FontManager.getFromBundle("multiplayerGamePlayerAbandon"),SpaceGame.height/2 - 50);
        FontManager.draw(FontManager.getFromBundle("multiplayerGameLoose"),SpaceGame.height/2);

        if(timeToLeftGame <= 0)
            FontManager.draw(FontManager.getFromBundle("multiplayerGameExit"),SpaceGame.height/2 + 50);
    }

    @Override
    public void updateLose(float delta) {
        if(timeToLeftGame > 0){
            timeToLeftGame-=delta;
        }else {
            if(Gdx.input.justTouched()){
                SpaceGame.googleServices.leaveRoom();
                ScreenManager.changeScreen(game, MainMenuScreen.class);
            }
        }
    }

    @Override
    public void disposeScreen() {
        playerShip.dispose();
        enemyShip.dispose();
        for(Shoot shoot: ShootsManager.shoots){
            shoot.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            outcomeMessage.setLeaveOperation();
            state = GameState.LOSE;
            if(!playerShip.isDefeated())
                abandonPlayer = true;
        }
        return false;
    }
}
