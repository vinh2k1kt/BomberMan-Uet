package uet.oop.bomberman.entities.moving.enemy;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;

import java.util.List;

public class Jelly extends Character {
    private int renderDeadImageTime  = 10;
    private final CollisionChecker collisionChecker;
    private final double speed = 0;
    private double dx, dy;
    private final Level level;
    private PathFinding AI;

    public Jelly(int xUnit, int yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        this.currentDirection = Direction.RIGHT;
        this.dx = speed;
        this.level = level;
        collisionChecker = new CollisionChecker(this, level);
    }

    @Override
    public void update() {
        if (AI == null) {
            AI = new PathFinding(Constants.COLUMNS * Constants.ROWS, this.level);
            for (int node = 0; node < Constants.ROWS * Constants.COLUMNS; node++) {
                AI.checkNode(node, AI.graph);
            }
        }
        if (alive) {
            if (!collisionChecker.isBlocked(this)) {
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

    public void showPath(int start, int end) {
        List<Integer> path = AI.reconstructPath(start, end);
        if (path.isEmpty()) {
            System.out.println("wrong " + end);
        } else {
            for (Integer i : path) {
                if (i == start) {
                    level.gc.setFill(Color.YELLOW);
                } else if (i == end) {
                    level.gc.setFill(Color.BLUE);
                } else {
                    level.gc.setFill(Color.BLACK);
                }
                int col, row;
                if (i % Constants.COLUMNS == 0) {
                    i--;
                }
                row = i / Constants.COLUMNS;
                col = i - row * Constants.COLUMNS;
                level.gc.fillRect( col * Constants.TILES_SIZE, row * Constants.TILES_SIZE,
                        Constants.TILES_SIZE - 5, Constants.TILES_SIZE - 5);
            }
//            for (Edge edge : AI.graph.get(64)) {
//                System.out.println(edge.from + " " + edge.to);
//            }
            System.out.printf("The shortest path from %d to %d is: [%s]\n", start, end, AI.formatPath(path));
//            level.timer.stop();
            // The shortest path from 43 to 95 is: [43 -> 42 -> 41 -> 40 -> 39 -> 38 -> 37 -> 36 -> 35 -> 34 -> 33 -> 64 -> 95]
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
            case LEFT, UP -> this.img = Animation.skellyLeftAni.get(index).getFxImage();
            case RIGHT, DOWN -> this.img = Animation.skellyRightAni.get(index).getFxImage();
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
