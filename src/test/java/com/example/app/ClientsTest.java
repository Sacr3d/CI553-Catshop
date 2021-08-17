package com.example.app;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.example.app.clients.Setup;
import com.example.app.middle.LocalMiddleFactory;
import com.example.app.middle.MiddleFactory;

/**
 * Testing the clients capabilities
 * 
 * @author matti
 * @version 1.0
 */
class ClientsTest {

	MiddleFactory mlf = new LocalMiddleFactory(); // Direct access

	/**
	 * Generate database.txt and start Setup.Main() to generate the database
	 * 
	 * @throws Exception
	 */
	@BeforeAll
	public static void setUp() throws Exception {
		String[] emtpyArgs = { "" };

		String msg = "Derby";
		Files.write(Paths.get("./DataBase.txt"), msg.getBytes());

		Setup.main(emtpyArgs);

	}

	/**
	 * Delete test artifacts after all tests
	 * 
	 * @throws Exception
	 */
	@AfterAll
	public static void tearDown() throws Exception {
		Files.deleteIfExists(Paths.get("./DataBase.txt"));
	}

	/**
	 * Place holder test
	 */
	@Test
	void allClientsRun() {

		try {
			Main.startCustomerGUIMVC(mlf);
			Main.startCashierGUIMVC(mlf);
			Main.startBackDoorGUIMVC(mlf);
			Main.startPickGUIMVC(mlf);
			Main.startDisplayGUIMVC(mlf);
			Main.startCollectionGUIMVC(mlf);
		} catch (Exception e) {
			Assertions.fail(e.getMessage());
		}

	}

}
