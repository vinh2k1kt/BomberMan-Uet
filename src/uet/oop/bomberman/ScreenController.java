package uet.oop.bomberman;

import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.menu.GameOver;
import uet.oop.bomberman.menu.Menu;
import uet.oop.bomberman.menu.Submenu;
import uet.oop.bomberman.util.Constants;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScreenController {
    Label loadingLabel;
    ArrayList<Label> gameOverLabel = new ArrayList<>();

    public ArrayList<Node> subMenuNodes = new ArrayList<>();
    Stage primaryStage;
    Level level;

    public Scene loadingScene;
    public Scene currentScene;
    public static int x = 0;
    public int levelIndex = -1;

    private boolean firstTime = true;

    public ScreenController(Stage primaryStage) throws IOException, InterruptedException {
        SpriteContainer.initDesertStage();

        this.primaryStage = primaryStage;

        URL url = new File("src/uet/oop/bomberman/menu/submenu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        root.getStylesheets().add("style.css");
        subMenuNodes.addAll(root.getChildrenUnmodifiable());

        level = new Level(primaryStage, Constants.levelPath.get(levelIndex + 1), this);

        Menu.level = level;
        GameOver.level = level;
        Submenu.level = level;

        url = new File("src/uet/oop/bomberman/menu/loading.fxml").toURI().toURL();
        root = FXMLLoader.load(url);
        root.getStylesheets().add("style.css");

        //Do The Exact Same Thing We Did To GameOver Label
        for (Node node : root.getChildrenUnmodifiable()) {
            if (node instanceof Label) {
                loadingLabel = (Label) node;
                break;
            }
        }
        loadingScene =  new Scene(root);

        renderLoadingScene();
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
        primaryStage.setScene(currentScene);
    }

    public void renderLoadingScene() throws IOException, InterruptedException {
        SpriteContainer.initDesertStage();
        if (levelIndex < Constants.levelPath.size() - 1) {
            levelIndex++;
            level.previousPoints = level.points;
        } else {
            level.goToNextLevel = false;
            level.finalLevel = true;
        }
        if (level.finalLevel) {
            try {
                GameOver.firstTime = true;
                GameOver.isGameOver = false;
                URL url = new File("src/uet/oop/bomberman/menu/gameOver.fxml").toURI().toURL();
                Parent root = FXMLLoader.load(url);
                root.getStylesheets().add("style.css");

                //Getting GameOver Label So We Can Change The Text Cause Javafx Suck And We Can't Do It Normally
                for (Node node : root.getChildrenUnmodifiable()) {
                    if (node instanceof Label) {
                        gameOverLabel.add((Label) node);
                    }
                }

                gameOverLabel.get(1).setText("Game Completed!");
                gameOverLabel.get(0).setText("Your Point: " + level.points);
                level.finalLevel = false;
                setCurrentScene(new Scene(root));
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (!firstTime) {
            level.loadMap(Constants.levelPath.get(levelIndex));
            level.createMap();
        } else {
            firstTime = false;
        }

        playThemeSong();

        loadingLabel.setText("Level " + (levelIndex + 1));
        setCurrentScene(loadingScene);

        Task<Void> sleeper = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> setCurrentScene(level.levelScene));
        new Thread(sleeper).start();
    }

    public void playThemeSong() {
        level.soundTrack.setFile(Constants.soundTrack.get(levelIndex));
        level.soundTrack.play();
        level.soundTrack.loop();
    }
}
