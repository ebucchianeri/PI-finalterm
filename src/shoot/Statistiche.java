package shoot;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.*;


public class Statistiche {

	public static void main(String[] args) {

		JFrame f = new JFrame();
		int numero = 13;
		Risultato[] rr = new Risultato[numero];	
		ArrayList<Risultato> r = new ArrayList<Risultato>();
		PannelloStatistiche ps;
		
		try{
		//public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,int year,int month,int day, int g, String n,String l, String annotazioni) throws RisultatoErratoException 
		rr[0] = new Risultato(0,378,95,94,94,95,2000,8,02,0,null,null,null,new ArrayList<Colpo>());
		rr[1] = new Risultato(1,400,100,100,100,100,2000,8,02,1,"Prima federale","Pontedera",null,null);
		rr[2] = new Risultato(2,385,96,97,96,96,new BigDecimal(404.3),new BigDecimal(100.4),new BigDecimal(101.4),new BigDecimal(101.1),new BigDecimal(101.4),2014,04,05,0,null,null,null,null);
		rr[3] = new Risultato(3,399,99,100,100,100,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh",null);
		rr[4] = new Risultato(4,385,96,96,96,97,new BigDecimal(400.0),new BigDecimal(100.0),new BigDecimal(100.0),new BigDecimal(100.0),new BigDecimal(100.0),2014,04,05,0,null,null,null,null);
		rr[5] = new Risultato(5,383,95,98,95,95,new BigDecimal(399.0),new BigDecimal(99.0),new BigDecimal(100.0),new BigDecimal(100.0),new BigDecimal(100.0),2014,04,05,0,null,null,null,null);
		rr[6] = new Risultato(6,388,96,98,97,97,new BigDecimal(400.0),new BigDecimal(100.0),new BigDecimal(100.0),new BigDecimal(100.0),new BigDecimal(100.0),2014,04,05,0,null,null,null,null);
		rr[7] = new Risultato(7,new BigDecimal(401.0),new BigDecimal(100.0),new BigDecimal(101.0),new BigDecimal(100.0),new BigDecimal(100.0),2014,04,05,0,null,null,null,null);
		rr[8] = new Risultato(3,372,92,95,95,90,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh",null);
		rr[9] = new Risultato(3,384,95,96,98,95,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh",null);
		rr[10] = new Risultato(6,388,96,98,97,97,new BigDecimal(404.0),new BigDecimal(102.0),new BigDecimal(102.0),new BigDecimal(100.0),new BigDecimal(100.0),2014,04,05,0,null,null,null,null);
		rr[11] = new Risultato(3,370,90,90,95,95,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh",null);
		rr[12] = new Risultato(7,new BigDecimal(404.3),new BigDecimal(100.4),new BigDecimal(101.4),new BigDecimal(101.1),new BigDecimal(101.4),2014,04,05,0,null,null,null,null);
		
		//new BigDecimal(404.3),new BigDecimal(100.4),new BigDecimal(101.4),new BigDecimal(101.1),new BigDecimal(101.4) da err
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		for(int i=0;i<numero;i++){
			r.add(rr[i]);
		}
			
		Container c = f.getContentPane();
		

		ps = new PannelloStatistiche(r);
		c.add(ps);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		

	}

}
