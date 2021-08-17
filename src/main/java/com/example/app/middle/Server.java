package com.example.app.middle;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import com.example.app.remote.ROrder;
import com.example.app.remote.RStockR;
import com.example.app.remote.RStockRW;

/**
 * The server for the middle tier.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

public class Server {
	public static void main(String[] args) {
		String stockR = args.length < 1 // URL of stock R
				? Names.STOCK_R // default location
				: args[0]; // supplied location

		String stockRW = args.length < 2 // URL of stock RW
				? Names.STOCK_RW // default location
				: args[1]; // supplied location

		String order = args.length < 3 // URL of order manip
				? Names.ORDER // default location
				: args[2]; // supplied location

		(new Server()).bind(stockR, stockRW, order);
	}

	private void bind(String urlStockR, String urlStockRW, String urlOrder) {
		RStockR theStockR; // Remote stock object
		RStockRW theStockRW; // Remote stock object
		ROrder theOrder; // Remote order object
		System.out.println("Server: "); // Introduction
		try {
			LocateRegistry.createRegistry(1099);
			String ipAddress = InetAddress.getLocalHost().getHostAddress();
			System.out.println("Server IP address " + ipAddress);
		} catch (Exception e) {
			System.out.println("Fail Starting rmiregistry" + e.getMessage());
			System.exit(0);
		}

		try {
			theStockR = new RStockR(urlStockR); // Stock R
			Naming.rebind(urlStockR, theStockR); // bind to url
			System.out.println("StockR  bound to: " + // Inform world
					urlStockR); //

			theStockRW = new RStockRW(urlStockRW); // Stock RW
			Naming.rebind(urlStockRW, theStockRW); // bind to url
			System.out.println("StockRW bound to: " + // Inform world
					urlStockRW); //

			theOrder = new ROrder(urlOrder); // Order
			Naming.rebind(urlOrder, theOrder); // bind to url
			System.out.println("Order   bound to: " + // Inform world
					urlOrder);

		} catch (Exception err) // Error
		{ //
			System.out.println("Fail Server: " + // Variety of
					err.getMessage()); // reasons
		}
	}
}
