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
	//TODO: Clarify idle sprite from walking sprites
	
	private BufferedImage spriteSheet;
	private HashMap<String, BufferedImage[]> spriteSets;
	
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
		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(spriteSheetPath));
		}catch(IOException e) {
			throw e;
		}
		
		spriteSets = new HashMap<String, BufferedImage[]>();
		spriteSets.put("front", new BufferedImage[] {
				spriteSheet.getSubimage(8, 4, tileSizeX, tileSizeY)
		});
		spriteSets.put("back", new BufferedImage[] {
				spriteSheet.getSubimage(72, 4, tileSizeX, tileSizeY)
		});
		spriteSets.put("right", new BufferedImage[] {
				flipSpriteHorizontally(spriteSheet.getSubimage(42, 4, tileSizeX, tileSizeY))
		});
		spriteSets.put("left", new BufferedImage[] {
				spriteSheet.getSubimage(42, 4, tileSizeX, tileSizeY)
		});
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
		//TODO: Account for animation
		return spriteSets.get(key)[0];
	}
}
