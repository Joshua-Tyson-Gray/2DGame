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
	
	public boolean mapOverflowX;
	public boolean mapOverflowY;
	
	private PlayerTopDown player;
	private Map map;

	/**
	 * Constructor for WorldData. Initializes entities and assets to their default values.
	 * @param inpCtrl
	 */
	public SceneTopDown() {
		upFactor = 0;
		downFactor = 0;
		rightFactor = 0;
		leftFactor = 0;
		GameManager gm = GameManager.getInstance();
		this.player = new PlayerTopDown(this, "/player/zelda.properties", gm.getWindowWidth() / 2, gm.getWindowHeight() / 2);
		this.map = new Map(this, -100, -100, "/maps/castle.png");
		
		mapOverflowX = map.getMapWidth() > gm.getWidth();
		mapOverflowY = map.getMapHeight() > gm.getHeight();
	}
	
	/**
	 * Gets the scale of the world.
	 * @return
	 */
	public int getScale() {
		return scale;
	}
	
	/**
	 * Updates all world data of all entities and assets. Does not render.
	 */
	public void updateScene() {
		int playerSpeed = player.getCurrentSpeed();
		// Deltas are relative to the player. To make them relative to the map, multiply them by -1.
		int deltaY = playerSpeed * (upFactor + downFactor);
		int deltaX = playerSpeed * (leftFactor + rightFactor);
		int mapXPos = map.getXPos();
		int mapYPos = map.getYPos();

		int mapMarginLeft = mapXPos;
		int mapMarginRight = mapXPos + map.getRawMapWidth() - GameManager.getInstance().getWidth();
		int mapMarginTop = mapYPos;
		int mapmarginBottom = mapYPos + map.getRawMapHeight() - GameManager.getInstance().getHeight();
		
		if(deltaX < 0) {
			map.updatePosX((deltaX<0)? : );
		}else if(deltaX > 0) {
			
		}
		
		
		if(deltaX != 0) {
			if(deltaX < 0) {
				
			}
		}
		if(deltaY != 0) {
			
		}
		
//		if(deltaX != 0 && mapOverflowX) {
//			int mapDeltaX = (mapXPos / deltaX) * deltaX;
//			int playerDeltaX = deltaX - mapDeltaX;
//			map.updateXPos(-mapDeltaX);
//			player.updateXPos(playerDeltaX);
//		}else if(deltaX != 0){
//			player.updateXPos(deltaX);
//		}
//		if(deltaY != 0 && mapOverflowY) {
//			int mapDeltaY = (mapYPos / deltaY) * deltaY;
//			int playerDeltaY = deltaY - mapDeltaY;
//			map.updateYPos(-mapDeltaY);
//			player.updateYPos(playerDeltaY);
//		}else if(deltaY != 0){
//			player.updateYPos(deltaY);
//		}		
		
		//Check if the bounds of the map are hit
		//TODO: Account for recentering the player
//		if(deltaX < 0 && mapXPos - deltaX < 0 || deltaX > 0 && mapXPos + map.getMapWidth() - deltaX > GameManager.getInstance().getWindowWidth()) {
//			map.updateXPos(-deltaX);
//		}else {
//			player.updateXPos(deltaX);
//		}
//		if(deltaY < 0 && mapYPos - deltaY < 0 || deltaY > 0 && mapYPos + map.getMapHeight() - deltaY > GameManager.getInstance().getWindowHeight()) {
//			map.updateYPos(-deltaY);
//		}else {
//			player.updateYPos(deltaY);
//		}
		
		map.update();
		player.update();
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
