package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class Shoot extends GameObject{

    public static final float SPEED = 700;
    public boolean isShooted;
    public GameObject shooter;

    public Shoot(GameObject shooter) {
        super("shoot", 0, 0);
        isShooted = false;
        this.shooter = shooter;
    }

    public void update(float delta, float x, float y) {
        //Colocamos el disparo cuando éste se realiza
        if (Gdx.input.isTouched(1) && !isShooted) {
            this.setX(shooter.getX() + shooter.getWidth());
            this.setY(shooter.getY() + 12);
            isShooted = true;
        }

        //Controlamos la situación cuando el disparo intenta salir de la pantalla
        if (this.getX() > SpaceGame.width)
            isShooted = false;

        //Si el disparo se ha realizado, movemos el tiro 20 píxeles a la derecha
        if (isShooted)
            this.setX(this.getX() + (SPEED * delta));
    }

    //Reiniciará el estado del disparo
    public void restart() {
        isShooted = false;
        this.setX(1000);
    }

}
