package com.tfg.spacegame.gameObjects.Enemies;

import com.tfg.spacegame.gameObjects.Enemy;

/**
 * Created by gaems-dev on 20/01/16.
 */
public class Type2 extends Enemy{

    private static int NORMAL_SPEED = 150;
    private static int HIGH_SPEED = 500;

    private float timeToGoFast = 2f;
    private int howMuchToMoveSlowly = 200;

    public static Type2 createEnemy(int x, int y){
        Type2 result;
        result = new Type2(x,y);
        return result;
    }

    public Type2(int x, int y) {
        super("enemy", x, y);
    }

    @Override
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
