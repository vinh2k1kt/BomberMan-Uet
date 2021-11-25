package uet.oop.bomberman.util;

import javafx.scene.paint.Color;

public class Constants {

    /**
     * Screen and Stuff
     */
    public static final String GAME_TILE = "BomberMan";
    public static final double ORIGINAL_TILES_SIZE = 12;
    public static final double SCALE = 3;
    public static final double TILES_SIZE = ORIGINAL_TILES_SIZE * SCALE;

    public static int COLUMNS = 31;
    public static int ROWS = 13;

    public static double SCREEN_WIDTH  = TILES_SIZE * COLUMNS;
    public static double SCREEN_HEIGHT = TILES_SIZE * ROWS;

    public static int PLAYER_SPEED = 2;

    public static final Color hitBoxColor = Color.TRANSPARENT;
    public static final Color tileHitBoxColor = Color.TRANSPARENT;

    /**
     * Sprite and animation
     */
    public static final int SPRITE_COL = 16;
    public static final int SPRITE_ROW = 12;

    public static final int FRAME_NUM = 3;

    public static final int LEFT_INDEX = 3;
    public static final int RIGHT_INDEX = 1;
    public static final int UP_INDEX = 0;
    public static final int DOWN_INDEX = 2;

    /**
     * Player
     */
    public static final double VGAP = 8;
    public static final double HGAP = 4;
    public static final double SOLID_AREA_WIDTH = TILES_SIZE - HGAP * 3;
    public static final double SOLID_AREA_HEIGHT = TILES_SIZE - VGAP - 2;

    /**
     * Enemy
     */
    public static final int ENEMY_SPEED = 2;
}
