package uet.oop.bomberman.graphics;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import uet.oop.bomberman.dataStructure.AssetPool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite {

	public BufferedImage image;
	public Image FxImage;
	public String pictureFile;
	public int width, height;

	public boolean isSubSprite = false;
	public int row, column, index;

	public Sprite(String pictureFile) {
		this.pictureFile = pictureFile;

		try {
			File file = new File (pictureFile);

			if (AssetPool.hasSprite(pictureFile)) {
				throw new Exception("Asset already exists: " + pictureFile);
			}
			this.image = ImageIO.read(file);
			this.width = image.getWidth();
			this.height= image.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public Sprite(BufferedImage image) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.FxImage = SwingFXUtils.toFXImage(image, null);
	}

	public Sprite(BufferedImage image, int row, int column, int index) {
		this.image = image;
		this.width = image.getWidth();
		this.height = image.getHeight();
		this.row = row;
		this.column = column;
		this.index = index;
		this.isSubSprite = true;
		this.FxImage = SwingFXUtils.toFXImage(image, null);
	}

	public Image getFxImage() {
		return FxImage;
	}
}

