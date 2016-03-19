package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;

public class PlayerShip extends GameObject{


    public PlayerShip() {
        super("playerShip", 80, SpaceGame.height/2);
    }
}
