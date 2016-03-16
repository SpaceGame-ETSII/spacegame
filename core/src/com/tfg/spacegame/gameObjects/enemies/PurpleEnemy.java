package com.tfg.spacegame.gameObjects.enemies;

import com.badlogic.gdx.utils.Array;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.enemies.partsOfEnemy.Eye;
import com.tfg.spacegame.utils.AssetsManager;
import com.tfg.spacegame.utils.DamageManager;
import com.tfg.spacegame.utils.enums.TypeEnemy;

public class PurpleEnemy extends Enemy {

    private enum PurpleEnemyState{
        APPEAR,OPEN_EYES,OPEN_MAIN_EYE
    }

    //Indica la velocidad a la que se moverá el enemigo
    private final int SPEED = 75;

    //Valor inicial que tendrá el contador de disparo cada vez que se reinicie
    private final int INITIAL_COUNTER = 150;

    private final float FREQUENCY_MAIN_EYE_OPEN = 3f;

    private float timeMainEyeOpen;

    public final int APPEAR_POSITION_X = 450;

    private PurpleEnemyState state;

    //Partes referentes a los ojos del enemigo
    private Eye eye1;
    private Eye eye2;
    private Eye eye3;
    private Eye eye4;

    //Parte del enemigo correspondiente al cuerpo del enemigo
    private PartOfEnemy body;

    //Contador que usaremos para saber cuándo disparar
    private float counterToShoot;

    public PurpleEnemy(int x, int y) {
        super("purple_eye_center", x, y, 1100, AssetsManager.loadParticleEffect("purple_destroyed"));

        // Establememos el tipo del enemigo
        type = TypeEnemy.PURPLE;

        state = PurpleEnemyState.APPEAR;

        //Creamos las distintas partes que compondrán al enemigo
        body = new PartOfEnemy("purple_body", x, y, 1,
                AssetsManager.loadParticleEffect("purple_destroyed"), this, false, false);
        eye1 = new Eye("purple_eye_1", x + 35, y + 380, this);
        eye2 = new Eye("purple_eye_2", x + 10, y + 260, this);
        eye3 = new Eye("purple_eye_3", x + 10, y + 145, this);
        eye4 = new Eye("purple_eye_4", x + 36, y + 25, this);

        //Actualizamos el ojo central para hacerlo conincidir con su posición dentro del cuerpo del enemigo
        this.setX(body.getX() + 175);
        this.setY(body.getY() + 165);

        //Inicializamos las variables de control
        counterToShoot = INITIAL_COUNTER;
        timeMainEyeOpen = 0;
    }

    private void changeClosedStatusAllEyes(boolean b){
        eye1.setClosed(b);
        eye2.setClosed(b);
        eye3.setClosed(b);
        eye4.setClosed(b);
    }

    private void changeWaitingStatusAllEyes(boolean b){
        eye1.setWaiting(b);
        eye2.setWaiting(b);
        eye3.setWaiting(b);
        eye4.setWaiting(b);
    }

    public void update(float delta) {
        super.update(delta);

        if (!this.isDefeated()) {
            switch (state){
                case APPEAR:
                    if(body.getX() >= APPEAR_POSITION_X){
                        this.setX(this.getX() - SPEED * delta);
                        body.setX(body.getX() - SPEED * delta);
                        eye1.setX(eye1.getX() - SPEED * delta);
                        eye2.setX(eye2.getX() - SPEED * delta);
                        eye3.setX(eye3.getX() - SPEED * delta);
                        eye4.setX(eye4.getX() - SPEED * delta);
                    }else{
                        changeClosedStatusAllEyes(false);

                        //Antes de preparar al enemigo para disparar, iniciamos el patrón de disparo del mismo
                        eye1.setWaiting(true);

                        //Una vez en la posición designada, el enemigo estará listo para empezar a disparar
                        state = PurpleEnemyState.OPEN_EYES;
                    }
                    break;
                case OPEN_EYES:
                    //Si el enemigo está listo y ha terminado el contador, disparará y lo reiniciamos
                    if (counterToShoot <= 0) {
                        this.shoot();
                        counterToShoot = INITIAL_COUNTER;
                    } else
                        /*Para que el contador que marca el inicio del disparo se reduzca, será necesario que alguno
                          de los ojos del enemigo este a la espera de disparar*/
                        if (eye1.isWaiting() || eye2.isWaiting() || eye3.isWaiting() || eye4.isWaiting())
                            counterToShoot -= delta * SPEED;

                    if(isAllEyesClosed())
                        state = PurpleEnemyState.OPEN_MAIN_EYE;
                    break;
                case OPEN_MAIN_EYE:
                    if(timeMainEyeOpen >= FREQUENCY_MAIN_EYE_OPEN){
                        changeClosedStatusAllEyes(false);
                        changeWaitingStatusAllEyes(false);
                        eye1.setWaiting(true);
                        state = PurpleEnemyState.OPEN_EYES;
                        timeMainEyeOpen = 0;
                    }else{
                        timeMainEyeOpen+=delta;
                    }
                    break;
            }
        }
    }

    public Array<PartOfEnemy> getPartsOfEnemy() {
        Array<PartOfEnemy> partsOfEnemy = new Array<PartOfEnemy>();
        partsOfEnemy.add(body);
        partsOfEnemy.add(eye1);
        partsOfEnemy.add(eye2);
        partsOfEnemy.add(eye3);
        partsOfEnemy.add(eye4);

        return partsOfEnemy;
    }

    public void render(){
        /*El ojo central (enemigo en sí), solo será visible y por lo tanto dañable cuando los cuatro ojos que disparan
          estén abatidos*/
        if (isAllEyesClosed() && !state.equals(PurpleEnemyState.APPEAR)) {
            super.render();
        }
    }

    public void changeToDeletable() {
        super.changeToDeletable();
        body.changeToDeletable();
        eye1.changeToDeletable();
        eye2.changeToDeletable();
        eye3.changeToDeletable();
        eye4.changeToDeletable();
    }

    public void shoot(){
        /*Comenzamos la secuencia del patrón de disparo disparando el primer ojo y después irán disparando en orden
          cada uno de ellos*/
        if (eye1.isWaiting()) {
            eye1.shoot();
            eye1.setWaiting(false);
            eye2.setWaiting(true);
        }else if (eye2.isWaiting()){
            eye2.shoot();
            eye2.setWaiting(false);
            eye3.setWaiting(true);
        }else if (eye3.isWaiting()){
            eye3.shoot();
            eye3.setWaiting(false);
            eye4.setWaiting(true);
        }else if (eye4.isWaiting()){
            eye4.shoot();
            eye4.setWaiting(false);
            eye1.setWaiting(true);
        }
    }

    public void collideWithShoot(Shoot shoot) {
        if (this.canCollide() && isAllEyesClosed()) {
            DamageManager.calculateDamage(shoot,this);
        }
    }

    private boolean isAllEyesClosed(){
        return eye1.getClosed() && eye2.getClosed() && eye3.getClosed() && eye4.getClosed();
    }

    public void dispose(){
        super.dispose();
        eye1.dispose();
        eye2.dispose();
        eye3.dispose();
        eye4.dispose();
        body.dispose();
    }

}
