package uet.oop.bomberman.entities.still.block.undestroyable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.util.Constants;

public class Wall extends Tile {

    public Wall(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);

        hitBox.setX(xUnit * Constants.TILES_SIZE);
        hitBox.setY(yUnit * Constants.TILES_SIZE);
        hitBox.setWidth(Constants.TILES_SIZE);
        hitBox.setHeight(Constants.TILES_SIZE);

//        kwanUwU
//        this.img = new Image(new File("res/brick.jpg").toURI().toString());
    }

    @Override
    public void update() {

    }

}