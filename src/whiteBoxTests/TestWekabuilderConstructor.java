package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.Wekabuilder;

public class TestWekabuilderConstructor {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	String path = "C:\\Users\\Henning\\Desktop\\Uni\\Softwareprojektmanagement\\Weka Daten\\SPM_TestdatensatzMittel_2017.csv";
	String dataDir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	Wekabuilder w;
	
	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testConstructor() {
		try {
			w = new Wekabuilder(path, dataDir);
			assertTrue(w.data != null);
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}
}