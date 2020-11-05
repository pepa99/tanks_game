package tenkovi;
import java.awt.*;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mreza extends Panel implements Runnable {
    private Thread nit=new Thread(this);
    private Polje[][] matrica;
    private int m;
    private int n;
    private Panel p1=new Panel();
    private Panel p2=new Panel();
    private int brojnovcica;
    private Panel p3=new Panel();
    boolean pocni=false;
    private Panel p4=new Panel();
    private CheckboxGroup grupa=new CheckboxGroup();
    private Checkbox trava;
    private int poeni=0;
    private Checkbox zid;
    boolean izmena=true;
    boolean igranje=false;
    Label poenil=new Label("Poeni:"+poeni);
    Igrac igrac;
    private Igra igra;
    private static class Elem{
    	Figura f;
    	Elem sledeci;
    	Elem(Figura fig){f=fig;}
    }
    private Elem prvii, posli, prvit, poslt, prvin, posln;
    public Mreza(Igra i, Dimension d) {
    	igra=i;
    	m=d.width;
    	n=d.height;
    	matrica=new Polje[m][n];
    	trava=new Checkbox("Trava",grupa,false);
    	zid=new Checkbox("Zid", grupa, false);
    	p2.setLayout(new GridLayout(1,2));
    	p2.add(new Label("Podloga:"));
    	p3.setLayout(new GridLayout(2,1));
        Panel p4=new Panel(); p4.setBackground(Color.GREEN);
        Panel p5=new Panel(); p5.setBackground(Color.LIGHT_GRAY);
        p4.add(trava); p5.add(zid);
        p3.add(p4); p3.add(p5);
    	p2.add(p3);
    	p1.setLayout(new GridLayout(m,n));
    	for(int i1=0;i1<m;i1++)
    		for(int i2=0; i2<n;i2++) {
    		   double j=Math.random();
               if(j<=0.8) {
                matrica[i1][i2]=new Trava(this);
    			p1.add(matrica[i1][i2]);
    			} else {
    				matrica[i1][i2]=new Zid(this);
    				p1.add(matrica[i1][i2]);
    			}
    		}
    	add(p1);
    	add(p2,"East");
    	nit.start();
    }
    void dodajoslukivac(Polje p) {
    	if(izmena) {
			int j1=0; int j2=0;
			for(int i1=0; i1<m;i1++)
            	for(int i2=0; i2<n;i2++)
            		if(p==matrica[i1][i2]) {
            			j1=i1;
            			j2=i2;
            		}
			p1.remove(matrica[j1][j2]);
			if(trava.getState()) {
				matrica[j1][j2]=new Trava(this);
			}
			if(zid.getState()) {
				matrica[j1][j2]=new Zid(this);
			}
			p1.add(matrica[j1][j2],j1*n+j2);
			revalidate();
			repaint();
			
		}
    }
    public Mreza(Igra i) {
    	this(i,new Dimension(17, 17));
    }
    public synchronized void zaustavi() {
    	nit.interrupt();
    	pocni=false;
    	//igranje=false;
    	Elem tek=prvit;
    	while(tek!=null) {
    		Tenk tenk=(Tenk) tek.f;
    		tenk.zaustavi();
    		tek=tek.sledeci;
    	}
    }
    private synchronized void izbacinovcic(Novcic n) {
    	Elem tek=prvin;
    	Elem preth=null;
    	while(tek!=null) {
    		if(tek.f==n) {
    			break;
    		}
    		preth=tek;
    		tek=tek.sledeci;
    	}
    	Polje po=tek.f.nalazise();
    	if(tek==prvin) {
    		prvin=prvin.sledeci;
    		if(posln==tek) posln=null;
    	} else {
    		if(tek==posln) posln=preth;
    		if(tek!=null)
    		preth.sledeci=tek.sledeci;
    	}
    	po.repaint();
    	if(prvin==null || brojnovcica==0) zaustavi();
    }
    private synchronized void dodaj(Figura f) {
    	if(f instanceof Novcic) {
    		if(prvin==null) {prvin=new Elem(f); posln=prvin;}
    		else {posln.sledeci=new Elem(f); posln=posln.sledeci;}
    	}
    	if(f instanceof Tenk) {
    		if(prvit==null) {prvit=new Elem(f); poslt=prvit;}
    		else {poslt.sledeci=new Elem(f); poslt=poslt.sledeci;}
    	}
    	if(f instanceof Igrac) {
    		prvii=new Elem(f);
    		prvii.sledeci=null;
    	}
    }
    private synchronized void iscrtaj() {
    	Elem tek1=prvii, tek2=prvin, tek3=prvit;
    	while(tek1!=null) {
    		tek1.f.crtaj();
    		tek1=tek1.sledeci;
    	}
    	while(tek2!=null) {
    		tek2.f.crtaj();
    		tek2=tek2.sledeci;
    	}
    	while(tek3!=null) {
    		tek3.f.crtaj();
    		tek3=tek3.sledeci;
    	}
    }
    synchronized void azurirajpolja() {
    	for(int i=0;i<m;i++)
    		for(int j=0;j<n;j++)
    			matrica[i][j].repaint();
    }
    private synchronized boolean slobodno(Polje p) {
    	Elem tek1=prvii, tek2=prvit, tek3=prvin;
    	if(p instanceof Zid) return false;
    	while(tek1!=null) {
    		if(tek1.f.nalazise()==p) return false;
    		tek1=tek1.sledeci;
    	}
    	while(tek2!=null) {
    		if(tek2.f.nalazise()==p) return false;
    		tek2=tek2.sledeci;
    	}
    	while(tek3!=null) {
    		if(tek3.f.nalazise()==p) return false;
    		tek3=tek3.sledeci;
    	}
    	return true;
    }
    public synchronized void inicijalizuj(int i) {
    	if(igranje) {
    		brojnovcica=i;
    		int j=0;
    		while(j<i) {
    		int i1=(int) (Math.random()*m);
    		int i2=(int) (Math.random()*n);
    		if(slobodno(matrica[i1][i2])) {
    			j++;
    			dodaj(new Novcic(matrica[i1][i2]));
    		}
    		}
    		j=0;
    		while(j<i/3) {
        		int i1=(int) (Math.random()*m);
        		int i2=(int) (Math.random()*n);
        		if(slobodno(matrica[i1][i2])) {
        			j++;
        			Tenk t=new Tenk(matrica[i1][i2]);
        			t.pokreni();
        			dodaj(t);
        		}
        		}
    		j=0;
    		while(j<1) {
    			int i1=(int) (Math.random()*m);
        		int i2=(int) (Math.random()*n);
        		if(slobodno(matrica[i1][i2])) {
        			j++;
        			igrac=new Igrac(matrica[i1][i2]);
        			dodaj(igrac);
        		
        		}
    		}
    	}
    }
    public Polje[][] matrica(){return matrica;}
    private synchronized void azuriraj() {
    	Elem tek=prvin;
    	while(tek!=null) {
    		if(tek.f.equals(igrac)) {
    		poeni++;
    		poenil.setText("Poeni:"+poeni);
    		izbacinovcic((Novcic) tek.f);
    		}
    		tek=tek.sledeci;
    	}
    	tek=prvit;
    	while(tek!=null) {
    		if(tek.f.equals(igrac)) {
        		zaustavi();
        		igrac=null;
        		break;
        	}
        	tek=tek.sledeci;
    	}
    }
	@Override
	public void run() {
		while(!Thread.interrupted()) {
			iscrtaj();
			azuriraj();
			try {Thread.sleep(40);}
			catch(InterruptedException e) {zaustavi();}
		}
	}

}
