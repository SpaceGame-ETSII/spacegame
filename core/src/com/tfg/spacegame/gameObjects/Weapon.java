package com.tfg.spacegame.gameObjects;

import com.tfg.spacegame.GameObject;

public class Weapon extends GameObject {

    //Objeto de juego que ha provocado el disparo
    private GameObject shooter;

    public Weapon(String nameTexture, int x, int y, GameObject shooter) {
        super(nameTexture,x,y);
        this.shooter = shooter;
    }

    public GameObject getShooter(){
        return shooter;
    }

    public void update(float delta) {
    }

}