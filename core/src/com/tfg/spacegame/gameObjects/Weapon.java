package com.tfg.spacegame.gameObjects;

import com.tfg.spacegame.GameObject;

public class Weapon extends GameObject{


    private GameObject shooter;


    public Weapon(String nameWeapon, int x, int y, GameObject shooter) {
        super(nameWeapon,x,y);
        this.shooter = shooter;
    }

    public GameObject getShooter(){
        return shooter;
    }

    public void update(float delta){
    }

}