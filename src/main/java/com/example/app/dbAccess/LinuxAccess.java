package com.example.app.dbaccess;

/**
 * Implements management of an mySQL database on Linux.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */
class LinuxAccess extends DBAccess {
	public void loadDriver() throws Exception {
		Class.forName("org.gjt.mm.mysql.Driver").getDeclaredConstructor().newInstance();
	}

	public String urlOfDatabase() {
		return "jdbc:mysql://localhost/cshop?user=root";
	}
}
