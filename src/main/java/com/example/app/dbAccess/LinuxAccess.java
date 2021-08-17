package com.example.app.dbaccess;

import java.lang.reflect.InvocationTargetException;

/**
 * Implements management of an mySQL database on Linux.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.3
 */
class LinuxAccess extends DBAccess {
	@Override
	public void loadDriver() throws NoDriverException {
		try {
			Class.forName("org.gjt.mm.mysql.Driver").getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | ClassNotFoundException e) {
			throw new NoDriverException(e.getMessage());
		}
	}

	@Override
	public String urlOfDatabase() {
		return "jdbc:mysql://localhost/cshop?user=root";
	}
}
