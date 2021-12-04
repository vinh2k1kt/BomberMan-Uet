package uet.oop.bomberman.menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Menu {

    public Stage primaryStage;

    @FXML
    Button buttonPlay = new Button();
    @FXML
    Button buttonExit = new Button();

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

    public void howtoplayButton(ActionEvent event) throws IOException {
        URL url = new File("src/uet/oop/bomberman/menu/howtoplay.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        root.getStylesheets().add("uet/oop/bomberman/menu/image/style.css");
        primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("BomberMan");
        primaryStage.setScene(new Scene(root));
    }
}
