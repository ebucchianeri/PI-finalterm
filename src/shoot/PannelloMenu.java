package shoot;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import javax.swing.*;

import java.util.*;
import java.io.*;

public class PannelloMenu extends JPanel implements ActionListener{
	
	JButton inserisci;
	JButton salva;
	JButton salvanome;
	JButton cancella;
	JButton carica;
	JButton modifica;
	JButton statistiche;
	int contatore;
	Risultato mod;
	PannelloElenco p;
	PannelloRisposte out;
	
    JFileChooser fc;
    File file;
	
    public void actionPerformed(ActionEvent e){
    	if(e.getSource() == salva){
			ObjectOutputStream oss;
			if(p.r!=null && p.r.size()!=0){
					if(file!=null){
						try {
							out.setText("Salvataggio in " + file.toString() +".");
							oss = new ObjectOutputStream(new FileOutputStream(file.toString()));
						
							oss.writeObject(p.getElencoRisultati());
							oss.close();
						}
						catch(FileNotFoundException ex){
							out.setText("Salvataggio in non riuscito: file non trovato.");
						} 
						catch (SecurityException ex){
							out.setText("Salvataggio in non riuscito.");
						} 
						catch (IOException ex){
							out.setText("Salvataggio in non riuscito.");
						}
					}
					else  {
						// Devo fare necessariamente il salvataggio con nome
						out.setText("Salvataggio in un nuovo file.");
			    		int returnVal = fc.showSaveDialog(this);
			            if (returnVal == JFileChooser.APPROVE_OPTION) {
			                file = fc.getSelectedFile();
			                try {
				                out.setText("Salvataggio in " + file.toString() +".");
				                out.setText("Salvataggio in " + file.toString() +".");
								oss = new ObjectOutputStream(new FileOutputStream(file.toString()));
							
								
								oss.writeObject(p.getElencoRisultati());
								oss.close();
			                } 
			                catch(FileNotFoundException ex){
								out.setText("Salvataggio in non riuscito: file non trovato.");
							} 
			                catch (SecurityException ex){
								out.setText("Salvataggio in non riuscito.");
							} 
			                catch (IOException ex){
								out.setText("Salvataggio in non riuscito.");
							}
							
			            } 
			            else {
			            	out.setText("Salvataggio annullato.");
			            }
					}
			}
			else {
				out.setText("Niente da salvare.");
			}
    	}
    	else if(e.getSource() == salvanome){
    		if(p.r!=null &&  p.r.size()!=0){
	    		out.setText("Salvataggio in un nuovo file.");
	    		int returnVal = fc.showSaveDialog(this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	            	ObjectOutputStream oss;
	                file = fc.getSelectedFile();
	                
	                
	                try {
		                out.setText("Salvataggio in " + file.toString() +".");
		                out.setText("Salvataggio in " + file.toString() +".");
						oss = new ObjectOutputStream(new FileOutputStream(file.toString()));
					
						
						oss.writeObject(p.getElencoRisultati());
						oss.close();
	                } catch(FileNotFoundException ex){

						out.setText("Salvataggio in non riuscito: file non trovato.");
					} catch (SecurityException ex){
						out.setText("Salvataggio in non riuscito.");
					} catch (IOException ex){
						out.setText("Salvataggio in non riuscito.");
					}
					
	            } else {
	            	out.setText("Salvataggio annullato.");
	            }
    		}
    		else {
    			out.setText("Niente da salvare.");
    		}
    		
    	}
    	else if(e.getSource() == cancella){
    		// Devo eliminare dall'arraylist dei risultati e dai pannelli da visualizzare tutti quelli che sono selezionati al momento
    		// del click su cancella.
    		int num = p.cancellaSelezionati();
    		out.setText(num + " risultati cancellati.");
    		
    	}
    	else if(e.getSource() == carica){
    		// Quando carico devo anche ri inizializzare il contatore col numero di id piu alto che trovo nel file
    		 int returnVal = fc.showOpenDialog(this);
    		 
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                file = fc.getSelectedFile();
	                //This is where a real application would open the file.
	                out.setText("Apro " + file.getName() + ".");
	                
	    	    	ObjectInputStream ois;
	    	    	ArrayList<Risultato> ris = new ArrayList<Risultato>();

	    			try{
	    				ois = new ObjectInputStream(new FileInputStream(file.toString()));
	    				ris = (ArrayList<Risultato>) ois.readObject();
	    				contatore = maxId(ris);
		    			p.setInsiemeRisultato(ris);
		    			p.stampaInfo(p.r);
		    			
	    			}
	    			catch(Exception ex){
	    				out.setText("Il file non puo' essere aperto.");
	    			}
	    			
	            } else {
	            	out.setText("Caricamento annullato.");
	            }
	            
	            
    		
    	}
    	else if(e.getSource() == modifica){
    		// Devo modificare il risultato selezionato. Dal pannello con la selezione risalgo al risultato da modificare (tramite
    		// l'id che deve essere unico. Se ne ho piu' di uno modifico solo il primo risultato del primo pannello che trovo selezionato
    		int idmod = p.getPrimoSelezionato();
    		if( idmod >= 0 ) {
    			// Ho qualcosa di selezionato
        		JFrame f = new JFrame();
        		Risultato r1 = p.r.get(p.getIndice(idmod));
        		
        		Container c = f.getContentPane();
        		PannelloInserimentoValori pi = new PannelloInserimentoValori(this,f,idmod);
        		c.add(pi);
        		
        		// adesso che ho costruito il pannello di inserimento dei valori devo riempire i campi a seconda di quello che
        		// ho a disposizione nel risultato
        		if(r1!=null){
        			
	        		pi.data.setText(r1.data.get(Calendar.DATE)+"/"+(r1.data.get(Calendar.MONTH)+1)+"/"+r1.data.get(Calendar.YEAR));
	        		if(r1.gara == 0){
	        			pi.allenamento.doClick();
	        		} else {
	        			pi.gara.doClick();
	        			pi.tipogara.setText(r1.nome);
	        			pi.luogogara.setText(r1.luogo);
	        		}
	        		
	        		if(r1.mouches == true){
	        			pi.numeromouches.setText(r1.nmouches+"");
	        		}
	        		// Solo le annotazioni vengono perse, il bersaglio se c'e' voglio mantenerlo!!!
	        		if(r1.colpi!=null){
	        			pi.colpi = r1.colpi;
	        		}
	        		
	        		// non ho un bersaglio inizializzato
	        		if(r1.intero == 1 && r1.decimale == 1){
	        			// ho un risultato con interi e decimali
	        			pi.interi.setSelected(true); pi.decimali.setSelected(true);
	        			for(int j = 0; j<4;j++){
	        				pi.campiinteri[j].setText(r1.serie[j]+"");
	        				pi.campidecimali[j].setText(r1.seriedecimali[j].doubleValue()+"");
	        			}
	        			pi.campiinteri[4].setText(r1.punti+"");
	        			pi.campidecimali[4].setText(r1.puntidecimali.doubleValue()+"");
	        			
	        		}
	        		else if(r1.intero == 1){
	        			// ho un risultato con solo interi
	        			pi.interi.setSelected(true); 
	        			for(int j = 0; j<4;j++){
	        				pi.campiinteri[j].setText(r1.serie[j]+"");
	        			}
	        			pi.campiinteri[4].setText(r1.punti+"");
	        			
	        		}
	        		else if(r1.decimale == 1){
	        			// ho un risultato con soli decimali
	        			pi.decimali.setSelected(true);
	        			for(int j = 0; j<4;j++){
	        				pi.campidecimali[j].setText(r1.seriedecimali[j].doubleValue()+"");
	        			}
	        			pi.campidecimali[4].setText(r1.puntidecimali.doubleValue()+"");
	        		}
	        		else {
	        			// ho un risultato vuoto
	        		}
	        	
	        		f.setVisible(true);
	        		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        		f.pack();
	        		
        		}
    		}
    		else {
    			out.setText("Nessun elemento selezionato.");
    		}
    	}
    	else if(e.getSource() == inserisci){
    		// Devo creare una nuova finestra per l'inserimento dei valori. Devo pero' riportare tutte le modifiche a 
    		// queste variabili d'istanza
    		JFrame f = new JFrame();
    		    		
    		Container c = f.getContentPane();
    		PannelloInserimentoValori pi = new PannelloInserimentoValori(this,f,0);
    		c.add(pi);
    		f.setVisible(true);
    		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		f.pack();
    	}
    	else if(e.getSource() == statistiche){
    		if (p.r!=null ){
    			// Chiedo al pannelloElenco quanti sono i selezionati al momento del click su statistiche.
    			int nselezionati = p.numeroSelezionati();
    			if(nselezionati == 0){
    				// Se non ci sono selezionati vado a scriverlo nella text area di controllo.
    				out.setText("Nessun elemento selezionato.");
    			}
    			else {
    			// adesso pero' devo prendere i selezionati e copiarli in un nuovo array da fare, e da passarli
    			// al PannelloStatistiche, non devo direttamente ordinare quelli che visualizzo.
    				out.setText(nselezionati+" elementi selezionati.");
    				// Ordina, in caso di errore restituisce null.
	    			ArrayList<Risultato> rr = p.creaArrayStat();
	    			// Se creaArrayStat restituisce null io non alloco.
	    			if(rr != null){
		    			JFrame f = new JFrame();
		    			PannelloStatistiche ps = new PannelloStatistiche(rr);
		    			Container c = f.getContentPane();
		    			c.add(ps);
		    			f.setVisible(true);
		    			f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    			f.pack();
	    			}
	    			else {
	    				out.setText("Errore nella creazione delle statistiche.");
	    			}
    			}
    		}
    	}
    }
    
    
    public void setRisultato(Risultato n, int id){
    	p.setRisultatoPerId(n,id);
    }
    
    private int maxId(ArrayList<Risultato> ris){
    	int max = 0;
	  	for( Risultato e : ris){
	  		if(e.id > max) max = e.id;
	  	}
	  	return max;
    }
	
    public void aggiornaRisultati(Risultato r){
    	p.addRisultato(r);
    	
    }
    
    
    
	public PannelloMenu(PannelloElenco pa, PannelloRisposte risposte){
		super();
		contatore = 0;
		out = risposte;
		p = pa;
		SpringLayout layout = new SpringLayout();
	    this.setLayout(layout);
		this.setBorder(BorderFactory.createEmptyBorder(30,0,0,0));
		
		
 
        //Create a file chooser
        fc = new JFileChooser();
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		

		inserisci = new JButton("Inserisci");
		salva = new JButton("Salva");
		salvanome = new JButton("Salva con nome");
		cancella = new JButton("Cancella");
		modifica = new JButton("Modifica");
		carica = new JButton("Carica");
		statistiche = new JButton("Statistiche");
		
		salva.addActionListener(this);
		salvanome.addActionListener(this);
		cancella.addActionListener(this);
		modifica.addActionListener(this);
		inserisci.addActionListener(this);
		carica.addActionListener(this);
		statistiche.addActionListener(this);
		this.add(carica);
		this.add(salva);
		this.add(salvanome);
		this.add(inserisci);
		this.add(modifica);
		this.add(cancella);
		this.add(statistiche);

	    
        SpringUtilities.makeCompactGrid(this,
                7, 1, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
        
	}
	
}