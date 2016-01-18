package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.TypeElement;

public class Inventary extends GameObject {

    //Son las esferas de los elementos que tiene el inventario
    private Element red;
    private Element yellow;
    private Element blue;

    //Constante de velocidad con la que el invetario se abrirá y cerrará
    private final static int TRANSITION_SPEED = 900;

    //Atributo auxiliar que almacenará los cálculos de la posición del inventario en el movimiento
    private float relativePos;

    //Indica si el inventario se está cerrando
    private boolean isClosing;

    //Son los huecos donde se puede equipar los elementos para aplicar al arma
    private TypeElement slot1;
    private TypeElement slot2;

    public Inventary() {
        super("inventary", 0, 0);

        red = new Element("red", 0, 0, "rojo_seleccionado");
        yellow = new Element("yellow", 0, 0, "amarillo_seleccionado");
        blue = new Element("blue", 0, 0, "azul_seleccionado");

        relativePos = 0.0f;

        slot1 = TypeElement.COLORLESS;
        slot2 = TypeElement.COLORLESS;
    }

    //Se ejecutará cada vez que se active el inventario
    public void restart() {
        isClosing = false;

        this.setToInitialState();

        this.setX(-this.getWidth());
        red.setX(red.getX() - this.getWidth());
        yellow.setX(yellow.getX() - this.getWidth());
        blue.setX(blue.getX() - this.getWidth());
    }

    //Pone el inventario en su estado inicial
    private void setToInitialState() {
        this.setX(0);
        this.setY(0);
        red.setX(32);
        red.setY(350);
        yellow.setX(31);
        yellow.setY(193);
        blue.setX(29);
        blue.setY(39);
        red.setIsActivate(false);
        blue.setIsActivate(false);
        yellow.setIsActivate(false);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);

        red.render(batch);
        yellow.render(batch);
        blue.render(batch);
    }

    public void update(float delta, Ship ship, float x, float y) {

        //Primero comprobamos si el inventario está colocado en su sitio
        if (this.getX() < 0) {
            relativePos = TRANSITION_SPEED * delta;

            this.setX(this.getX() + relativePos);
            red.setX(red.getX() + relativePos);
            yellow.setX(yellow.getX() + relativePos);
            blue.setX(blue.getX() + relativePos);

            ship.setX(ship.getX() + relativePos);

            //Evitamos que el inventario se pase de largo
            if (this.getX() > 0) {
                this.setToInitialState();
                ship.setX(this.getWidth() + 1);
            }
        } else {
            //Como el inventario está colocado en su sitio, ahora comprobamos si el jugador está interactuando con algún elemento
            if (Gdx.input.isTouched()) {
                deactivateElements();
                if (red.isOverlapingWith(x, y)) {
                    red.setIsActivate(true);
                }
                if (yellow.isOverlapingWith(x, y)) {
                    yellow.setIsActivate(true);
                }
                if (blue.isOverlapingWith(x, y)) {
                    blue.setIsActivate(true);
                }
            }
            if (red.isActivate())
                red.update(delta);
            if (blue.isActivate())
                blue.update(delta);
            if (yellow.isActivate())
                yellow.update(delta);
        }
    }

    public void updateClosing(float delta, Ship ship) {
        if (this.getX() > -this.getWidth()) {
            relativePos = TRANSITION_SPEED * delta;

            this.setX(this.getX() - relativePos);
            red.setX(red.getX() - relativePos);
            yellow.setX(yellow.getX() - relativePos);
            blue.setX(blue.getX() - relativePos);

            ship.setX(ship.getX() - relativePos);
        } else {
            isClosing = false;
            ship.setX(0);
        }
    }

    public boolean isClosing() {
        return isClosing;
    }

    public void setIsClosing(boolean isClosing) {
        deactivateElements();
        this.isClosing = isClosing;
    }

    public void deactivateElements() {
        red.setIsActivate(false);
        blue.setIsActivate(false);
        yellow.setIsActivate(false);
    }

}
