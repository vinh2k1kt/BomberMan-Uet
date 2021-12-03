package uet.oop.bomberman;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.util.Constants;

public class LoadingScreen {

    Canvas canvas;
    GraphicsContext gc;
    Scene scene;

    public LoadingScreen() {
        canvas = new Canvas(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        Group container = new Group();
        container.getChildren().add(canvas);
        scene = new Scene(container);
    }

    public void render() {
        gc.fillRect(0,0,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        System.out.println("Here");
    }
}
