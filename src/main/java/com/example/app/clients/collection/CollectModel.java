package com.example.app.clients.collection;

import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.OrderProcessing;

/**
 * Implements the Model of the collection client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

public class CollectModel implements Publisher<String> {
	private String theOutput = "";
	private OrderProcessing theOrder = null;
	private Subscriber<? super String> subscriber;

	/*
	 * Construct the model of the Collection client
	 * 
	 * @param mf The factory to create the connection objects
	 */
	public CollectModel(MiddleFactory mf) {
		try //
		{
			theOrder = mf.makeOrderProcessing(); // Process order
		} catch (Exception e) {
			DEBUG.error("%s\n%s", "CollectModel.constructor\n%s", e.getMessage());
		}
	}

	/**
	 * Check if the product is in Stock
	 * 
	 * @param orderNumber The order to be collected
	 */
	public void doCollect(String orderNumber) {
		int orderNum = 0;
		String on = orderNumber.trim(); // Product no.
		try {
			orderNum = Integer.parseInt(on); // Convert
		} catch (Exception err) {
			// Convert invalid order number to 0
		}
		String theAction;
		try {
			boolean ok = theOrder.informOrderCollected(orderNum);
			if (ok) {
				theAction = "";
				theOutput = "Collected order #" + orderNum;
			} else {
				theAction = "No such order to be collected : " + orderNumber;
				theOutput = "No such order to be collected : " + orderNumber;
			}
		} catch (Exception e) {
			theOutput = String.format("%s%n%s", "Error connection to order processing system", e.getMessage());
			theAction = "!!!Error";
		}
		subscriber.onNext(theAction);
	}

	/**
	 * The output to be displayed
	 * 
	 * @return The string to be displayed
	 */
	public String getResponce() {
		return theOutput;
	}

	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		this.subscriber = subscriber;
		// When all values or emitted, call complete.
		subscriber.onNext(""); // Notify
	}

}
