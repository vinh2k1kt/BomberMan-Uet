package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.sound.Sound;
import uet.oop.bomberman.util.Constants;

public abstract class Entity {

    public Rectangle hitBox = new Rectangle();

    public Level level;

    //Tọa độ X tính từ góc trái trên trong Canvas
    public double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    public double y;
    public Sound sound;
    public Image img;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img, Level level) {
        this.sound = new Sound();
        this.x = xUnit * Constants.TILES_SIZE;
        this.y = yUnit * Constants.TILES_SIZE;
        this.img = img;
        this.level = level;
    }

    public int getXUnit() {
        return (int) Math.round(this.x / Constants.TILES_SIZE);
    }

    public int getYUnit() {
        return (int) Math.round(this.y / Constants.TILES_SIZE);
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);
    }

    public abstract void update();
}
