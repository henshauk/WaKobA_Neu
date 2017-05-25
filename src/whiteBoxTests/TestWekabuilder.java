package whiteBoxTests;

import static org.junit.Assert.*;

import org.junit.Test;
import data.Wekabuilder;

public class TestWekabuilder {

	@Test
	public void testConstructorGood() {
		//testet den Konstruktor mit funktionierendem Pfad und Ordner
		String path = ""; //richtiger Pfad
		String dataDir = ""; //richtiger Ordner
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			System.out.println(w.data);
			assertTrue(w.data != null);
		}
		catch(Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testConstructorBadPath() {
		//testet den Konstruktor mit richtigem Ordner, aber falschem Pfad
		String path = ""; //falscher Pfad
		String dataDir = ""; //richtiger Ordner
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		}
		catch(Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testConstructorBadDataDir() {
		//testet den Konstruktor mit funktionierendem Pfad, aber falschem Ordner
		String path = ""; //richtiger Pfad
		String dataDir = ""; //falscher Ordner
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		}
		catch(Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}
}
