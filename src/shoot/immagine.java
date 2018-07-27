package shoot;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

class immagine {
	public static void main(String[] args){
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disegno d = new disegno();
		Container c = f.getContentPane();
		c.add(d);
		f.setVisible(true);
		f.pack();
		d.salva();
		f.setSize(30, 30);

		
	}
}

class disegno extends JPanel {
	int larghezza;
	int altezzamin;
	int altezzamax;
	
	public disegno(){
		super();
		larghezza = 600; altezzamin = 80; altezzamax = 120;
		this.setPreferredSize(new Dimension(30,30));
	}
	
	public void salva(){
		File file = null;
		JFileChooser fc = null;
	    BufferedImage bImg = new BufferedImage(30, 30, BufferedImage.TYPE_INT_ARGB);

	    
	    Graphics2D cg = bImg.createGraphics();
	    //Color sfondo = cg.getBackground();
	    //cg.setBackground(sfondo);

		
		//cg.setColor(sfondo);
		//cg.fillRect(0, 0, 30, 30);
	    this.paint(cg);
        fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        try {
        	int returnVal = fc.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) 
            	file = fc.getSelectedFile();
            if (ImageIO.write(bImg, "png", new File(file.toString())))
            {
                System.out.println("-- saved");
            }
        }
        catch (SecurityException ex){
			System.out.println(" if a security manager exists and its checkWrite method denies write access to the file.");
        } catch (IOException x) {

	    }
	}
	
	public void paint(Graphics e){
		Graphics2D e2 = (Graphics2D)e;
//		Centro a 15,15
		Color sfondo = e2.getBackground();
		
		
		//e2.setColor(sfondo);
		//e2.fillRect(0, 0, 30, 30);
		/*
		e2.setColor(Color.YELLOW);
		e2.fillOval(7, 7, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(7,7, 10, 10);
		e2.setColor(Color.YELLOW);
		e2.fillOval(14, 16, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(14, 16, 10, 10);
		e2.setColor(Color.RED);
		e2.fillOval(9, 14, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(9, 14, 10, 10);
		e2.setColor(Color.RED);
		e2.fillOval(12, 12, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(12, 12, 10, 10);
		*/
		
		e2.setColor(Color.GRAY);
		e2.fillOval(7, 7, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(7,7, 10, 10);
		e2.setColor(Color.GRAY);
		e2.fillOval(14, 16, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(14, 16, 10, 10);
		e2.setColor(Color.GRAY);
		e2.fillOval(9, 14, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(9, 14, 10, 10);
		e2.setColor(Color.GRAY);
		e2.fillOval(12, 12, 10, 10);
		e2.setColor(Color.BLACK);
		e2.drawOval(12, 12, 10, 10);
		
		
	}
}
