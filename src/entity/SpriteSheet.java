package entity;

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
	private BufferedImage spriteSheet;
	private int spriteFrameNum;
	private int spriteCounter;
	private String spriteAnimName;
	private HashMap<String, SpriteAnimation> spriteData;
	
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
		spriteData = new HashMap<String, SpriteAnimation>();
		
		//Idle Animations
		spriteData.put("idleFront", new SpriteAnimation(spriteSheet, 8, 4, 1, tileSizeX, tileSizeY, false));
		spriteData.put("idleBack", new SpriteAnimation(spriteSheet, 72, 4, 1, tileSizeX, tileSizeY, false));
		spriteData.put("idleRight", new SpriteAnimation(spriteSheet, 42, 4, 1, tileSizeX, tileSizeY, true));
		spriteData.put("idleLeft", new SpriteAnimation(spriteSheet, 42, 4, 1, tileSizeX, tileSizeY, false));
		
		//Walk Animations
		spriteData.put("walkFront", new SpriteAnimation(spriteSheet, 8, 72, 10, tileSizeX, tileSizeY, false));
		spriteData.put("walkBack", new SpriteAnimation(spriteSheet, 675, 72, 10, tileSizeX, tileSizeY, false));
		spriteData.put("walkRight", new SpriteAnimation(spriteSheet, 344, 72, 10, tileSizeX, tileSizeY, true));
		spriteData.put("walkLeft", new SpriteAnimation(spriteSheet, 344, 72, 10, tileSizeX, tileSizeY, false));
		
		this.spriteAnimName = "walkFront";
		this.spriteFrameNum = 0;
	}
	
	/**
	 * Sets the Sprite Animation based on the name of the animation passed.
	 * @param spriteName The animation to set
	 */
	public void setSpriteAnim(String spriteName) {
		//TODO: Error handle if the spriteName is not an available animation
		//Only reset the spriteFrame number if the spriteName changed
		this.spriteFrameNum = (this.spriteAnimName == spriteName ? this.spriteFrameNum: 0);
		this.spriteAnimName = spriteName;
	}
	
	/**
	 * Gets the name of the current animation
	 * @return
	 */
	public String getSpriteAnimName() {
		return this.spriteAnimName;
	}
	
	/**
	 * Updates the sprite frame if the appropriate time has passed.Should be run every frame in each second.
	 */
	public void updateSpriteFrame() {
		spriteCounter++;
		//TODO: Get the counter in the SpriteAnimation class
		//TODO: Dynamically determine the time
		if(spriteCounter > 4) {
			if(spriteFrameNum + 1 == spriteData.get(spriteAnimName).getNumFrames()) {
				spriteFrameNum = 0;
			}else {
				spriteFrameNum += 1;
			}
			spriteCounter = 0;
		}
	}
	
	/**
	 * Obtains the sprite of the current sprite animation.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSpriteFrame() {
		return spriteData.get(spriteAnimName).getSpriteFrame(spriteFrameNum);
	}
}
