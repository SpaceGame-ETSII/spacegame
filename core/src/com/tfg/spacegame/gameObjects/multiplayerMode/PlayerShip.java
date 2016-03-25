package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.BurstPowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.PowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.RegLifePowerUp;
import com.tfg.spacegame.gameObjects.multiplayerMode.powerUps.ShieldPowerUp;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class PlayerShip extends Ship {

    public BurstPowerUp    burstPowerUp;
    public RegLifePowerUp  regLifePowerUp;
    public ShieldPowerUp   shieldPowerUp;

    public PlayerShip() {
        super("playerShip");

        burstPowerUp = new BurstPowerUp("burstPlayer", SpaceGame.width/3, 5);
        regLifePowerUp = new RegLifePowerUp("regLifePlayer", SpaceGame.width/2, 5);
        shieldPowerUp = new ShieldPowerUp("shieldPlayer", (SpaceGame.width*2/3) , 5);
    }

    public void update(float delta, float y, boolean canShipMove){
        super.update(delta, y, canShipMove);

        if(burstPowerUp.isTouched())
            burstPowerUp.act(delta, this);

        if(regLifePowerUp.isTouched())
            regLifePowerUp.act(delta, this);

        if(shieldPowerUp.isTouched())
            shieldPowerUp.act(delta, this);
    }

    public void healHalfLife(){
        this.damageReceived-=damageReceived/2;
        if(damageReceived<0)
            damageReceived=0;
        cockpit = AssetsManager.loadTexture("cockpit_damage" + damageReceived);
    }


    public void render(){
        super.render();

        burstPowerUp.render();
        regLifePowerUp.render();
        shieldPowerUp.render();
    }

}
