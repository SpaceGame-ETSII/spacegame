package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.LandscapeShip;
import com.tfg.spacegame.utils.ShootsManager;

public class BurstPowerUp extends PowerUp{

    private final float BURST_TIME = 3f;
    private final float SHOOT_TIME = 0.2f;
    private float burstTime;
    private float shootTime;

    public BurstPowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
        burstTime = 0;
        shootTime = 0;
    }

    @Override
    public void act(float delta, LandscapeShip g) {
        // Disparamos una ráfaga continuada de 3 segundos
        if(burstTime < BURST_TIME){
            if(shootTime >= SHOOT_TIME){
                ShootsManager.shootOneBasicMultiplayerWeapon(g);
                shootTime = 0;
            }else{
                shootTime+=delta;
            }
            burstTime+=delta;
        }
    }
}
