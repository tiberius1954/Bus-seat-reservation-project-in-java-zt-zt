import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import com.toedter.calendar.JDateChooser;

import java.util.ArrayList;
import java.util.Date;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Layout;


public class Buses extends JFrame{
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
	
Buses(){
	init();
	dd.cmblayoutcombofill(cmblayouts);
	bustable_update();
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
	setTitle("Buses");
	lbheader = hh.fflabel("Buses");
	lbheader.setBounds(40, 10, 200, 35);
	add(lbheader);
	
	basePanel = new JPanel(null);
	basePanel.setBounds(40, 50, 1000, 465);
	basePanel.setBorder(hh.ztroundborder(Color.BLUE));
	 basePanel.setOpaque(false);
	add(basePanel);
	
	ePanel = new JPanel(null);
	ePanel.setBounds(15, 30, 400, 400);
	ePanel.setBorder(hh.ztroundborder(Color.BLUE));
	ePanel.setOpaque(false);
	basePanel.add(ePanel);	
	
	tPanel = new JPanel(null);
	tPanel.setBounds(425, 30, 560, 400);
	tPanel.setBorder(hh.ztroundborder(Color.BLUE));
	tPanel.setOpaque(false);
	basePanel.add(tPanel);		

	lbbusno = hh.clabel("Bus number");
	lbbusno.setBounds(10, 30, 120, 30);
	ePanel.add(lbbusno);

	txbusno=gr.gTextField(100);
	txbusno.setBounds(140, 30, 150, 30);
	txbusno.addKeyListener(hh.MUpper());
	ePanel.add(txbusno);	
	
	lbcolor = hh.clabel("Color");
	lbcolor.setBounds(10, 70, 120, 30);
	ePanel.add(lbcolor);

	txcolor =gr.gTextField(100);
	txcolor.setBounds(140, 70, 150, 30);
	txcolor.addKeyListener(hh.MUpper());
	ePanel.add(txcolor);	
	
	lbmodel = hh.clabel("Model");
	lbmodel.setBounds(10, 110, 120, 30);
	ePanel.add(lbmodel);

	txmodel =gr.gTextField(100);
	txmodel.setBounds(140, 110, 150, 30);
	txmodel.addKeyListener(hh.MUpper());
	ePanel.add(txmodel);	
	
	lblayouts = hh.clabel("Layout");
	lblayouts.setBounds(10, 150, 120, 30);
	ePanel.add(lblayouts);
	
	cmblayouts = gr.grcombo();
	cmblayouts.setFont(hh.textf);
	cmblayouts.setBounds(140, 150, 150, 30);
	cmblayouts.setName("layout");
	ePanel.add(cmblayouts);
	
	btnsave = gr.sbcs("Save");
	btnsave.setBounds(160, 220, 100, 30);
	btnsave.setForeground(Color.black);
	btnsave.setBackground(hh.lpiros);
	ePanel.add(btnsave);
	btnsave.addActionListener(e -> savebuttrun());

	btncancel = gr.sbcs("Cancel");
	btncancel.setBounds(160, 260, 100, 30);
	btncancel.setBackground(hh.vvzold);
	ePanel.add(btncancel);
 	btncancel.addActionListener(e -> clearFields());

	btndelete = gr.sbcs("Delete");
	btndelete.setBounds(160, 300, 100, 30);
	btndelete.setForeground(Color.black);
	btndelete.setBackground(Color.yellow);
	ePanel.add(btndelete);
    btndelete.addActionListener(e -> data_delete());
	
	bustable = hh.ztable();

	bustable.setTableHeader(new JTableHeader(bustable.getColumnModel()) {
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.height = 25;
			return d;
		}
	});

	hh.madeheader(bustable);
	bustable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			bustable.scrollRectToVisible(bustable.getCellRect(bustable.getRowCount() - 1, 0, true));
		}
	});
	bustable.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			int row = bustable.getSelectedRow();
			if (row >= 0) {
				rowid = bustable.getValueAt(row, 0).toString();				
				String busno = bustable.getValueAt(row, 1).toString();					
		        txbusno.setText(busno);
				String scolor = bustable.getValueAt(row, 2).toString();
		        txcolor.setText(scolor);
			   String smodel = bustable.getValueAt(row, 3).toString();
			   txmodel.setText(smodel);
				String layid = bustable.getValueAt(row, 4).toString();	
				int ilayid = hh.stoi(layid);
				hh.setSelectedValue(cmblayouts, ilayid);
				cmblayouts.updateUI();		
			//	String capacity = bustable.getValueAt(row, 6).toString();
		         myrow = row;			
			}
		}
	});

	jScrollPane1 = new JScrollPane(bustable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	bustable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
			new String[] { "busid", "Bus number", "Color", "Model","Layid", "Layout","Capacity"}));
	hh.setJTableColumnsWidth(bustable, 520,  0, 110, 110, 110, 0, 110, 80);
	jScrollPane1.setViewportView(bustable);
	jScrollPane1.setBounds(20, 30, 520, 350);
	jScrollPane1.setBorder(hh.borderf);
	tPanel.add(jScrollPane1);	
	setVisible(true);
}

private void savebuttrun() {
	DefaultTableModel d1 = (DefaultTableModel) bustable.getModel();
	String sql ="";
	String jel="";
	int myid;
	String busno = txbusno.getText();
	String scolor = txcolor.getText();
	String smodel = txmodel.getText();	
	int layid = (int) ((Layout) cmblayouts.getSelectedItem()).getBlid();
    String lname =  (String) ((Layout) cmblayouts.getSelectedItem()).getLname();
    String capacity =  (String) ((Layout) cmblayouts.getSelectedItem()).getCapacity();
    
    if (bvalidation(busno, scolor, smodel, layid) == true) {
 		return;
	}
    if (rowid != "") {
		jel = "UP";
		sql = "update  buses set busno= '" + busno + "', color= '" + scolor + "',  model = '" + smodel
				+ "' , layid= " + layid + " where busid = " + rowid;
	} else {
		sql = "insert into buses (busno, color, model, layid) values ( '" + busno + "','"
	           + scolor + "','" + smodel + "'," + layid + ")";
	}
	try {
		int flag = dh.Insupdel(sql);
		if (flag == 1) {
			hh.ztmessage("Success", "Message");
			if (rowid == "") {
				myid = dd.table_maxid("SELECT MAX(busid) AS max_id FROM buses");				
				d1.addRow(new Object[] { myid, busno, scolor, smodel, layid, lname, capacity });
				         hh.gotolastrow(bustable);
				       
			}  else {			
				d1.setValueAt(busno, myrow, 1);
				d1.setValueAt(scolor, myrow, 2);
				d1.setValueAt(smodel, myrow, 3);
				d1.setValueAt(layid, myrow, 4);
				d1.setValueAt(lname, myrow, 5);
				d1.setValueAt(capacity, myrow, 6);				
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

private void data_delete() {
	int flag=0;
	DefaultTableModel model = (DefaultTableModel) bustable.getModel();
	int row = bustable.getSelectedRow();
	if (row >= 0) {
		String sbusid = model.getValueAt(row, 0).toString();	
		if (sbusid.equals("")) {
			return;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {				
			String sql = "delete from buses where busid='" + sbusid + "'";
			flag = dh.Insupdel(sql);					
			if (flag > 0) {
				model.removeRow(row);
				clearFields();
			}			
		}
		}
}

private void clearFields() {
	rowid = "";
	myrow = 0;
	txbusno.setText("");
	txcolor.setText("");
	txmodel.setText("");
	cmblayouts.setSelectedIndex(0);
}

private Boolean bvalidation(String busno, String scolor, String  smodel, int layid){
	Boolean ret = false;
	ArrayList<String> err = new ArrayList<String>();
	
	if(sval.emptyvalid(busno)){
		err.add( "Bus number  is empty !");
		ret = true;
	}
	if (sval.emptyvalid(scolor)) {
		err.add("Color is empty !");
		ret = true;
   }
	if (sval.emptyvalid(smodel)) {
		err.add("Model is empty !");
		ret = true;
	}
	if  (layid == 0) {
		err.add("You don't choose layout !");
		ret = true;
	}		
		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
	return ret;
}


private void bustable_update(){
		DefaultTableModel model = (DefaultTableModel) bustable.getModel();
		model.setRowCount(0);
		String Sql = "select  b.busid, b.busno, b.color, b.model, layid, l.lname, l.capacity  from buses b "
				+ " left join buslayout l  on b.layid = l.blid";
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String busid = rs.getString("busid");
				String busno = rs.getString("busno");
				String scolor = rs.getString("color");
				String smodel= rs.getString("model");
				String layid = rs.getString("layid");
				String lname = rs.getString("lname");
				String capacity = rs.getString("capacity");			   				
					model.addRow(new Object[] { busid, busno, scolor, smodel, layid, lname, capacity});
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "busid", "Bus number", "Color", "Model","Layid", "Layout","Capacity" };
		((DefaultTableModel) bustable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(bustable, 520,  0, 110, 110, 110, 0, 110, 80);
		int[] cols = {  6 };
		hh.cellsright(bustable, cols); 

		bustable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				bustable.scrollRectToVisible(bustable.getCellRect(bustable.getRowCount() - 1, 0, true));
			}
		});
		if (bustable.getRowCount() > 0) {
			int row = bustable.getRowCount() - 1;
			bustable.setRowSelectionInterval(row, row);		
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Buses();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
JPanel basePanel, ePanel, tPanel;
JLabel lbheader,  lbbusno, lbcolor, lbmodel, lblayouts;
JTextField  txbusno,txcolor, txmodel;
JComboBox cmblayouts;
JButton btnsave, btncancel, btndelete;
JTable bustable;
JScrollPane jScrollPane1;
}
