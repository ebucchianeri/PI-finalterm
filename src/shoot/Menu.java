package shoot;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* Menu, formato da 3 tasti o un solo tasto, che viene utilizzato insieme al bersaglio e permette la cancellazione dell
 * ultimo colpo, il salvataggio nel pannelloinserimentovalori dei dati inseriti o lo stamp. 
 */
public class Menu extends JPanel implements ActionListener {
	Bersaglio b;
	JButton cancella;
	JButton salva;
	JButton stamp;
	JFrame f;
	

		 
    public void actionPerformed(ActionEvent e){
    	// Se ho questo bottone cliccabile allora sono anche in una classe di inserimento
    	if(e.getSource() == cancella){
//    		System.out.println("CANCELLA ULTIMO");
    		b.cancellaUltimo();
    	}
    	else if (e.getSource() == salva){
//    		System.out.println("SALVATAGGIO");
//    		al momento del salva io dispose la finestra e passo al padre l'array di colpi
    		
    		ObjectOutputStream oss;
    		
    		if(b.inserimento == 0){
    			// Sono in una classe di visualizzazione, questo tasto non viene neanche creato.
    			
    			/*Risultato nuovo = null;
    			try {
    			nuovo = new Risultato(b.r.id,b.totaleintero,b.seriei[0],b.seriei[1],b.seriei[2],b.seriei[3],b.totaledecimale,b.seried[0],b.seried[1],b.seried[2],b.seried[3],2014,04,05,0,null,null,null,b.colpi);
    			}
    			catch(Exception ex){
    				System.out.println("eccezione"+ex.getMessage());
    			}
    			b.prisultato.modificaRisultato(nuovo);
    			*/
    			
    		}
    		else {
    			// sono in un bersaglio di inserimento, quando clicco salva controllo di aver inserito
    			// tutti e quaranta i colpi. POTREI INSERIRNE MENO?????
				if(b.n >= 40){ 
//					devo passare i valori alla finestra padre, e devo far in modo di memorizzare l'arrayList<risultato>
//					System.out.println("Devo salvare i punteggi inseriti");
					b.finestrapadre.colpi = b.colpi;
					

					// adesso devo inizializzare i vari campi
					b.finestrapadre.interi.setSelected(true);
					b.finestrapadre.decimali.setSelected(true);
					for(int t = 0;t<4;t++){
						b.finestrapadre.campiinteri[t].setText(b.seriei[t]+"");
					}
					b.finestrapadre.campiinteri[4].setText(b.totaleintero+"");
					for(int t = 0;t<4;t++){
						b.finestrapadre.campidecimali[t].setText(b.seried[t]+"");
					}
					b.finestrapadre.campidecimali[4].setText(b.totaledecimale+"");
				}
    		}
    	}
    	else if (e.getSource() == stamp){
//    		Salvo l'immagine con i colpi su un file png.
    		File file = null;
    		JFileChooser fc = null;
    	    BufferedImage bImg = new BufferedImage(f.getWidth(), f.getHeight()-40, BufferedImage.TYPE_INT_RGB);
    	    
    	    Graphics2D cg = bImg.createGraphics();
    	    f.paint(cg);
            fc = new JFileChooser();
            fc.setMultiSelectionEnabled(false);
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

            try {
            	int returnVal = fc.showSaveDialog(this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) 
	            	file = fc.getSelectedFile();
	            if (ImageIO.write(bImg, "png", new File(file.toString())))
	            {
	                System.out.println("Saved");
	            }
            }
            catch (SecurityException ex){
				System.out.println("Impossibile creare il file.");
            } catch (IOException x) {

    	    }

    	}
    } 
     
	
	/* COSTRUTTORE: se il bersaglio associato e' solo di visualizzazione, ovvero inserimento = 0, allora l'unico tasto che
	 * inserisco e' lo stamp, altrimenti, in caso di inserimento = 1, aggiungo tutti e 3 i tasti
	 */
	public Menu(Bersaglio be, JFrame fi){
		super();
		f = fi;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setPreferredSize(new Dimension(700,40));
		b = be;
		stamp = new JButton("Stamp");
		stamp.addActionListener(this);
		this.add(stamp);
		if(be.inserimento == 1) {
			cancella = new JButton("Cancella Ultimo");
			cancella.addActionListener(this);
			salva = new JButton("Salva");
			salva.addActionListener(this);
			this.add(cancella);
			this.add(salva);
		}
	}
}
