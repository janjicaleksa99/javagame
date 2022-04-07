package igra;

import java.awt.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class Igra extends Frame {
	private Mreza mreza = new Mreza(this);
	private CheckboxGroup cg;
	private Checkbox trava;
	private Checkbox zid;
	private boolean rezim = false, start = false;
	private Label BrPoena;
	private int trzid = -1;

	{
		cg = new CheckboxGroup();
		trava = new Checkbox("Trava", cg, false);
		zid = new Checkbox("Zid", cg, false);
		BrPoena = new Label("Broj poena: ");
	}
	
	public Igra() {
		super("Igra");
		setSize(800, 700);
		setVisible(true);
		
		MenuBar mb = new MenuBar();
		Menu menu = new Menu("Rezim");
		MenuItem izmena = new MenuItem("Rezim izmena");
		MenuItem igranje = new MenuItem("Rezim igranje");
		menu.add(izmena);
		menu.add(igranje);
		mb.add(menu);
		setMenuBar(mb);
		
		setLayout(new FlowLayout());
		add(mreza);
		
		Panel podloga = new Panel();
		Label l = new Label("Podloga: ");
		podloga.add(l);
		//l.setLocation(500, 300);
		add(podloga);
		
		Panel polje = new Panel(new GridLayout(2, 0));
		polje.setPreferredSize(new Dimension(100, 510));
		Panel polje1 = new Panel(new BorderLayout());
		polje1.setPreferredSize(new Dimension(100, 250));
		Panel polje2 = new Panel(new BorderLayout());
		polje2.setPreferredSize(new Dimension(100, 250));
		polje1.setBackground(Color.GREEN);
		polje2.setBackground(Color.LIGHT_GRAY);
		polje1.add(trava, BorderLayout.CENTER);
		polje2.add(zid, BorderLayout.CENTER);
		trava.setLocation(0, polje1.getHeight() / 2);
		zid.setLocation(0, polje2.getHeight() / 2);
		polje.add(polje1);
		polje.add(polje2);
		add(polje);
		
		Panel bottom = new Panel();
		Label novcica = new Label("Novcica:   ");
		TextField brnovcica = new TextField();
		mreza.PostaviPoene(BrPoena);
		Button Pocni = new Button("Pocni");
		
		bottom.add(novcica);
		bottom.add(brnovcica);
		bottom.add(BrPoena);
		bottom.add(Pocni);
		add(bottom, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mreza.ZaustaviNit();
				dispose();
			}
		});
		
		izmena.addActionListener(ae -> {
			if (rezim == true) {
				mreza.ZaustaviNit();
			}
			rezim = false;
			trava.setEnabled(true);
			zid.setEnabled(true);
			Pocni.setEnabled(false);
			
		});
		
		igranje.addActionListener(ae -> {
			rezim = true;
			trava.setEnabled(false);
			zid.setEnabled(false);
			Pocni.setEnabled(true);
			
		});
		
		Pocni.addActionListener(ae -> {
			if (start == false) {
				mreza.InicijalizujMrezu(Integer.parseInt(brnovcica.getText()));
				start = true;
			}
			else {
				mreza.ZaustaviNit();
				mreza.StvoriNit();
				mreza.InicijalizujMrezu(Integer.parseInt(brnovcica.getText()));
			}
		});
		
		trava.addItemListener((ie) -> {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				trzid = 1;
			}
		});
		zid.addItemListener((ie) -> {
			if (ie.getStateChange() == ItemEvent.SELECTED) {
				trzid = 0;
			}
		});
	}
	
	public String IzabranoDugme() {
		if (trzid == 1)
			return "trava";
		else if (trzid == 0)
			return "zid";
		return null;
	}
	
	public boolean isRezim() {
		return rezim;
	}

	public static void main(String args[]) {
		new Igra();
	}
}
