package gameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

import scene.SceneTopDown;

/**
 * Controls the game loop and the generation of the window. Is a singleton so there can only ever be one manager.
 * @author tyson
 *
 */
public class GameManager extends JPanel implements Runnable{
	
	// Singleton members
	private static GameManager gm = null;
	
	public static GameManager getInstance() {
		if(gm == null) {
			gm = new GameManager();
		}
		return gm;
	}
	
	// Class members
	private final int FPS;
	private final int windowWidth;
	private final int windowHeight;
	
	private Thread gameThread;
	private SceneTopDown scene;
	
	private GameManager(){
		this.FPS = 60;
		//TODO: make screen calculation dynamic based on system
		//TODO: determine scale of the scene here, maybe completely move it here from Scene
		windowWidth = 1024;
		windowHeight = 768;
		this.setPreferredSize(new Dimension(windowWidth, windowHeight));
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		// TODO: Could there be multiple? In the case of multiplayer?
		this.setFocusable(true);
	}
	
	/**
	 * Loads the passed scene into the game as the current scene.
	 * @param scene
	 */
	public void loadScene(SceneTopDown scene) {
		this.scene = scene;
		this.addKeyListener(scene);
	}
	
	/**
	 * Gets the height of the window.
	 * @return height of the window
	 */
	public int getWindwoHeight() {
		return windowHeight;
	}
	
	/**
	 * Gets the width of the window.
	 * @return width of the window
	 */
	public int getWindowWidth() {
		return windowWidth;
	}
	
	/**
	 * Creates and starts the game thread.
	 */
	public void startGame() {
		//TODO: Error handeling if scene is not loaded
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	/**
	 * Gets the frame rate for the game loop.
	 * @return frame rate
	 */
	public int getFPS() {
		return FPS;
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		//Game Loop
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				scene.updateScene();
				repaint();
				delta--;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		scene.renderScene(g2);
		g2.dispose();
	}
	
/* =============UNIT TESTING================== */
	public static void main (String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Game");
		
		GameManager gm = GameManager.getInstance();
		window.add(gm);
		gm.loadScene(new SceneTopDown());
		gm.startGame();
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
