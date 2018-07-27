package shoot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;


/* E' il pannello che visualizza il numero del colpo e il suo valore. E' posizionato a lato del bersaglio e
 * viene costantemente aggiornato dalle chiamate dell'oggetto Bersaglio
 */
class Punti extends JPanel implements MouseMotionListener{
	JFrame padre;
	Bersaglio b;
	int numero;
	int i;
	ArrayList<Colpo> cc;
	int inserimento;
	Rectangle primi;
	Rectangle secondi;
	Rectangle terzi;
	Rectangle quarti;
	
 	public void mouseDragged(MouseEvent e) {}
 	
 	public void mouseMoved(MouseEvent e){
 		if(primi.contains(e.getX(), e.getY())){
 			b.mouseover = 1;
 			b.repaint();
 		}
 		else if(secondi.contains(e.getX(), e.getY())){

 			b.mouseover = 2;
 			b.repaint();
 		}
 		else if(terzi.contains(e.getX(), e.getY())){
 			b.mouseover = 3;
 			b.repaint();
 		}
 		else if(quarti.contains(e.getX(), e.getY())){

 			b.mouseover = 4;
 			b.repaint();
 		}
 		else {
 			b.mouseover = 0;
 			b.repaint();
 		}
 			
 	}
 	
	
	public Punti(JFrame f, int ins){
		super();
		inserimento = ins;
		//pj = new ArrayList<JLabel>();
		//this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(100,650));
		padre = f;
		numero = 0;
		// So se e' una di visualizzazione gestisco i movimenti del mouse
		if( inserimento == 0) {
			this.addMouseMotionListener(this);
		}
		
		int l = 15+(15*9)+3;
		primi = new Rectangle(10, 0,  80, l);
		int m = l;
		secondi = new Rectangle(10, m+3,  80, l);
		m = m + (15*10)+3 + 3;
		terzi = new Rectangle(10, m+3,  80, l);
		m = m +(15*10)+3 + 3;
		quarti = new Rectangle(10, m+3,  80,l);
	}
	
	/* Preso un array di colpi e il loro numero effettivo, stampa
	 * i vari valori dei colpi, uno sotto l'altro
	 */
	public void set(ArrayList<Colpo> c, int n){
		numero = n+1;
		cc = c;
		this.repaint();
	}
	
	public void paintBackground(Graphics e){
		e.clearRect(0, 0, this.getSize().width,this.getSize().height);
	}
		
	public void paint(Graphics e){
		Graphics2D e2 = (Graphics2D)e;
		this.paintBackground(e);
		int j;
		int i1 = 0, i2 = 0, i3 = 0;
		
		e.setColor(Color.WHITE);
		e.fillRect(primi.x, primi.y, primi.width,  primi.height);
		e.fillRect(secondi.x, secondi.y, secondi.width,  secondi.height);
		e.fillRect(terzi.x, terzi.y, terzi.width,  terzi.height);
		e.fillRect(quarti.x, quarti.y, quarti.width,  quarti.height);
		
		e.setColor(Color.BLACK);
		for(i=0; i<numero; i++){

			j = i+1;
			Colpo p = cc.get(i);
			e.drawString("#"+j+": "+p.intero+","+p.decimale, 10, 15+(15*i)+i1+i2+i3);
			
			 e.setColor(Color.BLACK);
			if(i==9) i1 = 5;
			if(i==19) i2 = 5;
			if(i==29) i3 = 5;

			

		}
		
		
	}
	
}