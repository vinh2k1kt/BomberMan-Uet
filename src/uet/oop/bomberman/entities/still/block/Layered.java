package uet.oop.bomberman.entities.still.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.entities.still.block.undestroyable.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;

public abstract class Layered extends Tile {

    private Tile bufferedEntity;
    public boolean isTrap = false;
    public boolean canRemove = false;

    public Layered(double xUnit, double yUnit, Image img, Tile bufferedEntity, Level level) {
        super(xUnit, yUnit, img, level);
        this.bufferedEntity = bufferedEntity;

        hitBox.setX(xUnit * Constants.TILES_SIZE);
        hitBox.setY(yUnit * Constants.TILES_SIZE);
        hitBox.setWidth(Constants.TILES_SIZE);
        hitBox.setHeight(Constants.TILES_SIZE);
    }

    public Layered(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);

        hitBox.setX(xUnit * Constants.TILES_SIZE);
        hitBox.setY(yUnit * Constants.TILES_SIZE);
        hitBox.setWidth(Constants.TILES_SIZE);
        hitBox.setHeight(Constants.TILES_SIZE);
    }

    public void setCanRemove() {
        canRemove = true;
    }

    public void clearRemove() {
        canRemove = false;
    }

    public void renderBufferedEntity() {
        if (bufferedEntity instanceof Layered) {
            level.gc.drawImage(bufferedEntity.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
            level.gc.drawImage(((Layered) bufferedEntity).getBufferedEntity().img, x, y, Constants.TILES_SIZE
                    , Constants.TILES_SIZE);
        } else {
            level.gc.drawImage(bufferedEntity.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        }
    }

    public Tile getBufferedEntity() {
        return bufferedEntity;
    }

    public void setBufferedEntity(Tile bufferedEntity) {
        this.bufferedEntity = bufferedEntity;
    }

    @Override
    public void update() {

    }

    public void render(GraphicsContext gc) {
        if (canRemove) {
            renderBufferedEntity();
        }
        super.render(gc);
    }
}
