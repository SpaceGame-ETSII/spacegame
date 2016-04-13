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
    private MultiplayerMessage outcomeMessage;
    private MultiplayerMessage incomeMessage;
    // 0 - Nothing ; 1 - Master ; 2 - Slave
    private boolean firstSendingMessage;
    private boolean canSendMessage;


    public MultiplayerScreen(final SpaceGame game, String roomId, Boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");
        outcomeMessage  = new MultiplayerMessage();
        incomeMessage = new MultiplayerMessage();
        state = GameState.READY;
        firstSendingMessage = false;
        canSendMessage = false;

        infoMessage = FontManager.getFromBundle("connectServer");

        timeToStartGame = MAX_TIME_TO_START_GAME;
        timeToLeftGame = MAX_TIME_TO_LEFT_GAME;

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

            if(!firstSendingMessage)
                firstSendingMessage = SpaceGame.googleServices.calculateMasterSlave();
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

        updateIncomeMessage(delta);
        updateOutComeMessage();

        if(playerBurstPowerUp.isTouched())
            playerBurstPowerUp.act(delta, playerShip);

        if(playerRegLifePowerUp.isTouched())
            playerRegLifePowerUp.act(delta, playerShip);

        if(enemyBurstPowerUp.isTouched())
            enemyBurstPowerUp.act(delta, enemyShip);

        if(enemyRegLifePowerUp.isTouched())
            enemyRegLifePowerUp.act(delta, enemyShip);

        outcomeMessage.resetPlayerOperations();
        outcomeMessage.resetRivalOperations();

        incomeMessage.resetRivalOperations();
        incomeMessage.resetPlayerOperations();
    }

    private void updateIncomeMessage(float delta){
        incomeMessage = SpaceGame.googleServices.receiveGameMessage();

        // ACK PLAYER
        if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_ACK)){

            canSendMessage = true;

            playerShip.update(delta, incomeMessage.getPlayerPositionY(), true);

            if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_LEAVE)){
                state = GameState.LOSE;
                if(!playerShip.isDefeated())
                    abandonPlayer = true;
            }
            if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_SHOOT))
                playerShip.shoot();

            if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_BURST))
                playerBurstPowerUp.setTouched();

            if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_REG_LIFE))
                playerRegLifePowerUp.setTouched();
        }

        // ENEMY
        enemyYposition = incomeMessage.getRivalPositionY();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_SHOOT))
            enemyShip.shoot();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_BURST))
            enemyBurstPowerUp.setTouched();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_REG_LIFE))
            enemyRegLifePowerUp.setTouched();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_LEAVE)){
            if (!enemyShip.isDefeated())
                abandonEnemy = true;
            state = GameState.WIN;
        }
    }

    private void updateOutComeMessage(){
        // Obtenemos un vector de coordenadas si está en la zona de movimiento de la nave
        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(playerShip.getX() + playerShip.getWidth());

        if(!coordinates.equals(Vector3.Zero))
            outcomeMessage.setPlayerPositionY(playerShip.getCenter().y);

        coordinates = TouchManager.getAnyXTouchGreaterThan(playerShip.getX() + playerShip.getWidth());

        if(!coordinates.equals(Vector3.Zero) && Gdx.input.justTouched())

            if(playerBurstPowerUp.isOverlapingWith(coordinates.x,coordinates.y) && !playerBurstPowerUp.isTouched())
                outcomeMessage.setPlayerOperation(outcomeMessage.MASK_BURST);

            else if(playerRegLifePowerUp.isOverlapingWith(coordinates.x,coordinates.y)  && !playerRegLifePowerUp.isTouched())
                outcomeMessage.setPlayerOperation(outcomeMessage.MASK_REG_LIFE);

            else
                outcomeMessage.setPlayerOperation(outcomeMessage.MASK_SHOOT);

        if(playerShip.isDefeated())
            outcomeMessage.setPlayerOperation(outcomeMessage.MASK_LEAVE);

        // Build the enemy ACK
        outcomeMessage.setRivalPositionY(enemyYposition);
        outcomeMessage.setRivalOperations(incomeMessage.getRivalOperations());
        outcomeMessage.setRivalOperation(incomeMessage.MASK_ACK);

        if(canSendMessage || firstSendingMessage){
            SpaceGame.googleServices.sendGameMessage(outcomeMessage.getForSendMessage());
            firstSendingMessage = false;
            canSendMessage = false;
        }
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
            outcomeMessage.setPlayerOperation(outcomeMessage.MASK_LEAVE);
        }
        return false;
    }
}
