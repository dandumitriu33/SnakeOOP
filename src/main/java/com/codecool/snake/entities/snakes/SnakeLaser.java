package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import javafx.geometry.Point2D;

public class SnakeLaser extends GameEntity implements Animatable, Interactable {
    private Snake snake;
    private Point2D heading;


    public SnakeLaser(Snake snake) {
        super();

        this.snake = snake;
        setImage(Globals.getInstance().getImage("SnakeLaser"));
        setPosition(snake.getHead().getPosition());
        int speed = 6;
        double direction = snake.getHead().getRotate();
        heading = Utils.directionToVector(direction, speed);
    }

    @Override
    public void step() {
        if (isOutOfBounds()) {
            destroy();
        }
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }



    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof Enemy){
            System.out.println(getMessage());
//            Enemy.modifyEnemyCounter(-1);    // already decreased in Simple, Circle, Follow
            destroy();
        }
    }

    @Override
    public String getMessage() {
        return "woosh";
    }

}
