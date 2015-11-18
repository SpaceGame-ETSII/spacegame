package com.tfg.spacegame.GameObjects;

import com.tfg.spacegame.GameObject;

public class Shoot extends GameObject{

    public static final float SPEED = 700;
    public GameObject shooter;

    public Shoot(GameObject shooter) {
        super("shoot", 0, 0);
        this.setX(shooter.getX()+shooter.getWidth());
        this.setY(shooter.getY()+shooter.getHeight()/2-getHeight()/2);

        this.shooter = shooter;
    }

    public void update(float delta) {

        this.setX(this.getX() + (SPEED * delta));
    }

}
