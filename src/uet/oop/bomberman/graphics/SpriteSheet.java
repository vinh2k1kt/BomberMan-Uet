package uet.oop.bomberman.graphics;

import uet.oop.bomberman.dataStructure.AssetPool;
import uet.oop.bomberman.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

	public static SpriteSheet spriteSheet = new SpriteSheet("res/SpriteSheet_Final.png",
			16, 16, 0, Constants.SPRITE_COL, Constants.SPRITE_COL * Constants.SPRITE_ROW);

	public List<Sprite> sprites;
	public int tileWidth;
	public int tileHeight;
	public int spacing;
	public SpriteSheet(String pictureFile, int tileWidth, int tileHeight, int spacing, int columns, int size) {
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		this.spacing = spacing;

		Sprite parent = AssetPool.getSprite(pictureFile);
		sprites = new ArrayList<>();
		int row = 0;
		int count = 0;
		while (count < size) {
			for (int column = 0; column < columns; column++)
			{
				int imgX = (column * tileWidth) + (column * spacing);
				int imgY = (row * tileHeight) + (row * spacing);

				sprites.add(new Sprite(parent.image.getSubimage(imgX, imgY, tileWidth, tileHeight),
						row, column, count));
				count++;
				if (count > size - 1) {
					break;
				}
			}
			row++;
		}
	}
}
