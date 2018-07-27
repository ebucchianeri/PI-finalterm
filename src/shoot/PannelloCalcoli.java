package shoot;


import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.*;



// TEST DA FARE: testare con solo interi, solo decimali, niente!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//Devo aggiungere statistiche sulle varie serie

public class PannelloCalcoli extends JPanel {
	ArrayList<Risultato> r;
	ArrayList<Risultato> ri;
	ArrayList<Risultato> rd;
	Risultato rmini;
	Risultato rmaxi;
	Risultato rmind;
	Risultato rmaxd;
	BigDecimal maxsd;
	BigDecimal minsd;
	//BigDecimal mediad;
	//BigDecimal mediaseried;
	int maxsi;
	int minsi;
	double mediai;
	double mediaseriei;
	
	/* Pannello calcoli prende direttamente l'arrayList di risultati che riceve dal programma, quindi prima di fare le statistiche
	* controllare che esistano risultati con interi e/o decimali
	*
	*/
	public PannelloCalcoli(ArrayList<Risultato> ris){
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		r=ris;

		ri = new ArrayList<Risultato>();
		rd = new ArrayList<Risultato>();
		controllaArray();
		// Devo controllare che ci siano anche dei decimali. Li riceve solo ordinati, ci possono essere
		// anche dei risultati non significativi, devo controllare!!
		// Devo controllare anche sugli interi.
		
		/*
		 for(Risultato e : ri){
		 
			System.out.println("intero"+e.punti+" "+e.serie[0]+" "+e.serie[1]+" "+e.serie[2]+" "+e.serie[3]) ;
		}
		
		for(Risultato e : rd){
			System.out.println("decimale"+e.puntidecimali.doubleValue()+" "+e.seriedecimali[0].doubleValue()+" "+e.seriedecimali[1].doubleValue()+" "+e.seriedecimali[2].doubleValue()+" "+e.seriedecimali[3].doubleValue()) ;
		}
		*/
		
		
//		Le medie le calcolo solo sugli interi.
		rmini = minInt();
		rmaxi = maxInt();
		rmind = minDec();
		rmaxd = maxDec();
		maxsd = maxSerieDec();
		minsd = minSerieDec();
		maxsi = maxSerieInt();
		minsi = minSerieInt();
		//mediad = mediaDec();
		mediai = mediaInt();
		mediaseriei = mediaSerieInt();
		//mediaseried = mediaSerieDec();

		
		// PARTE DEGLI INTERI
		if(rmaxi!=null && rmini!=null){
			JLabel ti = new JLabel("Statistiche su interi");
			ti.setForeground(Color.RED);
			this.add(ti);
			

			JLabel pi1 = new JLabel("Max intero: " + rmaxi.punti);
			JLabel pi2 = new JLabel("Min intero: " + rmini.punti);
			JLabel pi3 = new JLabel("Max serie intera: " + maxsi);
			JLabel pi4 = new JLabel("Min serie intera: " + minsi);
			this.add(pi1);
			this.add(pi2);
			this.add(pi3);
			this.add(pi4);
			JLabel tm = new JLabel("Medie interi: ");
			tm.setForeground(Color.RED);
			this.add(tm);
			JLabel pi5 = new JLabel("Media intera: " + arrotondamento(mediai));
			JLabel pi6 = new JLabel("Media serie intere: " + arrotondamento(mediaseriei));
			this.add(pi5);
			this.add(pi6);

		}
		// PARTE DEI DECIMALI
		if(rmaxd!=null && rmind!=null){
			JLabel td = new JLabel("Statistiche sui decimali");
			td.setForeground(Color.RED);
			this.add(td);
			JLabel di1 = new JLabel("Max decimale: " + rmaxd.puntidecimali.doubleValue());
			JLabel di2 = new JLabel("Min decimale: " + rmind.puntidecimali.doubleValue());
			JLabel di3 = new JLabel("Max serie decimale: " + maxsd.doubleValue());
			JLabel di4 = new JLabel("Min serie decimale: " + minsd.doubleValue());
			this.add(di1);
			this.add(di2);
			this.add(di3);
			this.add(di4);
		
		}
	}
	
	
	public static double arrotondamento(double x){
		x = Math.floor(x*100);
		x = x/100;
		return x;
		}
	
	private void controllaArray(){
		
		for( Risultato e : r){
			if(e.intero == 1 && e.decimale == 1){
				ri.add(e);
				rd.add(e);
			}
			else if(e.intero == 1){
				ri.add(e);
			}
			else if(e.decimale == 1){
				rd.add(e);
			}
		}
		
	}
	
	private Risultato minInt(){
		Risultato rm = null;
		int m = 400;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				if(e.punti < m){
					rm = e;
					m = e.punti;
				}
			}
		}
		return rm;
		
	}
	
	private Risultato  maxInt(){
		Risultato rm = null;
		int m = 0;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				if(e.punti > m){
					rm = e;
					m = e.punti;
				}
			}
		}
		return rm;
		
	}
	
	
	private Risultato  minDec(){
		Risultato rm = null;
		BigDecimal m = new BigDecimal(436.0);
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				if(e.puntidecimali.compareTo(m)<0){
					rm = e;
					m = e.puntidecimali;
				}
			}
		}
		return rm;
		
	}

	private Risultato maxDec(){
		Risultato rm = null;
		BigDecimal m = new BigDecimal(0.0);
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				if(e.puntidecimali.compareTo(m)>0){
					rm = e;
					m = e.puntidecimali;
				}
			}
		}
		return rm;
		
	}
	
	
	private int minSerieInt(){
		int ms = 200;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				// Se esiste un punteggio intero, allora controllo quale e' la serie max
				for(int i = 0; i<4; i++){
					if(e.serie[i]<ms){
						ms = e.serie[i];
					}
				}
			}
		}
		return ms;
		
	}
	
	private int maxSerieInt(){
		int ms = 0;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				// Se esiste un punteggio intero, allora controllo quale e' la serie max
				for(int i = 0; i<4; i++){
					if(e.serie[i]>ms){
						ms = e.serie[i];
					}
				}
			}
		}
		return ms;
		
	}
	
	
	private BigDecimal minSerieDec(){
		BigDecimal ms = new BigDecimal(200.0);
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				// Se esiste un punteggio decimale, allora controllo quale e' la serie max
				// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				for(int i = 0; i<4; i++){
					if(e.seriedecimali[i].compareTo(ms)<0){
						ms = e.seriedecimali[i];
					}
				}
			}
		}
		return ms;
		
	}
	
	private BigDecimal maxSerieDec(){
		BigDecimal ms = new BigDecimal(0.0);
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				// Se esiste un punteggio decimale, allora controllo quale e' la serie max
				// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				for(int i = 0; i<4; i++){
					if(e.seriedecimali[i].compareTo(ms)>0){
						ms = e.seriedecimali[i];
					}
				}
			}
		}
		return ms;
		
	}
	
	
	
	private BigDecimal mediaDec(){
		BigDecimal ms = new BigDecimal(0.0);
		int i = 0;
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				ms = ms.add(e.puntidecimali);
				i++;
			}
		}
		if(i!=0) return ms.divide(new BigDecimal((double)i));
		else return new BigDecimal(0.0);
	}
	
	
	private double mediaInt(){
		double m = 0;
		int i = 0;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				m = m + e.punti;
				i++;
			}
		}
		
		if(i!=0) return m/(double)i;
		else return 0;
	}
	
	
	
	private BigDecimal mediaSerieDec(){
		BigDecimal ms = new BigDecimal(0.0);
		int n = 1;
		
		for(Risultato e : rd){
			if(e.decimale == 1){
				// Se esiste un punteggio decimale, allora controllo quale e' la serie max
				// -1, 0, or 1 as this BigDecimal is numerically less than, equal to, or greater than val.
				for(int i = 0; i<4; i++){
					ms = ms.add(e.seriedecimali[i]);
					n++;
				}
			}
		}
		// lancia ArithmeticException - if the exact quotient does not have a terminating decimal expansion
		if(n!=0) return ms.divide(new BigDecimal(n));
		else return new BigDecimal(n);
	}
	
	private double mediaSerieInt(){
		double ms = 0;
		int n = 0;
		
		for(Risultato e : ri){
			if(e.intero == 1){
				// Se esiste un punteggio intero, allora controllo quale e' la serie max
				for(int i = 0; i<4; i++){
					ms = ms + e.serie[i];
					n++;
				}
			}
		}
		if(n!=0) return ms/ (double)n;
		else return n;
		
	}

}
