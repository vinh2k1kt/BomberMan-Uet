package uet.oop.bomberman.entities.moving;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;

public abstract class Character extends Entity {

    public Direction currentDirection = Direction.NONE;

    public Character(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        hitBox.setX(xUnit * Constants.TILES_SIZE + Constants.HGAP);
        hitBox.setY(yUnit * Constants.TILES_SIZE + Constants.VGAP);
        hitBox.setWidth(Constants.SOLID_AREA_WIDTH);
        hitBox.setHeight(Constants.SOLID_AREA_HEIGHT);
    }

    public void setHitBox(double x, double y) {
        hitBox.setX(x);
        hitBox.setY(y);
        hitBox.setWidth(Constants.SOLID_AREA_WIDTH);
        hitBox.setHeight(Constants.SOLID_AREA_HEIGHT);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public abstract void update();
}
