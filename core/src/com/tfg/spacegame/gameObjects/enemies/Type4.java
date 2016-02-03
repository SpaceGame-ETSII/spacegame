package com.tfg.spacegame.gameObjects.enemies;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class Type4 extends Enemy{

    // El escudo del enemigo
    private GameObject shield;
    // El cuerpo del enemigo
    private GameObject body;

    private static final int SPEED = 100;

    // Frencuencia de disparo se refiere a cada cuanto tiempo va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 1f;
    // Cada cuanto tiempo va a abrir el cañon (es vulnerable)
    private static final float FREQUENCY_OF_OPEN_CANNON = 1.5f;
    // Cada cuanto tiempo cierra el cañon (vuelve a ser invulnerable)
    private static final float FREQUENCY_OF_CLOSE_CANNON = 1.2f;

    // Direccion de movimiento
    private int direction;

    // Tiempo necesario para que dispare
    private float timeToShoot;
    // Tiempo necesario para que abra el cañon (pasa a ser vulnerable)
    private float timeToOpenCannon;
    // Tiempo necesario para que se cierre el cañon (pasa a ser invulnerable)
    private float timeToCloseCannon;

    // Variable de control para saber si ha disparado o no
    private boolean hasShooted;

    public Type4(int x, int y) {
        super("type4", x, y, 7, AssetsManager.loadParticleEffect("basic_destroyed"));
        shield = new GameObject("type4_shield", x + 15,y - 37);
        body = new GameObject("type4_body", x + 35, y - 37);

        timeToShoot = FREQUENCY_OF_SHOOTING;
        timeToOpenCannon = FREQUENCY_OF_OPEN_CANNON;
        timeToCloseCannon = FREQUENCY_OF_CLOSE_CANNON;

        hasShooted = false;
    }

    public void update(float delta){
        super.update(delta);

        if (!this.isDefeated()) {
            // Si el cuerpo del enemigo supera el alto de la pantalla
            // La direccion debe ser negativa para ir hacía abajo
            if(body.getY() + body.getHeight() > SpaceGame.height){
                direction = -1;
            }
            // Si en cambio es inferior a la parte inferior de la pantalla
            // La direccion debe ser positiva para ir hacía arriba
            else if(body.getY() < 0 ){
                direction = 1;
            }

            // Movemos las tres partes del enemigo
            this.setY(this.getY() + SPEED * delta * direction);
            body.setY(body.getY() + SPEED * delta * direction);
            shield.setY(shield.getY() + SPEED * delta * direction);

            // Si el tiempo de espera para que se abra el cañon ha concluido
            if(timeToOpenCannon < 0){
                // Si el tiempo de espera para que dispare el enemigo ha concluido
                if(timeToShoot < 0){
                    // Si no ha disparado aún el enemigo
                    if(!hasShooted){
                        // El enemigo dispara y lo hacemos saber a la variable de control
                        this.shoot();
                        hasShooted = true;
                    }
                    // Si ya ha disparado
                    else {
                        // Si el tiempo necesario de espera para que se cierre el cañon ha concluido
                        if(timeToCloseCannon < 0){
                            // Reseteamos todos los tiempos y controles
                            hasShooted = false;
                            timeToOpenCannon = FREQUENCY_OF_OPEN_CANNON;
                            timeToShoot = FREQUENCY_OF_SHOOTING;
                            timeToCloseCannon = FREQUENCY_OF_CLOSE_CANNON;
                        }else
                            timeToCloseCannon -= delta;
                    }
                }else
                    timeToShoot -=delta;
            }else
                timeToOpenCannon -= delta;
        }
    }

    public void shoot(){
        ShootsManager.shootOneBasicWeapon(this);
    }

    public void render(SpriteBatch batch){
        // Mientras que el tiemp ode espera del cañon no ha concluido
        // No es necesario mostrar el cañon
        if(timeToOpenCannon < 0)
            super.render(batch);
        shield.render(batch);
        body.render(batch);
    }

    public void dispose(){
        super.dispose();
        shield.dispose();
        body.dispose();
    }
}
