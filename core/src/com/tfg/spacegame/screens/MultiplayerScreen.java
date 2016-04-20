package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.multiplayerMode.RivalShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.BurstPowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.RegLifePowerUp;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.enums.GameState;

public class MultiplayerScreen extends GameScreen{
    final SpaceGame game;

    // Nave del jugador
    public static PlayerShip playerShip;
    // Nave del rival
    public static RivalShip rivalShip;

    // Tiempo máximo para poder empezar la partida
    private final float MAX_TIME_TO_START_GAME = 5f;
    // Tiempo máximo para poder salir de la partida
    private final float MAX_TIME_TO_LEFT_GAME = 1f;

    private final int TIMES_TO_SEND_SAME_OPERATION = 5;
    private int times_sended_receive_damage_operation;

    // TODO Actualizate con el backgroundManager
    private Texture background;

    // Mensaje de información para mostrar al usuario
    private String infoMessage;

    // Tiempos -> de comienzo y de salida
    private float timeToStartGame;
    private float timeToLeftGame;

    // PowerUps del jugador
    private BurstPowerUp playerBurstPowerUp;
    private RegLifePowerUp playerRegLifePowerUp;

    // PowerUps del rival
    private BurstPowerUp rivalBurstPowerUp;
    private RegLifePowerUp rivalRegLifePowerUp;

    // Sabremos si el jugador abandonó la partida
    private boolean abandonPlayer;
    // Sabremos si el rival abandonó la partida
    private boolean abandonRival;

    // Mensajes de entrada y de salida del juego
    private MultiplayerMessage outcomeMessage;
    private MultiplayerMessage incomeMessage;

    private boolean leaveRoom;

    public MultiplayerScreen(final SpaceGame game, String roomId, Boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");

        outcomeMessage  = new MultiplayerMessage();
        incomeMessage = new MultiplayerMessage();

        state = GameState.READY;

        leaveRoom = false;

        times_sended_receive_damage_operation = 0;

        infoMessage = FontManager.getFromBundle("connectServer");

        timeToStartGame = MAX_TIME_TO_START_GAME;
        timeToLeftGame = MAX_TIME_TO_LEFT_GAME;

        playerShip  = new PlayerShip();
        rivalShip = new RivalShip();

        abandonRival = false;
        abandonPlayer = false;

        playerBurstPowerUp = new BurstPowerUp("burstPlayer", SpaceGame.width/3, 5);
        playerRegLifePowerUp = new RegLifePowerUp("regLifePlayer", SpaceGame.width/2, 5);

        rivalBurstPowerUp = new BurstPowerUp("burstEnemy",SpaceGame.width/3,SpaceGame.height - 55);
        rivalRegLifePowerUp = new RegLifePowerUp("regLifeEnemy",SpaceGame.width/2,SpaceGame.height-55);

        CollisionsManager.load();
        ShootsManager.load();
        CameraManager.loadShakeEffect(1f, CameraManager.NORMAL_SHAKE);

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
        // Lógica de espera para empezar la partida
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
        rivalShip.render();

        ShootsManager.render();

        playerBurstPowerUp.render();
        playerRegLifePowerUp.render();

        rivalBurstPowerUp.render();
        rivalRegLifePowerUp.render();
    }

    @Override
    public void updateStart(float delta) {

        CollisionsManager.update();
        ShootsManager.update(delta, playerShip);
        CameraManager.update(delta);

        // Actualizaremos la lógica por parte de la entrada del mensaje
        updateIncomeMessage(delta);
        // Actaulizaremos la lógica por parte de la salida del mensaje
        updateOutComeMessage(delta);

        if(playerBurstPowerUp.isTouched())
            playerBurstPowerUp.act(delta, playerShip);

        if(playerRegLifePowerUp.isTouched())
            playerRegLifePowerUp.act(delta, playerShip);

        if(rivalBurstPowerUp.isTouched())
            rivalBurstPowerUp.act(delta, rivalShip);

        if(rivalRegLifePowerUp.isTouched())
            rivalRegLifePowerUp.act(delta, rivalShip);

        // Reseteamos todas las operaciones
        outcomeMessage.resetOperations();
        incomeMessage.resetOperations();
    }

    private void updateIncomeMessage(float delta){
        // Obtenemos el mensaje de entrada
        incomeMessage = SpaceGame.googleServices.receiveGameMessage();

        if (incomeMessage.checkOperation(incomeMessage.MASK_LEAVE)) {
            if (!rivalShip.isDefeated())
                abandonRival = true;
            state = GameState.WIN;
        }
        if (incomeMessage.checkOperation(incomeMessage.MASK_SHOOT))
            rivalShip.shoot();
        if (incomeMessage.checkOperation(incomeMessage.MASK_BURST))
            rivalBurstPowerUp.setTouched();
        if (incomeMessage.checkOperation(incomeMessage.MASK_REG_LIFE))
            rivalRegLifePowerUp.setTouched();
        if(incomeMessage.checkOperation(incomeMessage.MASK_HAS_RECEIVE_DAMAGE)){
            rivalShip.receiveDamage();
        }

        if(rivalShip.isCompletelyDefeated()){
            state = GameState.WIN;
        }

        // Actualizamos la lógica del rival
        rivalShip.update(delta,incomeMessage.getPositionY());

        incomeMessage.resetOperations();
    }

    private void updateOutComeMessage(float delta){

        // Vamos a construir el mensaje de salir
        // Como vemos es muy parecido en principio al usado en el modo campaña

        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(playerShip.getX() + playerShip.getWidth());

        boolean canShipMove = false;
        if(!coordinates.equals(Vector3.Zero))
            canShipMove = true;

        playerShip.update(delta, coordinates.y , canShipMove);
        outcomeMessage.setPositionY(playerShip.getCenter().y);

        coordinates = TouchManager.getAnyXTouchGreaterThan(playerShip.getX() + playerShip.getWidth());

        if(!coordinates.equals(Vector3.Zero))

            if(playerBurstPowerUp.isOverlapingWith(coordinates.x,coordinates.y) && !playerBurstPowerUp.isTouched()){
                playerBurstPowerUp.setTouched();
                outcomeMessage.setOperation(outcomeMessage.MASK_BURST);
            }
            else if(playerRegLifePowerUp.isOverlapingWith(coordinates.x,coordinates.y)  && !playerRegLifePowerUp.isTouched()){
                playerRegLifePowerUp.setTouched();
                outcomeMessage.setOperation(outcomeMessage.MASK_REG_LIFE);
            }
            else{
                playerShip.shoot();
                outcomeMessage.setOperation(outcomeMessage.MASK_SHOOT);
            }

        if(playerShip.isUndamagable() || playerShip.isDefeated()){
            if(times_sended_receive_damage_operation <= TIMES_TO_SEND_SAME_OPERATION){
                outcomeMessage.setOperation(outcomeMessage.MASK_HAS_RECEIVE_DAMAGE);
                times_sended_receive_damage_operation++;
            }
        }else{
            times_sended_receive_damage_operation = 0;
        }

        if(leaveRoom){
            if(!playerShip.isDefeated())
                abandonPlayer = true;
            outcomeMessage.setOperation(outcomeMessage.MASK_LEAVE);
            state = GameState.LOSE;
        }

        if(playerShip.isCompletelyDefeated()){
            state = GameState.LOSE;
        }

        SpaceGame.googleServices.sendGameMessage(outcomeMessage.getForSendMessage());

        outcomeMessage.resetOperations();
    }

    @Override
    public void renderPause(float delta) {

    }

    @Override
    public void updatePause(float delta) {

    }

    @Override
    public void renderWin(float delta) {
        if(abandonRival)
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
        rivalShip.dispose();
        for(Shoot shoot: ShootsManager.shoots){
            shoot.dispose();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.BACK){
            leaveRoom = true;
        }
        return false;
    }
}
