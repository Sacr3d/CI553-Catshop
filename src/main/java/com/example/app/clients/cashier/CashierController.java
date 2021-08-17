package com.example.app.clients.cashier;

/**
 * The Cashier Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */

public class CashierController {
	private CashierModel model = null;

	public CashierModel getModel() {
		return model;
	}

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public CashierController(CashierModel model, CashierView view) {
		this.model = model;
	}

	/**
	 * Check interaction from view
	 * 
	 * @param pn The product number to be checked
	 */
	public void doCheck(String pn) {
		model.doCheck(pn);
	}

	/**
	 * Buy interaction from view
	 */
	public void doBuy() {
		model.doBuy();
	}

	/**
	 * Bought interaction from view
	 */
	public void doBought() {
		model.doBought();
	}
}
