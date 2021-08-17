package com.example.app.middle;

/**
 * Facade for read/write access to the stock list.
 * The actual implementation of this is held on the middle tier.
 * The actual stock list is held in a relational DataBase on the 
 * third tier.
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

import java.rmi.Naming;
import java.rmi.RemoteException;

import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.remote.RemoteStockRWI;

/**
 * Setup connection to the middle tier
 * 
 * @author matti
 * @version 3.1
 */

public class FStockRW extends FStockR implements StockReadWriter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 659324635112773384L;

	private RemoteStockRWI aRStockRW = null;
	private String theStockURL = null;

	public FStockRW(String url) {
		super(url); // Not used
		theStockURL = url;
	}

	private void connect() throws StockException {
		try // Setup
		{ // connection
			aRStockRW = // Connect to
					(RemoteStockRWI) Naming.lookup(theStockURL);// Stub returned
		} catch (Exception e) // Failure to
		{ // attach to the
			aRStockRW = null;
			throw new StockException("Com: " + e.getMessage()); // object

		}
	}

	/**
	 * Buys stock and hence decrements number in stock list
	 * 
	 * @return StockNumber, Description, Price, Quantity
	 * @throws StockException if remote exception
	 */

	public boolean buyStock(String number, int amount) throws StockException {
		DEBUG.trace("F_StockRW:buyStock()");
		try {
			if (aRStockRW == null)
				connect();
			return aRStockRW.buyStock(number, amount);
		} catch (RemoteException e) {
			aRStockRW = null;
			throw new StockException("Net: " + e.getMessage());
		}
	}

	/**
	 * Adds (Restocks) stock to the product list
	 * 
	 * @param number Stock number
	 * @param amount of stock
	 * @throws StockException if remote exception
	 */

	public void addStock(String number, int amount) throws StockException {
		DEBUG.trace("F_StockRW:addStock()");
		try {
			if (aRStockRW == null)
				connect();
			aRStockRW.addStock(number, amount);
		} catch (RemoteException e) {
			aRStockRW = null;
			throw new StockException("Net: " + e.getMessage());
		}
	}

	/**
	 * Modifies Stock details for a given product number. Information modified:
	 * Description, Price
	 * 
	 * @param detail Stock details to be modified
	 * @throws StockException if remote exception
	 */

	public void modifyStock(Product detail) throws StockException {
		DEBUG.trace("F_StockRW:modifyStock()");
		try {
			if (aRStockRW == null)
				connect();
			aRStockRW.modifyStock(detail);
		} catch (RemoteException e) {
			aRStockRW = null;
			throw new StockException("Net: " + e.getMessage());
		}
	}

}
