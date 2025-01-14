package com.example.app.clients.backdoor;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import com.example.app.catalogue.Basket;
import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.StockException;
import com.example.app.middle.StockReadWriter;

/**
 * Implements the Model of the back door client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.3
 */
public class BackDoorModel implements Publisher<String> {
	private Basket theBasket = null; // Bought items
	private String pn = ""; // Product being processed

	private StockReadWriter theStock = null;
	private Subscriber<? super String> subscriber;

	/*
	 * Construct the model of the back door client
	 * 
	 * @param mf The factory to create the connection objects
	 */

	public BackDoorModel(MiddleFactory mf) {
		try //
		{
			theStock = mf.makeStockReadWriter(); // Database access
		} catch (Exception e) {
			DEBUG.error("CustomerModel.constructor\n%s", e.getMessage());
		}

		theBasket = makeBasket(); // Initial Basket
	}

	/**
	 * Get the Basket of products
	 * 
	 * @return basket
	 */
	public Basket getBasket() {
		return theBasket;
	}

	/**
	 * Check The current stock level
	 * 
	 * @param productNum The product number
	 */
	public void doCheck(String productNum) {
		pn = productNum.trim(); // Product no.
	}

	/**
	 * Query
	 * 
	 * @param productNum The product number of the item
	 */
	public void doQuery(String productNum) {
		String theAction = "";
		pn = productNum.trim(); // Product no.
		try { // & quantity
			if (theStock.exists(pn)) // Stock Exists?
			{ // T
				Product pr = theStock.getDetails(pn); // Product
				theAction = // Display
						String.format("%s : %7.2f (%2d) ", //
								pr.getDescription(), // description
								pr.getPrice(), // price
								pr.getQuantity()); // quantity
			} else { // F
				theAction = // Inform
						"Unknown product number " + pn; // product number
			}
		} catch (StockException e) {
			theAction = e.getMessage();
		}
		subscriber.onNext(theAction);
	}

	/**
	 * Re stock
	 * 
	 * @param productNum The product number of the item
	 * @param quantity   How many to be added
	 */
	public void doRStock(String productNum, String quantity) {
		String theAction = "";
		theBasket = makeBasket();
		pn = productNum.trim(); // Product no.
		int amount = 0;
		try {
			String aQuantity = quantity.trim();
			amount = validateQuantity(aQuantity);

			if (amount > 0 && theStock.exists(pn)) // Stock Exists?
			{ // T
				theStock.addStock(pn, amount); // Re stock
				Product pr = theStock.getDetails(pn); // Get details
				theBasket.add(pr); //
				theAction = ""; // Display
			} else { // F
				theAction = // Inform Unknown
						"Unknown product number " + pn; // product number
			}
		} catch (StockException e) {
			theAction = e.getMessage();
		}
		subscriber.onNext(theAction);
	}

	/**
	 * returns a valid int for the amount string
	 * 
	 * @param aQuantity string value for quantity
	 * @return validated quantity
	 */
	private int validateQuantity(String aQuantity) {
		String theAction;
		int amount = 0;
		try {
			amount = Integer.parseInt(aQuantity); // Convert
			if (amount < 0)
				throw new NumberFormatException("-ve");
		} catch (Exception err) {
			theAction = "Invalid quantity";
			subscriber.onNext(theAction);
			return 0;
		}
		return amount;
	}

	/**
	 * Clear the product()
	 */
	public void doClear() {
		String theAction = "";
		theBasket.clear(); // Clear s. list
		theAction = "Enter Product Number"; // Set display
		subscriber.onNext(theAction);
	}

	/**
	 * return an instance of a Basket
	 * 
	 * @return a new instance of a Basket
	 */
	protected Basket makeBasket() {
		return new Basket();
	}

	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		this.subscriber = subscriber;
		// When all values or emitted, call complete.
		subscriber.onNext(""); // Notify
	}
}
