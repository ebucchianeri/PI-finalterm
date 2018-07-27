package shoot;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

public class App {

		public static void main(String[] args) {
			//System.err.println("ciao mondo");
			JFrame f = new JFrame();
			f.setVisible(true);
			f.setResizable(false);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			Container c = f.getContentPane();
			c.setLayout(new BorderLayout());
			Punti p = new Punti(f,1);
			Bersaglio d = new Bersaglio(p);
			d.addMouseListener(d);
			d.addMouseMotionListener(d);
			Menu m = new Menu(d,f);

			
			
			c.add(d,BorderLayout.CENTER);
			c.add(p,BorderLayout.EAST);
			c.add(m,BorderLayout.SOUTH);
			
			/*
			Risultato[] rr = null;
			ObjectInputStream ois;
			try{
				ois = new ObjectInputStream(new FileInputStream("punteggisalvati"));
				rr = (Risultato[]) ois.readObject();
			}
			catch(Exception ex){
				System.out.println("ERROREEEE");
				ex.printStackTrace();
			}
			System.out.println("LETTURA"+rr[0].colpi.size());
			int i;
			for(i=0; i<rr[0].colpi.size(); i++){
				Colpo cc = rr[0].colpi.get(i);
				System.out.println("#"+i+": "+cc.intero+","+cc.decimale);
			}
			*/
			f.pack();
			
			
			
		}

}