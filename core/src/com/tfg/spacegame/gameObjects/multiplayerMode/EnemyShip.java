package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.BurstPowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.RegLifePowerUp;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class EnemyShip extends Enemy {

    //Indica la velocidad para el movimiento de la nave
    private final float MAX_SPEED = 10;

    private final int MAX_VITALITY = 5;

    public BurstPowerUp    burstPowerUp;
    public RegLifePowerUp  regLifePowerUp;

    public EnemyShip() {
        super("enemyShip", SpaceGame.width - (80+100), SpaceGame.height/2, 5, AssetsManager.loadParticleEffect("ship_defeated"));

        this.setY(this.getY() + this.getHeight()/2);

        burstPowerUp    = new BurstPowerUp("burstEnemy",SpaceGame.width/3,SpaceGame.height - 55);
        regLifePowerUp  = new RegLifePowerUp("regLifeEnemy",SpaceGame.width/2,SpaceGame.height-55);
    }

    public void update(float delta){

        float accel = MAX_SPEED *delta;

        if(Math.abs(MultiplayerScreen.enemyYposition - this.getCenter().y) > accel ){
            float diffY = MultiplayerScreen.enemyYposition - this.getCenter().y;
            this.setY(this.getY() + diffY*accel);
        }

        if(burstPowerUp.isTouched())
            burstPowerUp.act(delta, this);

        if(regLifePowerUp.isTouched())
            regLifePowerUp.act(delta, this);

    }

    public void healHalfLife(){
        this.setVitality(this.getVitality()+this.getVitality()/2);
        if(this.getVitality()>MAX_VITALITY)
            this.setVitality(MAX_VITALITY);
    }

    public void shoot(){
        ShootsManager.shootBurstBasicWeaponForShip(this);
    }

    public void render(){
        super.render();

        burstPowerUp.render();
        regLifePowerUp.render();
    }
}
