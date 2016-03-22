package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.tfg.spacegame.GameObject;

public abstract class PowerUp extends GameObject{

    private boolean touched;

    public PowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
        touched = false;
    }

    public abstract void act();

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(){
        touched = true;
    }
}
