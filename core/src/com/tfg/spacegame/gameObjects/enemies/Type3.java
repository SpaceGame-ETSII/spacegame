package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.ShootsManager;

public class Type3 extends Enemy {

    //Velocidad a la que se moverá el enemigo
    private static final int SPEED = 80;

    //Valor por el que se multiplicará la velocidad inicial del enemigo antes de girar
    private static final int ACCELERATION = 2;

    //Indicará el diámetro del círculo que dibujará el enemigo
    private static final int DIAMETER = 150;

    //Será el valor de referencia que servirá para la frecuencia de disparo
    private static final int FREQUENCY = 100;

    //Valor inicial al que se reiniciará el timeToShoot
    private static final float INITIAL_TIME_TO_SHOOT = 150.0f;

    //Indicará el grado del movimiento que irá variando con la ondulación
    private float degrees;

    //Indica la posición de la x inicial donde empezará a girar
    private float initialXToRotate;

    //Cantidad de pixeles que quedan para que el enemigo empiece a girar
    private int pixelsToMoveSlowly;

    //Tiempo que queda hasta el disparo
    private float timeToShoot;

    public Type3(int x, int y) {
        super("tipo3", x, y, 7);

        pixelsToMoveSlowly = 150;
        timeToShoot = INITIAL_TIME_TO_SHOOT;
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

            //Si el tiempo se ha acabado, el enemigo disparará
            if (timeToShoot <= 0) {
                this.shoot();
                timeToShoot = INITIAL_TIME_TO_SHOOT;
            } else {
                timeToShoot -= FREQUENCY * delta;
            }
        }
    }

    public void shoot(){
        ShootsManager.shootOneBasicWeapon(this);
    }

    public void collideWithShoot(Shoot shoot) {
        this.damage(1);
    }

}
