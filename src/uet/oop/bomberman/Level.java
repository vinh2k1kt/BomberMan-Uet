package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.moving.enemy.Bat;
import uet.oop.bomberman.entities.moving.enemy.Frog;
import uet.oop.bomberman.entities.moving.enemy.Ghost;
import uet.oop.bomberman.entities.moving.enemy.Skelly;
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
import uet.oop.bomberman.menu.GameOver;
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

public class Level {

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
    public ArrayList<Skelly> skellies = new ArrayList<>();
    public ArrayList<Ghost> ghosts = new ArrayList<>();
    public ArrayList<Bat> bats = new ArrayList<>();
    public ArrayList<Frog> frogs = new ArrayList<>();
    public int numberOfEnemies;

    public boolean isRunning = true;
    public boolean gameOver = false;
    public boolean goToNextLevel = false;
    public boolean isPause = false;
    public boolean finalLevel = false;
    public boolean resetRequired = false;
    public boolean isMute = false;
    public boolean showPath = false;
    public int points = 0;
    public int previousPoints = 0;

    public double lastNanoTime;
    public double deltaTime;
    public double FPS = 60;
    public double drawInterval = 1000000000 / FPS;
    public final Sound soundTrack = new Sound();

    private final Group container = new Group();
    private int time = 202;
    private int renderedFrame = 0;

    public final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
            deltaTime += (currentNanoTime - lastNanoTime) / drawInterval;

            lastNanoTime = currentNanoTime;
            if (renderedFrame == FPS) {
                time--;
                renderedFrame = 0;
            }

            if (deltaTime >= 1) {
                renderedFrame++;
                if (isRunning) {
                    gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
                    update();
                    render();

                } else {
                    soundTrack.stop();
                    if (gameOver) {
                        gameOver = false;
                        GameOver.isGameOver = true;
                        GameOver.firstTime = true;

                        try {
                            URL url = new File("src/uet/oop/bomberman/menu/gameOver.fxml").toURI().toURL();
                            Parent root = FXMLLoader.load(url);
                            root.getStylesheets().add("style.css");

                            ArrayList<Label> gameOverLabel = new ArrayList<>();
                            for (Node node : root.getChildrenUnmodifiable()) {
                                if (node instanceof Label) {
                                    gameOverLabel.add((Label) node);
                                }
                            }
                            gameOverLabel.get(1).setText("Game Over!");
                            gameOverLabel.get(0).setText("Your Point: " + points);

                            screenController.setCurrentScene(new Scene(root));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (goToNextLevel) {
                        try {
                            points += time * 10;
                            screenController.renderLoadingScene();
                            goToNextLevel = false;
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resetRequired) {
                        try {
                            resetRequired = false;
                            points = previousPoints;
                            screenController.levelIndex--;
                            screenController.renderLoadingScene();
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
        container.getStylesheets().add("style.css");
        levelScene = new Scene(container);

        Font[] customFont = Font.loadFonts(new FileInputStream(
                "res/PressStart2P-Regular.ttf"), 14);
        gc.setFont(customFont[0]);

        createMap();

        sc.setCurrentScene(this.levelScene);

        // Game loop
        timer.start();
    }

    public void loadMap(String path) throws IOException {

        if (screenController.levelIndex == 0) {
            points = 0;
            previousPoints = 0;
        }

        //Clear Previous Level Data
        skellies.clear();
        bombers.clear();
        bats.clear();
        frogs.clear();
        ghosts.clear();
        tiles.clear();

        //Reset Boolen Variables
        gameOver = false;
        goToNextLevel = false;
        isRunning = true;
        time = 202;
        numberOfEnemies = 0;

        // Clear to load new map
        mapDataFile.clear();

        FileInputStream inputStream = new FileInputStream(path);
        Scanner sc = new Scanner(inputStream, StandardCharsets.UTF_8);

        // First Line contain number of row and column
        String[] levelInfo = sc.nextLine().trim().split(" ");
        Constants.ROWS = Integer.parseInt(levelInfo[1]);
        Constants.COLUMNS = Integer.parseInt(levelInfo[2]);
        Constants.SCREEN_WIDTH = Constants.COLUMNS * Constants.TILES_SIZE;
        Constants.SCREEN_HEIGHT = Constants.ROWS * Constants.TILES_SIZE + 50;
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
                        ((Layered) tiles.get(tiles.size() - 1)).canRemove = true;
                        tileMap[row][col] = "b";
                    }
                    case 'b' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new BombItem(col, row, SpriteContainer.bombItem.getFxImage(), this), this));
                        ((Layered) tiles.get(tiles.size() - 1)).canRemove = true;
                        tileMap[row][col] = "b";
                    }
                    case 'f' -> {
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new FlameItem(col, row, SpriteContainer.flameItem.getFxImage(), this), this));
                        ((Layered) tiles.get(tiles.size() - 1)).canRemove = true;
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
                    case '~' -> {
                        tiles.add(new Wall(col, row, SpriteContainer.wall_2.getFxImage(), this));
                        tileMap[row][col] = "b";
                    }
                    case 'w' -> {
                        tileMap[row][col] = "b";
                        tiles.add(new Wall(col, row, SpriteContainer.wall.getFxImage(), this));
                    }
                    case 'S' -> {
                        tileMap[row][col] = " ";
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        skellies.add(new Skelly(col, row, Animation.skellyRightAni.get(0).getFxImage(), this));
                    }
                    case 'B' -> {
                        tileMap[row][col] = " ";
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        bats.add(new Bat(col, row, Animation.batRightAni.get(0).getFxImage(), this));
                    }
                    case 'G' -> {
                        tileMap[row][col] = " ";
                        tiles.add(new Grass(col, row, SpriteContainer.grass.getFxImage(), this));
                        ghosts.add(new Ghost(col, row, Animation.ghostRightAni.get(0).getFxImage(), this));
                    }
                    case 'F' -> {
                        tileMap[row][col] = "b";
                        tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage(),
                                new Grass(col, row, SpriteContainer.grass.getFxImage(), this), this, true));
                        numberOfEnemies++;
                    }
                    case '*' -> {
                        int Random = (int) (Math.random() * 12 + 1);
                        switch (Random) {
                            case 0, 1, 2 -> {
                                tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                        , new SpeedItem(col, row, SpriteContainer.speedItem.getFxImage(), this), this));
                                tileMap[row][col] = "b";
                            }
                            case 3, 4, 5 -> {
                                tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                        , new FlameItem(col, row, SpriteContainer.flameItem.getFxImage(), this), this));
                                tileMap[row][col] = "b";
                            }
                            case 6, 7, 8 -> {
                                tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                        , new BombItem(col, row, SpriteContainer.bombItem.getFxImage(), this), this));
                                tileMap[row][col] = "b";
                            }
                            default -> {
                                tiles.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                        , new Grass(col, row, SpriteContainer.grass.getFxImage(), this), this));
                                tileMap[row][col] = "b";
                            }
                        }
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
        numberOfEnemies += skellies.size() + bats.size() + ghosts.size();
    }

    public void printTileMap() {
        for (int row = 0; row < Constants.ROWS; row++) {
            for (int col = 0; col < Constants.COLUMNS; col++) {
                System.out.print(tileMap[row][col]);
            }
            System.out.println();
        }
    }

    //Update
    private void update() {

        if (time < 0) {
            gameOver();
        }

        int count = 0;
        for (Tile tile : tiles) {
            if (tile instanceof Layered) {
                if (((Layered) tile).canRemove) {

                    tileMap[tile.getYUnit()][tile.getXUnit()] = " ";
                    bats.forEach(Bat::setUpdateRequired);
                    if (((Layered) tile).isTrap) {
                        frogs.add(new Frog(tile.getXUnit(), tile.getYUnit()
                                , Animation.frogRightAni.get(0).getFxImage(), this));
                    }
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

        skellies.removeIf(Skelly::isDead);
        if (!skellies.isEmpty()) {
            skellies.forEach(Skelly::update);
        }

        bats.removeIf(Bat::isDead);
        if (!bats.isEmpty()) {
            bats.forEach(Bat::update);
        }

        ghosts.removeIf(Ghost::isDead);
        if (!ghosts.isEmpty()) {
            ghosts.forEach(Ghost::update);
        }

        frogs.removeIf(Frog::isDead);
        if (!frogs.isEmpty()) {
            frogs.forEach(Frog::update);
        }

        bombers.removeIf(Player::isDead);
        if (!bombers.isEmpty()) {
            bombers.forEach(Player::update);
        } else {
            soundTrack.stop();
            gameOver();
        }
    }

    //Render
    private void render() {

        if (!bombers.isEmpty()) {
            renderInfo();
        }

        tiles.forEach(e -> e.render(gc));

        bombs.forEach(b -> b.render(gc));

        skellies.forEach(j -> j.render(gc));

        bats.forEach(b -> b.render(gc));

        ghosts.forEach(g -> g.render(gc));

        frogs.forEach(f -> f.render(gc));

        bombers.forEach(bomber -> bomber.render(gc));

        if (!bats.isEmpty() && !bombers.isEmpty() && showPath) {
            for (Bat bat : bats) {
                bat.showPath(bat.getNode(), bombers.get(0).getNode());
            }
        }
    }

    private void renderInfo() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, Constants.SCREEN_HEIGHT - 50, Constants.SCREEN_WIDTH, 50);
        gc.setFill(Color.WHITE);
        gc.fillText("Time: ", 10, Constants.SCREEN_HEIGHT - 20);
        gc.fillText(String.valueOf(time), 80, Constants.SCREEN_HEIGHT - 20);

        gc.fillText("Point: ", 150, Constants.SCREEN_HEIGHT - 20);
        gc.fillText(String.valueOf(points), 230, Constants.SCREEN_HEIGHT - 20);

        gc.drawImage(SpriteContainer.flameItem.getFxImage(), Constants.TILES_SIZE * 21, Constants.SCREEN_HEIGHT - 42
                , Constants.TILES_SIZE, Constants.TILES_SIZE);
        gc.fillText(String.valueOf(bombers.get(0).bombRange), Constants.TILES_SIZE * 22, Constants.SCREEN_HEIGHT - 15);

        gc.drawImage(SpriteContainer.bombItem.getFxImage(), Constants.TILES_SIZE * 24, Constants.SCREEN_HEIGHT - 42
                , Constants.TILES_SIZE, Constants.TILES_SIZE);
        gc.fillText(String.valueOf(bombers.get(0).bombNum), Constants.TILES_SIZE * 25, Constants.SCREEN_HEIGHT - 15);

        gc.drawImage(SpriteContainer.speedItem.getFxImage(), Constants.TILES_SIZE * 27
                , Constants.SCREEN_HEIGHT - 42
                , Constants.TILES_SIZE, Constants.TILES_SIZE);
        gc.fillText(String.valueOf((int) (bombers.get(0).speed - Constants.SPEED))
                , Constants.TILES_SIZE * 28, Constants.SCREEN_HEIGHT - 15);

    }

    /**
     * Pause Game
     */
    public void pause() {
        if (isPause) {
            if (!isMute) {
                soundTrack.play();
            }
            isPause = false;
            timer.start();
            container.getChildren().removeAll(screenController.subMenuNodes);
        } else {
            soundTrack.stop();
            isPause = true;
            timer.stop();
            container.getChildren().addAll(screenController.subMenuNodes);
        }
    }

    /**
     * Game Over
     */
    public void gameOver() {
        bombs.clear();
        isRunning = false;
        gameOver = true;
    }

    public void complete() {
        bombs.clear();
        isRunning = false;
        goToNextLevel = true;
    }

    public void reset() {
        bombs.clear();
        pause();
        isRunning = false;
        resetRequired = true;
    }
}
