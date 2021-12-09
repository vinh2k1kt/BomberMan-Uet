package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;

public class SpeedItem extends Item{

    public SpeedItem(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
    }
        public void update() {
        super.update();
    }

    @Override
    public void updatePlayerBuff(Player bomber) {
        bomber.speed++;
    }
}
