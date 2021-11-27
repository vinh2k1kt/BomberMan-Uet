package uet.oop.bomberman.entities.still;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.util.Constants;

public abstract class Tile extends Entity {

    public Tile(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public abstract void update();

    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);

//        Hit box check
        gc.setFill(Constants.TILE_HITBOX_COLOR);
        gc.fillRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }
}
