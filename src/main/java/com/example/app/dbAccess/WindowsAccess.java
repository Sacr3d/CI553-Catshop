package com.example.app.dbaccess;

/**
 * Implements management of a Microsoft Access database.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */
class WindowsAccess extends DBAccess {
	@Override
	public void loadDriver() throws Exception {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
	}

	@Override
	public String urlOfDatabase() {
		return "jdbc:odbc:cshop";
	}
}
