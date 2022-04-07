package igra;

import java.awt.Color;

@SuppressWarnings("serial")
public class Zid extends Polje {
	Color color = Color.LIGHT_GRAY;
	
	public Zid(Mreza mreza) {
		super(mreza);
		this.setBackground(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean DozvoljenaFigura(Figura f) {
		return false;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
}
