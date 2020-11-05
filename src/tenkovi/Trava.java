package tenkovi;

import java.awt.Color;
import java.awt.Graphics;

public class Trava extends Polje {
	  public Trava(Mreza m) {
		  super(m);
	  }
      @Override
      public boolean dozvoljeno(Figura f) {return true;}
      public void paint(Graphics g) {
    	  g.setColor(Color.GREEN);
    	  g.fillRect(0, 0, getWidth(), getHeight());
      }
}
