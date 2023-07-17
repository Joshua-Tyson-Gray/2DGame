package entity;

import java.awt.Graphics2D;

import scene.SceneTopDown;

/**
 * Represents any tangible component in the game.
 * @author tyson
 *
 */
public abstract class Entity{
	
	protected int currentSpeed;
	protected int defaultSpeed;
	protected int xLoc;
	protected int yLoc;

	protected SpriteSheet spriteSheet;
	protected SceneTopDown scene;

	/**
	 * Updates the data of the entity.
	 */
	public abstract void update();
	
	/**
	 * Draws the entity to the panel.
	 * @param g2
	 */
	public abstract void render(Graphics2D g2);
}
