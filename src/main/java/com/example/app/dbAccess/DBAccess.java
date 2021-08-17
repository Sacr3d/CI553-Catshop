package com.example.app.dbaccess;

/**
  * Implements generic management of a database.
  * @author  Mike Smith University of Brighton
  * @author matti
  * @version 3.2
  */

/**
 * Base class that defines the access to the database driver
 */
public class DBAccess {
	private static final String EMPTY_STRING = "";

	/**
	 * Load the Apache Derby database driver
	 * 
	 * @throws NoDriverException
	 */
	public void loadDriver() throws NoDriverException {
		throw new NoDriverException("No driver");
	}

	/**
	 * Return the url to access the database
	 * 
	 * @return url to database
	 */
	public String urlOfDatabase() {
		return EMPTY_STRING;
	}

	public String username() {
		return EMPTY_STRING;
	}

	public String password() {
		return EMPTY_STRING;
	}
}
