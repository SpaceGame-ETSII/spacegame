package com.tfg.spacegame.gameObjects.multiplayerMode.powerUps;


import com.badlogic.gdx.graphics.Color;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public abstract class PowerUp extends GameObject{

    private boolean touched;

    public PowerUp(String textureName, int x, int y) {
        super(textureName, x, y);
        touched = false;
    }

    public abstract void act(float delta, GameObject g);

    public boolean isTouched(){
        return touched;
    }

    public void setTouched(){
        touched = true;
    }

    public void render(){
        if(isTouched()){
            Color c = SpaceGame.batch.getColor();
            c.a = 0.2f;
            SpaceGame.batch.setColor(c);
            super.render();
            c.a = 1.0f;
            SpaceGame.batch.setColor(c);
        }else{
            super.render();
        }
    }
}
