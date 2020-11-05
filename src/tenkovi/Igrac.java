package tenkovi;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {

	public Igrac(Polje p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crtaj() {
		Graphics g=polje.getGraphics();
		g.setColor(Color.RED);
		g.drawLine(polje.getWidth()/2, 0, polje.getWidth()/2, polje.getHeight());
		g.drawLine(0, polje.getHeight()/2, polje.getWidth(), polje.getHeight()/2);
	}

}
