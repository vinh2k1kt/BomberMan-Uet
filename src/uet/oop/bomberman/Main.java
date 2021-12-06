package uet.oop.bomberman;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        new Sound();
        Animation.initAnimation();

        primaryStage.setResizable(false);

//        URL url = new File("src/uet/oop/bomberman/menu/menu.fxml").toURI().toURL();
//        Parent root = FXMLLoader.load(url);
//        primaryStage.setTitle("BomberMan");
//        primaryStage.setScene(new Scene(root));
//        root.getStylesheets().add("uet/oop/bomberman/menu/image/style.css");

        primaryStage.setScene(new ScreenController(primaryStage).getCurrentScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
