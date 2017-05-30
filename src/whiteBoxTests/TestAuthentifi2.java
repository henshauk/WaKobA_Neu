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

public class TestAuthentifi2 {

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
	public void testSetFileNew() {
		f.delete();
		assertFalse(f.exists());
		Authentifi.setFile(dir);
		assertEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	@Test
	public void testUserAusgeben() throws IOException {
		FileWriter fwOb = new FileWriter(f);
		PrintWriter pwOb = new PrintWriter(fwOb);
		pwOb.flush();
		pwOb.close();
		fwOb.close();

		PrintStream fileStream = new PrintStream(f);
		fileStream.println("user");
		fileStream.println("pw");
		fileStream.close();

		Authentifi.setFile(dir);
		Authentifi.userAusgeben();
		String erwartet = "user: user" + System.getProperty("line.separator") + "password: pw"
				+ System.getProperty("line.separator");
		String ausgabe = outContent.toString();
		assertEquals(erwartet, ausgabe);
	}
	
	@Test
	public void testRmAllUser() throws IOException {
		Authentifi.setFile(dir);
		Authentifi.rmAllUser();
		Authentifi.userAusgeben();
		assertEquals("", outContent.toString());
	}
	
}
