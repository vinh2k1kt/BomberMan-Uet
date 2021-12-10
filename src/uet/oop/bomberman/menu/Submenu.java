package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import uet.oop.bomberman.Level;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Submenu {

    public static Level level;

    @FXML
    private Button Resume;

    @FXML
    private Button Exit;

    @FXML
    void Continue(ActionEvent event) {
        level.pause();
    }

    @FXML
    void goToMainMenu(ActionEvent event) throws IOException {
        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        root.getStylesheets().add("style.css");
    }

    @FXML
    void Restart(ActionEvent event) throws IOException {
        level.reset();
    }

    @FXML
    void Mute(ActionEvent event) {
        level.isMute = !level.isMute;
    }

}
