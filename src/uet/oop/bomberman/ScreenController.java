package uet.oop.bomberman;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.menu.Menu;
import uet.oop.bomberman.util.Constants;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScreenController {

    public ArrayList<Node> nodes = new ArrayList<>();
    Stage stage;
    Level level;
    public LoadingScreen loadingScreen;
    public Scene currentScene;
    public static int x = 0;
    public int levelIndex = 0;

    public ScreenController(Stage primaryStage) throws IOException {

        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        for (Node node : root.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                break;
        }

        stage = primaryStage;
        level = new Level(primaryStage, Constants.levelPath.get(levelIndex), this);
        Menu.level = level;
        loadingScreen = new LoadingScreen();
        currentScene = level.levelScene;
//        currentScene = loadingScreen.scene;
        setCurrentScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
        stage.setScene(currentScene);
    }

    public void renderLoadingScene() throws IOException, InterruptedException {
        setCurrentScene(this.loadingScreen.scene);

        if (levelIndex < Constants.levelPath.size()) {
            levelIndex++;
        }

        level.loadMap(Constants.levelPath.get(levelIndex));
        level.createMap();

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
}
