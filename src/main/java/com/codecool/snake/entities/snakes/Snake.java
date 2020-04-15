package com.codecool.snake.entities.snakes;

import com.codecool.snake.DelayedModificationList;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.eventhandler.InputHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.util.List;


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

    private SnakeControl getUserInput() {
        SnakeControl turnDir = SnakeControl.INVALID;
        if (id == 1) {
            if (InputHandler.getInstance().isKeyPressed(KeyCode.LEFT)) turnDir = SnakeControl.TURN_LEFT;
            if (InputHandler.getInstance().isKeyPressed(KeyCode.RIGHT)) turnDir = SnakeControl.TURN_RIGHT;
        } else {
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
        if (counter == 0) {
            Globals.getInstance().stopGame();
        }
    }

    /*
    Function checks if any of the snakes is marked for deletion and if it is true it deletes its body and head.
     */
    private void checkSnakeDeathCondition() {

        if ((head.isOutOfBounds() || health <= 0) && !this.alreadyDeleted) {
            this.head.destroy();
            this.alreadyDeleted = true;
            for (int i = 0; i < body.getList().size(); i++) {
                body.getList().get(i).destroy();
            }
            counter--;
        }
    }

    private void updateSnakeBodyHistory() {
        GameEntity prev = head;
        for (GameEntity currentPart : body.getList()) {
            currentPart.setPosition(prev.getPosition());
            prev = currentPart;
        }
    }

    private GameEntity getLastPart() {
        GameEntity result = body.getLast();

        if (result != null) return result;
        return head;
    }

    public int getId() {
        return this.id;
    }

    public boolean isAlreadyDeleted() {
        return alreadyDeleted;
    }

    public List<GameEntity> getBody() {
        return body.getList();
    }


}
