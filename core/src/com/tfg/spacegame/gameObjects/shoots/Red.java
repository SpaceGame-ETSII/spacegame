package com.tfg.spacegame.gameObjects.shoots;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Ship;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Red extends Shoot{

    //Velocidad de movimiento
    public static final float SPEED = 800;

    /*Tiempo necesario a esperar para que empiece a moverse el disparo
     Se trata de un delay de aparición*/
    private float timeToMove;

    //Variable para guardar el tamaño del disparo a pintar en pantalla, así conseguimos pintar el disparo de manera dinámica
    private int pixelsToDraw;

    //Variable para guardar la textura
    private TextureRegion texture;

    //Efecto de particulas de este disparo
    private ParticleEffect shoot;

    //Efecto de particulas de este disparo
    private ParticleEffect shootEffect;

    //Efecto de partículas cuando el disparo choca
    private ParticleEffect shockEffect;

    public Red(GameObject shooter, int x, int y,float delay) {
        super("red_shoot", x, y, shooter);

        texture = new TextureRegion(this.getTexture());

        //Creamos el efecto de particulas
        shoot = AssetsManager.loadParticleEffect("red_shoot_effect");
        shootEffect = AssetsManager.loadParticleEffect("red_effect_shock");
        shockEffect = AssetsManager.loadParticleEffect("red_shoot_effect_shoot");

        this.updateParticleEffect();

        //Lo iniciamos, pero aunque lo iniciemos si no haya un update no avanzará
        shoot.start();
        shootEffect.start();
        shockEffect.start();

        timeToMove = delay;
        pixelsToDraw = 0;
    }

    public void updateParticleEffect() {
        //Comprobamos si el disparo ha chocado
        if (!this.isShocked()) {
            //Se actuará de forma distinta si el shooter es enemigo o no
            if (this.getShooter() instanceof Ship) {
                shoot.getEmitters().first().setPosition(this.getX()-10,this.getY()+3);
                shootEffect.getEmitters().first().setPosition(this.getX(), this.getY());
                shootEffect.getEmitters().first().getAngle().setHigh(135, 225);
                shootEffect.getEmitters().first().getAngle().setLow(160, 200);
            } else {
                //FALTA EL ENEMIGO ROJO
            }
        } else {
            //Si el disparo ha chocado, el efecto a mostrar es el del shockEffect
            if (this.getShooter() instanceof Enemy) {
            } else {
                shockEffect.getEmitters().first().setPosition(this.getX() + this.getWidth(), this.getY());
            }
        }
    }

    public void update(float delta) {
        if (!this.isShocked()) {
            //Esperaremos a que sea el momento correcto para moverse
            if (timeToMove < 0) {
                //Actualizamos el movimiento del disparo
                if (getShooter() instanceof Ship)
                    this.setX(this.getX() + (SPEED * delta));
                else
                    this.setX(this.getX() - (SPEED * delta));

                //Actualizamos la posición del efecto de particulas de acuerdo con la posición del shooter
                this.updateParticleEffect();

                //Actualizamos el efecto de particulas
                shoot.update(delta);
                shootEffect.update(delta);
            }
            //Mientras no sea el momento para moverse
            else {
                //Vamos a ir restando el tiempo de delay con el delta hasta que sea menor que 0
                timeToMove -= pixelsToDraw;

                //Podemos además ir actualizando la posición Y por si el shooter se está moviendo
                this.setY(getShooter().getY() + getShooter().getHeight() / 2 - this.getHeight() / 2);
            }
        } else {
            // Actualizamos el efecto de particulas
            this.updateParticleEffect();
            shockEffect.update(delta);

            // Restamos el tiempo que estará el efecto en pantalla, y si pasa el tiempo, marcamos el shoot como deletable
            if (shockEffect.isComplete()) {
                this.changeToDeletable();
            }
        }

    }

    public void render(SpriteBatch batch){
        if (!this.isShocked()) {
            if(pixelsToDraw<=texture.getRegionWidth()){
                texture.setRegion(0,0,pixelsToDraw,texture.getRegionHeight());
                pixelsToDraw += 10;
            }
            batch.draw(texture, this.getX(), this.getY());

            if(timeToMove < 0) {
                shoot.draw(batch);
                shootEffect.draw(batch);
            }
        } else {
            shockEffect.draw(batch);
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
        shoot.dispose();
        shootEffect.dispose();
    }
}
