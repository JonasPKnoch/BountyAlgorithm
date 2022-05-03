package graphics;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

public class GraphicsMain {
	
    public static void main(String[] args) {
    	PixelGrid grid = new PixelGrid();
    	
        JFrame frame = new JFrame("Bounty Algorithm");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(grid, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
