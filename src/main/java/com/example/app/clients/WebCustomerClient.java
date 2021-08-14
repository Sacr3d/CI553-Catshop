package com.example.app.clients;

import javax.swing.JApplet;

import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.Names;
import com.example.app.middle.RemoteMiddleFactory;

/**
 * The Customer Client as an Applet
 * 
 * @author Mike Smith University of Brighton
 * @version 2.0
 */

public class WebCustomerClient extends JApplet {
	private static final long serialVersionUID = 1;

	public void init() {
		String supplied = getParameter("stock"); //
		String stockURL = supplied.equals("") // URL of stock R
				? Names.STOCK_R // default location
				: supplied; // supplied location

		System.out.println("URL " + stockURL);
		RemoteMiddleFactory mrf = new RemoteMiddleFactory();
		mrf.setStockRInfo(stockURL);
		displayGUI(mrf); // Create GUI
	}

	public void displayGUI(MiddleFactory mf) {
		DEBUG.trace("Need to add code");
		// new CustomerGUI( this, mf, 0, 0 );
	}
}
