package com.example.app.dbaccess;

/**
 * Implements management of an mySQL database on Linux.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */
class LinuxAccess extends DBAccess {
	@Override
	public void loadDriver() throws Exception {
		Class.forName("org.gjt.mm.mysql.Driver").getDeclaredConstructor().newInstance();
	}

	@Override
	public String urlOfDatabase() {
		return "jdbc:mysql://localhost/cshop?user=root";
	}
}
