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
import Databaseop.Databaseop.Layout;

public class Drivers extends JFrame{
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
	
	Drivers(){
		init();
		dtable_update("");
		dd.cmbbusescombofill(cmbbuses);
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
		setTitle("Drivers");
		lbheader = hh.fflabel("Drivers");
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
		
		lbname = hh.clabel("Name");
		lbname.setBounds(0, 30, 140, 30);
		ePanel.add(lbname);

		txname=gr.gTextField(100);
		txname.setBounds(150, 30, 200, 30);
		txname.addKeyListener(hh.MUpper());
		ePanel.add(txname);	
		
		lblicense = hh.clabel("License number");
		lblicense.setBounds(0, 80, 140, 30);
		ePanel.add(lblicense);

		txlicense=gr.gTextField(100);
		txlicense.setBounds(150, 80, 200, 30);
		txlicense.addKeyListener(hh.MUpper());
		ePanel.add(txlicense);	
		
		lbphone = hh.clabel("Phone");
		lbphone.setBounds(0, 130, 140, 30);
		ePanel.add(lbphone);

		txphone=gr.gTextField(100);
		txphone.setBounds(150, 130, 200, 30);	
		ePanel.add(txphone);	
		
		lbbus= hh.clabel("Bus");
		lbbus.setBounds(0, 190, 140, 30);
		ePanel.add(lbbus);		
		
		cmbbuses = gr.grcombo();		
		cmbbuses.setBounds(150, 190, 200, 30);
		cmbbuses.setName("bus");
		ePanel.add(cmbbuses);
		
		btnsave = gr.sbcs("Save");
		btnsave.setBounds(200, 250, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);
       btnsave.addActionListener(e -> save_data());
		
		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(200, 290, 100, 30);
		btncancel.setBackground(hh.vvzold);
		ePanel.add(btncancel);
	 	btncancel.addActionListener(e -> clearFields());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(200, 330, 100, 30);
		btndelete.setForeground(Color.black);
		btndelete.setBackground(Color.yellow);
		ePanel.add(btndelete);
        btndelete.addActionListener(e -> data_delete());
		
		dtable = hh.ztable();	
		dtable.setTableHeader(new JTableHeader(dtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(dtable);
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		
		dtable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = dtable.getSelectedRow();
				if (row >= 0) {
					rowid = dtable.getValueAt(row, 0).toString();				
					String name = dtable.getValueAt(row, 1).toString();					
			        txname.setText(name);
					String license = dtable.getValueAt(row, 2).toString();
			        txlicense.setText(license);
			        String phone = dtable.getValueAt(row, 3).toString();
				   txphone.setText(phone);				
            		String busid = dtable.getValueAt(row, 4).toString();	
    				int ibusid = hh.stoi(busid);
    				hh.setSelectedValue(cmbbuses, ibusid);
    				cmbbuses.updateUI();		
			        myrow = row;				    
				}
			}
		});	

		jScrollPane1 = new JScrollPane(dtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Driver ID", "Name", "License number", "Phone","Busid","Bus number"}));
		hh.setJTableColumnsWidth(dtable, 620,  0, 200, 160, 120, 0, 120);
		jScrollPane1.setViewportView(dtable);
		jScrollPane1.setBounds(15, 20, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);				
		setVisible(true);		
	}
	
	private void save_data(){
	DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
	String sql ="";	
	int myid;	
	String name = txname.getText();
	String license = txlicense.getText();	
	int busid = (int) ((Bus) cmbbuses.getSelectedItem()).getBusid();
    String busno =  (String) ((Bus) cmbbuses.getSelectedItem()).getBusno();
    String phone = txphone.getText();
    
    if (dvalidation(name, license, phone) == true) {
 		return;
	}
    if (rowid != "") {		
		sql = "update drivers set busid= '" + busid + "', name= '" + name + "',  license_number = '" 
       + license + "' , phone= '" + phone + "' where driverid = " + rowid;
	} else {
		sql = "insert into drivers (busid, name, license_number, phone) values ( '" + busid + "','"
	           + name + "','" + license + "', '" + phone + "')";
	}
	try {
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			hh.ztmessage("Success", "Message");
			if (rowid == "") {
				myid = dd.table_maxid("SELECT MAX(driverid) AS max_id FROM drivers");				
				d1.addRow(new Object[] { myid, name, license, phone, busid, busno });
				         hh.gotolastrow(dtable);
				       
			}  else {			
				d1.setValueAt(name, myrow, 1);
				d1.setValueAt(license, myrow, 2);
				d1.setValueAt(phone, myrow, 3);
				d1.setValueAt(busid, myrow, 4);
				d1.setValueAt(busno, myrow, 5);					
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
	private Boolean dvalidation(String name, String license, String phone){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();		
		if(sval.emptyvalid(name)){
			err.add( "Name  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(license)){
			err.add( "License number  is empty !");
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
	private void data_delete() {
		int flag=0;
		DefaultTableModel model = (DefaultTableModel) dtable.getModel();
		int row = dtable.getSelectedRow();
		if (row >= 0) {
			String sdriverid = model.getValueAt(row, 0).toString();	
			if (sdriverid.equals("")) {
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
			if (a == JOptionPane.YES_OPTION) {				
				String sql = "delete from drivers where driverid='" + sdriverid + "'";
				flag = dh.Insupdel(sql);					
				if (flag > 0) {
					model.removeRow(row);
					clearFields();
				}			
			}
			}
	}
	
	private void clearFields(){
		txname.setText("");
		txlicense.setText("");
		txphone.setText("");
		cmbbuses.setSelectedIndex(0);
		rowid = "";
		myrow = 0;
	}

	private void dtable_update(String swhere){
		DefaultTableModel model = (DefaultTableModel) dtable.getModel();
		model.setRowCount(0);
		String Sql = "select  d.driverid, d.name, d.license_number, d.phone, d.busid, b.busno"
				+ " from drivers d  left join buses b  on d.busid = b.busid " + swhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String driverid = rs.getString("driverid");
				String name = rs.getString("name");
				String license = rs.getString("license_number");
				String phone= rs.getString("phone");
				String busid = rs.getString("busid");
				String busno= rs.getString("busno");			
				model.addRow(new Object[] { driverid, name, license, phone, busid, busno});
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "Driver ID", "Name", "License number", "Phone","busid","Busno"};
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(dtable, 620,  0, 200, 160, 120, 0, 120);
		dtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				dtable.scrollRectToVisible(dtable.getCellRect(dtable.getRowCount() - 1, 0, true));
			}
		});
		if (dtable.getRowCount() > 0) {
			int row = dtable.getRowCount() - 1;
			dtable.setRowSelectionInterval(row, row);		
		}
	}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Drivers();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lbheader, lbname, lblicense, lbphone, lbbus;
	JPanel basePanel, ePanel, tPanel;
    JTextField  txname, txlicense, txphone;
    JComboBox cmbbuses;
    JButton btnsave, btncancel, btndelete;
    JTable dtable;
    JScrollPane jScrollPane1;
}
