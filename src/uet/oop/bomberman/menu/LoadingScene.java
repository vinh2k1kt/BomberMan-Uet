package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.graphics.Animation;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class LoadingScene extends Thread {

    Stage primaryStage;
    Scene loadingScene;
    String levelPath;

    @FXML
    private Label label;

    public LoadingScene(Stage primaryStage, String levelPath) throws IOException {

        URL url = new File("src/uet/oop/bomberman/menu/loadingScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        loadingScene = new Scene(root);
        primaryStage.setScene(loadingScene);

        this.levelPath = levelPath;
        this.primaryStage = primaryStage;
        this.start();
    }

    public Scene getScene() {
        return loadingScene;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            Platform.runLater(() -> {
                try {
                    Animation.initAnimation();
                    Level level = new Level(primaryStage, levelPath);

                    primaryStage.setScene(level.levelScene);
                    primaryStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}