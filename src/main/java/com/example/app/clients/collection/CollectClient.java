package com.example.app.clients.collection;

import javax.swing.JFrame;

import com.example.app.middle.MiddleFactory;
import com.example.app.middle.Names;
import com.example.app.middle.RemoteMiddleFactory;

/**
 * The standalone Collection Client.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.2
 */

public class CollectClient {
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

		window.setTitle("Collection Client (MVC RMI)");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		CollectModel model = new CollectModel(mf);
		CollectView view = new CollectView(window, mf, 0, 0);
		CollectController cont = new CollectController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Display Screen
	}
}
