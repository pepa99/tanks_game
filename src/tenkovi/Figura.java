package tenkovi;

import java.awt.Graphics;

public abstract class Figura {
     protected Polje polje;
     public Figura(Polje p) {polje=p;}
     public synchronized Polje nalazise() {return polje;}
     public synchronized void pomeri(Polje p) {polje=p;}
     @Override
     public boolean equals(Object o) {
    	 Figura f=(Figura) o;
    	 return (polje==f.polje);
     }
     public abstract void crtaj(); 
}
