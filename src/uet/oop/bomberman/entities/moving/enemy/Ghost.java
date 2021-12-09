package uet.oop.bomberman.entities.moving.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;

public class Ghost extends Character {

    private int renderDeadImageTime  = 10;
    private final CollisionChecker collisionChecker;
    private final double speed = 2;
    private double dx, dy;

    public Ghost(int xUnit, int yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        this.currentDirection = Direction.RIGHT;
        this.dx = speed;
        this.level = level;
        collisionChecker = new CollisionChecker(this, level);
    }

    @Override
    public void update() {
        if (alive) {
            if (!collisionChecker.isBlocked(this)) {

                if (x < 0 || x > Constants.SCREEN_WIDTH) {
                    dx = -dx;
                }

                if (y < 0 || y > Constants.SCREEN_HEIGHT) {
                    dy = -dy;
                }

                x += dx;
                y += dy;
                if ((int) (Math.random() * 30 + 1) == 1 ) {
                    randomDirection();
                }
                chooseSprite();
            }
            else {
                randomDirection();
            }

            for (Player bomber : level.bombers) {
                collisionChecker.Kill(bomber);
            }
        } else {
            if (renderDeadImageTime > 20) {
                this.img = SpriteContainer.deadEnemy.getFxImage();
                renderDeadImageTime--;
            } else {
                super.afterDead(Animation.mobDeadAni);
            }
        }
    }

    private void randomDirection() {
        int random = (int) (Math.random() * 4 + 1);
        switch (random) {
            case 1 -> {
                currentDirection = Direction.LEFT;
                dx = -speed;
                dy = 0;
            }
            case 2 -> {
                currentDirection = Direction.RIGHT;
                dx = speed;
                dy = 0;
            }
            case 3 -> {
                currentDirection = Direction.UP;
                dy = -speed;
                dx = 0;
            }
            case 4 -> {
                currentDirection = Direction.DOWN;
                dy = speed;
                dx = 0;
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
            case LEFT, UP -> this.img = Animation.ghostLeftAni.get(index).getFxImage();
            case RIGHT, DOWN -> this.img = Animation.ghostRightAni.get(index).getFxImage();
        }
    }

    public void isKill() {
        if (alive) {
            level.numberOfEnemies--;
            this.delay = 10;
            this.alive = false;
            index = 0;
            count = 0;

            currentDirection = Direction.NONE;
        }
    }
}
