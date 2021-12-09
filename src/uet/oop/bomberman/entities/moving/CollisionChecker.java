package uet.oop.bomberman.entities.moving;

import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.moving.enemy.Ghost;
import uet.oop.bomberman.entities.still.block.item.Item;
import uet.oop.bomberman.entities.still.bomb.Bomb;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;

public class CollisionChecker {

    Character character;
    Level level;

    public CollisionChecker(Character character, Level level) {
        this.level = level;
        this.character = character;
    }

    public boolean isBlocked(Character character) {

        // if Collided then reset hitbox back to normal mode not predict mode
        switch (character.currentDirection) {
            case UP -> {

                character.setHitBox(character.x + Constants.HGAP
                        , character.y + Constants.VGAP - character.speed);

                //Check collied with tile
                for (int i = -1; i <= 1; i++) {

                    if (character instanceof Ghost) {
                        return false;
                    }

                    Entity tileToCheck = level.tiles.get((character.getYUnit() - 1) * Constants.COLUMNS + character.getXUnit() + i);
                    if (tileToCheck.hitBox.getBoundsInParent().intersects(character.hitBox.getBoundsInParent())
                            && !(tileToCheck instanceof Item)) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return true;
                    }
                }

                checkHitbox(character);

                //Check collied with bombs
                for (Bomb bomb : level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                return false;
            }
            case DOWN -> {

                character.setHitBox(character.x + Constants.HGAP
                        , character.y + Constants.VGAP + character.speed);

                //Check collied with tile
                for (int i = -1; i <= 1; i++) {

                    if (character instanceof Ghost) {
                        return false;
                    }

                    Entity tileToCheck = level.tiles.get((character.getYUnit() + 1) * Constants.COLUMNS + character.getXUnit() + i);
                    if (tileToCheck.hitBox.getBoundsInParent().intersects(character.hitBox.getBoundsInParent())
                            && !(tileToCheck instanceof Item)) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return true;
                    }
                }

                checkHitbox(character);

                //Check collied with bombs
                for (Bomb bomb : level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                return false;
            }
            case LEFT -> {

                character.setHitBox(character.x + Constants.HGAP - character.speed
                        , character.y + Constants.VGAP);

                //Check collied with tile
                for (int i = -1; i <= 1; i++) {

                    if (character instanceof Ghost) {
                        return false;
                    }

                    Entity tileToCheck = level.tiles.get((character.getYUnit() + i) * Constants.COLUMNS + character.getXUnit() - 1);
                    if (tileToCheck.hitBox.getBoundsInParent().intersects(character.hitBox.getBoundsInParent())
                            && !(tileToCheck instanceof Item)) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return true;
                    }
                }

                checkHitbox(character);

                //Check collied with bombs
                for (Bomb bomb : level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                return false;
            }
            case RIGHT -> {

                character.setHitBox(character.x + Constants.HGAP + character.speed
                        , character.y + Constants.VGAP);

                //Check collied with tile
                for (int i = -1; i <= 1; i++) {


                    if (character instanceof Ghost) {
                        return false;
                    }

                    Entity tileToCheck = level.tiles.get((character.getYUnit() + i) * Constants.COLUMNS + character.getXUnit() + 1);
                    if (tileToCheck.hitBox.getBoundsInParent().intersects(character.hitBox.getBoundsInParent())
                            && !(tileToCheck instanceof Item)) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return true;
                    }
                }

                checkHitbox(character);

                //Check collied with bombs
                for (Bomb bomb : level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.x + Constants.HGAP, character.y + Constants.VGAP);
                return false;
            }
        }
        return false;
    }

    public void Kill(Player bomber) {
        if (this.character.hitBox.getBoundsInParent().intersects(bomber.hitBox.getBoundsInParent())) {
            bomber.isKill();
        }
    }

    public void checkHitbox(Character character) {
        level.gc.setFill(Constants.PREDICTING_HITBOX_COLOR);

        level.gc.fillRect(character.hitBox.getX(), character.hitBox.getY()
                , character.hitBox.getWidth(), character.hitBox.getHeight());
    }
}
