package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import scene.SceneTopDown;

/**
 * Represents any tangible component in the game.
 * @author tyson
 *
 */
public abstract class Entity{
	
	protected int currentSpeed;
	protected int defaultSpeed;
	protected int xPos;
	protected int yPos;

	protected SpriteSheet spriteSheet;
	protected SceneTopDown scene;
	protected BufferedImage image;

	/**
	 * Updates the data of the entity.
	 */
	public abstract void update();
	
	/**
	 * Draws the entity to the panel.
	 * @param g2
	 */
	public void render(Graphics2D g2, int scale) {
		g2.drawImage(image, xPos * scale, yPos * scale, getWidth() * scale, getHeight() * scale, null);
	}
	
	/**
	 * Gets the width of the asset. The width returned accounts for the scaling of the scene.
	 * @return width after scaling in pixels
	 */
	public int getWidth() {
		return image.getWidth();
	}
	
	/**
	 * Gets the height of the asset. The height returned accounts for the scaling of the scene.
	 * @return height after scaling in pixels
	 */
	public int getHeight() {
		return image.getHeight();
	}
	
	/**
	 * Gets the current speed of the player.
	 * @return speed of the player
	 */
	public int getCurrentSpeed() {
		return currentSpeed;
	}
	
	/**
	 * Updates the x and y location of the entity's position.
	 * @param deltaX the change in the x direction
	 * @param deltaY the change in the y direction
	 */
	public void updatePos(int deltaX, int deltaY) {
		yPos += deltaY;
		xPos += deltaX;
	}
	
	/**
	 * Updates the x location of the entity's position.
	 * @param deltaX the change in the x direction
	 */
	public void updateXPos(int deltaX) {
		xPos += deltaX;
	}
	
	/**
	 * Updates the y location of the entity's position.
	 * @param deltaY the change in the y direction
	 */
	public void updateYPos(int deltaY) {
		yPos += deltaY;
	}
}
