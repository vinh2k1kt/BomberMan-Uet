package uet.oop.bomberman.entities.Brick;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.block.Tile;

public abstract class DestroyableEntity extends Tile {
    public DestroyableEntity(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
}
