package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.block.Layered;
import uet.oop.bomberman.entities.block.destroyable.Brick;
import uet.oop.bomberman.entities.block.undestroyable.Grass;
import uet.oop.bomberman.entities.block.undestroyable.Wall;
import uet.oop.bomberman.entities.block.item.BombItem;
import uet.oop.bomberman.entities.block.item.FlameItem;
import uet.oop.bomberman.entities.block.item.SpeedItem;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.moving.enemy.Jelly;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.graphics.SpriteContainer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Level extends Canvas {

    public static Scene levelScene = new Scene(new Group());
    public static Canvas levelCanvas;
    public static GraphicsContext gc;
    public static ArrayList<Entity> entities = new ArrayList<>();
    public static String[][] tileMap;
    public static ArrayList<Bomb> bombs = new ArrayList<>();

    public static double currentGameTime;
    public static double lastNanoTime;
    public static double deltaTime;
    public static double drawInterval = 1000000000 / 120.0;
    public final static long startNanoTime = System.nanoTime();

    public static boolean isRunning = true;

    public static ArrayList<Player> bombers = new ArrayList<>();
    public static ArrayList<Jelly> jellies = new ArrayList<>();
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
                    timer.stop();
                }
            }
            lastNanoTime = System.nanoTime();
        }
    };


    public Level() throws IOException {

        loadMap("res/levels/Level1.txt");

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
                    case '#' -> {
                        entities.add(new Wall(col, row, SpriteContainer.wall.getFxImage()));
                    }
                    case 's' -> {
                        entities.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new SpeedItem(col, row, SpriteContainer.speedItem.getFxImage())));
                    }
                    case 'b' -> {
                        entities.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new BombItem(col, row, SpriteContainer.bombItem.getFxImage())));
                    }
                    case 'f' -> {
                        entities.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new FlameItem(col, row, SpriteContainer.flameItem.getFxImage())));
                    }
                    case 'p' -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                        bombers.add(new Player(col, row, SpriteContainer.player_right.getFxImage()));
                    }
                    case '*' -> {
                        entities.add(new Brick(col, row, SpriteContainer.brick.getFxImage()
                                , new Grass(col, row, SpriteContainer.grass.getFxImage())));
                    }
                    case '1' -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                        jellies.add(new Jelly(col, row, SpriteContainer.Jelly.getFxImage()));
                    }
                    default -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                    }
                }
            }
            row++;
        }
    }

    //Game Over
    public static void gameOver() {

    }

    private void render() {

        entities.forEach(e -> e.render(gc));

        if (!bombs.isEmpty()) {
            bombs.forEach(b -> b.render(gc));
        }

        jellies.forEach(j -> j.render(gc));

        if (!bombers.isEmpty()) {
            bombers.forEach(bomber -> bomber.render(gc));
        }
    }

    private void update() {

        int count = 0;
        for (Entity entity : entities) {
            entity.update();
            if (entity instanceof Layered) {
                if (((Layered) entity).canRemove) {
                    entities.set(count, ((Layered) entity).getBufferedEntity());
                    if (!(((Layered) entity).getBufferedEntity() instanceof Grass)) {
                        ((Layered) entities.get(count)).clearRemove();
                    }
                }
            }
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
        }
    }
}
