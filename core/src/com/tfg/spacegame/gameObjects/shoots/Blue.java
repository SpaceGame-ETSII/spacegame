package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Blue extends Shoot {

    //Velocidad de movimiento
    public static final float SPEED = 300;

    //Constante que se multiplicará por la velocidad cuando el misil gire
    public static final float ACCELERATION = 70;

    //Efecto de particulas del fuego de propulsión
    private ParticleEffect propulsionEffect;

    //Indica el punto objetivo adonde se dirigirá la nave
    private float xTarget;

    //Indica el punto central entre el sitio donde se dispara y el punto donde se pretende ir
    private float xCenter;
    private float yCenter;

    //Almacenará el centro del círculo que servirá como referencia para cada giro
    private float xCenterOfCircle;

    //Radio del círculo que servirá de referencia para el giro
    private float diameter;

    //Almacenará los grados para calcular el giro
    private float degrees;

    //Velocidad a la que girará el misil
    private float speed_for_circle;

    //Servirá para indicar si el giro va en el sentido de las agujas del reloj o al revés
    private int direction;

    //Servirá para impedir que la nave gire si el yTarget es apenas diferentemente apreciable al Y del shooter
    private boolean canRotate;

    public Blue(GameObject shooter, int x, int y, float yTarget) {
        super("blue_shoot",x,y,shooter,
                AssetsManager.loadParticleEffect("blue_shoot_effect_shoot"),
                AssetsManager.loadParticleEffect("blue_shoot_effect_shock"));

        propulsionEffect = AssetsManager.loadParticleEffect("blue_propulsion_effect");

        //Comprobamos si el punto objetivo está arriba o abajo de la posición de tiro
        //Esto servirá para saber cuántos son los grados iniciales y dónde estará el punto central
        if (yTarget >= y) {
            degrees = 270;
            yCenter = ((yTarget - y) / 2) + y;
        } else {
            degrees = 90;
            yCenter = ((y - yTarget) / 2) + yTarget;
        }

        //Sabiendo la altura del punto central, calculamos el diámetro que tendrá el círculo de referencia para el giro
        diameter = Math.abs(yCenter - y);

        //Dependiendo de si es la nave o no el shooter, el disparo vendrá de la derecha o la izquierda
        if (shooter instanceof Ship) {
            xTarget = SpaceGame.width - (SpaceGame.width / 8);
            xCenter = ((xTarget - x) / 2) + x;
            xCenterOfCircle = xCenter - diameter;
        } else {
            xTarget = SpaceGame.width / 8;
            xCenter = ((x - xTarget) / 2) + xTarget;
            xCenterOfCircle = xCenter + diameter;
        }

        //La velocidad del círculo dependerá completamente del diámetro del círculo de giro
        speed_for_circle = SPEED * ACCELERATION / diameter;

        //Damos un valor inicial, aunque realmente no servirá para nada
        direction = 1;

        //Si la distancia entre la posición del shooter y el yTarget no supera el 8% de la pantalla, el disparo irá recto
        if (Math.abs(yTarget - (shooter.getY() + (shooter.getHeight()/2))) < (SpaceGame.height * 8 / 100)) {
            canRotate = false;
        } else {
            canRotate = true;
        }

        this.updateParticleEffect();
        propulsionEffect.start();
    }

    public void update(float delta) {
        if (!this.isShocked()) {
            //Actualizamos el movimiento del disparo
            if (getShooter() instanceof Ship) {

                //Comprobamos si la nave no está dentro del rango donde debe girar o si debe rotar
                if (Math.abs(xCenter - this.getX()) > diameter || !this.canRotate) {
                    //La nave está fuera del rango de giro

                    //Simplemente avanzamos la X
                    this.setX(this.getX() + (SPEED * delta));
                } else {
                    //La nave está dentro del rango de giro

                    //Si el punto central es superior a la posición del misil, el sentido de giro será el de las agujas del reloj
                    if (yCenter > this.getY()) {
                        direction = 1;
                    } else {
                        direction = -1;
                    }

                    //Actualizamos los grados a girar en base a la velocidad, dependiente de delta y en el sentido adecuado
                    degrees += speed_for_circle * delta * direction;

                    //Comprobamos si los grados sale del rango de 0 a 360, lo que indicará que el misil terminó el primer giro
                    if (degrees > 360 || degrees < 0) {
                        //Ahora preparamos el inicio del segundo giro

                        //Colocamos el centro del círculo listo para el siguiente giro
                        xCenterOfCircle = xCenter + diameter;

                        //Cambiamos a 180 grados que es el primer estado del segundo giro
                        degrees = 180;

                        //Recolocamos la X para hacer que siempre se salte el if del primer giro
                        this.setX(xCenter);

                        //Ajustamos para que la posición Y sobre pase el yCenter y no tengamos problemas para que direction se actualice
                        this.setY((float) (yCenter + 0.1 * direction));
                    } else {
                        //En caso de no ser el comienzo del segundo giro, simplemente continuamos el giro normalmente
                        this.setX(xCenterOfCircle + (diameter * MathUtils.cosDeg(degrees)));
                        this.setY(yCenter + (diameter * MathUtils.sinDeg(degrees)));
                    }

                }
            } else {
                //De momento, el disparo de un enemigo va recto
                this.setX(this.getX() - (SPEED * delta));
            }
        }

        this.updateParticleEffect();

        //Actualizamos los efectos de partículas
        super.update(delta);

        if (!this.isShocked()) {
            propulsionEffect.update(delta);
        }
    }

    public void updateParticleEffect() {
        super.updateParticleEffect();

        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Enemy) {
                propulsionEffect.getEmitters().first().setPosition(this.getX(),this.getY() + this.getHeight()/2);

                // Rotamos el efecto de particulas 180º
                propulsionEffect.getEmitters().first().getAngle().setHigh(180,180);
            } else {
                // Lo centramos con la nave para que salga en la posición de su cañón
                propulsionEffect.getEmitters().first().setPosition(this.getX(),this.getY() + this.getHeight()/2);
            }
        }
    }

    public void render(SpriteBatch batch){
        super.render(batch);
        if (!this.isShocked()) {
            propulsionEffect.draw(batch);
        }
    }

    public void collideWithShip() {
        super.collideWithShip();
        this.shock();
    }

    public void collideWithEnemy(Enemy enemy) {
        super.collideWithEnemy(enemy);
        this.shock();
    }

    public void collideWithShoot(Shoot shoot) {
        super.collideWithShoot(shoot);
        this.shock();
    }

    public void dispose(){
        super.dispose();
        propulsionEffect.dispose();
    }

}