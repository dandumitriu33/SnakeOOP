package com.codecool.snake.entities.enemies;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;
import java.util.Random;

import javafx.geometry.Point2D;



public class SimpleEnemyCircle extends Enemy implements Animatable, Interactable {

    private static Point2D heading;
    private static Random rnd = new Random();
    private static double direction;

    public SimpleEnemyCircle() {
        super(10);

        Enemy.modifyEnemyCounter(1);
        setImage(Globals.getInstance().getImage("SimpleEnemyCircle"));
        setX(rnd.nextDouble() * Globals.WINDOW_WIDTH);
        setY(rnd.nextDouble() * Globals.WINDOW_HEIGHT);

        direction = rnd.nextDouble() * 360;
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

        //heading = Utils.directionToVector(direction, 1);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
        getMessage();
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof SnakeHead){
            System.out.println(getMessage());
            destroy();
            Enemy.modifyEnemyCounter(-1);
        }
    }

    @Override
    public String getMessage() {
        return (getDamage() + " damage");
    }



    public static void rotateSimpleEnemyCircle() {
        direction = direction + 1;
        heading = Utils.directionToVector(direction, 1);

    }


}
