package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;

public class Enemy extends GameObject {

    private boolean defeated;
    private Vector2 initialPosition;
    private int vitality;

    public Enemy(String textureName, int x, int y, int vitality) {
        super(textureName, x, y);
        initialPosition = new Vector2(x,y);
        this.vitality = vitality;
    }

    public void update(float delta) {
    }

    public void damage(int damage) {
        vitality -= damage;
        if (vitality <= 0)
            this.defeat();
    }

    public void defeat() {
        defeated = true;
    }

    public boolean isDefeated(){
        return defeated;
    }

    public Vector2 getInitialPosition(){
        return initialPosition;
    }

    public void collideWithShip() {}

    public void collideWithShoot(Shoot shoot) {}

}
