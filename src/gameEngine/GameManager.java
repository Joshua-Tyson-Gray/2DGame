package gameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Controls the game loop and the generation of the window. Intended to be dynamic so the WorldData that is passed defines the game data.
 * @author tyson
 *
 */
public class GameManager extends JPanel implements Runnable{
	
	private final int FPS;
	private final int screenWidth;
	private final int screenHeight;
	
	private Thread gameThread;
	private WorldData world;
	
	public GameManager(WorldData world, int FPS){
		this.world = world;
		this.FPS = FPS;
		//TODO: It would be better to determine the screen size first and use that to dictate how large the tiles will be instead.
		screenWidth = world.getTileSize() * world.getMaxScreenCol();
		screenHeight = world.getTileSize() * world.getMaxScreenRow();
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		this.addKeyListener(world.getInputController());
		this.setFocusable(true);
	}
	
	/**
	 * Creates and starts the game thread.
	 */
	public void startGame() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		//Game Loop
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) {
				world.update();
				repaint();
				delta--;
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		world.render(g2);
		g2.dispose();
	}
	
/* =============UNIT TESTING================== */
	public static void main (String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Game");
		int FPS = 60;
		GameManager gm = new GameManager(new WorldData(new InputController()), FPS);
		
		window.add(gm);
		gm.startGame();
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
