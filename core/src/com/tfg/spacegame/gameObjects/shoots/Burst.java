package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.multiplayerMode.EnemyShip;
import com.tfg.spacegame.utils.ShootsManager;
import com.tfg.spacegame.utils.enums.TypeShoot;

public class Burst {

    //Será necesario para hacer una ráfaga de disparos básicos
    private int numberOfBasicShoots;

    //Guarda el último disparo realizado en una ráfaga
    private Shoot lastShootOfBurst;

    //Guarda el punto de partida del último disparo realizado en una ráfaga
    private float startPoint;

    //Tipo de arma a hacer la ráfaga (naranja o basica)
    private TypeShoot typeToBurst;

    //Si hubiese un objetivo de la ráfaga, lo guardamos aqui
    private GameObject burstTarget;

    private GameObject burstShooter;

    //Dentro del efecto ráfaga existe un factor de aparición que definiremos
    //en el método burst. A mayor número mayor tiempo entre disparos
    //Menos de 1.0 los disparos se soperponen
    private static double aparitionFactor;

    private boolean endShooting;

    public Burst(GameObject shooter, int number, float start, TypeShoot typeburst, GameObject target, double factor){

        numberOfBasicShoots     = number;
        startPoint              = start;
        typeToBurst             = typeburst;
        burstTarget             = target;
        burstShooter            = shooter;
        aparitionFactor         = factor;

        lastShootOfBurst = null;
        endShooting = false;
    }

    //Actualiza el estado de la ráfaga ee disparo que haya en pantalla
    public void updateBurst(Ship ship) {
        //Si estamos en medio de una ráfaga de la nave, continuamos disparando si es el momento
        if (numberOfBasicShoots > 0) {
            //Disparamos un nuevo shoot en la ráfaga si no hubo un último, o bien la distancia recorrida por el
            //último es superior a su punto de inicio más su ancho por 1.3)

            if (lastShootOfBurst == null ||
                    lastShootOfBurst.getX() > startPoint + (lastShootOfBurst.getWidth() * aparitionFactor) || (burstShooter instanceof Enemy && lastShootOfBurst.getX() < (startPoint - (lastShootOfBurst.getWidth()*aparitionFactor)))) {
                if(typeToBurst.equals(TypeShoot.BASIC))
                    lastShootOfBurst = ShootsManager.shootOneBasicWeapon(burstShooter);
                else if(typeToBurst.equals(TypeShoot.ORANGE))
                    lastShootOfBurst = ShootsManager.shootOneOrangeWeapon(ship,(int)(ship.getX() + ship.getWidth()),(int) (ship.getY() + ship.getHeight()/2), 45f ,burstTarget, numberOfBasicShoots);
                numberOfBasicShoots -= 1;
                startPoint = lastShootOfBurst.getX();
                //Si acabamos de lanzar el último disparo de la ráfaga, no lo guardamos
                if (numberOfBasicShoots == 0){
                    lastShootOfBurst = null;
                    // Si el burst era de un tipo naranja, desactivamos el efecto de localización.
                    if(typeToBurst.equals(TypeShoot.ORANGE)){
                        Enemy enemy = (Enemy) burstTarget;
                        enemy.setTargettedByShip(false);
                    }
                    endShooting = true;
                }
            }
        }
    }

    public boolean isEndShooting(){
        return endShooting;
    }
}
