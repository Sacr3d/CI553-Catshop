package com.example.app.clients;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

/**
 * A class to display a picture in a client
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */
public class Picture extends Canvas {
	private static final long serialVersionUID = 1;
	private int pictureWidth = 260;
	private int pictureHieght = 260;
	private transient Image thePicture = null;

	public Picture() {
		setSize(pictureWidth, pictureHieght);
	}

	public Picture(int aWidth, int aHeight) {
		pictureWidth = aWidth;
		pictureHieght = aHeight;
		setSize(pictureWidth, pictureHieght);
	}

	public void set(ImageIcon ic) {
		thePicture = ic.getImage(); // Image to be drawn
		repaint();
	}

	public void clear() {
		thePicture = null; // clear picture
		repaint(); // Force repaint
	}

	@Override
	public void paint(Graphics g) // When 'Window' is first
	{ // shown or damaged
		drawImage((Graphics2D) g);
	}

	@Override
	public void update(Graphics g) // Called by repaint
	{ //
		drawImage((Graphics2D) g); // Draw picture
	}

	/**
	 * Draw the picture First set the area to white and then draw the image
	 * 
	 * @param g Grapics context
	 */

	public void drawImage(Graphics2D g) {
		setSize(pictureWidth, pictureHieght);
		g.setPaint(Color.white);
		g.fill(new Rectangle2D.Double(0, 0, pictureWidth, pictureHieght));
		if (thePicture != null) {
			g.drawImage(thePicture, 0, 0, null);
		}
	}
}
