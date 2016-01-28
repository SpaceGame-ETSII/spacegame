package com.tfg.spacegame.gameObjects;

import com.tfg.spacegame.GameObject;

public class Shoot extends GameObject {

    //Objeto de juego que ha provocado el disparo
    private GameObject shooter;

    public Shoot(String nameTexture, int x, int y, GameObject shooter) {
        super(nameTexture,x,y);
        this.shooter = shooter;
    }

    public GameObject getShooter(){
        return shooter;
    }

    public void update(float delta) {
    }

    public void collideWithShip() {
    }

    public void collideWithEnemy(Enemy enemy) {}

    public void collideWithShoot(Shoot shoot) {}

}