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

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Layout;

public class Passengers extends JFrame {
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
	
	Passengers(){
		init();
	ptable_update("");
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
		setSize(new java.awt.Dimension(1100, 600));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Passengers");
		lbheader = hh.fflabel("Passengers");
		lbheader.setBounds(40, 10, 200, 35);
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
		
		tPanel = new JPanel(null);
		tPanel.setBounds(400, 25, 650, 450);
		tPanel.setBorder(hh.ztroundborder(Color.BLUE));
		tPanel.setOpaque(false);
		basePanel.add(tPanel);		

		lbpname = hh.clabel("Name");
		lbpname.setBounds(10, 30, 100, 30);
		ePanel.add(lbpname);

		txpname=gr.gTextField(100);
		txpname.setBounds(120, 30, 200, 30);
		txpname.addKeyListener(hh.MUpper());
		ePanel.add(txpname);	
		
		lbusername = hh.clabel("Username");
		lbusername.setBounds(10, 65, 100, 30);
		ePanel.add(lbusername);

		txusername=gr.gTextField(100);
		txusername.setBounds(120, 65, 200, 30);		
		ePanel.add(txusername);	
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 100, 100, 30);
		ePanel.add(lbphone);

		txphone=gr.gTextField(100);
		txphone.setBounds(120, 100, 200, 30);	
		ePanel.add(txphone);	
		
		lbemail= hh.clabel("Email");
		lbemail.setBounds(10, 135, 100, 30);
		ePanel.add(lbemail);

		txemail=gr.gTextField(100);
		txemail.setBounds(120, 135, 200, 30);
		txemail.addKeyListener(hh.MUpper());
		ePanel.add(txemail);	
		
		lbgender= hh.clabel("Gender");
		lbgender.setBounds(10, 170, 100, 30);
		ePanel.add(lbgender);		
		
		cmbgender = gr.grcombo();
		cmbgender.addItem("Female");
		cmbgender.addItem("Male");
		cmbgender.setBounds(120, 170, 200, 30);
		ePanel.add(cmbgender);
		
		lbpassword= hh.clabel("Password");
		lbpassword.setBounds(10, 205, 100, 30);
		ePanel.add(lbpassword);

		txpassword= gr.gpasstField(100);
		txpassword.setBounds(120, 205, 200, 30);
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
		
		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(50, 390, 100, 30);
		btnupdate.setForeground(Color.black);
		btnupdate.setBackground(hh.lpiros);
		ePanel.add(btnupdate);
		btnupdate.addActionListener(e -> saveupdate());
		
		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(160, 390, 100, 30);
		btncancel.setBackground(hh.vvzold);
		ePanel.add(btncancel);
	 	btncancel.addActionListener(e -> clearFields());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(270, 390, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
        btndelete.addActionListener(e -> data_delete());
		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(10, 10, 630, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(hh.blue2);
		tPanel.add(searchPanel);
		
		lbsearch = hh.clabel("Search:");
		lbsearch.setBounds(20, 10, 70, 25);
		searchPanel.add(lbsearch);

		btnGroup = new ButtonGroup();
		rname = new JRadioButton("Name");
		rname.setHorizontalTextPosition(SwingConstants.LEFT);
		rname.setActionCommand("name");
		rname.setBounds(95, 10, 80, 25);
		rname.setFont(new Font("Tahoma", Font.BOLD, 16));
		rname.setBackground(Color.ORANGE);
		btnGroup.add(rname);	
		
		rtel = new JRadioButton("Phone");
		rtel.setHorizontalTextPosition(SwingConstants.LEFT);
		rtel.setActionCommand("tel");
		rtel.setBounds(180, 10, 80, 25);
		rtel.setFont(new Font("Tahoma", Font.BOLD, 16));
		rtel.setBackground(Color.ORANGE);
		btnGroup.add(rtel);
		searchPanel.add(rname);	
		searchPanel.add(rtel);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(270, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(470, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
			    ptable_update("");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(500, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  	sqlgyart();
			}
		});
		rname.setSelected(true);
		
		ptable = hh.ztable();
		ptable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(ptable);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		ptable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = ptable.getSelectedRow();
				if (row >= 0) {
					rowid = ptable.getValueAt(row, 0).toString();				
					String pname = ptable.getValueAt(row, 1).toString();					
			        txpname.setText(pname);
					String username = ptable.getValueAt(row, 2).toString();
			        txusername.setText(username);
				   String phone = ptable.getValueAt(row, 3).toString();
				   txphone.setText(phone);
					String email= ptable.getValueAt(row, 4).toString();	
					txemail.setText(email);
					String gender =ptable.getValueAt(row, 5).toString();
					cmbgender.setSelectedItem(gender);
                    cmbgender.updateUI();		
					String address = ptable.getValueAt(row, 6).toString();
					txaaddress.setText(address);
					String password = ptable.getValueAt(row, 7).toString();
					txpassword.setText(password);
			         myrow = row;			
				}
			}
		});	

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "passid", "Name", "Username", "Phone","Email", "Gender","Address",
						"Password"}));
		hh.setJTableColumnsWidth(ptable, 1080,  0, 200, 100, 150, 200, 80, 240, 110);
		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(15, 80, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);			
		setVisible(true);
	}
	
	private void saveupdate() {	
		       if (hh.zempty(rowid)) {
			    	return;
			    }
			DefaultTableModel d1 = (DefaultTableModel) ptable.getModel();
			String sql ="";
			String pname = txpname.getText();
			String phone = txphone.getText();
			String username = txusername.getText();
			String email = txemail.getText();	
			String address = txaaddress.getText();
			String password = String.valueOf(txpassword.getPassword());
			String gender = cmbgender.getSelectedItem().toString();		   
		    
		    if (dd.pvalidation(pname, username, phone, email, address, password, gender) == true) {
		 		return;
			}
			sql = "update  passenger set pname= '" + pname + "', username= '" + username 
						+ "', phone='"+ phone + "',  email = '" + email + "' , address= '" + address 
						+ "',  password= '" +password + "', gender= '"+ gender +"' where passid = " + rowid;
			
			try {
				int flag = dh.Insupdel(sql);
				if (flag == 1) {
					    hh.ztmessage("Success", "Message");						
						d1.setValueAt(pname, myrow, 1);
						d1.setValueAt(username, myrow, 2);
						d1.setValueAt(phone, myrow, 3);
						d1.setValueAt(email, myrow, 4);
						d1.setValueAt(gender, myrow, 5);
						d1.setValueAt(address, myrow, 6);
						d1.setValueAt(password, myrow, 7);						
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
	
	private void data_delete() {
		int flag=0;
		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		int row = ptable.getSelectedRow();
		if (row >= 0) {
			String spassid = model.getValueAt(row, 0).toString();	
			if (spassid.equals("")) {
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
			if (a == JOptionPane.YES_OPTION) {				
				String sql = "delete from passenger where passid='" + spassid + "'";
				flag = dh.Insupdel(sql);					
				if (flag > 0) {
					model.removeRow(row);
					clearFields();
				}			
			}
			}
	}
	
	private void clearFields(){
		txpname.setText("");
		txphone.setText("");
		txusername.setText("");
		txemail.setText(""); 
		txaaddress.setText("");
		txpassword.setText("");
		rowid = "";
		myrow = 0;
	}
	
	
	private void ptable_update(String swhere){

		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		model.setRowCount(0);
		String Sql = "select  passid, pname, username, phone, email, gender, address, password "
				+ " from passenger " + swhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String passid = rs.getString("passid");
				String pname = rs.getString("pname");
				String username = rs.getString("username");
				String phone= rs.getString("phone");
				String email = rs.getString("email");
				String gender= rs.getString("gender");
				String address = rs.getString("address");	
				String password = rs.getString("password");
				model.addRow(new Object[] { passid, pname, username, phone, email, gender, address, password});
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "passid", "Name", "Username", "Phone","Email", "Gender","Address",
				"Password"};
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ptable, 1080,  0, 200, 100, 150, 200, 80, 240, 110);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});
		if (ptable.getRowCount() > 0) {
			int row = ptable.getRowCount() - 1;
			ptable.setRowSelectionInterval(row, row);		
		}
	}

	private void sqlgyart() {
		int jelem = 0;
		if (rname.isSelected()) {
			jelem = 1;	
		} else if (rtel.isSelected()) {
			jelem = 2;
		} else {
			String qual = "Did not choose !";
			JOptionPane.showMessageDialog(null, qual);
		}
		if (jelem < 1 || hh.zempty(txsearch.getText())) {
			return;
		}
		String sql = "";
		String ss = txsearch.getText().trim().toLowerCase();

		if (jelem == 1) {
			sql = " where  lower(pname) LIKE '%" + ss + "%' order by pname " + " COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where phone LIKE '%" + ss + "%' order by phone COLLATE NOCASE ASC";
		}
		ptable_update(sql);
	}

		public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Passengers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JTable ptable;
	JPanel basePanel, ePanel, tPanel, searchPanel;
	JComboBox cmbgender;
	JButton btnupdate, btncancel, btndelete, btnsearch, btnclear;
	JScrollPane jScrollPane1, jsp1;
	JLabel lbheader, lbpname, lbusername, lbphone, lbemail, lbgender, lbpassword, lbaddress, lbsearch;
	JTextField txpname, txusername, txphone, txemail, txsearch;
	JPasswordField txpassword;
	JTextArea txaaddress;
	JRadioButton rname, rtel;
	ButtonGroup btnGroup = new ButtonGroup();

}
