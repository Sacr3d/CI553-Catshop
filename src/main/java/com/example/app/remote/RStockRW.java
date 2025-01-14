package com.example.app.remote;

import java.rmi.RemoteException;

import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;
import com.example.app.dbaccess.StockRW;
import com.example.app.middle.StockException;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Implements Read/Write access to the stock list, the stock list is held in a
 * relational DataBase.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public class RStockRW extends java.rmi.server.UnicastRemoteObject implements RemoteStockRWI {
	private static final long serialVersionUID = 1;
	private StockRW aStockRW = null;

	/**
	 * All transactions are done via StockRW to ensure that a single connection to
	 * the database is used for all transactions
	 * 
	 * @param url of remote object
	 * @throws java.rmi.RemoteException if issue
	 * @throws StockException           if issue
	 */
	public RStockRW(String url) throws RemoteException, StockException {
		aStockRW = new StockRW();
	}

	/**
	 * Returns true if product exists
	 * 
	 * @param pNum The product number
	 * @return true if product exists else false
	 * @throws StockException if underlying error
	 */
	public synchronized boolean exists(String pNum) throws StockException {
		return aStockRW.exists(pNum);
	}

	/**
	 * Returns details about the product in the stock list
	 * 
	 * @param pNum The product number
	 * @return StockNumber, Description, Price, Quantity
	 * @throws StockException if underlying error
	 */
	public synchronized Product getDetails(String pNum) throws StockException {
		return aStockRW.getDetails(pNum);
	}

	/**
	 * Returns an image of the product in the stock list
	 * 
	 * @param pNum The product number
	 * @return image
	 * @throws StockException if underlying error
	 */
	public synchronized ImageIcon getImage(String pNum) throws StockException {
		return aStockRW.getImage(pNum);
	}

	/**
	 * Buys stock and hence decrements number in the stock list
	 * 
	 * @param pNum   product number
	 * @param amount amount required
	 * @return StockNumber, Description, Price, Quantity
	 * @throws StockException if underlying error
	 */
	// Need to Fix
	// What happens if can not commit data
	//
	public synchronized boolean buyStock(String pNum, int amount) throws StockException {
		return aStockRW.buyStock(pNum, amount);
	}

	/**
	 * Adds (Restocks) stock to the product list
	 * 
	 * @param pNum   The product number
	 * @param amount Quantity
	 * @throws StockException if underlying error
	 */
	public synchronized void addStock(String pNum, int amount) throws StockException {
		aStockRW.addStock(pNum, amount);
	}

	/**
	 * Modifies Stock details for a given product number. Information modified:
	 * Description, Price
	 * 
	 * @param product The product to be modified
	 * @throws StockException if underlying error
	 */
	public synchronized void modifyStock(Product product) throws StockException {
		aStockRW.modifyStock(product);
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
