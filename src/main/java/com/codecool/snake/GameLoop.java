package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.enemies.Enemy;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.enemies.SimpleEnemyCircle;
import com.codecool.snake.entities.enemies.SimpleEnemyFollow;
import com.codecool.snake.entities.powerups.HealthPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.SpeedPowerUp;
import com.codecool.snake.entities.snakes.Snake;
import javafx.geometry.Point2D;

import java.util.List;

public class GameLoop {
    private Snake snake;
    private Snake snakePlayer2;
    private boolean running = false;
    private Game myGame;  /////////////////////////////////////////////////////

    public GameLoop(Game game, Snake snake, Snake snakePlayer2) {
        this.snake = snake;
        this.snakePlayer2 = snakePlayer2;
        this.myGame = game; ///////////////////////////////////////////////////
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {
        if (Snake.getGameOver()) {
            this.myGame.displayGameOver();  ////////////////////////////////////////////////////////////
        }

        if (running) {
            if(Game.powerupCounter <=0 ){
                new HealthPowerUp();
                new SimplePowerUp();
                new SpeedPowerUp();
                Game.powerupCounter += 3;
            }
            if (Enemy.getEnemyCounter() <= 0) {
                new SimpleEnemy();
                new SimpleEnemyCircle();
                new SimpleEnemyFollow();

            }



            if (Enemy.getEnemyCounter() > 0) {
                //System.out.println("Enemy Circle movement");
                SimpleEnemyCircle.rotateSimpleEnemyCircle();
                for (GameEntity gameObject : Globals.getInstance().display.getObjectList()) {
                    if (gameObject instanceof SimpleEnemyFollow) {
                        if (snake != null && snakePlayer2 != null) {
                            Point2D pointSnake1 = snake.getHead().getPosition();
                            Point2D pointSnake2 = snakePlayer2.getHead().getPosition();
                            Point2D gameObjectCoords = gameObject.getPosition();
                            double distanceToHead1 = calculateDistance(pointSnake1.getX(), pointSnake1.getY(), gameObjectCoords.getX(), gameObjectCoords.getY());
                            double distanceToHead2 = calculateDistance(pointSnake2.getX(), pointSnake2.getY(), gameObjectCoords.getX(), gameObjectCoords.getY());
                            if (distanceToHead1 <= distanceToHead2) {
                                SimpleEnemyFollow.followSnake(snake, ((SimpleEnemyFollow) gameObject));
                            } else {

                                SimpleEnemyFollow.followSnake(snakePlayer2, ((SimpleEnemyFollow) gameObject));
                            }

                        }
                        else if(snakePlayer2 == null) {
                            SimpleEnemyFollow.followSnake(snake, ((SimpleEnemyFollow) gameObject));
                        }
                        else if(snake == null) {
                            SimpleEnemyFollow.followSnake(snakePlayer2, ((SimpleEnemyFollow) gameObject));
                        }
                    }
                }
            }
            if (snake != null) {
                snake.step();
                if (snake.isAlreadyDeleted()) {
                    snake = null;
                }
            }
            if (snakePlayer2 != null) {
                snakePlayer2.step();
                if (snakePlayer2.isAlreadyDeleted()) {
                    snakePlayer2 = null;
                }
            }
            for (GameEntity gameObject : Globals.getInstance().display.getObjectList()) {
                if (gameObject instanceof Animatable) {
                    ((Animatable) gameObject).step();
                }
            }
            checkCollisions();
        }
        Globals.getInstance().display.frameFinished();
    }

    private void checkCollisions() {
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (int idxToCheck = 0; idxToCheck < gameObjs.size(); ++idxToCheck) {
            GameEntity objToCheck = gameObjs.get(idxToCheck);
            if (objToCheck instanceof Interactable) {
                for (int otherObjIdx = idxToCheck + 1; otherObjIdx < gameObjs.size(); ++otherObjIdx) {
                    GameEntity otherObj = gameObjs.get(otherObjIdx);
                    if (otherObj instanceof Interactable) {
                        if (objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
                            ((Interactable) objToCheck).apply(otherObj);
                            ((Interactable) otherObj).apply(objToCheck);
                        }
                    }
                }
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    private double calculateDistance(double xA, double yA, double xB, double yB) {
        return Math.sqrt((xA - xB) * (xA - xB) + (yA - yB) * (yA - yB));
    }
}