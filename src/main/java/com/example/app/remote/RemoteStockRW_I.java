/**
 * @author  Mike Smith University of Brighton
 * @version 2.0
 */

package com.example.app.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.example.app.catalogue.Product;
import com.example.app.middle.StockException;

/**
 * Defines the RMI interface for read/write access to the stock object.
 * 
 * @author Mike Smith University of Brighton
 * @version 2.0
 */

public interface RemoteStockRW_I extends RemoteStockR_I, Remote {
	boolean buyStock(String number, int amount) throws RemoteException, StockException;

	void addStock(String number, int amount) throws RemoteException, StockException;

	void modifyStock(Product detail) throws RemoteException, StockException;
}
