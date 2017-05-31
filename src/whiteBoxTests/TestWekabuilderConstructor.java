package whiteBoxTests;

import data.Wekabuilder;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;

public class TestWekabuilderConstructor {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	private String path = new File(".").getAbsolutePath();

	private String data = "C:\\Users\\Henning\\Desktop\\Uni\\Softwareprojektmanagement\\Weka Daten\\SPM_TestdatensatzMittel_2017.csv";
	private String dataDir = path + File.separator + "WebContent" + File.separator + "WEB-INF";
	private Wekabuilder w;

	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
		w = new Wekabuilder(data, dataDir);
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testConstructor() throws Exception {
		w = new Wekabuilder(data, dataDir);
		assertTrue(w.data != null);
	}

	@Test
	public void testConstructorWithoutFile() throws Exception {
		w = new Wekabuilder(dataDir);
		int dateien = new File(dataDir + File.separator + "store").list().length;
		if(dateien == 0)
			dateien = 1;
		String erwartet = "konstr.2 --" + System.getProperty("line.separator") + "Liste: " + Integer.toString(dateien)
				+ System.getProperty("line.separator");
		assertEquals(erwartet, outContent.toString());
	}

}