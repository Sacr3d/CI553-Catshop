package com.example.app.clients.cashier;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import com.example.app.catalogue.Basket;
import com.example.app.catalogue.Product;
import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.OrderException;
import com.example.app.middle.OrderProcessing;
import com.example.app.middle.StockException;
import com.example.app.middle.StockReadWriter;

/**
 * Implements the Model of the cashier client
 * 
 * @author Mike Smith University of Brighton
 * @version 1.0
 */
public class CashierModel implements Publisher<String> {
	private enum State {
		PROCESS, CHECKED
	}

	private State theState = State.PROCESS; // Current state
	private Product theProduct = null; // Current product
	private Basket theBasket = null; // Bought items

	private String pn = ""; // Product being processed

	private StockReadWriter theStock = null;
	private OrderProcessing theOrder = null;
	private Subscriber<? super String> subscriber;

	/**
	 * Construct the model of the Cashier
	 * 
	 * @param mf The factory to create the connection objects
	 */

	public CashierModel(MiddleFactory mf) {
		try //
		{
			theStock = mf.makeStockReadWriter(); // Database access
			theOrder = mf.makeOrderProcessing(); // Process order
		} catch (Exception e) {
			DEBUG.error("CashierModel.constructor\n%s", e.getMessage());
		}
		theState = State.PROCESS; // Current state
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
	 * Check if the product is in Stock
	 * 
	 * @param productNum The product number
	 */
	public void doCheck(String productNum) {
		String theAction = "";
		theState = State.PROCESS; // State process
		pn = productNum.trim(); // Product no.
		int amount = 1; // & quantity
		try {
			if (theStock.exists(pn)) // Stock Exists?
			{ // T
				Product pr = theStock.getDetails(pn); // Get details
				if (pr.getQuantity() >= amount) // In stock?
				{ // T
					theAction = // Display
							String.format("%s : %7.2f (%2d) ", //
									pr.getDescription(), // description
									pr.getPrice(), // price
									pr.getQuantity()); // quantity
					theProduct = pr; // Remember prod.
					theProduct.setQuantity(amount); // & quantity
					theState = State.CHECKED; // OK await BUY
				} else { // F
					theAction = // Not in Stock
							pr.getDescription() + " not in stock";
				}
			} else { // F Stock exists
				theAction = // Unknown
						"Unknown product number " + pn; // product no.
			}
		} catch (StockException e) {
			DEBUG.error("%s%n%s", "CashierModel.doCheck", e.getMessage());
			theAction = e.getMessage();
		}
		subscriber.onNext(theAction);
	}

	/**
	 * Buy the product
	 */
	public void doBuy() {
		String theAction = "";
		try {
			if (theState != State.CHECKED) // Not checked
			{ // with customer
				theAction = "Check if OK with customer first";
			} else {
				boolean stockBought = // Buy
						theStock.buyStock( // however
								theProduct.getProductNum(), // may fail
								theProduct.getQuantity()); //
				if (stockBought) // Stock bought
				{ // T
					makeBasketIfReq(); // new Basket ?
					theBasket.add(theProduct); // Add to bought
					theAction = "Purchased " + // details
							theProduct.getDescription(); //
				} else { // F
					theAction = "!!! Not in stock"; // Now no stock
				}
			}
		} catch (StockException e) {
			DEBUG.error("%s\n%s", "CashierModel.doBuy", e.getMessage());
			theAction = e.getMessage();
		}
		theState = State.PROCESS; // All Done
		subscriber.onNext(theAction);
	}

	/**
	 * Customer pays for the contents of the basket
	 */
	public void doBought() {
		String theAction = "";
		try {
			if (theBasket != null && theBasket.size() >= 1) // items > 1
			{ // T
				theOrder.newOrder(theBasket); // Process order
				theBasket = null; // reset
			} //
			theAction = "Next customer"; // New Customer
			theState = State.PROCESS; // All Done
			theBasket = null;
		} catch (OrderException e) {
			DEBUG.error("%s\n%s", "CashierModel.doCancel", e.getMessage());
			theAction = e.getMessage();
		}
		theBasket = null;
		subscriber.onNext(theAction);
	}

	/**
	 * make a Basket when required
	 */
	private void makeBasketIfReq() {
		if (theBasket == null) {
			try {
				int uon = theOrder.uniqueNumber(); // Unique order num.
				theBasket = makeBasket(); // basket list
				theBasket.setOrderNum(uon); // Add an order number
			} catch (OrderException e) {
				DEBUG.error("Comms failure\n" + "CashierModel.makeBasket()\n%s", e.getMessage());
			}
		}
	}

	/**
	 * return an instance of a new Basket
	 * 
	 * @return an instance of a new Basket
	 */
	protected Basket makeBasket() {
		return new Basket();
	}

	/**
	 * ask for update of view callled at start of day or after system reset
	 */
	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		this.subscriber = subscriber;
		// When all values or emitted, call complete.
		subscriber.onNext("Welcome"); // Notify
	}
}
