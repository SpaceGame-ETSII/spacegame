package com.tfg.spacegame.utils;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;

public class CollissionsManager {

    //Contendrán las colisiones realizadas
    private static Array<Pair<Shoot, Enemy>> shootsToEnemies;
    private static Array<Pair<Shoot, Shoot>> shootsToShoots;

    public static void load() {
        shootsToEnemies = new Array<Pair<Shoot, Enemy>>();
        shootsToShoots = new Array<Pair<Shoot, Shoot>>();
    }

    public static void update(float delta, Ship ship) {
        //Arrays que harán de auxiliares para no sobreescribir las originales
        Array<Enemy> enemies = new Array<Enemy>(EnemiesManager.enemies);
        Array<Shoot> shoots = new Array<Shoot>(ShootsManager.shoots);
        Array<Shoot> shootsSource = new Array<Shoot>(ShootsManager.shoots);

        //Almacenan el enemigo o bala que han chocado con la nave
        Enemy enemyOverlapsShip = null;
        Shoot shootOverlapsShip = null;

        //Indicará que un shoot ya ha dado con un enemigo, y servirá para que no se compruebe posteriormente con otro shoot
        boolean shootIsOverlapped;

        //Primero comprobamos si algún enemigo ha dado a la nave
        for (Enemy enemy: enemies) {
            if (enemy.isOverlapingWith(ship) && !ship.isUndamagable()) {
                //Si un enemigo ha dado a la nave la almacenamos en la variable
                enemyOverlapsShip = enemy;

                //Borramos el enemigo de la lista y salimos
                enemies.removeValue(enemy, false);
                break;
            }
        }

        //Ahora comprobamos las colisiones de los shoots
        for (Shoot shootDst: shoots) {
            shootIsOverlapped = false;

            //En primer lugar comprobamos si el shoot dió a la nave, siempre y cuando no hubiese sido golpeada antes
            if (enemyOverlapsShip == null && shootOverlapsShip == null && shootDst.getShooter() != ship &&
                    shootDst.isOverlapingWith(ship) && !shootDst.isShocked() && !ship.isUndamagable()) {

                //Almacenamos el shoot y lo eliminamos de la lista a comprobar
                shootOverlapsShip = shootDst;
                shoots.removeValue(shootDst, false);
            } else {

                //Si la bala no dio a la nave, comprobamos si dio a algún enemigo
                for (Enemy enemy : enemies) {
                    if (shootDst.isOverlapingWith(enemy) && !shootDst.isShocked() && !enemy.isDefeated() &&
                            enemy.isDamagable() && shootDst.getShooter() != enemy) {

                        //Añadimos el par colisionado a la lista
                        shootsToEnemies.add(new Pair<Shoot, Enemy>(shootDst, enemy));

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

                    for (Shoot shootSrc : shootsSource) {

                        if (!shootDst.equals(shootSrc) && shootDst.isOverlapingWith(shootSrc)
                                && !shootDst.isShocked() && !shootSrc.isShocked()
                                && !shootDst.getShooter().equals(shootSrc) && !shootSrc.getShooter().equals(shootDst)
                                && !shootDst.getShooter().equals(shootSrc.getShooter()) && !shootSrc.getShooter().equals(shootDst.getShooter())) {
                            //Añadimos el par colisionado a la lista
                            shootsToShoots.add(new Pair<Shoot, Shoot>(shootDst, shootSrc));
                            //Borramos los elementos de la colisión para que no se comprueben más
                            shoots.removeValue(shootDst, false);
                            shootsSource.removeValue(shootSrc, false);

                            //Si la bala ya ha chocado, salimos del for
                            break;
                        }
                    }
                }
            }
        }

        //Ahora delegamos el tratamiento de las colisiones a otros métodos
        if (enemyOverlapsShip != null) {
            manageEnemyToShip(enemyOverlapsShip, ship);
        } else if (shootOverlapsShip != null) {
            manageShootToShip(shootOverlapsShip, ship);
        }
        manageShootsToEnemies();
        manageShootsToShoots();
    }

    //Gestiona una colisión de enemigo a la nave
    private static void manageEnemyToShip(Enemy enemy, Ship ship) {
        ship.receiveDamage();
        EnemiesManager.manageCollisionWithShip(enemy);
    }

    //Gestiona una colisión de shoot a la nave
    private static void manageShootToShip(Shoot shoot, Ship ship) {
        ship.receiveDamage();
        ShootsManager.manageCollisionWithShip(shoot);
    }

    //Gestionará las colisiones entre disparos y enemigos
    private static void manageShootsToEnemies() {
        for (Pair<Shoot, Enemy> shootToEnemy: shootsToEnemies) {
            EnemiesManager.manageCollisionWithShoot(shootToEnemy);
            ShootsManager.manageCollisionWithEnemy(shootToEnemy);
        }
        shootsToEnemies = new Array<Pair<Shoot, Enemy>>();
    }

    //Gestionará las colisiones entre disparos y disparos
    private static void manageShootsToShoots() {
        for (Pair<Shoot, Shoot> shootToShoot: shootsToShoots) {
            ShootsManager.manageCollisionWithShoot(shootToShoot);
        }
        shootsToShoots = new Array<Pair<Shoot, Shoot>>();
    }

}
