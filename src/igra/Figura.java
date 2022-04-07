package igra;

import java.awt.Graphics;

public abstract class Figura {
	protected Polje polje;

	public Figura(Polje p) {
		super();
		this.polje = p;
	}
	
	public void PomeriFiguru(Polje p) {
		polje = p;
	}
	
	public abstract void IscrtajFiguru();
	
	public Polje getP() {
		return polje;
	}
	
	public void OfarbajPolje(Polje p) {
		Graphics g = p.getGraphics();
		g.setColor(p.getColor());
		g.fillRect(0, 0, p.getWidth(), p.getHeight());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Figura other = (Figura) obj;
		if (polje == null) {
			if (other.polje != null)
				return false;
		} else if (!polje.equals(other.polje))
			return false;
		return true;
	}
}
