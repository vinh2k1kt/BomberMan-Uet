package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.moving.Animation;
import uet.oop.bomberman.util.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Animation.initAnimation();
        Level level = new Level();

        primaryStage.setTitle(Constants.GAME_TILE);
        primaryStage.setScene(Level.levelScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
