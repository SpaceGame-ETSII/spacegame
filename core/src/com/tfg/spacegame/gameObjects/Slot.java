package com.tfg.spacegame.gameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tfg.spacegame.GameObject;
import com.tfg.spacegame.utils.TypeElement;

public class Slot extends GameObject {

    //Tipo de elemento que se ha colocado en el slot
    private TypeElement equippedElement;

    //Efecto de partícula en el caso de tener equipado un elemento
    private ParticleEffect particleEffect;

    public Slot(String texture, int x, int y) {
        super(texture, x, y);

        equippedElement = TypeElement.COLORLESS;
        particleEffect = null;
    }

    public void setX(float delta, float x) {
        super.setX(x);
        this.updateParticleEffect(delta);
    }

    public void setY(float delta, float y) {
        super.setY(y);
        this.updateParticleEffect(delta);
    }

    public void render(SpriteBatch batch) {
        super.render(batch);
        if (this.hasElementEquipped()) {
            this.particleEffect.draw(batch);
        }
    }

    //Recoloca el efecto de partícula sobre la posición del slot
    public void updateParticleEffect(float delta) {
        if (particleEffect != null) {
            particleEffect.getEmitters().first().setPosition((getWidth() / 2) + getX(), (getHeight() / 2) + getY());
            this.update(delta);
        }
    }

    public void update(float delta) {
        if (this.hasElementEquipped()) {
            particleEffect.update(delta);
        }
    }

    public boolean hasElementEquipped() {
        return !equippedElement.equals(TypeElement.COLORLESS);
    }
    
    public boolean hasSpecifiedElement(TypeElement type) { return equippedElement.equals(type); }


    public void equipElement(TypeElement type) {
        equippedElement = type;
        particleEffect = new ParticleEffect();

        if (type.equals(TypeElement.RED))
            particleEffect.load(Gdx.files.internal("particleEffects/rojo_equipado"), Gdx.files.internal(""));
        else if (type.equals(TypeElement.BLUE))
            particleEffect.load(Gdx.files.internal("particleEffects/azul_equipado"), Gdx.files.internal(""));
        else if (type.equals(TypeElement.YELLOW))
            particleEffect.load(Gdx.files.internal("particleEffects/amarillo_equipado"), Gdx.files.internal(""));

        particleEffect.getEmitters().first().setPosition((getWidth() / 2) + getX(), (getHeight() / 2) + getY());
    }

    public void unequip() {
        equippedElement = TypeElement.COLORLESS;
        particleEffect = null;
    }

}
