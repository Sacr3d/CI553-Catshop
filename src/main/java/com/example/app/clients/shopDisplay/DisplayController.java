package com.example.app.clients.shopdisplay;

/**
 * The BackDoor Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */

public class DisplayController {

	private DisplayModel model = null;

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public DisplayController(DisplayModel model, DisplayView view) {
		this.model = model;
	}

	public DisplayModel getModel() {
		return model;
	}
}
