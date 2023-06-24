package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.awt.geom.AffineTransform;

import javax.imageio.ImageIO;

import gameEngine.InputController;
import gameEngine.WorldData;

public class Player extends Entity{
	
	private InputController inpCtrl;
	private WorldData world;
	private HashMap<String, int[]> spriteMap;
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
		loadSpriteSheet();
	}
	
	private void loadSpriteSheet() {
		spriteMap = new HashMap<String, int[]>();
		try {
			this.spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/player.png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		spriteMap.put("front", new int[]{8, 4});
		spriteMap.put("back", new int[]{72, 4});
		spriteMap.put("right", new int[]{42, 4});
		spriteMap.put("left", new int[]{42, 4});
	}
	
	/**
	 * Flips the sprite's image in the horizontal direction
	 * @param img The image to flip
	 * @return A BufferedImage of the flipped sprite.
	 */
	private BufferedImage flipSpriteHorizontally(BufferedImage img) {
		AffineTransform at = new AffineTransform();
		at.concatenate(AffineTransform.getScaleInstance(-1, 1));
		at.concatenate(AffineTransform.getTranslateInstance(-img.getWidth(), 0));
		BufferedImage flippedSprite = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = flippedSprite.createGraphics();
		g.transform(at);
		g.drawImage(img, 0, 0, null);
		g.dispose();
		return flippedSprite;
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
		BufferedImage image = this.spriteSheet.getSubimage(spriteMap.get(direction)[0], spriteMap.get(direction)[1], world.getBaseTileSize(), world.getBaseTileSize());
		if(direction == "right") {
			image = flipSpriteHorizontally(image);
		}
		g2.drawImage(image, x, y, world.getTileSize(), world.getTileSize(), null);
	}
}
