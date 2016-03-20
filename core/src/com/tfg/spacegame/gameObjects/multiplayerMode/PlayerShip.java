package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;

public class PlayerShip extends GameObject{

    //Indica la velocidad para el movimiento de la nave
    public static final float SPEED = 50;

    public PlayerShip() {
        super("playerShip", 80, SpaceGame.height/2);
    }

    public void update(float delta, float y){
        if (y < (this.getY() + this.getHeight() / 2))
            this.setY(this.getY() - (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
        if (y > (this.getY() + this.getHeight() / 2))
            this.setY(this.getY() + (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
    }
}
