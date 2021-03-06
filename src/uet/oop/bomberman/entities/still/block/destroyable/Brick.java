package uet.oop.bomberman.entities.still.block.destroyable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.still.block.Layered;

public class Brick extends Layered {

    public boolean destroyed = false;

    private int explosionTime = 21;

    private int count;
    private int index;

    public Brick(double xUnit, double yUnit, Image img, Tile bufferedEntity, Level level) {
        super(xUnit, yUnit, img, bufferedEntity, level);

//        kwanUwU
//        this.img = new Image(new File("res/brick.jpg").toURI().toString());
    }

    public Brick(double xUnit, double yUnit, Image img, Tile bufferedEntity, Level level, boolean isTrap) {
        super(xUnit, yUnit, img, bufferedEntity, level);
        this.isTrap = isTrap;

//        kwanUwU
//        this.img = new Image(new File("res/brick.jpg").toURI().toString());
    }

    public void destroy() {
        if (!destroyed) {
            destroyed = true;
        }
    }

    private void chooseSprite() {
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
        this.img = Animation.brick_explosion.get(index).getFxImage();
    }

    @Override
    public void update() {
        if (destroyed) {
            if (explosionTime > 0) {
                explosionTime--;
                chooseSprite();
            } else {
                setCanRemove();
            }
        }
    }

    public void render(GraphicsContext gc) {
        if (destroyed) {
            renderBufferedEntity();
        }
        super.render(gc);
    }

}
