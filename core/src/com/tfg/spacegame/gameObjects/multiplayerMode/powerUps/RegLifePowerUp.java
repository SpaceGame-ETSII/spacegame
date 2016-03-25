package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.gameObjects.multiplayerMode.PlayerShip;

public class RegLifePowerUp extends PowerUp{

    public RegLifePowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
    }

    @Override
    public void act(float delta, GameObject g) {
        if(g instanceof PlayerShip){
            PlayerShip playerShip = (PlayerShip)g;
            playerShip.healHalfLife();
        }else if(g instanceof EnemyShip){
            EnemyShip enemyShip = (EnemyShip)g;
            enemyShip.healHalfLife();
        }
    }
}
