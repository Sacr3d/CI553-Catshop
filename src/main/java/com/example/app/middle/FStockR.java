package com.example.app.middle;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Facade for read access to the stock list.
 * The actual implementation of this is held on the middle tier.
 * The actual stock list is held in a relational DataBase on the 
 * third tier.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.remote.RemoteStockRI;

/**
 * Setup connection to the middle tier
 * 
 * @author matti
 * @version 3.2
 */

public class FStockR implements StockReader {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2138086458011024379L;

	private static final String NET = "Net: ";
	private RemoteStockRI aRStockR = null;
	private String theStockURL = null;

	public FStockR(String url) {
		DEBUG.trace("F_StockR: %s", url);
		theStockURL = url;
	}

	private void connect() throws StockException {
		try // Setup
		{ // connection
			aRStockR = // Connect to
					(RemoteStockRI) Naming.lookup(theStockURL);// Stub returned
		} catch (Exception e) // Failure to
		{ // attach to the
			aRStockR = null;
			throw new StockException("Com: " + e.getMessage()); // object

		}
	}

	/**
	 * Checks if the product exits in the stock list
	 * 
	 * @return true if exists otherwise false
	 */

	public synchronized boolean exists(String number) throws StockException {
		DEBUG.trace("F_StockR:exists()");
		try {
			if (aRStockR == null)
				connect();
			return aRStockR.exists(number);
		} catch (RemoteException e) {
			aRStockR = null;
			throw new StockException(NET + e.getMessage());
		}
	}

	/**
	 * Returns details about the product in the stock list
	 * 
	 * @return StockNumber, Description, Price, Quantity
	 */

	public synchronized Product getDetails(String number) throws StockException {
		DEBUG.trace("F_StockR:getDetails()");
		try {
			if (aRStockR == null)
				connect();
			return aRStockR.getDetails(number);
		} catch (RemoteException e) {
			aRStockR = null;
			throw new StockException(NET + e.getMessage());
		}
	}

	public synchronized ImageIcon getImage(String number) throws StockException {
		DEBUG.trace("F_StockR:getImage()");
		try {
			if (aRStockR == null)
				connect();
			return aRStockR.getImage(number);
		} catch (RemoteException e) {
			aRStockR = null;
			throw new StockException(NET + e.getMessage());
		}
	}

}
