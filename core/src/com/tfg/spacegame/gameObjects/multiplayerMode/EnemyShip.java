package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class EnemyShip extends GameObject{


    public EnemyShip() {
        super("enemyShip", SpaceGame.width - (80+100), SpaceGame.height/2);
    }
}
