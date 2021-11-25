package uet.oop.bomberman.entities.moving;

import javafx.scene.Scene;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.Items;
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
                        , character.getY() + Constants.VGAP - Constants.PLAYER_SPEED);

                checkHitbox(character);
                //Player pick up item
                if(character instanceof Player) {
                    for(Entity e : Level.entities) {
                        if(e instanceof Items) {
                            if(character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                                Level.entities.remove(e);
                                Level.bomber.pickUpItem(e);
                                break;
                            }
                        }
                    }
                }
                //Check collied with tiles
                for (Entity e : Level.entities) {
                    if (e != character
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case DOWN -> {

                character.setHitBox(character.getX() + Constants.HGAP
                        , character.getY() + Constants.VGAP + Constants.PLAYER_SPEED);

                checkHitbox(character);
                //Player pick up item
                if(character instanceof Player) {
                    for(Entity e : Level.entities) {
                        if(e instanceof Items) {
                            if(character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                                Level.entities.remove(e);
                                Level.bomber.pickUpItem(e);
                                break;
                            }
                        }
                    }
                }
                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case LEFT -> {
                character.setHitBox(character.getX() + Constants.HGAP - Constants.PLAYER_SPEED
                        , character.getY() + Constants.VGAP);

                checkHitbox(character);
                //Player pick up item
                if(character instanceof Player) {
                    for(Entity e : Level.entities) {
                        if(e instanceof Items) {
                            if(character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                                Level.entities.remove(e);
                                Level.bomber.pickUpItem(e);
                                break;
                            }
                        }
                    }
                }
                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                return false;
            }
            case RIGHT -> {
                character.setHitBox(character.getX() + Constants.HGAP + Constants.PLAYER_SPEED
                        , character.getY() + Constants.VGAP);

                checkHitbox(character);
                //Player pick up item
                if(character instanceof Player) {
                    for(Entity e : Level.entities) {
                        if(e instanceof Items) {
                            if(character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                                Level.entities.remove(e);
                                Level.bomber.pickUpItem(e);
                                break;
                            }
                        }
                    }
                }
                // if Collided then reset hitbox back to normal mode not predict mode
                for (Entity e : Level.entities) {
                    if (e != character
                            && character.hitBox.getBoundsInParent().intersects(e.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
                    }
                }

                //Check collied with bombs
                for (Bomb bomb : Level.bombs) {
                    if (character.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                        character.setHitBox(character.getX() + Constants.HGAP, character.getY() + Constants.VGAP);
                        return true;
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
        Level.gc.fillRect(character.hitBox.getX(), character.hitBox.getY()
                , character.hitBox.getWidth(), character.hitBox.getHeight());
    }
}
