package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.tfg.spacegame.gameObjects.multiplayerMode.MultiplayerShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PowerUp;
import com.tfg.spacegame.utils.ShootsManager;

public class BurstPowerUp extends PowerUp {

    private final float BURST_TIME = 3f;
    private final float SHOOT_TIME = 0.2f;
    private float burstTime;
    private float shootTime;

    private boolean shooting;

    public BurstPowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
        burstTime = 0;
        shooting = false;
        shootTime = 0;
    }

    public boolean isShooting(){
        return shooting;
    }

    @Override
    public void act(float delta, MultiplayerShip g) {
        // Disparamos una ráfaga continuada de 3 segundos
        if(burstTime < BURST_TIME){
            if(shootTime >= SHOOT_TIME){
                ShootsManager.shootOneBasicMultiplayerWeapon(g);
                shootTime = 0;
            }else{
                shootTime+=delta;
            }
            burstTime+=delta;

            shooting = true;
        }else{
            shooting = false;
        }
    }
}
