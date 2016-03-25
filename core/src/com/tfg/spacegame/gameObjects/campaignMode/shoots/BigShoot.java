package com.tfg.spacegame.gameObjects.campaignMode.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.campaignMode.Shoot;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.AssetsManager;

public class BigShoot extends Shoot {

    //Velocidad de movimiento
    public static final float SPEED = 200;

    /*Tiempo necesario a esperar para que empiece a moverse el disparo
      Se trata de un delay de aparición*/
    private float timeToMove;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    public BigShoot(GameObject shooter, int x, int y, float delay) {
        super("bigshoot_shoot", x, y, shooter, AssetsManager.loadParticleEffect("bigshoot_shoot_effect"), null);

        // Creamos el efecto de particulas
        shoot = AssetsManager.loadParticleEffect("bigshoot_shoot_effect");
        this.updateParticleEffect();

        // Lo iniciamos, pero aunque lo iniciemos si no haya un update no avanzará
        shoot.start();
        timeToMove = delay;
    }

    public void updateParticleEffect() {
        shoot.getEmitters().first().setPosition(this.getX(),this.getY()+18);
    }

    public void update(float delta) {
        // Esperaremos a que sea el momento correcto para moverse
        if(timeToMove < 0){
            // Actualizamos el movimiento del disparo
            if(getShooter() instanceof Enemy)
                this.setX(this.getX() - (SPEED * delta));

            //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            this.updateParticleEffect();

            //Actualizamos el efecto de particulas
            shoot.update(delta);
        }
        //Mientras no sea el momento para moverse
        else{
            //Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
            timeToMove-=delta;

            //Podemos además ir actualizando la posición Y por si el shooter se está moviendo
            this.setY(getShooter().getY()+getShooter().getHeight()/2 - this.getHeight()/2);
        }
    }

    public void render(){
        super.render();
        //Mientras no sea el momento para disparar, no renderizamos el efecto de particulas
        if(timeToMove < 0)
            shoot.draw(SpaceGame.batch);
    }

    public void dispose(){
        super.dispose();
        shoot.dispose();
    }
}