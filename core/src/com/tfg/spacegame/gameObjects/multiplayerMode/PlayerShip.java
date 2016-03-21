package com.tfg.spacegame.gameObjects.multiplayerMode;


import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShootsManager;

public class PlayerShip extends Ship {

    //Indica la velocidad para el movimiento de la nave
    public static final float SPEED = 50;

    public PlayerShip() {
        super("playerShip");
    }

}
