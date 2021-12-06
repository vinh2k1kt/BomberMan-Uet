package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;

public class SpeedItem extends Item{

    public SpeedItem(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
    }

    @Override
    public void update() {
        for (Player bomber : level.bombers)
            if (bomber.hitBox.getBoundsInParent().intersects(this.hitBox.getBoundsInParent())) {
                setCanRemove();
                updatePlayerBuff(bomber);
            }
    }

    private void updatePlayerBuff(Player bomber) {
        bomber.speed++;
    }
}
