package com.codecool.snake.entities.snakes;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import javafx.geometry.Point2D;

import java.util.LinkedList;
import java.util.Queue;



public class SnakeBody extends GameEntity implements Interactable {
    private Queue<Point2D> history = new LinkedList<>();
    private int snakeId = 0;
    private static int counter = 0;
    private static final int historySize = 10;

    public SnakeBody(Point2D coord, int snakeId) {
        this.snakeId = snakeId;
        setImage(Globals.getInstance().getImage("SnakeBody"));
        setX(coord.getX());
        setY(coord.getY());

        for (int i = 0; i < historySize; i++) {
            history.add(coord);
        }
    }

    @Override
    public void setPosition(Point2D pos) {
        Point2D currentPos = history.poll(); // remove the oldest item from the history
        setX(currentPos.getX());
        setY(currentPos.getY());
        history.add(pos); // add the parent's current position to the beginning of the history
    }

    public Queue<Point2D> getHistory() {
        return history;
    }

    @Override
    public void apply(GameEntity entity) {

    }

    @Override
    public String getMessage() {
        return null;
    }

    public static void setCounter(int counter) {
        SnakeBody.counter = counter;
    }

    public static int getCounter() {
        return counter;
    }

    public int getSnakeId() {
        return snakeId;
    }
}