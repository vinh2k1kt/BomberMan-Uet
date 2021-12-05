package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uet.oop.bomberman.Level;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Menu {

    Stage primaryStage;

    public static Level level;

    @FXML
    AnchorPane anchorPane = new AnchorPane();
    public void playButton(ActionEvent event) throws IOException {
        level.pause();
    }

    public void exitButton(ActionEvent event) throws IOException {
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }
}
