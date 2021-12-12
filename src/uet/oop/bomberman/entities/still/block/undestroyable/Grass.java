package uet.oop.bomberman.entities.still.block.undestroyable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;

public class Grass extends Tile {

    public Grass(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
    }

    @Override
    public void update() {

    }
}
