package whiteBoxTests;

import data.Authentifi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAuthentifi2 {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private File f;
	private String dir = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF";
	private String file = "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF\\login.txt";

	@Before
	public void setUpStreams() throws IOException {
		System.setOut(new PrintStream(outContent));
		f = new File(file);
	}

	@After
	public void cleanUp() {
		System.setOut(null);
	}

	@Test
	public void testSetFileNew() {
		f.delete();
		Authentifi.setFile(dir);
		assertEquals("create login file" + System.getProperty("line.separator"), outContent.toString());
	}
}
