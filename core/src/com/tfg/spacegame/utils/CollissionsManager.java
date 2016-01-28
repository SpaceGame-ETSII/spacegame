package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Weapon;

public class CollissionsManager {

    //Contendrán las colisiones realizadas
    private static Array<Pair<Weapon, Enemy>> shootsToEnemies;
    private static Array<Pair<Weapon, Weapon>> shootsToShoots;

    public void load() {
        shootsToEnemies = new Array<Pair<Weapon, Enemy>>();
        shootsToShoots = new Array<Pair<Weapon, Weapon>>();
    }

    public void update(float delta, Ship ship) {
        //Arrays que harán de auxiliares para no sobreescribir las originales
        Array<Enemy> enemies = EnemiesManager.enemies;
        Array<Weapon> shoots = ShootsManager.shoots;

        //Almacenan el enemigo o bala que han chocado con la nave
        Enemy enemyOverlapsShip = null;
        Weapon shootOverlapsShip = null;

        //Indicará que un shoot ya ha dado con un enemigo, y servirá para que no se compruebe posteriormente con otro shoot
        boolean shootIsOverlapped;

        //Primero comprobamos si algún enemigo ha dado a la nave
        for (Enemy enemy: enemies) {
            if (enemy.isOverlapingWith(ship)) {
                //Si un enemigo ha dado a la nave la almacenamos en la variable
                enemyOverlapsShip = enemy;

                //Borramos el enemigo de la lista y salimos
                enemies.removeValue(enemy, false);
                break;
            }
        }

        //Ahora comprobamos las colisiones de los shoots
        for (Weapon shootDst: shoots) {
            shootIsOverlapped = false;

            //En primer lugar comprobamos si el shoot dió a la nave, siempre y cuando no hubiese sido golpeada antes
            if (enemyOverlapsShip == null && shootOverlapsShip == null && shootDst.isOverlapingWith(ship)) {
                //Almacenamos el shoot y lo eliminamos de la lista a comprobar
                shootOverlapsShip = shootDst;
                shoots.removeValue(shootDst, false);
            } else {

                //Si la bala no dio a la nave, comprobamos si dio a algún enemigo
                for (Enemy enemy : enemies) {
                    if (shootDst.isOverlapingWith(enemy)) {
                        //Añadimos el par colisionado a la lista
                        shootsToEnemies.add(new Pair<Weapon, Enemy>(shootDst, enemy));

                        //Borramos los elementos de la colisión para que no se comprueben más
                        shoots.removeValue(shootDst, false);
                        enemies.removeValue(enemy, false);

                        //Si la bala ya ha chocado, salimos del for y marcamos como colisionado el shoot
                        shootIsOverlapped = true;
                        break;
                    }
                }

                //Por último, si la bala no ha chocado con un enemigo, comprobamos si ha chocado con otra bala
                if (!shootIsOverlapped) {

                    for (Weapon shootSrc : shoots) {
                        if (shootDst != shootSrc && shootDst.isOverlapingWith(shootSrc)) {
                            //Añadimos el par colisionado a la lista
                            shootsToShoots.add(new Pair<Weapon, Weapon>(shootDst, shootSrc));

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

        //Ahora delegamos el tratamiento de las colisiones a otros métodos
        if (enemyOverlapsShip != null) {
            this.manageEnemyToShip(enemyOverlapsShip, ship);
        } else if (shootOverlapsShip != null) {
            this.manageShootToShip(shootOverlapsShip, ship);
        }
        this.manageShootsToEnemies();
        this.manageShootsToShoots();
    }

    //Gestiona una colisión de enemigo a la nave
    private void manageEnemyToShip(Enemy enemy, Ship ship) {
        ship.receiveDamage();
        EnemiesManager.manageCollisionWithShip(enemy);
    }
    
    //Gestiona una colisión de shoot a la nave
    private void manageShootToShip(Weapon shoot, Ship ship) {
        ship.receiveDamage();
        ShootsManager.manageCollisionWithShip(shoot);
    }

    //Gestionará las colisiones entre disparos y enemigos
    private void manageShootsToEnemies() {
        for (Pair<Weapon, Enemy> shootToEnemy: shootsToEnemies) {
            EnemiesManager.manageCollisionWithShoot(shootToEnemy);
            ShootsManager.manageCollisionWithEnemy(shootToEnemy);
        }
        shootsToEnemies = new Array<Pair<Weapon, Enemy>>();
    }

    //Gestionará las colisiones entre disparos y disparos
    private void manageShootsToShoots() {
        for (Pair<Weapon, Weapon> shootToShoot: shootsToShoots) {
            ShootsManager.manageCollisionWithShoot(shootToShoot);
        }
        shootsToShoots = new Array<Pair<Weapon, Weapon>>();
    }

}
