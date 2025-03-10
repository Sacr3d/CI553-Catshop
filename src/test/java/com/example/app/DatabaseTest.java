package com.example.app;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.example.app.clients.Setup;

/**
 * Testing the database creation
 * 
 * @author matti
 * @version 1.0
 */
class DatabaseTest {

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
	 * Create the Derby database and ensure drivers are installed
	 * 
	 * @assert That the catshop.db directory was created
	 * 
	 */
	@Test
	void dataBaseCreation() {
		Assertions.assertTrue(Paths.get("./catshop.db").toFile().isDirectory());
	}

}
