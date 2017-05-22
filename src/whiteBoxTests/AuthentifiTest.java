package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
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

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Ignore
	public void testSetFileNew() {
		// erstellt eine neue Datei im angegeben Ordner
		String dir = ""; // TBD
		Authentifi.setFile(dir);
		assertEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	@Ignore
	public void testSetFileExisting() {
		// Datei existiert schon => Bestätigungsmeldung?
		String dir = ""; // TBD
		Authentifi.setFile(dir);
	}

	@Ignore
	public void testValidGood() {
		// testet den Login mit gültigen Daten
		String user = "test";
		String pass = "testpw";
		boolean accessGranted = false;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertTrue(accessGranted);
	}

	@Ignore
	public void testValidBadUsername() {
		// testet den Login mit nichtexistentem Usernamen und Passwort,
		// das von jemand anderem benutzt wird
		String user = "test";
		String pass = "testpw";
		boolean accessGranted = true;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertFalse(accessGranted);
	}

	@Ignore
	public void testValidBadPassword() {
		// testet den Login mit gültigem Usernamen, aber falschem Passwort
		String user = "test";
		String pass = "testpw";
		boolean accessGranted = true;
		try {
			accessGranted = Authentifi.valid(user, pass);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		assertFalse(accessGranted);
	}

	@Ignore
	public void testReadLogins() {
		try {
			Authentifi.readLogins();
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
		// TBD: Arrays kontrollieren?
	}

	@Ignore
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

	@Ignore
	public void testNewUserGood() {
		// legt einen neuen Benutzer an, dessen Username noch nicht existiert
		String user = "user";
		String pw = "pw";
		try {
			assertEquals(Authentifi.newUser(user, pw),
					"Benutzer " + user + " erfolreich angelegt!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}
	
	@Ignore
	public void testNewUserBadUsername() {
		//versucht einen neuen Nutzer anzulegen, dessen Username bereits vergeben ist
		String user = "user";
		String pw = "pw";
		try {
			assertEquals(Authentifi.newUser(user, pw),
					"Benutzer " + user + " erfolreich angelegt!");
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