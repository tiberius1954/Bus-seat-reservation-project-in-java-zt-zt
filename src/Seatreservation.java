import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;

import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Classes.Hhelper.StringUtils;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Seatreservation extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Allvalid allv = new Allvalid();
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
	DefaultTableModel model, pmodel, tmodel;
	String rowid = "";
	int myrow = 0;
	JFrame myframe = this;
	String myschedid = "";
	String myrouteid = "";
	String mybusid = "";
	String myfare = "";
	String mylayid = "";
	int myrows = 0;
	int mycols = 0;
	ArrayList<String> myseats = new ArrayList<String>();
	String aktheader = "";
	Object[][] sdata = null;
	String nowdate = hh.currentDate();
	int itotal_amount =0;
	int mypyhid =0;

	Seatreservation() {
		init();
		 falsemethod(false);
			hh.iconhere(this);
	}

	Seatreservation(String schedid, String routeid, String busid, String fare, String pheader) {
		myschedid = schedid;
		myrouteid = routeid;
		mybusid = busid;
		myfare = fare;
		aktheader = pheader;
		otherparam(busid);
		init();
		 falsemethod(false);
			hh.iconhere(this);
	}

	private void init() {
		UIManager.put("ComboBox.selectionBackground", hh.lpiros);
		UIManager.put("ComboBox.selectionForeground", hh.feher);
		UIManager.put("ComboBox.background", new ColorUIResource(hh.homok));
		UIManager.put("ComboBox.foreground", Color.BLACK);
		UIManager.put("ComboBox.border", new LineBorder(Color.green, 1));
		UIManager.getDefaults().put("ToggleButton.disabledText", Color.RED);

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
		setTitle("Seatreservation");
		lbheader = hh.fflabel("Seatreservation");
		lbheader.setBounds(40, 10, 200, 35);
		add(lbheader);
		cards = new CardLayout();
		cardPanel = new JPanel();
		cardPanel.setLayout(cards);
		cardPanel.setBounds(10, 50, 1060, 550);
		// cardPanel.setBorder(hh.border4);
		cardPanel.setBackground(hh.basecolor1);

		basePanel = new JPanel(null);
		basePanel.setBounds(10, 50, 100, 550);
		// basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		basePanel.setName("base");

		seatPanel = new JPanel(null);
		seatPanel.setBounds(10, 10, 280, 510);
		seatPanel.setBorder(hh.ztroundborder(Color.BLUE));
		seatPanel.setOpaque(false);
		basePanel.add(seatPanel);
		tbuttons = new ColoredTButton[myrows][mycols];
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
					tbuttons[i][j] = new ColoredTButton();
					tbuttons[i][j].setText(title);
					tbuttons[i][j].setBounds(x, y, 35, 30);
					tbuttons[i][j].setMargin(new Insets(0, 0, 0, 0));
					tbuttons[i][j].setBorder(hh.borderz);
					// sn = title;
					tbuttons[i][j].setName(title);
					tbuttons[i][j].setContentAreaFilled(false);
					tbuttons[i][j].setOpaque(true);
					tbuttons[i][j].setBackground(Color.orange);
					tbuttons[i][j].setFocusPainted(false);
					tbuttons[i][j].setFocusable(false);
					seatPanel.add(tbuttons[i][j]);

					tbuttons[i][j].addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent evt) {
							buttActionPerformed(evt);
						}
					});
				}
				x = x + 40;
			}
			y = y + 35;
			x = 10;
		}
		enablequestion();

		tPanel = new JPanel(null);
		tPanel.setBounds(300, 10, 745, 510);
		tPanel.setBorder(hh.ztroundborder(Color.BLUE));
		tPanel.setOpaque(false);
		basePanel.add(tPanel);

		lbtitle = hh.clabel(aktheader);
		// lbtitle.setBackground(Color.BLUE);
		// lbtitle.setForeground(new Color(0, 0, 204));
		lbtitle.setBounds(10, 30, 730, 40);
		// lbtitle.setBorder(hh.bb);
		lbtitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbtitle.setFont(new java.awt.Font("Tahoma", 1, 16));
		tPanel.add(lbtitle);

		btntotable = gr.sbcs("Put the seats to the table");
		btntotable.setBackground(hh.vpiros);
		btntotable.setForeground(Color.white);
		btntotable.setBounds(65, 110, 230, 35);
		tPanel.add(btntotable);
		btntotable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableupload();
			}
		});

		String[] columnNames = { "Seat", "Fare", "Delete" };
		Object[][] data = {};
		model = new DefaultTableModel(data, columnNames) {
			boolean[] canEdit = new boolean[] { false, false, true };

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		};
		btable = hh.ztable();
		btable.setBorder(hh.borderf);
		btable.setModel(model);

		btable.getColumnModel().getColumn(2).setCellRenderer(new ClientsTableButtonRenderer());
		btable.getColumnModel().getColumn(2).setCellEditor(new ClientsTableRenderer(new JCheckBox()));
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		btable.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
		btable.setPreferredScrollableViewportSize(btable.getPreferredSize());
		hh.madeheader(btable);
		hh.setJTableColumnsWidth(btable, 338, 30, 30, 40);
		scroll = new JScrollPane();
		scroll.setViewportView(btable);
		scroll.setBounds(10, 170, 338, 215);
		tPanel.add(scroll);

		btnreservation = gr.sbcs("Seat reservation");
		btnreservation.setBackground(hh.vpiros);
		btnreservation.setForeground(Color.white);
		btnreservation.setBounds(105, 400, 160, 35);
		tPanel.add(btnreservation);
		btnreservation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rows= model.getRowCount();
				if (rows > 0) {
				toreservation();
				  cards.show(cardPanel, "pay");		  
				}
			}
		});
		picPanel = new JPanel(null);
		picPanel.setBounds(360, 70, 370, 425);
		picPanel.setBorder(hh.line);
		picPanel.setOpaque(false);
		tPanel.add(picPanel);

		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/busseats.jpg"));
		Image i2 = i1.getImage().getScaledInstance(365, 420, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(2, 2, 365, 420);
		picPanel.add(lbpicture);
		cardPanel.add(basePanel, "base");

		payPanel = makepaypanel();
		payPanel.setName("pay");
		cardPanel.add(payPanel, "pay");
		add(cardPanel);

	    cards.show(cardPanel, "base");
	  
		setVisible(true);
	}

	private JPanel makepaypanel() {
		JPanel ppanel = new JPanel(null);
		ppanel.setBackground(hh.basecolor1);
		ppanel.setBorder(hh.ztroundborder(Color.BLUE));
		ppanel.setOpaque(false);
		lbpayment = hh.fflabel("Payment");
		lbpayment.setBounds(30, 20, 300, 35);
		ppanel.add(lbpayment);
		
		lbtitle1 = hh.clabel(aktheader);	
		lbtitle1.setBounds(310, 20, 730, 35);	
		lbtitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		lbtitle1.setFont(new java.awt.Font("Tahoma", 1, 16));
		ppanel.add(lbtitle1);

		ePanel = new JPanel(null);
		ePanel.setBackground(hh.basecolor1);
		ePanel.setBorder(hh.ztroundborder(Color.BLUE));
		ePanel.setOpaque(false);
		ePanel.setBounds(10, 70, 240, 450);

		lbpaymethod = hh.clabel("Payment method");
		lbpaymethod.setBounds(20, 10, 190, 25);
		lbpaymethod.setHorizontalAlignment(JLabel.CENTER);
		ePanel.add(lbpaymethod);

		cmbpaymethod = gr.grcombo();
		cmbpaymethod.addItem("Cash on delivery");
		cmbpaymethod.addItem("Debit card");
		cmbpaymethod.setBounds(20, 40, 190, 30);
		ePanel.add(cmbpaymethod);
		cmbpaymethod.addActionListener(new ActionListener() { 
			@Override            
			public void actionPerformed(ActionEvent e) { 
				JComboBox comboBox = (JComboBox) e.getSource();   
				String sss = (String) comboBox.getSelectedItem();			
				if (sss.equals("Cash on delivery")) {
					falsemethod(false)	;				
				} else {
					falsemethod(true);
				}
				}
});
		lbcard_name = hh.clabel("Card name");
		lbcard_name.setBounds(20, 75, 190, 25);
		lbcard_name.setHorizontalAlignment(JLabel.CENTER);
		ePanel.add(lbcard_name);

		txcardname = gr.gTextField(100);
		txcardname.setBounds(20, 105, 190, 30);
		ePanel.add(txcardname);

		lbcard_number = hh.clabel("Card number");
		lbcard_number.setBounds(20, 140, 190, 25);
		lbcard_number.setHorizontalAlignment(JLabel.CENTER);
		ePanel.add(lbcard_number);

		txcardnumber = gr.gTextField(100);
		txcardnumber.setBounds(20, 170, 190, 30);
		ePanel.add(txcardnumber);
		
		lbcvv = hh.clabel("CVV");
		lbcvv.setBounds(20, 205, 190, 25);
		lbcvv.setHorizontalAlignment(JLabel.CENTER);
		ePanel.add(lbcvv);

		txcvv = gr.gTextField(100);
		txcvv.setBounds(20, 235, 190, 30);
		ePanel.add(txcvv);		
		
		lbyear_month = hh.clabel("MM/YY");
		lbyear_month.setBounds(20, 270, 190, 25);
		lbyear_month.setHorizontalAlignment(JLabel.CENTER);
		ePanel.add(lbyear_month);

		txyearmonth = MMyytextfield();
		txyearmonth.setBounds(85, 300, 60, 30);
		ePanel.add(txyearmonth);		

		btnpay = gr.sbcs("Pay");
		btnpay.setBounds(50, 355, 130, 35);
		btnpay.setForeground(Color.black);
		btnpay.setBackground(Color.ORANGE);
		ePanel.add(btnpay);
		btnpay.addActionListener(e -> makepay());		

		rPanel = new JPanel(null);
		rPanel.setBackground(hh.basecolor1);
		rPanel.setBorder(hh.ztroundborder(Color.BLUE));
		rPanel.setOpaque(false);
		rPanel.setBounds(290, 70, 755, 450);
		ppanel.add(rPanel);		
		
		 lbptableheader= hh.clabel("Payments table");
		 lbptableheader.setBounds(50, 15, 320, 25);
		 lbptableheader.setHorizontalAlignment(JLabel.CENTER);
		 rPanel.add(lbptableheader);
		
		String[] pcolumnNames = { "Seatno", "Amount", "Payment method" };
		Object[][] pdata = {};
		pmodel = new DefaultTableModel(pdata, pcolumnNames) {};
	    paytable = hh.ztable();
	    paytable.setBorder(hh.borderf);
		paytable.setModel(pmodel);	
		paytable.setPreferredScrollableViewportSize(paytable.getPreferredSize());
		hh.madeheader(paytable);
		hh.setJTableColumnsWidth(paytable, 320, 80, 80, 160);
		int[] cols = { 1};
		hh.cellsright(paytable, cols); 
		pscroll = new JScrollPane();
		pscroll.setViewportView(paytable);
		pscroll.setBounds(50, 50, 320, 300);
		rPanel.add(pscroll);
		
		 lbttableheader= hh.clabel("Tickets table");
		 lbttableheader.setBounds(390, 15, 320, 25);
		 lbttableheader.setHorizontalAlignment(JLabel.CENTER);
		 rPanel.add(lbttableheader);
		 
			String[] tcolumnNames = { "Seatno", "Amount", "Ticketno" };
			Object[][] tdata = {};
			tmodel = new DefaultTableModel(tdata, tcolumnNames){};
		    ticket_table = hh.ztable();
		    ticket_table.setBorder(hh.borderf);
		    ticket_table.setModel(tmodel);	
		    ticket_table.setPreferredScrollableViewportSize( ticket_table.getPreferredSize());
			hh.madeheader(ticket_table);
			hh.setJTableColumnsWidth(ticket_table, 320, 80, 80, 160);
			int[] cols1 = { 1 };
			hh.cellsright(ticket_table, cols1); 
			tscroll = new JScrollPane();
			tscroll.setViewportView( ticket_table);
			tscroll.setBounds(390, 50, 320, 300);
			rPanel.add(tscroll);	
		    ppanel.add(ePanel);

		return ppanel;
	}

	private void makepay() {		
		String paymentmethod =  cmbpaymethod.getSelectedItem().toString();
		String cardname ="";
		String cardnumber ="";
		String cvv = ""; 
		String yearmonth="";
		String smonth = "";
		String syear = "";	
		
		if (paymentmethod.equals("Debit card")) {
		    cardname = txcardname.getText();
		    cardnumber =txcardnumber.getText();
		    cvv = txcvv.getText(); 
		    yearmonth=txyearmonth.getText();
		    smonth = yearmonth.substring(0, 2);
		    syear = yearmonth.substring(3);	
		if (svalidation(cardname, cardnumber, cvv, yearmonth) == true) {
	 		return;
		}		
		}
		try {
		String Sql = "insert into payment (card_name, card_number, cvv, exp_year, exp_month,"
				+ " total_amount, payment_date, payment_method)"
				+ " values ('" + cardname + "','" + cardnumber + "','" + cvv + "','" + syear + "','"
				+ smonth + "','" + itotal_amount + "','"+nowdate +"','"+paymentmethod +"')";				
		       int flag = dh.Insupdel(Sql);
		       if (flag==1) {
		       mypyhid= dd.table_maxid("SELECT MAX(payhid) AS max_id FROM payment");	 
		       
		   	for (int i = 0; i < sdata.length; i++) {
		   		String resid = sdata[i][2].toString();
		   		String amount = sdata[i][1].toString();
		   		String seatno = sdata[i][0].toString();
		   	   Sql =" insert into payments ( resid, amount, payhid) values ('" + resid+"','" +amount 
		   			   + "','"+mypyhid +"')";
		      	flag = dh.Insupdel(Sql);
		      	pmodel.addRow(new Object[] { seatno, amount, paymentmethod  });
		      	
		   	}
		   	int randomNum=0;
		   	String ticketno="";
		   	for (int i = 0; i < sdata.length; i++) {
		   		String resid = sdata[i][2].toString();
		   		String amount = sdata[i][1].toString();
		   		String seatno = sdata[i][0].toString();
		   		randomNum = (int)(Math.random() * 100000);
		   		ticketno ="TC"+ hh.itos(randomNum);		   		
		   	   Sql =" insert into ticket ( resid, tdate, amount, ticketno) values ('" + resid+"','" + nowdate 
		   			   +"','" +amount+ "','"+ticketno +"')";
		      	flag = dh.Insupdel(Sql);
		      	tmodel.addRow(new Object[] { seatno, amount, ticketno  }); 		      	
		   	}
		 	for (int i = 0; i < sdata.length; i++) {
		 		String resid = sdata[i][2].toString();	
		   	   Sql =" update seatreservation set status='Booked' where resid='"+resid +"'";
		      	flag = dh.Insupdel(Sql);	  	
		   	} 
		 	}
		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
		}	
	}
	
	private Boolean svalidation(String cardname, String cardnumber, String cvv, String yearmonth){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();		
		if(sval.emptyvalid(cardname)){
			err.add( "Cardname  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(cardnumber)){
			err.add( "Cardnumber  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(cvv)){
			err.add( "CVV  is empty !");
			ret = true;
		}
		if(sval.emptyvalid(yearmonth) || yearmonth.contains("_")){
			err.add( "MM/YY  is empty !");
			ret = true;
		}
		if (err.size() > 0) {
			JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
		}
	
	return ret;
	}

	private void toreservation() {
		int rows = model.getRowCount();
		if (  rows== 0) {
			return;
		}
		sdata = new Object[rows][3];		
		String ress = "pending";
		try {
			for (int i = 0; i < rows; i++) {
				String seatno = model.getValueAt(i, 0).toString();
				String amount = model.getValueAt(i, 1).toString();			
				String Sql = "insert into seatreservation (passid, seatno, schedid, fare, resdate, status)"
						+ " values ('" + dh.mypassid + "','" + seatno + "','" + myschedid + "','" + amount + "','"
						+ nowdate + "','" + ress + "')";				
				int flag = dh.Insupdel(Sql);
				int myresid = dd.table_maxid("SELECT MAX(resid) AS max_id FROM seatreservation");	
				sdata[i][0] = seatno;
				sdata[i][1] = amount;
				sdata[i][2] = hh.itos(myresid);
				itotal_amount = itotal_amount + hh.stoi(amount);
			}		

		} catch (Exception e) {
			System.err.println("SQLException: " + e.getMessage());
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "sql insert hiba");
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
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
	}

	private void buttActionPerformed(java.awt.event.ActionEvent e) {
		JToggleButton target = (JToggleButton) e.getSource();
		String ezaz = target.getName();
		if (target.isSelected()) {
			if (!myseats.contains(ezaz)) {
				myseats.add(ezaz);
			}
		} else {
			myseats.remove(ezaz);
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
							tbuttons[i][j].setEnabled(false);
						}
					}
				}
			}
		}
	}

	private void whichseat(String pseatno) {
		String sseatno = "";
		String ppseatno = pseatno.trim();
		for (int i = 0; i < myrows; i++) {
			for (int j = 0; j < mycols; j++) {
				if (tbuttons[i][j] != null) {
					String se = tbuttons[i][j].getName();
					System.out.println(se);
					sseatno = se.trim();
					if (sseatno.equals(ppseatno)) {
						tbuttons[i][j].setSelected(false);
					}
				}
			}
		}
	}

	private void tableupload() {
		String text = "";
		for (int i = 0; i < myseats.size(); i++) {
			text = myseats.get(i);
			model.addRow(new Object[] { text, myfare, "Delete" });
		}
	}

	class ClientsTableButtonRenderer extends JButton implements TableCellRenderer {
		public ClientsTableButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setForeground(Color.black);
			setBackground(UIManager.getColor("Button.background"));
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	public class ColoredTButton extends JToggleButton {
		@Override
		public void paintComponent(Graphics g) {
			Color bg;
			if (isSelected()) {
				// bg = Color.RED;
				bg = hh.lpiros;
			} else {
				// bg = Color.green;
				bg = hh.vvzold;
			}
			setBackground(bg);
			super.paintComponent(g);
		}
	}

	public class ClientsTableRenderer extends DefaultCellEditor {
		private JButton button;
		private String label;
		private boolean clicked;
		private int row, col;
		private JTable tabla;

		public ClientsTableRenderer(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
					if (col == 2) {
						int result = JOptionPane.showConfirmDialog(null, "Would  you like to delete this row ?",
								"Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
						if (result == JOptionPane.YES_OPTION) {
							String seatno = tabla.getValueAt(row, 0).toString();
							((DefaultTableModel) tabla.getModel()).removeRow(row);
							whichseat(seatno);
							myseats.remove(seatno);
						}
					}
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.tabla = table;
			this.row = row;
			this.col = column;
			button.setForeground(Color.black);
			button.setBackground(UIManager.getColor("Button.background"));
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			clicked = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (clicked) {
				// JOptionPane.showMessageDialog(button, "Column +row " + col + ", " + row + " -
				// Clicked!");
			}
			clicked = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	public static int findinarr(String arr[], String key) {
		int n = arr.length;
		for (int i = 0; i < n; i++) {
			if (arr[i].equals(key))
				return i;
		}
		return -1;
	}
	public JFormattedTextField MMyytextfield() {
		JFormattedTextField  textField = null;
		MaskFormatter dateMask;
		try {
			dateMask = new MaskFormatter("##/##");
			dateMask.setPlaceholderCharacter('_');	
			textField = new JFormattedTextField(dateMask);
			textField.setColumns(6);
			//textField.setBorder(bb);
			textField.setFont(new Font("Tahoma", 1, 16));			
			textField.setBackground(Color.white);
			textField.setForeground(Color.black);	
		//	textField.setDisabledTextColor(Color.PINK);	
			textField.setPreferredSize(new Dimension(150, 25));	
			textField.setCaretColor(Color.RED);
			textField.putClientProperty("caretAspectRatio", 0.2);
			Border margin = new EmptyBorder(4, 4, 4, 4);
	        Border border = BorderFactory.createLineBorder(Color.BLACK);
	        textField.setBorder(BorderFactory.createCompoundBorder(border, margin));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	   
		return textField;
	} 
private void falsemethod(Boolean bok) {	
	txcardname.setEditable(bok);
	txcardnumber.setEditable(bok);
	txcvv.setEditable(bok);
	txyearmonth.setEditable(bok);	
	if (bok==false) {
		txcardname.setText("");
		txcardnumber.setText("");
		txcvv.setText("");
		txyearmonth.setText("");
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
					new Seatreservation(schedid, routeid, busid, fare, header);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader, lbtitle,lbtitle1, lbpicture, lbpayment, lbpaymethod, lbcard_name, lbcard_number, lbcvv,
	lbyear_month, lbptableheader, lbttableheader;
	JTextField txcardname, txcardnumber, txcvv,  txyearmonth;
	JPanel basePanel, payPanel, seatPanel, tPanel, picPanel, cardPanel, ePanel, rPanel;
	CardLayout cards;
	ColoredTButton tbuttons[][];
	String[][] arr;
//String sn = "";
	JButton btntotable, btnreservation, btnpay;
	JTable btable, restable, paytable, ticket_table;
	JScrollPane scroll, pscroll, tscroll;
	JComboBox cmbpaymethod;
}
