package tenkovi;

import java.awt.Color;
import java.awt.Graphics;

public class Zid extends Polje {
    public Zid(Mreza m) {
    	super(m);
    }
    public boolean dozvoljeno(Figura f) {return false;}
    public void paint(Graphics g) {
    	g.setColor(Color.LIGHT_GRAY);
    	g.fillRect(0, 0, getWidth(), getHeight());
    }
}
