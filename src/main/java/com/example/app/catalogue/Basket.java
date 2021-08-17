package com.example.app.catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Formatter;
import java.util.Locale;

/**
 * A collection of products from the CatShop. used to record the products that
 * are to be/ wished to be purchased.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.2
 *
 */
public class Basket extends ArrayList<Product> implements Serializable {
	private static final long serialVersionUID = 1;
	private int theOrderNum = 0; // Order number

	/**
	 * Constructor for a basket which is used to represent a customer order/ wish
	 * list
	 */
	public Basket() {
		theOrderNum = 0;
	}

	/**
	 * Set the customers unique order number Valid order Numbers 1 .. N
	 * 
	 * @param anOrderNum A unique order number
	 */
	public void setOrderNum(int anOrderNum) {
		theOrderNum = anOrderNum;
	}

	/**
	 * Returns the customers unique order number
	 * 
	 * @return the customers order number
	 */
	public int getOrderNum() {
		return theOrderNum;
	}

	/**
	 * Returns a description of the products in the basket suitable for printing.
	 * 
	 * @return a string description of the basket products
	 */
	public String getDetails() {
		Locale uk = Locale.UK;
		StringBuilder sb = new StringBuilder(256);
		try (Formatter fr = new Formatter(sb, uk)) {
			String csign = (Currency.getInstance(uk)).getSymbol();
			double total = 0.00;
			if (theOrderNum != 0)
				fr.format("Order number: %03d%n", theOrderNum);

			if (this.size() > 0) {
				for (Product pr : this) {
					int number = pr.getQuantity();
					fr.format("%-7s", pr.getProductNum());
					fr.format("%-14.14s ", pr.getDescription());
					fr.format("(%3d) ", number);
					fr.format("%s%7.2f", csign, pr.getPrice() * number);
					fr.format("%s", "\n");
					total += pr.getPrice() * number;
				}
				fr.format("%s", "----------------------------\n");
				fr.format("%s", "Total                       ");
				fr.format("%s%7.2f%n", csign, total);
			}
		}
		return sb.toString();

	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
