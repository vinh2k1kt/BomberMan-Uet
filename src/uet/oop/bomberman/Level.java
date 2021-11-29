package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.moving.enemy.Jelly;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.entities.still.block.Layered;
import uet.oop.bomberman.entities.still.block.destroyable.Brick;
import uet.oop.bomberman.entities.still.block.item.BombItem;
import uet.oop.bomberman.entities.still.block.item.FlameItem;
import uet.oop.bomberman.entities.still.block.item.Portal;
import uet.oop.bomberman.entities.still.block.item.SpeedItem;
import uet.oop.bomberman.entities.still.block.undestroyable.Grass;
import uet.oop.bomberman.entities.still.block.undestroyable.Wall;
import uet.oop.bomberman.entities.still.bomb.Bomb;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.menu.LoadingScene;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.Constants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level extends Canvas {

    public Scene levelScene;
    public Canvas levelCanvas;
    public GraphicsContext gc;
    public ArrayList<Tile> tiles = new ArrayList<>();
    public String[][] tileMap;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public int numberOfEnemies;
    public Stage stage;

    public double currentGameTime;
    public double lastNanoTime;
    public double deltaTime;
    public double drawInterval = 1000000000 / 120.0;
    public final static long startNanoTime = System.nanoTime();

    public boolean isRunning = true;
    public boolean gameOver = false;
    public boolean goToNextLevel = false;

    public ArrayList<Player> bombers = new ArrayList<>();
    public ArrayList<Jelly> jellies = new ArrayList<>();
    public final Sound soundTrack = new Sound();

    private final List<String> mapDataFile = new ArrayList<>();

    private AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
            deltaTime += (currentNanoTime - lastNanoTime) / drawInterval;

            lastNanoTime = currentNanoTime;

            if (deltaTime >= 1) {
                if (isRunning) {
                    gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                    render();
                    update();
                } else {
                    if (gameOver) {
                        renderGameOverSccene();
                    }
                    if (goToNextLevel) {
                        try {
                            loadNextLevel();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    timer.stop();
                }
            }
            lastNanoTime = System.nanoTime();
        }
    };

    public Level(Stage primaryStage, String levelPath) throws IOException {
        soundTrack.setFile("Main");
        soundTrack.play();
        soundTrack.loop();

        this.stage = primaryStage;
        loadMap(levelPath);

        // Init Scene and Canvas
        levelCanvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = levelCanvas.getGraphicsContext2D();
        Group container = new Group();
        container.getChildren().add(levelCanvas);
        levelScene = new Scene(container);

        createMap();

        // Game loop
        timer.start();
    }

    private void loadMap(String path) throws IOException {

        // Clear to load new map
        mapDataFile.clear();

        FileInputStream inputStream = new FileInputStream(path);
        Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);

        // First Line contain number of row and column
        String[] levelInfo = sc.nextLine().trim().split(" ");
        Constants.ROWS = Integer.parseInt(levelInfo[1]);
        Constants.COLUMNS = Integer.parseInt(levelInfo[2]);
        Constants.SCREEN_WIDTH = Constants.COLUMNS * Constants.TILES_SIZE;
        Constants.SCREEN_HEIGHT = Constants.ROWS * Constants.TILES_SIZE;
        tileMap = new String[Constants.ROWS][Constants.COLUMNS];

        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            // Skip Explaination Part
            if (line.length() < 5) {
                break;
            }

            mapDataFile.add(line);
        }
    }

    private void createMap() {
        int row = 0;
        for (String line : mapDataFile) {
            for (int col = 0; col < Constants.COLUMNS; col++) {
                tileMap[row][col] = String.valueOf(line.charAt(col));
                switch (line.charAt(col)) {
                    case 's' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new SpeedItem(col, row, SpriteContainer.speedItem.getFxImage(), this), this));
                    }
                    case 'b' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new BombItem(col, row, SpriteContainer.bombItem.getFxImage(), this), this));
                    }
                    case 'f' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new FlameItem(col, row, SpriteContainer.flameItem.getFxImage(), this), this));
                    }
                    case 'x' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new Portal(col, row, SpriteContainer.portal.getFxImage(), this), this));
                    }
                    case 'p' -> {
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        bombers.add(new Player(col, row, SpriteContainer.player_right.getFxImage(), this));
                    }
                    case '#' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall.getFxImage(), this));
                    }
                    case '*' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new Grass(col, row, SpriteContainer.grass.getFxImage(), this), this));
                    }
                    case '1' -> {
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        jellies.add(new Jelly(col, row, SpriteContainer.Jelly.getFxImage(), this));
                    }
                    default -> {
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                    }
                }
            }
            row++;
        }
        numberOfEnemies = jellies.size();
    }

    private void render() {

        tiles.forEach(e -> e.render(gc));

        bombs.forEach(b -> b.render(gc));

        jellies.forEach(j -> j.render(gc));

        bombers.forEach(bomber -> bomber.render(gc));
    }

    private void update() {

        int count = 0;
        for (Tile tile : tiles) {
            if (tile instanceof Layered) {
                if (((Layered) tile).canRemove) {
                    tiles.set(count, ((Layered) tile).getBufferedEntity());
                }
            }
            tile.update();
            count++;
        }

        bombs.removeIf(Bomb::Removeable);
        if (!bombs.isEmpty()) {
            bombs.forEach(Bomb::update);
        }

        jellies.removeIf(Jelly::isDead);
        if (!jellies.isEmpty()) {
            jellies.forEach(Jelly::update);
        }

        bombers.removeIf(Player::isDead);
        if (!bombers.isEmpty()) {
            bombers.forEach(Player::update);
        } else {
            soundTrack.stop();
            gameOver();
        }
    }

    /**
     * Game Over
     */
    public void gameOver() {
        isRunning = false;
        goToNextLevel = true;

    }

    /**
     * Render Game OverScene
     */
    public void renderGameOverSccene() {
        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        try {
            URL url = new File("src/uet/oop/bomberman/menu/gameOver.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(url);
            stage.setScene(new Scene(root));
            root.getStylesheets().add("uet/oop/bomberman/menu/style.css");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load Next Level
     */
    private void loadNextLevel() throws IOException {
        Constants.TEST = "Level 2";
        new LoadingScene(stage, "res/levels/Level2.txt");
    }
}
