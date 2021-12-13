package uet.oop.bomberman.entities.moving.enemy;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.graphics.SpriteContainer;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;
import java.util.List;

public class Frog extends Character {
    private boolean leaked = false;
    private int renderDeadImageTime = 10;
    private final CollisionChecker collisionChecker;
    private final double speed = 4;
    private double dx, dy;
    public PathFinding AI;
    public boolean updateRequired = false;

    public Frog(double xUnit, double yUnit, Image img, Level level) {
        super(xUnit, yUnit, img, level);
        collisionChecker = new CollisionChecker(this, level);
    }

    @Override
    public void update() {

        if (AI == null) {
            AI = new PathFinding(Constants.COLUMNS * Constants.ROWS, this.level);
            for (int node = 0; node < Constants.ROWS * Constants.COLUMNS; node++) {
                AI.initNodeList(node, AI.graph);
            }
        }

        if (alive) {

            if (updateRequired) {
                randomDirection();
                updateAdjList();
                updateRequired = false;
            }

            List<Integer> path = getPath(this.getNode(), level.bombers.get(0).getNode());

            if (path.isEmpty()) {
                if ((int) (Math.random() * 20 + 1) == 1) {
                    randomDirection();
                }
                move();
            } else {
                if (path.size() > 1) {
                    getToNextNode(path.get(1));
                } else {
                    getToNextNode(level.bombers.get(0).getNode());
                }
            }
            chooseSprite();

            updateHitBox();

            for (Player bomber : level.bombers) {
                if (this.hitBox.getBoundsInParent().intersects(bomber.hitBox.getBoundsInParent())) {
                    if (!leaked) {
                        if (bomber.speed > Constants.SPEED) {
                            bomber.speed -= -1;
                        } else if (bomber.bombNum > 1) {
                            bomber.bombNum--;
                        } else if (bomber.bombRange > 1) {
                            bomber.bombRange--;
                        }
                        isKill();
                        level.numberOfEnemies--;
                        leaked = true;
                    }
                }
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

    private void updateHitBox() {
        this.hitBox.setX(this.x + Constants.HGAP);
        this.hitBox.setY(this.y + Constants.VGAP);
    }

    private void getToNextNode(int targetNode) {

        int targetX = (int) (AI.toCol(targetNode) * Constants.TILES_SIZE);
        int targetY = (int) (AI.toRow(targetNode) * Constants.TILES_SIZE);

        moveToX(targetX);
        moveToY(targetY);

        if (dx != 0) {
            if (dx > 0) { currentDirection = Direction.RIGHT;}
            if (dx < 0) { currentDirection = Direction.LEFT;}

            if (!collisionChecker.isBlocked(this)) {
                if (!(this.getXUnit() == AI.toCol(targetNode)) || this.x != targetX) {
                    x += dx;
                    return;
                }
            }
        }

        if (dy != 0) {
            if (dy > 0) { currentDirection = Direction.DOWN;}
            if (dy < 0) { currentDirection = Direction.UP;}

            if (!collisionChecker.isBlocked(this)) {
                if (!(this.getYUnit() == AI.toRow(targetNode)) || this.y != targetY) {
                    y += dy;
                }
            }
        }
    }

    private void moveToY(int targetY) {
        if (targetY > this.y) {
            dy = speed;
            currentDirection = Direction.DOWN;
        } else {
            dy = -speed;
            currentDirection = Direction.UP;
        }
    }

    private void moveToX(int targetX) {
        if (targetX > this.x) {
            dx = speed;
            currentDirection = Direction.RIGHT;
        } else {
            dx = -speed;
            currentDirection = Direction.LEFT;
        }
    }

    private void randomDirection() {
        int random = (int) (Math.random() * 4 + 1);

        switch (random) {
            case 1 -> {
                dx = -speed;
                dy = 0;
                currentDirection = Direction.LEFT;
            }
            case 2 -> {
                dx = speed;
                dy = 0;
                currentDirection = Direction.RIGHT;
            }
            case 3 -> {
                dy = -speed;
                dx = 0;
                currentDirection = Direction.UP;
            }
            case 4 -> {
                dy = speed;
                dx = 0;
                currentDirection = Direction.DOWN;
            }
        }
    }

    private void move() {
        if (!collisionChecker.isBlocked(this)) {
            x += dx;
        }

        if (!collisionChecker.isBlocked(this)) {
            y += dy;
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
            case LEFT, UP -> this.img = Animation.frogLeftAni.get(index).getFxImage();
            case RIGHT, DOWN -> this.img = Animation.frogRightAni.get(index).getFxImage();
        }
    }


    public void isKill() {
        if (alive) {
            level.points -= 100;
            level.numberOfEnemies--;
            this.delay = 10;
            this.alive = false;
            index = 0;
            count = 0;

            currentDirection = Direction.NONE;
        }
    }

    /**
     * PathFinding Method
     *
     * @param start start Node
     * @param end   target Node
     * @return Shortest Path from start to end
     */
    public List<Integer> getPath(int start, int end) {
        return AI.reconstructPath(start, end);
    }

    public void setUpdateRequired() {
        updateRequired = true;
    }

    public void updateAdjList() {
        System.gc();
        AI.graph.clear();
        AI.graph = AI.createEmptyGraph(Constants.ROWS * Constants.COLUMNS);
        for (int node = 0; node < Constants.ROWS * Constants.COLUMNS; node++) {
            AI.initNodeList(node, AI.graph);
        }
    }

    public void showPath(int start, int end) {
        List<Integer> path = AI.reconstructPath(start, end);
        if (!path.isEmpty()) {
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
                level.gc.fillRect(col * Constants.TILES_SIZE + 5, row * Constants.TILES_SIZE + 5,
                        Constants.TILES_SIZE - 20, Constants.TILES_SIZE - 20);
            }

//            System.out.printf("The shortest path from %d to %d is: [%s]\n", start, end, AI.formatPath(path));
        }
    }
}
