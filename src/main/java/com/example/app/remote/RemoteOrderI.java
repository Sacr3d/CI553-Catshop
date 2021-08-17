package com.example.app.remote;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.example.app.catalogue.Basket;
import com.example.app.middle.OrderException;

/**
 * Defines the RMI interface for the Order object.
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public interface RemoteOrderI extends Remote, Serializable {
	public void newOrder(Basket order) throws RemoteException, OrderException;

	public int uniqueNumber() throws RemoteException, OrderException;

	public Basket getOrderToPick() throws RemoteException, OrderException;

	public boolean informOrderPicked(int orderNum) throws RemoteException, OrderException;

	public boolean informOrderCollected(int orderNum) throws RemoteException, OrderException;

	public Map<String, List<Integer>> getOrderState() throws RemoteException, OrderException;
}
