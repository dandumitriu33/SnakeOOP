package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;

public abstract class Enemy extends GameEntity{
    private final int damage;
    private static int enemyCounter;
    public Enemy(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public static int getEnemyCounter() {
        return enemyCounter;
    }

    public static void setEnemyCounter(int enemyCounter) {
        Enemy.enemyCounter = enemyCounter;
    }

    public static void modifyEnemyCounter(int diff) {
        Enemy.enemyCounter += diff;
    }
}
