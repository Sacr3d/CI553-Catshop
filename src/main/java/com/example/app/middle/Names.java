package com.example.app.middle;

/**
 * Location of the various objects accessed remotely.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 4.0
 */

public enum Names {

	STOCK_RW("rmi://localhost/stock_rw"), STOCK_R("rmi://localhost/stock_r"), ORDER("rmi://localhost/order");

	private final String value;

	Names(String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
