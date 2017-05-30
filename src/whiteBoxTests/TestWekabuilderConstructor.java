package whiteBoxTests;

import data.Wekabuilder;

import org.junit.Test;
import static org.junit.Assert.*;


public class TestWekabuilderConstructor {

	private String path = "C:\\Users\\Henning\\Desktop\\Uni\\Softwareprojektmanagement\\Weka Daten\\SPM_TestdatensatzMittel_2017.csv";
	private String dataDir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	private Wekabuilder w;
	

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