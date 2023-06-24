package entity;

import java.awt.Graphics2D;

/**
 * Represents any tangible component in the game.
 * @author tyson
 *
 */
public abstract class Entity {
	protected int x;
	protected int y;
	protected int speed;
	protected SpriteSheet spriteSheet;
	
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
