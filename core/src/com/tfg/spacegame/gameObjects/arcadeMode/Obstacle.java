package com.tfg.spacegame.gameObjects.arcadeMode;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.SpaceGame;
import com.tfg.spacegame.utils.enums.TypeObstacle;

public class Obstacle extends GameObject {

    //Velocidad de giro según los tamaños
    private final static int BIG_ROTATION_SPEED = 20;
    private final static int MEDIUM_ROTATION_SPEED = 50;
    private final static int SMALL_ROTATION_SPEED = 200;

    //Rango en el que puede variar la velocidad de rotación
    private final static int RANGE_FOR_ADITIVE_SPEED_ROTATION = 20;

    //Velocidad a la que se moverá el obstáculo
    private final static int INITIAL_SPEED = 150;

    //Velocidad a la que se moverá el obstáculo
    private float speed;

    //Tipo de obstáculo según constitución
    private TypeObstacle type;

    //Grados en los que girará el obstáculo
    private float degrees;

    public Obstacle(String textureName, int x, int y, TypeObstacle type) {
        super(textureName, x, y);

        this.type = type;
        speed = INITIAL_SPEED;
        degrees = 0;
    }

    public void update(float delta) {
        //Primero rotamos el obstáculo
        this.setRotation(degrees);

        //Aumentamos los grados para la siguiente ocasión
        this.updateDegrees(delta);

        //Actualizamos la posición del obstáculo
        this.setY(this.getY() - (speed * delta));
    }

    private void updateDegrees(float delta) {
        //Calculamos un valor para aumentar o decrementar la velocidad dentro de un rango
        int aditiveSpeed = MathUtils.random(-RANGE_FOR_ADITIVE_SPEED_ROTATION, RANGE_FOR_ADITIVE_SPEED_ROTATION);

        //Según el tipo de obstáculo usaremos una velocidad de rotación u otra
        if (type.equals(TypeObstacle.BIG)) {
            degrees += (BIG_ROTATION_SPEED + aditiveSpeed) * delta;
        } else if (type.equals(TypeObstacle.MEDIUM)) {
            degrees += (MEDIUM_ROTATION_SPEED + aditiveSpeed) * delta;
        } else if (type.equals(TypeObstacle.SMALL)) {
            degrees += (SMALL_ROTATION_SPEED + aditiveSpeed) * delta;
        }
    }

    public void render() {
        this.renderRotate(degrees);
    }

    public void renderTransparent(float alpha) {
        Color c = SpaceGame.batch.getColor();
        float oldAlpha = c.a;

        c.a = alpha;
        SpaceGame.batch.setColor(c);

        this.renderRotate(degrees / 2);

        c.a = oldAlpha;
        SpaceGame.batch.setColor(c);
    }

    public void changeSpeed(float aditiveSpeed) {
        //this.speed += aditiveSpeed;
        this.speed = INITIAL_SPEED + aditiveSpeed;
    }

    public TypeObstacle getType() {
        return type;
    }

    public void dispose() {
        super.dispose();
    }

}