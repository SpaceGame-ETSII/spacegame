package com.tfg.spacegame.gameObjects.enemies;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.utils.ShootsManager;

public class Type4 extends Enemy{

    private GameObject shield;

    private static final int SPEED = 100;
    private static final float TIMETOSHOOT_DEFAULT = 2f;

    private int direction;

    private float timeToShoot;

    public Type4(int x, int y) {
        super("type4", x, y);
        shield = new GameObject("type4_shield",x-20,y);
        timeToShoot = TIMETOSHOOT_DEFAULT;
    }

    public void update(float delta){
        if(this.getY() + this.getHeight() > SpaceGame.height){
            direction = -1;
        }else if(this.getY() < 0 ){
            direction = 1;
        }
        this.setY(this.getY() + SPEED * delta * direction);
        shield.setY(shield.getY() + SPEED * delta * direction);

        if(timeToShoot < 0){
            timeToShoot = TIMETOSHOOT_DEFAULT;
            this.shoot();
        }else{
            timeToShoot -= delta;
        }
    }

    public void shoot(){
        ShootsManager.shootOneBasicWeapon(this);
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        shield.render(batch);
    }

    public void dispose(){
        super.dispose();
        shield.dispose();
    }
}
