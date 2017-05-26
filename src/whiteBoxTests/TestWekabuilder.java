package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import data.Wekabuilder;

public class TestWekabuilder {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	String path = "C:\\Users\\Henning\\Desktop\\Uni\\Softwareprojektmanagement\\Weka Daten\\SPM_TestdatensatzMittel_2017.csv";
	String dataDir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));

	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testConstructorGood() {
		// testet den Konstruktor mit funktionierendem Pfad und Ordner
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Ignore
	public void testConstructorBadPath() {
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Ignore
	public void testConstructorBadDataDir() {
		Wekabuilder w;
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testFilter() {
		int[] arr = { 1, 3, 5 };
		try {
			Wekabuilder w = new Wekabuilder(path, dataDir);
			w.filter(arr);
			// Kontrolle TBD
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}
}
