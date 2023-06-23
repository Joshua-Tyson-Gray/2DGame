package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import gameEngine.InputController;
import gameEngine.WorldData;

public class Player extends Entity{
	
	private InputController inpCtrl;
	private WorldData world;
	
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
	}

	@Override
	public void update() {
		if(inpCtrl.upPressed) {
			y -= speed;
		}
		if(inpCtrl.downPressed) {
			y += speed;
		}
		if(inpCtrl.rightPressed) {
			x += speed;
		}
		if(inpCtrl.leftPressed) {
			x -= speed;
		}
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.setColor(Color.WHITE);
		g2.fillRect(x, y, world.getTileSize(), world.getTileSize());
	}
}
