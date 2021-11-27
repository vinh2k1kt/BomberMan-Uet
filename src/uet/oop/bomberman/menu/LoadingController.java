package uet.oop.bomberman.menu;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import uet.oop.bomberman.Level;

import java.net.URL;
import java.util.ResourceBundle;

import static uet.oop.bomberman.Level.stage;

public class LoadingController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new loadingScreen().start();
    }

    class loadingScreen extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        stage.setTitle("BomberMan");
                        stage.setScene(Level.levelScene);
                        stage.show();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
