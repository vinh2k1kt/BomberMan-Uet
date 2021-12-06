package uet.oop.bomberman.entities.still.block.undestroyable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;

public class Wall extends Tile {

    public Wall(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);

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

    public void render(GraphicsContext gc) {
        gc.drawImage(SpriteContainer.grass.getFxImage(), this.x, this.y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        super.render(gc);
    }
}
