package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;

import com.tfg.spacegame.gameObjects.multiplayerMode.MultiplayerShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PowerUp;


public class ShieldPowerUp extends PowerUp {

    private float timeProtecting;

    public ShieldPowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
        timeProtecting = 4f;
    }

    @Override
    public void act(float delta, MultiplayerShip g) {
        if(timeProtecting>0){
            if(!g.isProtected()){
                g.protectShip();
            }
            g.updateShield();
            timeProtecting-=delta;
        }else{
            g.unprotectShip();
        }
    }
}
