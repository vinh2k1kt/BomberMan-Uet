package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.util.Constants;

public abstract class Entity {

        public Rectangle hitBox = new Rectangle();

        //Tọa độ X tính từ góc trái trên trong Canvas
        protected double x;

        //Tọa độ Y tính từ góc trái trên trong Canvas
        protected double y;

        protected Image img;

        //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
        public Entity( double xUnit, double yUnit, Image img) {
            this.x = xUnit * Constants.TILES_SIZE;
            this.y = yUnit * Constants.TILES_SIZE;
            this.img = img;
        }

        public void render(GraphicsContext gc) {
            gc.drawImage(img, x, y, Constants.TILES_SIZE, Constants.TILES_SIZE);

//            Hit box check
//            gc.setFill(Color.BLACK);
//            gc.fillRect(hitBox.getX(), hitBox.getY(), hitBox.getWidth(), hitBox.getHeight());
        }
        public abstract void update();
    }
