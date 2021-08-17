package com.example.app.clients.shopdisplay;

import java.io.Serializable;

/**
 * The BackDoor Controller
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.1
 */

public class DisplayController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 185236412480688765L;
	
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
