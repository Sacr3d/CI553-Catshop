package com.example.app.clients.collection;

/**
 * The Collection Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */

public class CollectController {
	private CollectModel model = null;

	public CollectModel getModel() {
		return model;
	}

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public CollectController(CollectModel model, CollectView view) {
		this.model = model;
	}

	/**
	 * Collect interaction from view
	 * 
	 * @param orderNum The order collected
	 */
	public void doCollect(String orderNum) {
		model.doCollect(orderNum);
	}
}
