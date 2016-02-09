package com.tfg.spacegame.gameObjects.enemies;

import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Red;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class RedEnemy extends Enemy{

    //Indica la velocidad a la que se moverá el enemigo
    public static final int SPEED = 200;

    //Frencuencia de disparo se refiere a cada cuantos segundos va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 3f;

    //Direccion de movimiento para el eje X
    private int directionX;

    //Direccion de movimiento para el eje Y
    private int directionY;

    //Punto de inicio para el movimiento romboide
    private static final float ENTRY_POINT = SpaceGame.width / 1.2f;

    //Punto de referencia sobre el eje X para el movimiento romboide
    private static final float X_REFERENCE = 500;

    //Punto de referencia sobre el eje Y para el movimiento romboide
    private static final float Y_REFERENCE = 200;

    //Tiempo necesario para que dispare
    private float timeToShoot;

    //Variable de control para saber si ha disparado o no
    private boolean hasShooted;

    public RedEnemy(int x, int y) {
        super("red_enemy", x, y, 15, AssetsManager.loadParticleEffect("basic_type5_destroyed"));
        timeToShoot = FREQUENCY_OF_SHOOTING;
        hasShooted = false;
        directionY=0;
    }

    public void update(float delta){
        super.update(delta);

        if (!this.isDefeated()) {
            //
            if (this.getX() >= ENTRY_POINT) {
                directionX = -1;
            }else {
                if (directionY==0){
                    directionY = 1;
                }
                if (this.getY() < Y_REFERENCE && directionX == -1) {
                    directionX = 1;
                    this.setY(Y_REFERENCE);
                } else if (this.getY() > Y_REFERENCE && directionX == 1) {
                    directionX = -1;
                    this.setY(Y_REFERENCE);
                }

                if (this.getX() > X_REFERENCE && directionY == -1) {
                    directionY = 1;
                    this.setX(X_REFERENCE);
                } else if (this.getX() < X_REFERENCE && directionY == 1) {
                    directionY = -1;
                    this.setX(X_REFERENCE);
                }
            }

            // Movemos al enemigo describiendo un patrón romboide
            this.setY(this.getY() + SPEED * delta * directionY);
            this.setX(this.getX() + SPEED * delta * directionX);

            //Si hemos sobrepasado el tiempo para disparar
            if (timeToShoot < 0) {
                //Si no ha disparado aún el enemigo
                if (!hasShooted) {
                    //El enemigo dispara y lo hacemos saber a la variable de control
                    //this.shoot();
                    hasShooted = true;
                } else {
                    //Reseteamos todos los tiempos y controles
                    hasShooted = false;
                    timeToShoot = FREQUENCY_OF_SHOOTING;
                }
            } else {
                //Actualizamos el tiempo para disparar
                timeToShoot -= delta;
            }
        }
    }

    public void shoot(){
        ShootsManager.shootRedWeapon(this);
    }

    public void collideWithShoot(Shoot shoot) {
        if (shoot instanceof Basic){
            this.damage(1);
        }else if (shoot instanceof Red && shoot.getShooter()!=this){
            this.damage(3);
        }
    }
}
