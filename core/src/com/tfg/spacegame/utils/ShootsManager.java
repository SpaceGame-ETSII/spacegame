package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.shoots.BigShoot;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;
import com.tfg.spacegame.gameObjects.shoots.Red;
import com.tfg.spacegame.utils.enums.TypeWeapon;

public class ShootsManager {

    //Almacenará todos los shoots en pantalla
    public static Array<Shoot> shoots;

    //Será necesario para hacer una ráfaga de disparos básicos
    private static int numberOfBasicShoots;

    //Guarda el último disparo realizado en una ráfaga
    private static Shoot lastShootOfBurst;

    //Guarda el punto de partida del último disparo realizado en una ráfaga
    private static float startPoint;

    public static void load() {
        shoots = new Array<Shoot>();
        numberOfBasicShoots = 0;
        startPoint = 0;
    }

    /**
     * Prepara la realización de una ráfaga de tres disparos
     * @param shooter - El shooter que realizó el disparo
     */
    public static void shootBurstBasicWeaponForShip(GameObject shooter){
        if(isShipReadyToShoot(TypeWeapon.BASIC)){
            numberOfBasicShoots = 3;
            startPoint = 0;
        }
    }

    /**
     * Lanza un único disparo básico
     * @param shooter - El shooter que realizó el disparo
     */
    public static Basic shootOneBasicWeapon(GameObject shooter) {
        Basic basic = new Basic(shooter,0,0);

        int x = (int) (shooter.getX() - basic.getWidth());
        int y = (int) (shooter.getY() + shooter.getHeight()/2);

        if (shooter instanceof Ship) {
            x += shooter.getWidth() + basic.getWidth();
            y -= (shooter.getHeight()/2 - basic.getHeight()/2);
        }

        basic.setX(x);
        basic.setY(y);

        shoots.add(basic);

        return basic;
    }

    /**
     * Indica si la nave puede disparar en el momento actual, según el arma
     * @param type - El tipo de disparo equipado en la nave
     * @return Indica si puede o no disparar la nave
     */
    private static boolean isShipReadyToShoot(TypeWeapon type) {
        boolean result = false;
        Array<Shoot> selected = new Array<Shoot>();

        //Obtenemos todos los disparos en pantalla que realizó la nave
        for(Shoot shoot : shoots){
            if(shoot.getShooter() instanceof Ship && !shoot.isShocked())
                selected.add(shoot);
        }

        //Según el tipo de disparo, la condición será distinta
        switch (type){
            case BASIC:
                if(selected.size <= 0)
                    result = true;
                break;
            case RED:
                if(selected.size <= 0)
                    result = true;
                break;
            default:
                throw new IllegalArgumentException("Se ha seleccionado un tipo de arma inválido");
        }
        return result;
    }

    public static void render(){
        for(Shoot shoot : shoots)
            shoot.render(SpaceGame.batch);
    }

    public static void update(float delta, Ship ship){
        for(Shoot shoot: shoots){
            shoot.update(delta);

            //Si algún disparo sobresale los limites de la pantalla o está marcado como borrable, se eliminará
            if(shoot.getX() > SpaceGame.width || shoot.getX()+shoot.getWidth() < 0 ||
                    shoot.getY()+shoot.getHeight() < 0 || shoot.getY() > SpaceGame.height ||
                    shoot.isDeletable()){
                shoots.removeValue(shoot,false);
            }
        }
        updateBurst(delta, ship);
    }

    //Actualiza el estado de la ráfaga ee disparo que haya en pantalla
    public static void updateBurst(float delta, Ship ship) {
        //Si estamos en medio de una ráfaga de la nave, continuamos disparando si es el momento
        if (numberOfBasicShoots > 0) {

            //Disparamos un nuevo shoot en la ráfaga si no hubo un último, o bien la distancia recorrida por el
            //último es superior a su punto de inicio más su ancho por 1.3
            if (lastShootOfBurst == null ||
                    lastShootOfBurst.getX() > (startPoint + lastShootOfBurst.getWidth()) * 1.3) {
                lastShootOfBurst = shootOneBasicWeapon(ship);
                numberOfBasicShoots -= 1;
                startPoint = lastShootOfBurst.getX();

                //Si acabamos de lanzar el último disparo de la ráfaga, no lo guardamos
                if (numberOfBasicShoots == 0)
                    lastShootOfBurst = null;
            }
        }
    }

    public static void shootOneType5Weapon(GameObject shooter) {
        BigShoot bigShoot = new BigShoot(shooter,0,0,0f);

        int x = (int) (shooter.getX());
        int y = (int) (shooter.getY() + shooter.getHeight()/2);

        bigShoot.setX(x);
        bigShoot.setY(y);

        shoots.add(bigShoot);
    }

    public static void shootRedWeapon(GameObject shooter) {
        Red redShoot = new Red(shooter,0,0,0f);
        if (shooter instanceof Ship){
            if(isShipReadyToShoot(TypeWeapon.RED)){
                int x = (int) (shooter.getX() + shooter.getWidth());
                int y = (int) (shooter.getY() + shooter.getHeight()/2);

                redShoot.setX(x);
                redShoot.setY(y);

                shoots.add(redShoot);
            }
        }else {
            int x = (int) (shooter.getX() + shooter.getWidth());
            int y = (int) (shooter.getY() + shooter.getHeight()/2);

            redShoot.setX(x);
            redShoot.setY(y);

            shoots.add(redShoot);
        }


    }

    //Gestiona la reacción de la colisión del shoot pasado por parámetro con la nave
    public static void manageCollisionWithShip(Shoot shoot) {
        shoot.collideWithShip();
    }

    //Gestiona la reacción de la colisión del shoot y el enemigo pasados por parámetro
    public static void manageCollisionWithEnemy(Pair<Shoot,Enemy> shootToEnemy) {
        Shoot shoot = shootToEnemy.getFirst();
        Enemy enemy = shootToEnemy.getSecond();

        shoot.collideWithEnemy(enemy);
    }

    //Gestiona la reacción de la colisión de los dos shoots pasados por parámetro
    public static void manageCollisionWithShoot(Pair<Shoot, Shoot> shootToShoot) {
        Shoot shoot1 = shootToShoot.getFirst();
        Shoot shoot2 = shootToShoot.getSecond();

        shoot1.collideWithShoot(shoot2);
        shoot2.collideWithShoot(shoot1);
    }
}
