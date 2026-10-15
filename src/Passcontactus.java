import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Passcontactus extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Spallvalid sval = new Spallvalid();
	
	Passcontactus(){
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
		setSize(new java.awt.Dimension(1160, 600));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Contact us");
		lbheader = hh.fflabel("Contact us");
		lbheader.setBounds(40, 10, 200, 35);
		add(lbheader);
		setLayout(null);
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);

		basePanel = new JPanel(null);
		basePanel.setBounds(20, 50, 1100, 480);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(20, 20, 500, 440);
		ePanel.setBorder(hh.ztroundborder(Color.BLUE));
		ePanel.setOpaque(false);
		basePanel.add(ePanel);
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 20, 80, 30);
		ePanel.add(lbname);

		txname = gr.gTextField(100);
		txname.addKeyListener(hh.MUpper());
		txname.setBounds(100, 20, 350, 30);
		ePanel.add(txname);

		lbemail = hh.clabel("Email");
		lbemail.setBounds(10, 60, 80, 30);
		ePanel.add(lbemail);

		txemail= gr.gTextField(100);
		txemail.setBounds(100, 60, 350, 30);	
		ePanel.add(txemail);
		
		lbmessage= hh.clabel("Message");
		lbmessage.setBounds(10, 100, 80, 30);
		ePanel.add(lbmessage);
		
		txamessage = gr.gTextArea();		
		jsp1 = new JScrollPane(txamessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp1.setBounds(100, 100, 370, 270);
		jsp1.setViewportView(txamessage);
		ePanel.add(jsp1);			

		btnsubmit = gr.sbcs("Submit");
		btnsubmit.setBounds(200, 390, 130, 30);
		btnsubmit.setForeground(Color.black);
		btnsubmit.setBackground(hh.lpiros);
		ePanel.add(btnsubmit);
	    btnsubmit.addActionListener(e -> save());
		
		picPanel = new JPanel(null);
		picPanel.setBounds(530, 20, 550, 440);		
	    picPanel.setBorder(hh.ztroundborder(Color.BLUE));
		picPanel.setOpaque(false);
		basePanel.add(picPanel);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/contactus.png"));
		Image i2 = i1.getImage().getScaledInstance(530, 420, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(10, 10, 530, 420);
		picPanel.add(lbpicture);
		basePanel.add(picPanel);		

        setVisible(true);
	}
	private void save() {
		
		String sql ="";		
		String name = txname.getText();
		String email = txemail.getText();	
		String message = txamessage.getText();
		String nowdate = hh.currentDate();
		 if (cvalidation(name, email, message) == true) {
		 		return;
			}
		 sql = "insert into contactus (name, email, message, date) values ( '" + name + "','" 
			+ email + "','" + message + "','" + nowdate + "')";
		 try {
				int flag = dh.Insupdel(sql);
				if (flag == 1) {										
					hh.ztmessage("Success", "Message");
				} else {
					JOptionPane.showMessageDialog(null, "sql if  error !");
				}
			} catch (Exception e) {
				System.err.println("SQLException: " + e.getMessage());
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "sql insert hiba");
			}			
	}
	private Boolean cvalidation(String name, String email, String message){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();		
		if(sval.emptyvalid(name)){
			err.add( "Name  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(email)){
			err.add( "Email   is empty !");
			ret = true;
		}
		if(sval.emptyvalid(message)){
			err.add( "Message  is empty !");
			ret = true;
		}
		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
	
	return ret;
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Passcontactus();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lbheader, lbname, lbemail, lbmessage, lbpicture;
	JTextField txname, txemail;
	JTextArea txamessage;
	JPanel basePanel, ePanel, picPanel;
	JButton btnsubmit;
	JScrollPane jsp1;
}
