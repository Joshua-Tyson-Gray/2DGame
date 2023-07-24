package entity;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * A data structure holding the data for a particular animation on a SpriteSheet.
 * @author tyson
 *
 */
public class SpriteAnimation {
	//TODO: Can I incorporate a directional variable to help with transitioning to idle?
	private int x, y;
	private int width, height;
	private int numFrames;
	private boolean shouldFlip;
	private int frameInterval;
	private int currentFrame;
	private int updateCounter;
	private String animType;
	private String animDirection;
	
	private BufferedImage spriteSheet;
	
	/**
	 * A data structure holding the data for a particular animation on a SpriteSheet.
	 * @param spriteSheet The overall sprite sheet that contains the animation
	 * @param x The x coordinate of the first frame in the animation
	 * @param y The y coordinate of the first frame in the animation
	 * @param numFrames The number of frames in the animation
	 * @param width The width of the sprite tiles in pixels
	 * @param height The height of the sprite tiles in pixels
	 * @param flipHorizontally boolean indicating if each sprite in the animation should be flipped horizontally before returning
	 * @param speed time in milliseconds for how long it should take to complete one cycle of the animation
	 * @param FPS frames per second determined by the game loop
	 */
	public SpriteAnimation(BufferedImage spriteSheet, int x, int y, int numFrames, int width, int height, boolean flipHorizontally, int speed, String animType, String animDirection, int FPS){
		this.x = x;
		this.y = y;
		this.numFrames = numFrames;
		this.spriteSheet = spriteSheet;
		this.shouldFlip = flipHorizontally;
		this.width = width;
		this.height = height;
		this.frameInterval = (int)Math.round((speed / 1000.0) * FPS / numFrames);
		this.currentFrame = 0;
		this.updateCounter = 0;
		this.animType = animType;
		this.animDirection = animDirection;
	}
	
	/**
	 * Gets the animation type. If there is no animation, returns null.
	 * @return
	 */
	public String getAnimType() {
		return animType;
	}
	
	/**
	 * Gets the direction of the animation.
	 * @return String
	 */
	public String getAnimDirection() {
		return animDirection;
	}
	
	/**
	 * Updates the animation frame if the appropriate time has elapsed.
	 */
	public void update() {
		if(numFrames == 1) {
			return;
		}
		updateCounter++;
		if(updateCounter > frameInterval) {
			if(currentFrame + 1 == numFrames) {
				currentFrame = 0;
			}else {
				currentFrame += 1;
			}
			updateCounter = 0;
		}
	}
	
	/**
	 * Resets the animation to the beginning frame.
	 */
	public void resetAnim() {
		currentFrame = 0;
	}
	
	/**
	 * Gets the y coordinate of the first frame in the animation on the sprite sheet.
	 * @return y coordinate
	 */
	public int getYCoordinate() {
		return y;
	}
	
	/**
	 * Gets the x coordinate of the first frame in the animation on the sprite sheet.
	 * @return x coordinate
	 */
	public int getXCoordinate() {
		return x;
	}
	
	/**
	 * Returns the number of frames in one complete cycle of the animation.
	 * @return number of frames in the animation
	 */
	public int getNumFrames() {
		return numFrames;
	}
	
	/**
	 * Gets the BufferedImage of the sprite frame in the animation based on the frame number passed.
	 * @return BufferedImage of the sprite frame
	 */
	public BufferedImage getFrame() {
		int frameXCoord = x + width * currentFrame;
		BufferedImage sprite = spriteSheet.getSubimage(frameXCoord, y, width, height);
		return (shouldFlip ? SpriteAnimation.flipFrameHorizontally(sprite) : sprite);
	}
	
	/**
	 * Checks if the Sprite truly has an animation or is just a stand alone image
	 * @return true if animation exists, false otherwise
	 */
	public boolean hasAnimation() {
		return !(numFrames == 1);
	}
	
	/**
	 * Gets how many frames should pass before the next sprite frame is rendered.
	 * @return Number of frames before the next sprite image is drawn.
	 */
	public int getFrameInterval() {
		return frameInterval;
	}
	
	/**
	 * Flips the sprite's image in the horizontal direction
	 * @param img The image to flip
	 * @return A BufferedImage of the flipped sprite.
	 */
	private static BufferedImage flipFrameHorizontally(BufferedImage img) {
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
	
}
