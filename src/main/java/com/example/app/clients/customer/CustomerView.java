package com.example.app.clients.customer;

import java.awt.Container;
import java.awt.Font;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RootPaneContainer;

import com.example.app.clients.Picture;
import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;

/**
 * Implements the Customer view.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public class CustomerView implements Subscriber<String> {
	class Name // Names of buttons
	{
		public static final String CHECK = "Check";
		public static final String CLEAR = "Clear";

		/**
		 * non-public constructor
		 */
		private Name() {
			throw new IllegalStateException("Utility class");
		}
	}

	private static final int H = 300; // Height of window pixels
	private static final int W = 400; // Width of window pixels

	private final JLabel theAction = new JLabel();
	private final JTextField theInput = new JTextField();
	private final JTextArea theOutput = new JTextArea();
	private final JScrollPane theSP = new JScrollPane();
	private final JButton theBtCheck = new JButton(Name.CHECK);
	private final JButton theBtClear = new JButton(Name.CLEAR);

	private Picture thePicture = new Picture(80, 80);
	private CustomerController cont = null;

	/**
	 * Construct the view
	 * 
	 * @param rpc Window in which to construct
	 * @param mf  Factor to deliver order and stock objects
	 * @param x   x-cordinate of position of window on screen
	 * @param y   y-cordinate of position of window on screen
	 */
	public CustomerView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
		try //
		{
			mf.makeStockReader();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}
		Container cp = rpc.getContentPane(); // Content Pane
		Container rootWindow = (Container) rpc; // Root Window
		cp.setLayout(null); // No layout manager
		rootWindow.setSize(W, H); // Size of Window
		rootWindow.setLocation(x, y);

		Font f = new Font("Monospaced", Font.PLAIN, 12); // Font f is

		theBtCheck.setBounds(16, 25 + 60 * 0, 80, 40); // Check button
		theBtCheck.addActionListener( // Call back code
				e -> cont.doCheck(theInput.getText()));
		cp.add(theBtCheck); // Add to canvas

		theBtClear.setBounds(16, 25 + 60 * 1, 80, 40); // Clear button
		theBtClear.addActionListener( // Call back code
				e -> cont.doClear());
		cp.add(theBtClear); // Add to canvas

		theAction.setBounds(110, 25, 270, 20); // Message area
		theAction.setText(""); // Blank
		cp.add(theAction); // Add to canvas

		theInput.setBounds(110, 50, 270, 40); // Product no area
		theInput.setText(""); // Blank
		cp.add(theInput); // Add to canvas

		theSP.setBounds(110, 100, 270, 160); // Scrolling pane
		theOutput.setText(""); // Blank
		theOutput.setFont(f); // Uses font
		cp.add(theSP); // Add to canvas
		theSP.getViewport().add(theOutput); // In TextArea

		thePicture.setBounds(16, 25 + 60 * 2, 80, 80); // Picture area
		cp.add(thePicture); // Add to canvas
		thePicture.clear();

		rootWindow.setVisible(true); // Make visible
		theInput.requestFocus(); // Focus is here
	}

	/**
	 * The controller object, used so that an interaction can be passed to the
	 * controller
	 * 
	 * @param c The controller
	 */
	public void setController(CustomerController c) {
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
		CustomerModel model = cont.getModel();
		String message = item;
		theAction.setText(message);
		ImageIcon image = model.getPicture(); // Image of product
		if (image == null) {
			thePicture.clear(); // Clear picture
		} else {
			thePicture.set(image); // Display picture
		}
		theOutput.setText(model.getBasket().getDetails());
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
