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


public class Transit_occupancy extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
	DefaultTableModel model;
	String rowid = "";
	int myrow = 0;
	JFrame myframe = this;
	String myschedid = "";
	String myrouteid = "";
	String mybusid = "";
	String myfare = "";
	String mylayid = "";
	String nowdate = hh.currentDate();
	int myrows = 0;
	int mycols = 0;
	int mycapacity = 0;
	int mycount=0;	
	String aktheader;	

	Transit_occupancy(String schedid, String routeid, String busid, String pheader) {
		myschedid = schedid;
		myrouteid = routeid;
		mybusid = busid;
		aktheader = pheader;
		otherparam(busid);
		init();
		otable_update();
		myresult();
		hh.iconhere(this);
	}

	Transit_occupancy() {
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
		setSize(new java.awt.Dimension(1100, 660));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Transit occupancy");
		lbheader = hh.fflabel("Transit occupancy");
		lbheader.setBounds(40, 10, 250, 35);		
		add(lbheader);
		
		lbheader1 = hh.clabel(aktheader);
		lbheader1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		lbheader1.setBounds(330, 15, 720, 35);
		add(lbheader1);
		
		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 1060, 560);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);

		seatPanel = new JPanel(null);
		seatPanel.setBounds(10, 10, 280, 510);
		seatPanel.setBorder(hh.ztroundborder(Color.BLUE));
		seatPanel.setOpaque(false);
		basePanel.add(seatPanel);

		tbuttons = new JLabel[myrows][mycols];
		arr = new String[myrows][mycols];
		String sql = "select * from busgrid where glid='" + mylayid + "'";
		ResultSet rs = dh.GetData(sql);
		int k = 0;
		try {
			while (rs.next()) {
				arr[k][0] = rs.getString("col1");
				arr[k][1] = rs.getString("col2");
				arr[k][2] = rs.getString("col3");
				arr[k][3] = rs.getString("col4");
				arr[k][4] = rs.getString("col5");
				if (mycols > 5) {
					arr[k][5] = rs.getString("col6");
				}
				k++;
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int x = 10;
		int y = 10;
		for (int i = 0; i < myrows; i++) {
			for (int j = 0; j < mycols; j++) {
				String title = arr[i][j];
				if (!hh.zempty(title)) {
					tbuttons[i][j] = new JLabel();
					tbuttons[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
					tbuttons[i][j].setText(title);
					tbuttons[i][j].setBounds(x, y, 35, 30);
					tbuttons[i][j].setBorder(hh.borderz);
					tbuttons[i][j].setFont(new Font("Tahoma", Font.BOLD, 14));
					// sn = title;
					tbuttons[i][j].setName(title);
					tbuttons[i][j].setOpaque(true);
					tbuttons[i][j].setBackground(hh.vvzold);
					tbuttons[i][j].setFocusable(false);
					seatPanel.add(tbuttons[i][j]);
				}
				x = x + 40;
			}
			y = y + 35;
			x = 10;
		}
		enablequestion();

		tPanel = new JPanel(null);
		tPanel.setBounds(310, 10, 740, 510);
		tPanel.setBorder(hh.ztroundborder(Color.BLUE));
		tPanel.setOpaque(false);
		basePanel.add(tPanel);

		otable = hh.ztable();

		otable.setTableHeader(new JTableHeader(otable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(otable);
		otable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				otable.scrollRectToVisible(otable.getCellRect(otable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane1 = new JScrollPane(otable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		otable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Seatno", "Passenger name", "Phone", "Status", "Reservation date" }));
		hh.setJTableColumnsWidth(otable, 700, 70, 250, 170, 80, 130);
		jScrollPane1.setViewportView(otable);
		jScrollPane1.setBounds(20, 30, 700, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);

		lbcapacity = hh.clabel("Bus capacity");
		lbcapacity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbcapacity.setBounds(50, 400, 140, 30);
		tPanel.add(lbcapacity);

		lbbookedseats = hh.clabel("Booked seats");
		lbbookedseats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbbookedseats.setBounds(220, 400, 140, 30);
		tPanel.add(lbbookedseats);

		lbbookedpercent = hh.clabel("Booked seats %");
		lbbookedpercent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbbookedpercent.setBounds(390, 400, 140, 30);
		tPanel.add(lbbookedpercent);

		lbfreeseats = hh.clabel("Free seats");
		lbfreeseats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbfreeseats.setBounds(540, 400, 140, 30);
		tPanel.add(lbfreeseats);
		

		txcapacity = hh.cTextField(100);
		txcapacity.setEditable(false);
		txcapacity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		txcapacity.setBounds(50, 440, 140, 30);
		tPanel.add(txcapacity);		

		txbookedseats = hh.cTextField(100);
		txbookedseats.setEditable(false);
		txbookedseats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		txbookedseats.setBounds(220, 440, 140, 30);
		tPanel.add(txbookedseats);

		txbookedpercent = hh.cTextField(100);
		txbookedpercent.setEditable(false);
		txbookedpercent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		txbookedpercent.setBounds(390, 440, 140, 30);
		tPanel.add(txbookedpercent);

		txfreeseats = hh.cTextField(100);
		txfreeseats.setEditable(false);
		txfreeseats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		txfreeseats.setBounds(540, 440, 140, 30);
		tPanel.add(txfreeseats);
		setVisible(true);
	}

	private void otable_update() {
		DefaultTableModel model = (DefaultTableModel) otable.getModel();
		model.setRowCount(0);
		String Sql = "select se.seatno, p.pname, p.phone, se.status, se.resdate from seatreservation se "
				+ " join passenger p on p.passid = se.passid " + " where se.schedid='" + myschedid
				+ "'  order by  printf('%02d', seatno) ";

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String seatno = rs.getString("seatno");
				String pname = rs.getString("pname");
				String phone = rs.getString("phone");
				String status = rs.getString("status");
				String resdate = rs.getString("resdate");
				model.addRow(new Object[] { seatno, pname, phone, status, resdate });
			}
			mycount= model.getRowCount();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "Seatno", "Passenger name", "Phone", "Status", "Reservation date" };
		((DefaultTableModel) otable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(otable, 700, 70, 250, 170, 80, 130);

		otable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				otable.scrollRectToVisible(otable.getCellRect(otable.getRowCount() - 1, 0, true));
			}
		});
		if (otable.getRowCount() > 0) {
			int row = otable.getRowCount() - 1;
			otable.setRowSelectionInterval(row, row);
		}
	}

	private void enablequestion() {
		String seatno;
		String Sql = "select seatno from seatreservation  where schedid= " + myschedid;
		ArrayList<String> List = new ArrayList<>();
		try {
			ResultSet rs = dh.GetData(Sql);
			while (rs.next()) {
				String ss = rs.getString("seatno");
				seatno = ss.trim();
				List.add(seatno);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		int size = List.size();
		if (size > 0) {
			for (int i = 0; i < myrows; i++) {
				for (int j = 0; j < mycols; j++) {
					if (tbuttons[i][j] != null) {
						String se = tbuttons[i][j].getName();
						seatno = se.trim();
						if (List.contains(seatno)) {
							tbuttons[i][j].setForeground(hh.piros);
						}
					}
				}
			}
		}
	}	

	private void otherparam(String pbusid) {
		try {
			String sql = "select * from buses where busid ='" + pbusid + "'";
			rs = dh.GetData(sql);
			while (rs.next()) {
				mylayid = rs.getString("layid");
			}
			sql = "select * from buslayout where blid ='" + mylayid + "'";
			rs = dh.GetData(sql);
			while (rs.next()) {
				myrows = rs.getInt("rows");
				mycols = rs.getInt("columns");
				mycapacity = rs.getInt("capacity");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String schedid = "3";
					String routeid = "1";
					String busid = "1";
					String fare = "1000";
					String header = "header";
					new Transit_occupancy(schedid, routeid, busid, header);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void myresult() {
		int in=0;
		txcapacity.setText(hh.itos(mycapacity));		
		txbookedseats.setText(hh.itos(mycount));
		in = (mycount*100)/mycapacity;
		txbookedpercent.setText(hh.itos(in));
		in = mycapacity - mycount;
		txfreeseats.setText(hh.itos(in));		
	}

	JLabel lbheader, lbidoszak, lbjel, lbbookedseats, lbcapacity, lbbookedpercent, lbfreeseats, lbheader1;
	JTextField txbookedseats, txcapacity, txbookedpercent, txfreeseats;
	JPanel basePanel, seatPanel, tPanel;
	JTable otable;
	JScrollPane jScrollPane1;
	JButton btnsearch;
	JLabel tbuttons[][];
	String[][] arr;
}
