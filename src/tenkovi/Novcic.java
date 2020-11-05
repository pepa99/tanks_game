package tenkovi;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crtaj() {
		Graphics g=polje.getGraphics();
		g.setColor(Color.YELLOW);
		g.fillOval(polje.getWidth()/4, polje.getHeight()/4, polje.getWidth()/2, polje.getHeight()/2);
	}

}
