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
import java.util.Date;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class Admin_view_feedbacks extends JFrame{
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
	
	 Admin_view_feedbacks(){
		 init();	
			hh.iconhere(this);
	 }
	 
	 private void init(){
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
			setTitle("Admin feed backs view");
			lbheader = hh.fflabel("Admin feed backs view");
			lbheader.setBounds(40, 10, 450, 35);
			add(lbheader);
			basePanel = new JPanel(null);
			basePanel.setBounds(10, 50, 1060, 500);
			basePanel.setBorder(hh.ztroundborder(Color.BLUE));
			basePanel.setOpaque(false);
			add(basePanel);			

			tPanel = new JPanel(null);
			tPanel.setBounds(20, 10, 1020, 470);
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
			
			remail = new JRadioButton("Email");
			remail.setHorizontalTextPosition(SwingConstants.LEFT);
			remail.setActionCommand("email");
			remail.setBounds(180, 10, 80, 25);
			remail.setFont(new Font("Tahoma", Font.BOLD, 16));
			remail.setBackground(Color.ORANGE);
			btnGroup.add(remail);
			searchPanel.add(rname);	
			searchPanel.add(remail);

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
				//   ftable_update("");
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
			
			ftable = hh.ztable();	
			ftable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			ftable.setTableHeader(new JTableHeader(ftable.getColumnModel()) {
				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 25;
					return d;
				}
			});
			ftable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					DefaultTableModel model = (DefaultTableModel) ftable.getModel();
					try {
						int row = ftable.getSelectedRow();
						if (row > -1) {
							String comment = model.getValueAt(row, 8).toString();								
							txacomment.setText(comment);							
						}
					} catch (Exception e) {
						System.out.println("sql error!!!");
					}
				}
			});
			
			hh.madeheader(ftable);
			ftable.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					ftable.scrollRectToVisible(ftable.getCellRect(ftable.getRowCount() - 1, 0, true));
				}
			});	

			jScrollPane1 = new JScrollPane(ftable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			ftable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
					new String[] {"Passinger name", "Email", "Schedid", "Start", "Destination",
							"Departure_time","Date","Rating", "Comment" }));
			hh.setJTableColumnsWidth(ftable, 1070, 200, 200, 0, 185, 185, 130, 90, 90, 0);	
			jScrollPane1.setViewportView(ftable);
			jScrollPane1.setBounds(15, 60, 990, 180);
			jScrollPane1.setBorder(hh.borderf);
			tPanel.add(jScrollPane1);
			
			lbcomment = hh.clabel("Comment");
			lbcomment.setBounds(260, 260, 80, 25);
			tPanel.add(lbcomment);
			
			txacomment = gr.gTextArea();		
			txacomment.setEditable(false);
			txacomment.setFocusable(false);

			comPane = new JScrollPane(txacomment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			comPane.setBorder(hh.borderf);
			comPane.setBounds(350, 250, 400, 200);
			comPane.setViewportView(txacomment);
			tPanel.add(comPane);
			setVisible(true);	
	 }
	 
	 private void ftable_update(String swhere){
			DefaultTableModel model = (DefaultTableModel) ftable.getModel();
			model.setRowCount(0);
			String Sql = "select p.pname, p.email, s.schedid,  s.departure_time, rou.origin,"
					+ " rou.destination ,f.fdate, f.rating, f.comments "
			+ " from feedback f "
			+ " join  passenger p ON p.passid = f.Passid "
			+ " join schedule s ON s.schedid = f.schedid "
		   +  " join  routes rou ON rou.routeid = s.routeId"
		   +  swhere;
			try {
				rs = dh.GetData(Sql);
				while (rs.next()) {			
					String pname = rs.getString("pname");
					String email = rs.getString("email");
					String schedid = rs.getString("schedid");
					String departure = rs.getString("departure_time");
					String origin= rs.getString("origin");
					String destination = rs.getString("destination");	
					String fdate = rs.getString("fdate");
					String comments = rs.getString("comments");
					String rating = rs.getString("rating");
					model.addRow(new Object[] { pname, email, schedid, origin, destination,
		             departure, fdate, rating, comments});
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
			String[] fej = { "Passinger name", "Email", "Schedid", "Start", "Destination",
					"Departure_time","Date","Rating", "Comment"};
			((DefaultTableModel) ftable.getModel()).setColumnIdentifiers(fej);	
			hh.setJTableColumnsWidth(ftable, 1070, 200, 200, 0, 185, 185, 130, 90, 90, 0);	
	
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
	 
	 private void sqlgyart() {
			ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
			vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();		
			String swhere =" where fdate >= date('" + ssdate + "') and fdate <=" + " date('" + vvdate + "')";
			int jelem = 0;
			if (rname.isSelected()) {
				jelem = 1;	
			} else if (remail.isSelected()) {
				jelem = 2;
			} else {
				String qual = "Did not choose !";
				JOptionPane.showMessageDialog(null, qual);
			}
			if (jelem < 1 ) {
				return;
			}
			String sql = "";
			String ss = txsearch.getText().trim().toLowerCase();
            if (!hh.zempty(ss)) {
			if (jelem == 1) {
				sql = " lower(pname) LIKE '%" + ss + "%' order by pname " + " COLLATE NOCASE ASC";
				swhere = swhere + " and " +sql;
			} else if (jelem == 2) {
				sql = " email LIKE '%" + ss + "%' order by email COLLATE NOCASE ASC";
				swhere = swhere + " and " +sql;
			}	
	        }
			 ftable_update(swhere);
			return;

		}
	 
		public static void main(String args[]) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						new Admin_view_feedbacks();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	
JLabel lbheader, lbsearch, lbjel, lbidoszak, lbcomment;
JPanel basePanel, tPanel, searchPanel;
JTable ftable;
JButton btnclear, btnsearch;
JRadioButton rname, remail;
ButtonGroup btnGroup = new ButtonGroup();
JTextField txsearch;
JScrollPane jScrollPane1, comPane;
JTextArea txacomment;
}
