package br.com.os.sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/** Represents a whole sprite sheet. */
public class SpriteSheet {
	
	public BufferedImage spriteSheet;
	
	/** Builds a sprite sheet.
	 * @param The number of the sprite sheet file. E.G. spriteSheetNumber = 1 means loading the
	 * image spriteSheet1.png. */
	public SpriteSheet(int spriteSheetNumber) {
		this.spriteSheet = BufferedImageLoader.loadImage("spriteSheet" + spriteSheetNumber + ".png");
	}
	
	/** Clip a spritesheet in a given region.
	 * @param coordinate The coordinate in which to clip the sprite sheet.
	 * @return A BufferedImage representing the clip the the given region. */
	private BufferedImage clipSprite(SpriteSheetCooordinate coordinate) {
		BufferedImage sprite = this.spriteSheet.getSubimage(coordinate.getX(),
				coordinate.getY(),coordinate.getWidth(), coordinate.getHeight());
		return sprite;
	}
	
	/** Clips this sprite sheet in a lot of regions.
	 * @param coordinates The coordinates in which to clip this sprite sheet.
	 * @return An ArrayList of BufferedImage containing the resulting sprites. */
	public ArrayList<BufferedImage> getSpritesWithCoordinates(SpriteSheetCooordinate... coordinates) {
		ArrayList<BufferedImage> sprites = new ArrayList<BufferedImage>();
		for(SpriteSheetCooordinate coordinate : coordinates) {
			sprites.add(this.clipSprite(coordinate));
		}
		return sprites;
		
	}
	
}
