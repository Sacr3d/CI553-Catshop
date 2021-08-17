package com.example.app.clients.backdoor;

import javax.swing.JFrame;

import com.example.app.middle.MiddleFactory;
import com.example.app.middle.Names;
import com.example.app.middle.RemoteMiddleFactory;

/**
 * The standalone BackDoor Client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public class BackDoorClient {
	public static void main(String[] args) {
		String stockURL = args.length < 1 // URL of stock RW
				? Names.STOCK_RW.value() // default location
				: args[0]; // supplied location
		String orderURL = args.length < 2 // URL of order
				? Names.ORDER.value() // default location
				: args[1]; // supplied location

		RemoteMiddleFactory mrf = new RemoteMiddleFactory();
		mrf.setStockRWInfo(stockURL);
		mrf.setOrderInfo(orderURL); //
		displayGUI(mrf); // Create GUI
	}

	private static void displayGUI(MiddleFactory mf) {
		JFrame window = new JFrame();
		window.setTitle("BackDoor Client (MVC RMI)");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		BackDoorModel model = new BackDoorModel(mf);
		BackDoorView view = new BackDoorView(window, mf, 0, 0);
		BackDoorController cont = new BackDoorController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Display Screen
	}
}
