package uet.oop.bomberman;

import uet.oop.bomberman.entities.Entity;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Wall;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.moving.enemy.Jelly;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.SpriteContainer;
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

    public static Player bomber;
    private Jelly jelly;
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
                    case ' ' -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                    }
                    case 'p' -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                        bomber = new Player(col, row, SpriteContainer.player_right.getFxImage());
                    }
                    case '*' -> {
                        entities.add(new Wall(col, row, SpriteContainer.brick.getFxImage()));
                    }
                    case '1' -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                        jelly = new Jelly(col, row, SpriteContainer.Jelly.getFxImage());
                    }
                    default -> {
                        entities.add(new Grass(col, row, SpriteContainer.grass.getFxImage()));
                    }
                }
            }
            row++;
        }
    }

    public static void gameOver() {

        isRunning = false;

//        gc.clearRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//        gc.setFill(Color.BLACK);
//        gc.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
//        gc.setFill(Color.WHITE);
//
//        gc.setFont(new Font("Arial", 100));
//        gc.setTextAlign(TextAlignment.CENTER);
//        gc.setTextBaseline(VPos.CENTER);
//        gc.fillText("Game Over", Constants.SCREEN_WIDTH / 2.0, Constants.SCREEN_HEIGHT / 2.0);
    }

    private void render() {
        entities.forEach(e -> e.render(gc));

        if (!bombs.isEmpty()) { bombs.forEach(b -> b.render(gc)); }

        bomber.render(gc);
        jelly.render(gc);
    }

    private void update() {
        entities.forEach(Entity::update);

        bombs.removeIf(Bomb::Removeable);
        if (!bombs.isEmpty()) {  bombs.forEach(Bomb::update); }

        bomber.update();
        jelly.update();
    }
}
