package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.entities.still.block.undestroyable.Grass;
import uet.oop.bomberman.entities.still.block.Layered;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.Constants;

public abstract class Item extends Layered {

    public Item(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        setBufferedEntity(new Grass(xUnit, yUnit, SpriteContainer.grass.getFxImage(), level));
    }

    public void update() {
        for (Player bomber : level.bombers)
            if (bomber.hitBox.getBoundsInParent().intersects(this.hitBox.getBoundsInParent())) {
                sound.setFile("Item");
                sound.play();
                setCanRemove();
                updatePlayerBuff(bomber);
            }
    }

    public abstract void updatePlayerBuff(Player bomber);

    public void render(GraphicsContext gc) {
        gc.drawImage(SpriteContainer.grass.getFxImage(), this.x, this.y, Constants.TILES_SIZE, Constants.TILES_SIZE);
        super.render(gc);
    }
}
