/**
 * @author  Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */
package com.example.app.middle;

/**
 * Provide access to middle tier components.
 */

public class RemoteMiddleFactory implements MiddleFactory {
	private String theStockRURL = "";
	private String theStockRWURL = "";
	private String theOrderURL = "";

	public void setStockRInfo(String url) {
		theStockRURL = url;
	}

	public void setStockRWInfo(String url) {
		theStockRWURL = url;
	}

	public void setOrderInfo(String url) {
		theOrderURL = url;
	}

	/**
	 * Return an object to access the database for read only access. Access is via
	 * RMI
	 */

	public StockReader makeStockReader() throws StockException {
		return new FStockR(theStockRURL);
	}

	/**
	 * Return an object to access the database for read/write access. Access is via
	 * RMI
	 */
	public StockReadWriter makeStockReadWriter() throws StockException {
		return new FStockRW(theStockRWURL);
	}

	/**
	 * Return an object to access the order processing system. Access is via RMI
	 */
	public OrderProcessing makeOrderProcessing() throws OrderException {
		return new FOrder(theOrderURL);
	}
}
