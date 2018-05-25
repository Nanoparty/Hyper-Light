package Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import Audio.AudioPlayer;
import Entities.Player1;
import Entities.Player2;
import Entities.Trail1;
import Entities.Trail2;


@SuppressWarnings("serial")
public class GamePanel extends JPanel 
	implements Runnable, KeyListener{
	
	// dimensions
	public static final int WIDTH = 500;
	public static final int HEIGHT = 350;
	
	boolean invincible = false;
	
	private AudioPlayer mainMusic;
	
	
		
	
	
	
	private int score1 = 0;
	private int score2 = 0;
	
	// game thread
	private Thread thread;
	private boolean running;
	private boolean end;
	private boolean menu;
	boolean repeat;
	
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	private boolean player1;
	
	// image
	private BufferedImage image;
	private Graphics2D g;
	
	ArrayList<Trail2> Gsquares;
	ArrayList<Trail1> Bsquares;
	
	// game state manager
	Player1 p;
	Player2 u;

	public GamePanel() {
		super();
		setPreferredSize(
			new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		setBackground(Color.BLACK);
				
		menu = true;
		
		try{
			
			
			Font Technonomicon = Font.createFont(Font.TRUETYPE_FONT, new File("Technonomicon.ttf")) .deriveFont(12f);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("Technonomicon.ttf")));
			
		}catch(IOException e){
			e.printStackTrace();
		}catch(FontFormatException e){
			e.printStackTrace();
		}
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		
		image = new BufferedImage(
					WIDTH, HEIGHT,
					BufferedImage.TYPE_INT_RGB
				);
		g = (Graphics2D) image.getGraphics();
		
		running = true;
		p = new Player1("/cyan.png");
		u = new Player2("/pink.png");
		
		Gsquares = new ArrayList<Trail2>();
		Bsquares = new ArrayList<Trail1>();
		
		mainMusic = new AudioPlayer("/technicko.mp3");
		mainMusic.playLoop();
		
		
		
		
		
		
		
	}
	
	public void run() {
		
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(menu){
			g.drawImage(p.getBackground(),0,0,null);
			g.setColor(Color.cyan);
			g.setFont(new Font("Technonomicon", Font.BOLD, 36));
			g.drawString("Light", 50, 140);
			g.setColor(Color.CYAN);
			g.drawString("Cycle",260,140);
			g.setFont(new Font("Technonomicon", Font.BOLD, 18));
			g.setColor(Color.PINK);
			g.drawString("<ENTER>",190,250);
			drawToScreen();
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0,0,WIDTH,HEIGHT);
		g.drawImage(p.getBackground(),0,0,null);
		drawToScreen();
		
		
		try{
			Thread.sleep(1000);
		}catch(Exception e){}
		
		
		while(repeat){
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			g.drawImage(p.getBackground(),0,0,null);
			g.setColor(Color.CYAN);
			g.setFont(new Font("Technonomicon",Font.BOLD, 56));
			g.drawString("3",210,169);
			drawToScreen();
			
			try{
				Thread.sleep(500);
			}catch(Exception e){}
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			g.drawImage(p.getBackground(),0,0,null);
			g.setColor(Color.CYAN);
			g.drawString("2", 210, 169);
			drawToScreen();
			
			try{
				Thread.sleep(500);
			}catch(Exception e){}
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			g.drawImage(p.getBackground(),0,0,null);
			g.setColor(Color.CYAN);
			g.drawString("1", 210, 169);
			drawToScreen();

			try{
				Thread.sleep(500);
			}catch(Exception e){}
			
			g.setColor(Color.BLACK);
			g.fillRect(0,0,WIDTH,HEIGHT);
			g.drawImage(p.getBackground(),0,0,null);
			g.setColor(Color.CYAN);
			g.drawString("Go", 200, 169);
			drawToScreen();

			try{
				Thread.sleep(200);
			}catch(Exception e){}
		
		// game loop
		while(running) {
			
			start = System.nanoTime();
				
			
			update();
			
			draw();
			drawToScreen();
			Collide();
			
			
			elapsed = System.nanoTime() - start;
			
			wait = targetTime - elapsed / 1000000;
			if(wait < 0) wait = 5;
			
			try {
				Thread.sleep(wait);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		while(end){
			
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(p.getBackground(),0,0,null);
		g.setFont(new Font("Technonomicon", Font.BOLD, 24));
		if(player1){
		g.setColor(Color.PINK);
		g.drawString("Player 2 Wins", 100,150);
		}else{
		g.setColor(Color.CYAN);
		g.drawString("Player 1 Wins", 100,150);
		}
		g.setColor(Color.CYAN);
		g.setFont(new Font("Technonomicon", Font.BOLD, 14));
		g.drawString("Continue <ENTER>",130,225);
		drawToScreen();
		}
		
		}
		
		
	}
	
	private void update() {
		
		p.update();
		u.update();
		
		
		
		Gsquares.add(new Trail2(p.getX(),p.getY()));
		Bsquares.add(new Trail1(u.getX(),u.getY()));
		
		
		
		if(p.getAlive() == false){
			player1 = true;
			end = true;
			running = false;
			score2++;
		}
		if(u.getAlive() == false){
			player1 = false;
			end = true;
			running = false;
			score1++;
		
		}
		
		for(int i=0; i < Bsquares.size();i++){
			Bsquares.get(i).update();
		}
		for(int i=0; i < Gsquares.size();i++){
			Gsquares.get(i).update();
		}
		
		
		
	}
	private void draw() {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.drawImage(p.getBackground(),0,0,null);
		
		for(int i = 0; i < Gsquares.size();i++){
			Gsquares.get(i).draw(g);
		}
		for(int i = 0; i < Bsquares.size();i++){
			Bsquares.get(i).draw(g);
		}
		p.draw(g);
		u.draw(g);
		
		g.setFont(new Font("Technonomicon",Font.BOLD, 18));
		g.setColor(Color.CYAN);
		g.drawString(score1 + "", 20,20);
		g.setColor(Color.PINK);
		g.drawString(score2 + "", 450, 20);
	}
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,
				WIDTH, HEIGHT,
				null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W){
			if(p.getDown() == false){
			p.setVector(0,-2);
			p.setUp(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			if(p.getUp() == false){
			p.setVector(0,2);
			p.setDown(true);
			}
			
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			if(p.getRight() == false){
			p.setVector(-2,0);
			p.setLeft(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			if(p.getLeft() == false){
			p.setVector(2,0);
			p.setRight(true);
			}
		}
		
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(u.getRight() == false){
			u.setVector(-2, 0);
			u.setLeft(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(u.getLeft() == false){
			u.setVector(2,0);
			u.setRight(true);
			System.out.println("right");
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(u.getUp() == false){
			u.setVector(0,2);
			u.setDown(true);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP){
			if(u.getDown() == false){
			u.setVector(0, -2);
			u.setUp(true);
			}
		}
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(menu){
					repeat = true;
					menu = false;
				}
				
				
				if(end){

				
				running = true;
				end = false;
				Gsquares.clear();
				Bsquares.clear();
				p.setPosition(30,169);
				u.setPosition(458,169);
				p.setAlive(true);
				u.setAlive(true);
				p.setVector(2,0);
				u.setVector(-2,0);
				p.setRight(true);
				u.setLeft(true);
			}
		}
	}
	public void keyReleased(KeyEvent key) {
	}
	
	public void Collide(){
		
		Rectangle r1 = p.getBounds();
		
		for(int i = 0; i < Bsquares.size(); i++){
			Rectangle r2 = Bsquares.get(i).getBounds();
			
			if(r1.intersects(r2)){
				p.setAlive(false);

			}
		}
		
		Rectangle r3 = u.getBounds();
		
		for(int i = 0; i < Gsquares.size(); i++){
			Rectangle r4 = Gsquares.get(i).getBounds();
			
			if(r3.intersects(r4)){
				u.setAlive(false);
			}
		}
		
		
		Rectangle r5 = u.getBounds();
		
		for(int i = 0; i < Bsquares.size(); i++){
			
			if(Bsquares.get(i).getCollidable()){
				Rectangle r6 = Bsquares.get(i).getBounds();
				
				if(r5.intersects(r6)){
					u.setAlive(false);
				}
				
			}
		
		}
		
		Rectangle r7 = p.getBounds();
		
		for(int i = 0; i < Gsquares.size(); i++){
			
			if(Gsquares.get(i).getCollidable()){
				Rectangle r8 = Gsquares.get(i).getBounds();
				
				if(r7.intersects(r8)){
					p.setAlive(false);
				}
				
			}
		
		}
		
		
	}
	
	

}
















