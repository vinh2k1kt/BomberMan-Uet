package uet.oop.bomberman.entities.moving;

import javafx.scene.Scene;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.block.item.Item;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;

public class CollisionChecker {

    Scene scene;
    Character character;

    public CollisionChecker(Scene scene, Character character) {
        this.scene = scene;
        this.character = character;
    }

    public boolean isBlocked(Character character) {

        // if Collided then reset hitbox back to normal mode not predict mode
        switch (character.currentDirection) {
            case UP -> {
                character.setHitBox(character.getX() + Constants.HGAP
                        , character.getY() + Constants.VGAP - character.speed);

                checkHitbox(character);

                //Check collied with tiles
                for (Entity e : Level.entities) {
                    if (e != character && !(e instanceof Item)
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case DOWN -> {

                character.setHitBox(character.getX() + Constants.HGAP
                        , character.getY() + Constants.VGAP + character.speed);

                checkHitbox(character);

                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character && !(e instanceof Item)
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case LEFT -> {
                character.setHitBox(character.getX() + Constants.HGAP - character.speed
                        , character.getY() + Constants.VGAP);

                checkHitbox(character);

                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character && !(e instanceof Item)
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case RIGHT -> {
                character.setHitBox(character.getX() + Constants.HGAP + character.speed
                        , character.getY() + Constants.VGAP);

                checkHitbox(character);

                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character && !(e instanceof Item)
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return !(character instanceof Player) || !bomb.canPassThru();
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
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
        Level.gc.setFill(Constants.hitBoxColor);

//        Level.gc.setFill(Color.BLUE);
        Level.gc.fillRect(character.hitBox.getX(), character.hitBox.getY()
                , character.hitBox.getWidth(), character.hitBox.getHeight());
    }
}
