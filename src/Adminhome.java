import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;

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
import Databaseop.Databaseop.Bus;

public class Adminhome extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();	
	JFrame myframe = this;
	Adminhome(){
		if(dh.myadminid ==null) {
			return;
		}
		init();
		hh.iconhere(this);
	}
	private void init(){
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
		setTitle("Admin dashboard");
		lbheader = hh.fflabel("Admin dasboard");
		lbheader.setBounds(40, 10, 200, 35);
		add(lbheader);
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 1060, 520);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		 basePanel.setOpaque(false);
		add(basePanel);
		
		menuPanel = new JPanel(null);
		menuPanel.setBounds(15, 25, 275, 470);
		menuPanel.setBorder(hh.ztroundborder(Color.BLUE));
		menuPanel.setOpaque(false);
		basePanel.add(menuPanel);
		
		btnroutes = cbutton("Routes");
		btnroutes.setBounds(13, 10, 250, 40);	
		menuPanel.add(btnroutes);
		btnroutes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Routes();
			}
		});
	
		btnschedules = cbutton("Schedules");
		btnschedules.setBounds(13, 60, 250, 40);	
		menuPanel.add(btnschedules);
		btnschedules.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		 	new Schedules();
			}
		});
		
		btnreservations = cbutton("Reservations");
		btnreservations.setBounds(13, 110, 250, 40);	
		menuPanel.add(btnreservations);
		btnreservations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			new Admin_view_reservations();
			}
		});		
		
		btnpassview = cbutton("Passenger view");
		btnpassview.setBounds(13, 160, 250, 40);	
		menuPanel.add(btnpassview);
		btnpassview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Passengers();
			}
		});
		
		btnbuses = cbutton("Buses");
		btnbuses.setBounds(13, 210, 250, 40);	
		menuPanel.add(btnbuses);
		btnbuses.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
		 	new Buses();
			}
		});
		
		btndrivers = cbutton("Drivers");
		btndrivers.setBounds(13, 260, 250, 40);	
		menuPanel.add(btndrivers);
		btndrivers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Drivers();
			}
		});
		btnadmins = cbutton("Adminusers");
		btnadmins.setBounds(13, 310, 250, 40);	
		menuPanel.add(btnadmins);
		btnadmins.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
			    new Adminusers();
			}
		});
		
		btnlayouts = cbutton("Layouts");
		btnlayouts.setBounds(13, 360, 250, 40);	
		menuPanel.add(btnlayouts);
		btnlayouts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Layouts();
			}
		});
		
		btnlayoutsview = cbutton("Layouts view");
		btnlayoutsview.setBounds(13, 410, 250, 40);	
		menuPanel.add(btnlayoutsview);
		btnlayoutsview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Layoutsviewp();
			}
		});
		
		picPanel = new JPanel(null);
		picPanel.setBounds(300, 25, 460, 470);
		picPanel.setBorder(hh.ztroundborder(Color.BLUE));
		picPanel.setOpaque(false);
		basePanel.add(picPanel);		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/dasboard.png"));
		Image i2 = i1.getImage().getScaledInstance(450, 460, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(5, 5, 450, 460);
		picPanel.add(lbpicture);
		basePanel.add(picPanel);		
		
		menuPanel1 = new JPanel(null);
		menuPanel1.setBounds(770, 25, 275, 470);
		menuPanel1.setBorder(hh.ztroundborder(Color.BLUE));
		menuPanel1.setOpaque(false);
		basePanel.add(menuPanel1);
		
		btnfeedback = cbutton("Feed back view");
		btnfeedback.setBounds(13, 10, 250, 40);	
		menuPanel1.add(btnfeedback);
		btnfeedback.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Admin_view_feedbacks();
			}
		});
		
		btncontactus = cbutton("Contact us view");
		btncontactus.setBounds(13, 60, 250, 40);	
		menuPanel1.add(btncontactus);
		btncontactus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Admin_view_feedbacks();
			}
		});
		
		btnoccupancy = cbutton("Occupancy");
		btnoccupancy.setBounds(13, 110, 250, 40);	
		menuPanel1.add(btnoccupancy);
		btnoccupancy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Destinationsearch(myframe, "Adminhome");
			}
		});		
		
		btnemployees = cbutton("Employees");
		btnemployees.setBounds(13, 160, 250, 40);	
		menuPanel1.add(btnemployees);
		btnemployees.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				new Employees();
			}
		});		
		
		btnexit = cbutton("Exit");
		btnexit.setBounds(13, 210, 250, 40);	
		menuPanel1.add(btnexit);
		btnexit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				dispose();
			}
		});		

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
					new Adminhome();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JPanel basePanel, menuPanel, picPanel, menuPanel1;
	JLabel lbheader, lbpicture;
	 JButton btnbuses, btnpassview, btnroutes, btnschedules, btnreservations, btndrivers, btnadmins,
	 btnlayouts,  btnlayoutsview, btnfeedback, btncontactus, btnoccupancy, btnemployees, btnexit;
}
