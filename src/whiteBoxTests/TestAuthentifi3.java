package whiteBoxTests;

import data.Authentifi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAuthentifi3 {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private File f;
	private String dir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	private String file = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF\\login.txt";

	@Before
	public void setUpStreams() throws IOException {
		System.setOut(new PrintStream(outContent));
		f = new File(file);
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testValidGood() throws IOException {
		// testet den Login mit gültigen Daten
		Authentifi.setFile(dir);

		String user = "user2";
		String pass = "pw";
		//Authentifi.newUser(user, pass);
		
		PrintStream fileStream = new PrintStream(f);
		fileStream.println("user");
		fileStream.println("pw");
		fileStream.println(user);
		fileStream.println(pass);
		fileStream.close();
		
		boolean accessGranted = false;
		accessGranted = Authentifi.valid(user, pass);
		assertTrue(accessGranted);
	}
}