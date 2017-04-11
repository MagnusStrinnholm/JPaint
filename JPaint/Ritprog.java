import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.border.*;

public class Ritprog extends JFrame implements ActionListener{
	
	//Klassvariabler
	
	public Rak startarak = new Rak();
	
	private Color ccc;
	private JColorChooser cd = new JColorChooser();
	
	private JOptionPane ruta;
	
	private String namn = null;
	
	private int count, count1;
	public static int pensel = 1;
	
	private boolean bool, counta; 
	private static boolean h�ndelse = false;
	public static boolean sudd = false;
	
	private javax.swing.Timer tid = new javax.swing.Timer(100, this);
	
	private JMenu meny = new JMenu("Arkiv"),
			meny2 = new JMenu("Hj�lp"),
			meny1 = new JMenu("F�rger"),
			meny3 = new JMenu("Visa");

	private JMenuItem ny = new JMenuItem("Ny"),
			spara = new JMenuItem("Spara"),
			sparasom = new JMenuItem("Spara som"),
			�ppna = new JMenuItem("�ppna"),
			avsluta = new JMenuItem("Avsluta"),
			omjpaint = new JMenuItem("Om JPaint"),
			cchoosermenu = new JMenuItem("Redigera F�rger");

	private JCheckBoxMenuItem verktygsl�da = new JCheckBoxMenuItem("Verktygsl�da", true),
			aa = new JCheckBoxMenuItem("Antialiasing", false);
	
	private JButton ritarak = new JButton(new ImageIcon("Button1.jpg")),	
			ritasne = new JButton(new ImageIcon("Button2.jpg")),
			ritabox = new JButton(new ImageIcon("Knapp4.jpg")),	
			ritacirkel = new JButton(new ImageIcon("Button3.jpg")),
			sudda = new JButton(new ImageIcon("Knapp6.jpg")),
			fylla = new JButton(new ImageIcon("Knapp5.jpg")),
			penselm = new JButton(new ImageIcon("Knapp8.jpg")),
			pensels = new JButton(new ImageIcon("Knapp10.jpg")),
			pensell = new JButton(new ImageIcon("Knapp7.jpg")),
			pensele = new JButton(new ImageIcon("Knapp9.jpg"));
	
	public static JLabel valdknapp;
	private JLabel version = new JLabel("JPaint Av M�"),
			tom = new JLabel(""),
			tom1 = new JLabel(""),
			tom2 = new JLabel("");
	private JLabel progr = new JLabel("version 1.0");		

	
	private JDialog c = new JDialog(),
			cchooser = new JDialog(),
			n = new JDialog();

	private JPanel p = new JPanel(),
			� = new JPanel(),
			u = new JPanel();
	
	//X Krysset
		
	public WindowListener win = new WindowAdapter(){
		public void windowClosing(WindowEvent e) {
            
		if(h�ndelse == true){	
			counta = true;
		
			try{
				Avsluta(counta);
			}catch(IOException ioe){
		}
	}
		else{
			System.exit(0);
		}
	}
};

	//ColorChooser

	public void Cchooser(){
		cchooser.add(cd);
		cchooser.pack();
		cchooser.setResizable(false);
		cchooser.setLocationRelativeTo(null);
		cchooser.setTitle("Redigera f�rger");
		
		cchooser.setModal(true);
		cchooser.setVisible(true);
		valdknapp.setBackground(cd.getColor());
	}

	//Massa f�rger

	public JPanel F�rgknappar(){

		JPanel fpanel = new JPanel();
		fpanel.setLayout(new BorderLayout());

		JPanel canel = new JPanel();
		fpanel.add("North",canel);
		canel.setLayout(new GridLayout(5,4));
		canel.setPreferredSize(new Dimension(70,90));
	
		Color[] f�rger = {new Color(152,0,0),new Color(132,66,0),new Color(219,219,112),
							new Color(200,200,200),new Color(0,0,108),new Color(155,192,210),
							new Color(230,230,230),new Color(64,224,208),new Color(195,100,1),
					  		Color.WHITE,Color.ORANGE,Color.PINK, Color.RED,Color.MAGENTA,Color.GRAY, 
					  		Color.GREEN,Color.CYAN,Color.YELLOW,Color.BLUE,Color.BLACK};
	
		for(int i=0; i < f�rger.length; i++){
			Button knapp = new Button("");
			knapp.setBackground(f�rger[i]);
			knapp.addActionListener(this);
			canel.add(knapp);
	}
	
		valdknapp = new JLabel("");
		valdknapp.setOpaque(true);
		valdknapp.setBackground(Color.BLACK);
		cd.setColor(valdknapp.getBackground());
		valdknapp.setBorder(new MatteBorder(1,1,1,1,Color.BLACK)); 
		fpanel.add(valdknapp);
		return fpanel;
	}
	
	//Raderar panelen
	
	public void Ny(){
			startarak.buffer = null;
			startarak.repaint();
			namn = null;
			h�ndelse = false;	
	}
	
	//Avsluta programmet
	
	public void Avsluta(boolean a) throws IOException{

		if(namn == null){
			ruta = new JOptionPane("Vill du spara �ndringarna i " + "namnl�s" + "?");
		}
		else{
			ruta = new JOptionPane("Vill du spara �ndringarna i " + namn + "?");
		}
					
			ruta.setMessageType(JOptionPane.WARNING_MESSAGE);
			ruta.setOptionType(JOptionPane.YES_NO_OPTION);
			
			n = ruta.createDialog(this, "JPaint");
			String[] alternativ = {"Ja", "Nej", "Avbryt"};
			ruta.setOptions(alternativ);
			
			n.setVisible(true);
			
			if(ruta.getValue() == "Nej"){
				if(a == true){
					System.exit(0);
			}
				else{
					Ny();
		}
	}				
			else if(ruta.getValue() == "Ja"){
				Spara();
				System.exit(0);
			}
		}

	//�ppna bild
	
		public String �ppna() throws IOException, NullPointerException{
		
		JFileChooser fc = new JFileChooser();
		
		int result = fc.showOpenDialog(null);
		if(result != JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(null, "Ingen fil valdes");
		}	
			namn = fc.getSelectedFile().getAbsolutePath();
			return namn;
	}
			
	//Spara som bild
	
		public String Sparasom() throws IOException, NullPointerException{
		
		JFileChooser fc = new JFileChooser();
		
		int result = fc.showSaveDialog(null);
		if(result != JFileChooser.APPROVE_OPTION){
			JOptionPane.showMessageDialog(null, "Ingen fil valdes");
		}	
			namn = fc.getSelectedFile().getAbsolutePath();
			h�ndelse = false;
			return namn;
			
	}
	
	//Spara Bild
	
	public void Spara() throws IOException{
		
		if(namn != null){
    	ImageIO.write(startarak.buffer, "JPG", new File(namn + ".jpg"));
    }
    	else{
    		Sparasom();
    }
 
		h�ndelse = false;
}
	
	//"Om JPaint" dialogen
	
	public void Om(){
		
		JLabel logo = new JLabel();
		logo.setPreferredSize(new Dimension(45,20));
		JPanel om = new JPanel();
		om.setLayout(new FlowLayout());
		om.add(progr);
		om.add(version);
		c.add(om);
		c.setLocationRelativeTo(this);
		c.pack();
		c.setTitle("Om JPaint");
		//c.setResizable(false);
		c.setModal(true);
		
		c.setVisible(true);
	}
	
	public static void h�nder(boolean a){
	
	h�ndelse = a;
}
	
	//Huvudf�nstret
	
	public Ritprog(){
	
	setJMenuBar(new JMenuBar());
	getJMenuBar().add(meny);
	getJMenuBar().add(meny3);
	getJMenuBar().add(meny1);
	getJMenuBar().add(meny2);

	meny.add(ny);
	meny.add(�ppna);
	meny.add(spara);
	meny.add(sparasom);
	meny.addSeparator();
	meny.add(avsluta);
	meny2.add(omjpaint);
	meny3.add(verktygsl�da);
	meny3.add(aa);
	meny1.add(cchoosermenu);
	
	startarak.addMouseListener(startarak.mouse);
	startarak.addMouseMotionListener(startarak.mo);
	
	addWindowListener(win);
	
	ritabox.addActionListener(this);
	ritacirkel.addActionListener(this);
	ritarak.addActionListener(this);
	ritasne.addActionListener(this);
	sudda.addActionListener(this);
	fylla.addActionListener(this);
	
	pensell.addActionListener(this);
	penselm.addActionListener(this);
	pensels.addActionListener(this);
	pensele.addActionListener(this);
	
	tid.addActionListener(this);
	tid.start();
	
	ny.addActionListener(this);
	�ppna.addActionListener(this);
	spara.addActionListener(this);
	sparasom.addActionListener(this);
	avsluta.addActionListener(this);
	omjpaint.addActionListener(this);
	cchoosermenu.addActionListener(this);
	verktygsl�da.addActionListener(this);
	aa.addActionListener(this);
	
	verktygsl�da.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
	aa.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	ny.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	�ppna.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	spara.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	avsluta.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
	
	setLayout(new BorderLayout());
	
	add(startarak);	

	add(p, BorderLayout.WEST);
	p.setLayout(new GridLayout(2,1));
	p.setBorder(new MatteBorder(1,1,1,1,Color.BLACK)); 

	p.add(�);
	�.setLayout(new GridLayout(6,1,0,15));
	
	p.add(u);
	u.setLayout(new GridLayout(2,1));
	
	�.add(ritabox);
	ritabox.setPreferredSize(new Dimension(45,20));
	�.add(ritacirkel);
	ritacirkel.setPreferredSize(new Dimension(45,20));
	�.add(ritasne);
	ritasne.setPreferredSize(new Dimension(45,20));
	�.add(ritarak);
	ritarak.setPreferredSize(new Dimension(45,20));
	
	
	�.add(sudda);
	sudda.setPreferredSize(new Dimension(45,20));
	�.add(fylla);
	fylla.setPreferredSize(new Dimension(45,20));
	
	�.add(tom1); �.add(tom);

	�.add(pensell); 
	pensell.setPreferredSize(new Dimension(45,20));
	�.add(penselm);
	penselm.setPreferredSize(new Dimension(45,20)); 
	�.add(pensels); 
	pensels.setPreferredSize(new Dimension(45,20));
	�.add(pensele);
	pensele.setPreferredSize(new Dimension(45,20));	
	
	u.add(tom2);
	u.add(F�rgknappar());

	setSize(800,600);
	setResizable(false);
	setLocationRelativeTo(null);
	setVisible(true);
	setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
}
 
	//Titeln

	public void actionPerformed(ActionEvent e){

	if(h�ndelse == false){
		if(namn == null){
			setTitle("namnl�s" + " - JPaint");
	}
		else{
			setTitle(namn + " - JPaint");
	}
}
	else{
		if(namn == null){
			setTitle("namnl�s" + " - JPaint*");
	}
		else{
			setTitle(namn + " - JPaint*");
	}
}

	//Knappar, menyer och fler knappar
	
	if(e.getSource() instanceof Button){
		valdknapp.setBackground(((Component)e.getSource()).getBackground());
		cd.setColor(valdknapp.getBackground());
	}

	if(e.getSource() == fylla){	
			System.out.print("Under Konstruktion");
	}
	
	if(e.getSource() == sudda){
		startarak.counting(count = 1, count1 = 1);
		sudd = true;
		ccc = valdknapp.getBackground();
		valdknapp.setBackground(Color.WHITE);				
	}
		
	if(e.getSource() == pensell){
		pensel = 1;				
	}
	
	if(e.getSource() == penselm){
		pensel = 2;				
	}
	
	if(e.getSource() == pensels){
		pensel = 4;					
	}
	
	if(e.getSource() == pensele){
		pensel = 9;					
	}

	if(e.getSource() == ritasne){
			if(sudd == true){
				valdknapp.setBackground(ccc);
			}
			sudd = false;				
			startarak.counting(count = 1, count1 = 1);
	}
	
	if(e.getSource() == ritarak){	
			if(sudd == true){
				valdknapp.setBackground(ccc);
			}
			sudd = false;
			startarak.counting(count = 1, count1 = 2);	
	}
	
	if(e.getSource() == ritabox){	
			if(sudd == true){
				valdknapp.setBackground(ccc);
			}
			sudd = false;	
			startarak.counting(count = 3, count1 = 2);			
	}
	
	if(e.getSource() == ritacirkel){
			if(sudd == true){
				valdknapp.setBackground(ccc);
			}
			sudd = false;
			startarak.counting(count = 2, count1 = 2);		
	}

	if(e.getSource() == cchoosermenu){
			Cchooser();
	}

	if(e.getSource() == omjpaint){
			Om();
	}
	
	if(e.getSource() == avsluta){
		if(h�ndelse == true){	
			try{
				Avsluta(counta = true);
			}catch(IOException ioe){
		}
	}
		else{
			System.exit(0);
		}
	}
	
	if(e.getSource() == verktygsl�da){
		
		if(verktygsl�da.isSelected()){
			add(p);
			repaint();
		}
		else{
			remove(p);
			repaint();
		}
	}
		
	if(e.getSource() == aa){
		
		if(aa.isSelected()){
			startarak.counting(bool = true);	
		}
		else{
			startarak.counting(bool = false);	
		}
	}
	
	if(e.getSource() == ny){
		if(h�ndelse == true){
			try{
				Avsluta(counta = false);
			}catch(IOException ioe){
		}
	}
		else{
			Ny();
	}
}
	
	if(e.getSource() == �ppna){
		try{
			�ppna();
		}catch(IOException ioe){
	}
}
	
	if(e.getSource() == spara){
		try{
			Spara();
		}catch(IOException ioe){
	}	
}
	
	if(e.getSource() == sparasom){
			try{
				Sparasom();
			}catch(IOException ioe){
		}	
	}
}
	
	public static void main(String arg []){
	
		Ritprog ritpr = new Ritprog();	    
	}
}