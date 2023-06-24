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
	private String direction = "front";
	
	/**
	 * Constructor for the Player entity.
	 * @param inpCtrl InputController for the player
	 * @param world The world in which the player entity resides
	 */
	public Player(InputController inpCtrl, WorldData world) {
		this.inpCtrl = inpCtrl;
		this.world = world;
		this.x = 100;
		this.y = 100;
		this.speed = 4;
		try {
			this.spriteSheet = new SpriteSheet("/player/player.png", world.getBaseTileSize());
		}catch (IOException e) {
			//TODO: Log instead of printing to screen
			System.out.println("Sprite Sheet could not be loaded.");
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		//TODO: Account for diagonal speed
		//Default to front direction if two contradictory directions are pressed simultaneously.
		if(inpCtrl.upPressed && inpCtrl.downPressed || inpCtrl.rightPressed && inpCtrl.leftPressed) {
			direction = "front";
		}else {
			if(inpCtrl.upPressed) {
				direction = "back";
				y -= speed;
			}
			if(inpCtrl.downPressed) {
				direction = "front";
				y += speed;
			}
			if(inpCtrl.rightPressed) {
				direction = "right";
				x += speed;
			}
			if(inpCtrl.leftPressed) {
				direction = "left";
				x -= speed;
			}
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		BufferedImage image = spriteSheet.getSprite(direction);
		g2.drawImage(image, x, y, world.getTileSize(), world.getTileSize(), null);
		//Do not g2.dispose()
	}
}
