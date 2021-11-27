package uet.oop.bomberman.entities.block.destroyable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.block.Layered;

public class Brick extends Layered {

    public boolean destroyed = false;
    public boolean canRemove = false;

    private int explosionTime = 21;

    private int count;
    private int index;

    public Brick(double xUnit, double yUnit, Image img, Entity bufferedEntity) {
        super(xUnit, yUnit, img, bufferedEntity);

//        kwanUwU
//        this.img = new Image(new File("res/brick.jpg").toURI().toString());
    }

    public void destroy() {
        destroyed = true;
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
