package uet.oop.bomberman.entities.block;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public abstract class Tile extends Entity {

    public Tile(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {

    }
}
