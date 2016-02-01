package com.tfg.spacegame.gameObjects.weapons;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Weapon;

public class Yellow extends Weapon{

    private boolean active;

    private ParticleEffect shootEffect;

    public Yellow(int x, int y, GameObject shooter) {
        super("trasnparent", x, y, shooter);
    }


    public void update(float delta){


    }

    public void render(SpriteBatch batch){

    }
}
