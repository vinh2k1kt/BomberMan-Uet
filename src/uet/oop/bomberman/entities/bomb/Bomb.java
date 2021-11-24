package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.moving.Animation;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;

public class Bomb extends Entity {

    private final Player owner;
    private int count = 0;
    private int index = 0;

    protected int timeBeforeExploded = 120;
    protected boolean exploded = false;
    protected boolean allowedToPassThru= true;
    protected boolean canRemove = false;

    public Bomb(double xUnit, double yUnit, Image img, Player owner) {
        super(xUnit, yUnit, img);
        this.owner = owner;
    }

    public void setHitBox(double x, double y) {
        hitBox.setX(x);
        hitBox.setY(y);
        hitBox.setWidth(Constants.SOLID_AREA_WIDTH);
        hitBox.setHeight(Constants.SOLID_AREA_HEIGHT);
    }

    public void chooseSprite() {
        /*
        Calculated which frame gonna be displayed
         */
        count++;

        int delay = 7;
        if (count == delay) {
            index++;
            count = 0;
        }
        if (index == 3) {
            index = 0;
        }
        this.img = Animation.bombAni.get(index).getFxImage();
    }
    @Override
    public void update() {

        firtTime();
        countDown();
        if (!exploded) {
            chooseSprite();
        }
    }

    private void countDown() {
        if (timeBeforeExploded > 0) {
            timeBeforeExploded--;
        } else {
            if (!exploded) {
                explode();
            } else {
                updateFlame();
            }
        }
    }

    private void updateFlame() {
    }

    private void explode() {
        this.canRemove = true;
    }

    public boolean Removeable() {
        return this.canRemove;
    }

    private void firtTime() {
        Rectangle rectangle = new Rectangle(x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        if (allowedToPassThru && !this.owner.hitBox.getBoundsInParent().intersects(rectangle.getBoundsInParent())) {
            allowedToPassThru = false;
            this.hitBox = rectangle;
        }
    }
}
