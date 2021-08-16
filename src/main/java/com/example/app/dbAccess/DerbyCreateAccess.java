package com.example.app.dbaccess;

/**
 * Implements management of an Apache Derby database. that is too be created
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

class DerbyCreateAccess extends DBAccess {
	private static final String URLdb = "jdbc:derby:catshop.db;create=true";
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	public void loadDriver() throws Exception {
		Class.forName(DRIVER).getDeclaredConstructor().newInstance();
	}

	public String urlOfDatabase() {
		return URLdb;
	}
}
