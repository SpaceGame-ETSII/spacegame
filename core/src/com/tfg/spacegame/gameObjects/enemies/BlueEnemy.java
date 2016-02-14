package com.tfg.spacegame.gameObjects.enemies;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Blue;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class BlueEnemy extends Enemy {

    //Indica la velocidad a la que se moverá el enemigo
    public static final int SPEED = 200;

    //Valor inicial que tendrá el contador de disparo cada vez que se reinicie
    public static final int INITIAL_COUNTER = 300;

    //Indica la dirección de de movimiento
    private int direction;

    //Indicará cuándo a partir de cuándo puede disparar el enemigo
    private boolean isReady;

    //Contador que usaremos para saber cuándo disparar
    private float counter;

    public BlueEnemy(int x, int y) {
        super("blue_enemy", x, y, 15, AssetsManager.loadParticleEffect("blue_destroyed"));

        //Inicialmente lo pondremos que va hacia arriba, sólo por darle un valor válido
        direction = 1;

        isReady = false;
        counter = INITIAL_COUNTER;
    }

    public void update(float delta){
        super.update(delta);

        if (!this.isDefeated()) {

            // Evitamos que el enemigo se salga de la pantalla tanto por arriba como por abajo
            if(this.getY() + this.getHeight() > SpaceGame.height){
                direction = -1;
            } else if(this.getY() < 0){
                direction = 1;
            } else {
                //Si el enemigo estaba fuera de la pantalla, cambiamos por tanto su estado para que pueda disparar
                isReady = true;
            }

            if (isReady && counter <= 0) {
                this.shoot();
                counter = INITIAL_COUNTER;
            } else {
                counter -= delta * SPEED;
            }

            //Movemos el enemigo
            this.setY(this.getY() + (SPEED * delta * direction));
        }
    }

    public void shoot(){
        ShootsManager.shootBlueWeapon(this, 200);
    }

    public void collideWithShoot(Shoot shoot) {
        //Si el enemigo es alcanzado por un disparo de tipo básico, sólo recibirá un punto de daño
        if (shoot instanceof Basic){
            this.damage(1);
        }else
            //Si por el contrario, es alcanzado por un disparo de tipo azul, perderá tres puntos de vida
            if (shoot instanceof Blue){
                this.damage(3);
            }
    }

}
