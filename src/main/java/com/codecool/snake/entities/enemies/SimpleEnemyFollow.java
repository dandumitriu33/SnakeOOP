package com.codecool.snake.entities.enemies;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;
import com.codecool.snake.entities.snakes.SnakeLaser;
import javafx.geometry.Point2D;

import java.util.Random;


public class SimpleEnemyFollow extends Enemy implements Animatable, Interactable {

    private static Point2D heading;
    private static Random rnd = new Random();
    private static double direction;

    public SimpleEnemyFollow() {
        super(10);

        Enemy.modifyEnemyCounter(1);
        setImage(Globals.getInstance().getImage("SimpleEnemyFollow"));
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



    public static void followSnake(Snake snake, SimpleEnemyFollow enemy) {
        //direction = direction + 1;
        heading = snake.getHead().getHeading();
        enemy.setNewX();


    }

    private void setNewX() {
        setX(getX() + heading.getX());
        setY(getX() + heading.getY());
    }


}
