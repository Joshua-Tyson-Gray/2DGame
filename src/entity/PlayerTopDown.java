package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import gameEngine.GameManager;
import scene.SceneTopDown;

/**
 * Player contains the data and functions necessary for the player entity.
 * @author tyson
 *
 */
public class PlayerTopDown extends EntityTopDown{
	
	/**
	 * Constructor for the Player entity.
	 * @param scene The world in which the player entity resides
	 * @param playerPropFilePath properties file for the player configuration
	 * @param xLoc The X location of the player
	 * @param yLoc The Y location of the player
	 * @param playerLocked indicated if the players position is locked in place at the coordinates provided
	 */
	public PlayerTopDown(SceneTopDown scene, String playerPropFilePath, int xLoc, int yLoc) {
		this.scene = scene;
		
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
		this.xPos = xLoc;
		this.yPos = yLoc;
		
		//Set speed of character
		this.defaultSpeed = Integer.parseInt(playerProps.getProperty("default_speed"));
		this.currentSpeed = defaultSpeed;
		double sine45 = 0.707;
		this.defaultDiagonalSpeed = (int)Math.round(sine45 * defaultSpeed);
		
		try {
			this.spriteSheet = new SpriteSheet(playerProps, GameManager.getInstance().getFPS());
		}catch (IOException e) {
			//TODO: Log instead of printing to screen
			System.out.println("Sprite Sheet could not be loaded.");
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		//TODO: There's got to be a more efficient way to calculate all this dynamically
		//Account for diagonal speed
		currentSpeed = (scene.isDiagonalDirection() ? defaultDiagonalSpeed : defaultSpeed);
		
		//Determine Animation Type
		if(!scene.rightPressed && !scene.leftPressed && !scene.upPressed && !scene.downPressed) {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}else if((scene.rightPressed ^ scene.leftPressed) || (scene.upPressed ^ scene.downPressed)) {
			spriteSheet.setAnimType(SpriteSheet.WALK);
		}else {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}
		
		//Determine Animation Direction
		if(scene.rightPressed && scene.upPressed && (spriteSheet.getAnimDirection() != NORTH || spriteSheet.getAnimDirection() != EAST)) {
			spriteSheet.setAnimDirection(NORTH);
		}else if(scene.leftPressed && scene.upPressed && (spriteSheet.getAnimDirection() != NORTH || spriteSheet.getAnimDirection() != WEST)) {
			spriteSheet.setAnimDirection(NORTH);
		}else if(scene.leftPressed && scene.downPressed && (spriteSheet.getAnimDirection() != SOUTH || spriteSheet.getAnimDirection() != WEST)) {
			spriteSheet.setAnimDirection(SOUTH);
		}else if(scene.rightPressed && scene.downPressed && (spriteSheet.getAnimDirection() != SOUTH || spriteSheet.getAnimDirection() != EAST)) {
			spriteSheet.setAnimDirection(SOUTH);
		}else if(scene.upPressed){
			spriteSheet.setAnimDirection(NORTH);
		}else if(scene.downPressed){
			spriteSheet.setAnimDirection(SOUTH);
		}else if(scene.rightPressed){
			spriteSheet.setAnimDirection(EAST);
		}else if(scene.leftPressed){
			spriteSheet.setAnimDirection(WEST);
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
	public void render(Graphics2D g2) {
		BufferedImage image = spriteSheet.getSpriteFrame();
		int exactXPos = xPos - ( (image.getWidth() * scene.getScale()) / 2 );
		int exactYPos = yPos - ( (image.getHeight() * scene.getScale()) / 2 );
		g2.drawImage(image, exactXPos, exactYPos, scene.getScale() * image.getWidth(), scene.getScale() * image.getHeight(), null);
	}
}
