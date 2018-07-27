package shoot;

import java.io.Serializable;
import java.math.BigDecimal;

/* Modella il colpo sul bersaglio, ha delle coordinate x, y relative al bersaglio e dei valori interi e decimali. 
 */
class Colpo implements Serializable{
	
	private static final long serialVersionUID = -3734556547234647729L;
	int cx;
	int cy;
	int d = 54;
	int intero;
	int decimale;
	BigDecimal puntodecimale;

	
//	COSTRUTTURE 1
	public Colpo(int x, int y){
		cx = x; cy = y;
		puntodecimale = new BigDecimal(0.0);
		intero = 0; decimale = 0;
	}
	
//	COSTRUTTORE 2
	public Colpo(int x, int y, double c, int i, int d){
		cx = x; cy = y;
		puntodecimale = new BigDecimal(c);
		intero = i; decimale = d;
	}
	
//	modifico i valori delle x e delle y del colpo
	public void set(int x, int y){
		cx = x; cy = y;
	}
	
//	modifico i valori di intero e decimale
	public void setP(int i, int d){
		intero = i; decimale = d;
	}


}