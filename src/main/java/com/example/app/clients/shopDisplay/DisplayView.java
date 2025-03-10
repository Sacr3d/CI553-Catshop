package com.example.app.clients.shopdisplay;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.swing.RootPaneContainer;

import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;
import com.example.app.middle.OrderException;

/**
 * The visual display seen by customers (Change to graphical version) Change to
 * a graphical display
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.2
 */
public class DisplayView extends Canvas implements Subscriber<String> {
	private static final long serialVersionUID = 1L;
	private Font catshopFont = new Font("Monospaced", Font.BOLD, 24);
	private int h = 300; // Height of window
	private int w = 400; // Width of window
	private String textToDisplay = "";
	private DisplayController cont = null;

	/**
	 * Construct the view
	 * 
	 * @param rpc Window in which to construct
	 * @param mf  Factor to deliver order and stock objects
	 * @param x   x-coordinate of position of window on screen
	 * @param y   y-coordinate of position of window on screen
	 */

	public DisplayView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
		Container cp = rpc.getContentPane(); // Content Pane
		Container rootWindow = (Container) rpc; // Root Window
		cp.setLayout(new BorderLayout()); // Border N E S W CENTER
		rootWindow.setSize(w, h); // Size of Window
		rootWindow.setLocation(x, y); // Position on screen
		rootWindow.add(this, BorderLayout.CENTER); // Add to rootwindow

		rootWindow.setVisible(true); // Make visible
	}

	public void setController(DisplayController c) {
		cont = c;
	}

	@Override
	public void update(Graphics g) // Called by repaint
	{ //
		drawScreen((Graphics2D) g); // Draw information on screen
	}

	/**
	 * Redraw the screen double buffered
	 * 
	 * @param g Graphics context
	 */
	@Override
	public void paint(Graphics g) // When 'Window' is first
	{ // shown or damaged
		drawScreen((Graphics2D) g); // Draw information on screen
	}

	private Dimension theAD; // Alternate Dimension
	private transient BufferedImage theAI; // Alternate Image
	private transient Graphics2D theAG; // Alternate Graphics

	public void drawScreen(Graphics2D g) // Re draw contents
	{ // allow resize
		Dimension d = getSize(); // Size of image

		if ((theAG == null) || (d.width != theAD.width) || (d.height != theAD.height)) { // New size
			theAD = d;
			theAI = (BufferedImage) createImage(d.width, d.height);
			theAG = theAI.createGraphics();
		}
		drawActualScreen(theAG); // draw
		g.drawImage(theAI, 0, 0, this); // Now on screen
	}

	/**
	 * Redraw the screen
	 * 
	 * @param g Graphics context
	 */

	public void drawActualScreen(Graphics2D g) // Re draw contents
	{
		g.setPaint(Color.white); // Paint Colour
		w = getWidth();
		h = getHeight(); // Current size

		g.setFont(catshopFont);
		g.fill(new Rectangle2D.Double(0, 0, w, h));

		// Draw state of system on display
		String[] lines = textToDisplay.split("\n");
		g.setPaint(Color.black);
		for (int i = 0; i < lines.length; i++) {
			g.drawString(lines[i], 0, 50 + 50 * i);
		}

	}

	/**
	 * Return a string of order numbers
	 * 
	 * @param map Contains the current state of the system
	 * @param key The key of the list requested
	 * @return As a string a list of order numbers.
	 */
	private String listOfOrders(Map<String, List<Integer>> map, String key) {
		StringBuilder res = new StringBuilder("");
		if (map.containsKey(key)) {
			List<Integer> orders = map.get(key);
			for (Integer i : orders) {
				res.append(" " + i);
			}
		} else {
			res = new StringBuilder("-No key-");
		}
		return res.toString();
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		// Do nothing

	}

	/**
	 * Update the view
	 * 
	 * @param item Specific args
	 */
	@Override
	public void onNext(String item) {
		// Code to update the graphical display with the current
		// state of the system
		// Orders awaiting processing
		// Orders being picked in the 'warehouse.
		// Orders awaiting collection

		try {
			Map<String, List<Integer>> res = cont.getModel().getOrderState();

			textToDisplay = "Orders in system" + "\n" + "Waiting        : " + listOfOrders(res, "Waiting") + "\n"
					+ "Being picked   : " + listOfOrders(res, "BeingPicked") + "\n" + "To Be Collected: "
					+ listOfOrders(res, "ToBeCollected");
		} catch (OrderException err) {
			textToDisplay = "\n" + "** Communication Failure **";
		}
		repaint(); // Draw graphically
	}

	@Override
	public void onError(Throwable throwable) {
		DEBUG.error(Thread.currentThread().getName() + " | ERROR = " + throwable.getClass().getSimpleName() + " | ",
				throwable.getMessage());
	}

	@Override
	public void onComplete() {
		// Do nothing

	}
}
