package scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import gameEngine.GameManager;

/**
 * Represents the base of a level or scene. It constitutes the background and manages any data associated with it.
 * @author tyson
 *
 */
public class Map extends Entity{
	private int xPos;
	private int yPos;
	
	private BufferedImage map;
	private SceneTopDown scene;

	/**\
	 * Creates a background image for a scene at the specified x and y coordinates from the filepath given.
	 * @param scene the parent scene for the background
	 * @param xPos x coordinate of image on screen
	 * @param yPos y coordinate of image on screen
	 * @param imageFilePath file path to the image for the background
	 */
	public Map(SceneTopDown scene, int xPos, int yPos, String imageFilePath) {
		this.scene = scene;
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			map = ImageIO.read(getClass().getResourceAsStream(imageFilePath));
		}catch(IOException e) {
			//TODO: Properly handle exception and print to logs
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the position of the map.
	 * @param deltaX change in x direction
	 * @param deltaY change in y direction
	 */
	public void updatePos(int deltaX, int deltaY) {
		xPos += deltaX;
		yPos += deltaY;
	}
	
	/**
	 * Updates the x position of the map.
	 * @param deltaX change in x direction
	 */
	public void updateXPos(int deltaX) {
		xPos += deltaX;
	}
	
	/**
	 * Updates the y position of the map.
	 * @param deltaY change in y direction
	 */
	public void updateYPos(int deltaY) {
		yPos += deltaY;
	}
	
	/**
	 * Gets the x coordinate of the background that corresponds to the upper left hand corner of the image.
	 * @return x coordinate
	 */
	public int getXPos() {
		return xPos;
	}
	
	/**
	 * Gets the y coordinate of the background that corresponds to the upper left hand corner of the image.
	 * @return y coordinate
	 */
	public int getYPos() {
		return yPos;
	}
	
	/**
	 * Gets the width of the map image. The number returned is the number of pixels after scaling.
	 * @return width of the map image
	 */
	public int getMapWidth() {
		return map.getWidth() * scene.getScale();
	}
	
	/**
	 * Gets the height of the map image. The number returned is the number of pixels after scaling.
	 * @return height of the map image
	 */
	public int getMapHeight() {
		return map.getHeight() * scene.getScale();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g2) {
		// TODO Auto-generated method stub
		g2.drawImage(map, xPos, yPos, scene.getScale() * map.getWidth(), scene.getScale() * map.getHeight(), null);
	}
	
}
