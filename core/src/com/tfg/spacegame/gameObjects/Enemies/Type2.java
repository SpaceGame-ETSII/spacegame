package com.tfg.spacegame.gameObjects.Enemies;

import com.tfg.spacegame.gameObjects.Enemy;

/**
 * Created by gaems-dev on 20/01/16.
 */
public class Type2 extends Enemy{

    // En el principio este enemigo viajará a una velocidad que
    // llamaremos "normal" para luego lanzarse hacia el jugador
    // con una velocidad "alta"
    private static int NORMAL_SPEED = 150;
    private static int HIGH_SPEED = 500;

    // Este es el tiempo en el que el enemigo esperará sin
    // moverse hasta que se lance hacía el jugador
    private float timeToGoFast;

    // Cuantos pixeles se moverá con velocidad normal
    // desde la parte extrema derecha de la pantalla
    private int howMuchToMoveSlowly;

    public static Type2 createEnemy(int x, int y){
        Type2 result;
        result = new Type2(x,y);
        return result;
    }

    public Type2(int x, int y) {
        super("enemy", x, y);
        timeToGoFast = 2f;
        howMuchToMoveSlowly = 200;
    }

    public void update(float delta){
        // Mientras tengamos que seguir moviendonos lentamente
        if(howMuchToMoveSlowly > 0){
            this.setX(this.getX() - NORMAL_SPEED * delta);
            howMuchToMoveSlowly-=NORMAL_SPEED*delta;
        }else{
            // Ahora tenemos que esperar hasta que podamos
            // movernos rapidamente

            // Si el tiempo ya ha acabado, podemos movernos rapidamente
            if(timeToGoFast <=0){
                this.setX(this.getX() - HIGH_SPEED * delta);
            }else{
                // Si no, restamos el delta al tiempo
                timeToGoFast-=delta;
            }
        }
    }

}
