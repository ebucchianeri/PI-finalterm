package shoot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

public class Programma {

	public static void main(String[] args) {

		JFrame f = new JFrame();
		JScrollPane s = null;
		PannelloElenco p = new PannelloElenco(f);
		
		PannelloRisposte risposte = new PannelloRisposte(f);
		PannelloMenu menu = new PannelloMenu(p, risposte);
		
		PannelloMenuAlto menualto = new PannelloMenuAlto(p);
		

		
		Container c = f.getContentPane();
		c.setLayout(new BorderLayout());

		
		s = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		s.setPreferredSize(new Dimension(720, 300));
		
		c.add(s,BorderLayout.CENTER);
		c.add(menu,BorderLayout.WEST);
		c.add(menualto,BorderLayout.NORTH);
		c.add(risposte,BorderLayout.SOUTH);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		

		

	}


}