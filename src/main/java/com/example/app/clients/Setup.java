package com.example.app.clients;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.example.app.dbaccess.DBAccess;
import com.example.app.dbaccess.DBAccessFactory;

/**
 * Repopulate the database with test data
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.4 Derby
 */

public class Setup {
	private static final String PADDING = "%-14.14s ";
	private static final String INSERT_INTO_STOCK_TABLE_VALUES = "insert into StockTable values ";
	private static final String INSERT_INTO_PRODUCT_TABLE_VALUES = "insert into ProductTable values ";
	private static String[] sqlStatements = {

//  " SQL code to set up database tables",

//  "drop table ProductList",
//  "drop table StockList",

			"drop table ProductTable",
			"create table ProductTable (" + "productNo      Char(4)," + "description    Varchar(40),"
					+ "picture        Varchar(80)," + "price          Float)",

			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0001', '40 inch LED HD TV', 'images/pic0001.jpg', 269.00)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0002', 'DAB Radio',         'images/pic0002.jpg', 29.99)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0003', 'Toaster',           'images/pic0003.jpg', 19.99)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0004', 'Watch',             'images/pic0004.jpg', 29.99)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0005', 'Digital Camera',    'images/pic0005.jpg', 89.99)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0006', 'MP3 player',        'images/pic0006.jpg', 7.99)",
			INSERT_INTO_PRODUCT_TABLE_VALUES + "('0007', '32Gb USB2 drive',   'images/pic0007.jpg', 6.99)",
//  "select * from ProductTable",

			"drop table StockTable",
			"create table StockTable (" + "productNo      Char(4)," + "stockLevel     Integer)",

			INSERT_INTO_STOCK_TABLE_VALUES + "( '0001',  90 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0002',  20 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0003',  33 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0004',  10 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0005',  17 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0006',  15 )", //
			INSERT_INTO_STOCK_TABLE_VALUES + "( '0007',  01 )", //

			"select * from StockTable, ProductTable " + " where StockTable.productNo = ProductTable.productNo"

	};

	public static void main(String[] args) {
		Connection theCon = null; // Connection to database
		DBAccess dbDriver = null;
		DBAccessFactory.setAction("Create");
		System.out.println("Setup CatShop database of stock items");
		try {
			dbDriver = (new DBAccessFactory()).getNewDBAccess();
			dbDriver.loadDriver();
			theCon = DriverManager.getConnection(dbDriver.urlOfDatabase(), dbDriver.username(), dbDriver.password());
		} catch (SQLException e) {
			System.err.println("Problem with connection to " + dbDriver.urlOfDatabase());
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState:     " + e.getSQLState());
			System.out.println("VendorError:  " + e.getErrorCode());
			System.exit(-1);
		} catch (Exception e) {
			System.err.println("Can not load JDBC/ODBC driver.");
			System.exit(-1);

		}

		try (Statement stmt = theCon.createStatement()) {

			// execute SQL commands to create table, insert data
			for (String sqlStatement : sqlStatements) {
				executeStatment(theCon, dbDriver, stmt, sqlStatement);
			}
		} catch (SQLException e1) {
			System.err.println("problems creating statement object");

		}

	}

	private static void executeStatment(Connection theCon, DBAccess dbDriver, Statement stmt, String sqlStatement) {
		try {
			System.out.println(sqlStatement);
			switch (sqlStatement.charAt(0)) {
			case '/':
				System.out.println("------------------------------");
				break;
			case 's':
			case 'f':
				query(stmt, dbDriver.urlOfDatabase(), sqlStatement);
				break;
			case '*':
				if (sqlStatement.length() >= 2)
					switch (sqlStatement.charAt(1)) {
					case 'c':
						theCon.commit();
						break;
					case 'r':
						theCon.rollback();
						break;
					case '+':
						theCon.setAutoCommit(true);
						break;
					case '-':
						theCon.setAutoCommit(false);
						break;
					default:
					}
				break;
			default:
				stmt.execute(sqlStatement);
			}

		} catch (Exception e) {
			System.out.println("problems with SQL sent to " + dbDriver.urlOfDatabase() + "\n" + sqlStatement + "\n"
					+ e.getMessage());
		}
	}

	private static void query(Statement stmt, String url, String stm) {
		try {
			ResultSet res = stmt.executeQuery(stm);

			ArrayList<String> names = new ArrayList<>(10);

			ResultSetMetaData md = res.getMetaData();
			int cols = md.getColumnCount();

			for (int j = 1; j <= cols; j++) {
				String name = md.getColumnName(j);
				System.out.printf(PADDING, name);
				names.add(name);
			}
			System.out.println();

			for (int j = 1; j <= cols; j++) {
				System.out.printf(PADDING, md.getColumnTypeName(j));
			}
			System.out.println();

			while (res.next()) {
				for (int j = 0; j < cols; j++) {
					String name = names.get(j);
					System.out.printf(PADDING, res.getString(name));
				}
				System.out.println();
			}

		} catch (Exception e) {
			System.err.println("problems with SQL sent to " + url + "\n" + e.getMessage());
		}
	}

}
