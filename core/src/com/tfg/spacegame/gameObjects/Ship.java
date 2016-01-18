package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;

public class Ship extends GameObject {

    public static final float SPEED = 800;
    private int vitality;

    //Variable usada para hacer la nave invulnerable cuando es golpeada
    private boolean undamagable;

    //Se usará como contador para volver la nave vulnerable
    private float timeToUndamagable;

    public Ship() {
        super("ship", 0, 0);
        vitality = 5;
        timeToUndamagable = 3.0f;
        this.setY(SpaceGame.height/2 - getHeight()/2);
    }

    public void update(float delta, float x, float y) {
        //Movimiento de la nave
        if (Gdx.input.isTouched() && y < (this.getY() + this.getHeight() / 2) && x < (this.getX() + this.getWidth()))
            this.setY(this.getY() - (SPEED * delta));
        if (Gdx.input.isTouched() && y > (this.getY() + this.getHeight() / 2) && x < (this.getX() + this.getWidth()))
            this.setY(this.getY() + (SPEED * delta));

        //Controlamos si la nave se sale de la pantalla
        if (this.getY() < 0)
            this.setY(0);
        if (this.getY() > SpaceGame.height - getHeight())
            this.setY(SpaceGame.height - getHeight());

        //Si la nave está en estado invulnerable, el contador se reduce
        if (undamagable)
            timeToUndamagable -= delta;
            if (timeToUndamagable <= 0)
                this.changeToDamagable();
    }

    public int getVitality() {
        return vitality;
    }

    public void receiveDamage() {
        vitality--;
        undamagable = true;
    }

    public boolean isUndamagable() {
        return undamagable;
    }

    public void changeToDamagable() {
        undamagable = false;
        timeToUndamagable = 3.0f;
    }

}
