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
import Databaseop.Databaseop.Bus;

public class Routes extends JFrame {
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

	Routes() {
		init();
		 rtable_update("");
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
		setTitle("Routes");
		lbheader = hh.fflabel("Routes");
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

		lborigin = hh.clabel("Origin");
		lborigin.setBounds(0, 30, 140, 30);
		ePanel.add(lborigin);

		txorigin = gr.gTextField(100);
		txorigin.setBounds(150, 30, 200, 30);
		txorigin.addKeyListener(hh.MUpper());
		ePanel.add(txorigin);

		lbdestination = hh.clabel("Destination");
		lbdestination.setBounds(0, 70, 140, 30);
		ePanel.add(lbdestination);

		txdestination = gr.gTextField(100);
		txdestination.setBounds(150, 70, 200, 30);
		txdestination.addKeyListener(hh.MUpper());
		ePanel.add(txdestination);

		lbdistance = hh.clabel("Distance km");
		lbdistance.setBounds(0, 110, 140, 30);
		ePanel.add(lbdistance);

		txdistance = gr.gTextField(100);
		txdistance.setBounds(150, 110, 200, 30);
		txdistance.setHorizontalAlignment(JLabel.RIGHT);
		txdistance.addKeyListener(hh.MUpper());
		ePanel.add(txdistance);

		lbduration = hh.clabel("Duration hh:mm");
		lbduration.setBounds(0, 150, 140, 30);
		ePanel.add(lbduration);
		
		txduration = hh.TimetextField();
		txduration.setBounds(150, 150, 60, 30);
		ePanel.add(txduration);

//		txduration = gr.gTextField(100);
//		txduration.setBounds(150, 150, 200, 30);
//		txduration.setHorizontalAlignment(JLabel.RIGHT);
//		ePanel.add(txduration);

		btnsave = gr.sbcs("Save");
		btnsave.setBounds(200, 210, 100, 30);
		btnsave.setForeground(Color.black);
		btnsave.setBackground(hh.lpiros);
		ePanel.add(btnsave);
		btnsave.addActionListener(e -> save_data());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBounds(200, 250, 100, 30);
		btncancel.setBackground(hh.vvzold);
		ePanel.add(btncancel);
		 btncancel.addActionListener(e -> clearFields());

		btndelete = gr.sbcs("Delete");
		btndelete.setBounds(200, 290, 100, 30);
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
		rorigin = new JRadioButton("Origin");
		rorigin.setHorizontalTextPosition(SwingConstants.LEFT);
		rorigin.setActionCommand("origin");
		rorigin.setBounds(95, 10, 80, 25);
		rorigin.setFont(new Font("Tahoma", Font.BOLD, 16));
		rorigin.setBackground(Color.ORANGE);
		btnGroup.add(rorigin);

		rdestination = new JRadioButton("Destination");
		rdestination.setHorizontalTextPosition(SwingConstants.LEFT);
		rdestination.setActionCommand("destination");
		rdestination.setBounds(180, 10, 120, 25);
		rdestination.setFont(new Font("Tahoma", Font.BOLD, 16));
		rdestination.setBackground(Color.ORANGE);
		btnGroup.add(rdestination);
		searchPanel.add(rorigin);
		searchPanel.add(rdestination);

		txsearch = hh.cTextField(25);
		txsearch.setBounds(305, 10, 200, 25);
		searchPanel.add(txsearch);

		btnclear = new JButton();
		btnclear.setFont(new java.awt.Font("Tahoma", 1, 16));
		btnclear.setMargin(new Insets(0, 0, 0, 0));
		btnclear.setBounds(506, 10, 25, 25);
		btnclear.setBorder(hh.borderf);
		btnclear.setText("x");
		searchPanel.add(btnclear);
		btnclear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txsearch.setText("");
				txsearch.requestFocus();
				rtable_update("");
			}
		});
		btnsearch = hh.cbutton("Filter");
		btnsearch.setForeground(Color.black);
		btnsearch.setBackground(Color.ORANGE);
		btnsearch.setBorder(BorderFactory.createMatteBorder(1, 1, 3, 3, Color.DARK_GRAY));
		btnsearch.setBounds(535, 10, 90, 27);
		btnsearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		searchPanel.add(btnsearch);
		btnsearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sqlgyart();
			}
		});
		rorigin.setSelected(true);

		rtable = hh.ztable();
		rtable.setTableHeader(new JTableHeader(rtable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		hh.madeheader(rtable);
		rtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rtable.scrollRectToVisible(rtable.getCellRect(rtable.getRowCount() - 1, 0, true));
			}
		});

		rtable.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = rtable.getSelectedRow();
				if (row >= 0) {
					rowid = rtable.getValueAt(row, 0).toString();
					String origin = rtable.getValueAt(row, 1).toString();
					txorigin.setText(origin);
					String destination = rtable.getValueAt(row, 2).toString();
					txdestination.setText(destination);
					String distance = rtable.getValueAt(row, 3).toString();
					txdistance.setText(distance);
					String duration = rtable.getValueAt(row, 4).toString();
					txduration.setText(duration);				
					myrow = row;
				}
			}
		});

		jScrollPane1 = new JScrollPane(rtable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		rtable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
				new String[] { "Route id", "Origin", "Destination", "Distance km", "Duration min" }));
		hh.setJTableColumnsWidth(rtable, 620, 0, 200, 200, 110, 110);
		int[] cols = { 3,4 };
		hh.cellsright(rtable, cols); 
		jScrollPane1.setViewportView(rtable);
		jScrollPane1.setBounds(15, 80, 620, 350);
		jScrollPane1.setBorder(hh.borderf);
		tPanel.add(jScrollPane1);
		setVisible(true);
	}	
	
	private void save_data(){
		DefaultTableModel d1 = (DefaultTableModel) rtable.getModel();
		String sql ="";	
		int myid;	
		String origin = txorigin.getText();
		String destination = txdestination.getText();	
		String distance = txdistance.getText();
		String duration = txduration.getText();	  
	    
	    if (rvalidation(origin, destination,distance, duration) == true) {
	 		return;
		}
	    if (rowid != "") {		
			sql = "update routes set origin= '" + origin + "', destination= '" + destination + "',  distance_km = '" 
	       + distance + "' , duration= '" + duration + "' where routeid = " + rowid;
		} else {
			sql = "insert into routes (origin, destination, distance_km, duration) values ( '" + origin + "','"
		           + destination + "','" + distance + "','" + duration + "')";
		}
		try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {
				hh.ztmessage("Success", "Message");
				if (rowid == "") {
					myid = dd.table_maxid("SELECT MAX(routeid) AS max_id FROM routes");				
					d1.addRow(new Object[] { myid, origin, destination, distance, duration });
					         hh.gotolastrow(rtable);
					       
				}  else {			
					d1.setValueAt(origin, myrow, 1);
					d1.setValueAt(destination, myrow, 2);
					d1.setValueAt(distance, myrow, 3);
					d1.setValueAt(duration, myrow, 4);									
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
		private Boolean rvalidation(String origin, String destination, String distance, String duration){
			Boolean ret = false;
			ArrayList<String> err = new ArrayList<String>();
			
			if(sval.emptyvalid(origin)){
				err.add( "Origin  is empty !");
				ret = true;
			}
			if(sval.emptyvalid(destination)){
				err.add( "Destination  is empty !");
				ret = true;
			}
			if(sval.emptyvalid(distance)){
				err.add( "Distance  is empty !");
				ret = true;
			}
			if(sval.emptyvalid(duration) || duration.contains("_")){
				err.add( "Duration  is empty !");
				ret = true;
			}
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
			
		return ret;
		}	
	
	private void  clearFields() {
		rowid = "";
		myrow = 0;
		txorigin.setText("");
		txdestination.setText("");
		txdistance.setText("");
		txduration.setText("");		
	}
	
	private void rtable_update(String swhere){
		DefaultTableModel model = (DefaultTableModel) rtable.getModel();
		model.setRowCount(0);
		String Sql = "select  routeid, origin, destination, distance_km, duration  from routes "
				+ swhere;

		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String routeid = rs.getString("routeid");
				String origin = rs.getString("origin");
				String destination = rs.getString("destination");
				String distance= rs.getString("distance_km");
				String duration = rs.getString("duration");
						
				model.addRow(new Object[] { routeid, origin, destination, distance, duration});
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "Route ID", "Origin", "Destination","Distance km", "Duration"};
		((DefaultTableModel) rtable.getModel()).setColumnIdentifiers(fej);	
		hh.setJTableColumnsWidth(rtable, 620, 0, 200, 200, 110, 110);
		int[] cols = { 3,4 };
		hh.cellsright(rtable, cols); 
		rtable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				rtable.scrollRectToVisible(rtable.getCellRect(rtable.getRowCount() - 1, 0, true));
			}
		});
		if (rtable.getRowCount() > 0) {
			int row = rtable.getRowCount() - 1;
			rtable.setRowSelectionInterval(row, row);		
		}
	}
	
	private void sqlgyart() {
		int jelem = 0;
		if (rorigin.isSelected()) {
			jelem = 1;	
		} else if (rdestination.isSelected()) {
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
			sql = "where  lower(origin) LIKE '%" + ss + "%' order by origin " + " COLLATE NOCASE ASC";
		} else if (jelem == 2) {
			sql = " where lower(destination) LIKE '%" + ss + "%' order by destination COLLATE NOCASE ASC";
		}
		rtable_update(sql);
	}
	private void data_delete() {
		int flag=0;
		DefaultTableModel model = (DefaultTableModel) rtable.getModel();
		int row = rtable.getSelectedRow();
		if (row >= 0) {
			String srouteid = model.getValueAt(row, 0).toString();	
			if (srouteid.equals("")) {
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
			if (a == JOptionPane.YES_OPTION) {				
				String sql = "delete from routes where routeid='" + srouteid + "'";
				flag = dh.Insupdel(sql);					
				if (flag > 0) {
					model.removeRow(row);
					clearFields();
				}			
			}
			}
	}


		public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Routes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lborigin, lbdestination, lbdistance, lbduration, lbsearch;
	JTextField txorigin, txdestination, txdistance, txduration, txsearch;
	JPanel basePanel, ePanel, tPanel, searchPanel;
	JButton btnsave, btncancel, btndelete, btnsearch, btnclear;
	JTable rtable;
	JScrollPane jScrollPane1;
	JRadioButton rorigin, rdestination;
	ButtonGroup btnGroup = new ButtonGroup();

}
