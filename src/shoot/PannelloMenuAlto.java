package shoot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

public class PannelloMenuAlto extends JPanel implements ActionListener{
	
	JButton selezionatutto;
	JButton deselezionatutto;
	JButton selezionagare;
	JButton selezionaallenamenti;
	JButton riordinaVR;
	JButton riordinaP;
	PannelloElenco p;
	JLabel divisore;
	
    public void actionPerformed(ActionEvent e){
    	if(e.getSource() == selezionatutto){
    		p.selezionaTutto();
    	}
    	else if(e.getSource() == deselezionatutto){
    		p.deselezionaTutto();
    	}
    	else if(e.getSource() == selezionagare){
    		p.selezionaGare();
    	}
    	else if(e.getSource() == selezionaallenamenti){
    		p.selezionaAllenamenti();
    	}
    	else if(e.getSource() == riordinaVR){
    		// Devo creare una nuova finestra per l'inserimento dei valori. Devo pero' riportare tutte le modifiche a 
    		// queste variabili d'istanza
    		p.riordinaVR();
    	}
    }
	public PannelloMenuAlto (PannelloElenco pa){
		super();
		divisore = new JLabel("||");
		p = pa;
		SpringLayout layout = new SpringLayout();
	    this.setLayout(layout);
	    
		this.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		
	
		selezionatutto = new JButton("Seleziona tutto");
		deselezionatutto = new JButton("Deseleziona tutto");
		selezionagare = new JButton("Seleziona gare");
		selezionaallenamenti = new JButton("Seleziona allenamenti");
		riordinaVR = new JButton("Ordine cronologico");
		selezionatutto.addActionListener(this);
		deselezionatutto.addActionListener(this);
		selezionagare.addActionListener(this);
		selezionaallenamenti.addActionListener(this);
		riordinaVR.addActionListener(this);
		this.add(selezionatutto);
		this.add(deselezionatutto);
		this.add(selezionagare);
		this.add(selezionaallenamenti);
		this.add(divisore);
		this.add(riordinaVR);


		
	    
	    SpringUtilities.makeCompactGrid(this,
	            1, 6, //rows, cols
	            6, 6,        //initX, initY
	            6, 6);       //xPad, yPad
	    
	}

}
