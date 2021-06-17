package Perlin2D;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		Fenetre fen = new Fenetre();
		fen.setTitle("Perlin Noise");
		fen.setResizable(false);
		fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fen.pack();
		fen.setVisible(true);
	}
}



