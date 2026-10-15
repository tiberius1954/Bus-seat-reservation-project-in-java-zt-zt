
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Passengerhome extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
	String rowid = "";
	int myrow = 0;
	JFrame myframe = this;

	Passengerhome() {
		if(dh.mypassid==null) {
			return;
		}
		init();
		hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				dispose();
			}
		});
		setLayout(null);
		setMinimumSize(new Dimension(100, 100));
		setSize(new java.awt.Dimension(1100, 630));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Passenger menu");
		lbheader = hh.fflabel("Passenger menu");
		lbheader.setBounds(40, 10, 250, 35);
		add(lbheader);
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 1060, 520);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);
		
		menuPanel = new JPanel(null);
		menuPanel.setBounds(15, 70, 350, 355);
		menuPanel.setBorder(hh.ztroundborder(Color.BLUE));
		menuPanel.setOpaque(false);
		basePanel.add(menuPanel);
		
		btnreservation = cbutton("Seat reservation");
		btnreservation.setBounds(50, 30, 250, 40);	
		menuPanel.add(btnreservation);
		btnreservation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Destinationsearch(myframe, "passengerhome");
			}
		});
		
		btncontactus = cbutton("Contact us");
		btncontactus.setBounds(50, 80, 250, 40);	
		menuPanel.add(btncontactus);
		btncontactus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Passcontactus();
			}
		});
		
		btnfeedback = cbutton("Feedback");
		btnfeedback.setBounds(50, 130, 250, 40);	
		menuPanel.add(btnfeedback);
		btnfeedback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			new Passfeedback();
			}
		});
		
		btnregistration = cbutton("Registration");
		btnregistration.setBounds(50, 180, 250, 40);	
		menuPanel.add(btnregistration);
		btnregistration.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			new Registration(myframe);			
			}
		});
		
		btnview = cbutton("Passenger view");
		btnview.setBounds(50, 230, 250, 40);	
		menuPanel.add(btnview);
		btnview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Passreservations();		
			}
		});
		
		btnexit = cbutton("Exit");
		btnexit.setBounds(50, 280, 250, 40);	
		menuPanel.add(btnexit);
		btnexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		    dispose();
			}
		});
	
		
		picPanel = new JPanel(null);
		picPanel.setBounds(375, 70, 670, 360);
	    picPanel.setBorder(hh.ztroundborder(Color.BLUE));
		picPanel.setOpaque(false);
		basePanel.add(picPanel);		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/buses.png"));
		Image i2 = i1.getImage().getScaledInstance(650, 345, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(10, 5, 650	, 345);
		picPanel.add(lbpicture);
		basePanel.add(picPanel);	
		setVisible(true);
	}
	
	public JButton cbutton(String string) {
		JButton bbutton = new JButton(string);
		bbutton.setFont(new java.awt.Font("Lucida Handwriting", 1, 24));
		bbutton.setBackground(new Color(159, 255, 155));
		bbutton.setBorder(BorderFactory.createMatteBorder(1, 1, 4, 4, Color.BLACK));
		bbutton.setForeground(hh.fekete);
		bbutton.setPreferredSize(new Dimension(100, 30));
		bbutton.setFocusable(false);
		bbutton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return bbutton;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Passengerhome();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbpicture;
	JPanel basePanel, menuPanel,  picPanel;
	JButton btnreservation, btncontactus, btnfeedback, btnregistration, btnexit, btnview;
}
