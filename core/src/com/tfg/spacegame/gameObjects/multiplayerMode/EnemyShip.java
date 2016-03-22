package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.BurstPowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.RegLifePowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.ShieldPowerUp;
import com.tfg.spacegame.screens.MultiplayerScreen;
import com.tfg.spacegame.utils.AssetsManager;

public class EnemyShip extends Enemy{

    //Indica la velocidad para el movimiento de la nave
    private  final float SPEED = 50;

    private ShieldPowerUp   shieldPowerUp;
    private BurstPowerUp    burstPowerUp;
    private RegLifePowerUp  regLifePowerUp;

    public EnemyShip() {
        super("enemyShip", SpaceGame.width - (80+100), SpaceGame.height/2, 5, AssetsManager.loadParticleEffect("ship_defeated"));

        burstPowerUp    = new BurstPowerUp("burstEnemy",SpaceGame.width/3,SpaceGame.height - 55);
        regLifePowerUp  = new RegLifePowerUp("regLifeEnemy",SpaceGame.width/2,SpaceGame.height-55);
        shieldPowerUp   = new ShieldPowerUp("shieldEnemy",(SpaceGame.width*2)/3,SpaceGame.height-55);
    }

    public void update(float delta){

        float accel = SPEED*delta;

        if(Math.abs(MultiplayerScreen.enemyYposition - this.getCenter().y) > accel ){
            float diffY = MultiplayerScreen.enemyYposition - this.getCenter().y;
            this.setY(this.getY() + diffY);
        }

        if(burstPowerUp.isTouched())
            burstPowerUp.act();

        if(regLifePowerUp.isTouched())
            regLifePowerUp.act();

        if(shieldPowerUp.isTouched())
            shieldPowerUp.act();
    }

    public void render(){
        super.render();

        burstPowerUp.render();
        regLifePowerUp.render();
        shieldPowerUp.render();
    }
}
