package com.test.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.rosuda.REngine.REXP;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

public class ExecuteRScript {

	public static void executeScript(String filePath) throws RserveException, FileNotFoundException, IOException {
		RConnection c = new RConnection();
		if (c.isConnected()) {
			System.out.println("Connected to RServe.");
			if (c.needLogin()) {
				System.out.println("Providing Login");
				c.login("username", "password");
			}

			REXP x;
			System.out.println("Reading script...");
			File file = new File(filePath);
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				for (String line; (line = br.readLine()) != null;) {
					System.out.println(line);
					x = c.eval(line);
					System.out.println(x);
				}
			}

		} else {
			System.out.println("Rserve could not connect");
		}

		c.close();
		System.out.println("Session Closed");
	}
}
