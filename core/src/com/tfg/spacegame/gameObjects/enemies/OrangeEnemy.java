package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
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
        shield.setX(getX() - 63);
        shield.setY(body.getY()+body.getHeight()/2 - shield.getHeight()/2);

        super.updateParticleEffect();
    }

    public PartOfEnemy getShield(){
        return shield;
    }

    public PartOfEnemy getBody(){
        return body;
    }

    public void collideWithShip() {}

    public void collideWithShoot(Shoot shoot) {

    }

    public Array<PartOfEnemy> getPartsOfEnemy() {
        Array<PartOfEnemy> result = new Array<PartOfEnemy>();
        result.add(getBody());
        result.add(getShield());

        return  result;
    }

    public void update(float delta){
        super.update(delta);
        super.updateParticleEffect();
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
    }

}
