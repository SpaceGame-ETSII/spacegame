package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.utils.*;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.enums.ColorShip;
import com.tfg.spacegame.utils.ShootsManager;

public class Ship extends GameObject {

    //Indica la velocidad para el movimiento de la nave
    public static final float SPEED = 50;

    //Indica la cantidad de golpes recibidos
    private int damageReceived;

    //Indica el máximo de golpes que se puede recibir
    private static final int VITALITY = 5;

    //Variable usada para hacer la nave invulnerable cuando es golpeada
    private boolean undamagable;

    //Se usará como contador para volver la nave vulnerable
    private float timeToUndamagable;

    //Imagen de la cabina que irá sobre la nave y que se actualizará con los daños
    private Texture cockpit;

    //Sirve para indicar los tiempos en los que la nave parpadeará a ser invulnerable
    private int timeForInvisible;

    //Indicará la duración del estado invulnerable
    private static final float DURATION_UNDAMAGABLE = 2.0f;

    //Indica el rango por el que se moverá el timeForInvisible
    private static final int RANGE_INVISIBLE_TIMER = 5;

    //Indica el color de la nave
    private ColorShip color;

    //Efecto de partículas para el fuego de la nave
    private ParticleEffect fireEffect;

    public Ship() {
        super("ship", 0, 0);

        timeForInvisible = RANGE_INVISIBLE_TIMER;
        damageReceived = 0;
        timeToUndamagable = DURATION_UNDAMAGABLE;
        cockpit = AssetsManager.loadTexture("cockpit");
        this.setY(SpaceGame.height / 2 - getHeight() / 2);

        //Inicializamos el color de la nava a incoloro
        color = ColorShip.COLORLESS;

        //Creamos el efecto de partículas del fuego
        fireEffect = AssetsManager.loadParticleEffect("propulsion_ship_effect");

        this.updateParticleEffect();

        //Lo iniciamos, pero aunque lo iniciemos si no hay un update no avanzará
        fireEffect.start();
    }

    private void updateParticleEffect() {
        fireEffect.getEmitters().first().setPosition(this.getX() + 40,this.getY() + this.getHeight()/2 + 2);
    }

    @Override
    public void render(SpriteBatch batch){
        //Si la nave no está en modo invulnerable o lo está y timeForInvisible es positivo, mostramos la nave
        if (!this.isUndamagable() || (this.isUndamagable() && timeForInvisible > 0)) {
            super.render(batch);
            batch.draw(cockpit, this.getX() + 76, this.getY() + 24);
        }

        fireEffect.draw(batch);

    }

    public void update(float delta, float y, boolean canShipMove) {

        //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
        this.updateParticleEffect();

        //Actualizamos el efecto de particulas
        fireEffect.update(delta);
        //Movimiento de la nave
        if (canShipMove) {
            if (y < (this.getY() + this.getHeight() / 2))
                this.setY(this.getY() - (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
            if (y > (this.getY() + this.getHeight() / 2))
                this.setY(this.getY() + (Math.abs(y - (this.getY() + this.getHeight() / 2)) * SPEED * delta));
        }

        //Controlamos si la nave se sale de la pantalla
        if (this.getY() < 0)
            this.setY(0);
        if (this.getY() > SpaceGame.height - getHeight())
            this.setY(SpaceGame.height - getHeight());

        //Si la nave está en estado invulnerable, el contador se reduce y actualizamos el valor de timeForInvisible
        if (this.isUndamagable()) {
            //timeForInvisible irá saltando de uno en uno de un valor negativo a positivo según el rango, y vuelta a empezar
            if (timeForInvisible >= RANGE_INVISIBLE_TIMER) {
                timeForInvisible = -RANGE_INVISIBLE_TIMER;
            }
            timeForInvisible++;
            timeToUndamagable -= delta;
        }

        if (timeToUndamagable <= 0)
            this.changeToDamagable();

    }

    public void setX(float x, float delta){
        super.setX(x);
        this.updateParticleEffect();
        fireEffect.update(delta);
    }

    public void changeColor(ColorShip color){
        this.color = color;
    }

    //Realiza un disparo, en función del arma equipada
    public void shoot(float x, float y) {
        switch (color) {
            case COLORLESS:
                ShootsManager.shootBurstBasicWeaponForShip(this);
                break;
            case RED:
                ShootsManager.shootRedWeapon(this);
                break;
            case BLUE:
                ShootsManager.shootBlueWeapon(this, y);
                break;
            case YELLOW:
                ShootsManager.shootYellowWeapon(this);
                break;
            case GREEN:
                break;
            case ORANGE:
                break;
            case PURPLE:
                break;
            default:
                ShootsManager.shootBurstBasicWeaponForShip(this);
                break;
        }
    }

    //Aumenta en uno el daño a la nave, la vuelve invunerable y cambia la apariencia de la cabina
    public void receiveDamage() {
        if (!undamagable) {
            damageReceived++;
            if (damageReceived < VITALITY) {
                cockpit = AssetsManager.loadTexture("cockpit_damage" + damageReceived);
                undamagable = true;
            }

            //Hacemos vibrar el móvil para hacer más patente el daño
            Gdx.input.vibrate(300);
        }
    }

    //Indica si la nave está derrotada, es decir, que el daño recibido sea mayor o igual a la vitalidad
    public boolean isDefeated() {
        return damageReceived >= VITALITY;
    }

    //Inicia si la navez puede recibir daños
    public boolean isUndamagable() {
        return undamagable;
    }

    //Hace que la nave pueda volver a recibir daños en el caso en que estuviese vulnerable
    public void changeToDamagable() {
        undamagable = false;
        timeToUndamagable = DURATION_UNDAMAGABLE;
    }

    @Override
    public void dispose() {
        super.dispose();
        cockpit.dispose();
        fireEffect.dispose();
    }

}