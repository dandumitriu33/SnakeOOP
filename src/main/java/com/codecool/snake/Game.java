package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;


public class Game extends Pane {
    private Snake snake = null;
    private Snake snakePlayer2 = null;
    private GameTimer gameTimer = new GameTimer();
    private boolean showingGameOver = false;


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public void init() {
        spawnSnake();
        spawnEnemies(4);
        spawnPowerUps(4);
        spawnRestartButton();

        GameLoop gameLoop = new GameLoop(this, snake, snakePlayer2);
        Globals.getInstance().setGameLoop(gameLoop);
        gameTimer.setup(gameLoop::step);
        gameTimer.play();
    }

    private void spawnRestartButton() {
        Button restart = new Button("Restart");
        restart.setLayoutX(920);
        restart.setLayoutY(10);
        this.getChildren().add(restart);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restartGame();
            }
        });
    }

    private void restartGame() {
        System.out.println("pressed restart");
        snake = null;
        snakePlayer2 = null;
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (GameEntity item : gameObjs) {
            item.destroy();
        }
        init();
        start();
        Snake.setGameOver(false);
        showingGameOver =false;
    }


    public void start() {
        this.requestFocus();
        setupInputHandling();
        Globals.getInstance().startGame();

    }

    public void displayGameOver() {
        if (Snake.getGameOver()) {

            Stage screenGameOver = new Stage();

            Button restart = new Button("Restart");
            restart.setLayoutX(10);
            restart.setLayoutY(10);
            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ((Stage)screenGameOver.getScene().getWindow()).close();
                    restartGame();
                }
            });

            Button exitGame = new Button("Exit Game");
            restart.setLayoutX(100);
            restart.setLayoutY(100);
            exitGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });


            Pane layout = new Pane();
            layout.getChildren().addAll(restart, exitGame);

            Scene sceneGameOver = new Scene(layout, 300, 300);
            // make boolean - if is visible don't show again
            screenGameOver.setScene(sceneGameOver);
            if (showingGameOver) {

            }
            else {
                screenGameOver.show();
                showingGameOver = true;
            }
        }
    }

    private void spawnSnake() {
        snake = new Snake(new Point2D(500, 500));
        snakePlayer2 = new Snake(new Point2D(200, 200));
    }

    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) new SimplePowerUp();
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }


}
