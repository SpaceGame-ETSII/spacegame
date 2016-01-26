package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.gameObjects.Enemy;

public class Type1 extends Enemy{

    public static final int SPEED = 200;

    //Indicará el rango de ondulación
    private static final int AMPLITUDE = 80;

    //Indicará el grado del movimiento que irá variando con la ondulación
    private float degrees;

    //Este es el tiempo requerido para el comienzo de movimiento de un enemigo
    //Usaremos esta propiedad para definir el escuadrón
    private float delay;

    public Type1(int x, int y, float delay) {
        super("enemy", x, y);

        this.delay = delay;
        degrees = 0;
    }

    public void update(float delta){
        //Si se ha vencido el tiempo requerido, el enemigo se moverá
        if (delay <= 0) {
            this.setX(this.getX() - SPEED * delta);
            degrees += delta*SPEED;
            this.setY(this.getInitialPosition().y + (AMPLITUDE * MathUtils.sinDeg(degrees)));
        } else {
            delay -= delta;
        }

    }


}
