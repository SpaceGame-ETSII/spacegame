package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.badlogic.gdx.graphics.Texture;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.utils.ShootsManager;

public class PlayerShip extends MultiplayerShip {

    public final int SPEED = 20;


    public PlayerShip() {
        super("playerShip",80,0);
    }

    public void update(float delta, float y, boolean canShipMove) {
        super.update(delta);

        if(!this.isDefeated()){

            //Movimiento de la nave
            if (canShipMove) {
                float accel = SPEED *delta;

                if(Math.abs(y - this.getCenter().y) > accel ){
                    float diffY = y - this.getCenter().y;
                    this.setY(this.getY() + diffY*accel);
                }
            }
            //Controlamos si la nave se sale de la pantalla
            if (this.getY() < 0)
                this.setY(0);
            if (this.getY() > SpaceGame.height - getHeight())
                this.setY(SpaceGame.height - getHeight());
        }
    }

    public void shoot() {
        ShootsManager.shootBurstBasicWeaponForShip(this);
    }
}
