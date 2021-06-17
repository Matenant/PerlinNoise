package Perlin1D;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	private Panel pan = new Panel();
	private Bruit n = new Bruit();
	
	public Fenetre() {
		
		pan.setPreferredSize(new Dimension(500, 255));
		
		this.add(pan);
	}
	
	class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			for(int i = 0; i < 500; i++) {
				int valeur = (int) ((n.Noise(i, 50)+1)*0.5*255);
				int valeur2 = (int) ((n.Noise(i+1, 50)+1)*0.5*255);
				g.drawLine(i, valeur%256, i+1, valeur2%256);
			}
		}
	}
}



