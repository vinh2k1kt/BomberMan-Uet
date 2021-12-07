package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
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
    Enemies
     */
    public static ArrayList<Sprite> skellyLeftAni = new ArrayList<>();
    public static ArrayList<Sprite> skellyRightAni = new ArrayList<>();
    public static ArrayList<Sprite> ghostLeftAni = new ArrayList<>();
    public static ArrayList<Sprite> ghostRightAni = new ArrayList<>();
    public static ArrayList<Sprite> frogLeftAni = new ArrayList<>();
    public static ArrayList<Sprite> frogRightAni = new ArrayList<>();
    public static ArrayList<Sprite> batLeftAni = new ArrayList<>();
    public static ArrayList<Sprite> batRightAni = new ArrayList<>();
    public static ArrayList<Sprite> mobDeadAni = new ArrayList<>();

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

        //Titan Sprites
        for (int i = 0; i < Constants.FRAME_NUM; i++) {

            //Player
            leftAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + i));
            rightAni.add(SpriteSheet.spriteSheet.sprites.get(i));
            upAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL + i));
            downAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + i));
            deadAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + (9 + i)));

            //Skelly
            skellyLeftAni.add(SpriteSheet.spriteSheet.sprites.get(9 + i));
            skellyRightAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL + (9 + i)));
            mobDeadAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 6 - 4 + i));

            //Frog
            frogLeftAni.add(SpriteSheet.spriteSheet.sprites.get(5 + i));
            frogRightAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL + 5 + i));

            //Bat
            batLeftAni.add(SpriteSheet.spriteSheet.sprites.get(14 + i));
            batRightAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL + 14 + i));
            //Tile
            brick_explosion.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 4 - 4 + i));
        }

        //Classic Sprites
        for (int i = 0; i < 3; i++) {
            //Bomb & Flame
            bombAni.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + (10 + i)));

            explosion_center.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 3 + (15 + i)));


            explosion_vertical_top.add(SpriteSheet.spriteSheet.sprites.get(17 + i));
            explosion_vertical_middle.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL + 17 + i));
            explosion_vertical_bottom.add(SpriteSheet.spriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 17 + i));

            explosion_horizontal_left.add(SpriteSheet.spriteSheet.sprites.get(20 + i * Constants.SPRITE_COL));
            explosion_horizontal_middle.add(SpriteSheet.spriteSheet.sprites.get(21 + i * Constants.SPRITE_COL));
            explosion_horizontal_right.add(SpriteSheet.spriteSheet.sprites.get(22 + i * Constants.SPRITE_COL));
        }
    }

    public static void testAni(GraphicsContext gc, ArrayList<Sprite> ani, int row) {
        int count = 0;
        for (Sprite sprite : ani) {
            gc.drawImage(sprite.getFxImage(), count * Constants.TILES_SIZE, row * Constants.TILES_SIZE
                    , Constants.TILES_SIZE, Constants.TILES_SIZE);
            count++;
        }
    }
}