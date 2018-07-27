package shoot;

import java.math.BigDecimal; 
import java.util.*;
import java.io.*;

/* Risultato */

class Risultato extends Object implements Serializable {
	
	private static final long serialVersionUID = -3736556547234648709L;
	int id; // intero identificativo del Risultato
	int gara; // 0 allenamento, 1 gara
	int colpoxcolpo; // 0 se non ho l'array, 1 se ho l'array
	int decimale; // 0 no decimali, 1 si decimali
	int intero; // 0 no interi, 1 si interi
	int punti; // Valore intero del risultato (intero deve essere 1)
	int[] serie; // Le 4 serie intere della prestazione (intero deve essere 1)
	boolean mouches;
	int nmouches;
	ArrayList<Colpo> colpi; // Array che memorizza colpo per colpo
	BigDecimal puntidecimali; // Valore decimale del risultato (decimale deve essere ad 1) sono gia' in BigDecimal
	BigDecimal[] seriedecimali; // Le 4 serie decimali del risultato (decimale deve essere ad 1)
	GregorianCalendar data; 	// Memorizzo la data -- GregorianCalendar(int year, int month, int dayOfMonth) 
	String luogo = null; // Memorizzo il luogo di dove e' stato ottenuto il risultato (nome sara' significativo solo in caso di gara ad 1)
	String nome = null; // Memorizzo il nome della gara (nome sara' significativo solo in caso di gara ad 1)
	String note = null; // Memorizzo le annotazioni (sempre facoltative).
	
	
	
	
	
	
	
	/* COSTRUTTORE 1
	* Ho sia INTERI che DECIMALI, ho la stringa del nome, del luogo e delle annotazioni,le mouches e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/	
	
	public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,BigDecimal pd,BigDecimal sd1,BigDecimal sd2,BigDecimal sd3,BigDecimal sd4,int year,int month,int day,int g, String n,String l, String annotazioni, int m, ArrayList<Colpo> a) throws RisultatoErratoException {
		if(s1<0 || s2<0 || s3<0 || s4 <0 || p < 0 || sd1.compareTo(new BigDecimal(0))<0 || sd2.compareTo(new BigDecimal(0))<0 || sd3.compareTo(new BigDecimal(0))<0 || sd4.compareTo(new BigDecimal(0))<0 || pd.compareTo(new BigDecimal(0))<0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id = idunico; intero = 1; decimale = 1; punti = p; gara=g;	serie = new int[4]; serie[0]=s1;serie[1]=s2;serie[2]=s3;serie[3]=s4;
		// Inizializzo le informazioni decimali:
		puntidecimali = new BigDecimal(pd.toString());
		seriedecimali = new BigDecimal[4];
		seriedecimali[0]=new BigDecimal(sd1.toString());
		seriedecimali[1]=new BigDecimal(sd2.toString());
		seriedecimali[2]=new BigDecimal(sd3.toString());
		seriedecimali[3]=new BigDecimal(sd4.toString());
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null;
		luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		note = annotazioni;
		colpi = a;
		mouches = true;
		
		if (m>=0 && m<=40) {
			nmouches = m;
		}
		else {
			 throw new RisultatoErratoException("Numero mouches errato");
		}
		// Solo se siamo in una gara memorizzo nome e luogo
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		if(s1+s2+s3+s4!=p) throw new RisultatoErratoException("La somma delle serie intere non coincide col totale.");
		
		BigDecimal somma = this.somma(seriedecimali[0],seriedecimali[1],seriedecimali[2],seriedecimali[3]);
		if(somma.compareTo(puntidecimali)!=0) throw new RisultatoErratoException("La somma delle serie decimali non coincide col totale.");
	}
	
	/* COSTRUTTORE 2: uguale al costruttore 1, senza mouches
	* Ho sia INTERI che DECIMALI, ho la stringa del nome, del luogo e delle annotazioni,e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/	
	
	public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,BigDecimal pd,BigDecimal sd1,BigDecimal sd2,BigDecimal sd3,BigDecimal sd4,int year,int month,int day,int g, String n,String l, String annotazioni, ArrayList<Colpo> a) throws RisultatoErratoException {
		if(s1<0 || s2<0 || s3<0 || s4 <0 || p < 0 || sd1.compareTo(new BigDecimal(0))<0 || sd2.compareTo(new BigDecimal(0))<0 || sd3.compareTo(new BigDecimal(0))<0 || sd4.compareTo(new BigDecimal(0))<0 || pd.compareTo(new BigDecimal(0))<0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id = idunico; intero = 1; decimale = 1; punti = p; gara=g;	serie = new int[4]; serie[0]=s1;serie[1]=s2;serie[2]=s3;serie[3]=s4;
		// Inizializzo le informazioni decimali:
		puntidecimali = new BigDecimal(pd.toString());
		seriedecimali = new BigDecimal[4];
		seriedecimali[0]=new BigDecimal(sd1.toString());
		seriedecimali[1]=new BigDecimal(sd2.toString());
		seriedecimali[2]=new BigDecimal(sd3.toString());
		seriedecimali[3]=new BigDecimal(sd4.toString());
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null;
		luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		note = annotazioni;
		colpi = a;
		mouches = false;
		// Solo se siamo in una gara memorizzo nome e luogo
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		if(s1+s2+s3+s4!=p) throw new RisultatoErratoException("La somma delle serie intere non coincide col totale.");
		
		BigDecimal somma = this.somma(seriedecimali[0],seriedecimali[1],seriedecimali[2],seriedecimali[3]);
		if(somma.compareTo(puntidecimali)!=0) throw new RisultatoErratoException("La somma delle serie decimali non coincide col totale.");
	}
	

	/* COSTRUTTORE 3
	* Ho solo gli INTERI, ho la stringa del nome, del luogo e delle annotazioni,le mouches e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/	
	public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,int year,int month,int day, int g, String n,String l, String annotazioni, int m, ArrayList<Colpo> a) throws RisultatoErratoException {
		if(s1<0 || s2<0 || s3<0 || s4 <0 || p < 0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id=idunico; intero = 1; decimale = 0; punti = p; gara = g; serie = new int[4]; 	serie[0]=s1;serie[1]=s2;serie[2]=s3;serie[3]=s4;
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null; luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		// Solo se siamo in una gara memorizzo nome e luogo e annotazioni
		note = annotazioni;
		colpi = a;
		mouches = true;
		if (m>=0 && m<=40) {
			nmouches = m;
		}
		else {
			 throw new RisultatoErratoException("Numero mouches errato");
		}
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		if(s1+s2+s3+s4!=p) throw new RisultatoErratoException("La somma delle serie intere non coincide col totale.");
	}
	
	/* COSTRUTTORE 4: uguale al costruttore 3 senza mouches
	* Ho solo INTERI, ho la stringa del nome, del luogo e delle annotazioni e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/	
	public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,int year,int month,int day, int g, String n,String l, String annotazioni,ArrayList<Colpo> a) throws RisultatoErratoException {
		if(s1<0 || s2<0 || s3<0 || s4 <0 || p < 0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id=idunico; intero = 1; decimale = 0; punti = p; gara = g; serie = new int[4]; 	serie[0]=s1;serie[1]=s2;serie[2]=s3;serie[3]=s4;
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null; luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		// Solo se siamo in una gara memorizzo nome e luogo e annotazioni
		note = annotazioni;
		colpi = a;
		mouches = false;
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		if(s1+s2+s3+s4!=p) throw new RisultatoErratoException("La somma delle serie intere non coincide col totale.");
	}
	
	/* COSTRUTTORE 5
	* Ho solo i DECIMAlI, ho la stringa del nome, del luogo e delle annotazioni,le mouches e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/
	public Risultato(int idunico,BigDecimal pd,BigDecimal sd1,BigDecimal sd2,BigDecimal sd3,BigDecimal sd4,int year,int month,int day,int g, String n,String l, String annotazioni,int m, ArrayList<Colpo> a) throws RisultatoErratoException {
		if(sd1.compareTo(new BigDecimal(0))<0 || sd2.compareTo(new BigDecimal(0))<0 || sd3.compareTo(new BigDecimal(0))<0 || sd4.compareTo(new BigDecimal(0))<0 || pd.compareTo(new BigDecimal(0))<0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id = idunico; intero = 0; decimale = 1; gara=g;	
		// Inizializzo le informazioni decimali:s
		puntidecimali = new BigDecimal(pd.toString());
		seriedecimali = new BigDecimal[4];
		seriedecimali[0]=new BigDecimal(sd1.toString());
		seriedecimali[1]=new BigDecimal(sd2.toString());
		seriedecimali[2]=new BigDecimal(sd3.toString());
		seriedecimali[3]=new BigDecimal(sd4.toString());
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null;
		luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		note = annotazioni;
		colpi = a;
		mouches = true;
		if (m>=0 && m<=40) {
			nmouches = m;
		}
		else {
			 throw new RisultatoErratoException("Numero mouches errato");
		}
		// Solo se siamo in una gara memorizzo nome e luogo
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		
		BigDecimal somma = this.somma(seriedecimali[0],seriedecimali[1],seriedecimali[2],seriedecimali[3]);
		if(somma.compareTo(puntidecimali)!=0) throw new RisultatoErratoException("La somma delle serie decimali non coincide col totale.");
	}
	
	/* COSTRUTTORE 6: uguale al costruttore 5 senza le mouches
	* Ho solo gli INTERI, ho la stringa del nome, del luogo e delle annotazioni e l'arrayList
	* Lancio una checked exception RisultatoErratoException se si hanno uno o piu' valori negativi,
	* se le somme delle serie non coincidono con i totali e se, in una gara, non aggiungo le
	* informazioni di luogo e nome.
	*/
	public Risultato(int idunico,BigDecimal pd,BigDecimal sd1,BigDecimal sd2,BigDecimal sd3,BigDecimal sd4,int year,int month,int day,int g, String n,String l, String annotazioni, ArrayList<Colpo> a) throws RisultatoErratoException {
		if(sd1.compareTo(new BigDecimal(0))<0 || sd2.compareTo(new BigDecimal(0))<0 || sd3.compareTo(new BigDecimal(0))<0 || sd4.compareTo(new BigDecimal(0))<0 || pd.compareTo(new BigDecimal(0))<0){
			 throw new RisultatoErratoException("Valori errati");
		}
		// Inizializzo i primi valori del risultato e i dati interi:
		id = idunico; intero = 0; decimale = 1; gara=g;	
		// Inizializzo le informazioni decimali:s
		puntidecimali = new BigDecimal(pd.toString());
		seriedecimali = new BigDecimal[4];
		seriedecimali[0]=new BigDecimal(sd1.toString());
		seriedecimali[1]=new BigDecimal(sd2.toString());
		seriedecimali[2]=new BigDecimal(sd3.toString());
		seriedecimali[3]=new BigDecimal(sd4.toString());
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null;
		luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		note = annotazioni;
		colpi = a;
		mouches = false;
		// Solo se siamo in una gara memorizzo nome e luogo
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
		
		// Faccio i controlli.
		// Se siamo in una gara devo avere entrambe le informazioni nome e luogo.
		else if (g==1 && ((l==null && n!=null) || (l!=null && n==null) || (l==null && n==null))) throw new RisultatoErratoException("Il risultato e' di una gara. Serve nome e luogo.");
		
		
		BigDecimal somma = this.somma(seriedecimali[0],seriedecimali[1],seriedecimali[2],seriedecimali[3]);
		if(somma.compareTo(puntidecimali)!=0) throw new RisultatoErratoException("La somma delle serie decimali non coincide col totale.");
	}
	
	
	/* COSTRUTTORE 7
	* Costruttore vuoto.
	*/
	public Risultato(int idunico,int year,int month,int day,int g, String n,String l, String annotazioni) throws RisultatoErratoException {
		// Non ho informazioni sui punti
		id = idunico; intero = 0; decimale = 0; gara=g;	
		// Inizializzo le ultime informazioni, data, luogo, nome e note.
		nome = null;
		luogo = null;
		data = new GregorianCalendar(year,month-1,day);
		note = null;
		colpi = null;
		mouches = false;
		note = annotazioni;
		// Solo se siamo in una gara memorizzo nome e luogo
		if(g==1 && l!=null && n!=null){
			luogo = l;
			nome = n;
		}
	}
	
	
	
	/* EFFECTS: prende in ingresso 4 BigDecimal e restituisce un oggetto BigDecimal che contiene la loro somma 
	 */
	private BigDecimal somma(BigDecimal num1,BigDecimal num2,BigDecimal num3,BigDecimal num4) {
		   BigDecimal tot = new BigDecimal(0);
		   tot=num1.add(num2);
		   tot=tot.add(num3);
		   tot=tot.add(num4);
		   return tot;
	}
	
	
	
	/* EFFECTS: restituisce una copia dell'oggetto, implemento il metodo clone.
	 * Clono oggetti Risultato che abbiamo almeno gli interi.
	 */
	public Risultato clone(){
		Risultato r = null;
		if(intero==1 && decimale==0){
			// L'elemento da duplicare e' solo di interi.
				try {
					r= new Risultato(id,punti,serie[0],serie[1],serie[2],serie[3],data.get(GregorianCalendar.YEAR),data.get(GregorianCalendar.MONTH)+1,data.get(GregorianCalendar.DATE),gara,nome,luogo,note,null);
				}
				catch (RisultatoErratoException e){
					r=null;
				}
		}
		if(intero==1 && decimale==1){
			// L'elemento da duplicare e' solo di interi.
				try {
					r= new Risultato(id,punti,serie[0],serie[1],serie[2],serie[3],puntidecimali,seriedecimali[0],seriedecimali[1],seriedecimali[2],seriedecimali[3],data.get(GregorianCalendar.YEAR),data.get(GregorianCalendar.MONTH)+1,data.get(GregorianCalendar.DATE),gara,nome,luogo,note,null);
				}
				catch (RisultatoErratoException e){
					r=null;
				}
		}
		if(r!=null) return r;
		else return null;
	}

	
	
	public String toString(){
		// Se ho solo INTERI
		if(intero==1 && decimale==0){
			// Se ho solo iNTERI e sono in un ALLENAMENTO e ho le NOTE
			if(gara==0){
				if(note!=null) return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+"\n"+note);
				// Se ho solo iNTERI e sono in un ALLENAMENTO e non ho le note
				else  return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR));
			}
			// Se ho solo INTERI ma sono in una GARA, quindi devo stampare anche nome e luogo e le note.
			else {
				if(note!=null) return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] + 
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
						" Nomegara:"+nome+" Luogo:"+luogo +"\n"+note);
				else if(note==null) return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
						" Nomegara:"+nome+" Luogo:"+luogo);
			}
		}
			// Se ho anche i DECIMALI
		else if (intero==1 && decimale==1){
			// Se ho anche i DECIMALI e sono in un ALLENAMENTO
			if(gara==0) {
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO eho le NOTE
				if(note!=null)return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] + "\n\t" + "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+"\n"+note);
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO e non ho le note
				else return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] + "\n\t" + "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR));
				
			}
			else {
				if(note!=null) return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] + "\n\t" + "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
					+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
					" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
					" Nomegara:"+nome+" Luogo:"+luogo +"\n"+note);
				else return ("ID:"+id+" RIS INTERO tot:" + punti + " serie:" + serie[0] +" "+ serie[1] +" "+ serie[2] +" "+ serie[3] + "\n\t" + "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
						" Nomegara:"+nome+" Luogo:"+luogo);
			
			}
		}
		
		else if (intero==0 && decimale==1){
			// Se ho anche i DECIMALI e sono in un ALLENAMENTO
			if(gara==0) {
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO eho le NOTE
				if(note!=null)return ("ID:"+id+ "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+"\n"+note);
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO e non ho le note
				else return ("ID:"+id+ "RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR));
				
			}
			else {
				if(note!=null) return ("ID:"+id+"RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
					+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
					" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
					" Nomegara:"+nome+" Luogo:"+luogo +"\n"+note);
				else return ("ID:"+id+"RIS DECIMALE tot:" + puntidecimali.doubleValue() + " serie:" + seriedecimali[0].doubleValue()
						+" "+ seriedecimali[1].doubleValue() +" "+ seriedecimali[2].doubleValue() +" "+ seriedecimali[3].doubleValue() +
						" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
						" Nomegara:"+nome+" Luogo:"+luogo);
			
			}
		}
		else {
			
			if(gara==0) {
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO eho le NOTE
				if(note!=null)return ("ID:"+id+" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+"\n"+note);
				// Se ho anche i DECIMALI e sono in un ALLENAMENTO e non ho le note
				else return ("ID:"+id+" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR));
				
			}
			else {
			if(note!=null) return ("ID:"+id+" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
					" Nomegara:"+nome+" Luogo:"+luogo +"\n"+note);
			else return ("ID:"+id+" DATA: " + data.get(GregorianCalendar.DATE) +"/"+ data.get(GregorianCalendar.MONTH)+"/"+data.get(GregorianCalendar.YEAR)+
					" Nomegara:"+nome+" Luogo:"+luogo);
			}
		}
		return null;
		
	}

}



class EmptyException extends RuntimeException{
    public EmptyException(String s){
	super(s);
    }
}

class ValoriPresentiException extends IOException{
    public ValoriPresentiException(String s){
	super(s);
    }
}

class RisultatoErratoException extends IOException{
    public RisultatoErratoException(String s){
	super(s);
    }
}