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

    public void howtoplayButton(ActionEvent event) throws IOException {
        URL url = new File("src/uet/oop/bomberman/menu/howtoplay.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("BomberMan");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add("uet/oop/bomberman/menu/image/style.css");
    }
}
