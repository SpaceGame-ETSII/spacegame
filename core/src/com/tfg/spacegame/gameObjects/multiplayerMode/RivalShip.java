package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.ShootsManager;

public class RivalShip extends MultiplayerShip {

    public RivalShip() {
        super("rivalShip", SpaceGame.width - (80+100), SpaceGame.height/2);
        this.setY(this.getY() + this.getHeight()/2);
        cockpitOffsetX=18;
        cockpitOffsetY=22;
        fireEffect.getEmitters().first().getAngle().setHigh(0);
    }

    protected void updateParticleEffect() {
        fireEffect.getEmitters().first().setPosition(this.getX()+this.getWidth(),this.getY() + this.getHeight()/2 + 2);
        destroyEffect.getEmitters().first().setPosition(this.getCenter().x,this.getCenter().y);
    }

    public void update(float delta, float positionY){
        super.update(delta);
        this.setY(positionY - this.getHeight()/2);
    }

    public void shoot(){
        ShootsManager.shootBurstBasicWeaponForShip(this);
    }
}
