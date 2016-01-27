package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.utils.ShootsManager;

public class Type5 extends Enemy{

    //Indica la velocidad a la que se moverá el enemigo hasta un punto fijo
    public static final int SPEED = 50;
    //Frencuencia de disparo se refiere a cada cuanto tiempo va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 3f;
    //Indicará el número de píxeles que deberá recorrer el enemigo hasta detenerse
    private int pixelsToMove;
    //Tiempo necesario para que dispare
    private float timeToShoot;
    //Variable de control para saber si ha disparado o no
    private boolean hasShooted;


    public Type5(int x, int y) {
        super("enemigo_basico_tipo5", x, y);
        pixelsToMove = 580;
        timeToShoot = FREQUENCY_OF_SHOOTING;
        hasShooted = false;
    }

    public void update(float delta){
        //Mientras el enemigo tenga distancia que recorrer, vamos actualizando su posición
        if (pixelsToMove > 0) {
            this.setX(this.getX() - SPEED * delta);
            pixelsToMove -= SPEED*delta;
        }else{
            if(timeToShoot < 0){
                // Si no ha disparado aún el enemigo
                if(!hasShooted){
                    // El enemigo dispara y lo hacemos saber a la variable de control
                    this.shoot();
                    hasShooted = true;
                } else {
                    // Reseteamos todos los tiempos y controles
                    hasShooted = false;
                    timeToShoot = FREQUENCY_OF_SHOOTING;
                }
            }else
                timeToShoot -=delta;
        }
    }

    public void shoot(){
        ShootsManager.shootOneType5Weapon(this);
    }

}
