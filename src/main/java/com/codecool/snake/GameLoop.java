package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;

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
        if(running) {
            if(snake != null) {
                snake.step();
                if(snake.isAlreadyDeleted()) {
                    snake = null;
                }
            }
            if(snakePlayer2 != null) {
                snakePlayer2.step();
                if(snakePlayer2.isAlreadyDeleted()) {
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
                    if (otherObj instanceof Interactable){
                        if(objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())){
                            ((Interactable) objToCheck).apply(otherObj);
                            ((Interactable) otherObj).apply(objToCheck);
                        }
                    }
                }
            }
        }
    }
}
