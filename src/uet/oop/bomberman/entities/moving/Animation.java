package uet.oop.bomberman.entities.moving;

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
    public static void initAnimation() {
        for (int i = 0; i < Constants.FRAME_NUM; i++) {

            leftAni.add(SpriteSheet.sprites.get(Constants.LEFT_INDEX + i * Constants.SPRITE_COL));
            rightAni.add(SpriteSheet.sprites.get(Constants.RIGHT_INDEX + i * Constants.SPRITE_COL));
            upAni.add(SpriteSheet.sprites.get(Constants.UP_INDEX + i * Constants.SPRITE_COL));
            downAni.add(SpriteSheet.sprites.get(Constants.DOWN_INDEX + i * Constants.SPRITE_COL));
            deadAni.add(SpriteSheet.sprites.get(Constants.SPRITE_COL * 2 + 4 + i));

            jellyLeftAni.add(SpriteSheet.sprites.get(9 + i * Constants.SPRITE_COL));
            jellyRightAni.add(SpriteSheet.sprites.get(10 + i * Constants.SPRITE_COL));

            bombAni.add(SpriteSheet.sprites.get(Constants.SPRITE_COL * 3 + i));
        }
    }
}