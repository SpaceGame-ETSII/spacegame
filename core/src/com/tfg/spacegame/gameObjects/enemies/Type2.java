package com.tfg.spacegame.gameObjects.enemies;

import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;

public class Type2 extends Enemy{

    // En el principio este enemigo viajará a una velocidad que
    // llamaremos "normal" para luego lanzarse hacia el jugador
    // con una velocidad "alta"
    private static final int NORMAL_SPEED = 150;
    private static final int HIGH_SPEED = 500;

    // Este es el tiempo en el que el enemigo esperará sin
    // moverse hasta que se lance hacía el jugador
    private float timeToGoFast;

    // Cantidad de pixeles se moverá con velocidad normal
    // desde la parte extrema derecha de la pantalla
    private int pixelsToMoveSlowly;

    public Type2(int x, int y) {
        super("enemy", x, y, 3);
        timeToGoFast = 2f;
        pixelsToMoveSlowly = 200;
    }

    public void update(float delta){
        // Mientras tengamos que seguir moviendonos lentamente
        if (pixelsToMoveSlowly > 0) {
            this.setX(this.getX() - NORMAL_SPEED * delta);
            pixelsToMoveSlowly -=NORMAL_SPEED*delta;
        } else {
            // Ahora tenemos que esperar hasta que podamos
            // movernos rapidamente

            // Si el tiempo ya ha acabado, podemos movernos rapidamente
            if (timeToGoFast <= 0) {
                this.setX(this.getX() - HIGH_SPEED * delta);
            } else {
                timeToGoFast -= delta;
            }
        }
    }

    public void collideWithShoot(Shoot shoot) {
        this.damage(1);
    }

}
