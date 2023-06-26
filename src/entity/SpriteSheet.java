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
	private HashMap<String, SpriteAnimation> animationData;
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
		animationData = new HashMap<String, SpriteAnimation>();
		//Dynamically load animation data from properties file
		for(String key : prop.stringPropertyNames()) {
			if(key.substring(0, 4).equals("anim") && key.substring(key.length() - 4, key.length()).equals("name")) {
				String animName = prop.getProperty(key);
				//Get property values and continue if one is missing
				String x = prop.getProperty("anim_" + animName + "_xStart");
				if(x == null) continue;
				String y = prop.getProperty("anim_" + animName + "_yStart");
				if(y == null) continue;
				String numFrames = prop.getProperty("anim_" + animName + "_numFrames");
				if(numFrames == null) continue;
				String tileSizeX = prop.getProperty("anim_" + animName + "_tileSizeX");
				if(tileSizeX == null) continue;
				String tileSizeY = prop.getProperty("anim_" + animName + "_tileSizeY");
				if(tileSizeY == null) continue;
				String flipHorizontally = prop.getProperty("anim_" + animName + "_flipHorizontally");
				if(flipHorizontally == null) continue;
				String speed = prop.getProperty("anim_" + animName + "_speed");
				if(speed == null) continue;
				
				//Add the animation
				animationData.put(prop.getProperty(key), new SpriteAnimation(
						spriteSheet, 
						Integer.parseInt(x), 
						Integer.parseInt(y), 
						Integer.parseInt(numFrames), 
						Integer.parseInt(tileSizeX), 
						Integer.parseInt(tileSizeY), 
						Boolean.parseBoolean(flipHorizontally), 
						Integer.parseInt(speed), 
						FPS
				));
			}
		}
		
		this.animDirection = prop.getProperty("default_start_direction");
		this.animType = prop.getProperty("default_start_type");
		animationData.get(getAnimName()).resetAnim();
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
		if(!animationData.containsKey(spriteName)){
			//TODO: Log instead of printing to console
			System.out.println("Sprite Animation " + spriteName + " was not loaded as it doesn't exist.");
			return;
		}
		//Only reset the spriteFrame number if the spriteName changed
		if(!this.getAnimName().equals(spriteName)) {
			this.animationData.get(getAnimName()).resetAnim();
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
		animationData.get(getAnimName()).update();
	}
	
	/**
	 * Obtains the sprite of the current sprite animation.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSpriteFrame() {
		return animationData.get(getAnimName()).getFrame();
	}
}
