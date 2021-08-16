package com.example.app.clients.backdoor;

/**
 * The BackDoor Controller
 * 
 * @author M A Smith (c) June 2014
 */

public class BackDoorController {
	public BackDoorModel getModel() {
		return model;
	}

	private BackDoorModel model = null;

	/**
	 * Constructor
	 * 
	 * @param model The model
	 * @param view  The view from which the interaction came
	 */
	public BackDoorController(BackDoorModel model, BackDoorView view) {
		this.model = model;
	}

	/**
	 * Query interaction from view
	 * 
	 * @param pn The product number to be checked
	 */
	public void doQuery(String pn) {
		model.doQuery(pn);
	}

	/**
	 * RStock interaction from view
	 * 
	 * @param pn       The product number to be re-stocked
	 * @param quantity The quantity to be re-stocked
	 */
	public void doRStock(String pn, String quantity) {
		model.doRStock(pn, quantity);
	}

	/**
	 * Clear interaction from view
	 */
	public void doClear() {
		model.doClear();
	}

}
