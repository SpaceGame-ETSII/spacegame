package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Blue extends Shoot {

    //Velocidad de movimiento
    public static final float SPEED = 250;

    //Efecto de particulas del fuego de propulsión
    private ParticleEffect propulsionEffect;

    //Indica el punto objetivo adonde se dirigirá la nave
    private float xTarget;

    private float xCenter;
    private float yCenter;
    private float xCenterOfCircle;
    private float diameter;
    private float degrees;

    private float speed_for_circle;
    private int direction;

    public Blue(GameObject shooter, int x, int y, float yTarget) {
        super("blue_shoot",x,y,shooter);

        propulsionEffect = AssetsManager.loadParticleEffect("blue_propulsion_effect");

        if (shooter instanceof Ship) {
            xTarget = 700;
            xCenter = ((xTarget - x) / 2) + x;
        } else {
            xTarget = 100;
            xCenter = ((x - xTarget) / 2) + xTarget;
        }

        if (yTarget >= y) {
            yCenter = ((yTarget - y) / 2) + y;
        } else {
            yCenter = ((y - yTarget) / 2) + yTarget;
        }

        diameter = Math.abs(yCenter - y);
        speed_for_circle = SPEED * 10 / diameter;
        direction = 1;

        this.updateParticleEffect();
        propulsionEffect.start();
    }

    public void update(float delta) {
        //Actualizamos el movimiento del disparo
        if (getShooter() instanceof Ship)
            if (Math.abs(xCenter - this.getX()) > diameter) {
                this.setX(this.getX() + (SPEED * delta));
            } else {
                if (xCenterOfCircle == 0) {
                    xCenterOfCircle = this.getX();
                    if (yCenter > this.getY()) {
                        degrees = 270;
                    } else {
                        degrees = 90;
                    }
                }

                if (yCenter > this.getY()) {
                    degrees += speed_for_circle * delta;
                } else {
                    degrees -= speed_for_circle * delta;
                }

                if (degrees < 360 && degrees > 0 && this.getX() < xCenter) {
                    this.setX(xCenterOfCircle + (diameter * MathUtils.cosDeg(degrees)));
                    this.setY(yCenter + (diameter * MathUtils.sinDeg(degrees)));
                } else {
                    if (degrees > 360) {
                        xCenterOfCircle = xCenter + diameter;
                        degrees = 180;
                        this.setX(xCenter);
                        this.setY((float) (yCenter + 0.1));
                    } else if (degrees < 0) {
                        xCenterOfCircle = xCenter + diameter;
                        degrees = 180;
                        this.setX(xCenter);
                        this.setY((float) (yCenter - 0.1));
                    } else {
                        this.setX(xCenterOfCircle + (diameter * MathUtils.cosDeg(degrees)));
                        this.setY(yCenter + (diameter * MathUtils.sinDeg(degrees)));
                    }
                }
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
