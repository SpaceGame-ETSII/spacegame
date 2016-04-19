package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.utils.ShootsManager;

public class PlayerShip extends LandscapeShip {

    public final float SPEED = 20;
    private boolean beenDamage;

    public PlayerShip() {
        super("playerShip",80,0,5);
    }

    public void update(float delta, float y, boolean canShipMove) {
        super.update(delta);
        beenDamage = false;

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

    public boolean hasBeenDamage(){
        return beenDamage;
    }

    public void setBeenDamage(boolean b){
        beenDamage = b;
    }

    public void shoot() {
        ShootsManager.shootBurstBasicWeaponForShip(this);
    }
}
