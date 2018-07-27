package shoot;


import java.awt.Dimension;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.math.BigDecimal;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.*;
import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import java.lang.reflect.InvocationTargetException;


/**   JTextFieldLimit  ||  PannelloInserimentoValori  **/

class JTextFieldLimit extends PlainDocument {
	  private int limit;

	  JTextFieldLimit(int limit) {
	   super();
	   this.limit = limit;
	   }

	  public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
	    if (str == null) return;

	    if ((getLength() + str.length()) <= limit) {
	      super.insertString(offset, str, attr);
	    }
	  }
}


class PannelloInserimentoValori extends JPanel implements ActionListener{

	ArrayList<Colpo> colpi;
	JRadioButton gara = null;
	JRadioButton allenamento = null;
	int g;
	JPanel pannelloradio = null;
	JCheckBox interi = null;
	JPanel pannellointeri = null;
	JTextField[] campiinteri = null;
	int i;
	JCheckBox decimali = null;
	JPanel pannellodecimali = null;
	JTextField[] campidecimali = null;
	int d;
	JLabel[] descrizioneinfo = null;
	JScrollPane scrollnote = null;
	JTextArea note = null;
	JTextField data = null;
	JTextField tipogara = null;
	JTextField luogogara = null;
	JLabel errore = null;
	JButton submit = null;
	JButton inseriscicolpi = null;
	Risultato punteggio = null;
	JPanel pannellom = null;
	JLabel mouches;
	JTextField numeromouches=null;
	int modificavalori; // Se 0 indica che stiamo aggiungendo nuovo risultato, se >0 indica che stiamo modificando un risultato
	PannelloMenu padre;
	JFrame finestra;



		
	public void actionPerformed(ActionEvent e){
	// questo e' il metodo che viene invocato quando un pulsante e' premuto, ovviamente devo aggiungerlo 
	// come listener dei vari bottoni che voglio controllare
		JButton b = (JButton)e.getSource();
		

		// Se ho premuto il bottone inserisci, significa che ho gia' inserito tutti i valori utili e adesso devo creare un oggetto
		// di tipo Risultato, chiamando la funzione del pannello superiore salvaValori()
		if(b == submit){
			punteggio = salvaValori(g);
			if (punteggio!=null) {
				if(modificavalori>0){
					// Sto modificando risultati gia' inseriti
					errore.setText("Inserimento corretto.");
					padre.setRisultato(punteggio,modificavalori);
				} else {
					// Sto aggiungendo nuovi risultati se modificavalori e' 0
					errore.setText("Inserimento corretto.");
					padre.aggiornaRisultati(punteggio);
				}
				finestra.dispose();

			}
			else {
//				System.out.println("risultato non valido! L'oggetto non e' stato costruito");
			}
		}
		else if(b == inseriscicolpi){
			// Devo creare una finestra con un Bersaglio e alla fine, quando clicco salva
			// salvare i valori inseriti in questo pannello
		
				JFrame f = new JFrame();
				f.setVisible(true);
				f.setResizable(false);
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				Container c = f.getContentPane();
				c.setLayout(new BorderLayout());
				Punti p = new Punti(f,1);
				Bersaglio d = new Bersaglio(p,this);
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

	
	
	//GESTORI DEI PULSANTI!
	class RadioButtonListener implements ActionListener {
		// questo e' il metodo che viene invocato quando un pulsante e' premuto, ovviamente devo aggiungerlo 
		// come listener dei vari bottoni che voglio controllare
		public void actionPerformed(ActionEvent e){
			 
			//Se e' stata spuntata la casella di allenamento, allora memorizzo g = 0, dove 0 e' allenamento, altrimenti 1.
			if(e.getActionCommand().equals("Allenamento")){
				g=0;
			}
			else if(e.getActionCommand().equals("Gara")){
				g=1;
			}
		}
	}
	
	class JCheckBoxListenerI implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
	        Object source = e.getItemSelectable();
	 
	        if (source == interi) {
	            i = 1;
	        }
	        //Now that we know which button was pushed, find out
	        //whether it was selected or deselected.
	        if (e.getStateChange() == ItemEvent.DESELECTED) {
	            i = 0;
	        } 
	 	}
	}
	
	class JCheckBoxListenerD implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
	        Object source = e.getItemSelectable();
	 
	        if (source == decimali) {
	            d = 1;
	        }
	        //Now that we know which button was pushed, find out
	        //whether it was selected or deselected.
	        if (e.getStateChange() == ItemEvent.DESELECTED) {
	            d = 0;
	        } 
	 	}
	}
	
	class KeyAdapterT extends KeyAdapter {
		@Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
           
                if (e.getModifiers() > 0) {
                	JTextArea b = (JTextArea)e.getSource();
                    b.transferFocusBackward();
                } else {
                	JTextArea b = (JTextArea)e.getSource();
                	b.transferFocus();
                }
                e.consume();
            }
        }
    }
	
	/**
	* METODO PER MANDARE A CAPO UN TESTO TAGLIANDOLO AL PRIMO SPAZIO RILEVATO DA DESTRA
	*

	* Il metodo retitisce la stringa suddivisa in più linee, senza troncare le parole, ripettando una lunghezza massima comune per ogni linea
	* @param lineaToWordWrap
	* stringa da suddividere in più linee
	* @param lenMax
	* lunghezza massima oltre la quale la linea viene mandata a capo, cercando di non tagliare le parole a metà
	* @return
	*/
	public static String setWordWrap(String lineaToWordWrap, int lenMax){

	String returnValue = "";
	String ultimoCarattere = "";
	int indiceWrap = 0;
	int i = 0;

	// controllo se raggiunta lunghezza max
	if (lineaToWordWrap.length()>lenMax){

	// ciclo per l'intera lunghezza massima
	for(i=0;i<lenMax;i++){

	// recupero nuovo indice
	indiceWrap = lenMax-i;

	// recupera carattere al limite
	ultimoCarattere = String.valueOf(lineaToWordWrap.charAt(indiceWrap)) ;

	// se il carattere è uguale a spazio
	if(ultimoCarattere.equals(" ")){
	break;
	}

	} // fine ciclo

	// controllo se raggiunto limite eseguo taglio anche se non ho trovato lo spazio
	if(i==lenMax){

	// recupero linea al limite
	returnValue = lineaToWordWrap.substring(0,lenMax).trim();

	// aggiungo la successiva riga limitata a sua volta
	returnValue = returnValue + "\r\n" + setWordWrap(lineaToWordWrap.substring(lenMax).trim (), lenMax);

	}else{

	// recupero riga limitata
	returnValue = lineaToWordWrap.substring(0,indiceWrap).trim();

	// aggiungo la successiva riga limitata a sua volta
	returnValue = returnValue + "\r\n" + setWordWrap(lineaToWordWrap.substring(indiceWrap). trim(), lenMax);
	}

	}else{

	// ritorno la linea senza alcuna limitazione
	returnValue = lineaToWordWrap;

	} // fine controllo se raggiunta lunghezza max

	return returnValue;

	}
	
	public PannelloInserimentoValori(PannelloMenu p, JFrame f, int mod){
		colpi = null;
		padre = p;
		modificavalori = mod;
		finestra = f;
		this.setPreferredSize(new Dimension(230,700));
		this.setMinimumSize(new Dimension(230,700));
		this.setMaximumSize(new Dimension(230,700));
		//pannellochiamante = pannellomenu;
		// Inizializzo i radiobutton, aggiungo che possa essere selezionata con ALT+G/A, la actionCommand predefinita prende il valore del label
		// setto come valore iniziale allenamento = true
		
		inseriscicolpi = new JButton("Inserisci colpi");
		inseriscicolpi.addActionListener(this);
		
		pannelloradio = new JPanel(new SpringLayout());
		gara = new JRadioButton("Gara");
		allenamento = new JRadioButton("Allenamento");
		gara.setMnemonic(KeyEvent.VK_G);
		allenamento.setMnemonic(KeyEvent.VK_A);
		allenamento.setSelected(true);
		
		pannelloradio.add(gara);
		pannelloradio.add(allenamento);
		
		SpringUtilities.makeCompactGrid(pannelloradio, 
				1, 2, //rows, cols
                3, 3,        //initX, initY, distanza dall'angono in alto a sinistra della casella dedicata al pannello
                3, 3);       //xPad, yPad 
		
		// Raggruppo i radioButton, in un buttongroup, e aggiungo gli ActionListener di tipo RadioButtonListener
		ButtonGroup group = new ButtonGroup();
        group.add(gara);
        group.add(allenamento);  
        gara.addActionListener(new RadioButtonListener());
        allenamento.addActionListener(new RadioButtonListener());
		
		
        // PARTE INTERA: Inizializzo le 5 text area per raccogliere i punti interi
        interi = new JCheckBox("Interi");
		interi.addItemListener(new JCheckBoxListenerI());
		interi.setSelected(false);
		
		pannellointeri = new JPanel(new SpringLayout());
		pannellointeri.setPreferredSize(new Dimension(200,130));
	    pannellointeri.setMinimumSize(new Dimension(200,130));
	    pannellointeri.setMaximumSize(new Dimension(200,130));
		String[] labelsinteri = {"1^ serie intera: ", "2^ serie intera: ", "3^ serie intera: ", "4^ serie intera: ", "Totale intero: "};
		int numPairs = labelsinteri.length;
		campiinteri = new JTextField[5];
		for (int i = 0; i < numPairs; i++) {
	         JLabel l = new JLabel(labelsinteri[i], JLabel.TRAILING);
	         pannellointeri.add(l);
	         campiinteri[i] = new JTextField();
	         //campiinteri[i].setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
	         campiinteri[i].setPreferredSize(new Dimension(60,20));
	         campiinteri[i].setDocument(new JTextFieldLimit(3));
	         campiinteri[i].addKeyListener(new KeyAdapterT());
	         l.setLabelFor(campiinteri[i]);
	         pannellointeri.add(campiinteri[i]);
	     }
		
		
        SpringUtilities.makeCompactGrid(pannellointeri, 
        								numPairs, 2, //rows, cols
                                        3, 3,        //initX, initY, distanza dall'angono in alto a sinistra della casella dedicata al pannello
                                        3, 3);       //xPad, yPad 


		decimali = new JCheckBox("Decimali");
		decimali.addItemListener(new JCheckBoxListenerD());
		decimali.setSelected(false);

		
		
		// Inizializzo le 5 text area per raccogliere i punti decimali
        pannellodecimali = new JPanel(new SpringLayout());
        pannellodecimali.setPreferredSize(new Dimension(200,130));
        pannellodecimali.setMinimumSize(new Dimension(200,130));
        pannellodecimali.setMaximumSize(new Dimension(200,130));
		String[] labelsdecimali = {"1^ serie decimale: ", "2^ serie decimale: ", "3^ serie decimale: ", "4^ serie decimale: ", "Totale decimale: "};
		int numPairsd = labelsdecimali.length;
		campidecimali = new JTextField[5];
		for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labelsdecimali[i], JLabel.TRAILING);
            pannellodecimali.add(l);
            campidecimali[i] = new JTextField();
            //campidecimali[i].setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            campidecimali[i].setPreferredSize(new Dimension(60,20));
            campidecimali[i].setDocument(new JTextFieldLimit(5));
            campidecimali[i].addKeyListener(new KeyAdapterT());
            l.setLabelFor(campidecimali[i]);
            pannellodecimali.add(campidecimali[i]);
        }
 
        SpringUtilities.makeCompactGrid(pannellodecimali,
                                        numPairs, 2, //rows, cols
                                        3, 3,        //initX, initY
                                        3, 3);       //xPad, yPad
        
        // Inserisco il campo per inserire le mouches
        pannellom = new JPanel(new SpringLayout());

        JLabel mouches = new JLabel("Numero mouches: ");
        numeromouches = new JTextField();
        numeromouches.addKeyListener(new KeyAdapterT());
        numeromouches.setDocument(new JTextFieldLimit(3));
        numeromouches.setPreferredSize(new Dimension(60,20));
        pannellom.add(mouches);
        pannellom.add(numeromouches);
        
        SpringUtilities.makeCompactGrid(pannellom,
                                        1, 2, //rows, cols
                                        2, 8,        //initX, initY
                                        2, 8);       //xPad, yPad
        
		// Posso inserire informazioni sulla gara. Prima inizializzo i JLabel che descivono quale e dove scrivere l'info
        String[] labelsinfo = {"Annotazioni: ","Tipo gara: ", "Luogo gara: ", "Data: "};
        descrizioneinfo = new JLabel[4];
        for( int i = 0; i<4; i++){
        	descrizioneinfo[i] = new JLabel(labelsinfo[i]);
        }
		
        note = new JTextArea();
        note.addKeyListener(new KeyAdapterT());
		note.setLineWrap(true);
		note.setWrapStyleWord(true);
		note.setDocument(new JTextFieldLimit(322));
        scrollnote = new JScrollPane(note,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scrollnote.setPreferredSize(new Dimension(200,30));
        scrollnote.setMaximumSize(new Dimension(200,30));
        scrollnote.setMinimumSize(new Dimension(200,30));
		tipogara = new JTextField();
		tipogara.setDocument(new JTextFieldLimit(20)); 
		luogogara = new JTextField();
		luogogara.setDocument(new JTextFieldLimit(20));
		//Adesso inizializzo la parte per ricevere la data
		data = new JTextField();
		data.setDocument(new JTextFieldLimit(10));
		//data.addMouseListener(this);
		data.addKeyListener(new KeyAdapterT());
		data.setText("gg/mm/aaaa");
		
		
		// Inizializzo il bottone di submit, ci aggiungo anche l'ActionListener per gestire l'evento
		submit = new JButton("Inserisci");
		submit.addActionListener(this);
		
		

		
		// Setto il layout del pannello passato come parametro al costruttore, che conterra' le TextArea e Bottoni

		
		//this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		//this.setLayout(new GridBagLayout());

		// Creo il JLabel che mostrera' le informazioni sugli errori verificati.
		errore = new JLabel(convertToMultiline(setWordWrap("E' necessario inserire la data",30)));



		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=0;
		c.insets.bottom = 15;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(inseriscicolpi, c);
		this.add(inseriscicolpi);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx=0;
		c.gridy=1;
		c.insets.bottom = 0;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannelloradio, c);
		this.add(pannelloradio);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(interi, c);
		this.add(interi);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellointeri, c);
		this.add(pannellointeri);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(decimali, c);
		this.add(decimali);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellodecimali, c);
		this.add(pannellodecimali);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=6;
		c.insets.bottom = 0;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellom, c);
		this.add(pannellom);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=7;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[0], c);
		this.add(descrizioneinfo[0]);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=8;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(scrollnote, c);
		this.add(scrollnote);
		
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=9;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[1], c);
		this.add(descrizioneinfo[1]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=10;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(tipogara, c);
		this.add(tipogara);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=11;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[2], c);
		this.add(descrizioneinfo[2]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=12;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(luogogara, c);
		this.add(luogogara);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=13;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[3], c);
		this.add(descrizioneinfo[3]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=14;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(data, c);
		this.add(data);
		
		
		// Aggiungo i tasti
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=15;
		c.insets.bottom = 3;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(submit, c);
		this.add(submit);	
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=16;
		c.insets.bottom = 15;
		c.insets.top = 15;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(submit, c);
		this.add(errore);
		
	}


	// EFFECTS: Presa una stringa in input viene parsata a seconda dei / e creato il relativo
	private int[] stringToArray(String str){
		int i;
		int[] result;
		String[] s = str.split("/");
		   if(s.length!=3) return null;
		   else {
			   result = new int[s.length];
			   for(i=0;i<3;i++) {
				   result[i]=Integer.parseInt(s[i]);
			   }
			   return result;
		   }
	}
	
	
	public PannelloInserimentoValori(PannelloMenu p){
		colpi = null;
		padre = p;
		
		this.setPreferredSize(new Dimension(230,700));
		this.setMinimumSize(new Dimension(230,700));
		this.setMaximumSize(new Dimension(230,700));
		//pannellochiamante = pannellomenu;
		// Inizializzo i radiobutton, aggiungo che possa essere selezionata con ALT+G/A, la actionCommand predefinita prende il valore del label
		// setto come valore iniziale allenamento = true
		
		inseriscicolpi = new JButton("Inserisci colpi");
		inseriscicolpi.addActionListener(this);
		
		pannelloradio = new JPanel(new SpringLayout());
		gara = new JRadioButton("Gara");
		allenamento = new JRadioButton("Allenamento");
		gara.setMnemonic(KeyEvent.VK_G);
		allenamento.setMnemonic(KeyEvent.VK_A);
		allenamento.setSelected(true);
		
		pannelloradio.add(gara);
		pannelloradio.add(allenamento);
		
		SpringUtilities.makeCompactGrid(pannelloradio, 
				1, 2, //rows, cols
                3, 3,        //initX, initY, distanza dall'angono in alto a sinistra della casella dedicata al pannello
                3, 3);       //xPad, yPad 
		
		// Raggruppo i radioButton, in un buttongroup, e aggiungo gli ActionListener di tipo RadioButtonListener
		ButtonGroup group = new ButtonGroup();
        group.add(gara);
        group.add(allenamento);  
        gara.addActionListener(new RadioButtonListener());
        allenamento.addActionListener(new RadioButtonListener());

		
		
        // PARTE INTERA: Inizializzo le 5 text area per raccogliere i punti interi
        interi = new JCheckBox("Interi");
		interi.addItemListener(new JCheckBoxListenerI());
		interi.setSelected(false);
		
		pannellointeri = new JPanel(new SpringLayout());
		pannellointeri.setPreferredSize(new Dimension(200,130));
	    pannellointeri.setMinimumSize(new Dimension(200,130));
	    pannellointeri.setMaximumSize(new Dimension(200,130));
		String[] labelsinteri = {"1^ serie intera: ", "2^ serie intera: ", "3^ serie intera: ", "4^ serie intera: ", "Totale intero: "};
		int numPairs = labelsinteri.length;
		campiinteri = new JTextField[5];
		for (int i = 0; i < numPairs; i++) {
	         JLabel l = new JLabel(labelsinteri[i], JLabel.TRAILING);
	         pannellointeri.add(l);
	         campiinteri[i] = new JTextField();
	         //campiinteri[i].setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
	         campiinteri[i].setPreferredSize(new Dimension(60,20));
	         campiinteri[i].setDocument(new JTextFieldLimit(3));
	         campiinteri[i].addKeyListener(new KeyAdapterT());
	         l.setLabelFor(campiinteri[i]);
	         pannellointeri.add(campiinteri[i]);
	     }
		
		
        // Lay out the panel.
        SpringUtilities.makeCompactGrid(pannellointeri, 
        								numPairs, 2, //rows, cols
                                        3, 3,        //initX, initY, distanza dall'angono in alto a sinistra della casella dedicata al pannello
                                        3, 3);       //xPad, yPad 


		decimali = new JCheckBox("Decimali");
		decimali.addItemListener(new JCheckBoxListenerD());
		decimali.setSelected(false);

		
		
		// Inizializzo le 5 text area per raccogliere i punti decimali
        pannellodecimali = new JPanel(new SpringLayout());
        pannellodecimali.setPreferredSize(new Dimension(200,130));
        pannellodecimali.setMinimumSize(new Dimension(200,130));
        pannellodecimali.setMaximumSize(new Dimension(200,130));
		String[] labelsdecimali = {"1^ serie decimale: ", "2^ serie decimale: ", "3^ serie decimale: ", "4^ serie decimale: ", "Totale decimale: "};
		int numPairsd = labelsdecimali.length;
		campidecimali = new JTextField[5];
		for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labelsdecimali[i], JLabel.TRAILING);
            pannellodecimali.add(l);
            campidecimali[i] = new JTextField();
            //campidecimali[i].setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            campidecimali[i].setPreferredSize(new Dimension(60,20));
            campidecimali[i].setDocument(new JTextFieldLimit(5));
            campidecimali[i].addKeyListener(new KeyAdapterT());
            l.setLabelFor(campidecimali[i]);
            pannellodecimali.add(campidecimali[i]);
        }
 
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(pannellodecimali,
                                        numPairs, 2, //rows, cols
                                        3, 3,        //initX, initY
                                        3, 3);       //xPad, yPad
        
        // Inserisco il campo per inserire le mouches
        pannellom = new JPanel(new SpringLayout());

        JLabel mouches = new JLabel("Numero mouches: ");
        numeromouches = new JTextField();
        numeromouches.addKeyListener(new KeyAdapterT());
        numeromouches.setDocument(new JTextFieldLimit(3));
        numeromouches.setPreferredSize(new Dimension(60,20));
        pannellom.add(mouches);
        pannellom.add(numeromouches);
        
        //Lay out the panel.
        SpringUtilities.makeCompactGrid(pannellom,
                                        1, 2, //rows, cols
                                        2, 8,        //initX, initY
                                        2, 8);       //xPad, yPad
        
		// Posso inserire informazioni sulla gara. Prima inizializzo i JLabel che descivono quale e dove scrivere l'info
        String[] labelsinfo = {"Annotazioni: ","Tipo gara: ", "Luogo gara: ", "Data: "};
        descrizioneinfo = new JLabel[4];
        for( int i = 0; i<4; i++){
        	descrizioneinfo[i] = new JLabel(labelsinfo[i]);
        }
		
        note = new JTextArea();
        note.addKeyListener(new KeyAdapterT());
		note.setLineWrap(true);
		note.setWrapStyleWord(true);
		note.setDocument(new JTextFieldLimit(322));
        scrollnote = new JScrollPane(note,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED );
        scrollnote.setPreferredSize(new Dimension(200,30));
        scrollnote.setMaximumSize(new Dimension(200,30));
        scrollnote.setMinimumSize(new Dimension(200,30));
		tipogara = new JTextField();
		tipogara.setDocument(new JTextFieldLimit(20)); 
		luogogara = new JTextField();
		luogogara.setDocument(new JTextFieldLimit(20));
		//Adesso inizializzo la parte per ricevere la data
		data = new JTextField();
		data.setDocument(new JTextFieldLimit(10));
		//data.addMouseListener(this);
		data.addKeyListener(new KeyAdapterT());
		data.setText("20/02/2000");
		
		
		// Inizializzo il bottone di submit, ci aggiungo anche l'ActionListener per gestire l'evento
		submit = new JButton("Inserisci");
		submit.addActionListener(this);
		
		

		
		// Setto il layout del pannello passato come parametro al costruttore, che conterra' le TextArea e Bottoni

		
		//this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		//this.setLayout(new GridBagLayout());

		// Creo il JLabel che mostrera' le informazioni sugli errori verificati.
		errore = new JLabel(convertToMultiline(setWordWrap("E' necessario inserire la data",30)));



		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(gridbag);
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=0;
		c.insets.bottom = 15;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(inseriscicolpi, c);
		this.add(inseriscicolpi);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.WEST;
		c.gridx=0;
		c.gridy=1;
		c.insets.bottom = 0;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannelloradio, c);
		this.add(pannelloradio);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(interi, c);
		this.add(interi);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=3;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellointeri, c);
		this.add(pannellointeri);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=4;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(decimali, c);
		this.add(decimali);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridx=0;
		c.gridy=5;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellodecimali, c);
		this.add(pannellodecimali);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=6;
		c.insets.bottom = 0;
		c.insets.top = 0;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(pannellom, c);
		this.add(pannellom);
		
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=7;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[0], c);
		this.add(descrizioneinfo[0]);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=8;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(scrollnote, c);
		this.add(scrollnote);
		
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=9;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[1], c);
		this.add(descrizioneinfo[1]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=10;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(tipogara, c);
		this.add(tipogara);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=11;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[2], c);
		this.add(descrizioneinfo[2]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=12;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(luogogara, c);
		this.add(luogogara);
		
		c.fill = GridBagConstraints.NONE;
		c.gridx=0;
		c.gridy=13;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(descrizioneinfo[3], c);
		this.add(descrizioneinfo[3]);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=14;
		c.insets.bottom = 0;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(data, c);
		this.add(data);
		
		
		// Aggiungo i tasti
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=15;
		c.insets.bottom = 3;
		c.insets.top = 3;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(submit, c);
		this.add(submit);	
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.CENTER;
		c.gridx=0;
		c.gridy=16;
		c.insets.bottom = 15;
		c.insets.top = 15;
		c.insets.left = 0;
		c.insets.right = 0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(submit, c);
		this.add(errore);
		
	}


	
	private boolean controllaFormatoData(String input){
		Pattern pattern = Pattern.compile("(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012]|[1-12])[/](19|20)\\d\\d");
	    Matcher matcher = pattern.matcher(input);
	    if(matcher.matches()) return true;
	    else return false;
		} 
	
	public static String convertToMultiline(String orig)	{
	    return "<html>" + orig.replaceAll("\n", "<br>") + "<br><html>";
	}
	
	public static String convertToLine(String orig)	{
	    return orig.replaceAll("\n", " ");
	}
	
	private Risultato salvaValori(int gara){
		//System.out.println("SALVA VALORI Created GUI on EDT?" + SwingUtilities.isEventDispatchThread());
		
		Risultato oggettorisultato = null;
		int puntii[] = new int[5];
		BigDecimal puntid[] = new BigDecimal[5]; 
		int j=0;
		// Adesso preparo il GregorianCalendar contenente la data da passare al costruttore del risultato, usando la funzione ausiliaria
		if(data.getText().equals("")){
			errore.setText(convertToMultiline(setWordWrap("Data assente. Inserire una data gg/mm/aaaa.",30)));
//			System.out.println("errore data");
			return null;
		}
		String s = data.getText();
		if(controllaFormatoData(s)!=true) {
			errore.setText(convertToMultiline(setWordWrap("Formato data errato. Inserire una data gg/mm/aaaa.",30)));
			//System.out.println("errore data");
			return null;
		}
		int[] r = stringToArray(data.getText());
		
		// SOLO INTERI

		if(i == 1 && d == 0){
			// Creo un oggetto con soli interi, perche' non sono stati inseriti i decimali
			//NumberFormatException - if the string does not contain a parsable integer.
			try {
				for(j=0;j<5;j++){
					puntii[j] = Integer.parseInt(campiinteri[j].getText());
				}
			}
			catch (NumberFormatException e){
				// Se cattura una NumberFormatException significa che sono stati inseriti valori non validi, devo ripetere
				// l'operazione oppure non faccio niente, quindi restituisco un null.
				errore.setText(convertToMultiline(setWordWrap("Errore nei dati.",30)));
				return null;
				
			}
		
			try {
				int contatore;
				if(modificavalori == 0 ) {
					padre.contatore++;
					contatore = padre.contatore;
				} else {
					contatore = modificavalori;
				}
				// Se non ho mouches inserite
				if(numeromouches.getText().equals("")){
//					System.out.println("NON HO MOUCHES!!!!!!");
					// Se e' una gara e ho inserito TIPOGARA, LUOGOGARA e ANNOTAZIONI
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato = new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,null);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato = new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,null);
				
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText(convertToMultiline(setWordWrap("Manca nome o luogo",30)));
//						System.out.println("maca data o luogo");
						return null;
					}
					// Se non e' una gara ma ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
//						System.out.println(notef);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],r[2],r[1],r[0],gara,null,null,notef,null);
					}
					// Se non e' una gara e non ho altro
					else if (gara==0) oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],r[2],r[1],r[0],gara,null,null,null,null);
				}
				// Se ho mouches inserite
				else {
					int nm = Integer.parseInt(numeromouches.getText());
//					System.out.println("mouches "+nm);
					
					// Se e' una gara e ho inserito TIPOGARA, LUOGOGARA e ANNOTAZIONI
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato = new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,nm,null);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato = new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,nm,null);
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText(convertToMultiline(setWordWrap("Manca nome o luogo",30)));
//						System.out.println("maca data o luogo");
						return null;
					}
					// Se non e' una gara ma ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],r[2],r[1],r[0],gara,null,null,notef,nm,null);
					}

					else if (gara==0) oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],r[2],r[1],r[0],gara,null,null,null,nm,null);
				
				}
			}
			catch (RisultatoErratoException e){
				errore.setText(convertToMultiline(setWordWrap(e.getMessage(),30)));
//				System.out.println("aaaaaaaaaa");
				return null;
				
			}
			catch(NumberFormatException e){
				errore.setText(convertToMultiline(setWordWrap("Errore nei dati.",30)));
//				System.out.println("Errore nei dati");
				this.validate();
				return null;
			}
//			System.out.println("giusto");
			return oggettorisultato;
		}
		
		/* HO ANCHE I DECIMALI: Se ho spuntato la casella dei decimali. Quindi devo prendere dalle caselle di testo i valori delle serie
		*  intere e di quelle decimali e salvarlo nei due array dichiarati poco sopra, di interi e di BigDecimal */
		if (i==1 && d==1) {
			try {
				for(j=0;j<5;j++) {
					puntii[j] = Integer.parseInt(campiinteri[j].getText());
					puntid[j] = new BigDecimal(campidecimali[j].getText());
				}
			}
			catch (NumberFormatException e){
				// Se cattura una NumberFormatException significa che sono stati inseriti valori non validi, devo ripetere
				// l'operazione oppure non faccio niente, quindi restituisco un null.
				errore.setText(convertToMultiline(setWordWrap("Errore nei dati.",30)));
				return null;
			}
			
			try {
//				System.out.println("Non ho avuto errore di NumberoFormatException");
				int contatore;
				if(modificavalori == 0 ) {
					padre.contatore++;
					contatore = padre.contatore;
				} else {
					contatore = modificavalori;
				}
				
				if(numeromouches.getText().equals("")){
//					System.out.println("NON HO MOUCHES!!!!!!");
					// Se e' una gara e ho inserito TIPOGARA, LUOGOGARA e ANNOTAZIONI
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,colpi);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,colpi);
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText(convertToMultiline(setWordWrap("Manca nome o luogo.",30)));
						return null;
					}
					
					// Se non e' una gara e ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,null,null,notef,colpi);
					}
					else if(gara==0) oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
							puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
							r[2],r[1],r[0],gara,null,null,null,colpi);
				}
				else {
					int nm = Integer.parseInt(numeromouches.getText());
//					System.out.println("mouches "+nm);
					
					
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,nm,colpi);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,nm,colpi);
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText(convertToMultiline(setWordWrap("Manca nome o luogo.",30)));
						return null;
					}
					
					// Se non e' una gara e ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
								puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,null,null,notef,nm,colpi);
					}
					else if(gara==0) oggettorisultato= new Risultato(contatore,puntii[4],puntii[0],puntii[1],puntii[2],puntii[3],
							puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
							r[2],r[1],r[0],gara,null,null,null,nm,colpi);
					
				}
			}
			catch (RisultatoErratoException e){
				errore.setText(convertToMultiline(setWordWrap(e.getMessage(),30)));
				return null;
			}
			return oggettorisultato;
		}
		
		
		// HO SOLO GLI INTERI: Se non ho spuntato la casella dei decimali.
		else if (i==0 && d==1){
			try {
				for(j=0;j<5;j++) {
					puntid[j] = new BigDecimal(campidecimali[j].getText());
				}
			}
			catch (NumberFormatException e){
				// Se cattura una NumberFormatException significa che sono stati inseriti valori non validi, devo ripetere
				// l'operazione oppure non faccio niente, quindi restituisco un null.
				errore.setText(convertToMultiline(setWordWrap("Errore nei dati.",30)));
				System.out.println("errore data");
				return null;
			}
			
			try {
//				System.out.println("Non ho avuto errore di NumberoFormatException");
				int contatore;
				if(modificavalori == 0 ) {
					padre.contatore++;
					contatore = padre.contatore;
				} else {
					contatore = modificavalori;
				}
				
				if(numeromouches.getText().equals("")){
				
					// Se e' una gara e ho inserito TIPOGARA, LUOGOGARA e ANNOTAZIONI
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,colpi);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,colpi);
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText("Manca Nome e/o Luogo.");
						return null;
					}
					
					// Se non e' una gara e ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,null,null,notef,colpi);
					}
					else if(gara==0) oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
							r[2],r[1],r[0],gara,null,null,null,colpi);
				}
				else {
					int nm = Integer.parseInt(numeromouches.getText());
//					System.out.println("mouches "+nm);
					
					// Se e' una gara e ho inserito TIPOGARA, LUOGOGARA e ANNOTAZIONI
					if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),notef,nm,colpi);
					}
					// Se e' una gara e ho inserito TIPOGARA e LUOGOGARA
					else if(gara==1 && !tipogara.getText().equals("") && !luogogara.getText().equals("") && note.getText().equals("")){
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,tipogara.getText(),luogogara.getText(),null,nm,colpi);
					}
					
					else if(gara==1 && (tipogara.getText().equals("") || luogogara.getText().equals("") )){
						errore.setText("Manca Nome e/o Luogo.");
						return null;
					}
					
					// Se non e' una gara e ho ANNOTAZIONI
					else if(gara==0 && !note.getText().equals("")){
						String notef = setWordWrap(convertToLine(note.getText()),90);
						//String notef = convertToMultiline(s1);
						oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
								r[2],r[1],r[0],gara,null,null,notef,nm,colpi);
					}
					else if(gara==0) oggettorisultato= new Risultato(contatore,puntid[4],puntid[0],puntid[1],puntid[2],puntid[3],
							r[2],r[1],r[0],gara,null,null,null,nm,colpi);
				}
			}
			catch (RisultatoErratoException e){
				errore.setText(convertToMultiline(setWordWrap(e.getMessage(),30)));
				return null;
			}
			return oggettorisultato;
		}
		// NON HO INTERI E NE DECIMALI
		else {
			//public Risultato(int idunico,int year,int month,int day,int g, String n,String l, String annotazioni) throws RisultatoErratoException {
//				System.out.println("Non ho ne interi ne decimali");
			try {
//				System.out.println("Non ho avuto errore di NumberoFormatException");
				int contatore;
				if(modificavalori == 0 ) {
					padre.contatore++;
					contatore = padre.contatore;
				} else {
					contatore = modificavalori;
				}
				
			
				if(!note.getText().equals("")){
//					System.out.println("HO NOTE");
					String notef = setWordWrap(convertToLine(note.getText()),90);
					//String notef = convertToMultiline(s1);
//					System.out.println(notef);
					oggettorisultato= new Risultato(contatore,r[2],r[1],r[0],0,null,null,notef);
				}
				else oggettorisultato= new Risultato(contatore,r[2],r[1],r[0],0,null,null,null);
			}
			catch (RisultatoErratoException e){
					errore.setText("Errore nei dati.");
				
				return null;
			}
			return oggettorisultato;
		}
	}
}


public class InserimentoValori {

	public static void main(String[] args) {

		JFrame f = new JFrame();
	
		// Non e' piu testabile singolarmente perche' ho un riferimento nel pannellomenu
		Container c = f.getContentPane();
		//PannelloInserimentoValori p = new PannelloInserimentoValori(f);

		//c.add(p);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		

		

	}
}