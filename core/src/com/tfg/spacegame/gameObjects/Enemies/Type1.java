package com.tfg.spacegame.gameObjects.Enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;

/**
 * Created by gaems-dev on 19/01/16.
 */
public class Type1 extends Enemy{

    public static int SPEED = 200;
    // El movimiento de este enemigo es de tipo
    // ondulatorio, para ello necesita dos propiedades: Amplitud (rango
    // de esa ondulación) y grados, que en este caso es para que vaya describiendo
    // la onda
    private int amplitude = 80;
    private float degrees = 0;

    // Este es el tiempo requerido para el comienzo de movimiento de un enemigo
    // Usaremos esta propiedad para definir el escuadrón
    private float timeUntilMove;

    /**
     * Crea un escuadrón de enemigos tipo 1 (5). Los parámetros de entrada son el lugar exacto
     * de aparición del primer enemigo. Todos irán retrasados respecto a él.
     * @return Un Array de Type1 con 5 elementos
     */
    public static Array<Type1> createSquadron(float x, float y){
        Array<Type1> result = new Array<Type1>();
        result.add(new Type1(SpaceGame.width,SpaceGame.height/2));
        result.add(new Type1(SpaceGame.width,SpaceGame.height/2,0.35f));
        result.add(new Type1(SpaceGame.width,SpaceGame.height/2,0.65f));
        result.add(new Type1(SpaceGame.width,SpaceGame.height/2,0.95f));
        result.add(new Type1(SpaceGame.width,SpaceGame.height/2,1.25f));

        return result;
    }

    public Type1(int x, int y) {
        super("enemy", x, y);
        timeUntilMove = 0;
    }

    public Type1(int x, int y, float time) {
        super("enemy", x, y);
        timeUntilMove = time;
    }

    @Override
    public void update(float delta){
        // Si se ha vencido el tiempo requerido a esperar para iniciar el movimiento
        // nos movemos
        if(timeUntilMove<=0){
            this.setX(this.getX() - SPEED * delta);
            degrees += delta*SPEED;
            this.setY(this.getInitialPosition().y + (amplitude * MathUtils.sinDeg(degrees)));
        }else{
            timeUntilMove-=delta;
        }

    }


}
