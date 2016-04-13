package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.ShootsManager;

public class RivalShip extends LandscapeShip {

    //Indica la velocidad para el movimiento de la nave
    private final float MAX_SPEED = 10;

    public RivalShip() {
        super("rivalShip", SpaceGame.width - (80+100), SpaceGame.height/2, 5);
        this.setY(this.getY() + this.getHeight()/2);
        cockpitOffsetX=18;
        cockpitOffsetY=22;
        fireEffect.getEmitters().first().getAngle().setHigh(0);
    }

    protected void updateParticleEffect() {
        fireEffect.getEmitters().first().setPosition(this.getX()+this.getWidth(),this.getY() + this.getHeight()/2 + 2);
        destroyEffect.getEmitters().first().setPosition(this.getCenter().x,this.getCenter().y);
    }

    public void update(float delta){
        super.update(delta);
        float accel = MAX_SPEED *delta;

        if(Math.abs(MultiplayerScreen.rivalYposition - this.getCenter().y) > accel ){
            float diffY = MultiplayerScreen.rivalYposition - this.getCenter().y;
            this.setY(this.getY() + diffY*accel);
        }
    }

    public void shoot(){
        ShootsManager.shootBurstBasicWeaponForShip(this);
    }

    public void render(){
        super.render();
    }
}
