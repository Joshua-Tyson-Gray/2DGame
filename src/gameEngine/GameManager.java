package gameEngine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameManager extends JPanel implements Runnable{
	
	
	private InputController inpCtrl = new InputController();
	private Thread gameThread;
	private WorldData world;
	private final int FPS = 60;
	private final int screenWidth;
	private final int screenHeight;
	
	public GameManager(){
		world = new WorldData(inpCtrl);
		
		//TODO: It would be better to determine the screen size first and use that to dictate how large the tiles will be instead.
		screenWidth = world.getTileSize() * world.getMaxScreenCol();
		screenHeight = world.getTileSize() * world.getMaxScreenRow();
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.DARK_GRAY);
		this.setDoubleBuffered(true);
		this.addKeyListener(inpCtrl);
		this.setFocusable(true);
	}
	
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
	
	//TODO: This will need to change as I get further into development
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		world.render(g2);
		g2.dispose();
	}
	
	public static void main (String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Game");
		
		GameManager gm = new GameManager();
		window.add(gm);
		gm.startGame();
		
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
