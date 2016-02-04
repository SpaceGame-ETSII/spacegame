package com.tfg.spacegame.gameObjects.shoots;


import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Shoot;

public class Yellow extends Shoot{

    private boolean active;


    private ParticleEffect shootEffect;

    public Yellow(GameObject shooter) {
        super("yellow_shoot", (int)(shooter.getX()+shooter.getWidth()+20), (int)shooter.getY(), shooter);

        active=true;
    }


    public void update(float delta){
        this.setY(getShooter().getY());
    }

    public void render(SpriteBatch batch){
        super.render(batch);
    }
}
