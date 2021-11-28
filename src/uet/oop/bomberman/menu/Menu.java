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

import static uet.oop.bomberman.Level.stage;


public class Menu {

    @FXML
    AnchorPane anchorPane = new AnchorPane();
    public void playButton(ActionEvent event) throws IOException {
        URL url = new File("src/uet/oop/bomberman/menu/loadingScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("BomberMan");
        stage.setScene(new Scene(root));
        root.getStylesheets().add("uet/oop/bomberman/menu/style.css");
        stage.show();
    }

    public void exitButton(ActionEvent even) throws IOException {
        stage.close();
    }

}
