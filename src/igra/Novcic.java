package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {
	Color color = Color.YELLOW;
	
	public Novcic(Polje p) {
		super(p);
		IscrtajFiguru();
	}

	@Override
	public void IscrtajFiguru() {
		int w = polje.getWidth();
		int h = polje.getHeight();
		Graphics g = polje.getGraphics();
 		g.setColor(color);
		g.translate(w / 2, h / 2); //postavi u centar
		g.translate(-w / 4, -h / 4); // transliraj za poluprecnik po obe ose
		g.fillOval(0, 0, w / 2, h / 2); // iscrtaj elipsu
		
	}

}
