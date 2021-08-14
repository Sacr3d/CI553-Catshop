package com.example.app;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Observable;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.example.app.clients.Setup;
import com.example.app.middle.LocalMiddleFactory;
import com.example.app.middle.MiddleFactory;

public class DatabaseTest {

	/**
	 * Create the Derby database and ensure drivers are installed
	 * 
	 * @assert That the catshop.db directory was created
	 * 
	 */
	@Test
	public void dataBaseCreation() {
		final String catshopDb = "catshop.db";

		Path path = Paths.get(catshopDb);

		try {
			FileUtils.deleteDirectory(path.toFile());
		} catch (IOException e) {
			// do nothing
		}

		Setup.main(null);
		assertTrue(Files.exists(path));
	}
	
	@Test
	public void CoustomerClientConnectToDb() {
		
		MiddleFactory mlf = new LocalMiddleFactory(); // Direct access

		class CustomerModel extends Observable {
			public boolean passedDb = true;

			/*
			 * Construct the model of the Customer
			 * 
			 * @param mf The factory to create the connection objects
			 */
			public CustomerModel(MiddleFactory mf) {
				try //
				{
					mf.makeStockReader();
				} catch (Exception e) {
					passedDb = false;
				}
			}
		}

		CustomerModel model = new CustomerModel(mlf);

		assertTrue(model.passedDb);

	}

}
