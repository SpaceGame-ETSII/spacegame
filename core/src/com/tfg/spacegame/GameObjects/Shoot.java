package com.tfg.spacegame.GameObjects;

import com.tfg.spacegame.GameObject;

public class Shoot extends GameObject{

    public static final float SPEED = 700;
    private GameObject shooter;

    // Delay es el tiempo necesario a esperar para que empiece a moverse el disparo
    private float delay;

    public Shoot(GameObject shooter) {
        super("shoot", 0, 0);
        this.setX(shooter.getX()+shooter.getWidth());
        this.setY(shooter.getY()+shooter.getHeight()/2-getHeight()/2);

        this.shooter = shooter;
    }

    // Constructor con un delay pasado por parámetro
    public Shoot(GameObject shooter, float delay) {
        super("shoot", 0, 0);
        this.setX(shooter.getX()+shooter.getWidth());
        this.setY(shooter.getY()+shooter.getHeight()/2-getHeight()/2);

        this.delay = delay;

        this.shooter = shooter;
    }

    public void update(float delta) {
        if(delay < 0)
            this.setX(this.getX() + (SPEED * delta));
        else{
            // Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
            delay-=delta;

            // Podemos además ir actualizando la posición Y por si el shooter se está moviendo
            this.setY(shooter.getY()+shooter.getHeight()/2-getHeight()/2);
        }

    }

}
