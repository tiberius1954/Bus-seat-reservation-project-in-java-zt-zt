import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;

import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Registration extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();	
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
    String sfrom="";
	JFrame pframe;
	
	Registration(JFrame parent){
		pframe= parent;	
		init();
		hh.iconhere(this);
	}
	
	Registration(){	
		pframe = this;
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
				    pframe.setVisible(true);
					dispose();			
			}
		});
		setLayout(null);
		setMinimumSize(new Dimension(100, 100));
		setSize(new java.awt.Dimension(1100, 600));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Passenger registration");
		lbheader = hh.fflabel("Passenger registration");
		lbheader.setBounds(20, 10, 400, 35);
		add(lbheader);
		
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 1060, 500);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		 basePanel.setOpaque(false);
		add(basePanel);
		
		ePanel = new JPanel(null);
		ePanel.setBounds(15, 25, 380, 450);
		ePanel.setBorder(hh.ztroundborder(Color.BLUE));
		ePanel.setOpaque(false);
		basePanel.add(ePanel);			

		picPanel = new JPanel(null);
		picPanel.setBounds(400, 25, 650, 450);
		picPanel.setBorder(hh.ztroundborder(Color.BLUE));
		picPanel.setOpaque(false);
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/signin.png"));
		Image i2 = i1.getImage().getScaledInstance(600, 410, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(25, 20, 600, 410);
		picPanel.add(lbpicture);
		basePanel.add(picPanel);		
		
		lbpname = hh.clabel("Name");
		lbpname.setBounds(10, 30, 100, 30);
		ePanel.add(lbpname);

		txpname=gr.gTextField(100);
		txpname.setBounds(120, 30, 240, 30);
		txpname.addKeyListener(hh.MUpper());
		ePanel.add(txpname);	
		
		lbusername = hh.clabel("Username");
		lbusername.setBounds(10, 65, 100, 30);
		ePanel.add(lbusername);

		txusername=gr.gTextField(100);
		txusername.setBounds(120, 65, 240, 30);	
		ePanel.add(txusername);	
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 100, 100, 30);
		ePanel.add(lbphone);

		txphone=gr.gTextField(100);
		txphone.setBounds(120, 100, 240, 30);	
		ePanel.add(txphone);	
		
		lbemail= hh.clabel("Email");
		lbemail.setBounds(10, 135, 100, 30);
		ePanel.add(lbemail);

		txemail=gr.gTextField(100);
		txemail.setBounds(120, 135, 240, 30);	
		ePanel.add(txemail);	
		
		lbgender= hh.clabel("Gender");
		lbgender.setBounds(10, 170, 100, 30);
		ePanel.add(lbgender);		
		
		cmbgender = gr.grcombo();
		cmbgender.addItem("Female");
		cmbgender.addItem("Male");
		cmbgender.setBounds(120, 170, 240, 30);
		ePanel.add(cmbgender);
		
		lbpassword= hh.clabel("Password");
		lbpassword.setBounds(10, 205, 100, 30);
		ePanel.add(lbpassword);

		txpassword= gr.gpasstField(100);
		txpassword.setBounds(120, 205, 240, 30);	
		ePanel.add(txpassword);	
		
		lbaddress= hh.clabel("Address");
		lbaddress.setBounds(10, 250, 100, 30);
		ePanel.add(lbaddress);
		
		txaaddress = gr.gTextArea();		
		jsp1 = new JScrollPane(txaaddress, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp1.setBounds(120, 250, 240, 120);
		jsp1.setViewportView(txaaddress);
		ePanel.add(jsp1);
		
		btnreg = gr.sbcs("Register");
		btnreg.setBounds(160, 390, 130, 30);
		btnreg.setForeground(Color.black);
		btnreg.setBackground(hh.lpiros);
		ePanel.add(btnreg);
		btnreg.addActionListener(e -> regsave());		
		setVisible(true);
	}
	
	private void regsave() {		
		
			String pname = txpname.getText();
			String phone = txphone.getText();
			String username = txusername.getText();
			String email = txemail.getText();	
			String address = txaaddress.getText();
			String password = String.valueOf(txpassword.getPassword());
			String gender = cmbgender.getSelectedItem().toString();	
			String snow = hh.currentDate();
		    
		    if (dd.pvalidation(pname, username, phone, email, address, password, gender) == true) {
		 		return;
			}
			String sql = "insert into   passenger (pname, username, phone,  email , address, "
					+ "  password, gender, regdate) values ( '"+pname+"','"  +username +"','"+phone +"','" +email 
					+ "', '" + address +"','" +password +"','" + gender + "','" + snow + "')";
			
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
			clearFields();		
	}

	private void clearFields(){
		txpname.setText("");
		txphone.setText("");
		txusername.setText("");
		txemail.setText(""); 
		txaaddress.setText("");
		txpassword.setText("");		
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Registration();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JPanel basePanel, ePanel, picPanel;
	 JLabel lbheader, lbpname, lbusername, lbphone, lbemail, lbgender, lbpassword, lbaddress,
	 lbpicture;
		JTextField txpname, txusername, txphone, txemail;
		JPasswordField txpassword;
		JTextArea txaaddress;
		JComboBox cmbgender;
		JButton btnreg;
		JScrollPane jsp1;
		JTable rtable;
}
