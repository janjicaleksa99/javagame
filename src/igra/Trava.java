package igra;

import java.awt.Color;

@SuppressWarnings("serial")
public class Trava extends Polje {
	Color color = Color.GREEN;
	
	public Trava(Mreza mreza) {
		super(mreza);
		this.setBackground(color);
	}

	@Override
	public boolean DozvoljenaFigura(Figura f) {
		return true;
	}
	
	@Override
	public Color getColor() {
		return color;
	}
}
