package com.example.app.dbaccess;

import java.lang.reflect.InvocationTargetException;

/**
 * Apache Derby database access
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.3
 */

class DerbyAccess extends DBAccess {
	private static final String URL_DB = "jdbc:derby:catshop.db";
	private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

	@Override
	public void loadDriver() throws NoDriverException {
		try {
			Class.forName(DRIVER).getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new NoDriverException(e.getMessage());
		}
	}

	@Override
	public String urlOfDatabase() {
		return URL_DB;
	}
}
