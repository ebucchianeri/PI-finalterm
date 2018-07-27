package shoot;

import java.awt.*;
import java.awt.event.*;
import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.TitledBorder;


/* Pannello che crea le varie statistiche, il costruttore prende come parametro un arraylist di risultati e crea
 * due diversi arraylist, rint e rord, da passare ad altri due pannelli, quelli delle statistiche
 * 
 */
class PannelloStatistiche extends JPanel implements ComponentListener  {
	ArrayList<Risultato> r;
	ArrayList<Risultato> rint; // arraylist di risultati non vuoti con almeno gli interi
	ArrayList<Risultato> rord; // arraylist di risultati ordinati per data, non vuoti con almeno gli interi
	PannelloCalcoli pc;
	PannelloGraficoSerie pgs; 
	PannelloGraficoSingolo pgsi;
	PannelloGraficoTotali pgt;
	JScrollPane s;
	
	/*
	public void setSize(Dimension d){
		this.setPreferredSize(new Dimension(d.width,d.height));
		this.setMinimumSize(new Dimension(d.width,d.height));
		this.setMaximumSize(new Dimension(d.width,d.height));
		s.setPreferredSize(new Dimension(d.width,d.height));
	}
	*/
	
	public PannelloStatistiche(ArrayList<Risultato> ris){
		r = ris;
		// Restituisce un arrayList di risultati, non vuoti, con almeno gli interi e ord. in modo crescente
		// che mi serve per le statistiche delle serie, inoltre inizializza anche ArrayList<Risultato> rint,
		// con risultati, non vuoti sia interi che decimali.
		if(ris!=null){
			rord = sistemaArray();
			if(rord!=null){
				
				Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
				this.setPreferredSize(new Dimension(rect.width,rect.height));
				this.setMinimumSize(new Dimension(rect.width,rect.height));
				this.setMaximumSize(new Dimension(rect.width,rect.height));
				JPanel pannello = new JPanel();
				// devo aggiungere il layout
				
				GridBagLayout gridbag = new GridBagLayout();
				GridBagConstraints c = new GridBagConstraints();
				pannello.setLayout(gridbag);
				
				s = new JScrollPane(pannello,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				s.setPreferredSize(new Dimension(rect.width,rect.height));
				JViewport p = s.getViewport();
				p.setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
						
				
				//TitledBorder(Border border, String title, int titleJustification, int titlePosition, Font titleFont, Color titleColor)
				s.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5),
						"Statistiche",TitledBorder.CENTER,TitledBorder.BELOW_TOP,new Font("Serif",Font.BOLD,25),Color.RED));
	
				// Se ho piu' di un risultato utile per le statistiche
				if (rord.size()>1){
					// PannelloCalcoli 
					pc = new PannelloCalcoli(r);
					// PannelloGraficoSerie vuole l'arraylist di risultati ordinati in modo crescente
					pgs = new PannelloGraficoSerie(rord);
					
					// Il PannelloGraficoTotali pero' non le vuole ordinate in modo crescente
					pgt = new PannelloGraficoTotali(rint);
					
					
					JScrollPane spc = new JScrollPane(pc,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					spc.setPreferredSize(new Dimension(210,400));
					spc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
					
					c.fill = GridBagConstraints.NONE;
					c.anchor = GridBagConstraints.WEST;
					c.gridx=0;
					c.gridy=0;
					c.insets.bottom = 0;
					c.insets.top = 0;
					c.insets.left = 0;
					c.insets.right = 0;
					//c.gridwidth = GridBagConstraints.REMAINDER;
					gridbag.setConstraints(spc, c);
					pannello.add(spc);
					
					c.fill = GridBagConstraints.NONE;
					c.anchor = GridBagConstraints.EAST;
					c.gridx=1;
					c.gridy=0;
					c.insets.bottom = 0;
					c.insets.top = 0;
					c.insets.left = 20;
					c.insets.right = 0;
					//c.gridwidth = GridBagConstraints.REMAINDER;
					gridbag.setConstraints(pgt, c);
					pannello.add(pgt);
					
					c.fill = GridBagConstraints.BOTH;
					c.anchor = GridBagConstraints.CENTER;
					c.gridx=0;
					c.gridy=1;
					c.insets.bottom = 15;
					c.insets.top = 0;
					c.insets.left = 0;
					c.insets.right = 0;
					c.gridwidth = GridBagConstraints.REMAINDER;
					gridbag.setConstraints(pgs, c);
					pannello.add(pgs);
		
				}
				else {
					if(r.get(0).colpi!=null){
						// Se ho un solo elemento
						pgsi = new PannelloGraficoSingolo(r.get(0));
						
						c.fill = GridBagConstraints.NONE;
						c.anchor = GridBagConstraints.WEST;
						c.gridx=0;
						c.gridy=0;
						c.insets.bottom = 0;
						c.insets.top = 0;
						c.insets.left = 0;
						c.insets.right = 0;
						c.gridwidth = GridBagConstraints.REMAINDER;
						gridbag.setConstraints(pgsi, c);
						pannello.add(pgsi);
					}
					else {
//						Se non ho risultati in rord
						JLabel pjl = new JLabel("Le statistiche sono calcolate su un solo risultato contenente il bersaglio associato,");
						JLabel pjl2 = new JLabel("oppure su un insieme di risultati di cui almeno uno deve contenere gli interi.");
						c.fill = GridBagConstraints.NONE;
						c.anchor = GridBagConstraints.NORTH;
						c.gridx=0;
						c.gridy=0;
						c.insets.bottom = 0;
						c.insets.top = 0;
						c.insets.left = 0;
						c.insets.right = 0;
						c.gridwidth = GridBagConstraints.REMAINDER;
						gridbag.setConstraints(pjl, c);
						pannello.add(pjl);
						
						c.fill = GridBagConstraints.NONE;
						c.anchor = GridBagConstraints.NORTH;
						c.gridx=0;
						c.gridy=1;
						c.insets.bottom = 0;
						c.insets.top = 0;
						c.insets.left = 0;
						c.insets.right = 0;
						c.gridwidth = GridBagConstraints.REMAINDER;
						gridbag.setConstraints(pjl2, c);
						pannello.add(pjl2);
					}
						
				}
				
				this.addComponentListener(this);
				this.add(s);
			}
		}
	}
	

    public void componentResized(ComponentEvent e) {
    	s.setPreferredSize(new Dimension(this.getSize().width,this.getSize().height));
    	s.setMinimumSize(new Dimension(this.getSize().width,this.getSize().height));
    }

    public void componentHidden(ComponentEvent e) {}

    public void componentMoved(ComponentEvent e) {}

    public void componentShown(ComponentEvent e) {}


	
	/* EFFECTS: restituisce un array di risultati non vuoti, con almeno gli interi, e ordinati per
	 *  totale intero crescente.
	 */
	private ArrayList<Risultato> sistemaArray(){
		// Devo togliere i valori solo decimali o solo di note
		ArrayList<Risultato> ris = new ArrayList<Risultato>();
		ArrayList<Risultato> risord = new ArrayList<Risultato>();
		
		for( Risultato e : r){
			if(e.intero == 1){
				ris.add(e);
			}
		}
		
		ArrayList<Risultato> rc = null;
		rint = (ArrayList<Risultato>)ris.clone();
		rc = (ArrayList<Risultato>)ris.clone();
		// adesso devo ordinare, quindi faccio get minimum e aggiungo volta volta.
		// Devo controllare che non siano null i risulati


		for(int i=0;i<ris.size();i++){
			Risultato m = getMinimum(rc);
			risord.add(m);
			rc.remove(m);
		}
		return risord;		
	}
	
	
	private Risultato getMinimum(ArrayList<Risultato> ris){
		Risultato rmin = null;
		int min = 400;
		
		for( Risultato e : ris){
			if(e.intero == 1){
				if(e.punti<=min){
					min = e.punti;
					rmin = e;
				}
			}
		}
		return rmin;
	}
}