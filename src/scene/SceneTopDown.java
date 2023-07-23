package scene;

import java.awt.Graphics2D;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.PlayerTopDown;
import gameEngine.GameManager;

/**
 * WorldData acts as a container for the game logic and the game data. 
 * It is meant to be used in tandem with GameManager which solely 
 * manages the game loop.
 * @author JoGray
 *
 */
public class SceneTopDown implements KeyListener{
	//TODO: Make a Scene class as a parent class that will be the KeyListener
	//TODO: Make Scenes dynamic by loaded from a properties file
	//TODO: Improve performance by only rendering that part of the image that is displayed
	private final int scale = 2;
	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;
	public boolean leftPressed = false;
	
	public static final int DEFAULT_UP_FACTOR = -1;
	public static final int DEFAULT_DOWN_FACTOR = 1;
	public static final int DEFAULT_RIGHT_FACTOR = 1;
	public static final int DEFAULT_LEFT_FACTOR = -1;
	
	public int upFactor, downFactor, rightFactor, leftFactor;
	private int sceneOriginX, sceneOriginY;
	
	private PlayerTopDown player;
	private Map map;

	/**
	 * Constructor for WorldData. Initializes entities and assets to their default values.
	 * @param inpCtrl
	 */
	public SceneTopDown() {
		GameManager gm = GameManager.getInstance();
		sceneOriginX = gm.getWindowWidth() / 2;
		sceneOriginY = gm.getWindowHeight() / 2;
		upFactor = 0;
		downFactor = 0;
		rightFactor = 0;
		leftFactor = 0;
		this.player = new PlayerTopDown(this, "/player/zelda.properties", sceneOriginX, sceneOriginY);
		this.map = new Map(this, -100, -100, "/maps/castle.png");
	}
	
	/**
	 * Gets the scale of the world.
	 * @return scale factor
	 */
	public int getScale() {
		return scale;
	}
	
	/**
	 * Gets the scene origin's x coordinate. The origin is considered the center of the screen.
	 * @return x coordinate in pixels
	 */
	public int getSceneOriginX() {
		return sceneOriginX;
	}
	
	/**
	 * Gets the scene origin's y coordinate. The origin is considered the center of the screen.
	 * @return y coordinate in pixels
	 */
	public int getSceneOriginY() {
		return sceneOriginY;
	}
	
	/**
	 * Updates all world data of all entities and assets. Does not render.
	 */
	public void updateScene() {
		int playerSpeed = player.getCurrentSpeed();
		int deltaX = playerSpeed * (leftFactor + rightFactor);
		int deltaY = playerSpeed * (upFactor + downFactor);
		
		if(deltaX != 0) {
			int mapMarginLeft = map.getXPos();
			int mapMarginRight = map.getXPos() + map.getMapWidth() - GameManager.getInstance().getWidth();
			int playerOffset = player.getPlayerOffsetX();
			int mapDeltaX = calculateMapDelta(deltaX, mapMarginLeft, mapMarginRight, playerOffset);
			map.updateXPos(mapDeltaX);
			player.updateXPos(deltaX + mapDeltaX);
		}
		
		if(deltaY != 0) {
			int mapMarginTop = map.getYPos();
			int mapMarginBottom = map.getYPos() + map.getMapHeight() - GameManager.getInstance().getHeight();
			int playerOffset = player.getPlayerOffsetY();
			int mapDeltaY = calculateMapDelta(deltaY, mapMarginTop, mapMarginBottom, playerOffset);
			map.updateYPos(mapDeltaY);
			player.updateYPos(deltaY + mapDeltaY);
		}
		
		map.update();
		player.update();
	}
	
	private int calculateMapDelta(int delta, int mapMarginLower, int mapMarginUpper, int playerOffset) {
		// The delta variable is relative to the player, so the value returned is multiplied by -1 to make it relative to the map
		int mapDelta = 0;
		if( (delta > 0  && playerOffset < 0) || (delta < 0 && playerOffset > 0) ) {
			// This condition is true if the player is not centered and is moving toward the origin. 
			// In this case we want the player to move instead of the map.
			mapDelta = -(Math.abs(playerOffset) < player.getCurrentSpeed() ? -(delta - player.getCurrentSpeed()) : 0);
		}else if(delta < 0 && mapMarginLower > delta) {
			// This condition is true if the player is moving up but the map doesn't need to move a full iteration of the player's speed.
			// In this case, move the map only as much as is needed.
			mapDelta = -(mapMarginLower / delta) * Math.abs(delta);
		}else if(delta > 0 && mapMarginUpper < delta) {
			// This condition is true if the player is moving down but the map doesn't need to move a full iteration of the player's speed.
			// In this case, move the map only as much as is needed.
			mapDelta = -(mapMarginUpper / delta) * Math.abs(delta);
		}else {
			mapDelta = -delta;
		}
		return mapDelta;
	}
	
	/**
	 * Renders the world to the screen.
	 * @param g2
	 */
	public void renderScene(Graphics2D g2) {
		map.render(g2);
		player.render(g2);
	}
	
	/**
	 * Checks if the character is moving in a diagonal direction
	 * @return
	 */
	public boolean isDiagonalDirection() {
		return upPressed && leftPressed || upPressed && rightPressed || downPressed && leftPressed || downPressed && rightPressed;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode) {
			case KeyEvent.VK_W:
				upPressed = true;
				upFactor = DEFAULT_UP_FACTOR;
				break;
			case KeyEvent.VK_S:
				downPressed = true;
				downFactor = DEFAULT_DOWN_FACTOR;
				break;
			case KeyEvent.VK_A:
				leftPressed = true;
				leftFactor = DEFAULT_LEFT_FACTOR;
				break;
			case KeyEvent.VK_D:
				rightPressed = true;
				rightFactor = DEFAULT_RIGHT_FACTOR;
				break;
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode) {
			case KeyEvent.VK_W:
				upPressed = false;
				upFactor = 0;
				break;
			case KeyEvent.VK_S:
				downPressed = false;
				downFactor = 0;
				break;
			case KeyEvent.VK_A:
				leftPressed = false;
				leftFactor = 0;
				break;
			case KeyEvent.VK_D:
				rightPressed = false;
				rightFactor = 0;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// No purpose for implementation
	}
}
