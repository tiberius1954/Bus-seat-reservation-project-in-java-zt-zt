import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.toedter.calendar.JDateChooser;
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
import java.util.Date;


public class Admin_view_reservations extends JFrame {
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
	JDateChooser sdate = new JDateChooser(new Date());
	JDateChooser vdate = new JDateChooser(new Date());
	String ssdate, vvdate;

Admin_view_reservations(){
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
	setSize(new java.awt.Dimension(1100, 600));
	setLocationRelativeTo(null);
	this.getContentPane().setBackground(hh.basecolor1);
	setTitle("Admin passenger reservations view");
	lbheader = hh.fflabel("Admin passenger reservations view");
	lbheader.setBounds(40, 10, 450, 35);
	add(lbheader);
	basePanel = new JPanel(null);
	basePanel.setBounds(10, 50, 1060, 500);
	basePanel.setBorder(hh.ztroundborder(Color.BLUE));
	basePanel.setOpaque(false);
	add(basePanel);
	
	tPanel = new JPanel(null);
	tPanel.setBounds(20, 30, 1020, 440);
	tPanel.setBorder(hh.ztroundborder(Color.BLUE));
	tPanel.setOpaque(false);
	basePanel.add(tPanel);
	
	searchPanel = new JPanel(null);
	searchPanel.setBounds(15, 10, 990, 45);
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
		    vtable_update("");
		}
	});	

	lbidoszak = hh.clabel("Term");
	lbidoszak.setBounds(500, 10, 45, 25);
	searchPanel.add(lbidoszak);
	
	sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
	sdate.setDateFormatString("yyyy-MM-dd");
	sdate.setFont(new Font("Arial", Font.BOLD, 16));
	sdate.setBounds(555, 10, 120, 25);
	searchPanel.add(sdate);
	lbjel = hh.clabel(" - ");	
	lbjel.setBounds(675, 10, 20, 25);
	searchPanel.add(lbjel);

	vdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
	vdate.setDateFormatString("yyyy-MM-dd");
	vdate.setFont(new Font("Arial", Font.BOLD, 16));
	vdate.setBounds(700, 10, 120, 25);
	searchPanel.add(vdate);
	
	btnsearch = hh.cbutton("Filter");
	btnsearch.setForeground(Color.black);
	btnsearch.setBackground(Color.ORANGE);
	btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
	btnsearch.setBounds(830, 10, 90, 27);
	btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
	searchPanel.add(btnsearch);
	btnsearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	  	sqlgyart();
		}
	});
	tPanel.add(searchPanel);
	rname.setSelected(true);
	
	vtable = hh.ztable();	
	vtable.setTableHeader(new JTableHeader(vtable.getColumnModel()) {
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.height = 25;
			return d;
		}
	});
	
	hh.madeheader(vtable);
	vtable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			vtable.scrollRectToVisible(vtable.getCellRect(vtable.getRowCount() - 1, 0, true));
		}
	});	

	jScrollPane1 = new JScrollPane(vtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	vtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
			new String[] {"Passenger", "Start", "Destination", "Departure_time", "Seatno","Fare","Date of reservation"}));
	hh.setJTableColumnsWidth(vtable, 990, 170, 200, 200, 130, 60, 80,140);
	int[] cols = { 4,5 };
	hh.cellsright(vtable, cols); 
	jScrollPane1.setViewportView(vtable);
	jScrollPane1.setBounds(15, 80, 990, 330);
	jScrollPane1.setBorder(hh.borderf);
	tPanel.add(jScrollPane1);	
	setVisible(true);
}	

private void vtable_update(String swhere){
	DefaultTableModel model = (DefaultTableModel) vtable.getModel();
	model.setRowCount(0);
	String Sql = "select p.pname, ro.origin, ro.destination, substr(sc.departure_time, 0, 11) as deptime, "
			+ " se.seatno, se.fare, se.resdate from seatreservation se "
			+ " join schedule sc on sc.schedid=se.schedid "
			+ " join routes ro on ro.routeid=sc.routeid "
			+ " join passenger p on p.passid=se.passid "
			+ swhere;
	try {
		rs = dh.GetData(Sql);
		while (rs.next()) {
			String pname = rs.getString("pname");
			String origin = rs.getString("origin");
			String destination = rs.getString("destination");
			String deptime = rs.getString("deptime");
			String seatno= rs.getString("seatno");
			String fare = rs.getString("fare");
			String resdate= rs.getString("resdate");			
			model.addRow(new Object[] { pname, origin, destination, deptime, seatno, fare, resdate});
		}	
	
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		dh.CloseConnection();
	}
	String[] fej = {  "Passenger", "Start", "Destination", "Departure_time", "Seatno","Fare","Date of reservation"};
	((DefaultTableModel) vtable.getModel()).setColumnIdentifiers(fej);	
	hh.setJTableColumnsWidth(vtable, 990, 170, 200, 200, 130, 60, 80,140);
	int[] cols = { 4,5 };
	hh.cellsright(vtable, cols); 
	
	vtable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			vtable.scrollRectToVisible(vtable.getCellRect(vtable.getRowCount() - 1, 0, true));
		}
	});
	if (vtable.getRowCount() > 0) {
		int row = vtable.getRowCount() - 1;
		vtable.setRowSelectionInterval(row, row);		
	}
}
private void sqlgyart() {

	ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
	vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();
//	String swhere = " where resdate >= date('" + ssdate + "') and resdate <=" + " date('" + vvdate + "')";
	String swhere =" where date(deptime) >= date('" + ssdate + "') and date(deptime) <= date('" + vvdate + "')";
	int jelem = 0;
	if (rname.isSelected()) {
		jelem = 1;	
	} else if (rtel.isSelected()) {
		jelem = 2;
	} else {
		String qual = "Did not choose !";
		JOptionPane.showMessageDialog(null, qual);
	}
	if (jelem < 1) {
		return;
	}
	String sql = "";
	String ss = txsearch.getText().trim().toLowerCase();
    if (!hh.zempty(ss)) {
	if (jelem == 1) {
		sql = " lower(pname) LIKE '%" + ss + "%' order by pname " + " COLLATE NOCASE ASC";
		swhere = swhere + " and " +sql;
	} else if (jelem == 2) {
		sql = " phone LIKE '%" + ss + "%' order by phone COLLATE NOCASE ASC";
		swhere = swhere + " and " +sql;
	}	
    }
	
	vtable_update(swhere);
	return;

}
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Admin_view_reservations();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
JLabel  lbheader, lbjel, lbidoszak, lbsearch;
JPanel basePanel, tPanel, searchPanel;
JButton btnsearch, btnclear;
JRadioButton rname, rtel;
ButtonGroup btnGroup = new ButtonGroup();
JTable vtable;
JTextField txsearch;
JScrollPane jScrollPane1;
}
