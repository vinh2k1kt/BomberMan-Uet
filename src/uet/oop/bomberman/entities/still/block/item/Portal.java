package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.entities.still.block.Layered;
import uet.oop.bomberman.entities.still.block.undestroyable.Grass;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;

public class Portal extends Layered {

    public boolean completeable = false;
    private final Rectangle innerHitBox;

    public Portal(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        setBufferedEntity(new Grass(xUnit, yUnit, SpriteContainer.grass.getFxImage(), level));

        innerHitBox = new Rectangle(x + 4, y + 4, Constants.TILES_SIZE - 4, Constants.TILES_SIZE - 4);
    }

    @Override
    public void update() {
        if (level.numberOfEnemies == 0) {
            passAble();
        }

        if (completeable) {
            for (Player bomber : level.bombers) {
                if (bomber.hitBox.getBoundsInParent().intersects(innerHitBox.getBoundsInParent())) {
                    level.complete();
                }
            }
        }
    }

    public void render(GraphicsContext gc) {
        renderBufferedEntity();
        super.render(gc);
    }
    public void passAble() {
        completeable = true;
        hitBox.setWidth(0);
        hitBox.setHeight(0);
    }
}
