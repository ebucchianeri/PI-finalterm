package shoot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

class PannelloRisultato extends JPanel implements MouseListener {
	// allenamento deve avere obbligatorio solo la data
	// le gare devono avere nome, luogo e data e punteggio intero
	Risultato r;
	int larghezza;
	int altezzamin;
	int altezzamax;
	Color coloresfondo;
	Color coloresfondoselezionato;
	PannelloElenco padre;
	String titolo;
	int selezionato;
	int ingrandito;
	BufferedImage imgc;
	BufferedImage imgbn;

	public void mousePressed(MouseEvent e) {    }

    public void mouseReleased(MouseEvent e) {	}

    public void mouseEntered(MouseEvent e) {     }

    public void mouseExited(MouseEvent e) {     }

    public void mouseClicked(MouseEvent e) {   
    	// Se il click avviene nei quadratini ho delle azioni, altrimenti ho la selezione
    		//System.out.println("Click on "+e.getX()+" "+e.getY());
    		if(e.getX() <= (larghezza-43) && e.getX() > (larghezza-74) && e.getY() <= (altezzamin - 28) && e.getY() > (altezzamin - 59)) {
    			//System.out.println("CLICK!!!");
//    			Ho il click su il tasto delle annotazioni, devo espandere o restingere il pannello dei risultati a seconda
//    			della variabile ingrandito
    			if(r.note!=null){
	    			if (ingrandito == 0) {
		    			ingrandito = 1;
		    			this.setSize(larghezza,altezzamax);
		    			this.setPreferredSize(new Dimension(larghezza,altezzamax));
		    			this.setMaximumSize(new Dimension(larghezza,altezzamax));
		    			this.setMinimumSize(new Dimension(larghezza,altezzamax));
		    			impostaTitolo();
		    		}
		    		else {
		    			ingrandito = 0;
		    			this.setSize(larghezza,altezzamin);
		    			this.setPreferredSize(new Dimension(larghezza,altezzamin));
		    			this.setMaximumSize(new Dimension(larghezza,altezzamin));
		    			this.setMinimumSize(new Dimension(larghezza,altezzamin));
		    			impostaTitolo();
		    		}
		    		padre.revalidate();
    			}
    		} 
    		else if(e.getX() <= (larghezza-9) && e.getX() > (larghezza-40) && e.getY() <= (altezzamin - 28) && e.getY() > (altezzamin - 59)) {
//    			System.out.println("CLICK su PALLINI" + (larghezza-10)+" "+(larghezza-40)+" "+(altezzamin - 29)+" "+(altezzamin - 61));
//    			ho il click sul tasto dei pallini
    			if(r.colpi!=null){
    				// VISUALIZZO IL BERSAGLIO
    				JFrame f = new JFrame();
    				f.setVisible(true);
    				f.setResizable(false);
    				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    				
    				Container c = f.getContentPane();
    				c.setLayout(new BorderLayout());
    				Punti p = new Punti(f,0);
    				Bersaglio d = new Bersaglio(p,r.colpi,r,this);
    				// In caso di modifiche Bersaglio modifica nel pannelloRisultato il puntatore a risultato.
    				// Adesso devo apportare tali modifiche anche all'esterno, ovvero in r di PannelloElenco
    				
    				d.addMouseListener(d);
    				d.addMouseMotionListener(d);
    				Menu m = new Menu(d,f);

    				c.add(d,BorderLayout.CENTER);
    				c.add(p,BorderLayout.EAST);
    				c.add(m,BorderLayout.SOUTH);
    				f.pack();
    			}
    		}    		
    		else {
//    			Ho il click sul solo pannello e quindi devo selezionare o deselezionare a seconda del valore della
//    			variabile selezionato
    			if (ingrandito == 0) {
    				if (e.getX() <= (larghezza) && e.getX() >= 0 && e.getY() <= (altezzamin-2) && e.getY() >= 2) {
    					if (selezionato == 0) {
    		    			selezionato = 1;
    	    			}
    	    			else {
    	    				selezionato = 0;
    	    			}
    				}
	    		}
    			else {
    				if (e.getX() <= (larghezza) && e.getX() >= 0 && e.getY() <= (altezzamax-2) && e.getY() >= 2) {
    					if (selezionato == 0) {
    		    			selezionato = 1;
    	    			}
    	    			else {
    	    				selezionato = 0;
    	    			}
    				}
    			}
    			
	    	repaint();
    		}
    } 
	
    /* Funzione per creare il titolo del pannello del risultato, a seconda delle informazioni contenute
     * nel risultato associato.
     */
    
    private void impostaTitolo(){
		//Titled borders
    	Border b = null;
    	TitledBorder title = null; 
		if (r.gara == 0){
			titolo = r.data.get(Calendar.DATE)+"/"+(r.data.get(Calendar.MONTH)+1)+"/"+r.data.get(Calendar.YEAR);
			b = BorderFactory.createEmptyBorder(2,2,2,2);
			title = BorderFactory.createTitledBorder(b,titolo,TitledBorder.LEFT,TitledBorder.BELOW_TOP);
			title.setTitleColor(new Color(46,131,131));
		}
		else {
			titolo = r.data.get(Calendar.DATE)+"/"+(r.data.get(Calendar.MONTH)+1)+"/"+r.data.get(Calendar.YEAR)+" - "+r.nome+"@"+r.luogo;
			b = BorderFactory.createEmptyBorder(2,2,2,2);
			title = BorderFactory.createTitledBorder(b,titolo,TitledBorder.LEFT,TitledBorder.BELOW_TOP);
			title.setTitleColor(new Color(34,34,90));
		}
		
		
		Font deltitolo = new Font(Font.SERIF,Font.BOLD,14);
		title.setTitleFont(deltitolo);
		this.setBorder(title);
    }
    
	public PannelloRisultato(Risultato r1, PannelloElenco f){
		super();	
		r = r1;
		selezionato = 0; ingrandito = 0;
		larghezza = 600; altezzamin = 80; altezzamax = 120;
		padre = f;
		this.setPreferredSize(new Dimension(larghezza,altezzamin));
		this.setMaximumSize(new Dimension(larghezza,altezzamin));
		this.setMinimumSize(new Dimension(larghezza,altezzamin));
		this.addMouseListener(this);
		this.impostaTitolo();
		
		// Carico le immagini per il tasto della schermata pallini
		try {
		    imgc = ImageIO.read(new File("images/pallinic.png"));
		    imgbn = ImageIO.read(new File("images/pallinibn.png"));
		} catch (IOException e) {
			System.out.println("Errore nel caricamento delle immagini");
		}
		
		coloresfondo = Color.WHITE;
		coloresfondoselezionato = new Color(157,178,174);
				//new Color(192,138,138);


	}
	
	/* Stampo nel pannello le informazioni del risultato associato
	 * 
	 */
	private void displayInfo(Graphics2D e, Risultato r){
		if (r.gara == 0){
			if(r.mouches==false){
				if(r.intero == 1 && r.decimale == 1) {
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 50);
				}
				else if(r.intero == 1){
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
				}
				else if(r.decimale == 1){
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 35);
				}
				// Se non ho interi e nemmeno decimali
				else {}
			}
			else {
				// Ho anche le mouches
				if(r.intero == 1 && r.decimale == 1) {
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 50);
				}
				else if(r.intero == 1){
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
				}
				else if(r.decimale == 1){
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
				}
				// Se non ho interi e nemmeno decimali
				else {}
			}
		}
		else {
			if(r.mouches==false){
				if(r.intero == 1 && r.decimale == 1) {
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 50);
				}
				else if(r.intero == 1){
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
				}
				else if(r.decimale == 1){
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 35);
					
				}
				else {}
			}
			else {
				if(r.intero == 1 && r.decimale == 1) {
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 50);
				}
				else if(r.intero == 1){
					e.drawString(r.punti+": "+r.serie[0]+" "+r.serie[1]+" "+r.serie[2]+" "+r.serie[3], 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
				}
				else if(r.decimale == 1){
					e.drawString(r.puntidecimali.doubleValue()+": "+r.seriedecimali[0].doubleValue()+" "+r.seriedecimali[1].doubleValue()+" "+r.seriedecimali[2].doubleValue()+" "+r.seriedecimali[3].doubleValue(), 20, 35);
					e.drawString("#mouches: "+r.nmouches, 250, 35);
				}
				else {}
			}
		}
		if(ingrandito == 1 && r.note!=null){
			drawString(e,r.note,10,60);
		}
	}
	
	public void drawString(Graphics g, String text, int x, int y) {
	    for (String line : text.split("\n"))
	        g.drawString(line, x, y += g.getFontMetrics().getHeight());
	}
	
	private void disegnaPallini(Graphics2D e2){
		// Devo disegnare i pallini tra -40 e -10 e -60 e -30
		//e2.drawOval(larghezza - 30, altezzamin - 50, 10, 10);
		if(r.colpi!=null){
			if(imgc != null){
//				Se l'immagine e' stata caricata con successo disegno quella.
				e2.drawImage(imgc, null, larghezza - 40, altezzamin - 60);
			}
			else {
//				Altrimenti disegno i vari cerchi
				e2.setColor(Color.YELLOW);
				e2.fillOval(larghezza - 35, altezzamin - 55, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 35, altezzamin - 55, 10, 10);
				e2.setColor(Color.YELLOW);
				e2.fillOval(larghezza - 28, altezzamin - 46, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 28, altezzamin - 46, 10, 10);
				e2.setColor(Color.RED);
				e2.fillOval(larghezza - 33, altezzamin - 48, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 33, altezzamin - 48, 10, 10);
				e2.setColor(Color.RED);
				e2.fillOval(larghezza - 30, altezzamin - 50, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 30, altezzamin - 50, 10, 10);
			}
		}
		else {
			if(imgbn != null){
				e2.drawImage(imgbn, null, larghezza - 40, altezzamin - 60);
			}
			else {
				e2.setColor(Color.GRAY);
				e2.fillOval(larghezza - 35, altezzamin - 55, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 35, altezzamin - 55, 10, 10);
				e2.setColor(Color.GRAY);
				e2.fillOval(larghezza - 28, altezzamin - 46, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 28, altezzamin - 46, 10, 10);
				e2.setColor(Color.GRAY);
				e2.fillOval(larghezza - 33, altezzamin - 48, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 33, altezzamin - 48, 10, 10);
				e2.setColor(Color.GRAY);
				e2.fillOval(larghezza - 30, altezzamin - 50, 10, 10);
				e2.setColor(Color.BLACK);
				e2.drawOval(larghezza - 30, altezzamin - 50, 10, 10);
			}
		}
	}
	
	/* PAINT disegno la componente.
	 */
	public void paintComponent(Graphics e){
		super.paintComponent(e); 
		Graphics2D e2 = (Graphics2D)e;
		Color c = e2.getColor();
		e2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		
		if ( selezionato == 0 ) {
			e2.setColor(coloresfondo);
			if (ingrandito == 0) {
				e2.fillRect(0, 2, larghezza, altezzamin-2);
			}
			else {
				e2.fillRect(0, 2, larghezza, altezzamax-2);
			}
		}
		else {
			e2.setColor(coloresfondoselezionato);
			if (ingrandito == 0) {
				e2.fillRect(0, 2, larghezza, altezzamin-2);
			}
			else {
				e2.fillRect(0, 2, larghezza, altezzamax-2);
				
			}
		}
		
		e2.setColor(c);
		int w = this.getSize().width;
		displayInfo(e2,r);
		
		// Disegno i pulsanti delle 2 funzionalita'
		e2.drawRect(larghezza - 74, altezzamin - 60, 30, 30);
		//e2.drawRect(larghezza - 65, altezzamin - 59, 22, 20);
		e2.drawRect(larghezza - 40, altezzamin - 60, 30, 30);
		
		// Devo disegnare i pallini tra -40 e -10 e -60 e -30
		disegnaPallini(e2);
		e2.setColor(c);


		if (ingrandito == 0){
			if (r.note==null) {
				e2.setColor(Color.GRAY);
			}
			else e2.setColor(c);
			Font normale = e2.getFont();
			Font esp = new Font(Font.SERIF,Font.PLAIN,20);
			e2.setFont(esp);
			e2.drawString("+", larghezza - 67, 41);
			e2.setFont(normale);
			
		}
		else {
			Font normale = e2.getFont();
			Font esp = new Font(Font.SERIF,Font.PLAIN,20);
			e2.setFont(esp);
			e2.drawString("-", larghezza - 63, 41);
			e2.setFont(normale);
		}
	}

}
