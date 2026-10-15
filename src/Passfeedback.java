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
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Passfeedback extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Spallvalid sval = new Spallvalid();	
	String mychedid="";
	String nowdate = hh.currentDate();
	Passfeedback(){
		init();
		ftable_update();
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
	setSize(new java.awt.Dimension(1160, 600));
	setLocationRelativeTo(null);
	this.getContentPane().setBackground(hh.basecolor1);
	setTitle("Travel feed back");
	lbheader = hh.fflabel(" Travel feed back");
	lbheader.setBounds(40, 10, 250, 35);
	add(lbheader);
	setLayout(null);
	setLocationRelativeTo(null);
	this.getContentPane().setBackground(hh.basecolor1);

	basePanel = new JPanel(null);
	basePanel.setBounds(20, 50, 1100, 490);
	basePanel.setBorder(hh.ztroundborder(Color.BLUE));
	basePanel.setOpaque(false);
	add(basePanel);
	
	tPanel = new JPanel(null);
	tPanel.setBounds(20, 20, 1050, 180);
	tPanel.setBorder(hh.ztroundborder(Color.BLUE));
	tPanel.setOpaque(false);
	basePanel.add(tPanel);	
	
	ePanel = new JPanel(null);
	ePanel.setBounds(20, 220, 1050, 250);
	ePanel.setBorder(hh.ztroundborder(Color.BLUE));
	ePanel.setOpaque(false);
	basePanel.add(ePanel);	
	
	ftable = hh.ztable();	
	ftable.setTableHeader(new JTableHeader(ftable.getColumnModel()) {
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.height = 25;
			return d;
		}
	});

	hh.madeheader(ftable);
	
	ftable.addMouseListener(new java.awt.event.MouseAdapter() {
		public void mouseClicked(java.awt.event.MouseEvent evt) {
			int row = ftable.getSelectedRow();
			if (row >= 0) {						
				String pname = ftable.getValueAt(row, 0).toString();					
		        txpname.setText(pname);
				String start = ftable.getValueAt(row, 3).toString();
		        txstart.setText(start);
		        String destination = ftable.getValueAt(row, 3).toString();
			   txdestination.setText(destination);				
        		String departure = ftable.getValueAt(row, 5).toString();
        		txdeparture.setText(departure);
			    String seatno = ftable.getValueAt(row, 6).toString();
			    txseatno.setText(seatno);
			    mychedid = ftable.getValueAt(row, 2).toString();
			}
		}
	});	

	jScrollPane1 = new JScrollPane(ftable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	ftable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
			new String[] {  "Passinger name", "Email", "Schedid", "Start", "Destination",
					"Departure_time","Seatno","Fare" }));
	hh.setJTableColumnsWidth(ftable, 1020, 200, 200, 0, 175, 175, 130, 60, 80);
	int[] cols = { 7 };
	hh.cellsright(ftable, cols); 
	jScrollPane1.setViewportView(ftable);
	jScrollPane1.setBounds(10, 10, 1030, 150);
	jScrollPane1.setBorder(hh.borderf);
	tPanel.add(jScrollPane1);		
	
	lbpname = hh.clabel("Passenger name");
	lbpname.setBounds(0, 20, 140, 25);
	ePanel.add(lbpname);

	txpname=hh.cTextField(100);
	txpname.setBounds(150, 20, 200, 25);	
	txpname.setEditable(false);
	ePanel.add(txpname);	
	
	lbstart = hh.clabel("Start");
	lbstart.setBounds(0, 60, 140, 25);
	ePanel.add(lbstart);

	txstart=hh.cTextField(100);
	txstart.setBounds(150, 60, 200, 25);	
	txstart.setEditable(false);
	ePanel.add(txstart);	
	
	lbdestination = hh.clabel("Destination");
	lbdestination.setBounds(0, 100, 140, 25);
	ePanel.add(lbdestination);

	txdestination=hh.cTextField(100);
	txdestination.setBounds(150, 100, 200, 25);	
	txdestination.setEditable(false);
	ePanel.add(txdestination);	
	
	lbdeparture = hh.clabel("Departure time");
	lbdeparture.setBounds(0, 140, 140, 25);
	ePanel.add(lbdeparture);

	txdeparture=hh.cTextField(100);
	txdeparture.setBounds(150, 140, 200, 25);	
	txdeparture.setEditable(false);
	ePanel.add(txdeparture);	
	
	lbseatno = hh.clabel("Seatno");
	lbseatno.setBounds(0, 180, 140, 25);
	ePanel.add(lbseatno);

	txseatno=hh.cTextField(100);
	txseatno.setBounds(150, 180, 200, 25);	
	txseatno.setEditable(false);
	ePanel.add(txseatno);
	
	lbcomment= hh.clabel("Comment");
	lbcomment.setBounds(360, 20, 80, 30);
	ePanel.add(lbcomment);
	
	txacomment = gr.gTextArea();		
	jsp1 = new JScrollPane(txacomment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	jsp1.setBounds(450, 20, 400, 180);
	jsp1.setViewportView(txacomment);
	ePanel.add(jsp1);	
	
	lbrating= hh.clabel("Rating");
	lbrating.setBounds(560, 210, 80, 30);
	ePanel.add(lbrating);		
	
	cmbrating = gr.grcombo();
	cmbrating.addItem(" ");
	cmbrating.addItem("1");
	cmbrating.addItem("2");
	cmbrating.addItem("3");
	cmbrating.addItem("4");
	cmbrating.addItem("5");	
	cmbrating.setBounds(650, 210, 60, 30);
	ePanel.add(cmbrating);	

	btnsubmit = gr.sbcs("Submit");
	btnsubmit.setBounds(890, 100, 110, 30);
	btnsubmit.setForeground(Color.black);
	btnsubmit.setBackground(hh.lpiros);
	ePanel.add(btnsubmit);
    btnsubmit.addActionListener(e -> submit());	
	setVisible(true);
}

private void submit() {
	String rating = 	cmbrating.getSelectedItem().toString();
	String comment = txacomment.getText();			
   String pname = txpname.getText();	
   String start = txstart.getText();   
    String destination = txdestination.getText();	
    String departure = txdeparture.getText(); 
    String seatno = txseatno.getText();  
	
	 if (fvalidation(pname, start, destination, departure, seatno, comment) == true) {
	 		return;
		}
	
	String sql =" insert into feedback (passid, schedid, rating, comments, fdate) "
			+ " values ( '" + dh.mypassid + "','" + mychedid +"','" + rating +"','" +comment 
			+ "','" + nowdate +"')";	
	 try {
			int flag = dh.Insupdel(sql);
			if (flag == 1) {										
				hh.ztmessage("Success", "Message");
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
private Boolean fvalidation(String pname, String start, String destination, String departure,
		String seatno, String comment){
	Boolean ret = false;
	ArrayList<String> err = new ArrayList<String>();		
	if(sval.emptyvalid(pname)){
		err.add( "Passenger name is empty !");
		ret = true;
	}
	if(sval.emptyvalid(start)){
		err.add( "Start is empty !");
		ret = true;
	}
	if(sval.emptyvalid(destination)){
		err.add( "Destination is empty !");
		ret = true;
	}
	if(sval.emptyvalid(departure)){
		err.add( "Departure time is empty !");
		ret = true;
	}
	if(sval.emptyvalid(seatno)){
		err.add( "Seatno is empty !");
		ret = true;
	}
	if(sval.emptyvalid(comment)){
		err.add( "Comment is empty !");
		ret = true;
	}
	if (err.size() > 0) {
		JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
	}

return ret;
}

private void clearFields(){	
	 txpname.setText(""); 
	 txstart.setText("");
	 txdestination.setText("");
	 txdeparture.setText(""); 
	 txseatno.setText("");
	 mychedid = "";
	 txacomment.setText("");
}

private void ftable_update(){
	DefaultTableModel model = (DefaultTableModel) ftable.getModel();
	model.setRowCount(0);
	String Sql = "select p.pname, p.email, s.schedid,  s.departure_time, rou.origin,"
			+ " rou.destination, r.seatno, r.fare "
	+ " from seatreservation r "
	+ " join  passenger p ON p.passid = r.Passid "
	+ " join schedule s ON s.schedid = r.schedid "
   +  " join  routes rou ON rou.routeid = s.routeId"
   +  " where p.passid ='"+ dh.mypassid +"'";
	try {
		rs = dh.GetData(Sql);
		while (rs.next()) {			
			String pname = rs.getString("pname");
			String email = rs.getString("email");
			String schedid = rs.getString("schedid");
			String departure = rs.getString("departure_time");
			String origin= rs.getString("origin");
			String destination = rs.getString("destination");
			String seatno= rs.getString("seatno");	
			String fare = rs.getString("fare");
			model.addRow(new Object[] { pname, email, schedid, origin, destination,
             departure, seatno, fare});
		}			
	} catch (SQLException e) {
		e.printStackTrace();
	} finally {
		dh.CloseConnection();
	}
	String[] fej = { "Passinger name", "Email", "Schedid", "Start", "Destination",
			"Departure_time","Seatno","Fare"};
	((DefaultTableModel) ftable.getModel()).setColumnIdentifiers(fej);	
	hh.setJTableColumnsWidth(ftable, 1020, 200, 200, 0, 175, 175, 130, 60, 80);
	int[] cols = { 7 };
	hh.cellsright(ftable, cols); 
	ftable.addComponentListener(new ComponentAdapter() {
		public void componentResized(ComponentEvent e) {
			ftable.scrollRectToVisible(ftable.getCellRect(ftable.getRowCount() - 1, 0, true));
		}
	});
	if (ftable.getRowCount() > 0) {
		int row = ftable.getRowCount() - 1;
		ftable.setRowSelectionInterval(row, row);		
	}
}	
	
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Passfeedback();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader;
	JPanel basePanel, tPanel, ePanel;
	JTable ftable;
	JTextField txpname, txstart, txdestination, txdeparture, txseatno;
	JLabel lbpname, lbstart, lbdestination, lbdeparture, lbseatno, lbcomment, lbrating;
	JScrollPane jScrollPane1, jsp1;
	JTextArea txacomment;
	JComboBox cmbrating;
	JButton  btnsubmit;
}
