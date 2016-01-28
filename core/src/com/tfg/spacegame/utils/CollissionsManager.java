package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Weapon;

public class CollissionsManager {

    public void update(float delta) {
        //Contendrán las colisiones realizadas, dando prioridad a un shoot con un enemigo
        Array<Pair<Weapon, Enemy>> shootToEnemy = new Array<Pair<Weapon, Enemy>>();
        Array<Pair<Weapon, Weapon>> shootToShoot = new Array<Pair<Weapon, Weapon>>();

        //Arrays que harán de auxiliares para no sobreescribir las originales
        Array<Enemy> enemies = EnemiesManager.enemies;
        Array<Weapon> shoots = ShootsManager.shoots;

        //Indicará que un shoot ya ha dado con un enemigo, y servirá para que no se compruebe posteriormente con otro shoot
        boolean isOverlapped;

        for (Weapon shootDst: shoots) {
            isOverlapped = false;

            for (Enemy enemy: enemies) {
                if (shootDst.isOverlapingWith(enemy)) {
                    //Añadimos el par colisionado a la lista
                    shootToEnemy.add(new Pair<Weapon, Enemy>(shootDst, enemy));

                    //Borramos los elementos de la colisión para que no se comprueben más
                    shoots.removeValue(shootDst, false);
                    enemies.removeValue(enemy, false);

                    //Si la bala ya ha chocado, salimos del for y marcamos como colisionado el shoot
                    isOverlapped = true;
                    break;
                }
            }

            //Si la bala no ha chocado con un enemigo, comprobamos si ha chocado con otra bala
            if (!isOverlapped) {

                for (Weapon shootSrc: shoots) {
                    if (shootDst != shootSrc && shootDst.isOverlapingWith(shootSrc)) {
                        //Añadimos el par colisionado a la lista
                        shootToShoot.add(new Pair<Weapon, Weapon>(shootDst, shootSrc));

                        //Borramos los elementos de la colisión para que no se comprueben más
                        shoots.removeValue(shootDst, false);
                        shoots.removeValue(shootSrc, false);

                        //Si la bala ya ha chocado, salimos del for
                        break;
                    }
                }
                
            }

        }

    }

}
