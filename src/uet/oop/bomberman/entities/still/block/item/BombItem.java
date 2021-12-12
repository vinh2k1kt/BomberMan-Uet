package uet.oop.bomberman.entities.still.block.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;

public class BombItem extends Item{

    public BombItem(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
    }

    public void update() {
        super.update();
    }

    @Override
    public void updatePlayerBuff(Player bomber) {
        if (Constants.MAX_BOMB_NUM > bomber.bombNum) {
            bomber.bombNum++;
        }
    }

}
