package com.tfg.spacegame.gameObjects.weapons;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.AssetsManager;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Weapon;

public class Basic extends Weapon {

    // Velocidad de movimiento
    public static final float SPEED = 600;

    // Tiempo necesario a esperar para que empiece a moverse el disparo
    // Se trata de un delay de aparición
    private float timeToMove;

    // Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    /**
     * Crea una ráfaga de tres disparos básicos
     * @param shooter - La nave
     * @return Un array con tres elementos
     */
    public static Array<Basic> shootBasicWeapon(GameObject shooter){
        Array<Basic> result = new Array<Basic>();
        // Los tiempos son a 'ojo', esto se puede consultar
        result.add(new Basic(shooter, 0.0f));
        result.add(new Basic(shooter, 0.1f));
        result.add(new Basic(shooter, 0.2f));

        return result;
    }

    public Basic(GameObject shooter, float delay) {
        // Situamos el disparo en el sitio correcto
        // X - Extremo derecha del shooter
        // Y - La mitad del alto del shooter - la mitad del alto del disparo
        super("shoot", (int)(shooter.getX()+shooter.getWidth()),(int)(shooter.getY()+shooter.getHeight()/2),shooter);
        this.setY(this.getY()-this.getHeight()/2);

        // Creamos el efecto de particulas
        shootEffect = AssetsManager.loadParticleEffect("shootEffect");
        // Lo ubicamos en el extremo derecha y mitad de altura del shooter
        shootEffect.getEmitters().first().setPosition(shooter.getX() + shooter.getWidth(),shooter.getY()+shooter.getHeight()/2);
        // Lo iniciamos, pero aunque lo iniciemos si no haya un update no avanzará
        shootEffect.start();

        timeToMove = delay;
    }

    public void update(float delta) {
        // Esperaremos a que sea el momento correcto para moverse
        if(timeToMove < 0){
            // Actualizamos el movimiento del disparo
            this.setX(this.getX() + (SPEED * delta));

            // Actualizamos la posición del efecto de particulas de acuerdo con la posición de la nave
            shootEffect.getEmitters().first().setPosition(getShooter().getX() + getShooter().getWidth(), getShooter().getY() + getShooter().getHeight() / 2);
            // Actualizamos el efecto de particulas
            shootEffect.update(delta);

        }
        // Mientras no sea el momento para moverse
        else{
            // Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
            timeToMove-=delta;

            // Podemos además ir actualizando la posición Y por si el shooter se está moviendo
            this.setY(getShooter().getY()+getShooter().getHeight()/2 - this.getHeight()/2);
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        // Mientras no sea el momento para disparar, no renderizamos el efecto de particulas
        if(timeToMove < 0)
            shootEffect.draw(batch);
    }
    
    public void dispose(){
        super.dispose();
        shootEffect.dispose();
    }
}
