package entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import gameEngine.WorldData;

/**
 * Player contains the data and functions necessary for the player entity.
 * @author tyson
 *
 */
public class PlayerTopDown extends EntityTopDown implements KeyListener{
	
	public boolean upPressed = false;
	public boolean downPressed = false;
	public boolean rightPressed = false;
	public boolean leftPressed = false;
	
	/**
	 * Constructor for the Player entity.
	 * @param world The world in which the player entity resides
	 * @param playerPropFilePath properties file for the player configuration
	 * @param xLoc The X location of the player
	 * @param yLoc The Y location of the player
	 */
	public PlayerTopDown(WorldData world, String playerPropFilePath, int xLoc, int yLoc) {
		this.world = world;
		
		//Load player properties
		Properties playerProps = new Properties();
		try{
			playerProps.load(getClass().getResourceAsStream(playerPropFilePath));
		}catch(IOException e) {
			//TODO: Log to file instead of printing to screen
			System.out.println("Player properties file could not be loaded.");
			e.printStackTrace();
		}
		
		//Set default location
		this.xLoc = xLoc;
		this.yLoc = yLoc;
		
		//Set speed of character
		this.defaultSpeed = Integer.parseInt(playerProps.getProperty("default_speed"));
		this.currentSpeed = defaultSpeed;
		double sine45 = 0.707;
		this.defaultDiagonalSpeed = (int)Math.round(sine45 * defaultSpeed);
		
		try {
			this.spriteSheet = new SpriteSheet(playerProps, world.getFPS());
		}catch (IOException e) {
			//TODO: Log instead of printing to screen
			System.out.println("Sprite Sheet could not be loaded.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if the character is moving in a diagonal direction
	 * @return
	 */
	private boolean isDiagonalDirection() {
		return upPressed && leftPressed || upPressed && rightPressed || downPressed && leftPressed || downPressed && rightPressed;
	}

	@Override
	public void update() {
		//TODO: There's got to be a more efficient way to calculate all this dynamically
		//Account for diagonal speed
		currentSpeed = (isDiagonalDirection() ? defaultDiagonalSpeed : defaultSpeed);
		
		//Determine Animation Type
		if(!rightPressed && !leftPressed && !upPressed && !downPressed) {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}else if((rightPressed ^ leftPressed) || (upPressed ^ downPressed)) {
			spriteSheet.setAnimType(SpriteSheet.WALK);
		}else {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}
		
		//Determine Animation Direction
		if(rightPressed && upPressed && (spriteSheet.getAnimDirection() != NORTH || spriteSheet.getAnimDirection() != EAST)) {
			spriteSheet.setAnimDirection(NORTH);
		}else if(leftPressed && upPressed && (spriteSheet.getAnimDirection() != NORTH || spriteSheet.getAnimDirection() != WEST)) {
			spriteSheet.setAnimDirection(NORTH);
		}else if(leftPressed && downPressed && (spriteSheet.getAnimDirection() != SOUTH || spriteSheet.getAnimDirection() != WEST)) {
			spriteSheet.setAnimDirection(SOUTH);
		}else if(rightPressed && downPressed && (spriteSheet.getAnimDirection() != SOUTH || spriteSheet.getAnimDirection() != EAST)) {
			spriteSheet.setAnimDirection(SOUTH);
		}else if(upPressed){
			spriteSheet.setAnimDirection(NORTH);
		}else if(downPressed){
			spriteSheet.setAnimDirection(SOUTH);
		}else if(rightPressed){
			spriteSheet.setAnimDirection(EAST);
		}else if(leftPressed){
			spriteSheet.setAnimDirection(WEST);
		}
		
		//Update location
		if(upPressed) {
			yLoc -= currentSpeed;
		}
		if(downPressed) {
			yLoc += currentSpeed;
		}
		if(leftPressed) {
			xLoc -= currentSpeed;
		}
		if(rightPressed) {
			xLoc += currentSpeed;
		}
		
//		if(!inpCtrl.rightPressed && !inpCtrl.leftPressed && !inpCtrl.upPressed && !inpCtrl.downPressed) {
//			switch(direction) {
//			case Player.NORTH:
//				spriteSheet.setSpriteAnim("idleBack");
//				break;
//			case Player.EAST:
//				spriteSheet.setSpriteAnim("idleRight");
//				break;
//			case Player.SOUTH:
//				spriteSheet.setSpriteAnim("idleFront");
//				break;
//			case Player.WEST:
//				spriteSheet.setSpriteAnim("idleLeft");
//				break;
//			}
//		}

//		if(!inpCtrl.rightPressed && !inpCtrl.leftPressed && !inpCtrl.upPressed && !inpCtrl.downPressed) {
//			if(spriteSheet.getSpriteAnimName() == "walkBack") {
//				spriteSheet.setSpriteAnim("idleBack");
//			}else if(spriteSheet.getSpriteAnimName() == "walkFront") {
//				spriteSheet.setSpriteAnim("idleFront");
//			}else if(spriteSheet.getSpriteAnimName() == "walkRight") {
//				spriteSheet.setSpriteAnim("idleRight");
//			}else if(spriteSheet.getSpriteAnimName() == "walkLeft") {
//				spriteSheet.setSpriteAnim("idleLeft");
//			}
//		}
		//Update direction and location of players Vertical Movement
//		if(inpCtrl.upPressed ^ inpCtrl.downPressed) {
//			if(inpCtrl.upPressed) {
//				y -= speed;
//				if(!(inpCtrl.rightPressed || inpCtrl.leftPressed)) {
//					spriteSheet.setSpriteAnim("walkBack");
//					direction = Player.NORTH;
//				}
//			}else {
//				y += speed;
//				if(!(inpCtrl.rightPressed || inpCtrl.leftPressed)) {
//					spriteSheet.setSpriteAnim("walkFront");
//					direction = Player.SOUTH;
//				}
//			}
//		}
//
//		//Update direction and location of players Horizontal Movement
//		if(inpCtrl.rightPressed ^ inpCtrl.leftPressed) {
//			if(inpCtrl.rightPressed) {
//				x += speed;
//				if(!(inpCtrl.upPressed || inpCtrl.downPressed)) {
//					spriteSheet.setSpriteAnim("walkRight");
//					direction = Player.EAST;
//				}
//			}else {
//				x -= speed;
//				if(!(inpCtrl.upPressed || inpCtrl.downPressed)) {
//					spriteSheet.setSpriteAnim("walkLeft");
//					direction = Player.WEST;
//				}
//			}
//		}
		
		spriteSheet.updateSpriteFrame();
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = spriteSheet.getSpriteFrame();
		g2.drawImage(image, xLoc, yLoc, world.getTileScale() * image.getWidth(), world.getTileScale() * image.getHeight(), null);
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
