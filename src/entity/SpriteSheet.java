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
	private String spriteAnimName;
	private HashMap<String, SpriteAnimation> spriteData;
	public static final String WALK = "walk";
	public static final String IDLE = "idle";
	
	/**
	 * Generates a SpriteSheet from the image path passed. Assumes the tiles are exact squares.
	 * @param spriteSheetPath Path to the sprite sheet
	 * @param tileSize Width/Height of the sprite tiles
	 * @param FPS frames per second determined by the game loop
	 */
	public SpriteSheet(String spriteSheetPath, int tileSize, int FPS) throws IOException{
		this(spriteSheetPath, tileSize, tileSize, FPS);
	}

	/**
	 * Generates a SpriteSheet from the image path passed.
	 * @param spriteSheetPath Path to the sprite sheet
	 * @param tileSizeX Width of the tiles
	 * @param tileSizeY Height of the tiles
	 * @param FPS frames per second determined by game loop
	 */
	public SpriteSheet(String spriteSheetPath, int tileSizeX, int tileSizeY, int FPS) throws IOException{
		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(spriteSheetPath));
		}catch(IOException e) {
			throw e;
		}
		spriteData = new HashMap<String, SpriteAnimation>();
		
		//Idle Animations
		spriteData.put("idleSouth", new SpriteAnimation(spriteSheet, 8, 4, 1, tileSizeX, tileSizeY, false, 400, FPS));
		spriteData.put("idleNorth", new SpriteAnimation(spriteSheet, 72, 4, 1, tileSizeX, tileSizeY, false, 400, FPS));
		spriteData.put("idleEast", new SpriteAnimation(spriteSheet, 42, 4, 1, tileSizeX, tileSizeY, true, 400, FPS));
		spriteData.put("idleWest", new SpriteAnimation(spriteSheet, 42, 4, 1, tileSizeX, tileSizeY, false, 400, FPS));
		
		//Walk Animations
		spriteData.put("walkSouth", new SpriteAnimation(spriteSheet, 8, 72, 10, tileSizeX, tileSizeY, false, 400, FPS));
		spriteData.put("walkNorth", new SpriteAnimation(spriteSheet, 675, 72, 10, tileSizeX, tileSizeY, false, 400, FPS));
		spriteData.put("walkEast", new SpriteAnimation(spriteSheet, 344, 72, 10, tileSizeX, tileSizeY, true, 400, FPS));
		spriteData.put("walkWest", new SpriteAnimation(spriteSheet, 344, 72, 10, tileSizeX, tileSizeY, false, 400, FPS));
		
		this.spriteAnimName = "idleSouth";
		spriteData.get(spriteAnimName).resetAnim();
	}
	
	/**
	 * Sets the Sprite Animation based on the name of the animation passed. The function silently returns if the spriteName is not valid.
	 * @param spriteName The animation to set
	 */
	public void setSpriteAnim(String spriteName) {
		if(!spriteData.containsKey(spriteName)){
			//TODO: Log instead of printing to console
			System.out.println("Sprite Animation " + spriteName + "was not loaded as it doesn't exist.");
			return;
		}
		//Only reset the spriteFrame number if the spriteName changed
		if(!this.spriteAnimName.equals(spriteName)) {
			this.spriteData.get(spriteAnimName).resetAnim();
		}
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
	 * Updates the sprite frame if the appropriate time has passed. Should be run every frame in each second.
	 */
	public void updateSpriteFrame() {
		spriteData.get(spriteAnimName).update();
	}
	
	/**
	 * Obtains the sprite of the current sprite animation.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSpriteFrame() {
		return spriteData.get(spriteAnimName).getFrame();
	}
}
