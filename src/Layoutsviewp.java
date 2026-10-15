import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;

import Classes.Grahelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;
import Databaseop.Databaseop.Payment;

public class Layoutsviewp extends JFrame{
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Grahelper gh = new Grahelper();
	Hhelper hh = new Hhelper();
	File lastDirectory = new File(System.getProperty("user.home"));
	String Abspath = "";
	String basePath = new File("").getAbsolutePath();	
	
	Layoutsviewp(){
		init();
		ptable_update();
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
		setSize(new java.awt.Dimension(1045, 685));
		setTitle("Layoutsview");
		setLocationRelativeTo(null);
		getContentPane().setBackground(hh.basecolor1);
		lbheader = hh.fflabel("Layoutsview");
		lbheader.setBounds(25, 0, 250, 30);
		add(lbheader);

		basePanel = new JPanel(null);
		basePanel.setBounds(15, 35, 1000, 598);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		 basePanel.setOpaque(false);
		add(basePanel);
		
		ptable = hh.ztable();
		ptable.setRowHeight(25);
	
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		String[] columnNames = { "blid","Name","Rows","Columns", "Capacity", "Path" };
		Object[][] data = { { null, null, null, null,null, null}};
		model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 5:
					return String.class;
				default:
					return String.class;
				}
			}
		};
		ptable.setModel(model);
		ptable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {			
				try {
					int row = ptable.getSelectedRow();
					if (row > -1) {
						String sglid = model.getValueAt(row, 0).toString();					
						String path = model.getValueAt(row, 5).toString();
		        		String picpath = basePath + path;
						Showpict(picpath);
						String srow =model.getValueAt(row, 2).toString();
						String scolumn=model.getValueAt(row, 3).toString();
						fill_table(srow, scolumn, sglid);
				}
				} catch (Exception e) {
					System.out.println("sql error!!!");
				}
			}
		});

		hh.madeheader(ptable);
		ptable.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				ptable.scrollRectToVisible(ptable.getCellRect(ptable.getRowCount() - 1, 0, true));
			}
		});

		jScrollPane = new JScrollPane(ptable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		hh.setJTableColumnsWidth(ptable, 500, 0,  80, 70, 75, 75, 200);
	
		jScrollPane.setViewportView(ptable);
		jScrollPane.setBounds(260, 10, 510, 170);
		jScrollPane.getViewport().setBackground(hh.skek);
		jScrollPane.setBorder(hh.lineo);
		basePanel.add(jScrollPane);				
		
		btndelete = hh.cbutton("Delete");
		btndelete.setBackground(hh.vpiros);
		btndelete.setBounds(795, 70, 110, 30);
		basePanel.add(btndelete);
		btndelete.addActionListener(e -> deletelayout());
		basePanel.add(btndelete);
		
		lbpicture = hh.clabel("Bus Layout map");
		lbpicture.setBounds(550, 190, 160, 30);
		basePanel.add(lbpicture);		

		picturePanel = new JPanel(null);
		picturePanel.setBounds(440, 215, 400, 370);
		picturePanel.setBorder(hh.line);		
		basePanel.add(picturePanel);
		
		lbuploadpic= hh.clabel("");
		lbuploadpic.setBounds(1, 1, 390, 365);
		picturePanel.add(lbuploadpic);
	
		 lbtablegrid = hh.clabel("Bus Layout grid");
		 lbtablegrid.setBounds(205, 190, 160, 30);
		 basePanel.add(lbtablegrid);		
		
		tablePanel = new JPanel(null);
		tablePanel.setBounds(170, 215, 250, 370);
		tablePanel.setBorder(hh.line);
		basePanel.add(tablePanel);		
		setVisible(true);
	}
	
	private void ptable_update() {
		DefaultTableModel model = (DefaultTableModel) ptable.getModel();
		model.setRowCount(0);
		String Sql = "select  blid, lname, rows, columns, capacity, path  from buslayout ";	
		try {
			rs = dh.GetData(Sql);
			while (rs.next()) {
				String blid = rs.getString("blid");
				String name = rs.getString("lname");
				String rows = rs.getString("rows");
				String columns = rs.getString("columns");			
				String capacity = rs.getString("capacity");
			    String path =rs.getString("path");				
					model.addRow(new Object[] { blid, name, rows, columns, capacity, path});
			}	
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "blid", "Name", "Rows", "Columns", "Capacity", "Path" };
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ptable, 500, 0,  80, 70, 75, 75, 200);
		int[] cols = { 2, 3, 4 };
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
	
	void Showpict(String pPath) {		
		File pctFile = new File( pPath);	
		BufferedImage img = null;	
		try {
			img = ImageIO.read(pctFile);
			int width = img.getWidth();
			int height = img.getHeight();
				BufferedImage image = gh.resizeImage(img, 390, 365);
				lbuploadpic.setIcon(new ImageIcon(image));			
		} catch (IOException e) {
			System.out.println("Picture do not load !");
			e.printStackTrace();
		}
	}
	private void fill_table(String srows, String scolumns, String sglid) {	
		int rows  = hh.stoi(srows);
		int cols = hh.stoi(scolumns);
		String [][] arr;
		arr = new String[rows][cols];
		basePanel.remove(tablePanel);
		basePanel.repaint();
		basePanel.revalidate();
		tablePanel.removeAll();
		tablePanel = new JPanel(null);	
		tablePanel.setBounds(170, 215, 250, 370);
		tablePanel.setBorder(hh.line);
		basePanel.add(tablePanel);		
		tablePanel.repaint();			
	   jt = new JTable(rows, cols);
		String sql = "select * from busgrid where glid='"+sglid +"'";
		ResultSet rs = dh.GetData(sql);
		int i =0;
		try {
			while (rs.next()) {
			arr[i][0] = rs.getString("col1");
			arr[i][1] = rs.getString("col2");
			arr[i][2] = rs.getString("col3");
			arr[i][3] = rs.getString("col4");
			arr[i][4] = rs.getString("col5");
			if (cols>5) {
			arr[i][5] = rs.getString("col6");
			}
			i++;
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}	   
	   
	   for (int row = 0; row < rows; row++) {			
	        for (int col = 0; col < cols; col++) {
	           jt.setValueAt(arr[row][col], row, col);
	        }	       
	    }	
		Font font = new Font("tahoma", Font.BOLD, 16);
		jt.setFont(font);	
		jt.setTableHeader(null);
	//	jt.setSelectionBackground(hh.lillas);
		jt.setRowHeight(25);
		jt.setRowSelectionAllowed(false);	
		jt.setCellSelectionEnabled(false);
		jt.setBorder(hh.borderf2);
		jt.setBackground(hh.citrom);
		jt.setGridColor(Color.black);
		jt.setOpaque(true);
		jt.setIntercellSpacing(new Dimension(0, 0));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		//  final TableCellEditor editor = new DefaultCellEditor(field);
		for (int x = 0; x < cols; x++) {
			jt.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	//		jt.getColumnModel().getColumn(x).setCellEditor(editor);
		}	
		 jScrollPane1= new JScrollPane(jt);
		 jScrollPane1.setBounds(5, 5, 240, 360);
		add( jScrollPane1);
		tablePanel.add( jScrollPane1);
	}
	private void deletelayout() {
		int flag=0;
		DefaultTableModel m1 = (DefaultTableModel) ptable.getModel();
		int row = ptable.getSelectedRow();
		if (row >= 0) {
			String sglid = model.getValueAt(row, 0).toString();	
			if (sglid.equals("")) {
				return;
			}
			int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
			if (a == JOptionPane.YES_OPTION) {				
				String sql = "delete from busgrid where glid='" + sglid + "'";
				flag = dh.Insupdel(sql);
				sql = "delete from buslayout where blid='" + sglid + "'";
				flag = dh.Insupdel(sql);				
				if (flag > 0) {
					m1.removeRow(row);
				}			
			}
			}
		}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				 new Layoutsviewp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lbheader, lbpicture, lbtablegrid, lbuploadpic;
	JPanel basePanel, picturePanel, tablePanel;
	JTable ptable, jt;
	JScrollPane jScrollPane, jScrollPane1;
	DefaultTableModel model;
	Image image;
	JButton btndelete;
}
