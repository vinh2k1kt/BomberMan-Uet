package uet.oop.bomberman.entities.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.block.Tile;
import uet.oop.bomberman.util.Constants;

public abstract class Items extends Tile {
    public Items(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        hitBox.setX(x);
        hitBox.setY(y);
        hitBox.setWidth(Constants.SOLID_AREA_WIDTH);
        hitBox.setHeight(Constants.SOLID_AREA_HEIGHT);
    }
}
