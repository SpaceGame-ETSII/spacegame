package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;
import com.tfg.spacegame.utils.AssetsManager;

public class RegLifePowerUp extends PowerUp{

    public RegLifePowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
    }

    @Override
    public void act(float delta, LandscapeShip ship) {
        int damRec = ship.getDamageReceived();
        damRec-=damRec/2;
        if(damRec<0)
            damRec=0;

        ship.setDamageReceived(damRec);
    }
}
