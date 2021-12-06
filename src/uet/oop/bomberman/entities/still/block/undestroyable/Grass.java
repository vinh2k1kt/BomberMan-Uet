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

    public void render(GraphicsContext gc) {
        if (!(this.img == SpriteContainer.grass.getFxImage())) {
            gc.drawImage(SpriteContainer.grass.getFxImage(), this.x, this.y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        }
        super.render(gc);
    }
}
