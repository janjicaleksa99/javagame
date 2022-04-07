package igra;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class Mreza extends Panel implements Runnable {
	private int Dim = 17, ms = 40, BrPoena = 0;
	private Polje matPolja[][];
	private Igra igra;
	private List<Igrac> listI = new ArrayList<>();
	private List<Novcic> listN = new ArrayList<>();
	private List<Tenk> listT = new ArrayList<>();
	private boolean start = false;
	private boolean end = false;
	private Thread thread;
	private Label poeni;
	
	
	public Mreza(Igra igra) {
		super(); 
		this.igra = igra;
		matPolja = new Polje[Dim][Dim];
		Inicijalizuj();
		setSize(300, 300);
		thread = new Thread(this);
		thread.start();
	}
	
	public Mreza(int dim, Igra igra) {
		super(); 
		Dim = dim;
		this.igra = igra;
		matPolja = new Polje[Dim][Dim];
		Inicijalizuj();
		thread = new Thread(this);
		thread.start();
	}
	
	public void Inicijalizuj() {
		double UkBrPolja = Dim * Dim;
		double TrBrTrave = 0;
		for (int i = 0; i < Dim; i++)
			for (int j = 0; j < Dim; j++) {
				double randnum = Math.random();
				if (randnum >= 0.2 && (TrBrTrave / UkBrPolja) <= 0.8) {
					matPolja[i][j] = new Trava(this);
					TrBrTrave++;
				}
				else matPolja[i][j] = new Zid(this); 
			}
			while (true) {
				if (((TrBrTrave + 1) / UkBrPolja) <= 0.8) {
					int i = ((int) (Math.random() * Dim));
					int j = ((int) (Math.random() * Dim));
					if (matPolja[i][j] instanceof Trava)
						continue;
					matPolja[i][j] = new Trava(this);
					TrBrTrave++;
				}
				else break;
			}
			setLayout(new GridLayout(Dim, Dim, 0, 0));
			for (int i = 0; i < Dim; i++)
				for (int j = 0; j < Dim; j++) {
					add(matPolja[i][j]);
				}
	}

	@Override
	public void run() {
		Igrac igrac = null;
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					while (!start)
						wait();
				}
				Thread.sleep(ms);
				if (listN.size() == 0) {
					end = true;
					break;
				}
				igrac = listI.get(0);
				for (int i = 0; i < listN.size(); i++)
					if (igrac.getP() == listN.get(i).getP()) { // pogledaj ove  2 for petlje
						listN.remove(i);
						Polje p = igrac.getP();
						Graphics g = p.getGraphics();
						g.setColor(p.getColor());
						g.fillRect(0, 0, p.getWidth(), p.getHeight());
						igrac.IscrtajFiguru();
						++BrPoena;
						poeni.setText("Broj poena: " + BrPoena);
						poeni.revalidate();
						break;
					}
				for (int i = 0; i < listT.size(); i++)
					if (igrac.getP() == listT.get(i).getP()) {
						Polje p = igrac.getP();
						/*Graphics g = p.getGraphics();
						g.setColor(p.getColor());
						g.fillRect(0, 0, p.getWidth(), p.getHeight());*/
						listI.remove(0);
						end = true;
					}
				if (end == true)
					break;
			}
		}
		catch (InterruptedException e) {
			
		}
		end = true;
		for (int i = 0; i < listT.size(); i++)
			listT.get(i).ZaustaviNit();
	}
	
	public synchronized boolean ProveriPreklop(Tenk t) {
		for (int i = 0; i < listN.size(); i++)
			if (t.getP() == listN.get(i).getP())
				return true;
		return false;
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
			BrPoena = 0;
			poeni.setText("Broj poena: " + BrPoena);
			for (int i = 0; i < Dim; i++)
				for (int j = 0; j < Dim; j++) {
					Graphics g = matPolja[i][j].getGraphics();
					g.setColor(matPolja[i][j].getColor());
					g.fillRect(0, 0, matPolja[i][j].getWidth(), matPolja[i][j].getHeight());
				}
			listI.clear();
			listN.clear();
			listT.clear();
		}
		thread = null;
	}
	
	public void InicijalizujMrezu(int BrNovcica) {
		boolean matBusy[][] = new boolean[Dim][Dim];
		int BrTenkova = BrNovcica / 3;
		int i = 0;
		while (true) {
			int x = ((int) (Math.random() * Dim));
			int y = ((int) (Math.random() * Dim));
			if (matBusy[x][y] == false && matPolja[x][y] instanceof Trava) {
				listN.add(new Novcic(matPolja[x][y]));
				matBusy[x][y] = true;
				++i;
			}
			if (i == BrNovcica)
				break;
		}
		i = 0;
		while (true) {
			int x = ((int) (Math.random() * Dim));
			int y = ((int) (Math.random() * Dim));
			if (matBusy[x][y] == false && matPolja[x][y] instanceof Trava) {
				Tenk t = new Tenk(matPolja[x][y]);
				listT.add(t);
				t.StvoriNit();
				t.PokreniNit();
				matBusy[x][y] = true;
				++i;
			}
			if (i == BrTenkova)
				break;
		}
		boolean postavljen = false;
		while (true) {
			int x = ((int) (Math.random() * Dim));
			int y = ((int) (Math.random() * Dim));
			if (matBusy[x][y] == false && matPolja[x][y] instanceof Trava) {
				listI.add(new Igrac(matPolja[x][y]));
				matBusy[x][y] = true;
				postavljen = true;
			}
			if (postavljen == true)
				break;
		}
		PokreniNit();
	}
	
	public void IzbaciPolje(Polje p) {
		for (int i = 0; i < Dim; i++)
			for (int j = 0; j < Dim; j++)
				if (matPolja[i][j] == p) {
					String ret = igra.IzabranoDugme();
					if (ret == "trava") {
						remove(i == 0 ? j : i * (Dim) + j);
						matPolja[i][j] = null;
						matPolja[i][j] = new Trava(this);
						add(matPolja[i][j], i == 0 ? j : i * (Dim) + j);
						revalidate();
					}
					else if (ret == "zid") {
						remove(i == 0 ? j : i * (Dim) + j);
						matPolja[i][j] = null;
						matPolja[i][j] = new Zid(this);
						add(matPolja[i][j], i == 0 ? j : i * (Dim) + j);
						revalidate();
					}
				}
	}
	
	public int[] PozicijaPolja(Polje p) {
		int ret[] = null;
		for (int i = 0; i < Dim; i++)
			for (int j = 0; j < Dim; j++) {
				if (matPolja[i][j] == p) {
					ret = new int[2];
					ret[0] = i;
					ret[1] = j;
				}
			}
		return ret;
	}
	
	public Polje DohvPoljeSaPom(Polje p, int x , int y) {
		for (int i = 0; i < Dim; i++)
			for (int j = 0; j < Dim; j++) {
				if (matPolja[i][j] == p) {
					if ((i + x) >= Dim || (i + x) < 0)
						return null;
					if ((j + y) >= Dim || (j + y) < 0)
						return null;
					return matPolja[i + x][j + y];
				}
			}
		return null;
	}
	
	public void PostaviPoene(Label p) {
		poeni = p;
		poeni.setText("Broj poena: " + BrPoena);
	}
	
	public synchronized List<Igrac> getListI() {
		return listI;
	}

	public synchronized  List<Novcic> getListN() {
		return listN;
	}

	public synchronized  List<Tenk> getListT() {
		return listT;
	}
	
	public synchronized  Igra getIgra() {
		return igra;
	}
	
	public synchronized int getBrPoena() {
		return BrPoena;
	}
}
