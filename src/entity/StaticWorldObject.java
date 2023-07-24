package entity;

import java.io.IOException;
import java.util.Properties;

import scene.SceneTopDown;

/**
 * A StaticWorldObject is an asset in the scene environment that does not animate.
 * @author tyson
 *
 */
public class StaticWorldObject extends EntityTopDown{

	public StaticWorldObject(SceneTopDown scene, String filepath, int xPos, int yPos){
		this.scene = scene;
		this.xPos = xPos;
		this.yPos = yPos;
		try {
			Properties props = new Properties();
			props.setProperty("spriteSheet_path", filepath);
			spriteSheet = new SpriteSheet(props);
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
