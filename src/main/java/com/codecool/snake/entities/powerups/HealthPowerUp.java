package com.codecool.snake.entities.powerups;

import com.codecool.snake.Game;
import com.codecool.snake.Globals;
import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.SnakeHead;

import java.util.Random;

public class HealthPowerUp extends GameEntity implements Interactable {

    private static Random rnd = new Random();
    public HealthPowerUp() {

        setImage(Globals.getInstance().getImage("HealthBerry"));
        setX(rnd.nextDouble() * (Globals.WINDOW_WIDTH-20));
        setY(rnd.nextDouble() * (Globals.WINDOW_HEIGHT-20));
    }

    @Override
    public void apply(GameEntity entity) {
        if(entity instanceof SnakeHead){
            System.out.println(getMessage());
            Game.powerupCounter -= 1;
            destroy();
        }
    }

    @Override
    public String getMessage() {
        return "Got Health Berry";
    }
}
