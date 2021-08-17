package com.example.app.clients.customer;

import javax.swing.JFrame;

import com.example.app.middle.MiddleFactory;
import com.example.app.middle.Names;
import com.example.app.middle.RemoteMiddleFactory;

/**
 * The standalone Customer Client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */
public class CustomerClient {
	public static void main(String[] args) {
		String stockURL = args.length < 1 // URL of stock R
				? Names.STOCK_R.value() // default location
				: args[0]; // supplied location

		RemoteMiddleFactory mrf = new RemoteMiddleFactory();
		mrf.setStockRInfo(stockURL);
		displayGUI(mrf); // Create GUI
	}

	private static void displayGUI(MiddleFactory mf) {
		JFrame window = new JFrame();
		window.setTitle("Customer Client (MVC RMI)");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		CustomerModel model = new CustomerModel(mf);
		CustomerView view = new CustomerView(window, mf, 0, 0);
		CustomerController cont = new CustomerController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Display Screen
	}
}
