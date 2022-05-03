package graphics;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class PixelGrid extends JPanel {
	private static final long serialVersionUID = 7148504528835036003L;
	private Color[][] pixels = new Color[][] {{Color.CYAN}};
	
	public void setPixels(Color[][] pixels) {
		this.pixels = pixels;
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);

        int pixelSize = Math.min(getWidth()/pixels.length, getHeight()/pixels[0].length);
        
        for(int i = 0; i < pixels.length; i++) {
        	for(int j = 0; j < pixels[0].length; j++) {
        		g.setColor(pixels[i][j]);
        		g.fillRect(i*pixelSize, j*pixelSize, pixelSize, pixelSize);
        	}
        }
    }
}