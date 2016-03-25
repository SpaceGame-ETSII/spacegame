package com.tfg.spacegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.GameScreen;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.*;
import com.tfg.spacegame.gameObjects.campaignMode.CampaignShip;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Inventary;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.utils.enums.GameState;


public class CampaignScreen extends GameScreen {

    private final SpaceGame game;

    //Objetos interactuables de la pantalla
    public static CampaignShip ship;
    private Inventary inventary;
    private DialogBox menuExitDialog;

    //Ayudan con la posición de la ventana cuando se abre y se cierra el inventario
    private int scrollingPosition;
    private static final int SCROLLING_SPEED = 100;

    public Texture background;

    // Vamos a controlar que touch está disparando y cual está controlando la nave
    // Seguiremos el siguiente modelo:
    // -1 para ningún touch asignado
    //  0 para el primer touch
    //  1 para el segundo touch
    public static int whichTouchIsShooting;
    public static int whichControlsTheShip;

    public CampaignScreen(SpaceGame game, String scriptLevel){
        this.game = game;
        scrollingPosition = 0;

        ShootsManager.load();
        CollissionsManager.load();
        EnemiesManager.load(scriptLevel);
        DamageManager.load();

        state = GameState.READY;

        //Creamos los objetos de juego
        ship = new CampaignShip("ship");
        inventary = new Inventary();

        background = AssetsManager.loadTexture("background2");

        menuExitDialog = new DialogBox();
        //Creamos los objetos para el diálgo de salida del modo campaña
        menuExitDialog.addElement("window", new GameObject("ventana", 200, 120));
        menuExitDialog.addElement("exit", new Button("buttonExit", 750, 430, null,true));
        menuExitDialog.addElement("cancel", new Button("buttonCancel", 425, 200, null,false));
        menuExitDialog.addElement("confirm", new Button("buttonConfirm", 325, 200, null,false));

        whichTouchIsShooting = -1;
        whichControlsTheShip = -1;

        //Preparamos un listener que si se desliza el dedo a la derecha se abre el inventario
        Gdx.input.setInputProcessor(new SimpleDirectionGestureDetector(new SimpleDirectionGestureDetector.DirectionListener() {

            public void onRight() {
                if (state.equals(GameState.START)) {
                    inventary.restart();
                    state = GameState.PAUSE;
                }
            }

            public void onLeft() {
                if (state.equals(GameState.PAUSE) && !inventary.isClosing()) {
                    inventary.setIsClosing(true);
                }
            }

            public void onDown() {
            }

            public void onUp() {
            }

        }));
    }

    @Override
    public void renderEveryState(float delta) {
        SpaceGame.batch.draw(background, scrollingPosition, 0);
        SpaceGame.batch.draw(background, background.getWidth() + scrollingPosition, 0);
    }

    @Override
    public void updateEveryState(float delta) {
        //Actualizamos la posición del scrolling
        scrollingPosition -= delta * SCROLLING_SPEED;
        if(scrollingPosition <= -background.getWidth())
            scrollingPosition = 0;
    }

    @Override
    public void renderPause(float delta) {
        inventary.render();
        ship.render();

        //En función de si estamos en el diálogo para salir o no veremos la ventana para salir del modo campaña
        if (menuExitDialog.isDialogIn()){
            menuExitDialog.renderElement("window");
            FontManager.drawText("exitModeQuestion",206,320);
            menuExitDialog.renderElement("confirm");
            menuExitDialog.renderElement("cancel");

            if (menuExitDialog.getElementButton("confirm").isPressed()) {
                menuExitDialog.setDialogIn(false);
                menuExitDialog.getElementButton("confirm").setPressed(false);
                game.setScreen(new MainMenuScreen(game));
            }

            if (menuExitDialog.getElementButton("cancel").isPressed()) {
                menuExitDialog.setDialogIn(false);
                menuExitDialog.getElementButton("cancel").setPressed(false);
            }
        }else{
            menuExitDialog.renderElement("exit");
        }
    }

    @Override
    public void updatePause(float delta) {

        if(!menuExitDialog.isDialogIn()){
            //Se hará una cosa u otra si el inventario está cerrándose o no
            if (inventary.isClosing()) {
                inventary.updateClosing(delta, ship);

                //Si el inventario ya no está cerrándose, volvemos a la partida
                if (!inventary.isClosing())
                    state = GameState.START;
            } else {
                Vector3 v = TouchManager.getFirstTouchPos();
                inventary.update(delta, ship, v.x, v.y);
            }
        }

        //Comprobamos sobre que botón pulsa el usuario y actualizamos las variables del diálgo en consecuencia
        if(TouchManager.isTouchedAnyToucher()){
            Vector3 v = TouchManager.getFirstTouchPos();
            if (menuExitDialog.getElementButton("exit").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.setDialogIn(true);
                menuExitDialog.getElementButton("cancel").setPressed(false);
                menuExitDialog.getElementButton("confirm").setPressed(false);
            }
            if (menuExitDialog.getElementButton("confirm").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.getElementButton("confirm").setPressed(true);
            }
            if (menuExitDialog.getElementButton("cancel").isOverlapingWith(v.x, v.y)) {
                menuExitDialog.getElementButton("cancel").setPressed(true);
            }
        }
    }

    @Override
    public void renderReady(float delta) {
        FontManager.drawText("tapToStart",370,240);

        if (Gdx.input.justTouched()) {
            state = GameState.START;
        }
    }

    @Override
    public void updateReady(float delta) {

    }

    @Override
    public void renderStart(float delta) {
        ship.render();

        EnemiesManager.render();
        ShootsManager.render();
    }

    @Override
    public void updateStart(float delta) {

        if (ship.isDefeated())
            state = GameState.LOSE;
        if(EnemiesManager.noMoreEnemiesToGenerateOrToDefeat())
            state = GameState.WIN;

        // Controlamos si algún touch ya ha dejado de ser pulsado
        if((whichControlsTheShip == 0 && !TouchManager.isFirstTouchActive()) || (whichControlsTheShip == 1 && !TouchManager.isSecondTouchActive()))
            whichControlsTheShip = -1;

        if((whichTouchIsShooting == 0 && !TouchManager.isFirstTouchActive()) || (whichTouchIsShooting == 1 && !TouchManager.isSecondTouchActive()))
            whichTouchIsShooting = -1;

        // Obtenemos un vector de coordenadas si está en la zona de movimiento de la nave
        Vector3 coordinates = TouchManager.getAnyXTouchLowerThan(ship.getX() + ship.getWidth());

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
        ship.update(delta, coordinates.y, canShipMove);

        // Si tocamos la pantalla disparamos
        // Obtenemos un vector de coordenadas. Este vector puede ser cualquier touch que cumpla
        // con la condición de que la posición X sea superior a la dada
        coordinates = TouchManager.getAnyXTouchGreaterThan(ship.getX() + ship.getWidth());
        // Preguntamos si el vector de coordenadas no es un vector de 0's. Si lo fuese es que el jugador
        // no ha tocado la pantalla. Además preguntamos si el toque ha sido solo de una sola vez
        if(!coordinates.equals(Vector3.Zero) && Gdx.input.justTouched() && whichTouchIsShooting == -1) {
            // Disparamos, pasando por parámetro las coordenadas del touch correspondiente
            ship.shoot(coordinates.x, coordinates.y);
            whichTouchIsShooting = TouchManager.assignWhichTouchCorresponds(coordinates);
        }

        //Realizamos la lógica de los objetos en juego
        CollissionsManager.update(delta, ship);
        EnemiesManager.update(delta);
        ShootsManager.update(delta, ship);
    }

    @Override
    public void renderWin(float delta) {
        if(ship.getX() > SpaceGame.width)
            FontManager.drawText("winGame",240,240);
        else
            ship.render();
    }

    @Override
    public void updateWin(float delta) {
        if(ship.getX() > SpaceGame.width){
            if (TouchManager.isTouchedAnyToucher())
                game.setScreen(new DemoMenuScreen(game));
        }else{
            ship.setX(ship.getX() + CampaignShip.SPEED*delta*3);
            ship.update(delta,ship.getY(),false);
        }
    }

    @Override
    public void renderLose(float delta) {
        FontManager.drawText("gameOver",370,240);
        ship.render();
    }

    @Override
    public void updateLose(float delta) {
        if (TouchManager.isTouchedAnyToucher() && ship.destroyEffect.isComplete()) {
            game.setScreen(new DemoMenuScreen(game));
        }
        ship.update(delta,ship.getY(),false);
    }

    @Override
    public void disposeScreen() {
        ship.dispose();
        for(Enemy enemy: EnemiesManager.enemies)
            enemy.dispose();
        for(Shoot shoot : ShootsManager.shoots)
            shoot.dispose();
        inventary.dispose();
        menuExitDialog.dispose();
        super.dispose();
    }
}