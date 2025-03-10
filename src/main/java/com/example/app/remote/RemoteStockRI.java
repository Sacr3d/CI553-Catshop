package com.example.app.remote;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import javax.swing.ImageIcon;

import com.example.app.catalogue.Product;
import com.example.app.middle.StockException;

/**
 * Defines the RMI interface for read access to the stock object.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public interface RemoteStockRI extends Remote, Serializable {
	boolean exists(String number) throws RemoteException, StockException;

	Product getDetails(String number) throws RemoteException, StockException;

	ImageIcon getImage(String number) throws RemoteException, StockException;
}
