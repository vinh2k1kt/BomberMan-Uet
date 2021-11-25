package uet.oop.bomberman.entities.moving.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;

public class Jelly extends Character {
    public Jelly(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.currentDirection = Direction.LEFT;
    }

    private int speed = - Constants.ENEMY_SPEED;
    private int count = 0;
    private int index = 0;
    private CollisionChecker collisionChecker = new CollisionChecker(Level.levelScene, this);

    @Override
    public void update() {
        if (!collisionChecker.isBlocked(this)) {
            x += speed;
            chooseSprite();
        } else if (currentDirection == Direction.LEFT) {
            speed = -speed;
            this.img = Animation.jellyRightAni.get(0).getFxImage();
            currentDirection = Direction.RIGHT;
            chooseSprite();
        } else {
            speed = -speed;
            this.img = Animation.jellyLeftAni.get(0).getFxImage();
            currentDirection = Direction.LEFT;
            chooseSprite();
        }

        collisionChecker.Kill(Level.bomber);
    }

    private void chooseSprite() {

        /*
        Calculated which frame gonna be displayed
         */
        count++;

        int delay = 7;
        if (count == delay) {
            index++;
            count = 0;
        }
        if (index == 3) {
            index = 0;
        }

        switch (currentDirection) {
            case LEFT -> this.img = Animation.jellyLeftAni.get(index).getFxImage();
            case RIGHT -> this.img = Animation.jellyRightAni.get(index).getFxImage();
        }
    }
}
