package uet.oop.bomberman.entities.still.bomb;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.entities.still.Tile;
import uet.oop.bomberman.graphics.Animation;
import uet.oop.bomberman.entities.still.block.destroyable.Brick;
import uet.oop.bomberman.entities.moving.enemy.Jelly;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayList;

public class FlameSegment extends Tile {

    protected boolean isLast;

    private int count;
    private int index;
    private ArrayList<Sprite> ani = new ArrayList<>();
    public FlameSegment(double xUnit, double yUnit, Image img, int direction, boolean isLast) {
        super(xUnit, yUnit, img);
        this.isLast = isLast;
        this.hitBox = new Rectangle(x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);

        switch (direction) {
            case 0 -> {
                if (isLast) {
                    this.img = Animation.explosion_vertical_bottom.get(0).getFxImage();
                    ani = Animation.explosion_vertical_bottom;
                } else {
                    this.img = Animation.explosion_vertical_middle.get(0).getFxImage();
                    ani = Animation.explosion_vertical_middle;
                }
            }
            case 1 -> {
                if (isLast) {
                    this.img = Animation.explosion_vertical_top.get(0).getFxImage();
                    ani = Animation.explosion_vertical_top;
                } else {
                    this.img = Animation.explosion_vertical_middle.get(0).getFxImage();
                    ani = Animation.explosion_vertical_middle;
                }
            }
            case 2 -> {
                if (isLast) {
                    this.img = Animation.explosion_horizontal_left.get(0).getFxImage();
                    ani = Animation.explosion_horizontal_left;
                } else {
                    this.img = Animation.explosion_horizontal_middle.get(0).getFxImage();
                    ani = Animation.explosion_horizontal_middle;
                }
            }
            case 3 -> {
                if (isLast) {
                    this.img = Animation.explosion_horizontal_right.get(0).getFxImage();
                    ani = Animation.explosion_horizontal_right;
                } else {
                    this.img = Animation.explosion_horizontal_middle.get(0).getFxImage();
                    ani = Animation.explosion_horizontal_middle;
                }
            }
            case 4 -> {
                this.img = Animation.explosion_center.get(0).getFxImage();
                ani = Animation.explosion_center;
            }
        }
    }

    public void chooseSprite() {
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
            return;
        }

        this.img = ani.get(index).getFxImage();
    }

    public void collided() {

        for (Bomb bomb : Level.bombs) {
            if (this.hitBox.getBoundsInParent().intersects(bomb.hitBox.getBoundsInParent())) {
                bomb.explodedImdiately();
            }
        }

        for (Player bomber : Level.bombers) {
            if (this.hitBox.getBoundsInParent().intersects(bomber.hitBox.getBoundsInParent())) {
                bomber.isKill();
            }
        }

        for (Jelly jelly : Level.jellies) {
            if (this.hitBox.getBoundsInParent().intersects(jelly.hitBox.getBoundsInParent())) {
                jelly.isKill();
            }
        }

        if (Level.tiles.get((int) (x / Constants.TILES_SIZE +  (y / Constants.TILES_SIZE) * Constants.COLUMNS))
                instanceof Brick)  {
            ((Brick) Level.tiles.get((int) (x / Constants.TILES_SIZE +  (y / Constants.TILES_SIZE) * Constants.COLUMNS))).destroy();
        }
    }
    @Override
    public void update() {
        collided();
        chooseSprite();
    }
}
