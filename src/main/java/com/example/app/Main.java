package com.example.app;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JFrame;

import com.example.app.clients.PosOnScrn;
import com.example.app.clients.backdoor.BackDoorController;
import com.example.app.clients.backdoor.BackDoorModel;
import com.example.app.clients.backdoor.BackDoorView;
import com.example.app.clients.cashier.CashierController;
import com.example.app.clients.cashier.CashierModel;
import com.example.app.clients.cashier.CashierView;
import com.example.app.clients.collection.CollectController;
import com.example.app.clients.collection.CollectModel;
import com.example.app.clients.collection.CollectView;
import com.example.app.clients.customer.CustomerController;
import com.example.app.clients.customer.CustomerModel;
import com.example.app.clients.customer.CustomerView;
import com.example.app.clients.shopdisplay.DisplayController;
import com.example.app.clients.shopdisplay.DisplayModel;
import com.example.app.clients.shopdisplay.DisplayView;
import com.example.app.clients.warehousepick.PickController;
import com.example.app.clients.warehousepick.PickModel;
import com.example.app.clients.warehousepick.PickView;
import com.example.app.middle.LocalMiddleFactory;
import com.example.app.middle.MiddleFactory;
import com.example.app.debug.DEBUG;

/**
 * Starts all the clients as a single application. Good for testing the system
 * using a single application but no use of RMI.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */
class Main {
	// Change to false to reduce the number of duplicated clients

	private static final boolean MANY = false; // Many clients? (Or minimal clients)

	public static void main(String[] args) {

		new Main().begin(args);
	}

	/**
	 * Starts test system (Non distributed)
	 */
	public void begin(String[] args) {
		if (Arrays.asList(args).contains("log"))
			DEBUG.set(true);

		MiddleFactory mlf = new LocalMiddleFactory(); // Direct access

		startCustomerGUIMVC(mlf);
		if (MANY)
			startCustomerGUIMVC(mlf);
		startCashierGUIMVC(mlf);
		startCashierGUIMVC(mlf);
		startBackDoorGUIMVC(mlf);
		if (MANY)
			startPickGUIMVC(mlf);
		startPickGUIMVC(mlf);
		startDisplayGUIMVC(mlf);
		if (MANY)
			startDisplayGUIMVC(mlf);
		startCollectionGUIMVC(mlf);
	}

	public static void startCustomerGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();
		window.setTitle("Customer Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		CustomerModel model = new CustomerModel(mlf);
		CustomerView view = new CustomerView(window, mlf, pos.width, pos.height);
		CustomerController cont = new CustomerController(model, view);
		view.setController(cont);

		model.subscribe(view);
		window.setVisible(true); // start Screen
	}

	/**
	 * start the cashier client
	 * 
	 * @param mlf A factory to create objects to access the stock list
	 */
	public static void startCashierGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();
		window.setTitle("Cashier Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		CashierModel model = new CashierModel(mlf);
		CashierView view = new CashierView(window, mlf, pos.width, pos.height);
		CashierController cont = new CashierController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Make window visible
	}

	public static void startBackDoorGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();

		window.setTitle("BackDoor Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		BackDoorModel model = new BackDoorModel(mlf);
		BackDoorView view = new BackDoorView(window, mlf, pos.width, pos.height);
		BackDoorController cont = new BackDoorController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Make window visible
	}

	public static void startPickGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();

		window.setTitle("Pick Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		PickModel model = new PickModel(mlf);
		PickView view = new PickView(window, mlf, pos.width, pos.height);
		PickController cont = new PickController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Make window visible
	}

	public static void startDisplayGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();

		window.setTitle("Display Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		DisplayModel model = new DisplayModel(mlf);
		DisplayView view = new DisplayView(window, mlf, pos.width, pos.height);
		DisplayController cont = new DisplayController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Make window visible
	}

	public static void startCollectionGUIMVC(MiddleFactory mlf) {
		JFrame window = new JFrame();

		window.setTitle("Collect Client MVC");
		window.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		Dimension pos = PosOnScrn.getPos();

		CollectModel model = new CollectModel(mlf);
		CollectView view = new CollectView(window, mlf, pos.width, pos.height);
		CollectController cont = new CollectController(model, view);
		view.setController(cont);

		model.subscribe(view); // Add observer to the model
		window.setVisible(true); // Make window visible
	}

}
