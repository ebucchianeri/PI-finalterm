package shoot;

import javax.swing.*;
import javax.swing.border.Border;

import javax.swing.border.TitledBorder;
import java.util.Calendar;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;

import javax.swing.BoxLayout;
import java.math.BigDecimal;


public class PannelloR {

		public static void main(String[] args) {

			JFrame f = new JFrame();
			PannelloElenco p = new PannelloElenco(f);
			JScrollPane s;
			Risultato[] rr = new Risultato[4];
			ArrayList<PannelloRisultato> pannelli = new ArrayList<PannelloRisultato>();
			
			
			ArrayList<Risultato> r = null;
			ObjectInputStream ois;
			try{
				ois = new ObjectInputStream(new FileInputStream("punteggisalvati"));
				r = (ArrayList<Risultato>) ois.readObject();
			}
			catch(Exception ex){
				System.out.println("ERROREEEE");
				ex.printStackTrace();
			}
			
			
			
			try{
			//public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,int year,int month,int day, int g, String n,String l, String annotazioni) throws RisultatoErratoException 
			rr[0] = new Risultato(0,380,95,95,95,95,2000,8,02,0,null,null,null,null);
			rr[1] = new Risultato(1,400,100,100,100,100,2000,8,02,1,"Prima federale","Pontedera",null,null);
			rr[2] = new Risultato(2,385,96,96,96,97,new BigDecimal(404.30),new BigDecimal(100.4),new BigDecimal(101.4),new BigDecimal(101.1),new BigDecimal(101.4),2014,04,05,0,null,null,null,null);
			rr[3] = new Risultato(3,400,100,100,100,100,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh",null);
			}
			catch (Exception e){
				System.out.println("eccezione"+e.getMessage());
			}
			
			for(int i=0;i<4;i++){
				r.add(rr[i]);
			}
			
			Container c = f.getContentPane();
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			
			int j = 0;
			for( Risultato ris : r ) {
				pannelli.add(new PannelloRisultato(ris,p));
				p.add(pannelli.get(j));
				j++;

			
			}
			
			s = new JScrollPane(p, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			s.setPreferredSize(new Dimension(720, 300));
			
			c.add(s);
			f.setVisible(true);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.pack();
			
			
			ObjectOutputStream oss;
			try{
				oss = new ObjectOutputStream(new FileOutputStream("punteggisalvati"));
				oss.writeObject(r);
				oss.close();
			} catch(FileNotFoundException ex){
				System.out.println("file non trovato");
			} catch (SecurityException ex){
				System.out.println(" if a security manager exists and its checkWrite method denies write access to the file.");
			} catch (IOException ex){
				System.out.println("IOEXCEPTION!");
				ex.printStackTrace();
			}
			

		}

}