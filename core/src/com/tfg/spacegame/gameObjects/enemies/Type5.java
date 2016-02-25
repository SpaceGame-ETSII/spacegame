package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.shoots.BigShoot;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.ShapeRendererManager;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.gameObjects.Shoot;

public class Type5 extends Enemy{

    //Indica la velocidad a la que se moverá el enemigo
    public static final int SPEED = 50;

    //Frencuencia de disparo se refiere a cada cuantos segundos va a disparar
    private static final float FREQUENCY_OF_SHOOTING = 3f;

    //Tiempo necesario para que dispare
    private float timeToShoot;

    //Variable de control para saber si ha disparado o no
    private boolean hasShooted;

    //Variable para el efecto de partículas que indica si el enemigo esta preparado para disparar
    private ParticleEffect shootEffectWarning;

    public Type5(int x, int y) {
        super("enemigo_basico_tipo5", x, y, 15, AssetsManager.loadParticleEffect("basic_type5_destroyed"));
        timeToShoot = FREQUENCY_OF_SHOOTING;
        hasShooted = false;

        float[] vertices = new float[20];

        vertices[0] = 0;
        vertices[1] = 57;

        vertices[2] = 0;
        vertices[3] = 144;

        vertices[4] = 56;
        vertices[5] = 200;

        vertices[6] = 223;
        vertices[7] = 200;

        vertices[8] = 275;
        vertices[9] = 148;

        vertices[10] = 319;
        vertices[11] = 148;

        vertices[12] = 319;
        vertices[13] = 53;

        vertices[14] = 275;
        vertices[15] = 53;

        vertices[16] = 223;
        vertices[17] = 0;

        vertices[18] = 56;
        vertices[19] = 0;

       // this.getLogicShape().setVertices(vertices);
        //Creamos el efecto de particulas
        shootEffectWarning = AssetsManager.loadParticleEffect("warning_shoot_type5_effect");
        this.updateParticleEffect();

        //Iniciamos el efecto de partículas
        shootEffectWarning.start();
    }

    public void update(float delta){
        super.update(delta);

        if (!this.isDefeated()) {
            //Mientras el enemigo tenga distancia que recorrer hasta un punto fijo dado, vamos actualizando su posición
            if (this.getX() >= 450) {
                this.setX(this.getX() - SPEED * delta);
            } else {
                //Si hemos sobrepasado el tiempo para disparar
                if (timeToShoot < 0) {
                    //Si no ha disparado aún el enemigo
                    if (!hasShooted) {
                        //El enemigo dispara y lo hacemos saber a la variable de control
                        this.shoot();
                        hasShooted = true;
                    } else {
                        //Reseteamos todos los tiempos y controles
                        hasShooted = false;
                        timeToShoot = FREQUENCY_OF_SHOOTING;
                    }
                } else {
                    //Actualizamos el tiempo para disparar
                    timeToShoot -= delta;

                    //Actualizamos el efecto de partículas
                    this.updateParticleEffect();
                    shootEffectWarning.update(delta);
                }
            }
        }
    }

    public void updateParticleEffect() {
        if (this.isDefeated()){
            super.updateParticleEffect();
        }else {
            shootEffectWarning.getEmitters().first().setPosition(this.getX(), this.getY() + this.getHeight() / 2);
        }
    }

    public void shoot(){
        ShootsManager.shootOneType5Weapon(this);
    }

    public void render(SpriteBatch batch){
        //Si el enemigo no ha disparado pintamos el efecto de partículas
        if(!this.hasShooted && !this.isDefeated())
            shootEffectWarning.draw(batch);
        super.render(batch);
    }

    public void dispose() {
        super.dispose();
        shootEffectWarning.dispose();
    }

    public void collideWithShoot(Shoot shoot) {
        if (!(shoot instanceof BigShoot)){
            this.damage(1);
        }
    }

}
