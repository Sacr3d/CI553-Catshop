package com.example.app.dbaccess;

/**
 * Apache Derby database access
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.2
 */

class DerbyAccess extends DBAccess {
	private static final String URL_DB = "jdbc:derby:catshop.db";
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	@Override
	public void loadDriver() throws Exception {
		Class.forName(DRIVER).getDeclaredConstructor().newInstance();
	}

	@Override
	public String urlOfDatabase() {
		return URL_DB;
	}
}
