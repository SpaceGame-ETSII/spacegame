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
    // Posicion de la nave rival
    // TODO No se puede poner en la rivalShip?
    public static float rivalYposition;

    // Tiempo máximo para poder empezar la partida
    private final float MAX_TIME_TO_START_GAME = 5f;
    // Tiempo máximo para poder salir de la partida
    private final float MAX_TIME_TO_LEFT_GAME = 1f;

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

    // TODO Piensa si se puede hacer en un solo mensaje
    // Mensajes de entrada y de salida del juego
    private MultiplayerMessage outcomeMessage;
    private MultiplayerMessage incomeMessage;

    // Con esto sabremos quien es el que envía primero el primer mensaje
    // Ya que nuestro objetivo es que haya solamente uno por momento circulando
    private boolean firstSendingMessage;

    // Variable que controlará que hasta que no se reciba un ACK no puede enviar su mensaje
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
        rivalShip = new RivalShip();

        abandonRival = false;
        abandonPlayer = false;

        playerBurstPowerUp = new BurstPowerUp("burstPlayer", SpaceGame.width/3, 5);
        playerRegLifePowerUp = new RegLifePowerUp("regLifePlayer", SpaceGame.width/2, 5);

        rivalBurstPowerUp = new BurstPowerUp("burstEnemy",SpaceGame.width/3,SpaceGame.height - 55);
        rivalRegLifePowerUp = new RegLifePowerUp("regLifeEnemy",SpaceGame.width/2,SpaceGame.height-55);

        CollissionsManager.load();
        ShootsManager.load();
        CameraManager.loadShakeEffect(1f, CameraManager.NORMAL_SHAKE);

        rivalYposition = rivalShip.getY();

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

            // Aqui vamos a descubrir si este dispositivo o el contrario va a empezar a enviar el primer mensaje
            // TODO No se si esto va a funcionar hasta que se pruebe
            if(!firstSendingMessage)
                firstSendingMessage = SpaceGame.googleServices.calculateMasterSlave();
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

        rivalShip.update(delta);

        CollissionsManager.update();
        ShootsManager.update(delta, playerShip);
        CameraManager.update(delta);

        // Actualizaremos la lógica por parte de la entrada del mensaje
        updateIncomeMessage(delta);
        // Actaulizaremos la lógica por parte de la salida del mensaje
        updateOutComeMessage();

        if(playerBurstPowerUp.isTouched())
            playerBurstPowerUp.act(delta, playerShip);

        if(playerRegLifePowerUp.isTouched())
            playerRegLifePowerUp.act(delta, playerShip);

        if(rivalBurstPowerUp.isTouched())
            rivalBurstPowerUp.act(delta, rivalShip);

        if(rivalRegLifePowerUp.isTouched())
            rivalRegLifePowerUp.act(delta, rivalShip);

        // Reseteamos todas las operaciones
        // TODO No se puede hacer más sencillo? :(
        outcomeMessage.resetPlayerOperations();
        outcomeMessage.resetRivalOperations();

        incomeMessage.resetRivalOperations();
        incomeMessage.resetPlayerOperations();
    }

    private void updateIncomeMessage(float delta){
        // Obtenemos el mensaje de entrada
        incomeMessage = SpaceGame.googleServices.receiveGameMessage();

        // Preguntamos si por parte del jugador, es un mensaje de ACK
        if(incomeMessage.checkPlayerOperation(incomeMessage.MASK_ACK)){

            // Al ser un mensaje de ACK, podremos enviar nuestro mensaje de vuelta
            canSendMessage = true;

            // Como lo es, actualizamos la lógica del jugador

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

        // Actualizamos la lógica del rival
        // TODO Esto está mal por ahora. Usaré el tiempo medio de latencia para retrasar estas actualizaciones (si llegase a funcionar, claro)
        rivalYposition = incomeMessage.getRivalPositionY();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_SHOOT))
            rivalShip.shoot();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_BURST))
            rivalBurstPowerUp.setTouched();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_REG_LIFE))
            rivalRegLifePowerUp.setTouched();

        if(incomeMessage.checkRivalOperation(incomeMessage.MASK_LEAVE)){
            if (!rivalShip.isDefeated())
                abandonRival = true;
            state = GameState.WIN;
        }
    }

    private void updateOutComeMessage(){

        // Vamos a construir el mensaje de salir
        // Como vemos es muy parecido en principio al usado en el modo campaña

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

        // Construimos el mensaje para el rival, a partir, parcialmente, de lo que le ha llegado del
        // mensaje de entrada
        outcomeMessage.setRivalPositionY(rivalYposition);
        outcomeMessage.setRivalOperations(incomeMessage.getRivalOperations());
        // Lo marcamos como ACK para el rival (que en su caso será el player)
        outcomeMessage.setRivalOperation(incomeMessage.MASK_ACK);

        // Finalmente enviamos el mensaje
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
            outcomeMessage.setPlayerOperation(outcomeMessage.MASK_LEAVE);
        }
        return false;
    }
}
