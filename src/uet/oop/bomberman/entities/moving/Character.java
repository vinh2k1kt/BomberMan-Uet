package uet.oop.bomberman.entities.moving;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;

import java.util.ArrayList;

public abstract class Character extends Entity {

    public boolean alive = true;
    public boolean canRemove = false;
    public Direction currentDirection = Direction.NONE;
    public double speed = 2;
    public int count = 0;
    public int index = 0;
    public int delay = 7;
    private int renderDeadAniTime = 30;

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

    public void isKill() {
        if (alive) {
            this.delay = 10;
            this.alive = false;
            index = 0;
            count = 0;

            currentDirection = Direction.NONE;

            //Remove KeyHandler;
            Level.levelScene.setOnKeyReleased(keyEvent -> {

            });
        }
    }

    public void afterDead(ArrayList<Sprite> deadAni) {
        if (renderDeadAniTime > 0) {
            renderDeadAniTime--;
            renderDeadAni(deadAni);
        } else {
            this.canRemove = true;
        }
    }

    private void renderDeadAni(ArrayList<Sprite> deadAni) {

        /*
        Calculated which frame gonna be displayed
         */
        count++;

        if (count == delay) {
            index++;
            count = 0;
        }

        if (index >= 3) {
            return;
        }

        this.img = deadAni.get(index).getFxImage();
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);

//        Hit box check
        gc.setFill(Constants.HITBOX_COLOR);
        gc.fillRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    public boolean isDead() {
        return canRemove;
    }

    @Override
    public abstract void update();
}
