package gameEngine;

import java.awt.Graphics2D;

import entity.Player;

/**
 * WorldData acts as a container for the game logic and the game data. 
 * It is meant to be used in tandem with GameManager which solely 
 * manages the game loop.
 * @author JoGray
 *
 */
public class WorldData {
	private final int baseTileSize = 32;
	private final int tileScale = 2;
	private final int tileSize = baseTileSize * tileScale;
	private final int maxScreenCol = 16;
	private final int maxScreenRow = 12;
	
	private InputController inpCtrl;
	private Player player;
	
	/**
	 * Constructor for WorldData. Initializes entities and assets to their default values.
	 * @param inpCtrl
	 */
	public WorldData(InputController inpCtrl) {
		this.inpCtrl = inpCtrl;
		this.player = new Player(inpCtrl, this);
	}
	
	/**
	 * Retrieves the size of the tile in logical pixels.
	 * @return
	 */
	public int getBaseTileSize() {
		return this.baseTileSize;
	}
	
	/**
	 * Retrieves the size of the tile after scaling to screen proportions.
	 * @return int representing the tile size in pixels
	 */
	public int getTileSize() {
		return this.tileSize;
	}
	
	/**
	 * Retrieves the number of columns for tiles on the screen.
	 * @return int representing the number of columns
	 */
	public int getMaxScreenCol() {
		return this.maxScreenCol;
	}
	
	/**
	 * Retrieves the number of rows for tiles on the screen.
	 * @return int representing the number of rows
	 */
	public int getMaxScreenRow() {
		return this.maxScreenRow;
	}
	
	/**
	 * Updates all world data of all entities and assets. Does not render.
	 */
	public void update() {
		//Depending on the state of the game, different components may be updated
		player.update();
	}
	
	/**
	 * Renders the world to the screen.
	 * @param g2
	 */
	public void render(Graphics2D g2) {
		player.draw(g2);
	}
}
