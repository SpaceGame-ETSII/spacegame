package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;

public class Type3 extends Enemy {

    //Velocidad a la que se moverá el enemigo
    private static final int SPEED = 80;

    //Valor por el que se multiplicará la velocidad inicial del enemigo antes de girar
    private static final int ACCELERATION = 2;

    //Indicará el diámetro del círculo que dibujará el enemigo
    private static final int DIAMETER = 150;

    //Indicará el grado del movimiento que irá variando con la ondulación
    private float degrees;

    //Indica la posición de la x inicial donde empezará a girar
    private float initialXToRotate;

    //Cantidad de pixeles que quedan para que el enemigo empiece a girar
    private int pixelsToMoveSlowly;

    public Type3(int x, int y) {
        super("tipo3", x, y);

        pixelsToMoveSlowly = 150;
        initialXToRotate = SpaceGame.width - (pixelsToMoveSlowly * ACCELERATION);
    }

    public void update(float delta){
        //Si aún no ha empezado a girar
        if (pixelsToMoveSlowly > 0) {
            this.setX(this.getX() - SPEED * ACCELERATION * delta);
            pixelsToMoveSlowly -= SPEED * ACCELERATION * delta;
        } else {
            // Si ya ha empezado a girar
            degrees += SPEED * delta;
            this.setX(initialXToRotate + (DIAMETER * MathUtils.cosDeg(degrees)));
            this.setY(this.getInitialPosition().y + (DIAMETER * MathUtils.sinDeg(degrees)));
            if (MathUtils.cosDeg(degrees) == 1 && MathUtils.sinDeg(degrees) == 0) {

            } else if (MathUtils.cosDeg(degrees) == 0 && MathUtils.sinDeg(degrees) == 1) {

            } else if (MathUtils.cosDeg(degrees) == -1 && MathUtils.sinDeg(degrees) == 0) {

            } else if (MathUtils.cosDeg(degrees) == 1 && MathUtils.sinDeg(degrees) == 0) {

            }

        }
    }

}
