package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

/**
 * Stores the sprites and animation data necessary for an entity.
 * @author tyson
 *
 */
public class SpriteSheet {
	private BufferedImage spriteSheet;
	private HashMap<String, SpriteAnimation> spriteData;
	private String animType;
	private String animDirection;
	public static final String WALK = "walk";
	public static final String IDLE = "idle";

	/**
	 * Generates a SpriteSheet from the image path passed.
	 * @param spriteSheetPath Path to the sprite sheet
	 * @param FPS frames per second determined by game loop
	 */
	public SpriteSheet(Properties prop, int FPS) throws IOException{
		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(prop.getProperty("spriteSheet_path")));
		}catch(IOException e) {
			throw e;
		}
		spriteData = new HashMap<String, SpriteAnimation>();
		//Dynamically load animation data from properties file
		for(String key : prop.stringPropertyNames()) {
			if(key.substring(0, 4).equals("anim") && key.substring(key.length() - 4, key.length()).equals("name")) {
				//TODO: Add exception handling if a particular key is not found.
				String animName = prop.getProperty(key);
				int x = Integer.parseInt(prop.getProperty("anim_" + animName + "_xStart"));
				int y = Integer.parseInt(prop.getProperty("anim_" + animName + "_yStart"));;
				int numFrames = Integer.parseInt(prop.getProperty("anim_" + animName + "_numFrames"));
				int tileSizeX = Integer.parseInt(prop.getProperty("anim_" + animName + "_tileSizeX"));
				int tileSizeY = Integer.parseInt(prop.getProperty("anim_" + animName + "_tileSizeY"));
				boolean flipHorizontally = Boolean.parseBoolean(prop.getProperty("anim_" + animName + "_flipHorizontally"));
				int speed = Integer.parseInt(prop.getProperty("anim_" + animName + "_speed"));
				
				spriteData.put(prop.getProperty(key), new SpriteAnimation(spriteSheet, x, y, numFrames, tileSizeX, tileSizeY, flipHorizontally, speed, FPS));
			}
		}
		
		this.animDirection = prop.getProperty("default_start_direction");
		this.animType = prop.getProperty("default_start_type");
		spriteData.get(getAnimName()).resetAnim();
	}
	
	/**
	 * Gets the direction of the animation.
	 * @return String
	 */
	public String getAnimDirection() {
		return animDirection;
	}
	
	/**
	 * Sets the animation direction
	 * @param animDirection
	 */
	public void setAnimDirection(String animDirection) {
		this.animDirection = animDirection;
		setSpriteAnim();
	}
	
	/**
	 * Gets the animation type.
	 * @return
	 */
	public String getAnimType() {
		return animType;
	}
	
	/**
	 * Sets the animation type
	 * @param animType
	 */
	public void setAnimType(String animType) {
		this.animType = animType;
		setSpriteAnim();
	}
	
	/**
	 * Sets the Sprite Animation based on the name of the animation passed. The function silently returns if the spriteName is not valid.
	 * @param spriteName The animation to set
	 */
	private void setSpriteAnim() {
		String spriteName = animType + animDirection;
		if(!spriteData.containsKey(spriteName)){
			//TODO: Log instead of printing to console
			System.out.println("Sprite Animation " + spriteName + " was not loaded as it doesn't exist.");
			return;
		}
		//Only reset the spriteFrame number if the spriteName changed
		if(!this.getAnimName().equals(spriteName)) {
			this.spriteData.get(getAnimName()).resetAnim();
		}
	}
	
	public int getFrameWidth() {
		return -1;
	}
	
	public int getFrameHeight() {
		return -1;
	}
	
	/**
	 * Gets the name of the current animation
	 * @return
	 */
	public String getAnimName() {
		return animType + animDirection;
	}
	
	/**
	 * Updates the sprite frame if the appropriate time has passed. Should be run every frame in each second.
	 */
	public void updateSpriteFrame() {
		spriteData.get(getAnimName()).update();
	}
	
	/**
	 * Obtains the sprite of the current sprite animation.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSpriteFrame() {
		return spriteData.get(getAnimName()).getFrame();
	}
}
