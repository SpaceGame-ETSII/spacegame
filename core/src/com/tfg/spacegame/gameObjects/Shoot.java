package com.tfg.spacegame.gameObjects;

import com.tfg.spacegame.GameObject;

public class Shoot extends GameObject {

    //Objeto de juego que ha provocado el disparo
    private GameObject shooter;

    // Indica si el disparo ha chocado con algo
    private boolean shocked;

    // Indica si el disparo se puede borrar
    private boolean deletable;

    public Shoot(String nameTexture, int x, int y, GameObject shooter) {
        super(nameTexture,x,y);
        this.shooter = shooter;
        shocked = false;
    }

    public GameObject getShooter(){
        return shooter;
    }

    public boolean isShocked() { return shocked;}

    public void shock() { shocked = true; }

    public boolean isDeletable() { return deletable; }

    public void changeToDeletable() { deletable = true;}

    public void update(float delta) {
    }

    public void collideWithShip() {
    }

    public void collideWithEnemy(Enemy enemy) {}

    public void collideWithShoot(Shoot shoot) {}

}