package entity;

import java.io.IOException;

import javax.imageio.ImageIO;

import scene.SceneTopDown;

public class WorldObject extends EntityTopDown{

	public WorldObject(SceneTopDown scene, String filepath, int xPos, int yPos){
		this.scene = scene;
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(filepath));
		}catch(IOException e) {
			//TODO: print to log instead of to console
			System.out.println("Asset image could not be loaded (" + filepath + ")");
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		// Nothing to update
		
	}
}
