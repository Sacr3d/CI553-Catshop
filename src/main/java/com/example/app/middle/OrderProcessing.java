package com.example.app.middle;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.example.app.catalogue.Basket;

/**
 * Defines the interface for accessing the order processing system.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

public interface OrderProcessing extends Serializable {
	// Used by
	public void newOrder(Basket bought) // Cashier
			throws OrderException;

	public int uniqueNumber() // Cashier
			throws OrderException;

	public Basket getOrderToPick() // Picker
			throws OrderException;

	public boolean informOrderPicked(int orderNum) // Picker
			throws OrderException;

	public boolean informOrderCollected(int orderNum) // Collection
			throws OrderException;

	public Map<String, List<Integer>> getOrderState() // Display
			throws OrderException;
}
