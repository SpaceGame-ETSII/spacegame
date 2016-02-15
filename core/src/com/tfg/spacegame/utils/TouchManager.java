package com.tfg.spacegame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;

public class TouchManager {

    private static Vector3 firstTouchPos;
    private static Vector3 secondTouchPos;

    public static void initialize(){
        firstTouchPos = new Vector3(Vector3.Zero);
        secondTouchPos = new Vector3(Vector3.Zero);
    }

    /**
     * Pregunta si el jugador ha realizado un primer touch
     * @return True en caso de que si
     */
    private static boolean isFirstTouchActive(){
        return Gdx.input.isTouched();
    }

    /**
     * Pregunta si el jugador ha realizado un segundo touch
     * @return True en caso de que si
     */
    private static boolean isSecondTouchActive(){
        return Gdx.input.isTouched(1);
    }

    /**
     * Método para obtener el valor del primer touch (dedo)
     * @return El vector solicitado
     */
    public static Vector3 getFirstTouchPos(){
        firstTouchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
        firstTouchPos = SpaceGame.camera.unproject(firstTouchPos);
        return firstTouchPos;
    }

    /**
     * Método para obtener el valor del segundo touch (dedo)
     * @return El vector solicitado
     */
    public static Vector3 getSecondTouchPos(){
        secondTouchPos.set(Gdx.input.getX(1),Gdx.input.getY(1),0);
        secondTouchPos = SpaceGame.camera.unproject(secondTouchPos);
        return secondTouchPos;
    }

    /**
     * Calcula si alguno de los dos touch (primer y segundo dedo) cumple con la condicion
     * Condicion: Que el valor X de las posición sea inferior a la dada
     * @param x Parámetro a valorar
     * @return Un vector con el posible touch que cumple la condicion. Si ningún touch la cumple
     * , devuelve un vector vacio (0,0,0) Que será usado
     */
    public static Vector3 getAnyXTouchLowerThan(float x){
        Vector3 result = Vector3.Zero;
        if(isFirstTouchActive() && getFirstTouchPos().x < x)
            result = getFirstTouchPos();
        if(isSecondTouchActive() && getSecondTouchPos().x < x)
            result = getSecondTouchPos();
        return result;
    }

    /**
     * Calcula si alguno de los dos touch (primer y segundo dedo) cumple con la condicion
     * Condicion: Que el valor X de las posición sea superior a la dada
     * @param x Parámetro a valorar
     * @return Un vector con el posible touch que cumple la condicion. Si ningún touch la cumple
     * , devuelve un vector vacio (0,0,0) Que será usado para comprobar si es un vector correcto
     */
    public static Vector3 getAnyXTouchGreaterThan(float x){
        Vector3 result = Vector3.Zero;
        if(isFirstTouchActive() && getFirstTouchPos().x > x)
            result = getFirstTouchPos();
        if(isSecondTouchActive() && getSecondTouchPos().x > x)
            result = getSecondTouchPos();
        return result;
    }

}
