import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EventObject;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.imageio.ImageIO;
import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableCellRenderer;
import org.sqlite.util.StringUtils;
import Classes.Allvalid;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Layouts extends JFrame {
 JFormattedTextField field = createFormattedTextField();
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Spallvalid sval = new Spallvalid();
	StringUtils hss = new StringUtils();
	private ImageIcon originalImageIcon;
	// private double scaleFactor = 1.0;
	private double scaleFactor = 0.90;
	private ImageIcon scaledImageIcon;
   private  String  myPath ="";
	Layouts() {
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
		setSize(new java.awt.Dimension(1045, 650));
		setTitle("Layouts");
		setLocationRelativeTo(null);
		getContentPane().setBackground(hh.basecolor1);

		lbheader = hh.fflabel("Layouts");
		lbheader.setBounds(25, 10, 250, 35);
		add(lbheader);

		basePanel = new JPanel(null);
		basePanel.setBounds(15, 50, 1000, 540);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		 basePanel.setOpaque(false);
		add(basePanel);

		lbname = hh.clabel("Name");
		lbname.setBounds(10, 30, 120, 30);
		basePanel.add(lbname);
	
		txlname =gr.gTextField(100);
		txlname.setBounds(140, 30, 150, 30);
		txlname.addKeyListener(hh.MUpper());
		basePanel.add(txlname);	

		lbrows = hh.clabel("Rows");
		lbrows.setBounds(10, 70, 120, 30);
		basePanel.add(lbrows);

		txrows =gr.gTextField(50);
		txrows.setBounds(140, 70, 40, 30);
		txrows.setHorizontalAlignment(JTextField.RIGHT);
		basePanel.add(txrows);
		txrows.addKeyListener(hh.Onlynum());

		lbcolumns = hh.clabel("Columns");
		lbcolumns.setBounds(10, 110, 120, 30);
		basePanel.add(lbcolumns);

		txcolumns =gr.gTextField(50);
		txcolumns.setBounds(140, 110, 40, 30);
		txcolumns.setHorizontalAlignment(JTextField.RIGHT);
		basePanel.add(txcolumns);
		txcolumns.addKeyListener(hh.Onlynum());

		lbcapacity = hh.clabel("Capacity");
		lbcapacity.setBounds(10, 150, 120, 30);
		basePanel.add(lbcapacity);

		txcapacity = gr.gTextField(50);
		txcapacity.setBounds(140, 150, 40, 30);
		txcapacity.setHorizontalAlignment(JTextField.RIGHT);
		basePanel.add(txcapacity);
		txcapacity.addKeyListener(hh.Onlynum());

		lbdriverpos = hh.clabel("Driver position");
		lbdriverpos.setBounds(10, 190, 120, 30);
		basePanel.add(lbdriverpos);
	
		cmbdriverpos= gr.grcombo();
		cmbdriverpos.setBounds(140, 190, 80, 30);
		basePanel.add(cmbdriverpos);
		cmbdriverpos.setModel(new DefaultComboBoxModel(new String[] { "Left", "Right" }));
		cmbdriverpos.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				writePanel();
			}
			}		
			});
		
	    btnsave = gr.sbcs("Save");
		btnsave.setBounds(50, 270, 110, 30);
		btnsave.setBackground(hh.lpiros);
		basePanel.add(btnsave);		
		btnsave.addActionListener(e -> savebutt());

		btncancel = gr.sbcs("Cancel");
		btncancel.setBackground(hh.vvzold);
		btncancel.setBounds(170, 270, 110, 30);
		basePanel.add(btncancel);
		btncancel.addActionListener(e -> cancelbutt());

		lbpicture = hh.clabel("Bus Layout map");
		lbpicture.setBounds(430, 10, 160, 30);
		basePanel.add(lbpicture);		

		picturePanel = new JPanel(null);
		picturePanel.setBounds(320, 45, 400, 370);
		picturePanel.setBorder(hh.line);
		basePanel.add(picturePanel);

		lbpicfilename = hh.clabel("");
		lbpicfilename.setBounds(330, 490, 250, 25);
		basePanel.add(lbpicfilename);

		lbuploadpic = hh.clabel("");
		lbuploadpic.setBounds(0, 0, 390, 370);
		lbuploadpic.setVerticalAlignment(SwingConstants.CENTER);
		lbuploadpic.setHorizontalAlignment(SwingConstants.CENTER);
		picturePanel.add(lbuploadpic);

		//btnupload = hh.cbutton("Chooser");
		btnupload =gr.sbcs("Chooser");
		btnupload.setBounds(485, 440, 120, 30);
		btnupload.setBackground(hh.vkek);
		basePanel.add(btnupload);
		btnupload.addActionListener(e -> picupload());
		
		eastPanel  = new JPanel();
		eastPanel.setPreferredSize(new Dimension(125, 30));		 
        westPanel  = new JPanel();
        westPanel.setPreferredSize(new Dimension(125, 30));
		
		driverPanel = new JPanel(null);
		driverPanel.setBounds(730, 5, 250, 35);
		driverPanel.setLayout(new BorderLayout(0, 2));
		driverPanel.add(eastPanel, BorderLayout.EAST);
        driverPanel.add(westPanel, BorderLayout.WEST);
		basePanel.add(driverPanel);		

		lbleft = hh.clabel("Driver");
		lbleft.setOpaque(true);
		lbleft.setBackground(new Color(249, 73, 58));
		lbleft.setBounds(0, 0, 70, 25);
		lbleft.setHorizontalAlignment(JLabel.CENTER);
		lbleft.setVerticalAlignment(JLabel.CENTER);
		westPanel.add(lbleft);
		
		lbright = hh.clabel("");
		lbright.setOpaque(true);
		lbright.setBackground(hh.basecolor1);
		lbright.setBounds(0, 0, 70, 25);
		lbright.setHorizontalAlignment(JLabel.CENTER);
		lbright.setVerticalAlignment(JLabel.CENTER);
		eastPanel.add(lbright);
		writePanel();

		tablePanel = new JPanel(null);
		tablePanel.setBounds(730, 45, 250, 370);
		tablePanel.setBorder(hh.line);
		basePanel.add(tablePanel);		
	
		btngener = gr.sbcs("Generate table");
		btngener.setBounds(780, 440, 160, 30);
		btngener.setBackground(hh.vkek);
		basePanel.add(btngener);
		btngener.addActionListener(e -> tablegenerate());
		setVisible(true);
	}
		private void savebutt() {	
		int myid;
		String glid ="";
		String lname =txlname.getText(); 
		String srows	=	txrows.getText();
		String scolumns = txcolumns.getText();
		String scapacity =txcapacity.getText();
		String position =cmbdriverpos.getSelectedItem().toString();
		String spath = myPath;
		
	if (validation(lname, srows, scolumns, scapacity) == true) {
 		return;
	}
	
	if (jt ==null) {
		return;
	}
	int rcount = jt.getRowCount();
	if( rcount == 0){
		return;
	}
	int colcount=  jt.getColumnCount();
   String sql = "insert into buslayout  (lname, rows,  columns, capacity, driver_position, path ) "
                  + " values  ('"+lname +"','" + srows +"','" + scolumns +"','"+ scapacity
                  +"','" + position +"','" + spath +"')";

try {
int flag = dh.Insupdel(sql);
if (flag > 0) {
	myid = dd.table_maxid("SELECT MAX(blid) AS max_id from buslayout");
	glid = hh.itos(myid);
	String ss ="";
	if (colcount== 5) {
	ss ="col1, col2, col3, col4, col5)";
	}else { 
		ss ="col1, col2, col3, col4, col5, col6)";
	}
	for (int row = 0; row < rcount; row++) {	
		sql ="insert into  busgrid (glid," + ss + " values ('"+glid;
		
        for (int col = 0; col < colcount; col++) {
            String value = jt.getValueAt(row, col).toString();            
            sql = sql +"','"+value ;
        }
        sql = sql +"')";
        dh.Insupdel(sql);
    }	
	hh.ztmessage("Success", "Message");
}
} catch (Exception e) {
	System.err.println("SQLException: " + e.getMessage());
	e.printStackTrace();
	JOptionPane.showMessageDialog(null, "sql insert hiba");
}
clearFields();
}	
	
	private Boolean validation(String name, String srows, String  scolumns, String scapacity){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();
		
		if(sval.emptyvalid(name)){
			err.add( "Name is empty !");
			ret = true;
		}
		if (sval.emptyvalid(srows)) {
			err.add("Rows is empty !");
			ret = true;
	   }
		if (sval.emptyvalid(scolumns)) {
			err.add("Columns is empty");
			ret = true;
		}
		if (sval.emptyvalid(scapacity)) {
			err.add("Capacity is empty");
			ret = true;
		}		
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
		return ret;
	}
	
	private void cancelbutt() {
		clearFields();
		txlname.requestFocus(true);
	}
	
	private void clearFields() {
		txlname.setText(""); 
		txrows.setText("");
		txcolumns.setText("");
		txcapacity.setText("");
	}

	private void picupload() {
		String basePath = new File("").getAbsolutePath();
		JFileChooser fileChooser = new JFileChooser();
		String dirName = basePath + "\\seatmaps";
		System.out.println("Dir name: "+ dirName);
		File theDir = new File(dirName);
		if (theDir.exists()) {
			System.out.println("Dir  exists !");
		} else {
			System.out.println("Dir no exist !");
			return;
		}
		fileChooser.setCurrentDirectory(theDir);
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println("selected file: " +selectedFile);		
			try {
				originalImageIcon = new ImageIcon(ImageIO.read(selectedFile));
				displayImage();
				lbpicfilename.setText(selectedFile.getName());
				myPath =  "\\seatmaps\\" +selectedFile.getName();
				System.out.println("myPath: " +myPath);			
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error");
			}
		}
	}

	private void displayImage() {
		if (originalImageIcon != null) {
			int newWidth = (int) (originalImageIcon.getIconWidth() * scaleFactor);
			int newHeight = (int) (originalImageIcon.getIconHeight() * scaleFactor);
			scaledImageIcon = new ImageIcon(
					originalImageIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
               lbuploadpic.setIcon(scaledImageIcon);
		}
	}

	private void tablegenerate() {	
		basePanel.remove(tablePanel);
		basePanel.repaint();
		basePanel.revalidate();
		tablePanel.removeAll();
		tablePanel = new JPanel(null);
		tablePanel.setBounds(730, 45, 250, 370);
		tablePanel.setBorder(hh.line);
		basePanel.add(tablePanel);		
		tablePanel.repaint();		
		tablePanel.invalidate();		
	    int rows =0;
	   int cols=0;
	   String srows = txrows.getText();
	   String scols = txcolumns.getText();
	   if (hh.zempty(scols) || hh.zempty(scols)) {
		   return;
	   }
	   rows = hh.stoi(srows);
		cols = hh.stoi(scols);

	   jt = new JTable(rows, cols);
	   for (int row = 0; row < rows; row++) {			
	        for (int col = 0; col < cols; col++) {
	           jt.setValueAt("", row, col);
	        }	       
	    }	
		Font font = new Font("tahoma", Font.BOLD, 16);
		jt.setFont(font);
		jt.addPropertyChangeListener(evt -> {
			if ("tableCellEditor".equals(evt.getPropertyName())) {
				if (jt.isEditing()) {
					TableCellEditor editor = jt.getCellEditor();
					if (editor instanceof DefaultCellEditor) {
						Component comp = ((DefaultCellEditor) editor).getComponent();
						if (comp != null) {
							comp.setFont(font);   
					   }
					}
				}
			}
		});
		jt.setTableHeader(null);
		jt.setSelectionBackground(hh.lillas);
		jt.setRowHeight(25);
		jt.setRowSelectionAllowed(false);	
		jt.setBorder(hh.borderf2);
		jt.setBackground(hh.citrom);
		jt.setGridColor(Color.black);
		jt.setOpaque(true);
		jt.setIntercellSpacing(new Dimension(0, 0));
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		  final TableCellEditor editor = new DefaultCellEditor(field);
		for (int x = 0; x < cols; x++) {
			jt.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
			jt.getColumnModel().getColumn(x).setCellEditor(editor);
		}
		jt.setCellSelectionEnabled(true);
		scrollPane = new JScrollPane(jt);
		scrollPane.setBounds(5, 5, 240, 360);
		add(scrollPane);
		tablePanel.add(scrollPane);
	}

	  private JFormattedTextField createFormattedTextField() {
	        JFormattedTextField field = new JFormattedTextField(createFormatter("##"));
	        return field;
	    }
	    protected MaskFormatter createFormatter(String s) {
	        MaskFormatter formatter = null;
	        try {
	            formatter = new MaskFormatter(s);
	        } catch (java.text.ParseException exc) {
	            System.err.println("formatter is bad: " + exc.getMessage());
	            System.exit(-1);
	        }
	        return formatter;
	    }
	    
	    private  void  writePanel() {	
	    	Object selected =cmbdriverpos.getSelectedItem();
	    	 if (selected != null) {
	    		 String text = selected.toString();
	    		 if(text=="Right") {
	    			 lbright.setText("Driver");
	    			 lbright.setBackground(new Color(249, 73, 58));
	    			 lbleft.setText("");
	    			 lbleft.setBackground(hh.basecolor1);
	    			 lbleft.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, Color.DARK_GRAY));
	    		 }else {
	    			 lbleft.setText("Driver");
	    			 lbleft.setBackground(new Color(249, 73, 58));
	    			 lbright.setText("");	
	    			 lbright.setBackground(hh.basecolor1);	
	    			 lbright.setBorder(BorderFactory.createMatteBorder(1, 1, 1	, 0, Color.DARK_GRAY));
	    		 }
	    	 }	       
	    }

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Layouts ts = new Layouts();			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JPanel basePanel, picturePanel, tablePanel, driverPanel, eastPanel, westPanel;
	JLabel lbheader, lbname, lbrows, lbcolumns, lbcapacity, lbdriverpos, lbpicture, lbpicfilename,
	lbuploadpic, lbtable, lbleft, lbright;
	JTextField txlname, txrows, txcolumns, txcapacity;
	JComboBox cmbdriverpos;
	JButton btnupload, btngener, btnsave, btncancel;
	JScrollPane scrollPane;
	JTable jt;
}


