package whiteBoxTests;

import data.Authentifi;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

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
    private FileWriter fw;

    @Before
    public void setUp() throws IOException {
        System.setOut(new PrintStream(outContent));

        f = new File(file);
      
        if(!f.exists())
        	f.createNewFile();
        	
        FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("1"); // Schreibe Name
		bw.newLine();
		bw.write("pass1"); // Schreibe PW in nächste Zeile
		bw.newLine();
		
		bw.write("2"); // Schreibe Name
		bw.newLine();
		bw.write("pass2"); // Schreibe PW in nächste Zeile
		bw.newLine();

    }

    @After
    public void cleanUp() {
        System.setOut(null);

        f.delete();
    }

    // setFile() ohne existierende Datei muss in einer separaten Klasse getestet
    // werden, weil @before anders sein muss

    @Test
    public void testSetFileExisting() {
        Authentifi.setFile(dir);
        // Wenn Datei schon existiert, wird nichts ausgegeben. Die einzige
        // mögliche Ausgabe der Funktion ist "create login file".
        assertNotEquals(
                "create login file" + System.getProperty("line.separator"),
                outContent.toString());
    }

    @Test
    public void testValidGood() throws IOException {
        // testet den Login mit gültigen Daten
    	Authentifi.setFile(dir);
    	Authentifi.readLogins();
        String user = "user";
        String pass = "pw";
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
        Authentifi.setFile(dir);

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
        Authentifi.setFile(dir);

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
        Authentifi.setFile(dir);

        try {
            Authentifi.readLogins();
        } catch (IOException e) {
            fail("IOException ist aufgetreten!");
        }
        // TBD: Arrays kontrollieren?
    }

    @Ignore
    public void testWriteLogins() {
        String[] users = {"user1", "user2"};
        String[] passwords = {"pw1", "pw2"};
        try {
            Authentifi.writeLogins();
        } catch (IOException e) {
            fail("IOException ist aufgetreten!");
        }
        // TBD: kontrollieren, ob Daten in Datei
        // => mit userAusgeben()? Erst separat testen.
    }

    @Test
    public void testNewUserGood() {
        // legt einen neuen Benutzer an, dessen Username noch nicht existiert
        Authentifi.setFile(dir);

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
        Authentifi.setFile(dir);

        String user = "user";
        String pw = "pw";
        try {
            Authentifi.newUser(user, pw);
            String output = Authentifi.newUser(user, pw);
            assertEquals(output, "Benutzer " + user + " konnte nicht angelegt werden! (Bereits vorhanden (newUser) )");
        } catch (IOException e) {
            fail("IOException ist aufgetreten");
        }
    }

    @Test
    public void testRmUserGood() throws IOException {
        // readLogins() wirft gelegentlich NullPointerExceptions
    	//Authentifi.setFile(dir);
        String user = "usertest";
        Authentifi.readLogins();
        try {
            Authentifi.newUser(user, "pass");
        } catch (IOException ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
        }
        try {
            assertEquals("Benutzer " + user + " wurde erfolgreich entfernt!",
                    Authentifi.rmUser("1"));
        } catch (IOException e) {
            fail("IOException ist aufgetreten!");
        }
    }

    @Test
    public void testRmUserBad() {
        String user = "userNichtExistent";
        try {
            assertEquals("Benutzer " + user + " konnte nicht gelöscht werden (nicht vorhanden)",
                    Authentifi.rmUser(user));
        } catch (IOException e) {
            fail("IOException ist aufgetreten!");
        }
    }

}
