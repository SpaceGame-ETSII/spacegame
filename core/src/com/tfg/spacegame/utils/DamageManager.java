package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.ArrayMap;
import com.tfg.spacegame.gameObjects.campaignMode.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.gameObjects.campaignMode.enemies.*;
import com.tfg.spacegame.gameObjects.campaignMode.shoots.*;

public class DamageManager {

    public static ArrayMap<Pair<Class,Class>,Integer> typesOfShootEnemy;

    public static void load(){
        typesOfShootEnemy = new ArrayMap<Pair<Class, Class>, Integer>();

        // Arma básica con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Type1.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Type2.class),4);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Type3.class),6);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Type4.class),12);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Type5.class),19);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Yellow.class),4);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Red.class),2);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Blue.class),3);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Orange.class),20);
        typesOfShootEnemy.put(new Pair<Class, Class>(Basic.class,Purple.class),3);

        // Arma amarilla con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Yellow.class,Type1.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Yellow.class,Type2.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Yellow.class,Yellow.class),3);

        // Arma verde con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Yellow.class),3);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Type1.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Type2.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Type3.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Type4.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Type5.class),2);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Green.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Green.class,Blue.class),1);

        // Arma verde (su fuego) con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Yellow.class),3);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Type1.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Type2.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Type3.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Type4.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Type5.class),2);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Green.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(GreenFire.class,Blue.class),1);

        // Arma roja con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Red.class),5);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Type1.class),5);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Type2.class),8);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Type3.class),12);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Type4.class),18);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Type5.class),35);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Purple.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Red.class,Orange.class),15);

        // Arma azul con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Blue.class),30);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Type1.class),5);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Type2.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Type3.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Type4.class),20);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Type5.class),25);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Purple.class),5);
        typesOfShootEnemy.put(new Pair<Class, Class>(Blue.class,Green.class),30);

        // Arma morada con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Purple.class),20);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Type1.class),5);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Type2.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Type3.class),11);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Type4.class),20);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Type5.class),30);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Red.class),2);
        typesOfShootEnemy.put(new Pair<Class, Class>(Purple.class,Blue.class),15);

        // Arma naranja con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Orange.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Type1.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Type2.class),3);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Type3.class),6);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Type4.class),14);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Type5.class),10);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Yellow.class),1);
        typesOfShootEnemy.put(new Pair<Class, Class>(Orange.class,Red.class),1);
    }

    // Calcula el daño y realiza la accion del damage en caso de que sea necesario
    public static void calculateDamage(Shoot shoot, Enemy enemy){
        Integer damage = typesOfShootEnemy.get(new Pair<Class, Class>(shoot.getClass(),enemy.getClass()));
        // Si hemos intentado calcular el daño de un shoot-enemy que no existe, no realizamos ese daño
        if(damage != null)
            enemy.damage(damage);
    }
}
