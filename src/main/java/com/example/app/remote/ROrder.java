package com.example.app.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

import com.example.app.catalogue.Basket;
import com.example.app.middle.OrderException;
import com.example.app.orders.Order;

/**
 * The order processing handling. This code is incomplete
 * 
 * @author Mike Smith University of Brighton
 * @author matti
 * @version 3.1
 */

public class ROrder extends UnicastRemoteObject implements RemoteOrderI {
	private static final long serialVersionUID = 1;
	private Order aOrder = null;

	public ROrder() throws RemoteException {
		aOrder = new Order();
	}

	public void newOrder(Basket bought) throws RemoteException, OrderException {
		aOrder.newOrder(bought);

	}

	public int uniqueNumber() throws RemoteException, OrderException {
		return aOrder.uniqueNumber();
	}

	public Basket getOrderToPick() throws RemoteException, OrderException {
		return aOrder.getOrderToPick();
	}

	public boolean informOrderPicked(int orderNum) throws RemoteException, OrderException {
		return aOrder.informOrderPicked(orderNum);
	}

	public boolean informOrderCollected(int orderNum) throws RemoteException, OrderException {
		return aOrder.informOrderCollected(orderNum);
	}

	public Map<String, List<Integer>> getOrderState() throws RemoteException, OrderException {
		return aOrder.getOrderState();
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

}
