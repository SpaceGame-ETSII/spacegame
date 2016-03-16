package com.tfg.spacegame.utils;


import com.badlogic.gdx.utils.ArrayMap;
import com.tfg.spacegame.gameObjects.Enemy;
import com.tfg.spacegame.gameObjects.Shoot;
import com.tfg.spacegame.utils.enums.TypeEnemy;
import com.tfg.spacegame.utils.enums.TypeShoot;

public class DamageManager {

    public static ArrayMap<Pair<TypeShoot,TypeEnemy>,Integer> typesOfShootEnemy;

    public static void load(){
        typesOfShootEnemy = new ArrayMap<Pair<TypeShoot, TypeEnemy>, Integer>();

        // Arma básica con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.TYPE1),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.TYPE2),4);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.TYPE3),6);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.TYPE4),12);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.TYPE5),19);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.YELLOW),4);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.RED),2);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.BLUE),2);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.ORANGE),20);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BASIC,TypeEnemy.PURPLE),3);

        // Arma amarilla con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.YELLOW,TypeEnemy.TYPE1),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.YELLOW,TypeEnemy.TYPE2),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.YELLOW,TypeEnemy.YELLOW),3);

        // Arma verde con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.YELLOW),3);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.TYPE1),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.TYPE2),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.TYPE3),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.TYPE4),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.TYPE5),2);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.GREEN),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.GREEN,TypeEnemy.BLUE),1);

        // Arma roja con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.RED),5);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.TYPE1),5);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.TYPE2),8);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.TYPE3),12);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.TYPE4),18);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.TYPE5),35);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.PURPLE),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.RED,TypeEnemy.ORANGE),15);

        // Arma azul con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.BLUE),30);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.TYPE1),5);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.TYPE2),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.TYPE3),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.TYPE4),20);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.TYPE5),25);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.PURPLE),5);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.BLUE,TypeEnemy.GREEN),30);

        // Arma morada con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.PURPLE),20);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.TYPE1),5);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.TYPE2),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.TYPE3),11);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.TYPE4),20);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.TYPE5),30);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.RED),2);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.PURPLE,TypeEnemy.BLUE),15);

        // Arma naranja con los enemigos a los que daña
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.ORANGE),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.TYPE1),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.TYPE2),3);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.TYPE3),6);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.TYPE4),14);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.TYPE5),10);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.YELLOW),1);
        typesOfShootEnemy.put(new Pair<TypeShoot, TypeEnemy>(TypeShoot.ORANGE,TypeEnemy.RED),1);
    }

    // Calcula el daño y realiza la accion del damage en caso de que sea necesario
    public static void calculateDamage(Shoot shoot, Enemy enemy){
        Integer damage = typesOfShootEnemy.get(new Pair<TypeShoot, TypeEnemy>(shoot.getType(),enemy.getType()));
        // Si hemos intentado calcular el daño de un shoot-enemy que no existe, no realizamos ese daño
        if(damage != null)
            enemy.damage(damage);
    }
}
