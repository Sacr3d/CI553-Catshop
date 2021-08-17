package com.example.app.clients.customer;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import javax.swing.ImageIcon;

import com.example.app.catalogue.Basket;
import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.StockException;
import com.example.app.middle.StockReader;

/**
 * Implements the Model of the customer client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */
public class CustomerModel implements Publisher<String> {
	private Basket theBasket = null; // Bought items

	private StockReader theStock = null;
	private ImageIcon thePic = null;
	private Subscriber<? super String> subscriber;

	/*
	 * Construct the model of the Customer
	 * 
	 * @param mf The factory to create the connection objects
	 */
	public CustomerModel(MiddleFactory mf) {
		try //
		{
			theStock = mf.makeStockReader(); // Database access
		} catch (Exception e) {
			DEBUG.error("CustomerModel.constructor\n" + "Database not created?\n%s\n", e.getMessage());
		}
		theBasket = makeBasket(); // Initial Basket
	}

	/**
	 * return the Basket of products
	 * 
	 * @return the basket of products
	 */
	public Basket getBasket() {
		return theBasket;
	}

	/**
	 * Check if the product is in Stock
	 * 
	 * @param productNum The product number
	 */
	public void doCheck(String productNum) {
		theBasket.clear(); // Clear s. list
		String theAction = "";
		String pn = productNum.trim(); // Product no.
		int amount = 1; // & quantity
		try {
			if (theStock.exists(pn)) // Stock Exists?
			{ // T
				Product pr = theStock.getDetails(pn); // Product
				if (pr.getQuantity() >= amount) // In stock?
				{
					theAction = // Display
							String.format("%s : %7.2f (%2d) ", //
									pr.getDescription(), // description
									pr.getPrice(), // price
									pr.getQuantity()); // quantity
					pr.setQuantity(amount); // Require 1
					theBasket.add(pr); // Add to basket
					thePic = theStock.getImage(pn); // product
				} else { // F
					theAction = // Inform
							pr.getDescription() + // product not
									" not in stock"; // in stock
				}
			} else { // F
				theAction = // Inform Unknown
						"Unknown product number " + pn; // product number
			}
		} catch (StockException e) {
			DEBUG.error("CustomerClient.doCheck()\n%s", e.getMessage());
		}
		subscriber.onNext(theAction);
	}

	/**
	 * Clear the products from the basket
	 */
	public void doClear() {
		String theAction = "";
		theBasket.clear(); // Clear s. list
		theAction = "Enter Product Number"; // Set display
		thePic = null; // No picture
		subscriber.onNext(theAction);
	}

	/**
	 * Return a picture of the product
	 * 
	 * @return An instance of an ImageIcon
	 */
	public ImageIcon getPicture() {
		return thePic;
	}

	/**
	 * Make a new Basket
	 * 
	 * @return an instance of a new Basket
	 */
	protected Basket makeBasket() {
		return new Basket();
	}

	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		this.subscriber = subscriber;
		// When all values or emitted, call complete.
		subscriber.onNext("Welcome"); // Notify
	}
}
