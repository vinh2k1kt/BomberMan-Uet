package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;

public class FlameItem extends Item{

    public FlameItem(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
        for (Player bomber : Level.bombers)
        if (bomber.hitBox.getBoundsInParent().intersects(this.hitBox.getBoundsInParent())) {
            setCanRemove();
            updatePlayerBuff(bomber);
        }
    }

    private void updatePlayerBuff(Player bomber) {
        bomber.bombRange++;
    }
}
