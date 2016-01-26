package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Weapon;
import com.tfg.spacegame.gameObjects.weapons.Basic;

public class ShootsManager {

    public static Array<Weapon> shoots;

    public static void load() {
        shoots = new Array<Weapon>();
    }

    /**
     * Crea una ráfaga de tres disparos básicos
     * @param shooter - La nave
     * @return Un array con tres elementos
     */
    public static void shootBurstBasicWeapon(GameObject shooter){
        Basic basic = new Basic(shooter,0,0,0.0f);

        if(canShipShoot(TypeWeapon.BASIC)){
            int x;

            if(shooter instanceof Enemy)
                x = (int) (shooter.getX());
            else
                x = (int) (shooter.getX() + shooter.getWidth());

            int y = (int)(shooter.getY()+shooter.getHeight()/2);

            basic.setX(x);
            basic.setY(y);

            shoots.add(basic);
            shoots.add(new Basic(shooter,x,y, 0.1f));
            shoots.add(new Basic(shooter,x,y, 0.2f));

        }
    }

    public static void shootOneBasicWeapon(GameObject shooter) {
        Basic basic = new Basic(shooter,0,0,0.0f);

        int x = (int) (shooter.getX());
        int y = (int) (shooter.getY()+shooter.getHeight()/2);

        basic.setX(x);
        basic.setY(y);

        shoots.add(basic);
    }

    private static boolean canShipShoot(TypeWeapon type) {
        boolean result = false;
        Array<Weapon> selected = new Array<Weapon>();

        for(Weapon w : shoots){
            if(w.getShooter() instanceof  Ship)
                selected.add(w);
        }

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
        for(Weapon weapon : shoots)
            weapon.render(SpaceGame.batch);
    }

    public static void update(float delta){
        for(Weapon shoot: shoots){
            shoot.update(delta);

            //Si algún disparo sobresale los limites de la pantalla
            //Se eleminará
            if(shoot.getX() > SpaceGame.width || shoot.getX()+shoot.getWidth() < 0 ||
                    shoot.getY()+shoot.getHeight() < 0 || shoot.getY() > SpaceGame.height){
                shoots.removeValue(shoot,false);
            }
        }
    }
}
