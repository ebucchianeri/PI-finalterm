package shoot;

import javax.swing.*;
import java.util.ArrayList;
import java.util.*;

/* Classe che permette di visualizzare l'elenco dei PannelloRisultato, dei valori Risultato inseriti ne viene
 * creato un PannelloRisultato ed esso viene salvato in un arrayList di PannelloElenco 
 */
public class PannelloElenco extends JPanel{
	
	ArrayList<PannelloRisultato> pannelli = new ArrayList<PannelloRisultato>();
	ArrayList<Risultato> r = null;
	Risultato[] rr = new Risultato[4];
	int ordine; // 0 dal piu' vecchio al piu' recente, 1 al contrario
	int crescente;
	JFrame padre;
	
	public ArrayList<Risultato> getElencoRisultati(){
		return r;
	}
	
	
	/* Funzione per selezionare tutti i pannelli inseriti, indipendentemente che siano gare o allenamenti */
	public void selezionaTutto(){
		for (PannelloRisultato pr : pannelli){
			pr.selezionato = 1;
			
			
		}
		this.repaint();
	}
	
	/* Funzione per deselezionare tutti i pannelli selezionati */
	public void deselezionaTutto(){
		for (PannelloRisultato pr : pannelli){
			pr.selezionato = 0;
			
		}
		this.repaint();
	}
	
	/* Funzione per selezionare tutti i pannelli di risultati che sono gare */
	public void selezionaGare(){
		for (PannelloRisultato pr : pannelli){
			if(pr.r.gara==1) {
				pr.selezionato = 1;
			}
			
		}
		this.repaint();
	}
	
	/* Funzione per selezionare tutti i pannelli di risultati che sono allenamenti */
	public void selezionaAllenamenti(){
		for (PannelloRisultato pr : pannelli){
			if(pr.r.gara==0) {
				pr.selezionato = 1;
			}
			
		}
		this.repaint();
	}
	
	
		
//	Funzione che restituisce il numero dei pannelli selezionati
	public int numeroSelezionati(){
		
		int i = 0;
		 for(PannelloRisultato pr : pannelli){
			 if(pr.selezionato == 1){
				 i++;
			 }
		 }
		 return i;
	}
	
	/* EFFECTS: restituisce un arraylist di risultati per fare le statistiche. Quindi una copia di r, 
	 * dove i risultati sono ordinati per data 
	 */
	
	public ArrayList<Risultato> creaArrayStat(){
		 ArrayList<Risultato> ar = new ArrayList<Risultato>(); // Arraylist di supporto, ordino prelevando da questo
		 ArrayList<Risultato> arord = new ArrayList<Risultato>(); // Arraylist finale, con i risultati ordinati

		 // Ser r non e' vuoto
		 if(r!=null || r.size()!=0){
			 
			 // Per ogni pannello selezionato, inserisco nell'array di supporto ar il corrispettivo risultato
			 for(PannelloRisultato pr : pannelli){
				 if(pr.selezionato == 1){
					 ar.add(pr.r);
				 }
			 }
			 //Adesso devo ordinare ar in arord.
			 int l = ar.size();
			 try{
					for(int t=0; t<l; t++){
						Risultato vecchio = getOlder(ar);
						arord.add(vecchio);
					}
			 }
			 catch(EmptyException e){
//				 HO AVUTO UNA ECCEZIONE MENTRE ORDINAVO L'ARRAY!!!
//				 System.out.println("Errore nell'ordinamento");
				 arord = null;
			 }
			 return arord;
		 }
		 else return null;
	}
	
	
	/* MODIFIES: Modifica l'ordinamento dei risultati in r e dei pannellorisultato in pannelli
	 * EFFECTS: Ordina in modo crescente o decrescente i risultati, ordinando di conseguenza anche pannelli 
	 */
	public void riordinaVR (){
		if(r!=null){
			this.removeAll();
			ArrayList<Risultato> newr = new ArrayList<Risultato>();
			ArrayList<PannelloRisultato> newp = new ArrayList<PannelloRisultato>();
			int l = r.size();
			
			try{
				for(int t =0; t<l; t++){
					Risultato vecchio = getOlder(r);
					newr.add(vecchio);
				}
			}
			catch( EmptyException e){
//				HO AVUTO UNA ECCEZIONE MENTRE ORDINAVO L'ARRAY!!!
//				System.out.println("Errore nell'ordinamento dei pannelli ");
			}
			
			if (ordine == 0) {
				ordine = 1;
				for(Risultato e : newr) {
					PannelloRisultato p = new PannelloRisultato(e,this);
					newp.add(p);
					this.add(p);
				}
			}
			else {
				l = newr.size();
				ordine = 0;
				for(int t=l-1; t>=0; t--){
					Risultato e = newr.get(t);
					PannelloRisultato p = new PannelloRisultato(e,this);
					newp.add(p);
					this.add(p);
				}
			}
		
			pannelli = newp;
			r = newr;
			padre.validate();
		}
	}
	
	
//	Funzione per stampare le informazioni principali di un risultato
	public void stampaInfo(ArrayList<Risultato> risu){
		for( Risultato e : risu){
			System.out.println("Id: "+e.id);
			if (e.intero == 1) System.out.println("totint: "+e.punti);
			if(e.decimale == 1)  System.out.println("totint: "+e.puntidecimali);
		}
	}
	
	
	
	private Risultato getOlder(ArrayList<Risultato> ris){
			if(ris!=null){
				
				int day = ris.get(0).data.get(GregorianCalendar.DATE);
				int month = ris.get(0).data.get(GregorianCalendar.MONTH);
				int year = ris.get(0).data.get(GregorianCalendar.YEAR);
//				System.out.println("Seleziono  d:" + day + " m:"+month+ " a:"+year);
				Risultato v = ris.get(0);
				Iterator<Risultato> g = ris.iterator();
				int i=0;
				while(g.hasNext()){
					Risultato current = g.next();
					int dday = current.data.get(GregorianCalendar.DATE);
					int mmonth = current.data.get(GregorianCalendar.MONTH);
					int yyear = current.data.get(GregorianCalendar.YEAR);
					//System.out.println("Selezionato  d:" + day + " m:"+month+ " a:"+year);
					//System.out.println("d:" + dday + " m:"+mmonth+ " a:"+yyear);
					if(yyear<year) {
//						System.out.println("il corrente e' minoe del selezionato");
							day = current.data.get(GregorianCalendar.DATE);
							month = current.data.get(GregorianCalendar.MONTH);
							year = current.data.get(GregorianCalendar.YEAR);
							v = current;
						}
					else if(yyear == year){
						if(mmonth<month) {
							day = current.data.get(GregorianCalendar.DATE);
							month = current.data.get(GregorianCalendar.MONTH);
							year = current.data.get(GregorianCalendar.YEAR);
							v = current;
							
						}
						else if (month==mmonth && day>dday) {
							day = current.data.get(GregorianCalendar.DATE);
							month = current.data.get(GregorianCalendar.MONTH);
							year = current.data.get(GregorianCalendar.YEAR);
							v = current;
						
						}
					}
				}
				ris.remove(v);
				//System.out.println("scelto d:" + v.data.get(GregorianCalendar.DATE) + " m:"+v.data.get(GregorianCalendar.MONTH)+ " a:"+v.data.get(GregorianCalendar.YEAR));
				return v;
			}
			else throw new EmptyException("RISULTATO VUOTO!");
			
		}
	
	
	/* EFFECTS: restituisce l'indice, o meglio la posizione, nell'arraylist di risultati di un risultato con id passato come parametro 
	 * o -1 se non esiste tale id
	 */
	public int getIndice(int id){
		int indice = 0;
		for( Risultato element : r){
			if(element.id == id){
//				System.out.println("trovato");
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	/* EFFECTS: restituisce l'indice, o meglio la posizione, nell'arraylist di pannellorisultato di un pannellorisultato
	 *  con id passato come parametro o -1 se non esiste tale id
	 */
	public int getIndicePannello(int id){
		int indice = 0;
		for( PannelloRisultato element : pannelli){
			if(element.r.id == id){
//				System.out.println("trovato");
				return indice;
			}
			indice++;
		}
		return -1;
	}
	
	/* MODIFIES: r, pannelli, this
	 * EFFECTS: aggiunge all' arraylist di risultato un nuovo elemento passato come parametro, aggiunge poi il pannellorisultato
	 * da tale risultato al pannelloelenco e a pannelli, l'insersione e' in coda per tutti. 
	 */
	public void addRisultato(Risultato ri){
		PannelloRisultato pa = new PannelloRisultato(ri,this);
		this.add(pa);
		pannelli.add(pa);
		r.add(ri);
		padre.validate();
//		System.out.println("aggiorno correttamente??");	
	}
	
//	Funzione che modifica il risultato nell'array usato per memorizzare i risultati inseriti
	public int setRisultato(int id, Risultato ris){
		int fine = 0;
//		System.out.println("Sistemo il risultato");
		
		fine = getIndice(id);
		if(fine>=0){
			r.set(fine, ris);
		}
		return fine;
	}
	
	/* MODIFICHE: r, this, pannelli
	 * EFFECTS: Modifica un elemento dell'arraylist di risultato, quello di indice id, inserendoci il nuovo risultato passato come
	 * parametro. Dopo viene aggiornato anche il vettore di arraylist di pannellorisultato. Il pannellorisultato visualizzato viene
	 * rimosso e quello nuovo viene ridisegnato per ultimo.
	 * - l'ordine in pannelli coincide con quelli aggiunti al pannelloelenco
	 */
	public void setRisultatoPerId(Risultato nuovo ,int id){
		int t = getIndice(id);
		if(t>=0){
			r.set(t, nuovo);
		}
	  	// adesso devo aggiornare i pannelli.
	  
		t = getIndicePannello(id);
		this.remove(pannelli.get(t));
		pannelli.remove(pannelli.get(t));
		PannelloRisultato pnew = new PannelloRisultato(nuovo,this);
		pannelli.add(pnew);
		
	  	
		this.add(pnew);
	  	this.repaint();
	  	padre.validate();
	}
	
	/* MODIFICHE: this, r, pannelli
	 * EFFECTS: preso come parametro un arraylist di risultato (tipo letto da file). Vengono quindi rimossi tutti i pannelli inseriti
	 * e le variabili r e pannelli vengono ri-inizializzati
	 */
	public void setInsiemeRisultato(ArrayList<Risultato> ris){
//		System.out.println("Sistemo l'array e i pannelli ");
		
		r = ris;
		int j = 0;
		this.removeAll();
		pannelli = new ArrayList<PannelloRisultato>();
		for( Risultato t : r ) {
			pannelli.add(new PannelloRisultato(t,this));
			this.add(pannelli.get(j));
			j++;
		}
		padre.validate();
	}
	
	/* MODIFICHE: this, r, pannelli
	 * EFFECTS: funzione che scorre tutti gli elementi di pannelli, controlla quali sono quelli selezionati e li rimuove dal pannelloelenco, 
	 * da pannelli e da r.
	 */
	public int cancellaSelezionati(){
		int n = 0;
		Iterator<PannelloRisultato> iter = pannelli.iterator();
		while(iter.hasNext()) {
		  PannelloRisultato b = iter.next();
		  
		  if(b.selezionato == 1) {
//			System.out.println("Elimino"+b.r.punti);
			n++;
			this.remove(b);
			iter.remove(); // Removes the 'current' item
			r.remove(b.r);
		  }
//		  Li chiamo entrambi???
		  padre.validate();
		  padre.repaint();
		}
		return n;
	}
	
	
	/* EFFECTS: restituisce l'id del primo elemento, o pannellorisultato, che nella ricerca lineare di pannelli e' trovato selezionato
	 * altrimenti, se nessuno e' selezionato restituisce un valore negativo
	 */
	public int getPrimoSelezionato(){
		Iterator<PannelloRisultato> iter = pannelli.iterator();
		while(iter.hasNext()) {
		  PannelloRisultato b = iter.next();
		  
		  if(b.selezionato == 1) {
			return b.r.id;
		  }
		}
		return -1;
	}
	
	
	
	public PannelloElenco(JFrame s){
		super();
		padre = s;
		ordine = 0;
		crescente = 0;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		r = new ArrayList<Risultato>();
		pannelli = new ArrayList<PannelloRisultato>();
		
		
		/*
		try{
		//public Risultato(int idunico,int p,int s1,int s2,int s3,int s4,int year,int month,int day, int g, String n,String l, String annotazioni) throws RisultatoErratoException 
		rr[0] = new Risultato(0,380,95,95,95,95,2000,8,02,0,null,null,null);
		rr[1] = new Risultato(1,400,100,100,100,100,2000,8,02,1,"Prima federale","Pontedera",null);
		rr[2] = new Risultato(2,385,96,96,96,97,new BigDecimal(404.30),new BigDecimal(100.4),new BigDecimal(101.4),new BigDecimal(101.1),new BigDecimal(101.4),2014,04,05,0,null,null,null,null);
		rr[3] = new Risultato(3,400,100,100,100,100,2000,8,02,0,null,"Pontedera","cihsu wehuweh ewhuihweu whuiewh");
		}
		catch (Exception e){
			System.out.println("eccezione"+e.getMessage());
		}
		
		for(int i=0;i<4;i++){
			r.add(rr[i]);
		}
		*/

	}
	
}