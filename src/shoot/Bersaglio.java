package shoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.JPanel;

class Bersaglio extends JPanel implements MouseListener, MouseMotionListener{
	int inserimento;
	int cx = 350;
	int cy = 350;
	ArrayList<Colpo> colpi;
	ArrayList<Color> colori;
	int n;
	int selezionato = 0;
	// relativo all'ultimo colpo inserito, modificati dall'ultima chiamata di distanza
	int intero;
	int decimale;
	
	PannelloRisultato prisultato;
	PannelloInserimentoValori finestrapadre;
	Punti pannello;
	Risultato r;
	int totaleintero;
	int[] seriei;
	BigDecimal[] seried;
	BigDecimal totaledecimale;
	Colpo current;
	int mouseover; // per chiarire su quale delle 4 serie e' sopra il mouse, nel pannello punti
	// inizializzato a 0 solo in classi di visualizzazione e non inserimento

	/* Al mousedown creo un nuovo pallino il cui centro e' nel punto del click, e fino a che non viene rilasciato
	 * puo' essere spostato, solo al momento del mouseup viene inserito nell array. Fino a che non si ha 
	 * il mouseup le info del pallino sono salvate in current 
	*/
    public void mousePressed(MouseEvent e) {
    	// Se il numero di pallini e' inferire a 40 posso inserirne di nuovi
    	if (n<40){
    		// Non voglio che i pallini vadano sopra le scritte quindi cotrollo la y
    		
    		/*
    		if(e.getY()>60) {
    			current = new Colpo(e.getX(),e.getY());
		    	//colpi.add(p);
		    	selezionato = 1;
		    	distanza(current);
		    	repaint();
    		}
    		// Se clicco nel punto delle scritte non faccio niente
    		else{
    			current = null;
    		}
    		*/
    		current = new Colpo(e.getX(),e.getY());
	    	//colpi.add(p);
	    	selezionato = 1;
	    	distanza(current);
	    	repaint();
    	}
    	// se il numero di pallini e' maggiore o uguale a 40, il mousedown non fa niente
    	else {
    		current = null;
    	}
    }

    /* Al mouse up inserisco il punto salvato in current nell array dei colpi, e aggiorno le variabili
     * che contengono le info sui punteggi
     */
     public void mouseReleased(MouseEvent e) {
    	if (selezionato == 1) {
    		//if(e.getY()>60) {
			  selezionato = 0;

			  colpi.add(current);
			  //n = n+1;
			  //pannello.set(colpi,n-1);
			  //distanza(current);
			  
			  if (n<10){
					seriei[0] = seriei[0] + current.intero;
					seried[0] = seried[0].add(current.puntodecimale);
				}
				else if (n >=10 && n< 20){
					seriei[1] = seriei[1] + current.intero;
					seried[1] = seried[1].add(current.puntodecimale);
				}
				else if (n >=20 && n< 30){
					seriei[2] = seriei[2] + current.intero;
					seried[2] = seried[2].add(current.puntodecimale);
				}
				else if (n >=30 && n< 40){
					seriei[3] = seriei[3] + current.intero;
					seried[3] = seried[3].add(current.puntodecimale);
				}
				else {}
			  
			  	n = n+1;
			  	pannello.set(colpi,n-1);
			  	distanza(current);
			  
				totaleintero = totaleintero + current.intero;
				totaledecimale = totaledecimale.add(current.puntodecimale);
				current = null;
				repaint();
    		//}
    	// TOLGO X INSERRE I COLPI IN ALTO
    	/*
    		else {
    			current = null;
    			repaint();
    		} 
    		*/
    	}
	}

     public void mouseEntered(MouseEvent e) {}

     public void mouseExited(MouseEvent e) {}

     public void mouseClicked(MouseEvent e) {} 
     
     public void mouseMoved(MouseEvent e) {
    	 // Se sono in questo pannello e il mouse over e' diverso da 0 allora c'e stato un problema
    	 if( mouseover != 0){
    		 mouseover = 0;
    		 this.repaint();
    	 }
     }

     /* Allo spostamento del mouse, devo spostare anche il pallino, modifico le informazioni del punto contenute
      * nella variabile current.
      */
     public void mouseDragged(MouseEvent e) {
    	  if (selezionato == 1) {
    		  // Se passo da una zona in cui non devo poter inserire dei colpi allora current = null;
    		//  if(e.getY()>60) {
    			  // Se torno da una zona in cui non potevo disegnare, allora devo ricreare un nuovo 
    			  // colpo nella variabile current
    			  if (current !=null) {
		       		  current.set(e.getX(),e.getY());
					  distanza(current);
		       		  repaint();
    			  }
    			  else {
    				  current = new Colpo(e.getX(),e.getY());
    				  distanza(current);
		       		  repaint();
    			  }
    		 /* }
    		  else {
      			current = null;
      			repaint();
      		}*/
       	  }
      }
	
     /* COSTRUTTORE 1:
 	 * Imposta inserimento = 1, ovvero serve per creare un nuovo risultato, permette di aggiungere e rimuovere i colpi.
 	 */
	public Bersaglio(Punti p, PannelloInserimentoValori fpadre){

		super();
		pannello = p;
		n = 0;
		inserimento = 1;
		mouseover = 0;
		finestrapadre = fpadre;
		intero = 0; decimale = 0; totaleintero = 0;
		seriei = new int[4];
		seried = new BigDecimal[4];
		totaleintero = 0;
		totaledecimale = new BigDecimal(0.0);
		seriei[0] = 0; seriei[1] = 0; seriei[2] = 0; seriei[3] = 0;
		seried[0] = new BigDecimal(0.0); seried[1] = new BigDecimal(0.0); seried[2] = new BigDecimal(0.0); 
		seried[3] = new BigDecimal(0.0);
		
		colpi = new ArrayList<Colpo>();
		colori = new ArrayList<Color>();
		colori.add(Color.DARK_GRAY);
    	colori.add(Color.DARK_GRAY);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.YELLOW);
    	colori.add(Color.RED);
		this.setPreferredSize(new Dimension(700,650));
		
		
		
	}
	
	
	/* COSTRUTTORE 2:
 	 * Imposta inserimento = 1, ovvero serve per creare un nuovo risultato, permette di aggiungere e rimuovere i colpi.
 	 */
	public Bersaglio(Punti p){

		super();
		pannello = p;
		n = 0;
		inserimento = 1;
		mouseover = 0;
		intero = 0; decimale = 0; totaleintero = 0;
		seriei = new int[4];
		seried = new BigDecimal[4];
		
		
		totaleintero = 0;
		totaledecimale = new BigDecimal(0.0);
		seriei[0] = 0; seriei[1] = 0; seriei[2] = 0; seriei[3] = 0;
		seried[0] = new BigDecimal(0.0); seried[1] = new BigDecimal(0.0); seried[2] = new BigDecimal(0.0); seried[3] = new BigDecimal(0.0);
		
		colpi = new ArrayList<Colpo>();
		colori = new ArrayList<Color>();
		colori.add(Color.DARK_GRAY);
    	colori.add(Color.DARK_GRAY);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.YELLOW);
    	colori.add(Color.RED);
		this.setPreferredSize(new Dimension(700,650));
	}
	
	
	/* COSTRUTTORE 3:
	 * Imposta inserimento = 0, ovvero serve solo per la visualizzazione e non mostra i tasti per la modifica, quindi
	 * l'array di colpi non puo' essere modificato
	 */
	public Bersaglio(Punti p, ArrayList<Colpo> c, Risultato ri, PannelloRisultato pr){

		super();
		pannello = p;
		n = 40;
		p.b = this;
		mouseover = 0;
		inserimento = 0; prisultato = pr;
		intero = 0; decimale = 0; totaleintero = 0;
		seriei = new int[4];
		seried = new BigDecimal[4];
		totaleintero = 0;
		totaledecimale = new BigDecimal(0.0);
		seriei[0] = 0; seriei[1] = 0; seriei[2] = 0; seriei[3] = 0;
		seried[0] = new BigDecimal(0.0); seried[1] = new BigDecimal(0.0); 
		seried[2] = new BigDecimal(0.0); seried[3] = new BigDecimal(0.0);
		
		// Poiche' siamo in una classe di visualizzazione devo conteggiare i valori
		// Poiche' visualizzo e non inserisco, questi devo conteggiarli
		int j = 0;
		for(j=0;j<40;j++){
			Colpo cc = c.get(j);

			totaleintero = totaleintero + cc.intero;
			totaledecimale = totaledecimale.add(cc.puntodecimale);
			if(j<10) {
				seriei[0] = seriei[0] + cc.intero;
				seried[0] = seried[0].add(cc.puntodecimale);
			}
			else if(j<20) {
				seriei[1] = seriei[1] + cc.intero;
				seried[1] = seried[1].add(cc.puntodecimale);
			}
			else if(j<30) {
				seriei[2] = seriei[2] + cc.intero;
				seried[2] = seried[2].add(cc.puntodecimale);
			}
			else if(j<40) {
				seriei[3] = seriei[3] + cc.intero;
				seried[3] = seried[3].add(cc.puntodecimale);
			}
			
			
		}
	
		r = ri;
		colpi = c;
		colori = new ArrayList<Color>();
		colori.add(Color.DARK_GRAY);
    	colori.add(Color.DARK_GRAY);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.GREEN);
    	colori.add(Color.YELLOW);
    	colori.add(Color.RED);
		this.setPreferredSize(new Dimension(700,650));
		pannello.set(c,n-1);
	}
	
	
	public void cancellaUltimo(){
//		in tutti i casi, quando clicco cancella ultimo current non deve essere piu' ridisegnabile
		current = null;
		if (n>0){
//			Prendo l'ultimo colpo inserito nell'array, quello da cancellare, e con i sui dati aggiorno le info
//			sui punteggi
			Colpo p = colpi.get(n-1);
			System.out.println("Devo togliere "+p.intero+","+p.decimale);
			n = n -1 ;
			if (n<10){
				seriei[0] = seriei[0] - p.intero;
				seried[0] = seried[0].subtract(p.puntodecimale);
			}
			else if (n >=10 && n< 20){
				seriei[1] = seriei[1] - p.intero;
				seried[1] = seried[1].subtract(p.puntodecimale);
			}
			else if (n >=20 && n< 30){
				seriei[2] = seriei[2] - p.intero;
				seried[2] = seried[2].subtract(p.puntodecimale);
			}
			else if (n >=30 && n< 40){
				seriei[3] = seriei[3] - p.intero;
				seried[3] = seried[3].subtract(p.puntodecimale);
			}
	
			
			else {}
			totaleintero = totaleintero - p.intero;
			totaledecimale = totaledecimale.subtract(p.puntodecimale);
			

			colpi.remove(n);

			// Se c'e ancora almeno un colpo
			if (n>0) {
				// Cosi mostro il precedente colpo come ultimo inserito
				p = colpi.get(n-1);
				distanza(p);
			}
			else {
				intero = 0; decimale = 0;
			}
			this.repaint();
			pannello.set(colpi,n-1);
			pannello.repaint();

		}
		
	}
	
	/* EFFECTS: funzione che serve per calcolare i valori di intero e decimale di ogni pallino
	 * MODIFIES: intero, decimale (che uso per l'ultimo colpo inserito) e le informazioni di intero e
	 * 	decimale nel colpo stesso.
	 */

	private void distanza(Colpo c){
		// Io devo considerare il bordo esterno del pallino, no il suo centro
		// Al centro del pallino devo aggiungere o togliere meta' diametro, a seconda del quadrante in cui sono.
		// Altrimenti basta togliere dalla distanza col centro meta' diametro
		int sx = cx - c.cx;
		int sy = cy - c.cy;
		int s;
		sx = Math.abs(sx);
		sy = Math.abs(sy);
		s = sx*sx + sy*sy;
		s = (int)Math.sqrt(s);
		// siamo nel 10
		if (s <= c.d/2) {
			//System.out.println(sx +" "+ sy);
			
			//System.out.println("DDDistanza: "+s);
			int m = s%30;
			int d = s/30;
			//System.out.println("DDDiv: "+d+ "Mod: "+m);
			intero = 10;
			decimale = 9 - m/3;
			c.setP(intero, decimale);
			//System.out.println(intero+" "+decimale);
		}
		// non siamo nel 10
		else {
			s = s - c.d/2;
			// DA RIGUARDARE
			s = s - 3;
			
			//System.out.println("Distanza: "+s);
			int m = s%30;
			int d = s/30;
			//System.out.println("Div: "+d+ "Mod: "+m);
			
			if(d > 9) {
				intero = 0;
				decimale = 0;
				c.setP(intero, decimale);
			}
			else {
			
				intero = 9 - d;
				decimale = 9 - m/3;
				c.setP(intero, decimale);
			
			}
		}
		String v = c.intero+"."+c.decimale;
		c.puntodecimale = new BigDecimal(v);
	}
	
	/* Imbianco tutto lo schermo ogni volta che vado a fare la paint.
	 */
	public void paintBackground(Graphics e){
		e.clearRect(0, 0, 700 ,650);
	}
	
	
	private int maxSeriei(){
		int max,i;
		max = 0;
		for(i=1;i<4;i++){
			if(seriei[i]>seriei[max]) {
				max = i;
			}
		}
		return max;
	}
	
	private int minSeriei(){
		int min,i;
		min = 0;
		for(i=1;i<4;i++){
			if(seriei[i]<seriei[min]) {
				min = i;
			}
		}
		return min;
	}
	
	private int maxSeried(){
		int min,i;
		BigDecimal e;
		min = 0;
		for(i=1;i<4;i++){
			// public int compareTo(BigDecimal val)
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(seried[i].compareTo(seried[min])>0) {
				min = i;
			}
		}
		return min;
	}
		
	private int minSeried(){
		int min,i;
		BigDecimal e;
		min = 0;
		for(i=1;i<4;i++){
			// public int compareTo(BigDecimal val)
			//-1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
			if(seried[i].compareTo(seried[min])<0) {
				min = i;
			}
		}
		return min;
	}
	
	public void paint(Graphics e){
		if(inserimento == 1) {
			Graphics2D e2 = (Graphics2D)e;
			e2.setColor(Color.BLACK);
			paintBackground(e);
			
	//		Disegno i cerchi del bersaglio
			// 3
			e2.drawOval(cx-211, cy-211, 423, 423);
			// 2
			e2.drawOval(cx-241, cy-241, 483, 483);
			// 1
			e2.drawOval(cx-271, cy-271, 543, 543);
					
			e2.fillOval(cx-181, cy-181, 363, 363);
			e2.setColor(Color.WHITE);	
			// 10
			e2.drawOval(cx-1, cy-1, 3, 3);
			e2.fillOval(cx-1, cy-1, 3, 3);
			// 9
			e2.drawOval(cx-31, cy-31, 63, 63);
			// 8
			e2.drawOval(cx-61, cy-61, 123, 123);
			// 7
			e2.drawOval(cx-91, cy-91, 183, 183);
			// 6
			e2.drawOval(cx-121, cy-121, 243, 243);
			// 5
			e2.drawOval(cx-151, cy-151, 303, 303);
			// 4
			e2.drawOval(cx-181, cy-181, 363, 363);
			e2.setColor(Color.BLACK);
			
	//		scrivo i numeri sul bersaglio, per ora solo asse orizzontale
			e2.setColor(Color.WHITE);
			int i; int j = 0;
			for(i = 8; i>0; i--){
				if (j>=5){
					e2.setColor(Color.BLACK);
				}
					e2.drawString(i+"", 390+(30*j), 355);
					e2.drawString(i+"", 300-(30*j), 355);
					j++;
				
			}

			for( Colpo p : colpi ) {
					e2.setColor(colori.get(p.intero));
					if(p.cy-(p.d/2) > 40) {
						e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						e2.setColor(Color.BLACK);
						e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
					}
			}
			
			if(current != null){
				e2.setColor(colori.get(current.intero));
				e2.fillOval(current.cx-(current.d/2), current.cy-(current.d/2), current.d, current.d);
				e2.setColor(Color.BLACK);
				e2.drawOval(current.cx-(current.d/2), current.cy-(current.d/2), current.d, current.d);
			}
			
			
	//		Ridisegno sopra il cerchio del 10 per facilitare l'inserimento.
	
			e2.setColor(Color.WHITE);
			e2.drawOval(cx-1, cy-1, 3, 3);
			e2.fillOval(cx-1, cy-1, 3, 3);
			e2.setColor(Color.BLACK);
			
	//		Scrivo le informazioni sul punteggio nella parte alta della finestra
			e2.setColor(Color.BLACK);
			e2.drawString("#Colpi inseriti: "+n, 10, 15);
			e2.drawString("Ultimo colpo: "+intero+","+decimale, 140, 15);
			
			
	//		Se ho terminato l'inserimento, ovvero ho inserito 40 colpi allora scrivo il punteggio in rosso
	//		altrimenti lo scrivo in nero
			if (n<40){
				e2.drawString("Tot. intero: "+totaleintero, 270, 15);
				e2.drawString("Tot. decimale: "+totaledecimale.floatValue(), 380, 15);
				e2.drawString("1^Si: "+seriei[0]+"  2^Si: "+seriei[1]+"  3^Si: "+seriei[2]+"  4^Si: "+seriei[3]+" ||", 10, 30);
				e2.drawString("1^Sd: "+seried[0]+"  2^Sd: "+seried[1]+"  3^Sd: "+seried[2]+"  4^Sd: "+seried[3], 300, 30);
			}
			else {
				e2.setColor(Color.RED);
				e2.drawString("Tot. intero: "+totaleintero, 270, 15);
				e2.drawString("Tot. decimale: "+totaledecimale.floatValue(), 380, 15);
				e2.drawString("1^Si: "+seriei[0]+"  2^Si: "+seriei[1]+"  3^Si: "+seriei[2]+"  4^Si: "+seriei[3]+" ||", 10, 30);
				e2.drawString("1^Sd: "+seried[0]+"  2^Sd: "+seried[1]+"  3^Sd: "+seried[2]+"  4^Sd: "+seried[3], 300, 30);
				e.setColor(Color.RED);
	
			}
		}
		else {
			Font base = e.getFont();
			if(mouseover == 0){
				Graphics2D e2 = (Graphics2D)e;
				e2.setColor(Color.BLACK);
				paintBackground(e);
				
		//		Disegno i cerchi del bersaglio
				// 3
				e2.drawOval(cx-211, cy-211, 423, 423);
				// 2
				e2.drawOval(cx-241, cy-241, 483, 483);
				// 1
				e2.drawOval(cx-271, cy-271, 543, 543);
						
				e2.fillOval(cx-181, cy-181, 363, 363);
				e2.setColor(Color.WHITE);	
				// 10
				e2.drawOval(cx-1, cy-1, 3, 3);
				e2.fillOval(cx-1, cy-1, 3, 3);
				// 9
				e2.drawOval(cx-31, cy-31, 63, 63);
				// 8
				e2.drawOval(cx-61, cy-61, 123, 123);
				// 7
				e2.drawOval(cx-91, cy-91, 183, 183);
				// 6
				e2.drawOval(cx-121, cy-121, 243, 243);
				// 5
				e2.drawOval(cx-151, cy-151, 303, 303);
				// 4
				e2.drawOval(cx-181, cy-181, 363, 363);
				e2.setColor(Color.BLACK);
				
		//		scrivo i numeri sul bersaglio, per ora solo asse orizzontale
				e2.setColor(Color.WHITE);
				int i; int j = 0;
				for(i = 8; i>0; i--){
					if (j>=5){
						e2.setColor(Color.BLACK);
					}
						e2.drawString(i+"", 390+(30*j), 355);
						e2.drawString(i+"", 300-(30*j), 355);
						j++;
					
				}
		
				for( Colpo p : colpi ) {
					e2.setColor(colori.get(p.intero));
					if(p.cy-(p.d/2) > 40) {
						e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						e2.setColor(Color.BLACK);
						e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
					}
				}
				
				if(current != null){
					e2.setColor(colori.get(current.intero));
					e2.fillOval(current.cx-(current.d/2), current.cy-(current.d/2), current.d, current.d);
					e2.setColor(Color.BLACK);
					e2.drawOval(current.cx-(current.d/2), current.cy-(current.d/2), current.d, current.d);
				}
				
				
		//		Ridisegno sopra il cerchio del 10 per facilitare l'inserimento.
		
				e2.setColor(Color.WHITE);
				e2.drawOval(cx-1, cy-1, 3, 3);
				e2.fillOval(cx-1, cy-1, 3, 3);
				e2.setColor(Color.BLACK);
				
		//		Scrivo le informazioni sul punteggio nella parte alta della finestra
				e2.setColor(Color.BLACK);
				e2.drawString("#Colpi inseriti: "+n, 10, 15);
				e2.drawString("Ultimo colpo: "+intero+","+decimale, 140, 15);
				
				
		//		Se ho terminato l'inserimento, ovvero ho inserito 40 colpi allora scrivo il punteggio in rosso
		//		altrimenti lo scrivo in nero
				if (n<40){
					e2.drawString("Tot. intero: "+totaleintero, 270, 15);
					e2.drawString("Tot. decimale: "+totaledecimale.floatValue(), 380, 15);
					e2.drawString("1^Si: "+seriei[0]+"  2^Si: "+seriei[1]+"  3^Si: "+seriei[2]+"  4^Si: "+seriei[3]+" ||", 10, 30);
					e2.drawString("1^Sd: "+seried[0]+"  2^Sd: "+seried[1]+"  3^Sd: "+seried[2]+"  4^Sd: "+seried[3], 300, 30);
				}
				else {
					e2.setColor(Color.RED);
					e2.drawString("Tot. intero: "+totaleintero, 270, 15);
					e2.drawString("Tot. decimale: "+totaledecimale.floatValue(), 380, 15);
					e2.drawString("1^Si: "+seriei[0]+"  2^Si: "+seriei[1]+"  3^Si: "+seriei[2]+"  4^Si: "+seriei[3]+" ||", 10, 30);
					e2.drawString("1^Sd: "+seried[0]+"  2^Sd: "+seried[1]+"  3^Sd: "+seried[2]+"  4^Sd: "+seried[3], 300, 30);
					e.setColor(Color.RED);
		
				}
			}
			else if(mouseover > 0){
				Graphics2D e2 = (Graphics2D)e;
				e2.setColor(Color.BLACK);
				paintBackground(e);
				
		//		Disegno i cerchi del bersaglio
				// 3
				e2.drawOval(cx-211, cy-211, 423, 423);
				// 2
				e2.drawOval(cx-241, cy-241, 483, 483);
				// 1
				e2.drawOval(cx-271, cy-271, 543, 543);
						
				e2.fillOval(cx-181, cy-181, 363, 363);
				e2.setColor(Color.WHITE);	
				// 10
				e2.drawOval(cx-1, cy-1, 3, 3);
				e2.fillOval(cx-1, cy-1, 3, 3);
				// 9
				e2.drawOval(cx-31, cy-31, 63, 63);
				// 8
				e2.drawOval(cx-61, cy-61, 123, 123);
				// 7
				e2.drawOval(cx-91, cy-91, 183, 183);
				// 6
				e2.drawOval(cx-121, cy-121, 243, 243);
				// 5
				e2.drawOval(cx-151, cy-151, 303, 303);
				// 4
				e2.drawOval(cx-181, cy-181, 363, 363);
				e2.setColor(Color.BLACK);
				
				
				//		scrivo i numeri sul bersaglio, per ora solo asse orizzontale
				e2.setColor(Color.WHITE);
				int i; int j = 0;
				for(i = 8; i>0; i--){
					if (j>=5){
						e2.setColor(Color.BLACK);
					}
						e2.drawString(i+"", 390+(30*j), 355);
						e2.drawString(i+"", 300-(30*j), 355);
						j++;
					
				}
				
				if(mouseover == 1){
					int t = 0;
					for(t = 0; t<10; t++){
						Colpo p = colpi.get(t);
						e2.setColor(colori.get(p.intero));
						if(p.cy-(p.d/2) > 40) {
							e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
							e2.setColor(Color.BLACK);
							e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						}
						
					}
					e2.setColor(Color.red);
					e2.drawString("Prima serie", 10, 15);
					e2.setColor(Color.BLACK);
					e2.drawString("Risultato intero: "+seriei[0] + " || Risultato decimale : "+seried[0], 10, 30);

					
					
				}
				else if(mouseover == 2){
					int t = 10;
					for(t = 10; t<20; t++){
						Colpo p = colpi.get(t);
						e2.setColor(colori.get(p.intero));
						if(p.cy-(p.d/2) > 40) {
							e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
							e2.setColor(Color.BLACK);
							e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						}
						
					}
					
					e2.setColor(Color.red);
					e2.drawString("Seconda serie", 10, 15);
					e2.setColor(Color.BLACK);
					e2.drawString("Risultato intero: "+seriei[1] + " || Risultato decimale : "+seried[1], 10, 30);
					
				}
				else if(mouseover == 3){
					int t = 20;
					for(t = 20; t<30; t++){
						Colpo p = colpi.get(t);
						e2.setColor(colori.get(p.intero));
						if(p.cy-(p.d/2) > 40) {
							e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
							e2.setColor(Color.BLACK);
							e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						}
					}
					
					e2.setColor(Color.red);
					e2.drawString("Terza serie", 10, 15);
					e2.setColor(Color.BLACK);
					e2.drawString("Risultato intero: "+seriei[2] + " || Risultato decimale : "+seried[2], 10, 30);
				}
				else if(mouseover == 4){
					int t = 30;
					for(t = 30; t<40; t++){
						Colpo p = colpi.get(t);
						e2.setColor(colori.get(p.intero));
						if(p.cy-(p.d/2) > 40) {
							e2.fillOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
							e2.setColor(Color.BLACK);
							e2.drawOval(p.cx-(p.d/2), p.cy-(p.d/2), p.d, p.d);
						}
						
					}
					
					e2.setColor(Color.red);
					e2.drawString("Quarta serie", 10, 15);
					e2.setColor(Color.BLACK);
					e2.drawString("Risultato intero: "+seriei[3] + " || Risultato decimale : "+seried[3], 10, 30);

				}



		//		Ridisegno sopra il cerchio del 10 per facilitare l'inserimento.
		
				e2.setColor(Color.WHITE);
				e2.drawOval(cx-1, cy-1, 3, 3);
				e2.fillOval(cx-1, cy-1, 3, 3);
				e2.setColor(Color.BLACK);

			}
		}
	}
	               
}