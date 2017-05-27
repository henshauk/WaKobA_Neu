package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import data.Result;
import data.Wekabuilder;

public class TestWekabuilder {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	String path = "C:\\Users\\Henning\\Desktop\\Uni\\Softwareprojektmanagement\\Weka Daten\\SPM_TestdatensatzMittel_2017.csv";
	String dataDir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	String storeDir = dataDir + File.separator + "store";
	int[] filterArr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 12, 15 };
	Wekabuilder w;

	@Before
	public void setUp() {
		System.setOut(new PrintStream(outContent));

		try {
			w = new Wekabuilder(path, dataDir);
		} catch (Exception e) {
			fail("Exception beim setUp ist aufgetreten!");
		}
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testFilter() {
		try {
			w.filter(filterArr);
			// Kontrolle TBD
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testStoreResult() {
		// Die Klasse Result wurde bereits erfolgreich getestet, kann hier also
		// genutzt werden.
		List<List<String>> testdata = new ArrayList<List<String>>();
		List<List<String>> result = new ArrayList<List<String>>();
		List<String> simple = new ArrayList<String>();
		simple.add("1");
		simple.add("2");
		testdata.add(simple);
		simple.remove(0);
		simple.add("3");
		simple.add("test");
		testdata.add(simple);

		Result r = new Result(testdata);

		try {
			String storedata = w.storeResult(r);
			result = Wekabuilder.getStoredData(storedata);
			assertEquals(testdata, result);
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}
	}

	@Test
	public void testBuildSKM() {
		try {
			w.filter(filterArr);
			w.buildSKM(3);
			// Kontrolle TBD
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testBuildFF() {
		try {
			w.filter(filterArr);
			w.buildFF(4);
			// Kontrolle TBD
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testBuildEM() {
		try {
			w.filter(filterArr);
			w.buildEM(5);
			// Kontrolle TBD
		} catch (Exception e) {
			fail("Exception ist aufgetreten!");
		}
	}

	@Test
	public void testAddKatToDiagrammData() {
		int[] arr = { 12, 14, 16 };
		Wekabuilder.addKatToDiagrammData(arr);
		// Kontrolle TBD
	}
}
