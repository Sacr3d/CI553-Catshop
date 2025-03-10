package com.example.app.middle;

import java.io.Serializable;

import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;

/**
 * Interface for read access to the stock list.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.0
 */

public interface StockReader extends Serializable {

	/**
	 * Checks if the product exits in the stock list
	 * 
	 * @param pNum Product nymber
	 * @return true if exists otherwise false
	 * @throws StockException if issue
	 */
	boolean exists(String pNum) throws StockException;

	/**
	 * Returns details about the product in the stock list
	 * 
	 * @param pNum Product nymber
	 * @return StockNumber, Description, Price, Quantity
	 * @throws StockException if issue
	 */

	Product getDetails(String pNum) throws StockException;

	/**
	 * Returns an image of the product in the stock list
	 * 
	 * @param pNum Product nymber
	 * @return Image
	 * @throws StockException if issue
	 */

	ImageIcon getImage(String pNum) throws StockException;
}
