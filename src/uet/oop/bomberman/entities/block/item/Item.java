package uet.oop.bomberman.entities.block.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.block.Grass;
import uet.oop.bomberman.entities.block.Layered;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.SpriteContainer;

public class Item extends Layered {

    public Item(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
        setBufferedEntity(new Grass(xUnit, yUnit, SpriteContainer.grass.getFxImage()));
    }

    public void update() {
        if (Level.bomber.hitBox.getBoundsInParent().intersects(this.hitBox.getBoundsInParent())) {
            setCanRemove();
        }
    }
}
