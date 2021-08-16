package com.example.app.clients.customer;

/**
 * The Customer Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */

public class CustomerController {
	private CustomerModel model = null;

	public CustomerModel getModel() {
		return model;
	}

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public CustomerController(CustomerModel model, CustomerView view) {
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
	 * Clear interaction from view
	 */
	public void doClear() {
		model.doClear();
	}

}
