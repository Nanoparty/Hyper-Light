package Main;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args){
		
		JFrame frame = new JFrame("XP Game");
		frame.setContentPane(new GamePanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setVisible(true);
		frame.setLocation(500,200);
		
	}
}
