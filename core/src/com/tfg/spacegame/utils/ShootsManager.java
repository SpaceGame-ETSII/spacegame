package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.shoots.Basic;

public class ShootsManager {

    public static Array<Shoot> shoots;

    public static void load() {
        shoots = new Array<Shoot>();
    }

    /**
     * Crea una ráfaga de tres disparos básicos
     * @param shooter - El shooter que realizó el disparo
     */
    public static void shootBurstBasicWeapon(GameObject shooter){
        Basic basic = new Basic(shooter,0,0,0.0f);

        if(isShipReadyToShoot(TypeWeapon.BASIC)){
            int x = (int) (shooter.getX() + shooter.getWidth());
            int y = (int) (shooter.getY() + shooter.getHeight() / 2);

            basic.setX(x);
            basic.setY(y);

            shoots.add(basic);
            shoots.add(new Basic(shooter,x,y, 0.1f));
            shoots.add(new Basic(shooter,x,y, 0.2f));
        }
    }

    /**
     * Lanza un único disparo básico
     * @param shooter - El shooter que realizó el disparo
     */
    public static void shootOneBasicWeapon(GameObject shooter) {
        Basic basic = new Basic(shooter,0,0,0.0f);

        int x = (int) (shooter.getX() - basic.getWidth());
        int y = (int) (shooter.getY() + shooter.getHeight()/2);

        basic.setX(x);
        basic.setY(y);

        shoots.add(basic);
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
        for(Shoot w : shoots){
            if(w.getShooter() instanceof Ship)
                selected.add(w);
        }

        //Según el tipo de disparo, la condición será distinta
        switch (type){
            case BASIC:
                if(selected.size == 0)
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
