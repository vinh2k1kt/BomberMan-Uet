package uet.oop.bomberman;


import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ScreenController {

    public ArrayList<Node> nodes = new ArrayList<>();
    Stage stage;
    Level level;
    LoadingScreen loadingScreen;
    public Scene currentScene;
    public static int x = 0;

    public ScreenController(Stage primaryStage, String levelPath) throws IOException {

        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        for (Node node : root.getChildrenUnmodifiable()) {
            nodes.add(node);
            if (node instanceof Parent)
                break;
        }

        stage = primaryStage;
        level = new Level(primaryStage, levelPath, this);
        loadingScreen = new LoadingScreen();
        currentScene = level.levelScene;
        setCurrentScene(currentScene);
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
        stage.setScene(currentScene);
    }

    public void renderLoadingScene() throws IOException {
        level.timer.stop();
        setCurrentScene(loadingScreen.scene);
        loadingScreen.render();


        level = new Level(stage, "res/levels/Level2.txt", this);
    }
}
