package tenkovi;

import java.awt.Canvas;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class Polje extends Canvas {
     private Mreza mreza;
     public Polje(Mreza m) {
    	 setSize(20,20);
    	 mreza=m;
    	 addKeyListener(new KeyAdapter() {
 			public void keyPressed(KeyEvent e) {
 				
 				if(mreza.igrac!=null) {
 					Polje po=mreza.igrac.nalazise();
 				if(e.getKeyCode()==KeyEvent.VK_A) {
 					Polje p=mreza.igrac.nalazise().pomereno(0, -1);
 					if(p!=null && p.dozvoljeno(mreza.igrac)) {
 						mreza.igrac.pomeri(p);
 					}
 				}
 				if(e.getKeyCode()==KeyEvent.VK_D) {
 					Polje p=mreza.igrac.nalazise().pomereno(0, 1);
 					if(p!=null && p.dozvoljeno(mreza.igrac)) {
 						mreza.igrac.pomeri(p);
 					}
 				}
 				if(e.getKeyCode()==KeyEvent.VK_W) {
 					Polje p=mreza.igrac.nalazise().pomereno(-1, 0);
 					if(p!=null && p.dozvoljeno(mreza.igrac)) {
 						mreza.igrac.pomeri(p);
 					}
 				}
 				if(e.getKeyCode()==KeyEvent.VK_S) {
 					Polje p=mreza.igrac.nalazise().pomereno(1, 0);
 					if(p!=null && p.dozvoljeno(mreza.igrac)) {
 						mreza.igrac.pomeri(p);
 					}
 				}
 				po.repaint();
 				}
 			}
 		});
    	 Polje p=this;
    	 addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent d) {
					mreza.dodajoslukivac(p);
				}
			});
     }
     public Mreza mreza() {return mreza;}
     public int[] pozicija() {
    	int[] niz;
    	niz=new int[2];
    	niz[0]=-1;
    	niz[1]=-1;
    	for(int i=0; i<mreza.matrica().length; i++) {
    		for(int j=0;j<mreza.matrica()[0].length;j++)
    		if(mreza.matrica()[i][j]==this) {
    			niz[0]=i;
    			niz[1]=j;
    		}
    	}
    	return niz;
     }
     public Polje pomereno(int i1, int i2) {
    	for(int i=0; i<mreza.matrica().length; i++) 
    		for(int j=0;j<mreza.matrica()[0].length;j++) {
    			if(i==pozicija()[0]+i1 && j==pozicija()[1]+i2)
    				return mreza.matrica()[i][j];
    		}
    	return null;
     }
     public abstract boolean dozvoljeno(Figura f);
}
