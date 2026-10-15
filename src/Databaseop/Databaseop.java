package Databaseop;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import Classes.Hhelper;
import Classes.Spallvalid;


public class Databaseop {
	Connection con;
	Statement stmt;
	PreparedStatement pst;
	ResultSet rs;
	Spallvalid sval = new Spallvalid();
	DatabaseHelper dh = new DatabaseHelper();
	Hhelper hh = new Hhelper();	
	Bus bus;
    Route route;
	Payment payment;

	public String myidfv(JTable dtable) {
		String retid = "";
		try {
			DefaultTableModel model = (DefaultTableModel) dtable.getModel();
			int row = dtable.getSelectedRow();
			if (row > -1) {
				retid = model.getValueAt(row, 0).toString();
			}
		} catch (Exception e) {
			System.out.println("sql error!!!");
		}
		return retid;
	}

	public int data_delete(JTable dtable, String sql) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		int sIndex = dtable.getSelectedRow();
		if (sIndex < 0) {
			return flag;
		}
		String iid = d1.getValueAt(sIndex, 0).toString();
		if (iid.equals("")) {
			return flag;
		}
		int a = JOptionPane.showConfirmDialog(null, "Do you really want to delete ?");
		if (a == JOptionPane.YES_OPTION) {
			int selections[] = dtable.getSelectedRows();
			int lastIndex = selections[0];
			String vsql = sql + iid;
			flag = dh.Insupdel(vsql);
			if (flag > 0) {
				d1.removeRow(sIndex);
			}
			if (dtable.getRowCount() > 0 && lastIndex> 0) {
				dtable.setRowSelectionInterval(lastIndex - 1, lastIndex - 1);
			}
		}
		return flag;
	}
	
	public Boolean pvalidation(String pname, String username, String phone, String email, 
			String address, String password, String gender){
		Boolean ret = false;
		ArrayList<String> err = new ArrayList<String>();
		
		if(sval.emptyvalid(pname)){
			err.add( "Name is empty !");
			ret = true;
		}
		if (sval.emptyvalid(username)) {
			err.add("Username is empty !");
			ret = true;
	   }
		if (sval.emptyvalid(phone)) {
			err.add("Phone is empty !");
			ret = true;
		}
		if (sval.emptyvalid(email)) {
			err.add("Email is empty !");
			ret = true;
		}
		if (sval.emptyvalid(address)) {
			err.add("Address is empty !");
			ret = true;
		}
		if (sval.emptyvalid(password)) {
			err.add("Password is empty !");
			ret = true;
		}
		if (sval.emptyvalid(gender)) {
			err.add("Gender  is empty !");
			ret = true;
		}
		
			if (err.size() > 0) {
				JOptionPane.showMessageDialog(null, err.toArray(), "Error message", 1);
			}
		return ret;
	}

	public int tdata_delete(JTable dtable, String sql, int row) {
		int flag = 0;
		DefaultTableModel d1 = (DefaultTableModel) dtable.getModel();
		flag = dh.Insupdel(sql);
		if (flag == 1) {
			d1.removeRow(row);
		}
		return flag;
	}

	public int table_maxid(String sql) {
		int myid = 0;
		try {
			rs = dh.GetData(sql);
			if (!rs.next()) {
				System.out.println("Error.");
			} else {
				myid = rs.getInt("max_id");
			}
			dh.CloseConnection();
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
		}
		return myid;
	}

	public Boolean cannotdelete(String sql) {
		Boolean found = false;
		rs = dh.GetData(sql);
		try {
			if (rs.next()) {
				found = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		dh.CloseConnection();
		return found;
	}

	public boolean recordfound(String Query) {
		boolean found = false;
		int count = 0;
		try {
			rs = dh.GetData(Query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			dh.CloseConnection();
		} catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}
		if (count > 0) {
			found = true;
		}
		return found;
	}

	public int manyrecord(String Query) {
		int count = 0;
		try {
			rs = dh.GetData(Query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
			dh.CloseConnection();
		} catch (SQLException SQLe) {
			System.out.println("Count(*) error " + SQLe.toString());
		}
		return count;
	}
	
	public void cmbstartcombofil(JComboBox mycombo){
        try {
        	mycombo.removeAllItems();
			String sql = "select distinct trim(origin) as origin  from routes order by origin";
			ResultSet rs = dh.GetData(sql); 
          while (rs.next()) {
               String origin = rs.getString("origin");
              mycombo.addItem(origin);
       }   
          dh.CloseConnection();
  
	} catch (Exception e) {
		e.printStackTrace();
    }
	}   
    
	public void cmbdestinationcombofil(JComboBox mycombo){ 
		try {
		 mycombo.removeAllItems();
       String sql = "select distinct trim(destination) as destination  from routes order by destination";
    	rs = dh.GetData(sql);   
       while (rs.next()) {
        String destination = rs.getString("destination");
        mycombo.addItem(destination);       
    } 
     dh.CloseConnection(); 
      } catch (Exception e) {
    	  e.printStackTrace();
        }
    }   
	
	public void cmblayoutcombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Layout  A = new Layout (0, "","");
		mycombo.addItem(A);	
		sql= "select blid, lname, capacity from buslayout order by upper(lname)";	
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Layout(rs.getInt("blid"), rs.getString("lname"), rs.getString("capacity"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();	
		} catch (SQLException e) {			
			e.printStackTrace();
		}			
	}
	public class Layout {
		  int blid;	
		  String lname;	
		  String capacity;
	
		  public Layout (int blid, String lname, String capacity) {
		    this.blid = blid;
		    this.lname = lname;
		    this.capacity = capacity;
		  }
		  @Override
		  public String toString() 
		  { 	
		     	//return lname + " " + capacity; 	 
		     	return  ( hh.zempty(lname) ) ? ""  :  lname + "  (" + capacity+")";
		  } 
		 public int getBlid() {
			  return blid;
		  }		
		 public void setBlid(int blid) {
			  this.blid = blid;
		  }
		
		 public void setLname(String lname) {
			  this.lname = lname;
		  }
		 public String getLname() {
			 return lname;
		 }		
		 public void setCapacity(String capacity) {
			  this.capacity = capacity;
		  }
		 public String getCapacity() {
			 return capacity;
		 }		 
		}

	public void cmbpaymentcombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
//		Payment A = new Payment(0, "");
		Payment A = new Payment();
	//	mycombo.addItem(A);
		sql = "select pid, name from payments order by upper(name)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Payment(rs.getInt("pid"), rs.getString("name"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void cmbroutescombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Route A = new Route(0, "");
	
	 	mycombo.addItem(A);
		sql = "select routeid,  origin || '-' || destination as rname  from routes  order by upper(rname)";
		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Route(rs.getInt("routeid"), rs.getString("rname"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		public class Route{
			  int routeid;	
			  String rname;	
			  public Route() {			  
			  }
			  public Route (int routeid, String rname) {
			    this.routeid = routeid;
			    this.rname = rname;			 
			  }
			  @Override
			  public String toString() 
			  { 	
			     	return rname; 	 
			  } 
			 public int getRouteid() {
				  return routeid;
			  }		
			 public void setRouteid(int routeid) {
				  this.routeid = routeid;
			  }
			
			 public void setRname(String rname) {
				  this.rname = rname;
			  }
			 public String getRname() {
				 return rname;
			 }		
			}
		
		public class Bus {
		    private int busid;
		    private String busno;		   

		    public Bus(int busid, String busno) {
		        this.busid = busid;
		        this.busno = busno;       
		    }	
		    
		    private void setBusid(int  busid) {
		    	this.busid = busid;
		    }
		    
		    private void setBusno(String busno) {
		    	this.busno = busno;
		    }		    
		    
		    @Override
			public String toString() {
				return busno;
			}		    
		    
		    public int getBusid() {
		    	return busid; 
		    }
		    public String getBusno() { return busno; }    
		} 
		
		
		public void cmbbusescombofill(JComboBox mycombo) {
			String sql;
			mycombo.removeAllItems();
    		Bus A = new Bus(0, "");
    		mycombo.addItem(A);	
			sql = "select busid, busno from buses order by upper(busno)";
			ResultSet rs = dh.GetData(sql);
			try {
				while (rs.next()) {
					A = new Bus(rs.getInt("busid"), rs.getString("busno"));
					mycombo.addItem(A);
				}
				dh.CloseConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}				
	
	public class Payment {
		String name;
		int pid;

		public Payment() {
		}

		public Payment(int pid, String name) {
			this.name = name;
			this.pid = pid;
		}

		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getPid() {
			return pid;
		}

		public void setPid(int pid) {
			this.pid = pid;
		}
	}
	
	public void cmbdrivercombofill(JComboBox mycombo) {
		String sql;
		mycombo.removeAllItems();
		Driver A = new Driver(0, "");
		mycombo.addItem(A);
		sql = "select driverid, name from drivers  order by upper(name)";

		ResultSet rs = dh.GetData(sql);
		try {
			while (rs.next()) {
				A = new Driver(rs.getInt("driverid"), rs.getString("name"));
				mycombo.addItem(A);
			}
			dh.CloseConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public class Driver {
		String name;
		int driverid;

		public Driver(int driverid, String name) {
			this.driverid = driverid;
			this.name = name;
		}

		public Driver() {

		}

		@Override
		public String toString() {
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getDriverid() {
			return driverid;
		}

		public void setDriverid(int driverid) {
			this.driverid = driverid;
		}
	}


}
		
	

