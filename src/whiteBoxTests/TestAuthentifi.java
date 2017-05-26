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

public class TestAuthentifi {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	public File f;
	public String dir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	public String file = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF\\login.txt";
	public FileWriter fw;

	@Before
	public void setUp() throws IOException {
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
		fw.close();
	}

	@After
	public void cleanUp() {
		System.setOut(null);

		f.delete();
	}

	// setFile() ohne existierende Datei muss in einer separaten Klasse getestet werden, weil @before
	// anders sein muss

	@Test
	public void testSetFileExisting() {
		Authentifi.setFile(dir);
		// Wenn Datei schon existiert, wird nichts ausgegeben. Die einzige
		// mögliche Ausgabe der Funktion ist "create login file".
		assertNotEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	@Test
	public void testValidGood() {
		// testet den Login mit gültigen Daten
		// ist davon abhängig, ob der FileWriter offen ist -> wieso?
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

	@Ignore
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

	@Test
	public void testNewUserGood() {
		// legt einen neuen Benutzer an, dessen Username noch nicht existiert
		String user = "newUser";
		String pw = "password";
		try {
			String output = Authentifi.newUser(user, pw);
			assertEquals(output, "Benutzer " + user + " erfolgreich angelegt!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}

	@Test
	public void testNewUserBadUsername() {
		// versucht einen neuen Nutzer anzulegen, dessen Username bereits
		// vergeben ist
		String user = "user";
		String pw = "pw";
		try {
			Authentifi.newUser(user, pw);
			String output = Authentifi.newUser(user, pw);
			assertEquals(output, "Nutzername " + user + " Bereits vergeben!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten");
		}
	}

	@Test
	public void testNewUserMaxUsers() {
		// versucht einen neuen Nutzer anzulegen, aber es gibt bereits eine
		// maximale Anzahl an Nutzern
		boolean failed = false;
		String pw = "pw";
		for(int i = 0; i < 1; i++) {
			String s = "user" + String.valueOf(i);
			try {
				Authentifi.newUser(s, pw);
				//readLogins() wirft NullPointerException
			} catch (IOException e) {
				fail("IOException ist aufgetreten!");
			}
			if(outContent.equals("Benutzer " + s + " konnte nicht angelegt werden!")) {
				failed = true;
				break;
			} else {
				outContent.reset();
			}
		}
		assertTrue(failed);
	}
	
	@Test
	public void testRmUserGood() {
		//readLogins() wirft gelegentlich NullPointerExceptions
		String user = "usertest";
		try {
			Authentifi.newUser(user, "pass");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			assertEquals("Benutzer "+user+" wurde erfolgreich entfernt!", Authentifi.rmUser(user));
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
	}

	@Test
	public void testRmUserBad() {
		String user = "userNichtExistent";
		try {
			assertEquals("Benutzer "+user+" konnte nicht gelöscht werden", Authentifi.rmUser(user));
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
	}
	
}