package gameEngine;

public class WorldData {
	final int baseTileSize = 16;
	final int tileScale = 3;
	final int tileSize = baseTileSize * tileScale;
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	InputController inpCtrl;
	public WorldData(InputController inpCtrl) {
		this.inpCtrl = inpCtrl;
	}
	
	public void update() {
		//Depending on the state of the game, different components may be updated
		if(inpCtrl.upPressed) {
			playerY -= playerSpeed;
		}
		if(inpCtrl.downPressed) {
			playerY += playerSpeed;
		}
		if(inpCtrl.rightPressed) {
			playerX += playerSpeed;
		}
		if(inpCtrl.leftPressed) {
			playerX -= playerSpeed;
		}
	}
}
