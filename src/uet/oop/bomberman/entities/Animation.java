package uet.oop.bomberman.entities;

import uet.oop.bomberman.Level;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.SpriteSheet;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayList;

public class Animation {

    /*
    Player Animations
     */
    public static ArrayList<Sprite> leftAni = new ArrayList<>();
    public static ArrayList<Sprite> rightAni = new ArrayList<>();
    public static ArrayList<Sprite> upAni = new ArrayList<>();
    public static ArrayList<Sprite> downAni = new ArrayList<>();

    public static ArrayList<Sprite> deadAni = new ArrayList<>();

    /*
    Jelly Animation
     */
    public static ArrayList<Sprite> jellyLeftAni = new ArrayList<>();
    public static ArrayList<Sprite> jellyRightAni = new ArrayList<>();

    /*
    Bomb Animation
     */
    public static ArrayList<Sprite> bombAni = new ArrayList<>();
    public static ArrayList<Sprite> explosion_center = new ArrayList<>();
    public static ArrayList<Sprite> explosion_vertical_top = new ArrayList<>();
    public static ArrayList<Sprite> explosion_vertical_middle = new ArrayList<>();
    public static ArrayList<Sprite> explosion_vertical_bottom = new ArrayList<>();
    public static ArrayList<Sprite> explosion_horizontal_left = new ArrayList<>();
    public static ArrayList<Sprite> explosion_horizontal_middle = new ArrayList<>();
    public static ArrayList<Sprite> explosion_horizontal_right = new ArrayList<>();

    /*
    Tile Animation
     */
    public static ArrayList<Sprite> brick_explosion = new ArrayList<>();

    public static void initAnimation() {
        for (int i = 0; i < Constants.FRAME_NUM; i++) {

            //Player
            leftAni.add(SpriteSheet.sprites.get(Constants.LEFT_INDEX + i * Constants.SPRITE_COL));
            rightAni.add(SpriteSheet.sprites.get(Constants.RIGHT_INDEX + i * Constants.SPRITE_COL));
            upAni.add(SpriteSheet.sprites.get(Constants.UP_INDEX + i * Constants.SPRITE_COL));
            downAni.add(SpriteSheet.sprites.get(Constants.DOWN_INDEX + i * Constants.SPRITE_COL));
            deadAni.add(SpriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 4 + i));

            //Jelly
            jellyLeftAni.add(SpriteSheet.sprites.get(9 + i * Constants.SPRITE_COL));
            jellyRightAni.add(SpriteSheet.sprites.get(10 + i * Constants.SPRITE_COL));

            //Bomb & Flame
            bombAni.add(SpriteSheet.sprites.get(Constants.SPRITE_COL * 3 + i));

            explosion_center.add(SpriteSheet.sprites.get(6 * Constants.SPRITE_COL - i * Constants.SPRITE_COL));

            explosion_vertical_top.add(SpriteSheet.sprites.get(4 * Constants.SPRITE_COL - i + 3));
            explosion_vertical_middle.add(SpriteSheet.sprites.get(5 * Constants.SPRITE_COL - i + 3));
            explosion_vertical_bottom.add(SpriteSheet.sprites.get(6 * Constants.SPRITE_COL - i + 3));

            explosion_horizontal_left.add(SpriteSheet.sprites.get(9 * Constants.SPRITE_COL - i * Constants.SPRITE_COL));
            explosion_horizontal_middle.add(SpriteSheet.sprites.get(9 * Constants.SPRITE_COL - i * Constants.SPRITE_COL + 1));
            explosion_horizontal_right.add(SpriteSheet.sprites.get(9 * Constants.SPRITE_COL - i * Constants.SPRITE_COL + 2));

            //Tile
            brick_explosion.add(SpriteSheet.sprites.get(7 + (i + 1) * Constants.SPRITE_COL));
        }
    }

    public static void testAni(ArrayList<Sprite> ani) {
        int count = 0;
        for (Sprite sprite : ani) {
            Level.gc.drawImage(sprite.getFxImage(), count * Constants.TILES_SIZE, 0
            , Constants.TILES_SIZE, Constants.TILES_SIZE);
            count++;
        }
    }
}