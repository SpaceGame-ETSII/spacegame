package com.tfg.spacegame.gameObjects.shoots;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.AssetsManager;

public class Yellow extends Shoot{

    private Vector2 vector;

    private ParticleEffect shootEffect;

    public Yellow(GameObject shooter) {
        super("yellow_shoot", 0, 0, shooter);
        this.setX((int)(shooter.getX()+shooter.getWidth()+this.getHeight()));
        this.setY(getShooter().getY() + this.getHeight()/2);
        this.getLogicShape().setOrigin(0,this.getHeight()/2);

        shootEffect = AssetsManager.loadParticleEffect("yellow_shoot_effect");
        shootEffect.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY()+this.getHeight()/2);

        shootEffect.start();

        vector = new Vector2();
    }

    public void update(float delta){
        this.setY(getShooter().getY() + this.getHeight());
        shootEffect.update(delta);
        shootEffect.getEmitters().first().setPosition(this.getX() - this.getHeight()/2, this.getY() + this.getHeight()/2);

        if(Gdx.input.isTouched(1) || (Gdx.input.isTouched(0) && SpaceGame.getTouchPos(0).x > SpaceGame.width/3)){
            // Obtenemos el angulo a donde tenemos que girar
            float x1 = this.getX();
            float y1 = this.getY();
            float x2 = this.getX()+this.getWidth();
            // Dependiendo de si ha habido multitouch o no obtenemos el valor Y correspondiente
            float y2;
            if(Gdx.input.isTouched(1))
                y2 = SpaceGame.getTouchPos(1).y;
            else
                y2 = SpaceGame.getTouchPos(0).y;
            vector.set(x2-x1,y2-y1);
            // Basta con llamar al vector.angle para tener el angulo a girar
            // Hay que tener que cuenta que hay que invertir este angulo, es decir
            // 360 - vector.angle para que esté bien situado
            float angle = vector.angle();

            this.getLogicShape().setRotation(angle);

            ParticleEmitter.ScaledNumericValue angles = this.shootEffect.getEmitters().first().getAngle();
            angles.setLow(angle);
            angles.setHigh(angle-15,angle+15);
        }else{
            this.changeToDeletable();
        }
    }

    public void render(SpriteBatch batch){
        shootEffect.draw(batch);
    }

    public void dispose(){
        super.dispose();
        shootEffect.dispose();
    }
}
