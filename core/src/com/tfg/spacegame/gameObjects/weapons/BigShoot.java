package com.tfg.spacegame.gameObjects.weapons;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Weapon;
import com.tfg.spacegame.utils.AssetsManager;

public class BigShoot extends Weapon{

    //Velocidad de movimiento
    public static final float SPEED = 200;

    /*Tiempo necesario a esperar para que empiece a moverse el disparo
      Se trata de un delay de aparición*/
    private float timeToMove;

    //Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    public BigShoot(GameObject shooter, int x, int y, float delay) {
        super("disparo_enemigo_basico_tipo5", x, y, shooter);

        // Creamos el efecto de particulas
        shootEffect = AssetsManager.loadParticleEffect("shootType5Effect");
        this.updateParticleEffect();

        // Lo iniciamos, pero aunque lo iniciemos si no haya un update no avanzará
        shootEffect.start();
        timeToMove = delay;
    }

    public void updateParticleEffect() {
        if (this.getShooter() instanceof Enemy){
            shootEffect.getEmitters().first().setPosition(this.getX(),this.getY()+18);
        }else{
            throw new IllegalArgumentException("No puede disparar siendo diferente de un enemigo básico tipo 5");
        }
    }

    public void update(float delta) {
        // Esperaremos a que sea el momento correcto para moverse
        if(timeToMove < 0){
            // Actualizamos el movimiento del disparo
            if(getShooter() instanceof Enemy)
                this.setX(this.getX() - (SPEED * delta));
            else
                this.setX(this.getX() + (SPEED * delta));

            //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            this.updateParticleEffect();

            //Actualizamos el efecto de particulas
            shootEffect.update(delta);
        }
        //Mientras no sea el momento para moverse
        else{
            //Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
            timeToMove-=delta;

            //Podemos además ir actualizando la posición Y por si el shooter se está moviendo
            this.setY(getShooter().getY()+getShooter().getHeight()/2 - this.getHeight()/2);
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        //Mientras no sea el momento para disparar, no renderizamos el efecto de particulas
        if(timeToMove < 0)
            shootEffect.draw(batch);
    }

    public void dispose(){
        super.dispose();
        shootEffect.dispose();
    }
}
