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
		
		//Set speed of character
		this.defaultSpeed = 4;
		this.speed = defaultSpeed;
		double sine45 = 0.707;
		this.defaultDiagonalSpeed = (int)Math.round(sine45 * defaultSpeed);
		
		try {
			this.spriteSheet = new SpriteSheet("/player/player.png", world.getBaseTileSize());
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
		//TODO: Fix all the nested if statements
		//Account for diagonal speed
		speed = (isDiagonalDirection() ? defaultDiagonalSpeed : defaultSpeed);

		if(!inpCtrl.rightPressed && !inpCtrl.leftPressed && !inpCtrl.upPressed && !inpCtrl.downPressed) {
			if(spriteSheet.getSpriteAnimName() == "walkBack") {
				spriteSheet.setSpriteAnim("idleBack");
			}else if(spriteSheet.getSpriteAnimName() == "walkFront") {
				spriteSheet.setSpriteAnim("idleFront");
			}else if(spriteSheet.getSpriteAnimName() == "walkRight") {
				spriteSheet.setSpriteAnim("idleRight");
			}else if(spriteSheet.getSpriteAnimName() == "walkLeft") {
				spriteSheet.setSpriteAnim("idleLeft");
			}
		}
		//Update direction and location of players Vertical Movement
		if(inpCtrl.upPressed ^ inpCtrl.downPressed) {
			if(inpCtrl.upPressed) {
				y -= speed;
				if(!(inpCtrl.rightPressed || inpCtrl.leftPressed)) {
					spriteSheet.setSpriteAnim("walkBack");
				}
			}else {
				y += speed;
				if(!(inpCtrl.rightPressed || inpCtrl.leftPressed)) {
					spriteSheet.setSpriteAnim("walkFront");
				}
			}
		}

		//Update direction and location of players Horizontal Movement
		if(inpCtrl.rightPressed ^ inpCtrl.leftPressed) {
			if(inpCtrl.rightPressed) {
				x += speed;
				if(!(inpCtrl.upPressed || inpCtrl.downPressed)) {
					spriteSheet.setSpriteAnim("walkRight");
				}
			}else {
				x -= speed;
				if(!(inpCtrl.upPressed || inpCtrl.downPressed)) {
					spriteSheet.setSpriteAnim("walkLeft");
				}
			}
		}
		
		spriteSheet.updateSpriteFrame();
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = spriteSheet.getSpriteFrame();
		g2.drawImage(image, x, y, world.getTileSize(), world.getTileSize(), null);
	}
}
