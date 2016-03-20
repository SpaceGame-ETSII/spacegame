package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class EnemyShip extends GameObject{

    //Indica la velocidad para el movimiento de la nave
    public static final float SPEED = 50;

    public EnemyShip() {
        super("enemyShip", SpaceGame.width - (80+100), SpaceGame.height/2);
    }

    public void update(float delta, float y){
        if (y < (this.getY() + this.getHeight() / 2))
            this.setY(this.getY() - (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
        if (y > (this.getY() + this.getHeight() / 2))
            this.setY(this.getY() + (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
    }
}
