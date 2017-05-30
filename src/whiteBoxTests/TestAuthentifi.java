package whiteBoxTests;

import data.Authentifi;

import java.io.BufferedWriter;
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

public class TestAuthentifi {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private File f;

	private String dir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	private String file = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF\\login.txt";

	@Before
	public void setUp() throws IOException {
		System.setOut(new PrintStream(outContent));

		f = new File(file);

		if (!f.exists())
			f.createNewFile();
/*
		PrintStream fileStream = new PrintStream(f);
		fileStream.println("user");
		fileStream.println("pw");
		fileStream.close();
*/
	}

	@After
	public void cleanUp() {
		System.setOut(null);

		f.delete();
	}

	/*
	 * setFile() ohne existierende Datei muss in einer separaten Klasse getestet
	 * werden, weil die Datei nicht in @before erstellt werden sollte.
	 *
	 * userAusgeben() ist ebenfalls in anderer Klasse, um sicherzustellen, dass
	 * nur die User in der Datei stehen, die erwartet werden.
	 */

	@Test
	public void testSetFileExisting() {
		assertTrue(f.exists());
		Authentifi.setFile(dir);
		// Wenn Datei schon existiert, wird nichts ausgegeben. Die einzige
		// mögliche Ausgabe der Funktion ist "create login file".
		assertTrue(f.exists());
		assertNotEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}

	

	@Test
	public void testValidBadUsername() throws IOException {
		// testet den Login mit nichtexistentem Usernamen und Passwort,
		// das von jemand anderem benutzt wird
		Authentifi.setFile(dir);

		String user = "testusername";
		String pass = "pw";
		boolean accessGranted = true;
		accessGranted = Authentifi.valid(user, pass, "");
		assertFalse(accessGranted);
	}

	@Test
	public void testValidBadPassword() throws IOException {
		// testet den Login mit gültigem Usernamen, aber falschem Passwort
		Authentifi.setFile(dir);

		String user = "user";
		String pass = "testpw";
		boolean accessGranted = true;
		accessGranted = Authentifi.valid(user, pass, "");
		assertFalse(accessGranted);
	}

	@Test
	public void testNewUserGood() throws IOException {
		// legt einen neuen Benutzer an, dessen Username noch nicht existiert
		Authentifi.setFile(dir);

		String user = "newUser";
		String pw = "password";

		String output = Authentifi.newUser(user, pw);
		assertEquals(output, "Benutzer " + user + " erfolgreich angelegt!");
	}

	@Test
	public void testNewUserBadUsername() throws IOException {
		// versucht einen neuen Nutzer anzulegen, dessen Username bereits
		// vergeben ist
		Authentifi.setFile(dir);

		String user = "usernametest";
		String pw = "pw";
		
		Authentifi.newUser(user, pw);
		String output = Authentifi.newUser(user, pw);
		assertEquals(output, "Benutzer " + user + " konnte nicht angelegt werden! (Bereits vorhanden (newUser) )");
}

	@Test
	public void testRmUserGood() throws IOException {
		Authentifi.setFile(dir);
		String user = "UserToRemove";
		String pw = "pass";

		assertEquals("Benutzer " + user + " erfolgreich angelegt!", Authentifi.newUser(user, pw));
		Authentifi.userAusgeben();
		assertEquals("", outContent);
		
		//assertEquals("Benutzer " + user + " wurde erfolgreich entfernt!", Authentifi.rmUser(user));
	}

	@Test
	public void testRmUserBad() throws IOException {
		String user = "userNichtExistent";
		assertEquals("Benutzer " + user + " konnte nicht gelöscht werden (nicht vorhanden)", Authentifi.rmUser(user));
	}

	@Test
	public void testDeleteFile() throws IOException {
		assertTrue(Authentifi.deleteFile(dir));
		
		//Datei wiederherstellen, genauso wie es in @Before passiert
		f.createNewFile();

		PrintStream fileStream = new PrintStream(f);
		fileStream.println("user");
		fileStream.println("pw");
		fileStream.close();
	}
	
}
