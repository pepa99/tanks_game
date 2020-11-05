package tenkovi;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class Igra extends Frame {
     private Mreza mreza;
     private Menu rezim=new Menu("Rezim");
     private MenuItem izmena=new MenuItem("Rezim izmena");
     private MenuItem igranje=new MenuItem("Rezim igranje");
     private Button pocni=new Button("Pocni");
     private Panel p1=new Panel();
     private TextField novcici=new TextField();
     public Igra() {
    	 super("Igra");
    	 dodaj();
    	 setSize(600,600);
    	 addWindowListener(new WindowAdapter () {public void windowClosing(WindowEvent e) {mreza.zaustavi();dispose();}});
    	 setLocation(400,300);
    	 setVisible(true);
     }
     private void dodaj() {
    	 mreza=new Mreza(this);
    	 add(mreza);
    	 MenuBar traka=new MenuBar();
    	 rezim.add(izmena); rezim.add(igranje);
    	 izmena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					if(mreza.igranje && mreza.pocni) mreza.zaustavi();
					mreza.igranje=false;
					mreza.izmena=true;
					pocni.setEnabled(false);	
			} 
    	 });
    	 igranje.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent e) {
 				if(mreza.igranje)
 					novaigra();
 					mreza.igranje=true;
 					mreza.izmena=false;
 					pocni.setEnabled(true);	
 			} 
     	 });
    	 traka.add(rezim);
    	 setMenuBar(traka);
    	// p1.setLayout(new GridLayout(1,4,10,10));
    	 p1.add(new Label("Novcici")); p1.add(novcici); p1.add(mreza.poenil); p1.add(pocni);
    	 pocni.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mreza.inicijalizuj(Integer.parseInt(novcici.getText()));
				mreza.pocni=true;
			}
    		 
    	 });
    	 add(p1,"South");
     }
     private void novaigra() {
    	 p1.remove(mreza.poenil);
    	 mreza.zaustavi();
    	 synchronized(mreza) {remove(mreza);}
    	 mreza=new Mreza(this);
    	 add(mreza);
    	 p1.add(mreza.poenil,2);
    	 revalidate();
     }
     public static void main(String[] args) {
    	 new Igra();
     }
}
