package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;
import javax.swing.Timer;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	//screen settings
	final int originalTileSize = 64;//64x64 title
	final int scale = 2;
	
	public final int tileSize = originalTileSize * scale; 
	public final int maxScreenCol = 12;
	public final int maxScreenRow = 6;
	public final int screenWidth = tileSize * maxScreenCol; 
	public final int screenHight = tileSize * maxScreenRow;
	
	//world set
	public final int maxWorldCol = 96;
	public final int maxWorldRow = 10;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHight = tileSize * maxWorldRow;
	
	int FPS = 60;
	//system
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Sound sound = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	Thread gameThread;
	//entity
	public Player player = new Player(this,keyH);
	public UI ui = new UI(this);
	//Game state
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int menuState = 2;
	
	public BufferedImage background;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHight));
		
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	public void setupGame() {
		playMusic(0);
	}
	
	public void setGame() {
		gameState = titleState;
	}
	
	public void startGameTread() {
		
		gameThread = new Thread(this);
		gameThread.start();
		gameState = playState;
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime()+drawInterval;
		
		while(gameThread != null) {		
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		
	}
	public void update() {
		if (gameState == playState) {
			player.update();
		}
		if (gameState == menuState) {
			
		}
		
	}
	public void getImage() {
		try {
		background = ImageIO.read(getClass().getResourceAsStream("/tiles/background.png"));
		} catch (IOException e) {
		e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		getImage();
		BufferedImage image = background;
		g.drawImage(image, 0, 0, worldWidth ,worldHight, null);
		Graphics2D g2 = (Graphics2D)g;
		tileM.draw(g2);
		player.draw(g2);
		ui.draw(g2);
		g2.dispose();
	}
	public void playMusic(int i) {
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	public void stopMusic() {
		sound.stop();
		
	}
	public void playSE(int i) {
		sound.setFile(i);
		sound.play();
	}

}
