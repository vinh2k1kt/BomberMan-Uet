package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Level;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.block.Wall;
import uet.oop.bomberman.entities.block.destroyable.Brick;
import uet.oop.bomberman.entities.moving.player.Player;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayList;

public class Flame extends Entity {

    protected int direction;
    protected ArrayList<FlameSegment> flameSegments = new ArrayList<>();
    protected Player cause = Level.bomber;

    public Flame(double xUnit, double yUnit, Image img, int direction, Player cause) {
        super(xUnit, yUnit, img);
        this.direction = direction;
        createFlameSegment();
    }

    private void createFlameSegment() {
        
        int flameLength = calculateFlameLength();

        double xUnit = x / Constants.TILES_SIZE;
        double yUnit = y / Constants.TILES_SIZE;

        boolean isLast = false;
        for (int i = 0; i < flameLength; i++) {

            isLast = i == flameLength - 1;

            switch (direction) {
                case 0 -> yUnit++;
                case 1 -> yUnit--;
                case 2 -> xUnit--;
                case 3 -> xUnit++;
            }
            flameSegments.add(new FlameSegment(xUnit, yUnit, null, direction, isLast));
        }
    }

    private int calculateFlameLength() {

        double xUnit = x / Constants.TILES_SIZE;
        double yUnit = y / Constants.TILES_SIZE;

        int length = 0;
        switch (direction) {
            case 0 -> {
                yUnit++;
                while (length < cause.bombRadius) {
                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length * Constants.COLUMNS)) instanceof Wall) {
                        return length;
                    }

                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length)) instanceof Brick) {
                        return ++length;
                    }

                    length++;
                }
            }
            case 1 -> {
                yUnit--;
                while (length < cause.bombRadius) {
                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS - length * Constants.COLUMNS)) instanceof Wall) {
                        return length;
                    }

                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length)) instanceof Brick) {
                        return ++length;
                    }

                    length++;
                }
            }
            case 2 -> {
                xUnit--;
                while (length < cause.bombRadius) {
                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS - length)) instanceof Wall) {
                        return length;
                    }

                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length)) instanceof Brick) {
                        return ++length;
                    }

                    length++;
                }
            }
            case 3 -> {
                xUnit++;
                while (length < cause.bombRadius) {
                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length)) instanceof Wall) {
                        return length;
                    }

                    if (Level.entities.get((int) (xUnit + yUnit * Constants.COLUMNS + length)) instanceof Brick) {
                        return ++length;
                    }

                    length++;
                }
            }
            case 4 -> {
                return 1;
            }
        }
        return length;
    }

    @Override
    public void update() {
        flameSegments.forEach(FlameSegment::update);
    }

    public void render(GraphicsContext gc) {
        flameSegments.forEach(fs -> fs.render(gc));
    }
}
