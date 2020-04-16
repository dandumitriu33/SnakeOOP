package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.codecool.snake.entities.snakes.SnakeLaser;
import javafx.geometry.Point2D;

import java.util.Random;



public class SimpleEnemy extends Enemy implements Animatable, Interactable {
    private Point2D heading;
    private static Random rnd = new Random();


    public SimpleEnemy() {
        super(10);

        Enemy.modifyEnemyCounter(1);
        setImage(Globals.getInstance().getImage("SimpleEnemy"));
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

        double direction = rnd.nextDouble() * 360;
        setRotate(direction);

        int speed = 1;
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            Enemy.modifyEnemyCounter(-1);
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof SnakeHead){
            System.out.println(getMessage());
            destroy();

        }
        else if(entity instanceof SnakeLaser){
            System.out.println(this + " killed by laser.");
            destroy();
            Enemy.modifyEnemyCounter(-1);
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }


}
