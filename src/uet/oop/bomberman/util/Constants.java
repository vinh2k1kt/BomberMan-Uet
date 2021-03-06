package uet.oop.bomberman.util;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Constants {

    public static String[] levels = {"res/levels/Level1.txt", "res/levels/Level2.txt", "res/levels/Level3.txt"
            , "res/levels/Level4.txt", "res/levels/Level5.txt"};
    public static HashMap<Integer, String> soundTrack = new HashMap<>();
    public static ArrayList<String> levelPath = new ArrayList<>(Arrays.asList(levels));

    /**
     * Screen and Stuff
     */
    public static final String GAME_TILE = "BomberMan";
    public static final double ORIGINAL_TILES_SIZE = 12;
    public static final double SCALE = 3;
    public static final double TILES_SIZE = ORIGINAL_TILES_SIZE * SCALE;

    public static int COLUMNS = 31;
    public static int ROWS = 13;

    public static double SCREEN_WIDTH = TILES_SIZE * COLUMNS;
    public static double SCREEN_HEIGHT = TILES_SIZE * ROWS;

    public static boolean isHitBoxShow = false;
    public static boolean isTileHitBoxShow = false;
    public static Color HITBOX_COLOR = Color.TRANSPARENT;
    public static Color PREDICTING_HITBOX_COLOR = Color.TRANSPARENT;
    public static Color TILE_HITBOX_COLOR = Color.TRANSPARENT;

    /**
     * Sprite and animation
     */
    public static final int SPRITE_COL = 26;
    public static final int SPRITE_ROW = 14;

    public static final int FRAME_NUM = 4;


    /**
     * Player
     */
    public static final double SPEED = 2;
    public static final double VGAP = 8;
    public static final double HGAP = 8;
    public static final double SOLID_AREA_WIDTH = TILES_SIZE - HGAP * 3;
    public static final double SOLID_AREA_HEIGHT = TILES_SIZE - VGAP - 2;
    public static final double MAX_BOMB_NUM = 5;
    public static final double MAX_BOMB_RANGE = 5;
    public static final double MAX_SPEED = 4;
}
