package com.example.app.dbaccess;

/**
  * Implements generic management of a database.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */

/**
 * Base class that defines the access to the database driver
 */
public class DBAccess {
	/**
	 * Load the Apache Derby database driver
	 */
	public void loadDriver() throws Exception {
		throw new RuntimeException("No driver");
	}

	/**
	 * Return the url to access the database
	 * 
	 * @return url to database
	 */
	public String urlOfDatabase() {
		return "";
	}

	public String username() {
		return "";
	}

	public String password() {
		return "";
	}
}
