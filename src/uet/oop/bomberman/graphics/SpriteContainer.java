package uet.oop.bomberman.graphics;

import uet.oop.bomberman.util.Constants;

public class SpriteContainer {

    /**
     * Player_Sprites
     */
    public static final Sprite player_left = SpriteSheet.sprites.get(3);
    public static final Sprite player_right = SpriteSheet.sprites.get(1);
    public static final Sprite player_up = SpriteSheet.sprites.get(0);
    public static final Sprite player_down = SpriteSheet.sprites.get(2);

    /**
     * Blocks
     */
    public static final Sprite wall = SpriteSheet.sprites.get(5);
    public static final Sprite grass = SpriteSheet.sprites.get(6);
    public static final Sprite brick = SpriteSheet.sprites.get(7);

    /**
     * Enemies
     */
    public static final Sprite Jelly = SpriteSheet.sprites.get(9);
    public static final Sprite deadJelly = SpriteSheet.sprites.get(Constants.SPRITE_COL * 3 + 9);

    /**
     * Bomb
     */
    public static final Sprite Bomb = SpriteSheet.sprites.get(Constants.SPRITE_COL * 3);

    /**
     * Items
     */
    public static final Sprite bombItem = SpriteSheet.sprites.get(Constants.SPRITE_COL * 10);
    public static final Sprite flameItem = SpriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 1);
    public static final Sprite speedItem = SpriteSheet.sprites.get(Constants.SPRITE_COL * 10 + 2);
}
