package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Howtoplay {
    private Stage stage;
    public void backFunc(ActionEvent event) throws IOException {
        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        root.getStylesheets().add("uet/oop/bomberman/menu/image/style.css");
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("BomberMan");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
