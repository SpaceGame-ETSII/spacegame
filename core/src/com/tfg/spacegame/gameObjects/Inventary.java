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

    //Son los huecos donde se puede equipar los elementos para aplicar al arma
    private Slot slot1;
    private Slot slot2;

    //Flecha indicadora para facilitar al jugador dónde colocar los elementos
    private GameObject arrow1;
    private GameObject arrow2;

    //Constante de velocidad con la que el inventario se abrirá y cerrará
    private final static int TRANSITION_SPEED = 900;

    //Velocidad con la que las flechas suben y bajan, y que puede cambiar de sentido
    private int transitionArrow;

    //Atributo auxiliar que almacenará los cálculos de la posición del inventario en el movimiento
    private float relativePos;

    //Indica si el inventario se está cerrando
    private boolean isClosing;

    public Inventary() {
        super("inventary", 0, 0);

        red = new Element("red", 0, 0, "rojo_seleccionado");
        yellow = new Element("yellow", 0, 0, "amarillo_seleccionado");
        blue = new Element("blue", 0, 0, "azul_seleccionado");

        slot1 = new Slot("slot", 0, 0);
        slot2 = new Slot("slot", 0, 0);

        arrow1 = new GameObject("arrow", 0, 0);
        arrow2 = new GameObject("arrow", 0, 0);

        relativePos = 0.0f;

        transitionArrow = 0;
    }

    //Se ejecutará cada vez que se active el inventario
    public void restart() {
        isClosing = false;

        this.setToInitialState(0.0f);

        this.setX(-this.getWidth());
        red.setX(red.getX() - this.getWidth());
        yellow.setX(yellow.getX() - this.getWidth());
        blue.setX(blue.getX() - this.getWidth());

        slot1.setX(slot1.getX() - this.getWidth());
        slot2.setX(slot2.getX() - this.getWidth());
    }

    //Coloca el inventario en su posición inicial
    private void setToInitialState(float delta) {
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

        slot1.setX(delta, 190);
        slot1.setY(delta, 151);
        slot2.setX(delta, 190);
        slot2.setY(delta, 276);

        arrow1.setX(203);
        arrow1.setY(213);
        arrow2.setX(203);
        arrow2.setY(340);

        transitionArrow = 150;
    }

    public void render(SpriteBatch batch) {
        super.render(batch);

        red.render(batch);
        yellow.render(batch);
        blue.render(batch);

        slot1.render(batch);
        slot2.render(batch);

        if (red.isActivate() || blue.isActivate() || yellow.isActivate()) {
            arrow1.render(batch);
            arrow2.render(batch);
        }
    }

    public void update(float delta, Ship ship, float x, float y) {

        //Primero comprobamos si el inventario no está colocado en su sitio
        if (this.getX() < 0) {
            //En caso de no estar en su sitio quiere decir que está en movimiento, por tanto movemos los elementos y la nave según la posición relativa
            relativePos = TRANSITION_SPEED * delta;
            this.moveAllElements(delta, relativePos);
            ship.setX(ship.getX() + relativePos);

            //Evitamos que el inventario se pase de largo
            if (this.getX() > 0) {
                this.setToInitialState(delta);
                ship.setX(this.getWidth() + 1);
            }
        } else {
            //Como el inventario está colocado en su sitio, ahora comprobamos si el jugador está interactuando con algún elemento

            if (Gdx.input.isTouched()) {

                //Si se ha pulsado algún slot, equipamos el elemento que estuviese activado
                this.checkSlotIsTouched(slot1, x, y);
                this.checkSlotIsTouched(slot2, x, y);

                //Desactivamos los elementos antes de activar el que se haya pulsado (si se dió el caso)
                deactivateElements();

                //Si se ha pulsado un elemento, lo activamos
                this.checkAnyElementIsTouched(x, y);
            }

            //Actualizamos cada objeto en los casos que sean necesario
            updateAllObjects(delta);
        }
    }

    //Mueve todos los elementos según la posición relativa
    public void moveAllElements(float delta, float distance) {
        this.setX(this.getX() + distance);
        red.setX(red.getX() + distance);
        yellow.setX(yellow.getX() + distance);
        blue.setX(blue.getX() + distance);
        slot1.setX(delta, slot1.getX() + distance);
        slot2.setX(delta, slot2.getX() + distance);
    }

    //Invoca los updates de todos los elementos dentro del inventario
    public void updateAllObjects(float delta) {
        if (red.isActivate())
            red.update(delta);
        if (blue.isActivate())
            blue.update(delta);
        if (yellow.isActivate())
            yellow.update(delta);
        if (slot1.hasElementEquipped())
            slot1.update(delta);
        if (slot2.hasElementEquipped())
            slot2.update(delta);
        if (this.isAnyElementActivated())
            updateArrows(delta);
    }

    //Actualiza el movimiento de las flechas que indican la posición de los slots
    public void updateArrows(float delta) {
        if ((arrow1.getY() > 220 && transitionArrow > 0) || (arrow1.getY() < 190 && transitionArrow < 0))
            transitionArrow *= -1;
        arrow1.setY(arrow1.getY() + transitionArrow * delta);
        arrow2.setY(arrow2.getY() + transitionArrow * delta);
    }

    //Actualiza el cierre del inventario
    public void updateClosing(float delta, Ship ship) {
        //Hacemos una cosa u otra según si el inventario no se ha cerrado del todo
        if (this.getX() > -this.getWidth()) {
            relativePos = TRANSITION_SPEED * delta;

            this.setX(this.getX() - relativePos);
            red.setX(red.getX() - relativePos);
            yellow.setX(yellow.getX() - relativePos);
            blue.setX(blue.getX() - relativePos);

            slot1.setX(delta, slot1.getX() - relativePos);
            slot2.setX(delta, slot1.getX() - relativePos);

            ship.setX(ship.getX() - relativePos);
        } else {
            //Si el inventario se ha cerrado completamente, cambiamos la bandera y recolocamos la nave
            isClosing = false;
            ship.setX(0);
        }
    }

    //Indica si el inventario está cerrándose
    public boolean isClosing() {
        return isClosing;
    }

    //Pondrá el inventario en el estado en el que está cerrándose, y por tanto desactivará el elemento que lo estuviese
    public void setIsClosing(boolean isClosing) {
        deactivateElements();
        this.isClosing = isClosing;
    }

    //Coloca el elemento que estuviese activado sobre un slot si éste ha sido tocado
    public void checkSlotIsTouched(Slot slot, float x, float y) {
        if (slot.isOverlapingWith(x, y)) {
            if (red.isActivate())
                slot.equipElement(TypeElement.RED);
            if (blue.isActivate())
                slot.equipElement(TypeElement.BLUE);
            if (yellow.isActivate())
                slot.equipElement(TypeElement.YELLOW);
        }
    }

    //Activa un elemento si éste ha sido tocado
    public void checkAnyElementIsTouched(float x, float y) {
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

    public boolean isAnyElementActivated() {
        return red.isActivate() || blue.isActivate() || yellow.isActivate();
    }

    public void deactivateElements() {
        red.setIsActivate(false);
        blue.setIsActivate(false);
        yellow.setIsActivate(false);
    }

    public void dipose() {
        red.dispose();
        blue.dispose();
        yellow.dispose();
        slot1.dispose();
        slot2.dispose();
        arrow1.dispose();
        arrow2.dispose();
    }

}