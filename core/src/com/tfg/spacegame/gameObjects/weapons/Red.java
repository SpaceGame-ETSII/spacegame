package com.tfg.spacegame.gameObjects.weapons;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Weapon;
import com.tfg.spacegame.utils.AssetsManager;

public class Red extends Weapon{

    //Velocidad de movimiento
    public static final float SPEED = 800;

    /*Tiempo necesario a esperar para que empiece a moverse el disparo
     Se trata de un delay de aparición*/
    private float timeToMove;

    //Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    public Red(GameObject shooter, int x, int y,float delay) {
        super("shoot_red", x, y, shooter);

        // Creamos el efecto de particulas
        shootEffect = AssetsManager.loadParticleEffect("shoot_redEffect");
        //this.updateParticleEffect();

        // Lo iniciamos, pero aunque lo iniciemos si no haya un update no avanzará
        //shootEffect.start();
        timeToMove = delay;
    }

    public void updateParticleEffect() {
        if (this.getShooter() instanceof Ship){
            shootEffect.getEmitters().first().setPosition(this.getX(),this.getY()+18);
        }else{
            throw new IllegalArgumentException("No puede disparar este arma");
        }
    }

    public void update(float delta) {
        // Esperaremos a que sea el momento correcto para moverse
        if (timeToMove < 0) {
            // Actualizamos el movimiento del disparo
            if (getShooter() instanceof Ship)
                this.setX(this.getX() + (SPEED * delta));
            else
                this.setX(this.getX() - (SPEED * delta));

            //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
            //this.updateParticleEffect();

            //Actualizamos el efecto de particulas
            //shootEffect.update(delta);
        }
        //Mientras no sea el momento para moverse
        else {
            //Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
            timeToMove -= delta;

            //Podemos además ir actualizando la posición Y por si el shooter se está moviendo
            this.setY(getShooter().getY() + getShooter().getHeight() / 2 - this.getHeight() / 2);
        }
    }

    public void render(SpriteBatch batch){
        //shootEffect.draw(batch);
        super.render(batch);
    }

    public void dispose(){
        super.dispose();
        //shootEffect.dispose();
    }
}
