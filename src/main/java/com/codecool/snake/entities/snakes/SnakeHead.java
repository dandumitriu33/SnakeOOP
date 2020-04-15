package com.codecool.snake.entities.snakes;

import com.codecool.snake.Globals;
import com.codecool.snake.Utils;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.powerups.HealthPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.SpeedPowerUp;
import javafx.geometry.Point2D;


public class SnakeHead extends GameEntity implements Interactable {
    private static final float turnRate = 2;
    private Snake snake;
    private boolean decapitate = false;
    private Point2D heading;

    public SnakeHead(Snake snake, Point2D position) {
        this.snake = snake;
        setImage(Globals.getInstance().getImage("SnakeHead"));
        setPosition(position);
    }

    public void updateRotation(SnakeControl turnDirection, float speed) {
        double headRotation = getRotate();

        if (turnDirection.equals(SnakeControl.TURN_LEFT)) {
            headRotation = headRotation - turnRate;
        }
        if (turnDirection.equals(SnakeControl.TURN_RIGHT)) {
            headRotation = headRotation + turnRate;
        }
        if (turnDirection.equals(SnakeControl.SHOOT)) {
            new SnakeLaser(snake);
        }


        // set rotation and position
        setRotate(headRotation);
        this.heading = Utils.directionToVector(headRotation, speed);
        setX(getX() + heading.getX());
        setY(getY() + heading.getY());
    }

    @Override
    public void apply(GameEntity entity) {

        if(entity instanceof Enemy){
            System.out.println(getMessage());
            snake.changeHealth(-((Enemy) entity).getDamage());
            Enemy.modifyEnemyCounter(-1);
        }
        else if(entity instanceof SimplePowerUp){
            System.out.println(getMessage());
            //Game.powerupCounter -= 1;
            snake.addPart(1); // change from 4
        }

        else if(entity instanceof HealthPowerUp) {
            snake.changeHealth(20);
            //Game.powerupCounter -= 1;
            System.out.println("snake " + snake.getId()+" " + snake.getHealth());
        }
        else if(entity instanceof SpeedPowerUp) {
            snake.setSpeed(0f); // change from 1f
            //Game.powerupCounter -= 1;
            System.out.println("snake " + snake.getId()+" " + snake.getSpeed());
        }
        else if (entity instanceof SnakeHead) {
            System.out.println("touched head");
            this.decapitate = true;
        }
        else if (entity instanceof SnakeBody ){
            if(((SnakeBody)entity).getSnakeId() != this.snake.getId()){
                System.out.println("touched snake body " + ((SnakeBody)entity).getSnakeId());
                this.decapitate = true;
            }
        }

    }

    @Override
    public String getMessage() {
        return "IMMA SNAEK HED! SPITTIN' MAH WENOM! SPITJU-SPITJU!";
    }

    public int getSnakeId() {
        return this.snake.getId();
    }

    public void setDecapitate(boolean decapitate) {
        this.decapitate = decapitate;
    }

    public boolean isDecapitate() {
        return decapitate;
    }

    public Point2D getHeading() {
        return heading;
    }

}
