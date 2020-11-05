package tenkovi;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
    public Tenk(Polje p) {
		super(p);
		// TODO Auto-generated constructor stub
	}
	private Thread nit=new Thread(this);
	public void pokreni() {nit.start();}
	public void zaustavi() {nit.interrupt();}
	@Override
	public void run() {
		while(!Thread.interrupted()) {
		 synchronized(this) {
			 Polje preth=polje;
			int i=(int) (Math.random()*4);
			boolean izadji=false;
			while(!izadji) {
			if(i==0) {
				Polje p=polje.pomereno(-1, 0);
				if(p!=null && p.dozvoljeno(this)) {
					polje=p;
					crtaj();
					izadji=true;
				} 
			}
			if(i==1) {
				Polje p=polje.pomereno(1, 0);
				if(p!=null && p.dozvoljeno(this)) {
					polje=p;
					crtaj();
					izadji=true;
				}
			}
			if(i==2) {
				Polje p=polje.pomereno(0, -1);
				if(p!=null && p.dozvoljeno(this)) {
					polje=p;
					crtaj();
					izadji=true;
				}
			}
			if(i==3) {
				Polje p=polje.pomereno(0, 1);
				if(p!=null && p.dozvoljeno(this)) {
					polje=p;
					crtaj();
					izadji=true;
				}
			}
			i=(int) (Math.random()*4);
			}
			preth.repaint();
		 }
		 try {
		 Thread.sleep(500);} catch(InterruptedException e) {zaustavi();}
		}
	}

	@Override
	public synchronized void crtaj() {
		Graphics g=polje.getGraphics();
		g.setColor(Color.BLACK);
		g.drawLine(0, 0, polje.getWidth(), polje.getHeight());
		g.drawLine(0, polje.getHeight(), polje.getWidth(), 0);
	}

}
