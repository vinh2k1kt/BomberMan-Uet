package uet.oop.bomberman.entities.still.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayList;

public class Bomb extends Tile {

    private int count = 0;
    private int index = 0;
    private ArrayList<Sprite> ani = Animation.bombAni;

    public int explosionTime = 21;// thoi gian de no

    protected final Player owner;
    protected boolean exploded = false;
    protected boolean allowedToPassThru = true;
    protected boolean canRemove = false;
    protected ArrayList<Flame> flames = new ArrayList<>();

    public int timeBeforeExploded = 120;

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
        this.img = ani.get(index).getFxImage();
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

            if (explosionTime > 0)
                explosionTime--;
            else
                this.canRemove = true;
        }
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
        flames.forEach(f -> f.render(Level.gc));
    }

    private void updateFlame() {
        flames.forEach(Flame::update);
    }

    private void explode() {

        //Clear Bomb's HitBox
        this.hitBox.setWidth(0);
        this.hitBox.setHeight(0);

        exploded = true;
        this.ani = Animation.explosion_center;
        for (int i = 0; i < 5; i++) {
            flames.add(new Flame(this.x / Constants.TILES_SIZE, this.y / Constants.TILES_SIZE
                    , null, i, this.owner));
        }
    }

    public void explodedImdiately() {
        this.timeBeforeExploded = 0;
    }

    public boolean Removeable() {
        return this.canRemove;
    }


    public boolean canPassThru() {
        return allowedToPassThru;
    }

    public void firtTime() {
        this.hitBox = new Rectangle(x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        if (allowedToPassThru && !this.owner.hitBox.getBoundsInParent().intersects(hitBox.getBoundsInParent())) {
            allowedToPassThru = false;
        }
    }
}
