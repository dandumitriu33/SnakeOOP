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

    private static Snake snake1;
    private static Snake snake2;


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

        Point2D nextStep;
        if (snake1!=null && snake2!=null) {
            Point2D pointSnake1 = snake1.getHead().getPosition();
            Point2D pointSnake2 = snake2.getHead().getPosition();
            Point2D gameObjectCoords = this.getPosition();
            double distanceToHead1 = Utils.calculateDistance(pointSnake1.getX(), pointSnake1.getY(), gameObjectCoords.getX(), gameObjectCoords.getY());
            double distanceToHead2 = Utils.calculateDistance(pointSnake2.getX(), pointSnake2.getY(), gameObjectCoords.getX(), gameObjectCoords.getY());
            if (distanceToHead1 <= distanceToHead2) {
                if (pointSnake1.getX() >= this.getX() && pointSnake1.getY() >= this.getY()) {
                    if (this.getX() == pointSnake1.getX()) setX(getX());
                    else setX(getX()+1);
                    if (this.getY() == pointSnake1.getY()) setY(getY());
                    else setY(getY() + 1);
                }
                else if (pointSnake1.getX() < this.getX() && pointSnake1.getY() < this.getY()) {
                    setX(getX() - 1);
                    setY(getY() - 1);
                }
                else if (pointSnake1.getX() >= this.getX() && pointSnake1.getY() < this.getY()) {
                    if (pointSnake1.getX() == this.getX()) setX(getX());
                    else setX(getX() + 1);
                    setY(getY() - 1);
                }
                else if (pointSnake1.getX() < this.getX() && pointSnake1.getY() >= this.getY()) {
                    setX(getX() - 1);
                    if (pointSnake1.getY() == getY()) setY(getY());
                    else setY(getY() + 1);
                }
            }
            else {
                if (pointSnake2.getX() >= this.getX() && pointSnake2.getY() >= this.getY()) {
                    if (this.getX() == pointSnake2.getX()) setX(getX());
                    else setX(getX()+1);
                    if (this.getY() == pointSnake2.getY()) setY(getY());
                    else setY(getY() + 1);
                }
                else if (pointSnake2.getX() < this.getX() && pointSnake2.getY() < this.getY()) {
                    setX(getX() - 1);
                    setY(getY() - 1);
                }
                else if (pointSnake2.getX() >= this.getX() && pointSnake2.getY() < this.getY()) {
                    if (pointSnake2.getX() == this.getX()) setX(getX());
                    else setX(getX() + 1);
                    setY(getY() - 1);
                }
                else if (pointSnake2.getX() < this.getX() && pointSnake2.getY() >= this.getY()) {
                    setX(getX() - 1);
                    if (pointSnake2.getY() == getY()) setY(getY());
                    else setY(getY() + 1);
                }
            }
        }
        else if (snake1!=null) {
            heading = snake1.getHead().getHeading();
            Point2D pointSnake1 = snake1.getHead().getPosition();

            if (pointSnake1.getX() >= this.getX() && pointSnake1.getY() >= this.getY()) {
                if (this.getX() == pointSnake1.getX()) setX(getX());
                else setX(getX()+1);
                if (this.getY() == pointSnake1.getY()) setY(getY());
                else setY(getY() + 1);
            }
            else if (pointSnake1.getX() < this.getX() && pointSnake1.getY() < this.getY()) {
                setX(getX() - 1);
                setY(getY() - 1);
            }
            else if (pointSnake1.getX() >= this.getX() && pointSnake1.getY() < this.getY()) {
                if (pointSnake1.getX() == this.getX()) setX(getX());
                else setX(getX() + 1);
                setY(getY() - 1);
            }
            else if (pointSnake1.getX() < this.getX() && pointSnake1.getY() >= this.getY()) {
                setX(getX() - 1);
                if (pointSnake1.getY() == getY()) setY(getY());
                else setY(getY() + 1);
            }


        }
        else if (snake2!=null) {
            Point2D pointSnake2 = snake2.getHead().getPosition();

            if (pointSnake2.getX() >= this.getX() && pointSnake2.getY() >= this.getY()) {
                if (this.getX() == pointSnake2.getX()) setX(getX());
                else setX(getX()+1);
                if (this.getY() == pointSnake2.getY()) setY(getY());
                else setY(getY() + 1);
            }
            else if (pointSnake2.getX() < this.getX() && pointSnake2.getY() < this.getY()) {
                setX(getX() - 1);
                setY(getY() - 1);
            }
            else if (pointSnake2.getX() >= this.getX() && pointSnake2.getY() < this.getY()) {
                if (pointSnake2.getX() == this.getX()) setX(getX());
                else setX(getX() + 1);
                setY(getY() - 1);
            }
            else if (pointSnake2.getX() < this.getX() && pointSnake2.getY() >= this.getY()) {
                setX(getX() - 1);
                if (pointSnake2.getY() == getY()) setY(getY());
                else setY(getY() + 1);
            }
        }

        getMessage();
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

//    public static void followSnake(Snake snake, SimpleEnemyFollow enemy) {
//        heading = snake.getHead().getHeading();
//        enemy.setNewX();
//    }

    private void setNewX() {
        setX(getX() + heading.getX());
        setY(getX() + heading.getY());
    }

    public static void setSnake1(Snake tempSnake) {
        snake1 = tempSnake;
    }

    public static void setSnake2(Snake tempSnake) {
        snake2 = tempSnake;
    }

}
