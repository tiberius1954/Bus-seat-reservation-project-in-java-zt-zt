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
import Databaseop.Databaseop.Driver;
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
import Databaseop.Databaseop.Route;

public class Schedules extends JFrame {
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

	Schedules() {
		init();
		dd.cmbbusescombofill(cmbbuses);
		dd.cmbroutescombofill(cmbroutes);
		dd.cmbdrivercombofill(cmbdrivers);
		 stable_update("");
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
		setTitle("Schedules");
		lbheader = hh.fflabel("Schedules");
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

		lbroute = hh.clabel("Route");
		lbroute.setBounds(0, 20, 90, 30);
		ePanel.add(lbroute);

		cmbroutes = gr.grcombo();
		cmbroutes.setBounds(100, 20, 270, 30);
		cmbroutes.setName("route");
		ePanel.add(cmbroutes);

		lbbus = hh.clabel("Bus");
		lbbus.setBounds(0, 70, 90, 30);
		ePanel.add(lbbus);

		cmbbuses = gr.grcombo();
		cmbbuses.setBounds(100, 70, 200, 30);
		cmbbuses.setName("bus");
		ePanel.add(cmbbuses);	
		
		cmbbuses.addActionListener(new ActionListener() { 
			int mydriverid=0;
			@Override            
			public void actionPerformed(ActionEvent e) { 
				JComboBox comboBox = (JComboBox) e.getSource();			 
			    int busid = (int) ((Bus) cmbbuses.getSelectedItem()).getBusid();
				if (busid > 0 && hh.zempty(rowid)){
					String sql= "select driverid from drivers where busid='"+busid+"'";
					try {
						rs = dh.GetData(sql);
						if (!rs.next()) {
							System.out.println("Error.");
						} else {
							mydriverid = rs.getInt("driverid");
							hh.setSelectedValue(cmbdrivers, mydriverid);
		    				cmbdrivers.updateUI();
						}
						dh.CloseConnection();
					
					} catch (SQLException ex) {
						System.err.println("SQLException: " + ex.getMessage());
						ex.printStackTrace();
					}
				
				}
			}				
});

		lbdeparture = hh.clabel("Departure");
		lbdeparture.setBounds(0, 120, 90, 30);
		ePanel.add(lbdeparture);

		txdeparture = hh.DatetextField();
		txdeparture.setBounds(100, 120, 105, 30);
		ePanel.add(txdeparture);

		txdeptime = hh.TimetextField();
		txdeptime.setBounds(220, 120, 60, 30);
		ePanel.add(txdeptime);

		lbarrival = hh.clabel("Arrival");
		lbarrival.setBounds(10, 170, 90, 30);
		ePanel.add(lbarrival);

		txarrival = hh.DatetextField();
		txarrival.setBounds(110, 170, 105, 30);
		ePanel.add(txarrival);

		txarrtime = hh.TimetextField();
		txarrtime.setBounds(220, 170, 60, 30);		
		ePanel.add(txarrtime);
		
		lbfare = hh.clabel("Fare");
		lbfare.setBounds(10, 220, 90, 30);
		ePanel.add(lbfare);
		
		txfare =hh. cTextField(100);
		txfare.setBounds(110, 220, 170, 30);
		txfare.setHorizontalAlignment(JTextField.RIGHT);
		ePanel.add(txfare);
		
		lbdriver = hh.clabel("Driver");
		lbdriver.setBounds(0,270, 90, 30);
		ePanel.add(lbdriver);

		cmbdrivers = gr.grcombo();
		cmbdrivers.setBounds(100, 270, 200, 30);
		cmbdrivers.setName("driver");
		ePanel.add(cmbdrivers);
		

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(160, 320, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);
		btnsave.addActionListener(e -> save_data());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(160, 360, 100, 30);
		btncancel.setBackground(hh.vvzold);
		ePanel.add(btncancel);
       btncancel.addActionListener(e -> clearFields());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(160, 400, 100, 30);
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
		rroute = new JRadioButton("Route");
		rroute.setHorizontalTextPosition(SwingConstants.LEFT);
		rroute.setActionCommand("route");
		rroute.setBounds(95, 10, 80, 25);
		rroute.setFont(new Font("Tahoma", Font.BOLD, 16));
		rroute.setBackground(Color.ORANGE);
		btnGroup.add(rroute);

		rbus = new JRadioButton("Bus");
		rbus.setHorizontalTextPosition(SwingConstants.LEFT);
		rbus.setActionCommand("bus");
		rbus.setBounds(180, 10, 80, 25);
		rbus.setFont(new Font("Tahoma", Font.BOLD, 16));
		rbus.setBackground(Color.ORANGE);
		btnGroup.add(rbus);
		searchPanel.add(rroute);
		searchPanel.add(rbus);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(270, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(471, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
			    stable_update("");
			}
		});
	
		btnsearch = gr.sbcs("Filter");
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
		rroute.setSelected(true);
		
		stable = hh.ztable();
		stable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		stable.setTableHeader(new JTableHeader(stable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(stable);
		stable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stable.scrollRectToVisible(stable.getCellRect(stable.getRowCount() - 1, 0, true));
			}
		});

		stable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = stable.getSelectedRow();
				if (row >= 0) {
					rowid = stable.getValueAt(row, 0).toString();				
					String sdep = stable.getValueAt(row, 5).toString();	
					 String ss = sdep.substring(0,10);
					 txdeparture.setText(ss);	
					 ss = sdep.substring(11);
					 txdeptime.setText(ss);
					 sdep = stable.getValueAt(row, 6).toString();
					txarrival.setText(sdep.substring(0,10));	
					txarrtime.setText(sdep.substring(11));			
					txfare.setText(stable.getValueAt(row, 7).toString());	
					String busid = stable.getValueAt(row, 3).toString();	
    				int ibusid = hh.stoi(busid);
    				hh.setSelectedValue(cmbbuses, ibusid);
    				cmbbuses.updateUI();	
    				String driverid = stable.getValueAt(row, 8).toString();	
    				int idriverid = hh.stoi(driverid);
    				hh.setSelectedValue(cmbdrivers, idriverid);
    				cmbdrivers.updateUI();
    				String routeid = stable.getValueAt(row, 1).toString();	
    				int irouteid = hh.stoi(routeid);
    				hh.setSelectedValue(cmbroutes, irouteid);
    				cmbroutes.updateUI();	
					myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(stable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		stable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "schedid", "routeid", "Route", "busid", "Busnumber", "Departure" ,"Arrival",
						"Fare","driverid", "Driver Name"}));
		hh.setJTableColumnsWidth(stable, 1000, 0, 0, 300, 0, 100, 110, 110, 100, 0, 200);
		int[] cols = {7 };
		hh.cellsright(stable, cols); 
		jScrollPane1.setViewportView(stable);
		jScrollPane1.setBounds(15, 80, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}
	
	private void save_data(){
		DefaultTableModel d1 = (DefaultTableModel) stable.getModel();
		String sql ="";	
		int myid;			
		String departure =txdeparture.getText();
		String deptime = txdeptime.getText();
		String arrival =txarrival.getText();
		String arrtime =txarrtime.getText();
		String fare = txfare.getText();
	    int routeid = (int) ((Route) cmbroutes.getSelectedItem()).getRouteid();
		String rname=  (String) ((Route) cmbroutes.getSelectedItem()).getRname();
		int busid = (int) ((Bus) cmbbuses.getSelectedItem()).getBusid();
	    String busno =  (String) ((Bus) cmbbuses.getSelectedItem()).getBusno();	 
	    int driverid = (int) ((Driver) cmbdrivers.getSelectedItem()).getDriverid();
	    String drivername =  (String) ((Driver) cmbdrivers.getSelectedItem()).getName();	
	    if (svalidation(rname, busno, departure, deptime, arrival, arrtime) == true) {
	 		return;
		}
	    String departure_time = departure +" " +deptime;
	    String arrival_time = arrival + " " +arrtime;
	    if (rowid != "") {		
			sql = "update schedule set routeid= '" + routeid + "', busid= '" + busid 
					+ "',  departure_time = '" + departure_time + "' , arrival_time= '" 
					+ arrival_time + "',  fare='" +fare + "', driverid='" + driverid + "' where schedid = " + rowid;
		} else {
			sql = "insert into schedule (routeid, busid, departure_time, arrival_time, fare, driverid) "
					+ " values ( '" + routeid + "','" +busid + "','" + departure_time + "','" + arrival_time 
					+ "','" + fare +"','" + driverid + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(schedid) AS max_id FROM schedule");				
					d1.addRow(new Object[] { myid, routeid, rname, busid, busno, departure_time, 
							arrival_time, fare, driverid, drivername });
					         hh.gotolastrow(stable);
					       
				}  else {			
     				d1.setValueAt(routeid, myrow, 1);
  				    d1.setValueAt(rname, myrow, 2);
					d1.setValueAt(busid, myrow, 3);
     				d1.setValueAt(busno, myrow, 4);	
     				d1.setValueAt(departure_time, myrow, 5);
     				d1.setValueAt(arrival_time, myrow, 6);	
     				d1.setValueAt(fare, myrow, 7);	
     				d1.setValueAt(driverid, myrow, 8);	
     				d1.setValueAt(drivername, myrow, 9);	
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
	private void clearFields() {
		txdeparture.setText("");
		txdeptime.setText("");
	     txarrival.setText("");
		txarrtime.setText("");
		txfare.setText("");
		cmbbuses.setSelectedIndex(0);
		cmbroutes.setSelectedIndex(0);
		cmbdrivers.setSelectedIndex(0);
	}
	private void data_delete() {
		int row = stable.getSelectedRow();
		if (row >= 0) {
		String sql  = " delete from schedule where schedid=";
		 dd.data_delete(stable, sql);
		 clearFields();
	}
	}
	
	private Boolean svalidation(String rname, String busno, String departure,  String deptime,
			String arrival, String arrtime) {
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();
		
		if(sval.emptyvalid(rname)){
			err.add( "Route  is empty !");
			ret = true;
		}
		
		if(sval.emptyvalid(busno)){
			err.add( "Bus number  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(departure) || departure.contains("_")){
			err.add( "Departure date  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(deptime) || deptime.contains("_")){
			err.add( "Departure time  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(arrival)|| arrival.contains("_")){
			err.add( "Arrival date  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(arrtime) || arrtime.contains("_")){
			err.add( "Arrival time  is empty !");
			ret = true;
		}
	
		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
		return ret;
	}
	
	private void sqlgyart() {
		int jelem = 0;
		if (rbus.isSelected()) {
			jelem = 2;	
		} else if (rroute.isSelected()) {
			jelem = 1;
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
			sql = " where  lower(rname) LIKE '%" + ss + "%' order by rname " + " COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where busno LIKE '%" + ss + "%' order by busno COLLATE NOCASE ASC";
		}
		stable_update(sql);		
	}
	
	private void stable_update(String swhere){
		DefaultTableModel model = (DefaultTableModel) stable.getModel();
		model.setRowCount(0);
		String Sql = "select  s.schedid, s.routeid,  r.origin || '-' || r.destination as rname, s.busid, "
				+ " b.busno, s.departure_time, s.arrival_time, s.fare, s.driverid, d.name from schedule s "
				+ "  left join routes r  on s.routeid = r.routeid "
				+ "  left join buses b  on s.busid = b.busid"
				+ "  left join drivers d  on s.driverid = d.driverid"
				+ swhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String schedid = rs.getString("schedid");
				String routeid = rs.getString("routeid");
				String rname= rs.getString("rname");
				String busid = rs.getString("busid");
				String busno= rs.getString("busno");
				String departure = rs.getString("departure_time");
				String arrival = rs.getString("arrival_time");
				String fare = rs.getString("fare");				
				String driverid = rs.getString("driverid");
				String name = rs.getString("name");
				model.addRow(new Object[] { schedid, routeid, rname, busid, busno, departure, 
					arrival, fare, driverid, name });
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = {"schedid", "routeid", "Route", "busid", "Busnumber", "Departure" ,"Arrival","Fare",
				"Driverid","Driver name"};
		((DefaultTableModel) stable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(stable, 1000, 0, 0, 300, 0, 100, 150, 150, 100, 0,200);
	int[] cols = { 7 };
	hh.cellsright(stable, cols); 
		stable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				stable.scrollRectToVisible(stable.getCellRect(stable.getRowCount() - 1, 0, true));
			}
		});
		if (stable.getRowCount() > 0) {
			int row = stable.getRowCount() - 1;
			stable.setRowSelectionInterval(row, row);		
		}
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Schedules();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	JLabel lbheader, lbroute, lbbus, lbdeparture,  lbarrival, lbfare, lbsearch, lbdriver;
	JPanel basePanel, ePanel, tPanel, searchPanel;
	JComboBox cmbroutes, cmbbuses, cmbdrivers;
	JFormattedTextField txdeparture, txdeptime, txarrival, txarrtime;
	JTextField txfare, txsearch;
	JButton btnsave, btncancel, btndelete, btnsearch, btnclear;
	JTable stable;
	JScrollPane jScrollPane1;
	JRadioButton rroute, rbus;
	ButtonGroup btnGroup = new ButtonGroup();
}
