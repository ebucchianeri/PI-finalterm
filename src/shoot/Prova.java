package shoot;


import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.awt.geom.AffineTransform;


public class Prova extends JPanel{
	
	
	public static void main(String[] args) {
		//System.err.println("ciao mondo");
		JFrame f = new JFrame();
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = f.getContentPane();
		c.setLayout(new BorderLayout());
		Prova p = new Prova();
	
		
		c.add(p,BorderLayout.CENTER);


		f.pack();

	}
	
	public Prova(){
		super();
		this.setPreferredSize(new Dimension(400,300));
		this.setMaximumSize(new Dimension(400,300));
		this.setMinimumSize(new Dimension(400,300));
	}
	
	
	public void paintComponent(Graphics e){
		Graphics2D e2 = (Graphics2D)e;
		AffineTransform t = e2.getTransform();

		e2.translate(0,this.getSize().height);

		e2.setFont(new Font("Serif",Font.PLAIN, 12));
		e2.setColor(Color.BLACK);
		

		e2.drawString("ciaociao", 30, -30);

		//e2.translate(0,this.getSize().height);
		e2.drawLine(0,0,10,10);
		//e2.scale(1, -1);
		e2.setTransform(t);
		e2.drawLine(0,0,10,10);
		
	}
}