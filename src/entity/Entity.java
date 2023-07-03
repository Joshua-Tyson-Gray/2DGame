package entity;

import java.awt.Graphics2D;

import gameEngine.WorldData;

/**
 * Represents any tangible component in the game.
 * @author tyson
 *
 */
public abstract class Entity {
	
	protected int currentSpeed; //If the entity is a stationary object, the speed should be set to 0.
	protected int defaultSpeed;
	protected int xLoc;
	protected int yLoc;

	protected SpriteSheet spriteSheet;
	protected WorldData world;

	/**
	 * Updates the data of the entity.
	 */
	public abstract void update();
	
	/**
	 * Draws the entity to the panel.
	 * @param g2
	 */
	public abstract void draw(Graphics2D g2);
}
