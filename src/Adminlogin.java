import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import Classes.Grlib;
import Classes.Hhelper;
import Classes.Spallvalid;
import Databaseop.DatabaseHelper;
import Databaseop.Databaseop;

public class Adminlogin extends JFrame {
	ResultSet rs;
	Connection con = null;
	Grlib gr = new Grlib();
	DatabaseHelper dh = new DatabaseHelper();
	Databaseop dd = new Databaseop();
	Hhelper hh = new Hhelper();
	Spallvalid sval = new Spallvalid();

	Adminlogin() {
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
		setSize(new java.awt.Dimension(1160, 600));
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);
		setTitle("Admin login");
		lbheader = hh.fflabel("Admin login");
		lbheader.setBounds(40, 10, 200, 35);
		add(lbheader);
		setLayout(null);
		setLocationRelativeTo(null);
		this.getContentPane().setBackground(hh.basecolor1);

		basePanel = new JPanel(null);
		basePanel.setBounds(20, 50, 1100, 480);
		basePanel.setBorder(hh.ztroundborder(Color.BLUE));
		basePanel.setOpaque(false);
		add(basePanel);

		ePanel = new JPanel(null);
		ePanel.setBounds(20, 100, 350, 250);
		ePanel.setBorder(hh.ztroundborder(Color.BLUE));
		ePanel.setOpaque(false);
		basePanel.add(ePanel);

		picPanel = new JPanel(null);
		picPanel.setBounds(385, 25, 695, 430);
		picPanel.setBorder(hh.ztroundborder(Color.BLUE));
		picPanel.setOpaque(false);
		basePanel.add(picPanel);
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("images/adminlogpic.png"));
		Image i2 = i1.getImage().getScaledInstance(680, 410, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		lbpicture = new JLabel(i3);
		lbpicture.setBounds(7, 7, 680, 410);
		picPanel.add(lbpicture);
		basePanel.add(picPanel);

		lbusername = hh.clabel("Admin name");
		lbusername.setBounds(10, 40, 120, 30);
		ePanel.add(lbusername);

		txusername = gr.gTextField(100);
		txusername.setBounds(140, 40, 180, 30);
		ePanel.add(txusername);

		lbpassword = hh.clabel("Password");
		lbpassword.setBounds(10, 90, 120, 30);
		ePanel.add(lbpassword);

		txpassword = gr.gpasstField(100);
		txpassword.setBounds(140, 90, 180, 30);	
		ePanel.add(txpassword);

		btnlogin = gr.sbcs("Login");
		btnlogin.setBounds(160, 140, 130, 30);
		btnlogin.setForeground(Color.black);
		btnlogin.setBackground(hh.lpiros);
		ePanel.add(btnlogin);
		btnlogin.addActionListener(e -> loginvalidate());
		setVisible(true);
	}

	private void loginvalidate() {
		Boolean boke = false;
		String password = String.valueOf(txpassword.getPassword());
		String username = txusername.getText();
		String sql = "SELECT * FROM adminuser where username= '" + username + "' and " + "password ='" + password + "'";
		try {
			ResultSet rs = dh.GetData(sql);
			if (rs.next()) {
				dh.myadminid = rs.getString("adid");
				boke = true;
			} else {
				JOptionPane.showMessageDialog(this, "Wrong  username or password !");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
		dh.CloseConnection();
		if (boke == true) {		
			new Adminhome();
			dispose();
		} else {
			return;
		}

	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Adminlogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lbheader;
	JPanel basePanel, ePanel, picPanel;
	JLabel lbusername, lbpassword, lbpicture;
	JTextField txusername;
	JPasswordField txpassword;
	JButton btnlogin;
}
