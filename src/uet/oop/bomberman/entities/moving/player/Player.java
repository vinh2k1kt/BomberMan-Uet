package uet.oop.bomberman.entities.moving.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Animation;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Item.bombItem;
import uet.oop.bomberman.entities.Item.speedItem;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.moving.Character;
import uet.oop.bomberman.entities.moving.CollisionChecker;
import uet.oop.bomberman.util.Constants;
import uet.oop.bomberman.util.Direction;
import uet.oop.bomberman.util.SpriteContainer;

import static uet.oop.bomberman.util.Constants.PLAYER_SPEED;

public class Player extends Character {

    //    private List<Bomb> bombs;
    boolean isMoving;
    boolean up, down, left, right;

    private int count = 0;
    private int index = 0;
    private int dx, dy;

    private final CollisionChecker collisionChecker = new CollisionChecker(Level.levelScene, this);

    public int bombNum = 1;
    public int bombRadius = 1;
    public boolean alive;

    public Player(double x, double y, Image img) {
        super(x, y, img);
        alive = true;

        Level.levelScene.setOnKeyPressed(keyEvent -> {
            resetTracking();
            switch (keyEvent.getCode()) {
                case A -> {
                    left = true;
                    currentDirection = Direction.LEFT;
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
                    if (Level.bombs.size() < bombNum) {
                        Level.bombs.add(new Bomb(Math.round(this.x / Constants.TILES_SIZE)
                                , Math.round(this.y / Constants.TILES_SIZE)
                                , SpriteContainer.Bomb.getFxImage(), this));
                    }
                    if (currentDirection != Direction.NONE) {
                        keepMoving();
                        isMoving = true;
                    }
                }
                default -> {
                    isMoving = false;
                    currentDirection = Direction.NONE;
                }
            }
        });

        Level.levelScene.setOnKeyReleased(keyEvent -> {
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


    @Override
    public void update() {

        /*
        Update hitBox
         */

        if (alive) {
            hitBox.setX(x + 4);
            hitBox.setY(y + 4);
            hitBox.setWidth(Constants.TILES_SIZE - 12);
            hitBox.setHeight(Constants.TILES_SIZE - 8);

            calculateMove();

            if (dx != 0 || dy != 0) {
                isMoving = true;
                move();
            } else {
                currentDirection = Direction.NONE;
                isMoving = false;
            }
        }
    }

    private void calculateMove() {
        dx = 0;
        dy = 0;
        if (up) dy = -PLAYER_SPEED;
        if (down) dy = PLAYER_SPEED;
        if (left) dx = -PLAYER_SPEED;
        if (right) dx = PLAYER_SPEED;
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

    public void isKill() {
        index = 0;
        count = 0;

        currentDirection = Direction.NONE;
        resetTracking();
        alive = false;
        while (index < Animation.deadAni.size() - 1) {
            chooseSprite();
        }

        //Remove KeyHandler;
        Level.levelScene.setOnKeyReleased(keyEvent -> {

        });
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(this.img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);

//        Hit box check
        gc.setFill(Constants.hitBoxColor);
        gc.fillRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
    }

    public void pickUpItem(Entity e) {
        if(e instanceof bombItem) {
            bombNum++;
        }
        if(e instanceof speedItem) {
            PLAYER_SPEED = PLAYER_SPEED*2;
        }
        //if(e instanceof flameItem) {

        //}
    }
}
