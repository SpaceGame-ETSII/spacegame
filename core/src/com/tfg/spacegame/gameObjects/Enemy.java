package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;

public class Enemy extends GameObject {

    private boolean defeated;
    private Vector2 initialPosition;

    public Enemy(String textureName, int x, int y) {
        super(textureName, x, y);
        initialPosition = new Vector2(x,y);
    }

    public void update(float delta) {
    }

    public void restart() {
    }

    public void defeat() {
    }

    public boolean isDefeated(){
        return defeated;
    }

    public void setDefeated(boolean b){
        this.defeated = b;
    }

    public Vector2 getInitialPosition(){
        return initialPosition;
    }

}
