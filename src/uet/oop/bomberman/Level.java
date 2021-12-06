package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.SpriteContainer;
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

    boolean first = true;
    public Scene levelScene;
    public Canvas levelCanvas;
    public GraphicsContext gc;
    public ScreenController screenController;
    public Stage stage;

    private final List<String> mapDataFile = new ArrayList<>();
    public ArrayList<Tile> tiles = new ArrayList<>();
    public String[][] tileMap;
    public ArrayList<Bomb> bombs = new ArrayList<>();
    public ArrayList<Player> bombers = new ArrayList<>();
    public ArrayList<Jelly> jellies = new ArrayList<>();
    public int numberOfEnemies;

    public boolean isRunning = true;
    public boolean gameOver = false;
    public boolean goToNextLevel = false;
    public boolean isPause = false;

    public double currentGameTime;
    public double lastNanoTime;
    public double deltaTime;
    public double drawInterval = 1000000000 / 120.0;
    public final static long startNanoTime = System.nanoTime();
    public final Sound soundTrack = new Sound();

    private Group container = new Group();

    public final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
            deltaTime += (currentNanoTime - lastNanoTime) / drawInterval;

            lastNanoTime = currentNanoTime;

            if (deltaTime >= 1) {
                if (isRunning) {
                    gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                    update();
                    render();

//                    gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//                    Animation.testAni(gc, Animation.skellyLeftAni, 0);
//                    Animation.testAni(gc, Animation.skellyLeftAni, 0);

//                    SpriteContainer.testSprite(gc, SpriteContainer.grass1, 0);
//                    SpriteContainer.testSprite(gc, SpriteContainer.grass2, 1);
                } else {
                    if (gameOver) {
                        renderGameOverSccene();
                    }
                    if (goToNextLevel) {
                        try {
                            screenController.renderLoadingScene();
                            goToNextLevel = false;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            lastNanoTime = System.nanoTime();
        }
    };

    public Level(Stage primaryStage, String levelPath, ScreenController sc) throws IOException {

        screenController = sc;

        this.stage = primaryStage;
        loadMap(levelPath);

        // Init Scene and Canvas
        levelCanvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = levelCanvas.getGraphicsContext2D();
        container.getChildren().add(levelCanvas);
        levelScene = new Scene(container);

        createMap();

        // Play Theme Song
//        soundTrack.setFile("Main");
//        soundTrack.play();
//        soundTrack.loop();

        sc.setCurrentScene(this.levelScene);

        // Game loop
        timer.start();
    }

    public void loadMap(String path) throws IOException {
        //Clear Previous Level Data
        jellies.clear();
        bombers.clear();
        tiles.clear();

        //Reset Boolen Variables
        gameOver = false;
        goToNextLevel = false;
        isRunning = true;

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

    public void createMap() {
        int row = 0;
        for (String line : mapDataFile) {
            for (int col = 0; col < Constants.COLUMNS; col++) {
                switch (line.charAt(col)) {
                    case 's' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new SpeedItem(col, row, SpriteContainer.speedItem.getFxImage(), this), this));
                        tileMap[row][col] = "b";
                    }
                    case 'b' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new BombItem(col, row, SpriteContainer.bombItem.getFxImage(), this), this));
                        tileMap[row][col] = "b";
                    }
                    case 'f' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new FlameItem(col, row, SpriteContainer.flameItem.getFxImage(), this), this));
                        tileMap[row][col] = "b";
                    }
                    case 'x' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new Portal(col, row, SpriteContainer.portal.getFxImage(), this), this));
                        tileMap[row][col] = "b";
                    }
                    case 'p' -> {
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        bombers.add(new Player(col, row, SpriteContainer.player_right.getFxImage(), this));
                        tileMap[row][col] = " ";
                    }
                    case '1' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall_top_left_corner.getFxImage(), this));
                        tileMap[row][col] = "b";
                    }
                    case '2' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall_top_right_corner.getFxImage(), this));
                        tileMap[row][col] = "b";
                    }
                    case '3' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall_bottom_right_corner.getFxImage(), this));
                        tileMap[row][col] = "b";
                    }
                    case '4' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall_bottom_left_corner.getFxImage(), this));
                        tileMap[row][col] = "b";
                    }
                    case '|' -> {
                        tileMap[row][col] = "b";
                        if (col == 0) {
                            tiles.add(new Wall(col, row, SpriteContainer.wall_left_side.getFxImage(), this));
                        } else {
                            tiles.add(new Wall(col, row, SpriteContainer.wall_right_side.getFxImage(), this));
                        }
                    }
                    case '-' -> {
                        tileMap[row][col] = "b";
                        if (row == 0) {
                            tiles.add(new Wall(col, row, SpriteContainer.wall_top_middle.getFxImage(), this));
                        } else {
                            tiles.add(new Wall(col, row, SpriteContainer.wall_bottom_middle.getFxImage(), this));
                        }
                    }
                    case 'w' -> {
                        tileMap[row][col] = "b";
//                        System.out.println("create map " + row + " " + col);
                        tiles.add(new Wall(col, row, SpriteContainer.wall.getFxImage(), this));
                    }
                    case 'j' -> {
                        tileMap[row][col] = " ";
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        jellies.add(new Jelly(col, row, Animation.skellyRightAni.get(0).getFxImage(), this));
                    }
                    case '*' -> {
                        tileMap[row][col] = "b";
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage(),
                        new Grass(col, row, SpriteContainer.grass.getFxImage(), this), this));
                    }
                    default -> {
                        tileMap[row][col] = " ";
                        int random = (int) (Math.random() * 3 + 1);
                        Image grassImage = SpriteContainer.grass.getFxImage();
                        switch (random) {
                            case 1 -> grassImage = SpriteContainer.grass.getFxImage();
                            case 2 -> grassImage = SpriteContainer.grass1.getFxImage();
                            case 3 -> grassImage = SpriteContainer.grass2.getFxImage();
                        }
                        tiles.add(new Grass(col, row, grassImage, this));
                    }
                }
            }
            row++;
        }
        numberOfEnemies = jellies.size();
    }

    public void printTileMap() {
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLUMNS; col++) {
                System.out.print(tileMap[row][col]);
            }
                System.out.println();
            }
        }

    private void render() {

        tiles.forEach(e -> e.render(gc));

        bombs.forEach(b -> b.render(gc));

        jellies.forEach(j -> j.render(gc));

        bombers.forEach(bomber -> bomber.render(gc));

            jellies.get(0).showPath(jellies.get(0).getNode(), bombers.get(0).getNode());
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
     * Pause Game
     */
    public void pause() {
        if (isPause) {
            isPause = false;
            timer.start();
            for (Node node : screenController.nodes) {
                container.getChildren().remove(node);
            }
        } else {
            isPause = true;
            timer.stop();
            for (Node node : screenController.nodes) {
                container.getChildren().add(node);
            }
        }
    }

    /**
     * Game Over
     */
    public void gameOver() {
        isRunning = false;
        gameOver = true;
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

    public void complete() {
        isRunning = false;
        goToNextLevel = true;
    }












    public Level(String levelPath) throws IOException {


        loadMap(levelPath);

        // Init Scene and Canvas
//        levelCanvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//        gc = levelCanvas.getGraphicsContext2D();
//        container.getChildren().add(levelCanvas);
//        levelScene = new Scene(container);

        createMap();

        // Play Theme Song
//        soundTrack.setFile("Main");
//        soundTrack.play();
//        soundTrack.loop();

    }


}
