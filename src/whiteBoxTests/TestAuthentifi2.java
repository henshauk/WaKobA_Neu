package whiteBoxTests;

import data.Authentifi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
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

	private String path = new File(".").getAbsolutePath();
	
	private String dir = path + File.separator + "WebContent" + File.separator + "WEB-INF";
	private String file = dir + File.separator + "login.txt";

	@Before
	public void setUpStreams() throws IOException {
		System.setOut(new PrintStream(outContent));
		f = new File(file);
		if(!f.exists()) {
			f.createNewFile();
		}
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Ignore
	public void testSetFileNew() throws IOException {
		if(f.exists()) {
			//assertTrue(f.exists());
			assertTrue(f.delete());
		}
		
		assertFalse(f.exists());
		Authentifi.setFile(dir);
		assertTrue(f.exists());
	}

	@Test
	public void testUserAusgeben() throws IOException {
		String user = "user";
		String pw = "pw";
		
		FileWriter fwOb = new FileWriter(f);
		PrintWriter pwOb = new PrintWriter(fwOb);
		pwOb.flush();
		pwOb.close();
		fwOb.close();

		PrintStream fileStream = new PrintStream(f);
		fileStream.println(user);
		fileStream.println(pw);
		fileStream.close();

		Authentifi.setFile(dir);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		while((line = br.readLine()) != null) {
			if(line.equals(user)) {
				assertEquals(pw, br.readLine());
			} else {
				fail("falscher Username");
			}
		}
		br.close();
	}
	
	@Test
	public void testRmAllUser() throws IOException {
		Authentifi.setFile(dir);
		Authentifi.rmAllUser();
		Authentifi.userAusgeben();
		assertEquals("", outContent.toString());
	}
	
	@Test
	public void testRmUserGood() throws IOException {
		Authentifi.setFile(dir);
		String user = "UserToRemove";
		String pw = "pass";

		//Benutzer anlegen und sichergehen, dass er existiert
		assertEquals("Benutzer " + user + " erfolgreich angelegt!", Authentifi.newUser(user, pw));
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		while((line = br.readLine()) != null) {
			if(line.equals(user)) {
				assertEquals(pw, br.readLine());
			}
		}
		br.close();
		fr.close();
		
		assertEquals("Benutzer " + user + " wurde erfolgreich entfernt!", Authentifi.rmUser(user));
	}
	
}
