package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Blue extends Shoot {

    //Velocidad de movimiento
    public static final float SPEED = 150;

    //Efecto de particulas del fuego de propulsión
    private ParticleEffect propulsionEffect;

    //Indica el punto objetivo adonde se dirigirá la nave
    private float xTarget;
    private float yTarget;

    private float xCenter;
    private float yCenter;
    private float startOfParabole;

    public Blue(GameObject shooter, int x, int y, float yTarget) {
        super("blue_shoot",x,y,shooter);

        propulsionEffect = AssetsManager.loadParticleEffect("blue_propulsion_effect");
        xTarget = 700;
        this.yTarget = yTarget;

        if (shooter instanceof Ship) {
            startOfParabole = 250;
            xCenter = (xTarget - startOfParabole) / 2;
            yCenter = (yTarget + y) / 2;
        } else {
            startOfParabole = 550;
            xCenter = (startOfParabole - xTarget) / 2;
            yCenter = (y - yTarget) / 2;
        }


        this.updateParticleEffect();
        propulsionEffect.start();
    }

    public void update(float delta) {
        //Actualizamos el movimiento del disparo
        if (getShooter() instanceof Ship)
            if (this.getX() < startOfParabole) {
                this.setX(this.getX() + (SPEED * delta));
            } else if (this.getX() < xCenter && this.getY() < yCenter) {
                
            } else if (this.getX() < xTarget && this.getY() < yTarget) {

            } else {

            }
        else
            this.setX(this.getX() - (SPEED * delta));
        this.updateParticleEffect();
        propulsionEffect.update(delta);
    }

    public void updateParticleEffect() {
        propulsionEffect.getEmitters().first().setPosition(this.getX(),this.getY() + this.getHeight()/2);
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        propulsionEffect.draw(batch);
    }

    public void dispose(){
        super.dispose();
        propulsionEffect.dispose();
    }

}
