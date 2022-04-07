package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Igrac extends Figura {
	Color color = Color.RED;
	
	public Igrac(Polje p) {
		super(p);
		IscrtajFiguru();
	}
	
	public void PomeriSe(int smer) {
		int poz[];
		int i , j;
		Polje temp = null;
		switch (smer) {
			case 0:
				poz = polje.pozPolja();
				i = poz[0];
				j = poz[1];
				temp = polje.DohvPoljeSaPom(0, - 1);
				if (temp != null) {
					boolean flag = temp.DozvoljenaFigura(this);
					if (flag == true) {
						OfarbajPolje(polje);
						polje = temp;
						IscrtajFiguru();
					}
					else temp = null;
				}
				break;
			case 1:
				poz = polje.pozPolja();
				i = poz[0];
				j = poz[1];
				temp = polje.DohvPoljeSaPom(0, 1);
				if (temp != null) {
					boolean flag = temp.DozvoljenaFigura(this);
					if (flag == true) {
						OfarbajPolje(polje);
						polje = temp;
						IscrtajFiguru();
					}
					else temp = null;
				}
				break;
			case 2:
				poz = polje.pozPolja();
				i = poz[0];
				j = poz[1];
				temp = polje.DohvPoljeSaPom(- 1, 0);
				if (temp != null) {
					boolean flag = temp.DozvoljenaFigura(this);
					if (flag == true) {
						OfarbajPolje(polje);
						polje = temp;
						IscrtajFiguru();
					}
					else temp = null;
				}
				break;
			case 3:
				poz = polje.pozPolja();
				i = poz[0];
				j = poz[1];
				temp = polje.DohvPoljeSaPom(1, 0);
				if (temp != null) {
					boolean flag = temp.DozvoljenaFigura(this);
					if (flag == true) {
						OfarbajPolje(polje);
						polje = temp;
						IscrtajFiguru();
					}
					else temp = null;
				}
				break;
		}
	}
	
	@Override
	public void IscrtajFiguru() {
		Graphics g = polje.getGraphics();
		g.setColor(color);
		g.drawLine(polje.getWidth() / 2, 0, polje.getWidth() / 2, polje.getHeight());
		g.drawLine(0, polje.getHeight() / 2, polje.getWidth(), polje.getHeight() / 2);
	}
	
}
