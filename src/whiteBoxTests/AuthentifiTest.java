package whiteBoxTests;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import data.Authentifi;


public class AuthentifiTest {
	
	//TBD: file festlegen und ggf. Logindaten eintragen, die schon vor dem Test existieren müssen

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	public File f;
	public String dir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	public String file = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF\\login.txt";
	public FileWriter fw;
	
	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
		
		f = new File(file);
		try {
			fw = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fw.write("user" + System.getProperty("line.separator") + "pass" + System.getProperty("line.separator"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
		
		f.delete();
	}

	@Test
	public void testSetFileNew() {
		assertTrue(f.exists());
		//Authentifi.setFile(dir);
		//assertEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	@Test
	public void testSetFileExisting() {
		Authentifi.setFile(dir);
		assertNotEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	@Test
	public void testValidGood() {
		// testet den Login mit gültigen Daten
		String user = "user";
		String pass = "pass";
		boolean accessGranted = false;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertTrue(accessGranted);
	}

	@Test
	public void testValidBadUsername() {
		// testet den Login mit nichtexistentem Usernamen und Passwort,
		// das von jemand anderem benutzt wird
		String user = "test";
		String pass = "pass";
		boolean accessGranted = true;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertFalse(accessGranted);
	}

	@Test
	public void testValidBadPassword() {
		// testet den Login mit gültigem Usernamen, aber falschem Passwort
		String user = "user";
		String pass = "testpw";
		boolean accessGranted = true;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertFalse(accessGranted);
	}

	@Test
	public void testReadLogins() {
		try {
			Authentifi.readLogins();
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		// TBD: Arrays kontrollieren?
	}

	@Test
	public void testWriteLogins() {
		String[] users = { "user1", "user2" };
		String[] passwords = { "pw1", "pw2" };
		try {
			Authentifi.writeLogins(users, passwords);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		// TBD: kontrollieren, ob Daten in Datei
		// => mit userAusgeben()? Erst separat testen.
	}

	@Test
	public void testNewUserGood() {
		// legt einen neuen Benutzer an, dessen Username noch nicht existiert
		String user = "newUser";
		String pw = "password";
		try {
			assertEquals(Authentifi.newUser(user, pw),
					"Benutzer " + user + " erfolreich angelegt!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}
	
	@Test
	public void testNewUserBadUsername() {
		//versucht einen neuen Nutzer anzulegen, dessen Username bereits vergeben ist
		String user = "user";
		String pw = "pw";
		try {
			assertEquals(Authentifi.newUser(user, pw),
					"Nutzername "+ user +" Bereits vergeben!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}
	
	@Ignore
	public void testNewUserMaxUsers() {
		//versucht einen neuen Nutzer anzulegen, aber es gibt bereits eine maximale Anzahl an Nutzern
		//Username ist noch nicht vergeben!
		String user = "user";
		String pw = "pw";
		try {
			assertEquals(Authentifi.newUser(user, pw),
					"Benutzer " + user + " konnte nicht angelegt werden!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}

}