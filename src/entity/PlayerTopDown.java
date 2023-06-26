package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Properties;

import gameEngine.InputController;
import gameEngine.WorldData;

/**
 * Player contains the data and functions necessary for the player entity.
 * @author tyson
 *
 */
public class PlayerTopDown extends Entity{
	
	private InputController inpCtrl;
	private WorldData world;
	final int defaultSpeed;
	final int defaultDiagonalSpeed;
	
	public static final String NORTH = "North";
	public static final String EAST = "East";
	public static final String SOUTH = "South";
	public static final String WEST = "West";
	
	/**
	 * Constructor for the Player entity.
	 * @param inpCtrl InputController for the player
	 * @param world The world in which the player entity resides
	 */
	public PlayerTopDown(InputController inpCtrl, WorldData world, String playerPropFilePath, int xLoc, int yLoc) {
		this.inpCtrl = inpCtrl;
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
		this.x = xLoc;
		this.y = yLoc;
		
		//Set speed of character
		this.defaultSpeed = Integer.parseInt(playerProps.getProperty("default_speed"));
		this.speed = defaultSpeed;
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
		return inpCtrl.upPressed && inpCtrl.leftPressed || inpCtrl.upPressed && inpCtrl.rightPressed || inpCtrl.downPressed && inpCtrl.leftPressed || inpCtrl.downPressed && inpCtrl.rightPressed;
	}

	@Override
	public void update() {
		//TODO: There's got to be a more efficient way to calculate all this dynamically
		//Account for diagonal speed
		speed = (isDiagonalDirection() ? defaultDiagonalSpeed : defaultSpeed);
		
		//Determine Animation Type
		if(!inpCtrl.rightPressed && !inpCtrl.leftPressed && !inpCtrl.upPressed && !inpCtrl.downPressed) {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}else if((inpCtrl.rightPressed ^ inpCtrl.leftPressed) || (inpCtrl.upPressed ^ inpCtrl.downPressed)) {
			spriteSheet.setAnimType(SpriteSheet.WALK);
		}else {
			spriteSheet.setAnimType(SpriteSheet.IDLE);
		}
		
		//Determine Animation Direction
		if(inpCtrl.rightPressed && inpCtrl.upPressed && (spriteSheet.getAnimDirection() != PlayerTopDown.NORTH || spriteSheet.getAnimDirection() != PlayerTopDown.EAST)) {
			spriteSheet.setAnimDirection(PlayerTopDown.NORTH);
		}else if(inpCtrl.leftPressed && inpCtrl.upPressed && (spriteSheet.getAnimDirection() != PlayerTopDown.NORTH || spriteSheet.getAnimDirection() != PlayerTopDown.WEST)) {
			spriteSheet.setAnimDirection(PlayerTopDown.NORTH);
		}else if(inpCtrl.leftPressed && inpCtrl.downPressed && (spriteSheet.getAnimDirection() != PlayerTopDown.SOUTH || spriteSheet.getAnimDirection() != PlayerTopDown.WEST)) {
			spriteSheet.setAnimDirection(PlayerTopDown.SOUTH);
		}else if(inpCtrl.rightPressed && inpCtrl.downPressed && (spriteSheet.getAnimDirection() != PlayerTopDown.SOUTH || spriteSheet.getAnimDirection() != PlayerTopDown.EAST)) {
			spriteSheet.setAnimDirection(PlayerTopDown.SOUTH);
		}else if(inpCtrl.upPressed){
			spriteSheet.setAnimDirection(PlayerTopDown.NORTH);
		}else if(inpCtrl.downPressed){
			spriteSheet.setAnimDirection(PlayerTopDown.SOUTH);
		}else if(inpCtrl.rightPressed){
			spriteSheet.setAnimDirection(PlayerTopDown.EAST);
		}else if(inpCtrl.leftPressed){
			spriteSheet.setAnimDirection(PlayerTopDown.WEST);
		}
		
		//Update location
		if(inpCtrl.upPressed) {
			y -= speed;
		}
		if(inpCtrl.downPressed) {
			y += speed;
		}
		if(inpCtrl.leftPressed) {
			x -= speed;
		}
		if(inpCtrl.rightPressed) {
			x += speed;
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
		g2.drawImage(image, x, y, world.getTileScale() * image.getWidth(), world.getTileScale() * image.getHeight(), null);
	}
}
