package Classes;

import Databaseop.Databaseop;

public class Spallvalid {
	Hhelper hh = new Hhelper();
//	Databaseop dd = new Databaseop();
	public String mess = "";	
	
	public boolean emptyvalid(String content ) {
		if (hh.zempty(content)) {		
			return true;
		}
		return false;
	}
}
