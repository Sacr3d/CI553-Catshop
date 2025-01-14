package com.example.app.middle;

import java.rmi.Naming;
import java.util.List;
import java.util.Map;

import com.example.app.catalogue.Basket;
import com.example.app.debug.DEBUG;
import com.example.app.remote.RemoteOrderI;

// There can only be 1 ResultSet opened per statement
// so no simultaneous use of the statement object
// hence the synchronized methods

/**
 * Facade for the order processing handling which is implemented on the middle
 * tier. This code is incomplete
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.01
 */

public class FOrder implements OrderProcessing {
	private static final String NET = "Net: ";

	/**
	 * 
	 */
	private static final long serialVersionUID = 141104575767673855L;

	private RemoteOrderI aROrder = null;
	private String theOrderURL = null;

	public FOrder(String url) {
		theOrderURL = url;
	}

	private void connect() throws OrderException {
		try // Setup
		{ // connection
			aROrder = // Connect to
					(RemoteOrderI) Naming.lookup(theOrderURL); // Stub returned
		} catch (Exception e) // Failure to
		{ // attach to the
			aROrder = null;
			throw new OrderException("Com: " + e.getMessage()); // object

		}
	}

	public void newOrder(Basket bought) throws OrderException {
		DEBUG.trace("F_Order:newOrder()");
		try {
			if (aROrder == null)
				connect();
			aROrder.newOrder(bought);
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}

	public int uniqueNumber() throws OrderException {
		DEBUG.trace("F_Order:uniqueNumber()");
		try {
			if (aROrder == null)
				connect();
			return aROrder.uniqueNumber();
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}

	/**
	 * Returns an order to pick from the warehouse if no order then returns null.
	 * 
	 * @return An order to pick
	 */

	public synchronized Basket getOrderToPick() throws OrderException {
		DEBUG.trace("F_Order:getOrderTioPick()");
		try {
			if (aROrder == null)
				connect();
			return aROrder.getOrderToPick();
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}

	/**
	 * Informs the order processing system that the order has been picked and the
	 * products are now on the conveyor belt to the shop floor.
	 */

	public synchronized boolean informOrderPicked(int orderNum) throws OrderException {
		DEBUG.trace("F_Order:informOrderPicked()");
		try {
			if (aROrder == null)
				connect();
			return aROrder.informOrderPicked(orderNum);
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}

	/**
	 * Informs the order processing system that the order has been collected by the
	 * customer
	 */

	public synchronized boolean informOrderCollected(int orderNum) throws OrderException {
		DEBUG.trace("F_Order:informOrderCollected()");
		try {
			if (aROrder == null)
				connect();
			return aROrder.informOrderCollected(orderNum);
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}

	/**
	 * Returns information about all orders in the order processing system
	 */

	public synchronized Map<String, List<Integer>> getOrderState() throws OrderException {
		DEBUG.trace("F_Order:getOrderState()");
		try {
			if (aROrder == null)
				connect();
			return aROrder.getOrderState();
		} catch (Exception e) {
			aROrder = null;
			throw new OrderException(NET + e.getMessage());
		}
	}
}
