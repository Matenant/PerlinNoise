package Perlin2D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Fenetre extends JFrame {
	
	private Panel pan = new Panel();
	private Bruit n = new Bruit();
	
	public Fenetre() {
		
		pan.setPreferredSize(new Dimension(500, 500));
		
		this.add(pan);
	}
	
	class Panel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int valeur;
			for(int i = 0; i < 500; i+=1) {
				for(int j = 0; j < 500; j+=1) { 
					valeur = (int) ((n.Noise(i, j, 50)+1)*0.5*255);
					//Pour éviter l'erreur avec l'interpolation d'hermite
					//valeur = Math.abs(valeur) % 256;
					//Ligne pour l'aléatoire complet
					//valeur = (int) (Math.random() *255);
					g.setColor(new Color(valeur, valeur, valeur));
					g.fillRect(i, j, 1, 1);
				}
			}
		}
	}
}



