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

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.util.Date;
import com.toedter.calendar.JDateChooser;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Hhelper.StringUtils;
import Classes.Spallvalid;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Bus;

public class Destinationsearch extends JFrame {
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
	String sfrom = "";
	JFrame pframe;
	JDateChooser sdate = new JDateChooser(new Date());

	Destinationsearch(JFrame parent, String wfrom) {
		pframe = parent;
		sfrom = wfrom;
		init();
		dd.cmbstartcombofil(cmbstart);
		dd.cmbdestinationcombofil(cmbdestination);
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
		setSize(new java.awt.Dimension(890, 600));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Search destination");
		lbheader = hh.fflabel("Search destination");
		lbheader.setBounds(40, 10, 300, 35);
		add(lbheader);
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 850, 500);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(15, 15, 450, 195);
		ePanel.setBorder(hh.ztroundborder(Color.BLUE));
		ePanel.setOpaque(false);
		basePanel.add(ePanel);

		lbdepdate = hh.clabel("Departure date");
		lbdepdate.setBounds(10, 10, 130, 25);
		ePanel.add(lbdepdate);

		sdate.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		sdate.setDateFormatString("yyyy-MM-dd");
		sdate.setFont(new Font("Arial", Font.BOLD, 16));
		sdate.setBounds(150, 10, 120, 25);
		ePanel.add(sdate);

		lbstart = hh.clabel("Start");
		lbstart.setBounds(10, 50, 120, 25);
		ePanel.add(lbstart);

		cmbstart = gr.grcombo();
		cmbstart.setBounds(140, 50, 270, 30);
		cmbstart.setName("start");
		ePanel.add(cmbstart);

		lbdestination = hh.clabel("Destination");
		lbdestination.setBounds(10, 90, 120, 25);
		ePanel.add(lbdestination);

		cmbdestination = gr.grcombo();
		cmbdestination.setBounds(140, 100, 270, 30);
		cmbstart.setName("destination");
		ePanel.add(cmbdestination);

		btnsearch = gr.sbcs("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(220, 150, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		ePanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillter();
			}
		});

		tPanel = new JPanel(null);
		tPanel.setBounds(15, 215, 810, 270);
		// tPanel.setBorder(hh.ztroundborder(Color.BLUE));
		tPanel.setOpaque(false);
		basePanel.add(tPanel);

		dtable = hh.ztable();
		// dtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		dtable.setTableHeader(new JTableHeader(dtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(dtable);
		jScrollPane1 = new JScrollPane(dtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		dtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] { "schedid", "routeid",
				"Start", "Destination", "busid", "Busnumber", "Departure", "Arrival","Fare" }));
		hh.setJTableColumnsWidth(dtable, 800, 0, 0, 220, 220, 0, 100, 130, 130, 0);
		jScrollPane1.setViewportView(dtable);	
	//	jScrollPane1.setBorder(hh.lineo);
		jScrollPane1.setBounds(10, 15, 800, 210);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);

		btnchoose = gr.sbcs("Choose");
		btnchoose.setForeground(Color.black);
		btnchoose.setBackground(Color.ORANGE);
		btnchoose.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnchoose.setBounds(360, 240, 90, 27);
		btnchoose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		tPanel.add(btnchoose);		
		btnchoose.addActionListener(e -> choose());
		setVisible(true);
	}

	private void fillter() {
		String ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
		String start = (String) cmbstart.getSelectedItem();
		String destination = (String) cmbdestination.getSelectedItem();

		String sql = "select  s.schedid, s.routeid,  r.origin, r.destination, s.busid, "
				+ " b.busno, s.departure_time as deptime, s.arrival_time, fare  from schedule s "
				+ "  left join routes r  on s.routeid = r.routeid " + "  left join buses b  on s.busid = b.busid "
				+ " where substr(deptime,1,10) ='" + ssdate + "'";
		if (!hh.zempty(start)) {
			sql = sql + " and lower(r.origin) LIKE '%" + start + "%'";
		}

		if (!hh.zempty(destination)) {
			sql = sql + " and lower(r.destination) LIKE  '%" + destination + "%'";
		}
		dtable_update(sql);
	}
	
	private void choose() {
		int row = dtable.getSelectedRow();
		if (row >= 0) {
			String schedid= dtable.getValueAt(row, 0).toString();	
			String routeid = dtable.getValueAt(row, 1).toString();
			String busid = dtable.getValueAt(row, 4).toString();
			String fare = dtable.getValueAt(row, 8).toString();
			String origin =  dtable.getValueAt(row, 2).toString();
			String destination =  dtable.getValueAt(row, 3).toString();
			String busno =  dtable.getValueAt(row, 5).toString();
			String deptime =  dtable.getValueAt(row, 6).toString();
			String arrival =  dtable.getValueAt(row,7).toString();
			String sheader = origin+"-"+destination +" " +busno + " " +deptime +" - " + arrival;
			if (sfrom =="passengerhome") {
                   new Seatreservation(schedid, routeid, busid, fare, sheader);   
			} else {
				  new Transit_occupancy(schedid, routeid, busid, sheader); 
			}
             dispose();
		}
	}

	private void dtable_update(String psql) {
		DefaultTableModel model = (DefaultTableModel) dtable.getModel();
		model.setRowCount(0);
		try {
			rs = dh.GetData(psql);
			while (rs.next()) {
				String schedid = rs.getString("schedid");
				String routeid = rs.getString("routeid");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String busid = rs.getString("busid");
				String busno = rs.getString("busno");
				String departure = rs.getString("deptime");
				String arrival = rs.getString("arrival_time");
				String fare = rs.getString("fare");

				model.addRow(new Object[] { schedid, routeid, origin, destination, busid, busno, departure, arrival, fare });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}

		String[] fej = { "schedid", "routeid", "Start", "Destination", "busid", "Busnumber", "Departure", "Arrival","Fare" };
		((DefaultTableModel) dtable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(dtable, 800, 0, 0, 220, 220, 0, 100, 130, 130,0);
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			//		new Destinationsearch();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbdepdate, lbstart, lbdestination;
	JPanel basePanel, ePanel, tPanel;
	JComboBox cmbstart, cmbdestination;
	JButton btnsearch, btnchoose;
	JTable dtable;
	JScrollPane jScrollPane1;
}
