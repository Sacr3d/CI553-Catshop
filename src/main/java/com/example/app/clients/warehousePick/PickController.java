package com.example.app.clients.warehousepick;

/**
 * The BackDoor Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */

public class PickController {
	private PickModel model = null;

	public PickModel getModel() {
		return model;
	}

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public PickController(PickModel model, PickView view) {
		this.model = model;
	}

	/**
	 * Picked interaction from view
	 * 
	 */
	public void doPick() {
		model.doPick();
	}

}
