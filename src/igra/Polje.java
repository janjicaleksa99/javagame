package igra;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public abstract class Polje extends Canvas {
	protected Mreza mreza;
	
	public Polje(Mreza mreza) {
		super();
		this.mreza = mreza;
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (mreza.getIgra().isRezim() == false)
					mreza.IzbaciPolje(Polje.this);
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				List<Igrac> listI = mreza.getListI();
				if (listI.size() == 0)
					return;
				char key = Character.toUpperCase(e.getKeyChar());
				Igrac igrac = listI.get(0);
				switch(key) {
					case KeyEvent.VK_A:
						igrac.PomeriSe(0);
						break;
					case KeyEvent.VK_D:
						igrac.PomeriSe(1);
						break;
					case KeyEvent.VK_W:
						igrac.PomeriSe(2);
						break;
					case KeyEvent.VK_S:
						igrac.PomeriSe(3);
						break;
				}
			}
		});
		setSize(30, 30);
	}
	
	public abstract boolean DozvoljenaFigura(Figura f);
	
	public abstract Color getColor();
	
	public Polje DohvPoljeSaPom(int i, int j) {
		return mreza.DohvPoljeSaPom(this, i, j);
	}
	
	public Mreza getMreza() {
		return mreza;
	}
	
	public int[] pozPolja() {
		return mreza.PozicijaPolja(this);	
	}
}
