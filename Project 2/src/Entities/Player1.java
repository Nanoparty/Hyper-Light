package Entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class Player1 {
	
	private BufferedImage image;
	private BufferedImage background;
	
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	
	private boolean alive;
	
	public Player1(String s){
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream(s));
			background = ImageIO.read(getClass().getResource("/lines.png"));
			dx = 1;
			dy = 1;
			y = 169;
			x = 458;
			alive = true;
			right = true;
		}catch(Exception e){}
	}
	
	public void setPosition(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public void setVector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public void update(){
		x += dx;
		y += dy;
		
		if(x > GamePanel.WIDTH)alive = false;
		if(x < 0)alive = false;
		if(y > GamePanel.HEIGHT)alive = false;
		if(y < 0)alive = false;
			
		
		
	}
	
	public void draw(Graphics2D g){
		
		g.drawImage(image, (int) x, (int)y, null);
		
		
		
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x,(int)y,6,6);
	}
	
	public int getX(){return (int)x;}
	public int getY(){return (int)y;}
	
	public void setLeft(boolean b){
		left = b;
		right = false;
		up = false;
		down = false;
		}
	public void setRight(boolean b){
		right = b;
		left = false;
		up = false;
		down = false;
		}
	public void setUp(boolean b){
		up = b;
		down = false;
		left = false;
		right = false;
		}
	public void setDown(boolean b){
		down = b;
		up = false;
		right = false;
		left = false;
		}
	
	public boolean getLeft(){return left;}
	public boolean getRight(){return right;}
	public boolean getUp(){return up;}
	public boolean getDown(){return down;}
	
	public void setAlive(boolean b){alive = b;}
	public boolean getAlive(){return alive;}
	
	public BufferedImage getBackground(){return background;}
	
	public void setX(double b){x = b;}
	public void setY(double b){y = b;}
}





















