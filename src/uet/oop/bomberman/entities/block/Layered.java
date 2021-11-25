package uet.oop.bomberman.entities.block;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.util.Constants;

public class Layered extends Entity {

    private Entity bufferedEntity;
    public boolean canRemove = false;

    public Layered(double xUnit, double yUnit, Image img, Entity bufferedEntity) {
        super(xUnit, yUnit, img);
        this.bufferedEntity = bufferedEntity;

        hitBox.setX(xUnit * Constants.TILES_SIZE);
        hitBox.setY(yUnit * Constants.TILES_SIZE);
        hitBox.setWidth(Constants.TILES_SIZE);
        hitBox.setHeight(Constants.TILES_SIZE);
    }

    public Layered(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);

        hitBox.setX(xUnit * Constants.TILES_SIZE);
        hitBox.setY(yUnit * Constants.TILES_SIZE);
        hitBox.setWidth(Constants.TILES_SIZE);
        hitBox.setHeight(Constants.TILES_SIZE);
    }

    public void setCanRemove() {
        canRemove = true;
    }

    public void clearRemove() {
        canRemove =false;
    }

    public void renderBufferedEntity() {
        Level.gc.drawImage(bufferedEntity.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
    }

    public Entity getBufferedEntity() {
        return bufferedEntity;
    }

    public void setBufferedEntity(Entity bufferedEntity) {
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
