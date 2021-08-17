package com.example.app.clients.cashier;

import java.awt.Container;
import java.awt.Font;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import com.example.app.catalogue.Basket;
import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;

/**
 * View of the model
 * 
 * @author M A Smith (c) June 2014
 * @author matti
 * @version 3.0
 */
public class CashierView implements Subscriber<String> {
	private static final int H = 300; // Height of window pixels
	private static final int W = 400; // Width of window pixels

	private static final String CHECK = "Check";
	private static final String BUY = "Buy";
	private static final String BOUGHT = "Bought";

	private final JLabel theAction = new JLabel();
	private final JTextField theInput = new JTextField();
	private final JTextArea theOutput = new JTextArea();
	private final JScrollPane theSP = new JScrollPane();
	private final JButton theBtCheck = new JButton(CHECK);
	private final JButton theBtBuy = new JButton(BUY);
	private final JButton theBtBought = new JButton(BOUGHT);

	private CashierController cont = null;

	/**
	 * Construct the view
	 * 
	 * @param rpc Window in which to construct
	 * @param mf  Factor to deliver order and stock objects
	 * @param x   x-coordinate of position of window on screen
	 * @param y   y-coordinate of position of window on screen
	 */

	public CashierView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
		try //
		{
			mf.makeStockReadWriter(); // Database access
			mf.makeOrderProcessing(); // Process order
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		Container cp = rpc.getContentPane(); // Content Pane
		Container rootWindow = (Container) rpc; // Root Window
		cp.setLayout(null); // No layout manager
		rootWindow.setSize(W, H); // Size of Window
		rootWindow.setLocation(x, y);

		Font f = new Font("Monospaced", Font.PLAIN, 12); // Font f is

		theBtCheck.setBounds(16, 25 + 60 * 0, 80, 40); // Check Button
		theBtCheck.addActionListener( // Call back code
				e -> cont.doCheck(theInput.getText()));
		cp.add(theBtCheck); // Add to canvas

		theBtBuy.setBounds(16, 25 + 60 * 1, 80, 40); // Buy button
		theBtBuy.addActionListener( // Call back code
				e -> cont.doBuy());
		cp.add(theBtBuy); // Add to canvas

		theBtBought.setBounds(16, 25 + 60 * 3, 80, 40); // Clear Button
		theBtBought.addActionListener( // Call back code
				e -> cont.doBought());
		cp.add(theBtBought); // Add to canvas

		theAction.setBounds(110, 25, 270, 20); // Message area
		theAction.setText(""); // Blank
		cp.add(theAction); // Add to canvas

		theInput.setBounds(110, 50, 270, 40); // Input Area
		theInput.setText(""); // Blank
		cp.add(theInput); // Add to canvas

		theSP.setBounds(110, 100, 270, 160); // Scrolling pane
		theOutput.setText(""); // Blank
		theOutput.setFont(f); // Uses font
		cp.add(theSP); // Add to canvas
		theSP.getViewport().add(theOutput); // In TextArea
		rootWindow.setVisible(true); // Make visible
		theInput.requestFocus(); // Focus is here
	}

	/**
	 * The controller object, used so that an interaction can be passed to the
	 * controller
	 * 
	 * @param c The controller
	 */

	public void setController(CashierController c) {
		cont = c;
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
		CashierModel model = cont.getModel();
		String message = item;
		theAction.setText(message);
		Basket basket = model.getBasket();
		if (basket == null)
			theOutput.setText("Customers order");
		else
			theOutput.setText(basket.getDetails());

		theInput.requestFocus(); // Focus is here
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
