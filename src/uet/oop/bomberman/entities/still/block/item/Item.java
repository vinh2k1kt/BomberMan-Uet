package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.still.block.undestroyable.Grass;
import uet.oop.bomberman.entities.still.block.Layered;
import uet.oop.bomberman.graphics.SpriteContainer;

public abstract class Item extends Layered {

    public Item(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        setBufferedEntity(new Grass(xUnit, yUnit, SpriteContainer.grass.getFxImage(), level));
    }

    public abstract void update();

}
