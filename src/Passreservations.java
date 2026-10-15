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


public class Passreservations extends JFrame{
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


	Passreservations(){
		init();
		hh.iconhere(this);
	//	ptable_update("");
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
		setTitle("My reservations");
		lbheader = hh.fflabel("My reservations");
		lbheader.setBounds(40, 10, 300, 35);
		add(lbheader);
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 1060, 500);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);
		
		tPanel = new JPanel(null);
		tPanel.setBounds(60, 30, 940, 440);
		tPanel.setBorder(hh.ztroundborder(Color.BLUE));
		tPanel.setOpaque(false);
		basePanel.add(tPanel);
		
		lbidoszak = hh.clabel("Term");
		lbidoszak.setBounds(205, 30, 70, 25);
		tPanel.add(lbidoszak);
		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(280, 30, 120, 30);
		tPanel.add(sdate);
		lbjel = hh.clabel(" - ");	
		lbjel.setBounds(400, 30, 20, 30);
		tPanel.add(lbjel);

		vdate.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		vdate.setDateFormatString("yyyy-MM-dd");
		vdate.setFont(new Font("Arial", Font.BOLD, 16));
		vdate.setBounds(420, 30, 120, 30);
		tPanel.add(vdate);

		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(hh.skek);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBounds(545, 30, 90, 30);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		tPanel.add(btnsearch);
		btnsearch.addActionListener((ActionListener) new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		
		ptable = hh.ztable();	
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

		jScrollPane1 = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		ptable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Start", "Destination", "Departure_time", "Seatno","Fare","Date of res.", "Status"}));
		hh.setJTableColumnsWidth(ptable, 900,  200, 200, 120, 60, 100,100, 80);
		int[] cols = { 3,4 };
		hh.cellsright(ptable, cols); 
		jScrollPane1.setViewportView(ptable);
		jScrollPane1.setBounds(20, 80, 900, 330);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);	
		setVisible(true);
	}	
	
	private void ptable_update(String swhere){
		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		model.setRowCount(0);
		String Sql = "select  ro.origin, ro.destination, substr(sc.departure_time, 0, 11) as deptime, "
				+ " se.seatno, se.fare, se.resdate, se.status from seatreservation se "
				+ " join schedule sc on sc.schedid=se.schedid "
				+ " join routes ro on ro.routeid=sc.routeid "
				+ swhere;
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String deptime = rs.getString("deptime");
				String seatno= rs.getString("seatno");
				String fare = rs.getString("fare");
				String resdate= rs.getString("resdate");	
				String status= rs.getString("status");
				model.addRow(new Object[] { origin, destination, deptime, seatno, fare, resdate, status});
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = {  "Start", "Destination", "Departure_time", "Seatno","Fare","Date of res.","Status"};
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(ptable, 900,  200, 200, 140, 60, 100,100, 80);
		int[] cols = { 3,4 };
		hh.cellsright(ptable, cols); 
		
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
		// dh.mypassid = "2";
		ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
		vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();
		String swhere = " where passid='" + dh.mypassid 
				+ "' and date(deptime) >= date('" + ssdate + "') and date(deptime) <= date('" + vvdate + "')";
				
		ptable_update(swhere);
		return;

	}
	
	public static void main(String args[]) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				new Passreservations();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}
JLabel lbheader, lbidoszak, lbjel;
JPanel basePanel, tPanel;
JTable ptable;
JScrollPane jScrollPane1;
JButton btnsearch;
}
