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

public class Admin_view_contactuss extends JFrame{
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
	
	Admin_view_contactuss(){
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
			setTitle("Admin contact us view");
			lbheader = hh.fflabel("Admin contact us view");
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
				 // ctable_update("");
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
			
			ctable = hh.ztable();	
			ctable.setTableHeader(new JTableHeader(ctable.getColumnModel()) {
				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 25;
					return d;
				}
			});
			
			ctable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					DefaultTableModel model = (DefaultTableModel) ctable.getModel();
					try {
						int row = ctable.getSelectedRow();
						if (row > -1) {
							String message = model.getValueAt(row, 3).toString();								
							txamessage.setText(message);							
						}
					} catch (Exception e) {
						System.out.println("sql error!!!");
					}
				}
			});
			
			hh.madeheader(ctable);
			ctable.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
				}
			});	

			jScrollPane1 = new JScrollPane(ctable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			ctable.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {},
					new String[] {"Name", "Email", "Date", "Message" }));
			hh.setJTableColumnsWidth(ctable, 670, 250, 300, 120, 0);	
			jScrollPane1.setViewportView(ctable);
			jScrollPane1.setBounds(190, 60, 670, 180);
			jScrollPane1.setBorder(hh.borderf);
			tPanel.add(jScrollPane1);			
			lbcomment = hh.clabel("Message");
			lbcomment.setBounds(260, 260, 80, 25);
			tPanel.add(lbcomment);
			
			txamessage = gr.gTextArea();		
			txamessage.setEditable(false);
			txamessage.setFocusable(false);

			comPane = new JScrollPane(txamessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			comPane.setBorder(hh.borderf);
			comPane.setBounds(350, 250, 400, 200);
			comPane.setViewportView(txamessage);
			tPanel.add(comPane);
			
		setVisible(true);
	}
	 private void ctable_update(String swhere){
			DefaultTableModel model = (DefaultTableModel) ctable.getModel();
			model.setRowCount(0);
			String Sql = "select name, email, date, message  from contactus "			
		   +  swhere;
			try {
				rs = dh.GetData(Sql);
				while (rs.next()) {			
					String name = rs.getString("name");
					String email = rs.getString("email");
					String date = rs.getString("date");
					String message= rs.getString("message");					
					model.addRow(new Object[] { name, email, date, message});
				}			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				dh.CloseConnection();
			}
			String[] fej = { "Name", "Email", "Date", "Message"};
			((DefaultTableModel) ctable.getModel()).setColumnIdentifiers(fej);	
			hh.setJTableColumnsWidth(ctable, 670, 250, 300, 120, 0);		
			ctable.addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					ctable.scrollRectToVisible(ctable.getCellRect(ctable.getRowCount() - 1, 0, true));
				}
			});
			if (ctable.getRowCount() > 0) {
				int row = ctable.getRowCount() - 1;
				ctable.setRowSelectionInterval(row, row);		
			}
		}	
	 
	 private void sqlgyart() {
			ssdate = ((JTextField) sdate.getDateEditor().getUiComponent()).getText();
			vvdate = ((JTextField) vdate.getDateEditor().getUiComponent()).getText();		
			String swhere =" where date >= date('" + ssdate + "') and date <=" + " date('" + vvdate + "')";
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
				sql = " lower(name) LIKE '%" + ss + "%' order by name " + " COLLATE NOCASE ASC";
				swhere = swhere + " and " +sql;
			} else if (jelem == 2) {
				sql = " email LIKE '%" + ss + "%' order by email COLLATE NOCASE ASC";
				swhere = swhere + " and " +sql;
			}	
	        }
			 ctable_update(swhere);
			return;
		}	
	 
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Admin_view_contactuss();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lbheader, lbsearch, lbjel, lbidoszak, lbcomment;
	JPanel basePanel, tPanel, searchPanel;
	JTable ctable;
	JButton btnclear, btnsearch;
	JRadioButton rname, remail;
	ButtonGroup btnGroup = new ButtonGroup();
	JTextField txsearch;
	JScrollPane jScrollPane1, comPane;
	JTextArea txamessage;

}
