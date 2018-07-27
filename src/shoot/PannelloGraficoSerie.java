package shoot;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


class PannelloGraficoSerie extends JPanel implements ActionListener {
	ArrayList<Risultato> r;
	// Ho 4 array di interi, uno per ogni serie
	int n;
	int[][] seriefinali; // Valori non duplicati ottenuti nelle varie serie
	int[][] occorrenze; // Occorrenza dei valori ottenuti nelle serie
	int[][] yserie;

	int i;
	int maxo; //variabile che contiene la massima occorrenza tra tutte, mi serve per fermare il timer
    private Timer timer;

	
	
	// Costruisce un pannello che fa le statistiche sulle serie intere, quindi l'arrayList di risultati che si aspetta di 
	// ricevere e' privo di risultati con valori solo decimali o con sole annotazioni. In pratica abbiamo tutti i risultati 
	// con intero = 1.
	public PannelloGraficoSerie(ArrayList<Risultato> ris){
		super();
		if(ris!=null){
			// Se ho almeno un valore da analizzare, e quindi almeno un intero ==1
			r = ris;
			seriefinali = new int[4][];
			occorrenze = new int[4][];
			yserie = new int[4][];
			maxo = 0;
			
			this.setPreferredSize(new Dimension(800,290));
			this.setMaximumSize(new Dimension(800,290));
			this.setMinimumSize(new Dimension(800,290));
			
			// devo inizializzare i vari vettori per le statistiche
			// r e' ordinato per valori di punti crescenti!!!!!! #####################################33##333####3
			
			inizializza();
			/*
		 	for(i = 0; i<seriefinali[0].length;i++){
			 
				System.out.println("prima "+seriefinali[0][i] + " e occorrenza: " + occorrenze[0][i]) ;
			}
			
			
			for(i = 0; i<seriefinali[1].length;i++){
				System.out.println("prima "+seriefinali[1][i] + " e occorrenza: " + occorrenze[1][i]) ;
			}
			*/
			
			
			// nel costruttore devo anche inizializzare il timer. Uso un timer solo per tutti e
			// 3  i grafici. Come e quando fermo i timer? Quando ho disegnato tutto.
			/*

	        
	        
	        
	        il timer pero' deve permettere di disegnare tutte le colonne del grafico 
	        quindi dovrei avere per ogni colonna de grafico 
	        una variabile y da incrementare. 
	        
	        Quando tutte sono state disegnate fino al limite posso anche stoppare il timer, ovviamente
	        inizialmente non disegno niente perche' tutte sono inizializzate a 0..
	        */
			
			
			int lx = seriefinali[0].length*25+50+seriefinali[1].length*25+50+seriefinali[2].length*25+50+seriefinali[3].length*25+50;
			
			if(lx > 800){
				this.setPreferredSize(new Dimension(lx+50,290));
				this.setMaximumSize(new Dimension(lx+50,290));
				this.setMinimumSize(new Dimension(lx+50,290));
			}
			
			//System.out.println("assex "+lx);
			
			setDoubleBuffered(true);
			
			timer = new Timer(10, this);
	        timer.start();
			
		}
		else {
			// Non ho niente da analizzare, posso stampare qualcosa
		}
	}
	
	

    @Override
    public void actionPerformed(ActionEvent e) {


    	for(int j = 0; j<4; j++){
    		for(int t = 0; t < yserie[j].length; t++){
    			yserie[j][t] = yserie[j][t] + 1;
    		}
    	}
        repaint();
    }
	
	
    private Integer getMin (ArrayList<Integer> a){
    	Iterator<Integer> g = a.iterator();
		Integer min = new Integer(200);
		while(g.hasNext()){
			 Integer next = g.next();
			 if(next < min){
				 min = next;
			 }

		}
		a.remove(min);    	
    	return min;
    }
    
    private ArrayList<Integer> ordinaArray(ArrayList<Integer> a){
    	int l = a.size();
    	ArrayList<Integer> valoriordinati = new ArrayList<Integer>();
    	try{
			for(int t =0; t<l; t++){
				Integer current = this.getMin(a);
				if(t == 0) {
					valoriordinati.add(current);
				} else {
					if(valoriordinati.get(t-1)!=current){
						valoriordinati.add(current);
					}
				}
			}
		}
		catch( EmptyException e){
			// DEVO FARE QUALCOSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaa
			System.out.println("Errore");
		}
    	return valoriordinati;
    }
    
	/* MODIFIES: prima, occorrenzaprima, seconda, occorrenzeseconda, terza, occorrenzeterza, quarta, occorrenzequarta
	 * EFFECTS: funzione che inizializza le variabili d'istanza necessarie alle statistiche.
	 */	
	private void inizializza(){
		int[] primeserie = new int[r.size()];
		int[] secondeserie = new int[r.size()];
		int[] terzeserie = new int[r.size()];
		int[] quarteserie = new int[r.size()];
				
		int i = 0;
		for( Risultato e : r){
			primeserie[i] = e.serie[0];
			i++;
		}
		
		i = 0;
		for( Risultato e : r){
			secondeserie[i] = e.serie[1];
			i++;
		}
		
		i = 0;
		for( Risultato e : r){
			terzeserie[i] = e.serie[2];
			i++;
		}
				
		i = 0;
		for( Risultato e : r){
			quarteserie[i] = e.serie[3];
			i++;
		}
		
		// adesso ho tutte le prime, seconde, terze e quarte serie in degli array,
		// prendo per ognuna il minimo e ogni volta conto quante volte occorre
		int num = 0;
		int c = 0;
		ArrayList<Integer> pr = new ArrayList<Integer>();
		ArrayList<Integer> se = new ArrayList<Integer>();
		ArrayList<Integer> te = new ArrayList<Integer>();
		ArrayList<Integer> qu = new ArrayList<Integer>();

		
		// Adesso ho il primo minimo, se ci sono altri valori continuo a scorrere cercando ancora il minimo
		
		// Creo l'array pr che contiene solo valori diversi tra loro, 
		// Dopo li ordino.
		for(i = 0;i<primeserie.length;i++){
			c=0;
			for(int j=i+1;j<primeserie.length;j++){
				if(primeserie[i]==primeserie[j]){
					c++;
				}
			}
			if(c==0){
				num++;
				pr.add(new Integer(primeserie[i]));
			}
		}

		// Chiamo la funzione che mi restituisce un array ordinato in modo crescente
		pr = ordinaArray(pr);
		int nprima=num;
		
		num = 0;
		c = 0;
		for(i = 0;i<secondeserie.length;i++){
			c=0;
			for(int j=i+1;j<secondeserie.length;j++){
				if(secondeserie[i]==secondeserie[j]){
					c++;
				}
			}
			if(c==0){
				num++;
				se.add(new Integer(secondeserie[i]));
			}
		}
		se = ordinaArray(se);
		int nseconda=num;
		
		
		num = 0;
		c = 0;
		for(i = 0;i<terzeserie.length;i++){
			c=0;
			for(int j=i+1;j<terzeserie.length;j++){
				if(terzeserie[i]==terzeserie[j]){
					c++;
				}
			}
			if(c==0){
				num++;
				te.add(new Integer(terzeserie[i]));
			}
		}
		te = ordinaArray(te);
		int nterza=num;
		
		
		num = 0;
		c = 0;
		for(i = 0;i<quarteserie.length;i++){
			c=0;
			for(int j=i+1;j<quarteserie.length;j++){
				if(quarteserie[i]==quarteserie[j]){
					c++;
				}
			}
			if(c==0){
				num++;
				qu.add(new Integer(quarteserie[i]));
			}
		}
		qu = ordinaArray(qu);
		int nquarta=num;
	
		
		seriefinali[0] = new int[nprima];
		yserie[0] = new int[nprima];
		occorrenze[0] = new int[nprima];
		
		seriefinali[1] = new int[nseconda];
		yserie[1] = new int[nseconda];
		occorrenze[1] = new int[nseconda];
		
		seriefinali[2] = new int[nterza];
		yserie[2] = new int[nterza];
		occorrenze[2] = new int[nterza];
		
		seriefinali[3] = new int[nquarta];
		yserie[3] = new int[nquarta];
		occorrenze[3] = new int[nquarta];
		
		for(i=0;i<nprima;i++){
			Integer t = getMinimum(pr).intValue();
			seriefinali[0][i] = t.intValue();
			pr.remove(t);
			
		}
		
		int count;
		int j=0;
		for(int e : seriefinali[0]){
			count = 0;
			for(int x : primeserie) {
				if(e==x){
					count++;
				}
			}
		occorrenze[0][j] = count;
		j++;
		}
		
		
		for(i=0;i<nseconda;i++){
			Integer t = getMinimum(se).intValue();
			seriefinali[1][i] = t.intValue();
			se.remove(t);
		}
		
		j=0;
		for(int e : seriefinali[1]){
			count = 0;
			for(int x : secondeserie) {
				if(e==x){
					count++;
				}
			}
		occorrenze[1][j] = count;
		j++;
		}
		
		
		for(i=0;i<nterza;i++){
			Integer t = getMinimum(te).intValue();
			seriefinali[2][i] = t.intValue();
			te.remove(t);
		}
		
		j=0;
		for(int e : seriefinali[2]){
			count = 0;
			for(int x : terzeserie) {
				if(e==x){
					count++;
				}
			}
		occorrenze[2][j] = count;
		j++;
		}
		
		
		
		
		for(i=0;i<nquarta;i++){
			Integer t = getMinimum(qu).intValue();
			seriefinali[3][i] = t.intValue();
			qu.remove(t);
		}
		
		j=0;
		for(int e : seriefinali[3]){
			count = 0;
			for(int x : quarteserie) {
				if(e==x){
					count++;
				}
			}
		occorrenze[3][j] = count;
		j++;
		}
		
	}

	
	
	/* EFFECTS: restituisce il minimo integer in un arraylist di integer, il min inizialmente e' fissto a 101, poiche'
	 * essendo serie da 10 colpi il massimo e' 100
	 */
	private Integer getMinimum(ArrayList<Integer> p){
		int min = 101;
		
		for( Integer e : p){
			
			if(e.intValue()<=min){
				min = e.intValue();
				return e;
			}
	
		}
		return null;
	}
	
	
	/* EFFECTS: funzioni ausiliarie per disenare i grafici. Da un array restituisce il valore minimo o massimo.
	 * 
	 */
	private int minoccorrenze(int[] a){
		int i; int m = a[0];
		
		for(i=0;i<a.length;i++){
			if(a[i]<m){
				m=a[i];
			}
		}
		return m;
	}
	
	private int maxoccorrenze(int[] a){
		int i; int m = 0;
		
		for(i=0;i<a.length;i++){
			if(a[i]>m){
				m=a[i];
			}
		}
		return m;
	}
	
	
	/*
	 * EFFECTS: disegna una linea, invertendo il segno alle y, poiche' ho ruotato 
	 */
	private void drawLineS(Graphics2D e,int x1,int y1,int x2,int y2){
		e.drawLine(x1, -y1, x2, -y2);
	}
	
	public void paint(Graphics e){
		Graphics2D e2 = (Graphics2D)e;
		e2.setFont(new Font("Serif",Font.PLAIN, 10));
		e2.clearRect(e2.getClipBounds().x,e2.getClipBounds().y,e2.getClipBounds().width,e2.getClipBounds().height);
		// Devo disegnare 4 grafici, uno per ogni serie. 
		// Sull asse delle ascisse posso avere valori che vanno da 0 a 100, e su quello delle ordinate valori che vanno da 0 a infinito.
		if(seriefinali[0]!=null && seriefinali[1]!=null && seriefinali[2]!=null && seriefinali[3]!=null){
			e2.translate(0,this.getSize().height);
			e2.setColor(Color.BLACK);

			// INIZIALIZZO LE VARIABILI PER IL PRIMO GRAFICO
			int numerovalori = seriefinali[0].length;
			int proxgrap = 0;
			int intervallox, intervalloy, j, minoccorrenze,maxoccorrenze;
			String[] nomiserie = {"Prima serie","Seconda serie","Terza serie","Quarta serie"};
			int pg = 0;
			
			// asse  x 
			drawLineS(e2,pg+35,50,pg+25*(numerovalori+2),50);
			// asse y : effettivamente va da 50 a 250 = 200
			drawLineS(e2,pg+50,35,pg+50,280);
			
			
			// RIPETO PER OGNI SERIE! 
			for(int ns = 0; ns<4; ns++){
				
				pg = proxgrap + pg;
				numerovalori = seriefinali[ns].length;
				minoccorrenze = minoccorrenze(occorrenze[ns]);
				maxoccorrenze = maxoccorrenze(occorrenze[ns]);
				proxgrap = 25*numerovalori + 50;
				//System.out.println("iT:"+ns +" Pg:"+pg+" Numval"+numerovalori+" Minocc"+minoccorrenze+"  Maxocc"+maxoccorrenze);
				
				e2.setColor(Color.RED);
				e2.setFont(new Font("Serif",Font.ITALIC, 12));
				e2.drawString(nomiserie[ns], pg+50,-15);
				e2.setColor(Color.BLACK);
				e2.setFont(new Font("Serif",Font.PLAIN, 10));
				
				// asse  x 
				drawLineS(e2,pg+35,50,pg+25*(numerovalori+2),50);
				// asse y : effettivamente va da 50 a 250 = 200
				drawLineS(e2,pg+50,35,pg+50,280);
				
				// intervallo di disegno diviso il massimo di occorrenze
				intervalloy = 200/maxoccorrenze;
				
				
				intervallox = 20;
				
				j = intervallox;
				
				for(int i = 0;i<numerovalori;i++){
					if(yserie[ns][i]<intervalloy*occorrenze[ns][i]){
						drawLineS(e2,pg+50+j, 50, pg+50+j, 50+yserie[ns][i]);
						drawLineS(e2,pg+51+j, 50, pg+51+j, 50+yserie[ns][i]);
						drawLineS(e2,pg+52+j, 50, pg+52+j, 50+yserie[ns][i]);
						
					}	
					else {
						drawLineS(e2,pg+50+j, 50, pg+50+j, 50+intervalloy*occorrenze[ns][i]);
						drawLineS(e2,pg+51+j, 50, pg+51+j, 50+intervalloy*occorrenze[ns][i]);
						drawLineS(e2,pg+52+j, 50, pg+52+j, 50+intervalloy*occorrenze[ns][i]);
						// Qui decido se fermare o no il timer
						if(maxo == occorrenze[ns][i]){
							timer.stop();
						}
					}
					// Mentre disegno calcolo il massimo delle occorrenze in modo da sapere quando fermare
					// il timer
					if(maxo<occorrenze[ns][i]){
						maxo = occorrenze[ns][i];
					}
					j=j+intervallox;
					
				}
				// Scrivo i numeri sull asse delle y
				j = intervalloy;
				minoccorrenze = 1;
				// dal minimo di occorrenze al massimo le metto tutte
				for(int i = 0;i<maxoccorrenze;i++){
					e2.drawString(minoccorrenze+"",pg+35, -(50+j));
					j=j+intervalloy;
					minoccorrenze = minoccorrenze+1;
					
				}
				
				// Scrivo i numeri sull asse delle x
				j = intervallox;
				for(int i = 0;i<numerovalori;i++){
					e2.drawString(seriefinali[ns][i]+"",pg+45+j, -35);
					j=j+intervallox;
				
				}
			}
		}
	}
}

