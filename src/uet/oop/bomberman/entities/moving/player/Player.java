package uet.oop.bomberman.entities.moving.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.enemy.Bat;
import uet.oop.bomberman.entities.moving.enemy.Ghost;
import uet.oop.bomberman.entities.moving.enemy.Skelly;
import uet.oop.bomberman.entities.still.bomb.Bomb;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;
import uet.oop.bomberman.graphics.SpriteContainer;

public class Player extends Character {

    //    private List<Bomb> bombs;
    boolean isMoving;
    boolean up, down, left, right;
    private double dx, dy;
    private double lastX = -1, lastY = -1;

    public final CollisionChecker collisionChecker;

    public int bombNum = 3;
    public int bombRange = 3;

    public Player(double x, double y, Image img, Level level) {
        super(x, y, img, level);
        this.collisionChecker = new CollisionChecker(this, level);
        alive = true;

        level.levelScene.setOnKeyPressed(keyEvent -> {
            resetTracking();
            switch (keyEvent.getCode()) {
                case A -> {
                    left = true;
                    currentDirection = Direction.LEFT;
                }
                case Q -> {
                    level.screenController.setCurrentScene(level.screenController.loadingScene);
                    System.out.println("Pressed");
                }
                case D -> {
                    right = true;
                    currentDirection = Direction.RIGHT;
                }
                case W -> {
                    up = true;
                    currentDirection = Direction.UP;
                }
                case S -> {
                    down = true;
                    currentDirection = Direction.DOWN;
                }
                case SPACE -> {
                    if (level.bombs.size() < bombNum) {
                        Bomb bomb = new Bomb(Math.round(this.x / Constants.TILES_SIZE)
                                , Math.round(this.y / Constants.TILES_SIZE)
                                , SpriteContainer.Bomb.getFxImage(), this, this.level);
                        if (!(Math.round(bomb.x / Constants.TILES_SIZE) == lastX) ||
                                !(Math.round(bomb.y / Constants.TILES_SIZE) == lastY)) {

                            sound.setFile("Placing");
                            sound.play();

                            // Update List for chasing
                            level.bombs.add(bomb);
                            level.tileMap[bomb.getYUnit()][bomb.getXUnit()] = "b";
                            level.bats.forEach(Bat::setUpdateRequired);

                            lastX = Math.round(this.x / Constants.TILES_SIZE);
                            lastY = Math.round(this.y / Constants.TILES_SIZE);
                        }
                    }

                    if (currentDirection != Direction.NONE) {
                        keepMoving();
                        isMoving = true;
                    }
                }
                case ESCAPE -> {
                    level.pause();
                }
                default -> {
                    isMoving = false;
                    currentDirection = Direction.NONE;
                }
                case SHIFT -> {
                    for (Bat bat : level.bats) {
                        bat.isKill();
                    }

                    for (Skelly bat : level.skellies) {
                        bat.isKill();
                    }

                    for (Ghost ghost : level.ghosts) {
                        ghost.isKill();
                    }
                }
            }
        });

        level.levelScene.setOnKeyReleased(keyEvent -> {
            switch (keyEvent.getCode()) {
                case A -> {
                    left = false;
                    this.img = SpriteContainer.player_left.getFxImage();
                }
                case D -> {
                    right = false;
                    this.img = SpriteContainer.player_right.getFxImage();
                }
                case W -> {
                    up = false;
                    this.img = SpriteContainer.player_up.getFxImage();
                }
                case S -> {
                    down = false;
                    this.img = SpriteContainer.player_down.getFxImage();
                }
            }
        });
    }

    /**
     * Fix Can't Move While Placing Bomb.
     */
    private void keepMoving() {
        switch (currentDirection) {
            case DOWN -> down = true;
            case UP -> up = true;
            case LEFT -> left = true;
            case RIGHT -> right = true;
        }
    }

    private void resetTracking() {
        left = false;
        right = false;
        down = false;
        up = false;
    }

    private void resetBombTracking() {
        if ((Math.round(this.x / Constants.TILES_SIZE) != lastX)
                || (Math.round(this.y / Constants.TILES_SIZE) != lastY)) {
            lastX = -1;
            lastY = -1;
        }
    }

    @Override
    public void update() {

        if (alive) {

            /*
            Update Hitbox
             */

            hitBox.setX(x + Constants.HGAP);
            hitBox.setY(y + Constants.VGAP);
            hitBox.setWidth(Constants.SOLID_AREA_WIDTH);
            hitBox.setHeight(Constants.SOLID_AREA_HEIGHT);

            resetBombTracking();

            calculateMove();

            if (dx != 0 || dy != 0) {
                isMoving = true;
                move();
            } else {
                currentDirection = Direction.NONE;
                isMoving = false;
            }
        } else {
            super.afterDead(Animation.deadAni);
        }
    }

    private void calculateMove() {
        dx = 0;
        dy = 0;
        if (up) dy = -speed;
        if (down) dy = speed;
        if (left) dx = -speed;
        if (right) dx = speed;
    }

    private void move() {

        /*
          Calculate Frames for animation;
         */

        if (!collisionChecker.isBlocked(this)) {
            if (currentDirection == Direction.LEFT || currentDirection == Direction.RIGHT) {
                x += dx;
            }
            if (currentDirection == Direction.DOWN || currentDirection == Direction.UP) {
                y += dy;
            }
        }
        if (isMoving) {
            chooseSprite();
        }
    }

    public void isKill() {
        if (alive) {
            this.delay = 10;
            this.alive = false;
            index = 0;
            count = 0;

            currentDirection = Direction.NONE;
            resetTracking();

            //Remove KeyHandler;
            level.levelScene.setOnKeyReleased(keyEvent -> {

            });
        }
    }

    private void chooseSprite() {

        /*
        Calculated which frame gonna be displayed
         */
        count++;

        if (count == delay) {

            index++;
            if (index == 2) {
                sound.setFile("Walking");
                sound.play();
            }
            count = 0;
        }

        if (index == 3) {
            index = 0;
        }

        if (!alive) {
            this.img = Animation.deadAni.get(index).getFxImage();
        } else {
            switch (currentDirection) {
                case UP -> this.img = Animation.upAni.get(index).getFxImage();
                case DOWN -> this.img = Animation.downAni.get(index).getFxImage();
                case LEFT -> this.img = Animation.leftAni.get(index).getFxImage();
                case RIGHT -> this.img = Animation.rightAni.get(index).getFxImage();
            }
        }
    }

    public void render(GraphicsContext gc) {
        super.render(gc);
        collisionChecker.checkHitbox(this);
    }
}
