package scene;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;

/**
 * Represents the base of a level or scene. It constitutes the background and manages any data associated with it.
 * @author tyson
 *
 */
public class Map extends Entity{

	/**\
	 * Creates a background image for a scene at the specified x and y coordinates from the filepath given.
	 * @param scene the parent scene for the background
	 * @param xPos x coordinate of image on screen
	 * @param yPos y coordinate of image on screen
	 * @param imageFilePath file path to the image for the background
	 */
	public Map(SceneTopDown scene, String imageFilePath, int xPos, int yPos) {
		this.scene = scene;
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imageFilePath));
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
//	
//	/**
//	 * Gets the width of the map image. The number returned is the number of pixels after scaling.
//	 * @return width of the map image
//	 */
//	public int getMapWidth() {
//		return image.getWidth() * scene.getScale();
//	}
//	
//	/**
//	 * Gets the height of the map image. The number returned is the number of pixels after scaling.
//	 * @return height of the map image
//	 */
//	public int getMapHeight() {
//		return image.getHeight() * scene.getScale();
//	}
	
	/**
	 * Gets the width of the map in pixels. This value is before scaling.
	 * @return
	 */
	public int getRawMapWidth() {
		return image.getWidth();
	}
	
	/**
	 * Gets the height of the map in pixels. This value is before scaling.
	 * @return
	 */
	public int getRawMapHeight() {
		return image.getHeight();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}	
}
