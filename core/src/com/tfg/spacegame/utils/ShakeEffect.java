package com.tfg.spacegame.utils;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.tfg.spacegame.SpaceGame;

public class ShakeEffect {

    // Propiedad para determinar lo que es una vibracion normal
    public static final float NORMAL_SHAKE = 3f;

    // Tiempo total que estará vibrando la pantalla
    private final float TOTAL_TIME;
    // Máxima potencia de vibracion
    private final float MAX_POWER;

    // Posición inicial de la cámara antes de iniciar la vibración
    private final Vector3 INITIAL_POSITION;

    // Potencia actual
    private float currentPower;
    // Tiempo actual
    private float currentTime;

    // Sabremos si tenemos que empezar el efecto o no con esta propiedad
    private boolean startEffect;

    public ShakeEffect(float total_time, float max_power){
        TOTAL_TIME  = total_time;
        MAX_POWER   = max_power;

        INITIAL_POSITION = new Vector3(SpaceGame.camera.position);

        currentPower = 0;
        currentTime  = 0;

        startEffect = false;
    }

    public void shake(float delta){
        if(startEffect){
            if(!isDone()){
                // Calculamos la potencia actual a aplicar a la cámara
                // Esta potencia disminuirá conforme vaya pasando el tiempo
                currentPower = MAX_POWER * ( (TOTAL_TIME - currentTime) / TOTAL_TIME  );

                // Calculamos cuanto vamos a trasladar la cámara
                // Esto es un random entre -1 y 1 y multiplicado por la potencia actual
                float x = (MathUtils.random.nextFloat() - 0.5f) * 2 * currentPower;
                float y = (MathUtils.random.nextFloat() - 0.5f) * 2 * currentPower;

                // Trasladamos la cámara esa cantidad
                SpaceGame.camera.position.x = INITIAL_POSITION.x + x;
                SpaceGame.camera.position.y = INITIAL_POSITION.y + y;

                // Aumentamos el tiempo actual con el delta
                currentTime+=delta;
            }else{
                // Restablecemos la posición de la camara con la posición inicial
                SpaceGame.camera.position.x = INITIAL_POSITION.x;
                SpaceGame.camera.position.y = INITIAL_POSITION.y;
                startEffect = false;
                currentTime = 0;
            }
        }
    }

    // Con este método sabremos si hemos acabado el efecto de vibración o no
    public boolean isDone(){
        return currentTime >= TOTAL_TIME;
    }

    // Con este método iniciaremos el efecto de vibración
    public void start(){
        startEffect = true;
    }

}
