package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;


public class OrangeEnemy extends Enemy {

    private PartOfEnemy shield;
    private PartOfEnemy body;

    public OrangeEnemy(int x, int y) {
        super("orange_enemy", x, y, 9, AssetsManager.loadParticleEffect("basic_type5_destroyed"));

        body = new PartOfEnemy("orange_enemy_body",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        body.setX(((x+getWidth()/2)-body.getWidth()/2)+getWidth());
        body.setY((y+getHeight()/2)-body.getHeight()/2);

        shield = new PartOfEnemy("orange_enemy_shield",x,y,7,AssetsManager.loadParticleEffect("basic_destroyed"),this, false);
        shield.setX(body.getX() - 5);
        shield.setY(body.getY()+body.getHeight()/2 - shield.getHeight()/2+5);

        super.updateParticleEffect();
    }



    public void collideWithShip() {}

    public void collideWithShoot(Shoot shoot) {}

    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        body.render(batch);
        //shield.render(batch);

    }
}
