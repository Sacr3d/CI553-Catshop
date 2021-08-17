package com.example.app.clients.shopdisplay;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.OrderException;
import com.example.app.middle.OrderProcessing;

// File is complete but not optimal
//  Will force update of display every 2 seconds
//  Could be clever & only ask for an update of the display 
//   if it really has changed

/**
 * Implements the Model of the display client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public class DisplayModel implements Publisher<String>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3983201667255086550L;

	private OrderProcessing theOrder = null;
	private transient Subscriber<? super String> subscriber;

	/**
	 * Set up initial connection to the order processing system
	 * 
	 * @param mf Factory to return an object to access the order processing system
	 */
	public DisplayModel(MiddleFactory mf) {
		try //
		{
			theOrder = mf.makeOrderProcessing(); // Process order
		} catch (Exception e) {
			// Serious error in system (Should not occure)
			DEBUG.error("ModelOfDisplay: " + e.getMessage());
		}
		new Thread(this::backgroundRun).start();

	}

	/**
	 * Run as a thread in background to continually update the display
	 */
	public void backgroundRun() {
		boolean interrupted = false;
		while (!interrupted) // Forever
		{
			try {
				Thread.sleep(2000);
				DEBUG.trace("ModelOfDisplay call view");
				subscriber.onNext("");
			} catch (InterruptedException e) {
				DEBUG.error("%s%n%s%n", "ModelOfDisplay.run()", e.getMessage());
				interrupted = true;
				Thread.currentThread().interrupt();
			}
		}
	}

	// Will be called by the viewOfDisplay
	// when it is told that the view has changed
	public synchronized Map<String, List<Integer>> getOrderState() throws OrderException {
		return theOrder.getOrderState();
	}

	@Override
	public void subscribe(Subscriber<? super String> subscriber) {
		this.subscriber = subscriber;
		// When all values or emitted, call complete.
		subscriber.onNext(""); // Notify
	}
}
