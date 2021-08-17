package com.example.app.dbaccess;

/**
 * Database Driver Exception
 * 
 * @author matti
 * @version 1.0
 */
public class NoDriverException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7047635067875715438L;

	/**
	 * Constructs a new exception with the specified detail message
	 * 
	 * @param string message
	 */
	public NoDriverException(String string) {
		super(string);
	}

}
