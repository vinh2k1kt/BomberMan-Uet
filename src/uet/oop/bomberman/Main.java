package uet.oop.bomberman;

import javafx.application.Application;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.util.Constants;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Level level = new Level();
        Animation.initAnimation();

        primaryStage.setTitle(Constants.GAME_TILE);
        primaryStage.setScene(Level.levelScene);
        primaryStage.sizeToScene();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
