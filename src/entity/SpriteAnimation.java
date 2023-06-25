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
	
	private BufferedImage spriteSheet;
	
	/**
	 * A data structure holding the data for a particular animation on a SpriteSheet.
	 * @param spriteSheet The overall sprite sheet that contains the animation
	 * @param x The x coordinate of the first frame in the animation
	 * @param y The y coordinate of the first frame in the animation
	 * @param numFrames The number of frames in the animation
	 * @param width The width of the sprite tiles in pixels
	 * @param height The height of the sprite tiles in pixels
	 * @param flipHorizontally boolean indicating if each sprite tile should be flipped horizontally after retrieval
	 */
	public SpriteAnimation(BufferedImage spriteSheet, int x, int y, int numFrames, int width, int height, boolean flipHorizontally){
		this.x = x;
		this.y = y;
		this.numFrames = numFrames;
		this.spriteSheet = spriteSheet;
		this.shouldFlip = flipHorizontally;
		this.width = width;
		this.height = height;
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
	 * @param frameNum index of the frame in the animation
	 * @return BufferedImage of the sprite frame
	 */
	public BufferedImage getSpriteFrame(int frameNum) {
		int frameXCoord = x + width * frameNum;
		BufferedImage sprite = spriteSheet.getSubimage(frameXCoord, y, width, height);
		return (shouldFlip ? SpriteAnimation.flipSpriteHorizontally(sprite) : sprite);
	}
	
	/**
	 * Checks if the Sprite truly has an animation or is just a stand alone image
	 * @return true if animation exists, false otherwise
	 */
	public boolean hasAnimation() {
		return !(this.numFrames == 1);
	}
	
	/**
	 * Flips the sprite's image in the horizontal direction
	 * @param img The image to flip
	 * @return A BufferedImage of the flipped sprite.
	 */
	private static BufferedImage flipSpriteHorizontally(BufferedImage img) {
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
