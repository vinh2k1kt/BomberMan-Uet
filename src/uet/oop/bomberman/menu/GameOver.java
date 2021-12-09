package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameOver implements Initializable {

    public static boolean firstTime = false;
    public static boolean isGameOver;
    public static Level level;
    Sound sound;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sound = new Sound();

        if (firstTime) {
            if (isGameOver) {
                sound.setFile("Elder");
            } else {
                sound.setFile("Souls");
            }
            isGameOver = false;
            System.out.println("gameOver");
            sound.play();
        }
    }

    @FXML
    void Replay(ActionEvent event) throws IOException, InterruptedException {
        sound.play();
        level.screenController.levelIndex = -1;
        level.screenController.renderLoadingScene();
    }

    public void exitButton(ActionEvent event) throws IOException {
        sound.play();
        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        root.getStylesheets().add("style.css");
    }
}
