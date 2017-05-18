package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import data.Authentifi;

public class AuthentifiTest {
	
	@Test
	public void testValidGood() {
		//testet den Login mit gültigen Daten
		String user = "test";
		String pass = "testpw";
		boolean accessGranted = false;
		try{
			accessGranted = Authentifi.valid(user, pass);
		} catch(IOException e) {
			System.out.println("IOException aufgetreten!");
		}
		assertTrue(accessGranted);
	}

}
