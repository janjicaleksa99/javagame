package igra;

import java.awt.Color;
import java.awt.Graphics;

public class Tenk extends Figura implements Runnable {
	private Color color = Color.BLACK;
	private Thread thread;
	private int ms = 500;
	private boolean start = false;
	private boolean end = false;

	public Tenk(Polje p) {
		super(p);
		IscrtajFiguru();
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!start)
						wait();
				}
				Thread.sleep(ms);
				int smer = ((int) (Math.random() * 4));
				while (true) {
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
								if (this.polje.getMreza().ProveriPreklop(this) == false)
									OfarbajPolje(polje);
								else OfarbajUNovcic(polje);
								polje = temp;
							}
							else temp = null;
						}
						if (temp == null) {
							while (smer == 0)
								smer = ((int) (Math.random() * 4));
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
								if (this.polje.getMreza().ProveriPreklop(this) == false)
									OfarbajPolje(polje);
								else OfarbajUNovcic(polje);
								polje = temp;
							}
							else temp = null;
						}
						if (temp == null) {
							while (smer == 1)
								smer = ((int) (Math.random() * 4));
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
								if (this.polje.getMreza().ProveriPreklop(this) == false)
									OfarbajPolje(polje);
								else OfarbajUNovcic(polje);
								polje = temp;
							}
							else temp = null;
						}
						if (temp == null) {
							while (smer == 2)
								smer = ((int) (Math.random() * 4));
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
								if (this.polje.getMreza().ProveriPreklop(this) == false)
									OfarbajPolje(polje);
								else OfarbajUNovcic(polje);
								polje = temp;
							}
							else temp = null;
						}
						if (temp == null) {
							while (smer == 3)
								smer = ((int) (Math.random() * 4));
						}
						break;
				}
					if (temp != null)
						break;
					continue;
				}
				IscrtajFiguru();
			}
		}
		catch (InterruptedException e) {
			
		}
		end = true;
	}
	
	public synchronized void PokreniNit() {
		if (thread != null) {
			start = true;
			end = false;
			notify();
		}
	}
	
	public synchronized void StvoriNit() {
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void ZaustaviNit() {
		if (thread != null) {
			thread.interrupt();
			/*try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/
			end = true;
			start = false;
		}
		thread = null;
	}
	
	public void OfarbajUNovcic(Polje polje) {
		Graphics g = polje.getGraphics();
		g.setColor(polje.getColor());
		g.fillRect(0, 0, polje.getWidth(), polje.getHeight());
		int w = polje.getWidth();
		int h = polje.getHeight();
 		g.setColor(Color.YELLOW);
		g.translate(w / 2, h / 2); //postavi u centar
		g.translate(-w / 4, -h / 4); // transliraj za poluprecnik po obe ose
		g.fillOval(0, 0, w / 2, h / 2); // iscrtaj elipsu
	}
	
	@Override
	public void IscrtajFiguru() {
		Graphics g = polje.getGraphics();
		g.setColor(color);
		int w = polje.getWidth();
		int h = polje.getHeight();
		g.drawLine(0, 0, w, h);
		g.drawLine(w, 0, 0, h);
	}
}
