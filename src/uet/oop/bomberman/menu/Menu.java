package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Menu {

    Stage primaryStage;

    @FXML
    AnchorPane anchorPane = new AnchorPane();
    public void playButton(ActionEvent event) throws IOException {

//        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        LoadingScene loadingScene = new LoadingScene(primaryStage, "res/levels/Level1.txt");
//
//        primaryStage.setScene(loadingScene.getScene());
        System.out.println("Clicked");
    }

    public void exitButton(ActionEvent event) throws IOException {
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.close();
    }

}
