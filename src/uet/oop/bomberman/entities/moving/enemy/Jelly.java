package uet.oop.bomberman.entities.moving.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Direction;

public class Jelly extends Character {
    private int renderDeadImageTime  = 10;
    private boolean alive = true;
    private CollisionChecker collisionChecker = new CollisionChecker(Level.levelScene, this);
    private double signedSpeed;

    public Jelly(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        this.currentDirection = Direction.RIGHT;
        this.signedSpeed = 2;
    }

    @Override
    public void update() {
        if (alive) {
            if (!collisionChecker.isBlocked(this)) {
                x += signedSpeed;
                chooseSprite();
            } else if (currentDirection == Direction.LEFT) {
                this.signedSpeed = -signedSpeed;
                this.img = Animation.jellyRightAni.get(0).getFxImage();
                currentDirection = Direction.RIGHT;
                chooseSprite();
            } else {
                this.signedSpeed = -signedSpeed;
                this.img = Animation.jellyLeftAni.get(0).getFxImage();
                currentDirection = Direction.LEFT;
                chooseSprite();
            }

            for (Player bomber : Level.bombers) {
                collisionChecker.Kill(bomber);
            }
        } else {
            if (renderDeadImageTime > 20) {
                this.img = SpriteContainer.deadJelly.getFxImage();
                renderDeadImageTime--;
            } else {
                super.afterDead(Animation.jellyDeadAni);
            }
        }
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

    public void isKill() {
        if (alive) {
            Level.numberOfEnemies--;
            this.delay = 10;
            this.alive = false;
            index = 0;
            count = 0;

            currentDirection = Direction.NONE;
        }
    }

    public boolean canRemove() {
        return alive;
    }
}
