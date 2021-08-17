package com.example.app.dbaccess;

/**
 * Implements management of a Microsoft Access database.
 * 
 * DBC 4.0 API, it provides a new feature to discover java.sql.Driver
 * automatically, it means the Class.forName is no longer required.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.3
 */
class WindowsAccess extends DBAccess {

	@Override
	public String urlOfDatabase() {
		return "jdbc:odbc:cshop";
	}
}
