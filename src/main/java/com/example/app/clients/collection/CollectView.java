package com.example.app.clients.collection;

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

import com.example.app.debug.DEBUG;
import com.example.app.middle.MiddleFactory;

/**
 * Implements the Customer view.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

public class CollectView implements Subscriber<String> {
	private static final String COLLECT = "Collect";

	private static final int H = 300; // Height of window pixels
	private static final int W = 400; // Width of window pixels

	private final JLabel theAction = new JLabel();
	private final JTextField theInput = new JTextField();
	private final JTextArea theOutput = new JTextArea();
	private final JScrollPane theSP = new JScrollPane();
	private final JButton theBtCollect = new JButton(COLLECT);

	private CollectController cont = null;

	/**
	 * Construct the view
	 * 
	 * @param rpc Window in which to construct
	 * @param mf  Factor to deliver order and stock objects
	 * @param x   x-cordinate of position of window on screen
	 * @param y   y-cordinate of position of window on screen
	 */
	public CollectView(RootPaneContainer rpc, MiddleFactory mf, int x, int y) {
		try //
		{
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

		theBtCollect.setBounds(16, 25 + 60 * 0, 80, 40); // Check Button
		theBtCollect.addActionListener( // Call back code
				e -> cont.doCollect(theInput.getText()));
		cp.add(theBtCollect); // Add to canvas

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

	public void setController(CollectController c) {
		cont = c;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		// TODO Auto-generated method stub

	}

	/**
	 * Update the view
	 * 
	 * @param item Specific args
	 */
	@Override
	public void onNext(String item) {
		CollectModel model = cont.getModel();
		String message = item;
		theAction.setText(message);

		theOutput.setText(model.getResponce());
		theInput.requestFocus(); // Focus is here
	}

	@Override
	public void onError(Throwable throwable) {
		DEBUG.error(Thread.currentThread().getName() + " | ERROR = " + throwable.getClass().getSimpleName() + " | ",
				throwable.getMessage());
	}

	@Override
	public void onComplete() {
		// TODO Auto-generated method stub

	}

}
