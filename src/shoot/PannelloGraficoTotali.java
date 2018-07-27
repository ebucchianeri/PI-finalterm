package shoot;

import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;



class Punto {
	int x;
	int y;
	
	public Punto(int a, int b){
		x = a; y = b;
	}
}
public class PannelloGraficoTotali extends JPanel implements ActionListener{
	ArrayList<Risultato> r;
	int pmax;
	int pmin;
	int linf;
	int lsup;
	int l;
	int intervallo; // intervallo interessante di punti da disegnare
	int incremento; // l'incremento in pixel che ho ogni 10 punti sull asse delle y
	double incr;
	int pixelp; // incremento in px che ho tra un risultato e il successivo sull'asse delle x
	int starty = 30;
	ArrayList<Punto> puntigrafico;
	//Punto[] puntidadisegnare;
	private Timer timer;
	int puntidadis;
	
	public PannelloGraficoTotali(ArrayList<Risultato> ris){
		r = ris;
		
		for(Risultato e: r){
			//System.out.println("tot:"+e.punti);
		}
		
		// gli do anche una dimensione minima?
		this.setPreferredSize(new Dimension(500,450));
		this.setMinimumSize(new Dimension(500,450));
		this.setMaximumSize(new Dimension(500,450));
		
		pixelp = 60;
		pmin = min();
		pmax = max();
		if(pmin>99) linf = this.multiplodi10inferiore(pmin,2);
		else if(pmin>9) linf = this.multiplodi10inferiore(pmin,1);
		else linf = this.multiplodi10inferiore(pmin,0);
		if(pmax>99) lsup = this.multiplodi10superiore(pmax,2);
		else if(pmax>9) lsup = this.multiplodi10superiore(pmax,1);
		else lsup = this.multiplodi10superiore(pmax,0);
		
		l = pixelp*(r.size()+1);
		//System.out.println(l);
		intervallo = lsup - linf;
		intervallo = intervallo;
		incr = (double)400/(intervallo/10);
		//System.out.println("aaaa"+intervallo+" "+incr);
		
		if(l > 500){
			this.setPreferredSize(new Dimension(l+50,450));
			this.setMaximumSize(new Dimension(l+50,450));
			this.setMinimumSize(new Dimension(l+50,450));
		}

		//System.out.println("MIN: "+pmin+"LINF: "+linf+"MAX: "+pmax+"LSUP: "+lsup +"INTERVALLO: "+intervallo+"incr: "+incremento);
		
		
		/* Invece che ricalcolare tutte le volte le posizioni dei vari colpi nel grafico, le calcolo 
		 *  una volta sola e le metto in un array che uso poi nella paint.
		 */
		puntigrafico = new ArrayList<Punto>();
		int j = pixelp;
		int numero;
		int t = 0;
		//System.out.println("incr "+incr);
		for(Risultato p : r){
			// Calcolo in che fascia sono
			int multiploinf;
			int salti;
			int y;
			
			
			// Calcolo l'offset da aggiungere
			double dasommare;
			if(p.punti>99){
				multiploinf = multiplodi10inferiore(p.punti,2);
				numero = multiploinf - linf;
				salti = numero/10;
				y = salti*(int)incr;
			Integer u = new Integer(p.punti);
			char unita = u.toString().charAt(2);
			//System.out.println("unita "+ unita);
			Integer rr = Integer.parseInt(Character.toString(unita));
			double mm = incr/10;
			dasommare = rr.intValue()*mm;
			//System.out.println(" "+ mm +"aggiiungo "+ dasommare);
			}
			else if (p.punti>9) {
				multiploinf = multiplodi10inferiore(p.punti,1);
				numero = multiploinf - linf;
				salti = numero/10;
				y = salti*(int)incr;
				Integer u = new Integer(p.punti);
				char unita = u.toString().charAt(1);
				//System.out.println("unita "+ unita);
				Integer rr = Integer.parseInt(Character.toString(unita));
				double mm = incr/10;
				dasommare = rr.intValue()*mm;
				//System.out.println(" "+ mm +"aggiiungo "+ dasommare);
			}
			else {
				multiploinf = multiplodi10inferiore(p.punti,0);
				numero = multiploinf - linf;
				salti = numero/10;
				y = salti*(int)incr;
				Integer u = new Integer(p.punti);
				char unita = u.toString().charAt(0);
				//System.out.println("unita "+ unita);
				Integer rr = Integer.parseInt(Character.toString(unita));
				double mm = incr/10;
				dasommare = rr.intValue()*mm;
				//System.out.println(" "+ mm +"aggiiungo "+ dasommare);
			}

			int yi = (int)y+(int)dasommare+20;
//			System.out.println(p.punti+" "+yi);

			puntigrafico.add(new Punto(j+starty,yi));
			t++;
			j=j+pixelp;
	
		}
	
		setDoubleBuffered(true);
		
		timer = new Timer(130, this);
		puntidadis = 0;
		timer.start();
		
	}
	
	public void actionPerformed(ActionEvent e) {


    	
    	//System.out.println("puntidadis"+puntidadis);
    	if(puntidadis<puntigrafico.size()) {
    		puntidadis++;
    		repaint();
    	}
    	else timer.stop();
    	
    }
	
	private int multiplodi10superiore(int m,int p) {
		Integer m1 = new Integer(m);
		String s = m1.toString();
		char u = s.charAt(p);
		String d = s.substring(0,p);
		//System.out.println("numero stringa: " + s + "Unita: " + u + "restante:" + d );
		if( Integer.parseInt(Character.toString(u)) != 0 ){
			// Se la cifra delle unita' e' diversa da 0 devo incrementare le restanti cifre!
			int dec = Integer.parseInt(d);
			dec++;
			int num = Integer.parseInt(dec+"0");
			//System.out.println(d + " " + num);
			return num;
		}
		else return m;
	}
	
	private int multiplodi10inferiore(int m,int p) {
		if(m==0){
			return 0;
		}
		else {
			Integer m1 = new Integer(m);
			String s = m1.toString();
			char u = s.charAt(p);
			String d = s.substring(0,p);
			//System.out.println("numero stringa: " + s + "Unita: " + u + "restante:" + d);
			if( Integer.parseInt(Character.toString(u)) != 0 ){
				// Se la cifra delle unita' e' diversa da 0 devo incrementare le restanti cifre!
				int dec = Integer.parseInt(d);
				int num = Integer.parseInt(dec+"0");
				//System.out.println(d + " " + num);
				return num;
			}
			else return m;
		}
	}
	
	private int min(){
		int m = 400;
		
		for(Risultato e : r){
			if(e.punti<m){
				m = e.punti;
			}
		}
		return m;
	}
	
	private int max(){
		int m = 0;
		
		for(Risultato e : r){
			if(e.punti>m){
				m = e.punti;
			}
		}
		return m;
	}
	
	private void drawLineS(Graphics2D e,int x1,int y1,int x2,int y2){
		e.drawLine(x1, -y1, x2, -y2);
	}
	
	
	
	public void setSize(Dimension d){
//		System.out.println("cambio dimensioniiii");
		this.setPreferredSize(new Dimension(d.width,d.height));
		this.setMinimumSize(new Dimension(d.width,d.height));
		this.setMaximumSize(new Dimension(d.width,d.height));
		this.invalidate();
		
	}
	
	public void paint(Graphics e){
		
		Graphics2D e2 = (Graphics2D)e;
		e2.translate(0, 450);
		
		
		//asse delle ordinate
		drawLineS(e2, starty, 5, starty, 430);
		// asse ascisse
		drawLineS(e2, 0, 20, l, 20);
		// In questo modo lo 0 e' a 20,10
		
		
		// Disegno i numeri e le linette sull asse delle ascisse
		int j = 0;
		for(Risultato er : r){
			e2.drawString(er.punti+"",pixelp-10+j+starty, -05);
			drawLineS(e2,starty+pixelp+j,18,starty+pixelp+j,22);
			j=j+pixelp;
		
		}
		// disegno i numeri e le linette sull asse delle ordinate
		int start = 20;
		int distanza = start;
		for(int i = linf; i<=lsup;i=i+10){
			drawLineS(e2,starty-3,distanza,starty+3,distanza);
			if(i!=linf){
				e2.setColor(Color.LIGHT_GRAY);
				drawLineS(e2,starty,distanza,l,distanza);
			}
			e2.setColor(Color.BLACK);
			//System.out.println("@"+i+" "+distanza);
			e2.drawString(i+"",0, -(distanza));
			distanza = distanza + (int)incr;
			
	
		}
		
		// devo disegnare i punti sul grafico
		Color c = e2.getColor();
		int pos = 0;
	
		// punti da dis viene incrementato dal timer
		for(pos = 0; pos<puntidadis;pos++){
			Punto p = puntigrafico.get(pos);
			e2.setColor(Color.red);
			e2.drawOval(p.x-2, -(p.y+2), 4, 4);
			// se non siamo all'ultimo punto
			if(pos!=puntigrafico.size()-1){
				// ovvero non siamo al primo e neanche all'ultimo
				Punto p2 = puntigrafico.get(pos+1);
				e2.setColor(Color.blue);
				drawLineS(e2,p.x,p.y,p2.x,p2.y);
				
			}
			
		}
		
		e2.setColor(c);
		
	}

}
