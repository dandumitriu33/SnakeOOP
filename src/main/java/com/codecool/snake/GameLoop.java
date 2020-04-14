package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.List;

public class GameLoop {
    private Snake snake;
    private Snake snakePlayer2;
    private boolean running = false;

    public GameLoop(Snake snake, Snake snakePlayer2) {
        this.snake = snake;
        this.snakePlayer2 = snakePlayer2;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {
        if (running) {
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
            if(objToCheck instanceof SnakeHead){
                System.out.println("--------------------------------------------------------------");
                System.out.println(((SnakeHead) objToCheck).getSnakeId());
                if(((SnakeHead) objToCheck).getSnakeId() == 1){
                    for(int i =0; i< snakePlayer2.getBody().size(); i++) {
                        GameEntity otherObj = snakePlayer2.getBody().get(i);
                        if (otherObj instanceof Interactable) {
                            if (objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
                                System.out.println("First apply head1 vs body2");
                                ((Interactable) objToCheck).apply(otherObj);
//                                ((Interactable) otherObj).apply(objToCheck);
                            }
                        }
                    }
                } else if(((SnakeHead) objToCheck).getSnakeId() == 2){
                    for(int i =0; i< snake.getBody().size(); i++) {
                        GameEntity otherObj = snake.getBody().get(i);
                        if (otherObj instanceof Interactable) {
                            if (objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
                                System.out.println("First apply head2 vs body1");
                                ((Interactable) objToCheck).apply(otherObj);
//                                ((Interactable) otherObj).apply(objToCheck);
                            }
                        }
                    }
                }
            }
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
//                for(int i =0; i< snakePlayer2.getBody().size(); i++) {
//                    GameEntity otherObj = snakePlayer2.getBody().get(i);
//                    if (otherObj instanceof Interactable) {
//                        if (objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())) {
//                            ((Interactable) objToCheck).apply(otherObj);
//                            ((Interactable) otherObj).apply(objToCheck);
//                        }
//                    }
//                }
            }
        }
    }
}
