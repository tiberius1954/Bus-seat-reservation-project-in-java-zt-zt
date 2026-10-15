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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Classes.Grahelper;
import Classes.Grlib;
import Classes.Hhelper;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Layoutsview extends JFrame{
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
	
	Layoutsview(){
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
		setSize(new java.awt.Dimension(1045, 650));
		setTitle("Layoutsview");
		setLocationRelativeTo(null);
		getContentPane().setBackground(hh.basecolor1);
		lbheader = hh.fflabel("Layoutsview");
		lbheader.setBounds(25, 10, 250, 35);
		add(lbheader);

		basePanel = new JPanel(null);
		basePanel.setBounds(15, 50, 1000, 540);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		 basePanel.setOpaque(false);
		add(basePanel);
		
		ptable = hh.ztable();
		ptable.setRowHeight(160);
	
		ptable.setTableHeader(new JTableHeader(ptable.getColumnModel()) {
			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 25;
				return d;
			}
		});

		String[] columnNames = { "blid","Name","Rows","Columns", "Capacity", "Image" };
		Object[][] data = { { null, null, null, null,null, null}};
		model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 5:
					return Icon.class;
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
//					if (row > -1) {
//						String path = m1.getValueAt(row, 4).toString();
//						String picpath = basePath + path;
//						Showpict(picpath);
//					}
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
		hh.setJTableColumnsWidth(ptable, 540, 0,  100, 80, 80, 80, 165);
	
		jScrollPane.setViewportView(ptable);
		jScrollPane.setBounds(20, 20, 550, 320);
		jScrollPane.getViewport().setBackground(hh.skek);
		jScrollPane.setBorder(hh.lineo);
		basePanel.add(jScrollPane);		
		setVisible(true);

	}
	public void ptable_update() {
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
			String path =basePath + rs.getString("path");	
	
				File file = new File(path);
				BufferedImage image;
				try {
					image = ImageIO.read(file);
					int h = image.getHeight();
					int w = image.getWidth();
					BufferedImage img = gh.createThumbnail(image, 160, 160);
					Icon icon = new ImageIcon(img);
					image.flush();
					image=null;
					model.addRow(new Object[] { blid, name, rows, columns, capacity, icon });
				} catch (IOException e) {				
					e.printStackTrace();
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dh.CloseConnection();
		}
		String[] fej = { "blid", "Name", "Rows", "Columns", "Capacity", "Image" };
		((DefaultTableModel) ptable.getModel()).setColumnIdentifiers(fej);
		hh.setJTableColumnsWidth(ptable, 540, 0,  100, 80, 80, 80, 165);
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
	
	

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				 new Layoutsview();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	JLabel lbheader;
	JPanel basePanel;
	JTable ptable;
	JScrollPane jScrollPane;
	DefaultTableModel model;
	Image image;
}
