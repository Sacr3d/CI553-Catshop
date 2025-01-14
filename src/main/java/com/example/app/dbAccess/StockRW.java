package com.example.app.dbaccess;

/**
 * Implements Read /Write access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

import java.sql.SQLException;

import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.middle.StockException;
import com.example.app.middle.StockReadWriter;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods
// 

/**
 * Implements read/write access to the stock database.
 */
public class StockRW extends StockR implements StockReadWriter {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6770152468391598736L;

	/*
	 * Connects to database
	 */
	public StockRW() throws StockException {
		super(); // Connection done in StockR's constructor
	}

	/**
	 * Customer buys stock, quantity decreased if sucessful.
	 * 
	 * @param pNum   Product number
	 * @param amount Amount of stock bought
	 * @return true if succeeds else false
	 */
	public synchronized boolean buyStock(String pNum, int amount) throws StockException {
		DEBUG.trace("DB StockRW: buyStock(%s,%d)", pNum, amount);
		int updates = 0;
		try {
			getStatementObject().executeUpdate("update StockTable set stockLevel = stockLevel-" + amount
					+ "       where productNo = '" + pNum + "' and " + "             stockLevel >= " + amount + "");
			updates = 1;
		} catch (SQLException e) {
			throw new StockException("SQL buyStock: " + e.getMessage());
		}
		DEBUG.trace("buyStock() updates -> %n", updates);
		return updates > 0; // sucess ?
	}

	/**
	 * Adds stock (Re-stocks) to the store. Assumed to exist in database.
	 * 
	 * @param pNum   Product number
	 * @param amount Amount of stock to add
	 */
	public synchronized void addStock(String pNum, int amount) throws StockException {
		try {
			getStatementObject().executeUpdate("update StockTable set stockLevel = stockLevel + " + amount
					+ "         where productNo = '" + pNum + "'");

			DEBUG.trace("DB StockRW: addStock(%s,%d)", pNum, amount);
		} catch (SQLException e) {
			throw new StockException("SQL addStock: " + e.getMessage());
		}
	}

	/**
	 * Modifies Stock details for a given product number. Assumed to exist in
	 * database. Information modified: Description, Price
	 * 
	 * @param detail Product details to change stocklist to
	 */
	public synchronized void modifyStock(Product detail) throws StockException {
		DEBUG.trace("DB StockRW: modifyStock(%s)", detail.getProductNum());
		try {
			if (!exists(detail.getProductNum())) {
				getStatementObject().executeUpdate("insert into ProductTable values ('" + detail.getProductNum() + "', "
						+ "'" + detail.getDescription() + "', " + "'images/Pic" + detail.getProductNum() + ".jpg', "
						+ "'" + detail.getPrice() + "' " + ")");
				getStatementObject().executeUpdate("insert into StockTable values ('" + detail.getProductNum() + "', "
						+ "'" + detail.getQuantity() + "' " + ")");
			} else {
				getStatementObject().executeUpdate("update ProductTable " + "  set description = '"
						+ detail.getDescription() + "' , " + "      price       = " + detail.getPrice()
						+ "  where productNo = '" + detail.getProductNum() + "' ");

				getStatementObject().executeUpdate("update StockTable set stockLevel = " + detail.getQuantity()
						+ "  where productNo = '" + detail.getProductNum() + "'");
			}

		} catch (SQLException e) {
			throw new StockException("SQL modifyStock: " + e.getMessage());
		}
	}
}
