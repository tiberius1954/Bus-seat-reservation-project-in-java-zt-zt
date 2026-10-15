import java.awt.*;
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
import Databaseop.Databaseop.Bus;

public class Adminusers extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();	
	Allvalid allv = new Allvalid();
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
	Bus bus;
	String rowid = "";
	int myrow = 0;
	JFrame myframe = this;	
	Adminusers(){
		init();
		atable_update("");
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
		setTitle("Adminusers");
		lbheader = hh.fflabel("Adminusers");
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
		
		lbname = hh.clabel("Username");
		lbname.setBounds(30, 30, 100, 30);
		ePanel.add(lbname);

		txusername=gr.gTextField(100);
		txusername.setBounds(140, 30, 200, 30);		
		ePanel.add(txusername);	
		
		lbpassword = hh.clabel("Password");
		lbpassword.setBounds(30, 80, 100, 30);
		ePanel.add(lbpassword);

		txpassword=gr.gpasstField(100);
		txpassword.setBounds(140, 80, 200, 30);	
		ePanel.add(txpassword);	
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(30, 130, 100, 30);
		ePanel.add(lbphone);

		txphone=gr.gTextField(100);
		txphone.setBounds(140, 130, 200, 30);	
		ePanel.add(txphone);	
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(190, 200, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);
       btnsave.addActionListener(e -> save_data());
		
		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(190, 240, 100, 30);
		btncancel.setBackground(hh.vvzold);
		ePanel.add(btncancel);
	 	btncancel.addActionListener(e -> clearFields());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(190, 280, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
         btndelete.addActionListener(e -> data_delete());
		
		atable = hh.ztable();	
		atable.setTableHeader(new JTableHeader(atable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(atable);
		atable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				atable.scrollRectToVisible(atable.getCellRect(atable.getRowCount() - 1, 0, true));
			}
		});
		
		atable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = atable.getSelectedRow();
				if (row >= 0) {
					rowid = atable.getValueAt(row, 0).toString();				
					String username = atable.getValueAt(row, 1).toString();					
			        txusername.setText(username);
			    	String password = atable.getValueAt(row, 2).toString();
					txpassword.setText(password);            	
			        String phone = atable.getValueAt(row, 3).toString();
				   txphone.setText(phone);				
			        myrow = row;				    
				}
			}
		});	

		jScrollPane1 = new JScrollPane(atable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		atable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "adminid", "Username", "Password", "Phone"}));
		hh.setJTableColumnsWidth(atable, 620,  0, 200, 200, 200);
		jScrollPane1.setViewportView(atable);
		jScrollPane1.setBounds(15, 20, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);				
		setVisible(true);			
	}
	
	private void save_data(){
		DefaultTableModel d1 = (DefaultTableModel) atable.getModel();
		String sql ="";	
		int myid;	
		String username = txusername.getText();
		String phone = txphone.getText();	
		String password = String.valueOf(txpassword.getPassword());	
	    
	    if (avalidation(username, password, phone) == true) {
	 		return;
		}
	    if (rowid != "") {		
			sql = "update adminuser set username= '"+ username + "', password ='"
				 + password + "' , phone= '" + phone + "' where adid = " + rowid;
		} else {
			sql = "insert into adminuser (username, password, phone) values ( '"+ username
             + "','" + password+ "'," + phone + ")";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(adid) AS max_id FROM adminuser");				
					d1.addRow(new Object[] { myid, username, password, phone});
					         hh.gotolastrow(atable);
					       
				}  else {			
					d1.setValueAt(username, myrow, 1);
					d1.setValueAt(password, myrow, 2);
					d1.setValueAt(phone, myrow, 3);								
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
	}
	private Boolean avalidation(String username, String password, String phone){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();		
		if(sval.emptyvalid(username)){
			err.add( "Userame  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(password)){
			err.add( "Paasword  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(phone)){
			err.add( "Phone  is empty !");
			ret = true;
		}
		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
	
	return ret;
	}
	private void clearFields(){
		txusername.setText("");
		txpassword.setText("");
		txphone.setText("");	
		rowid = "";
		myrow = 0;
	}
	private void data_delete() {
		int flag=0;
		DefaultTableModel model = (DefaultTableModel) atable.getModel();
		int row = atable.getSelectedRow();
		if (row >= 0) {
			String sadid = model.getValueAt(row, 0).toString();	
			if (sadid.equals("")) {
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
			if (a == JOptionPane.YES_OPTION) {				
				String sql = "delete from adminuser where adid='" + sadid + "'";
				flag = dh.Insupdel(sql);					
				if (flag > 0) {
					model.removeRow(row);
					clearFields();
				}			
			}
			}
	}
	private void atable_update(String swhere){
		DefaultTableModel model = (DefaultTableModel) atable.getModel();
		model.setRowCount(0);
		String Sql = "select  adid, username, password, phone"
				+ " from adminuser " + swhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String adid = rs.getString("adid");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phone= rs.getString("phone");
					
				model.addRow(new Object[] { adid, username, password, phone});
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = {  "adminid", "Username", "Password", "Phone"};
		((DefaultTableModel) atable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(atable, 620,  0, 200, 200, 200);
		atable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				atable.scrollRectToVisible(atable.getCellRect(atable.getRowCount() - 1, 0, true));
			}
		});
		if (atable.getRowCount() > 0) {
			int row = atable.getRowCount() - 1;
			atable.setRowSelectionInterval(row, row);		
		}
	}
	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Adminusers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader, lbname, lbpassword, lbphone;
	JPanel basePanel, ePanel, tPanel;
    JTextField  txusername, txphone;  
    JPasswordField txpassword;
    JButton btnsave, btncancel, btndelete;
    JTable atable;
    JScrollPane jScrollPane1;
}
