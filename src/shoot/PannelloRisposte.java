package shoot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* Pannello che serve per stampare le informazioni su modifiche ai vari risultati visualizzati
 */
public class PannelloRisposte extends JPanel{

	JFrame f;
	JTextField stringa;

	public PannelloRisposte(JFrame fi){
		super();
		this.f = fi;
		stringa = new JTextField();
		stringa.setEditable(false);
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=0;
		c.insets.bottom = 5;
		c.insets.top = 5;
		c.insets.left = 0;
		c.insets.right = 0;
		gridbag.setConstraints(stringa, c);
		this.add(stringa);
	}
	
	public void paintComponent(Graphics e){
		super.paintComponent(e);
		//System.out.println(f.getSize().width+" "+f.getSize().height);
		stringa.setPreferredSize(new Dimension(f.getSize().width-50,20));
		stringa.setMinimumSize(new Dimension(f.getSize().width-50,20));
		stringa.setMaximumSize(new Dimension(f.getSize().width-50,20));
		stringa.setSize(new Dimension(f.getSize().width-50,20));

	}
	
	// Funzione per modificare il testo visualizzato
	public void setText(String s){
		stringa.setText(s);
		this.repaint();
	}
}

