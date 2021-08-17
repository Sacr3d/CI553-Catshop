package com.example.app.remote;

import java.rmi.RemoteException;

import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;
import com.example.app.dbaccess.StockR;
import com.example.app.middle.StockException;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Implements Read access to the stock list, the stock list is held in a
 * relational DataBase.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */
public class RStockR extends java.rmi.server.UnicastRemoteObject implements RemoteStockRI {
	private static final long serialVersionUID = 1;
	private StockR aStockR = null;

	public RStockR(String url) throws RemoteException, StockException {
		aStockR = new StockR();
	}

	/**
	 * Checks if the product exits in the stock list
	 * 
	 * @param pNum The product number
	 * @return true if exists otherwise false
	 */
	public synchronized boolean exists(String pNum) throws RemoteException, StockException {
		return aStockR.exists(pNum);
	}

	/**
	 * Returns details about the product in the stock list
	 * 
	 * @param pNum The product number
	 * @return StockNumber, Description, Price, Quantity
	 */
	public synchronized Product getDetails(String pNum) throws RemoteException, StockException {
		return aStockR.getDetails(pNum);
	}

	/**
	 * Returns an image of the product BUG However this will not work for
	 * distributed version as an instance of an Image is not Serializable
	 * 
	 * @param pNum The product number
	 * @return Image
	 */
	public synchronized ImageIcon getImage(String pNum) throws RemoteException, StockException {
		return aStockR.getImage(pNum);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
