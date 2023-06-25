package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import gameEngine.InputController;
import gameEngine.WorldData;

/**
 * Player contains the data and functions necessary for the player entity.
 * @author tyson
 *
 */
public class Player extends Entity{
	
	private InputController inpCtrl;
	private WorldData world;
	final int defaultSpeed;
	final int defaultDiagonalSpeed;
	private String animationDirection;
	private String animationType;
	
	public static final String NORTH = "North";
	public static final String EAST = "East";
	public static final String SOUTH = "South";
	public static final String WEST = "West";
	
	/**
	 * Constructor for the Player entity.
	 * @param inpCtrl InputController for the player
	 * @param world The world in which the player entity resides
	 */
	public Player(InputController inpCtrl, WorldData world) {
		this.inpCtrl = inpCtrl;
		this.world = world;
		
		//Set default location
		this.x = 100;
		this.y = 100;
		
		this.animationDirection = Player.SOUTH;
		this.animationType = SpriteSheet.IDLE;
		
		//Set speed of character
		this.defaultSpeed = 6;
		this.speed = defaultSpeed;
		double sine45 = 0.707;
		this.defaultDiagonalSpeed = (int)Math.round(sine45 * defaultSpeed);
		
		try {
			this.spriteSheet = new SpriteSheet("/player/player.png", world.getBaseTileSize(), world.getFPS());
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
			animationType = SpriteSheet.IDLE;
		}else if((inpCtrl.rightPressed ^ inpCtrl.leftPressed) || (inpCtrl.upPressed ^ inpCtrl.downPressed)) {
			animationType = SpriteSheet.WALK;
		}else {
			animationType = SpriteSheet.IDLE;
		}
		
		//Determine Animation Direction
		if(inpCtrl.rightPressed && inpCtrl.upPressed && (animationDirection != Player.NORTH || animationDirection != Player.EAST)) {
			animationDirection = Player.NORTH;
		}else if(inpCtrl.leftPressed && inpCtrl.upPressed && (animationDirection != Player.NORTH || animationDirection != Player.WEST)) {
			animationDirection = Player.NORTH;
		}else if(inpCtrl.leftPressed && inpCtrl.downPressed && (animationDirection != Player.SOUTH || animationDirection != Player.WEST)) {
			animationDirection = Player.SOUTH;
		}else if(inpCtrl.rightPressed && inpCtrl.downPressed && (animationDirection != Player.SOUTH || animationDirection != Player.EAST)) {
			animationDirection = Player.SOUTH;
		}else if(inpCtrl.upPressed){
			animationDirection = Player.NORTH;
		}else if(inpCtrl.downPressed){
			animationDirection = Player.SOUTH;
		}else if(inpCtrl.rightPressed){
			animationDirection = Player.EAST;
		}else if(inpCtrl.leftPressed){
			animationDirection = Player.WEST;
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
		
		spriteSheet.setSpriteAnim(animationType + animationDirection);
		
		
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
		g2.drawImage(image, x, y, world.getTileSize(), world.getTileSize(), null);
	}
}
