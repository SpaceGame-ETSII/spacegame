package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
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

public class MultiplayerScreen extends GameScreen implements WarpListener{
    final SpaceGame game;

    private PlayerShip  playerShip;
    private EnemyShip   enemyShip;
    public static float enemyYposition;

    private Texture background;

    // Vamos a controlar que touch está disparando y cual está controlando la nave
    // Seguiremos el siguiente modelo:
    // -1 para ningún touch asignado
    //  0 para el primer touch
    //  1 para el segundo touch
    public static int whichTouchIsShooting;
    public static int whichControlsTheShip;

    public MultiplayerScreen(final SpaceGame game, String roomId, boolean createRoom){
        this.game = game;

        background = AssetsManager.loadTexture("background");

        state = GameState.PAUSE;

        playerShip  = new PlayerShip();
        enemyShip   = new EnemyShip();

        EnemiesManager.loadMultiplayerEnemies(enemyShip);
        CollissionsManager.load();
        ShootsManager.load();

        enemyYposition = enemyShip.getY();

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

        whichTouchIsShooting = -1;
        whichControlsTheShip = -1;
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

        FontManager.text.draw(SpaceGame.batch,state.toString(),100,100);
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
        playerShip.render();

        EnemiesManager.render();
        ShootsManager.render();
    }

    @Override
    public void updateStart(float delta) {
        EnemiesManager.enemies.first().update(delta);
        CollissionsManager.update(delta, playerShip);
        ShootsManager.update(delta, playerShip);

        // Controlamos si algún touch ya ha dejado de ser pulsado
        if((whichControlsTheShip == 0 && !TouchManager.isFirstTouchActive()) || (whichControlsTheShip == 1 && !TouchManager.isSecondTouchActive()))
            whichControlsTheShip = -1;

        if((whichTouchIsShooting == 0 && !TouchManager.isFirstTouchActive()) || (whichTouchIsShooting == 1 && !TouchManager.isSecondTouchActive()))
            whichTouchIsShooting = -1;

        // Obtenemos un vector de coordenadas si está en la zona de movimiento de la nave
        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(playerShip.getX() + playerShip.getWidth());

        // A priori la nave no puede moverse
        boolean canShipMove = false;
        // Obtenemos a priori un touch que pudiera controlar la nave
        int whoCouldControlTheShip = TouchManager.assignWhichTouchCorresponds(coordinates);

        // Controlamos si la nave puede moverse, esto es:
        // Si las coordenadas pertenecen a algún touch
        // Si es la primera vez que los asignadores estan en el estado inicial
        // Si el posible controlador de la nave no es el mismo touch que uno que esté disparando
        if(coordinates.y != 0 && (whoCouldControlTheShip != whichTouchIsShooting || whoCouldControlTheShip == -1 && whichTouchIsShooting == -1)) {
            canShipMove = true;
            whichControlsTheShip = TouchManager.assignWhichTouchCorresponds(coordinates);
        }
        // Actualizamos la nave pasando la posible coordenada de movimiento y el resultado
        // de preguntar la condicion de movimiento
        playerShip.update(delta, coordinates.y, canShipMove);

        // Si tocamos la pantalla disparamos
        // Obtenemos un vector de coordenadas. Este vector puede ser cualquier touch que cumpla
        // con la condición de que la posición X sea superior a la dada
        coordinates = TouchManager.getAnyXTouchGreaterThan(playerShip.getX() + playerShip.getWidth());
        // Preguntamos si el vector de coordenadas no es un vector de 0's. Si lo fuese es que el jugador
        // no ha tocado la pantalla. Además preguntamos si el toque ha sido solo de una sola vez
        if(!coordinates.equals(Vector3.Zero) && Gdx.input.justTouched() && whichTouchIsShooting == -1) {
            // Disparamos, pasando por parámetro las coordenadas del touch correspondiente
            playerShip.shoot(coordinates.x,coordinates.y);
            whichTouchIsShooting = TouchManager.assignWhichTouchCorresponds(coordinates);
        }

        WarpController.getInstance().sendGameUpdate(playerShip.getY()+"");
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
        System.out.println(message);
        state = GameState.READY;
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
        state = GameState.START;
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
        enemyYposition = Float.parseFloat(message);
    }
}
