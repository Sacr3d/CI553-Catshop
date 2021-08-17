package com.example.app.clients;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Returns a position for the client window on the screen The clients are
 * assumed to be all the same size 400x300
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */
public class PosOnScrn {
	private static final int CLIENT_W = 400;
	private static final int CLIENT_H = 300;

	private static final int MAX_X; // Width of screen
	private static final int MAX_Y; // Height of screen

	private static int cX = 0; // Initial window pos on screen
	private static int cY = 0; // Initial window pos on screen

	// class initialiser
	// Will be called (once) when the class is loaded
	static {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		MAX_X = (int) dimension.getWidth();
		MAX_Y = (int) dimension.getHeight();
	}

	/**
	 * Calculate position of next window
	 */
	private static void next() {
		if (cX + 2 * CLIENT_W > MAX_X) {
			if (cY + 2 * CLIENT_H < MAX_Y) {
				cX = 0;
				cY += CLIENT_H;
			}
		} else {
			cX += CLIENT_W;
		}
		// No room on screen
		// All new windows are tiled on top of each other
	}

	/**
	 * return position for new window on screen slight misuse of the inbuilt
	 * Dimension class as used to hold an x,y co-ordinate pair
	 * 
	 * @return position for new window
	 */
	public static Dimension getPos() {
		Dimension pos = new Dimension(cX, cY);
		next();
		return pos;
	}
}
