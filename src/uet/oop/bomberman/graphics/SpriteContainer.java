package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.util.Constants;

public class SpriteContainer {

    /**
     * Player_Sprites
     */
    public static final Sprite player_left = Animation.leftAni.get(0);
    public static final Sprite player_right = Animation.rightAni.get(0);
    public static final Sprite player_up = Animation.upAni.get(0);
    public static final Sprite player_down = Animation.downAni.get(0);

    /**
     * Blocks
     */

    public static Sprite grass = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 1);
    public static Sprite grass1 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 2);
    public static Sprite grass2 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 6);
    public static Sprite brick = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + 13);

    public static Sprite wall = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 7);
    public static Sprite wall_top_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4);
    public static Sprite wall_top_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 + 8);
    public static Sprite wall_bottom_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7);
    public static Sprite wall_bottom_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7 + 8);
    public static Sprite wall_top_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 + 1);
    public static Sprite wall_bottom_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7 + 2);
    public static Sprite wall_left_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5);
    public static Sprite wall_right_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 8);

    public static void initGrassStage() {
        grass = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 1);
        grass1 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 2);
        grass2 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 6);
        brick = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + 13);

        wall = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 7);
        wall_top_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4);
        wall_top_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 + 8);
        wall_bottom_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7);
        wall_bottom_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7 + 8);
        wall_top_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 + 1);
        wall_bottom_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 7 + 2);
        wall_left_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5);
        wall_right_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 8);
    }

    public static void initDesertStage() {
        grass = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 1);
        grass1 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 2);
        grass2 = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 5 + 6);

        wall = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 7);
        wall_top_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 9);
        wall_top_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 9 + 8);
        wall_bottom_left_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 12);
        wall_bottom_right_corner = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 12 + 8);
        wall_top_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 9 + 1);
        wall_bottom_middle = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 12 + 2);
        wall_left_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 10);
        wall_right_side = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 8);
    }


    /**
     * Enemies
     */
    public static final Sprite deadEnemy = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 - 1);

    /**
     * Bomb
     */
    public static final Sprite Bomb = Animation.bombAni.get(0);

    /**
     * Items
     */
    public static final Sprite bombItem = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 15);
    public static final Sprite flameItem = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 13);
    public static final Sprite speedItem = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 14);
    public static final Sprite portal = SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + 9);

    public static void testSprite(GraphicsContext gc, Sprite sprite, int i) {
        gc.drawImage(sprite.getFxImage(), i*Constants.TILES_SIZE ,0, Constants.TILES_SIZE, Constants.TILES_SIZE);
    }
}
