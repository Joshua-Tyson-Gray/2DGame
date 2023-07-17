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
	private boolean isPlayerLocked = true;
	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;
	public boolean leftPressed = false;
	
	private PlayerTopDown player;
	private Map map;

	
	/**
	 * Constructor for WorldData. Initializes entities and assets to their default values.
	 * @param inpCtrl
	 */
	public SceneTopDown() {
		GameManager gm = GameManager.getInstance();
		this.player = new PlayerTopDown(this, "/player/zelda.properties", gm.getWindowWidth() / 2, gm.getWindwoHeight() / 2);
		this.map = new Map(this, 0, 0, "/maps/castle.png");
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
		int deltaY = 0;
		int deltaX = 0;
		
		//Update location
		if(upPressed) {
			deltaY -= playerSpeed;
		}
		if(downPressed) {
			deltaY += playerSpeed;
		}
		if(leftPressed) {
			deltaX -= playerSpeed;
		}
		if(rightPressed) {
			deltaX += playerSpeed;
		}
		
		if(isPlayerLocked) {
			player.updateLoc(deltaX, deltaY);
		}else {
			map.updatePosition(-deltaX, -deltaY);
		}
		
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
				break;
			case KeyEvent.VK_S:
				downPressed = true;
				break;
			case KeyEvent.VK_A:
				leftPressed = true;
				break;
			case KeyEvent.VK_D:
				rightPressed = true;
				break;
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch(keyCode) {
			case KeyEvent.VK_W:
				upPressed = false;
				break;
			case KeyEvent.VK_S:
				downPressed = false;
				break;
			case KeyEvent.VK_A:
				leftPressed = false;
				break;
			case KeyEvent.VK_D:
				rightPressed = false;
				break;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// No purpose for implementation
	}
}
