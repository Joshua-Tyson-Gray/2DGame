package entity;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import gameEngine.GameManager;

/**
 * Stores the sprites and animation data necessary for an entity.
 * @author tyson
 *
 */
public class SpriteSheet {
	public static final String WALK = "walk";
	public static final String IDLE = "idle";
	
	private BufferedImage spriteSheet;
	private HashMap<String, SpriteAnimation> animationData;
	private SpriteAnimation currentAnim;
	private String direction;

	/**
	 * Generates a SpriteSheet from the properties object passed.
	 * @param props Properties object containing spritesheet data
	 */
	public SpriteSheet(Properties props) throws IOException{
		try {
			spriteSheet = ImageIO.read(getClass().getResourceAsStream(props.getProperty("spriteSheet_path")));
		}catch(IOException e) {
			throw e;
		}
		animationData = new HashMap<String, SpriteAnimation>();
		//Dynamically load animation data from properties file
		for(String key : props.stringPropertyNames()) {
			if(key.substring(0, 4).equals("anim") && key.substring(key.length() - 4).equals("name")) {
				String animName = props.getProperty(key);
				//Get property values and continue if one is missing
				String x = props.getProperty("anim_" + animName + "_xStart");
				if(x == null) continue;
				String y = props.getProperty("anim_" + animName + "_yStart");
				if(y == null) continue;
				String numFrames = props.getProperty("anim_" + animName + "_numFrames");
				if(numFrames == null) continue;
				String tileSizeX = props.getProperty("anim_" + animName + "_tileSizeX");
				if(tileSizeX == null) continue;
				String tileSizeY = props.getProperty("anim_" + animName + "_tileSizeY");
				if(tileSizeY == null) continue;
				String flipHorizontally = props.getProperty("anim_" + animName + "_flipHorizontally");
				if(flipHorizontally == null) continue;
				String speed = props.getProperty("anim_" + animName + "_speed");
				if(speed == null) continue;
				String direction = props.getProperty("anim_" + animName + "_direction");
				if(direction == null) continue;
				String type = props.getProperty("anim_" + animName + "_type");
				if(type == null) continue;
				
				//Add the animation
				animationData.put(props.getProperty(key), new SpriteAnimation(
						spriteSheet, 
						Integer.parseInt(x), 
						Integer.parseInt(y), 
						Integer.parseInt(numFrames), 
						Integer.parseInt(tileSizeX), 
						Integer.parseInt(tileSizeY), 
						Boolean.parseBoolean(flipHorizontally), 
						Integer.parseInt(speed), 
						type,
						direction,
						GameManager.getInstance().getFPS()
				));
			}
		}
		// If no animations, just load a single frame animation of the entire spritesheet.
		if(animationData.keySet().isEmpty()) {
			direction = EntityTopDown.SOUTH;
			currentAnim = new SpriteAnimation(spriteSheet, 0, 0, 1, spriteSheet.getWidth(), spriteSheet.getHeight(), false, 0, IDLE, direction, GameManager.getInstance().getFPS());
			animationData.put(IDLE + direction, currentAnim);
		}else {
			direction = props.getProperty("default_start_direction");
			String animType = props.getProperty("default_start_type");
			currentAnim = animationData.get(animType + direction);
		}
		
		currentAnim.resetAnim();
	}
	
	/**
	 * Gets the direction of the animation. If direction is not applicable, the empty string is returned.
	 * @return String
	 */
	public String getDirection() {
		return direction;
	}
	
	/**
	 * Sets the animation direction
	 * @param direction
	 */
	public void setDirection(String direction) {
		this.direction = direction;
		setSpriteAnim(currentAnim.getAnimType());
	}
	
	/**
	 * Gets the animation type. If there is no animation, returns null.
	 * @return
	 */
	public String getAnimType() {
		if(currentAnim == null) {
			return null;
		}
		return currentAnim.getAnimType();
	}
	
	/**
	 * Sets the animation type
	 * @param animType
	 */
	public void setAnimType(String animType) {
		setSpriteAnim(animType);
	}
	
	/**
	 * Sets the Sprite Animation based on the name of the animation passed. The function silently returns if the spriteName is not valid.
	 * @param spriteName The animation to set
	 */
	private void setSpriteAnim(String animType) {
		String spriteName = animType + direction;
		if(currentAnim != null && !animationData.containsKey(spriteName)){
			//TODO: Log instead of printing to console
			System.out.println("Sprite Animation " + spriteName + " was not loaded as it doesn't exist.");
			return;
		}
		//Only reset the spriteFrame number if the spriteName changed
		if(!this.getAnimName().equals(spriteName)) {
			currentAnim = this.animationData.get(spriteName);
			currentAnim.resetAnim();
		}
	}
	
	/**
	 * Gets the name of the current animation
	 * @return
	 */
	public String getAnimName() {
		return currentAnim.getAnimType() + direction;
	}
	
	/**
	 * Updates the sprite frame if the appropriate time has passed. Should be run every frame in each second.
	 */
	public void updateSpriteFrame() {
		if(currentAnim != null) {
			animationData.get(getAnimName()).update();
		}
	}
	
	/**
	 * Obtains the sprite of the current sprite animation.
	 * @param key The key or name of the sprite to retrieve
	 * @return BufferedImage of the sprite corresponding to the key
	 */
	public BufferedImage getSpriteFrame() {
		if(currentAnim != null) {
			return animationData.get(getAnimName()).getFrame();
		}
		return spriteSheet;
	}
}
