package Entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Trail2 {
	
	int x;
	int y;
	boolean collidable;
	long elapsed;
	long start;
	
	
	public Trail2(int x, int y){
		
		this.x = x;
		this.y = y;
		
		start = System.nanoTime();
		
	}

	public void update(){
		
		elapsed = (System.nanoTime()-start)/1000000;
		
		if(elapsed > 500 ){
			collidable = true;
		}
		
	}
	public void draw(Graphics2D g){
		g.setColor(Color.CYAN);
		g.fillRect(x, y, 6, 6);
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,6,6);
	}
	
	public boolean getCollidable(){return collidable;}
	
}
