package shoot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.geom.AffineTransform;



public class PannelloGraficoSingolo extends JPanel implements ActionListener{
	Risultato r; // Risultato privato
	ArrayList<Colpo> colpi; // ArrayList di colpi che prelevo dal Risultato
	int iniziox; // Pixel dove disegno l'asse
	int inizioy; // Pixel a partire dal quale disegno l'asse
	int minintero; // Valore minimo intero ottenuto
	int maxintero; // Valore massimo intero ottenuto
	int intervalloy;
	int dx; // spazio sull asse delle x tra un punto ed un altro;
	Punto[] puntidadisegnare;
	private Timer timer;
	int puntidadis;
	Color primaserie; // Colore per i colpi della prima serie
	Color secondaserie; // Colore per i colpi della seconda serie
	Color terzaserie; // Colore per i colpi della terza serie
	Color quartaserie; // Colore per i colpi della quarta serie
	
	// Azione che viene ripetuta dal timer.
	public void actionPerformed(ActionEvent e) {
    	if(puntidadis<puntidadisegnare.length) {
    		puntidadis++;
    		repaint();
    		
    	}
    	else timer.stop();
    	
    }
	
	public PannelloGraficoSingolo(Risultato ris){
		r = ris;
		colpi = ris.colpi;
		iniziox = 50;
		inizioy = 50;
		dx = 27;
		int lunghezza = 100+dx*40; // lunghezza dell' assex
		this.setPreferredSize(new Dimension(lunghezza,350));
		this.setMaximumSize(new Dimension(lunghezza,350));
		this.setMinimumSize(new Dimension(lunghezza,350));
		
		// Colori
		primaserie = new Color(1,151,6);
		secondaserie = new Color(51,99,255);
		terzaserie = new Color(255,112,1);
		quartaserie = new Color(180,1,255);
		
		// Disegna un grafico sui colpi sparati, poiche' abbiamo 10 punti con 10 decimali, uso un asse
		// delle y di 200 px, e un asse delle x di 27*40
		/*for(Colpo p : colpi){
			System.out.println(" "+p.intero+","+p.decimale);
		}
		*/
		
		
		minintero = minInt(); // Valore minimo intero ottenuto
		maxintero = maxInt(); // Valore massimo intero ottenuto


		intervalloy = 200/(maxintero-minintero);
		// spazio sull asse delle y tra un punto intero ed un altro;
		
		
		/* Nel costruttore inizializzo un array di 40 punti, dove salvo le coordinate x e y per ogni colpo rispetto a questo 
		 * piano cartesiano. Le coordinate sono relative al piano, in x e y quindi non aggiungo anche iniziox e 
		 *  inizioy, quindi al momento del disegno dovro fare una traslazione.
		 */
		puntidadisegnare = new Punto[40];
		int i = 0;
		//int x = iniziox;
		int x = 0;
		for(Colpo c : colpi){
			// da c.intero-minintero non ci devo sottrarre 1
			int basey = c.intero - minintero;
			float incr = (float)intervalloy/10;
			int incremento = (int)((float)(c.decimale)*incr);
			
			
			//int y = inizioy+basey*intervalloy+incremento;
			int y = basey*intervalloy+incremento;
			x = x + dx;
			puntidadisegnare[i] = new Punto(x,y);
			i++;
		}
		
		
		setDoubleBuffered(true);
		
		// inizializzo e faccio partire il timer, l'array di punti e' gia' stato creato
		timer = new Timer(100, this);
		puntidadis = 0;
		timer.start();

		
	}
	
	/* EFFECTS: restituisce l'intero piu' piccolo colpito, 
	 */
	private int minInt(){
		int i = 12;
		
		for(Colpo c : colpi){
			if(c.intero<i) {
				i = c.intero;
			}
		}
		return i;
	}
	
	/* EFFETS: restituisce l'intero piu' grande raggiunto acui viene poi sommato 1.
	 */
	private int maxInt(){
		int i = 0;

		for(Colpo c : colpi){
			if(c.intero>i) 	{
				i = c.intero;
			}
		}
		return i+1;
	}
	
	
	/* EFFECTS: disegna una linea, invertendo il segno alle y, poiche' ho ruotato 
	 */
	private void drawLineS(Graphics2D e,int x1,int y1,int x2,int y2){
		e.drawLine(x1, -y1, x2, -y2);
	}
	
	/* EFFECTS: disegna una linea, invertendo il segno alle y, poiche' ho ruotato 
	 */
	private void drawStringS(Graphics2D e,String s,int x, int y){
		e.drawString(s, x, -y);
	}

	public void paintComponent(Graphics e){
		Graphics2D e2 = (Graphics2D)e;
		
		e2.translate(0,this.getSize().height);

		e2.setColor(Color.BLACK);

		// Disegno la legenda dei colori.
		e2.setFont(new Font("Serif",Font.BOLD, 10));
		e2.setColor(primaserie);
		drawStringS(e2,"il verde", iniziox, 300);
		e2.setColor(Color.BLACK);
		drawStringS(e2,"indica la prima serie.", iniziox+48, 300);
		e2.setColor(secondaserie);
		drawStringS(e2,"l'azzurro",  iniziox, 290);
		e2.setColor(Color.BLACK);
		drawStringS(e2,"indica la seconda serie.",  iniziox+55, 290);
		e2.setColor(terzaserie);
		drawStringS(e2,"l'arancione",  iniziox, 280);
		e2.setColor(Color.BLACK);
		drawStringS(e2,"indica la terza serie.",  iniziox+70, 280);
		e2.setColor(quartaserie);
		drawStringS(e2,"il viola",  iniziox, 270);
		e2.setColor(Color.BLACK);
		drawStringS(e2,"indica la quarta serie.",  iniziox+46, 270);
		
		
		e2.setFont(new Font("Serif",Font.PLAIN, 9));
		// asse x :
		drawLineS(e2,iniziox-10,inizioy,iniziox+50+40*dx+10,inizioy);
		// asse y : effettivamente va da 50 a 250 = 200
		// da maxintero-minintero non ci sommo 1 perche' in maxintero e' gia conteggiato
		drawLineS(e2,iniziox,inizioy-10,iniziox,inizioy+(maxintero-minintero)*intervalloy);
		// Scrivo i numeri sull asse delle y
		int j=0;
		int i;
		for(i = minintero;i<maxintero;i++){
			drawLineS(e2,iniziox-3,inizioy+(j*intervalloy),iniziox+3,inizioy+(j*intervalloy));
			// controllo dei decimali, faccio una linea lunga, tranne nel caso coincida con minintero, e cioe' con l'asse
			if(i!=minintero) {
				e2.setColor(Color.LIGHT_GRAY);
				drawLineS(e2,iniziox,inizioy+(j*intervalloy),iniziox+40*27,inizioy+(j*intervalloy));
			}
			e2.setColor(Color.BLACK);
			//System.out.println(i+" "+(inizioy+(j*intervalloy)));
			drawStringS(e2,i+"", iniziox - 20, (inizioy+1 +(j*intervalloy)) );
			//e2.drawString(i+"", iniziox - 20, -(inizioy+1 +(j*intervalloy)) );
			j++;
		}
		// Disegno anche la linea massima delle y, il 10.9, rappresentato dal massimo = 11
		if(i==maxintero){
			float incr = (float)intervalloy/10;
			int y = inizioy+(j*intervalloy) - (int)incr;
			e2.setColor(Color.LIGHT_GRAY);
			//e2.drawString((i-1)+","+"9", iniziox - 23, -y);
			drawStringS(e2,(i-1)+","+"9", iniziox - 23, y);
			drawLineS(e2,iniziox,y,iniziox+40*27,y);

		}
		e2.setColor(Color.BLACK);

		// Disegno i punti nel grafo, quindi faccio una traslazione
		e2.translate(iniziox,-inizioy);

		int pos;
		// puntidadis e' la variabile d istanza incrementata dal timer.
		// Per disegnare ogni punto, prelevo il punto dall'array costruito e disegno cerchio e linea
		for(pos = 0; pos<puntidadis;pos++){
			
			Punto p = puntidadisegnare[pos];
			Colpo c = colpi.get(pos);
			e2.setColor(Color.BLACK);
			//e2.drawString(c.intero+","+c.decimale, p.x-12, -35+inizioy);
			drawStringS(e2,c.intero+","+c.decimale, p.x-12, 35-inizioy);
			// se non siamo all'ultimo punto
			if(pos!=puntidadisegnare.length-1){
				// ovvero non siamo al primo e neanche all'ultimo
				Punto p2 = puntidadisegnare[pos+1];
				e2.setColor(Color.blue);
				drawLineS(e2,p.x,p.y,p2.x,p2.y);
				
			}
			if(pos < 10){
				e2.setColor(primaserie);
			}
			else if(pos<20){
				e2.setColor(secondaserie);
			}
			else if(pos<30){
				e2.setColor(terzaserie);
			}
			else if(pos<40){
				e2.setColor(quartaserie);
			}
			e2.drawOval(p.x-2, -(p.y+2), 4, 4);
			e2.fillOval(p.x-2, -(p.y+2), 4, 4);
			
		}


		e2.translate(-iniziox,+inizioy);
		
		// Stampavo ne grafico non spostando le coordinate
		/*
		int pos;
		// Per controllare saltando l'animazione, al posto di pos<puntidadis scrivere pos<puntidadisegnare.length
		for(pos = 0; pos<puntidadis;pos++){
			
			Punto p = puntidadisegnare[pos];
			Colpo c = colpi.get(pos);
			e2.setColor(Color.BLACK);
			e2.drawString(c.intero+","+c.decimale, p.x-12, -35);
			// se non siamo all'ultimo punto
			if(pos!=puntidadisegnare.length-1){
				// ovvero non siamo al primo e neanche all'ultimo
				Punto p2 = puntidadisegnare[pos+1];
				e2.setColor(Color.blue);
				drawLineS(e2,p.x,p.y,p2.x,p2.y);
				
			}
			if(pos < 10){
				e2.setColor(primaserie);
			}
			else if(pos<20){
				e2.setColor(secondaserie);
			}
			else if(pos<30){
				e2.setColor(terzaserie);
			}
			else if(pos<40){
				e2.setColor(quartaserie);
			}
			e2.drawOval(p.x-2, -(p.y+2), 4, 4);
			e2.fillOval(p.x-2, -(p.y+2), 4, 4);
			
		}
		*/
		

	}
}
