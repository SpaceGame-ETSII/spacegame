package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Red;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class RedEnemy extends Enemy{

    //Indica la velocidad a la que se moverá el enemigo
    public static final int SPEED = 300;

    //Frencuencia de disparo se refiere a cada cuantos segundos va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 1f;

    //Tiempo necesario para que dispare
    private float timeToShoot;

    //Variable de control para saber si ha disparado o no
    private boolean hasShooted;

    public RedEnemy(int x, int y) {
        super("red_enemy", x, y, 15, AssetsManager.loadParticleEffect("basic_type5_destroyed"));
        timeToShoot = FREQUENCY_OF_SHOOTING;
        hasShooted = false;
    }

    public void update(float delta){
        super.update(delta);

        if (!this.isDefeated()) {
            //Mientras el enemigo tenga distancia que recorrer hasta un punto fijo dado, vamos actualizando su posición
            if (this.getX() >= 450) {
                this.setX(this.getX() - SPEED * delta);
            } else {
                //Si hemos sobrepasado el tiempo para disparar
                if (timeToShoot < 0) {
                    //Si no ha disparado aún el enemigo
                    if (!hasShooted) {
                        //El enemigo dispara y lo hacemos saber a la variable de control
                        this.shoot();
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
    }

    public void shoot(){
        ShootsManager.shootRedWeapon(this);
    }

    public void collideWithShoot(Shoot shoot) {
        if (shoot instanceof Basic){
            this.damage(1);
        }else if (shoot instanceof Red){
            this.damage(3);
        }
    }
}
