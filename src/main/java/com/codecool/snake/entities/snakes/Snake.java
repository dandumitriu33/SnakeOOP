package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;


public class Snake implements Animatable {
    private static int counter = 0;
    private int id;
    private static final float speed = 2;
    private int health = 100;
    private boolean alreadyDeleted = false;

    private SnakeHead head;
    private DelayedModificationList<GameEntity> body;


    public Snake(Point2D position) {
        id = ++counter;
        head = new SnakeHead(this, position);
        body = new DelayedModificationList<>();

        addPart(4);
    }

    public void step() {
        SnakeControl turnDir = getUserInput();
        head.updateRotation(turnDir, speed);

        updateSnakeBodyHistory();
        checkSnakeDeathCondition();
        checkForGameOverConditions();

        body.doPendingModifications();
    }

    private SnakeControl getUserInput() {   // TBD
        SnakeControl turnDir = SnakeControl.INVALID;
        if (id==1) {
            if (InputHandler.getInstance().isKeyPressed(KeyCode.LEFT)) turnDir = SnakeControl.TURN_LEFT;
            if (InputHandler.getInstance().isKeyPressed(KeyCode.RIGHT)) turnDir = SnakeControl.TURN_RIGHT;
        }
        else {
            if (InputHandler.getInstance().isKeyPressed(KeyCode.A)) turnDir = SnakeControl.TURN_LEFT;
            if (InputHandler.getInstance().isKeyPressed(KeyCode.D)) turnDir = SnakeControl.TURN_RIGHT;
        }
        return turnDir;
    }

    public void addPart(int numParts) {
        GameEntity parent = getLastPart();
        Point2D position = parent.getPosition();

        for (int i = 0; i < numParts; i++) {
            SnakeBody newBodyPart = new SnakeBody(position);
            body.add(newBodyPart);
        }
        Globals.getInstance().display.updateSnakeHeadDrawPosition(head);
    }

    public void changeHealth(int diff) {
        health += diff;
    }

    private void checkForGameOverConditions() {
        if (counter==0) {
            System.out.println("counter 0 game over");
            Globals.getInstance().stopGame();
        }
//        if (counter == 1) {
//            if (head.isOutOfBounds() || health <= 0) {
//                System.out.println("Game Over");
//                Globals.getInstance().stopGame();
//            }
//        }
    }

    private void checkSnakeDeathCondition() {
        System.out.println("checking " + this.id + " head on wall");
        if ((head.isOutOfBounds() || health <= 0) && !this.alreadyDeleted) {
            System.out.println("Snake died." + " counter is " + counter);
            this.head.destroy();
            this.alreadyDeleted = true;
            for(int i=0; i<body.getList().size(); i++) {
                body.getList().get(i).destroy();
            }
            Game.snakeDelete(this); // TODO delete snake object, not just head and body
            counter--;
        }
        System.out.println(this.id + " head not touching wall");
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for(GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if(result != null) return result;
        return head;
    }

    public int getId() {
        return this.id;
    }
}