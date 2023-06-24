package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

/**
 * Stores the sprites and animation data necessary for an entity.
 * @author tyson
 *
 */
public class SpriteSheet {
	//TODO: Store each sprite as an image instead of recalculating every time.
	
	private BufferedImage spriteSheet;
	private HashMap<String, int[]> spriteMap;
	
	/**
	 * Generates a SpriteSheet from the image path passed. Assumes the tiles are exact squares.
	 * @param spriteSheetPath Path to the sprite sheet
	 * @param tileSize Width/Height of the sprite tiles
	 */
	public SpriteSheet(String spriteSheetPath, int tileSize) throws IOException{
		this(spriteSheetPath, tileSize, tileSize);
	}

	/**
	 * Generates a SpriteSheet from the image path passed.
	 * @param spriteSheetPath Path to the sprite sheet
	 * @param tileSizeX Width of the tiles
	 * @param tileSizeY Height of the tiles
	 */
	public SpriteSheet(String spriteSheetPath, int tileSizeX, int tileSizeY) throws IOException{
		spriteMap = new HashMap<String, int[]>();
		try {
			this.spriteSheet = ImageIO.read(getClass().getResourceAsStream(spriteSheetPath));
		}catch(IOException e) {
			throw e;
		}
		spriteMap.put("front", new int[]{8, 4, tileSizeX, tileSizeY});
		spriteMap.put("back", new int[]{72, 4, tileSizeX, tileSizeY});
		spriteMap.put("right", new int[]{42, 4, tileSizeX, tileSizeY});
		spriteMap.put("left", new int[]{42, 4, tileSizeX, tileSizeY});
	}
	
	/**
	 * Flips the sprite's image in the horizontal direction
	 * @param img The image to flip
	 * @return A BufferedImage of the flipped sprite.
	 */
	private BufferedImage flipSpriteHorizontally(BufferedImage img) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(-1, 1));
		at.concatenate(AffineTransform.getTranslateInstance(-img.getWidth(), 0));
		BufferedImage flippedSprite = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = flippedSprite.createGraphics();
		g.transform(at);
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return flippedSprite;
	}
	
	/**
	 * Obtains the sprite of the corresponding key that is passed.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSprite(String key) {
		BufferedImage image = this.spriteSheet.getSubimage(spriteMap.get(key)[0], spriteMap.get(key)[1], spriteMap.get(key)[2], spriteMap.get(key)[3]);
		if(key == "right") {
			image = flipSpriteHorizontally(image);
		}
		return image;
	}
}
