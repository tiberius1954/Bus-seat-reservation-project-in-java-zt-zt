
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;



public class Employees extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Spallvalid sval = new Spallvalid();
	String rowid = "";
	int myrow = 0;
	int myid = 0;
	
	Employees(){
		init();
		etable_update("");
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
				if (hh.whichpanel(cardPanel) == "table") {
					dispose();
				} else if (hh.whichpanel(cardPanel) == "edit") {
					cards.show(cardPanel, "table");
				} else {
					cards.show(cardPanel, "table");
				}
			}
		});		
		
		this.getContentPane().setBackground(hh.basecolor1);
		setMinimumSize(new Dimension(100, 100));
		setSize(new java.awt.Dimension(1100, 610));
		setLayout(null);
		setLocationRelativeTo(null);
		setTitle("Employees");
		lbheader = hh.fflabel("Employees");
		lbheader.setBounds(40, 10, 200, 35);	
		add(lbheader);
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(20, 50, 1040, 500);	
		cardPanel.setBackground(hh.basecolor1);
		tPanel = maketpanel();
		tPanel.setName("table");
		cardPanel.add(tPanel, "table");

		ePanel = makeepanel();
		ePanel.setName("edit");
		cardPanel.add(ePanel, "edit");
		add(cardPanel);
		cards.show(cardPanel, "table");
	//	cards.show(cardPanel, "edit");	
     setVisible(true);
	}
	
	private JPanel maketpanel() {
		JPanel ttPanel = new JPanel();
		ttPanel = new JPanel(null);
		ttPanel.setBounds(10, 50, 1060, 510);
		ttPanel.setBorder(hh.ztroundborder(Color.BLUE));	
		ttPanel.setOpaque(false);		
		searchPanel = new JPanel(null);
		searchPanel.setBounds(20, 10, 1000, 45);
		searchPanel.setBorder(hh.linesz);
		searchPanel.setBackground(hh.blue2);
		ttPanel.add(searchPanel);		
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

		txsearch = hh.cTextField(40);
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
			    etable_update("");
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

		etable = hh.ztable();
		etable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		etable.setTableHeader(new JTableHeader(etable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(etable);
		etable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				etable.scrollRectToVisible(etable.getCellRect(etable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane = new JScrollPane(etable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		etable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Empid", "Name", "Phone", "Email", "Gender","Roll","Address","Dob", 
						"Date of join" }));
		hh.setJTableColumnsWidth(etable, 1320, 0, 250, 150, 200, 100, 120,300, 100, 100);
		jScrollPane.setViewportView(etable);
		jScrollPane.setBounds(20, 60, 1000, 350);
		jScrollPane.setBorder(hh.borderf);
		ttPanel.add(jScrollPane);		
		
		btnnew = gr.sbcs("New");
		btnnew.setBounds(320, 430, 125, 35);
		btnnew.setForeground(Color.black);
		btnnew.setBackground(hh.lpiros);
		ttPanel.add(btnnew);
	   btnnew.addActionListener(e -> data_new());

		btnupdate = gr.sbcs("Update");
		btnupdate.setBounds(450, 430, 125, 35);
		btnupdate.setBackground(hh.vvzold);
		ttPanel.add(btnupdate);
 		btnupdate.addActionListener(e -> data_update());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(580, 430, 125, 35);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ttPanel.add(btndelete);
		btndelete.addActionListener(e -> data_delete());
		return ttPanel;
	}
	
	private JPanel makeepanel() {
		JPanel eePanel = new JPanel(null);
		eePanel.setBounds(10, 50, 1060, 510);		
		eePanel.setBorder(hh.ztroundborder(Color.BLUE));	
		eePanel.setOpaque(false);
		
		JPanel lPanel = new JPanel(null);
	    lPanel.setBounds(20, 20, 400, 460);		
		lPanel.setBorder(hh.ztroundborder(Color.BLUE));	
		eePanel.add(lPanel);
		
		lbname = hh.clabel("Name");
		lbname.setBounds(10, 10, 100, 30);
		lPanel.add(lbname);

		txname=gr.gTextField(100);
		txname.setBounds(120, 10, 200, 30);
		txname.addKeyListener(hh.MUpper());
		lPanel.add(txname);	
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(10, 50, 100, 30);
		lPanel.add(lbphone);

		txphone=gr.gTextField(100);
		txphone.setBounds(120, 50, 200, 30);		
		lPanel.add(txphone);		
		txphone.addKeyListener(hh.Onlyphone());
		
		lbemail= hh.clabel("Email");
		lbemail.setBounds(10, 90, 100, 30);
		lPanel.add(lbemail);

		txemail=gr.gTextField(100);
		txemail.setBounds(120, 90, 200, 30);	
		lPanel.add(txemail);	
		
		lbgender= hh.clabel("Gender");
		lbgender.setBounds(10, 130, 100, 30);
		lPanel.add(lbgender);		
		
		cmbgender = gr.grcombo();
		cmbgender.addItem("Female");
		cmbgender.addItem("Male");
		cmbgender.setBounds(120, 130, 200, 30);
		lPanel.add(cmbgender);
		
		lbroll= hh.clabel("Roll");
		lbroll.setBounds(10, 170, 100, 30);
		lPanel.add(lbroll);

		txroll=gr.gTextField(100);
		txroll.setBounds(120, 170, 200, 30);
		txroll.addKeyListener(hh.MUpper());
		lPanel.add(txroll);	
		
		lbaddress= hh.clabel("Address");
		lbaddress.setBounds(10, 210, 100, 30);
		lPanel.add(lbaddress);

		txaaddress=gr.gTextArea();	
		addrPane = new JScrollPane(txaaddress, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addrPane.setBorder(hh.borderf);
		addrPane.setBounds(120, 210, 260, 100);
		addrPane.setViewportView(txaaddress);
		lPanel.add(addrPane);	
		
		lbdob = hh.clabel("Dob");
		lbdob.setBounds(10, 320, 90, 30);
		lPanel.add(lbdob);

		txdob = hh.DatetextField();
		txdob.setBounds(120, 320, 105, 30);
		lPanel.add(txdob);
		
		lbdateofjoin = hh.clabel("Date of join");
		lbdateofjoin.setBounds(10, 360, 100, 30);
		lPanel.add(lbdateofjoin);

		txdateofjoin = hh.DatetextField();
		txdateofjoin.setBounds(120, 360, 105, 30);
		lPanel.add(txdateofjoin);	
		
		btnsave = gr.sbcs("Save");
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		btnsave.setBounds(80, 405, 100, 32);
		btnsave.setFocusable(false);
		lPanel.add(btnsave);
    	btnsave.addActionListener(e -> data_save());

		btncancel = gr.sbcs("Cancel");
		btncancel.setForeground(Color.black);
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(185, 405, 100, 32);
		btncancel.setFocusable(false);
		lPanel.add(btncancel);
	   btncancel.addActionListener(e -> cancelall());
		
		JPanel rPanel = new JPanel(null);
	    rPanel.setBounds(435, 20, 585, 460);		
		rPanel.setBorder(hh.ztroundborder(Color.BLUE));	
		eePanel.add(rPanel);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/employees.png"));
		Image i2 = i1.getImage().getScaledInstance(560, 420, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(12, 20, 560, 420);
		rPanel.add(lbpicture);		
		eePanel.add(rPanel);		
		return eePanel;
	}
	private void data_new() {
		clearFields();
		txdateofjoin.setText(hh.currentDate());
		cards.show(cardPanel, "edit");
		txname.requestFocus(true);
	}
	private void data_update() {
		int row = etable.getSelectedRow();
		if (row >= 0) {
			clearFields();		
			rowid = etable.getValueAt(row, 0).toString();
			myrow = row;		
			txname.setText(etable.getValueAt(row, 1).toString());
			txphone.setText(etable.getValueAt(row, 2).toString());
			txemail.setText(etable.getValueAt(row, 3).toString());		
			cmbgender.setSelectedItem(etable.getValueAt(row, 4).toString());
		    txroll.setText(etable.getValueAt(row, 5).toString());
			txaaddress.setText(etable.getValueAt(row, 6).toString());
			txdob.setText(etable.getValueAt(row, 7).toString());
			txdateofjoin.setText(etable.getValueAt(row, 8).toString());	
			cards.show(cardPanel, "edit");
			txname.requestFocus();
		}
	}
	
	private void data_save() {
		String sql = "";
		DefaultTableModel d1 = (DefaultTableModel) etable.getModel();
		Boolean newrec = false;	
		String name = txname.getText();
		String phone = txphone.getText();
		String email = txemail.getText();
		String gender = (String) cmbgender.getSelectedItem();
		String roll = txroll.getText();
		String address = txaaddress.getText();	
		String dob = txdob.getText();
		String dateofjoin = txdateofjoin.getText();

		if (evalidation(name, phone, email, roll, address, dob, dateofjoin) == true) {
			return;
		}

		if (rowid != "") {
			sql = "update  employees set name= '" + name + "', gender = '" + gender
					+ "', phone = '" + phone + "',  email='" + email + "',  roll= '" + roll + "', address='"+ address 
					+  "', dob= '" + dob + "', dateofjoin= '" + dateofjoin 
					+ "' where empid = '" + rowid +"'";
		} else {		
			sql = "insert into employees (name, gender, phone, email, roll, address, dob, "
					+ "dateofjoin) values ('" + name + "','"+ gender + "','" + phone + "','" 
					+ email + "','" + roll + "','" + address + "','"
					+ dob + "','" + dateofjoin +  "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					int myid = dd.table_maxid("SELECT MAX(empid) AS max_id FROM employees");	
					d1.addRow(new Object[] {myid, name, phone, email, gender, roll, address, dob, dateofjoin});
			
				} else {				
					d1.setValueAt(name, myrow, 1);
					d1.setValueAt(phone, myrow, 2);
					d1.setValueAt(email, myrow, 3);
					d1.setValueAt(gender, myrow, 4);
					d1.setValueAt(roll, myrow, 5);
					d1.setValueAt(address, myrow, 6);
					d1.setValueAt(dob, myrow, 7);
					d1.setValueAt(dateofjoin, myrow, 8);			
				}
			} else {
				JOptionPane.showMessageDialog(null, "sql if  error !");
			}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}	
		clearFields();
		cards.show(cardPanel, "table");		
	}
	
	private Boolean evalidation(String name, String phone, String email, String roll, String address, 
			String dob, String dateofjoin) {			
				Boolean ret = false;
				ArrayList<String> err = new ArrayList<String>();
				
				if(sval.emptyvalid(name)){
					err.add( "Name is empty !");
					ret = true;
				}
				if (sval.emptyvalid(roll)) {
					err.add("Roll is empty !");
					ret = true;
			   }
				if (sval.emptyvalid(phone)) {
					err.add("Phone is empty !");
					ret = true;
				}
				if (sval.emptyvalid(email)) {
					err.add("Email is empty !");
					ret = true;
				}
				if (sval.emptyvalid(address)) {
					err.add("Address is empty !");
					ret = true;
				}
			
				if (sval.emptyvalid(dob)  || dob.contains("_")) {
					err.add("Dob  is empty !");
					ret = true;
				}
				if (sval.emptyvalid(dateofjoin)  || dateofjoin.contains("_")) {
					err.add("date of join  is empty !");
					ret = true;
				}
				
					if (err.size() > 0) {
						JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
					}
				return ret;
	}
	
	
	private void cancelall() {
		clearFields();
		cards.show(cardPanel, "table");	
	}
	
	private void data_delete() {
		String srowid ="";
		int row = etable.getSelectedRow();
		if (row >= 0) {
			srowid = etable.getValueAt(row, 0).toString();
			String sql = "delete from employees where empid =";
			int flag = dd.data_delete(etable, sql);			
		}
	}
	
	private void etable_update(String pwhere){	
		DefaultTableModel model = (DefaultTableModel) etable.getModel();
		model.setRowCount(0);
		String Sql = "select  empid, name, phone, email, gender, roll, address, dob, dateofjoin "
				+ " from employees " + pwhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String empid = rs.getString("empid");
				String name = rs.getString("name");		
				String phone= rs.getString("phone");
				String email = rs.getString("email");			
				String gender= rs.getString("gender");
				String roll= rs.getString("roll");
				String address = rs.getString("address");	
				String dob= rs.getString("dob");
				String dateofjoin= rs.getString("dateofjoin");
				model.addRow(new Object[] { empid, name, phone, email, gender,roll,  address, dob, dateofjoin});
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "empid", "Name",  "Phone","Email", "Gender", "Roll", "Address", "Dob", "Date of join"};
		((DefaultTableModel) etable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(etable, 1320, 0, 250, 150, 200, 100, 120,300, 100, 100);
		etable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				etable.scrollRectToVisible(etable.getCellRect(etable.getRowCount() - 1, 0, true));
			}
		});
		if (etable.getRowCount() > 0) {
			int row = etable.getRowCount() - 1;
			etable.setRowSelectionInterval(row, row);		
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
			sql = " where  lower(name) LIKE '%" + ss + "%' "
					+ " order by name " + " COLLATE NOCASE ASC";		
		} else if (jelem == 2) {
			sql = " where phone LIKE '%" + ss + "%'";
		}
		etable_update(sql);
	}
	
	private void clearFields(){
		txname.setText(""); 
		txphone.setText("");
		txemail.setText(""); 
		txroll.setText(""); 
		txdob.setText("");
		txdateofjoin.setText("");
		txaaddress.setText("");		
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Employees();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	CardLayout cards;
	JPanel tPanel, cardPanel, ePanel, searchPanel;
	JLabel lbheader, lbsearch, lbname, lbphone, lbemail, lbgender, lbroll, lbaddress, lbdob,
	lbdateofjoin, lbpicture;
	JTable etable;
	JScrollPane jScrollPane, addrPane;
	JButton btnsearch, btnclear, btnnew, btnupdate, btndelete, btnsave, btncancel;
	JTextField txsearch, txname, txphone, txemail, txroll, txdob, txdateofjoin;
	JTextArea txaaddress;
	JRadioButton rname, rtel;
	ButtonGroup btnGroup = new ButtonGroup();
	JComboBox cmbgender;
}
