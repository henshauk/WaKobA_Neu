package whiteBoxTests;

import data.Authentifi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAuthentifi3 {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private String path = new File(".").getAbsolutePath();
	
	private String dir = path + File.separator + "WebContent" + File.separator + "WEB-INF";

	@Before
	public void setUpStreams() throws IOException {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testValidGood() throws IOException {
		// testet den Login mit gültigen Daten
		Authentifi.setFile(dir);

		String user = "user";
		String pass = "pw";
		String sessionID = "123456";
		Authentifi.newUser(user, pass);

		boolean accessGranted = false;
		accessGranted = Authentifi.valid(user, pass, sessionID);
		assertTrue(accessGranted);
		
		assertTrue(Authentifi.logout(sessionID));
	}
	
}