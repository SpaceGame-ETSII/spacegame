package com.tfg.spacegame.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.enemies.RedEnemy;
import com.tfg.spacegame.gameObjects.shoots.*;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.enums.TypeWeapon;

public class ShootsManager {

    //TODO Hay que borrar esto cuando arreglemos el CampaignScreen
    public static Ship ship;

    //Almacenará todos los shoots en pantalla
    public static Array<Shoot> shoots;

    //Será necesario para hacer una ráfaga de disparos básicos
    private static int numberOfBasicShoots;

    //Guarda el último disparo realizado en una ráfaga
    private static Shoot lastShootOfBurst;

    //Guarda el punto de partida del último disparo realizado en una ráfaga
    private static float startPoint;

    //Tipo de arma a hacer la ráfaga (naranja o basica)
    private static TypeWeapon typeToBurst;

    //Si hubiese un objetivo de la ráfaga, lo guardamos aqui
    private static GameObject burstTarget;

    //Dentro del efecto ráfaga existe un factor de aparición que definiremos
    //en el método burst. A mayor número mayor tiempo entre disparos
    //Menos de 1.0 los disparos se soperponen
    private static double aparitionFactor;

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
            typeToBurst = TypeWeapon.BASIC;
            aparitionFactor = 1.3;
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
        for(Shoot shoot: shoots){
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
            case BLUE:
                if(selected.size <= 1)
                    result = true;
                break;
            case YELLOW:
                if(selected.size <= 0)
                    result = true;
                break;
            case PURPLE:
                if(selected.size <= 1)
                    result = true;
                break;
            case ORANGE:
                if(selected.size <= 0)
                    result = true;
                break;
            case GREEN:
                if(selected.size <= 1)
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
                shoot.changeToDeletable();
                shoots.removeValue(shoot,false);
            }
        }

        ShootsManager.ship = ship;

        updateBurst(ship);
    }

    //Actualiza el estado de la ráfaga ee disparo que haya en pantalla
    public static void updateBurst(Ship ship) {
        //Si estamos en medio de una ráfaga de la nave, continuamos disparando si es el momento
        if (numberOfBasicShoots > 0) {
            //Disparamos un nuevo shoot en la ráfaga si no hubo un último, o bien la distancia recorrida por el
            //último es superior a su punto de inicio más su ancho por 1.3
            if (lastShootOfBurst == null ||
                    lastShootOfBurst.getX() > (startPoint + lastShootOfBurst.getWidth()) * aparitionFactor) {
                if(typeToBurst.equals(TypeWeapon.BASIC))
                    lastShootOfBurst = shootOneBasicWeapon(ship);
                else if(typeToBurst.equals(TypeWeapon.ORANGE))
                    lastShootOfBurst = shootOneOrangeWeapon(ship,burstTarget);
                numberOfBasicShoots -= 1;
                startPoint = lastShootOfBurst.getX();
                //Si acabamos de lanzar el último disparo de la ráfaga, no lo guardamos
                if (numberOfBasicShoots == 0){
                    lastShootOfBurst = null;
                    // Si el burst era de un tipo naranja, desactivamos el efecto de localización.
                    if(typeToBurst.equals(TypeWeapon.ORANGE)){
                        Enemy enemy = (Enemy) burstTarget;
                        enemy.setTargettedByShip(false);
                    }
                }
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
        Red redShoot = new Red(shooter,0,0);
        if (shooter instanceof Ship){
            if(isShipReadyToShoot(TypeWeapon.RED)){
                int x = (int) (shooter.getX() + shooter.getWidth());
                int y = (int) (shooter.getY() + shooter.getHeight()/2);

                redShoot.setX(x);
                redShoot.setY(y);

                shoots.add(redShoot);
            }
        }else if (shooter instanceof RedEnemy){
            int x = (int) (shooter.getX() - redShoot.getWidth());
            int y = (int) (shooter.getY() + shooter.getHeight()/2);

            redShoot.setX(x);
            redShoot.setY(y);

            shoots.add(redShoot);
        }

    }

    public static void shootBlueWeapon(GameObject shooter, float yTarget) {
        Blue blueShoot;

        if (shooter instanceof Ship) {
            if (isShipReadyToShoot(TypeWeapon.BLUE)) {
                int x = (int) (shooter.getX() + shooter.getWidth());
                int y = (int) (shooter.getY() + shooter.getHeight() / 3);

                blueShoot = new Blue(shooter, x, y, yTarget);

                shoots.add(blueShoot);
            }
        } else {
            int x = (int) (shooter.getX());
            int y = (int) (shooter.getY() + shooter.getHeight() / 2);

            blueShoot = new Blue(shooter, x, y, ShootsManager.ship.getY() + (ShootsManager.ship.getHeight()/2));
            blueShoot.setX(blueShoot.getX() - blueShoot.getWidth());

            shoots.add(blueShoot);
        }
    }

    public static void shootYellowWeapon(GameObject shooter, float xTarget, float yTarget) {
        Yellow yellowShoot = new Yellow(shooter, xTarget, yTarget);

        if (shooter instanceof Ship) {
            if (isShipReadyToShoot(TypeWeapon.YELLOW)) {
                shoots.add(yellowShoot);
            }
        } else {
            shoots.add(yellowShoot);
        }
    }

    public static void shootPurpleWeapon(GameObject shooter, float xTarget, float yTarget) {
        Purple purpleShoot;

        if (shooter instanceof Ship) {
            if (isShipReadyToShoot(TypeWeapon.PURPLE)) {
                int x = (int) (shooter.getX() + shooter.getWidth());
                int y = (int) (shooter.getY() + shooter.getHeight() / 2);

                purpleShoot = new Purple(shooter, x, y, xTarget, yTarget);

                shoots.add(purpleShoot);
            }
        }
    }


    public static void shootBurstOrangeWeapon(GameObject gameObject, float x, float y) {
        Enemy enemy = EnemiesManager.getEnemyFromPosition(x,y);
        if(enemy != null && isShipReadyToShoot(TypeWeapon.ORANGE)){
            numberOfBasicShoots = 12;
            startPoint = 0;
            typeToBurst = TypeWeapon.ORANGE;
            burstTarget = enemy;
            aparitionFactor = 1.0;
            enemy.setTargettedByShip(true);
        }

    }

    public static Orange shootOneOrangeWeapon(GameObject shooter, GameObject target) {
        Orange result = null;

        int xShoot =(int) (shooter.getX() + shooter.getWidth());
        int yShoot = (int) (shooter.getY() + shooter.getHeight()/2);

        float angle = (shoots.size/(float)(numberOfBasicShoots + shoots.size)) * 45f;

        float angleOfStart = MathUtils.random(-angle,angle);

        result = new Orange(shooter,xShoot,yShoot, angleOfStart, target);
                shoots.add(result);

        return result;
    }

    public static void shootGreenWeapon(GameObject shooter, float yTarget) {
        Green greenShoot;

        if (shooter instanceof Ship) {
            if (isShipReadyToShoot(TypeWeapon.GREEN)) {
                int x = (int) (shooter.getX() + shooter.getWidth());
                int y = (int) (shooter.getY() + shooter.getHeight() / 3);

                greenShoot = new Green(shooter, x, y, yTarget);

                shoots.add(greenShoot);
            }
        } else {
            int x = (int) (shooter.getX());
            int y = (int) (shooter.getY() + shooter.getHeight() / 2);

            greenShoot = new Green(shooter, x, y, ShootsManager.ship.getY() + (ShootsManager.ship.getHeight()/2));
            greenShoot.setX(greenShoot.getX() - greenShoot.getWidth());

            shoots.add(greenShoot);
        }
    }

    public static void shootGreenFireWeapon(GameObject shooter, float xTarget, float yTarget) {
        GreenFire greenFireShoot = new GreenFire(shooter, xTarget, yTarget);
        shoots.add(greenFireShoot);
    }

    //Devuelve el arma verde en pantalla disparada por el shooter pasado por parámetro, si no existe devuelve null
    public static Green getGreenShootByShooterOnScreen(GameObject shooter) {
        Green green = null;
        for (Shoot shoot: shoots) {
            if (shoot instanceof Green && shoot.getShooter().equals(shooter) && !shoot.isShocked())
                green = (Green) shoot;
        }
        return green;
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
