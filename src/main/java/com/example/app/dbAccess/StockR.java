package com.example.app.dbaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Implements Read access to the stock list
 * The stock list is held in a relational DataBase
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */
import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.middle.StockException;
import com.example.app.middle.StockReader;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

// mySQL
//    no spaces after SQL statement ;

/**
 * Implements read only access to the stock database.
 * 
 * @author matti
 * @version 3.2
 *
 */
public class StockR implements StockReader {
	private static final String WHERE_PRODUCT_TABLE_PRODUCT_NO = "  where  ProductTable.productNo = '";

	/**
	 * 
	 */
	private static final long serialVersionUID = -6434034315442865857L;

	private transient Connection theCon = null; // Connection to database
	private transient Statement theStmt = null; // Statement object

	/**
	 * Connects to database Uses a factory method to help setup the connection
	 * 
	 * @throws StockException if problem
	 */
	public StockR() throws StockException {
		try {
			DBAccess dbDriver = (new DBAccessFactory()).getNewDBAccess();
			dbDriver.loadDriver();

			theCon = DriverManager.getConnection(dbDriver.urlOfDatabase(), dbDriver.username(), dbDriver.password());

			theStmt = theCon.createStatement();
			theCon.setAutoCommit(true);
		} catch (SQLException e) {
			throw new StockException("SQL problem:" + e.getMessage());
		} catch (Exception e) {
			throw new StockException("Can not load database driver.");
		}
	}

	/**
	 * Returns a statement object that is used to process SQL statements
	 * 
	 * @return A statement object used to access the database
	 */

	protected Statement getStatementObject() {
		return theStmt;
	}

	/**
	 * Returns a connection object that is used to process requests to the DataBase
	 * 
	 * @return a connection object
	 */

	protected Connection getConnectionObject() {
		return theCon;
	}

	/**
	 * Checks if the product exits in the stock list
	 * 
	 * @param pNum The product number
	 * @return true if exists otherwise false
	 */
	public synchronized boolean exists(String pNum) throws StockException {

		try {
			ResultSet rs = getStatementObject()
					.executeQuery("select price from ProductTable " + WHERE_PRODUCT_TABLE_PRODUCT_NO + pNum + "'");
			boolean res = rs.next();
			DEBUG.trace("DB StockR: exists(%s) -> %s", pNum, (res ? "T" : "F"));
			return res;
		} catch (SQLException e) {
			throw new StockException("SQL exists: " + e.getMessage());
		}
	}

	/**
	 * Returns details about the product in the stock list. Assumed to exist in
	 * database.
	 * 
	 * @param pNum The product number
	 * @return Details in an instance of a Product
	 */
	public synchronized Product getDetails(String pNum) throws StockException {
		try {
			Product dt = new Product("0", "", 0.00, 0);
			ResultSet rs = getStatementObject().executeQuery("select description, price, stockLevel "
					+ "  from ProductTable, StockTable " + WHERE_PRODUCT_TABLE_PRODUCT_NO + pNum + "' "
					+ "  and    StockTable.productNo   = '" + pNum + "'");
			if (rs.next()) {
				dt.setProductNum(pNum);
				dt.setDescription(rs.getString("description"));
				dt.setPrice(rs.getDouble("price"));
				dt.setQuantity(rs.getInt("stockLevel"));
			}
			rs.close();
			return dt;
		} catch (SQLException e) {
			throw new StockException("SQL getDetails: " + e.getMessage());
		}
	}

	/**
	 * Returns 'image' of the product
	 * 
	 * @param pNum The product number Assumed to exist in database.
	 * @return ImageIcon representing the image
	 */
	public synchronized ImageIcon getImage(String pNum) throws StockException {
		String filename = "default.jpg";
		try {
			ResultSet rs = getStatementObject()
					.executeQuery("select picture from ProductTable " + WHERE_PRODUCT_TABLE_PRODUCT_NO + pNum + "'");

			boolean res = rs.next();
			if (res)
				filename = rs.getString("picture");
			rs.close();
		} catch (SQLException e) {
			DEBUG.error("getImage()\n%s\n", e.getMessage());
			throw new StockException("SQL getImage: " + e.getMessage());
		}

		return new ImageIcon(filename);
	}

}
