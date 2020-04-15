package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.enemies.SimpleEnemy;
import com.codecool.snake.entities.powerups.HealthPowerUp;
import com.codecool.snake.entities.powerups.SimplePowerUp;
import com.codecool.snake.entities.powerups.SpeedPowerUp;
import com.codecool.snake.entities.snakes.Snake;
import com.codecool.snake.eventhandler.InputHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;


public class Game extends Pane {
    private Snake snake = null;
    private Snake snakePlayer2 = null;
    private GameTimer gameTimer = new GameTimer();
    private boolean showingGameOver = false;
    public static int powerupCounter;


    public Game() {
        Globals.getInstance().game = this;
        Globals.getInstance().display = new Display(this);
        Globals.getInstance().setupResources();

        init();
    }

    public static void refreshHealth(Game game) {
        calculatePlayer1Health(game);
        calculatePlayer2Health(game);
    }

    private static void calculatePlayer1Health(Game game) {
        int healthPoints1 = game.snake.getHealth();
        if (healthPoints1 > 100) healthPoints1 = 100;
        Rectangle player1BG = new Rectangle();
        player1BG.setX(800);
        player1BG.setY(10);
        player1BG.setWidth(104);
        player1BG.setHeight(24);
        Rectangle player1HP = new Rectangle();
        player1HP.setX(802);
        player1HP.setY(12);
        player1HP.setWidth(healthPoints1);
        player1HP.setHeight(20);
        player1HP.setFill(Color.RED);
        game.getChildren().addAll(player1BG, player1HP);
    }

    public static void calculatePlayer2Health(Game game) {
        int healthPoints2 = game.snakePlayer2.getHealth();
        if (healthPoints2 > 100) healthPoints2 = 100;
        Rectangle player2BG = new Rectangle();
        player2BG.setX(30);
        player2BG.setY(10);
        player2BG.setWidth(104);
        player2BG.setHeight(24);
        Rectangle player2HP = new Rectangle();
        player2HP.setX(32);
        player2HP.setY(12);
        player2HP.setWidth(healthPoints2);
        player2HP.setHeight(20);
        player2HP.setFill(Color.RED);
        game.getChildren().addAll(player2BG, player2HP);
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
        showingGameOver =false;
    }


    public void start() {
        this.requestFocus();
        setupInputHandling();
        Globals.getInstance().startGame();

    }

    public void displayGameOver() {
        if (snake.isDead() && snakePlayer2.isDead()) {
            Stage screenGameOver = new Stage();
            screenGameOver.initModality(Modality.WINDOW_MODAL);
            Label snakeScore = new Label("Player 1 score: " + snake.getBody().size());
            snakeScore.setLayoutX(70);
            snakeScore.setLayoutY(70);
            Label snakePlayer2Score = new Label("Player 2 score: " + snakePlayer2.getBody().size());
            snakePlayer2Score.setLayoutX(70);
            snakePlayer2Score.setLayoutY(90);
            Button restart = new Button("Restart");
            restart.setLayoutX(70);
            restart.setLayoutY(120);
            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    ((Stage)screenGameOver.getScene().getWindow()).close();
                    restartGame();
                }
            });
            Button exitGame = new Button("Exit Game");
            exitGame.setLayoutX(70);
            exitGame.setLayoutY(150);
            exitGame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Platform.exit();
                }
            });
            Pane layout = new Pane();
            layout.getChildren().addAll(snakeScore, snakePlayer2Score, restart, exitGame);
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
        snake = new Snake(new Point2D(500, 500), Snake.ControlSet.PLAYER_1, "Player 1");
        snakePlayer2 = new Snake(new Point2D(200, 200), Snake.ControlSet.PLAYER_2, "Player 2");
    }

    private void spawnEnemies(int numberOfEnemies) {
        for(int i = 0; i < numberOfEnemies; ++i) new SimpleEnemy();
    }

    private void spawnPowerUps(int numberOfPowerUps) {
        for(int i = 0; i < numberOfPowerUps; ++i) {
            new SimplePowerUp();
            new HealthPowerUp();
            new SpeedPowerUp();
            powerupCounter += 3;
        }
    }

    private void setupInputHandling() {
        Scene scene = getScene();
        scene.setOnKeyPressed(event -> InputHandler.getInstance().setKeyPressed(event.getCode()));
        scene.setOnKeyReleased(event -> InputHandler.getInstance().setKeyReleased(event.getCode()));
    }

}
