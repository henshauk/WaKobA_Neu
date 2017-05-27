package whiteBoxTests;

import data.Label;

import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestLabel {

	// Path:
	// "C:\\Users\\Henning\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp1\\wtpwebapps\\WaKobA\\WEB-INF"

	@Test
	public void testLoadCombo() {
		// testet das Laden der Label
		// Die drei Standard-Label sind hardcoded.
		Label l = new Label();
		try {
			l.loadCombo();
		} catch (ClassNotFoundException e) {
			fail("ClassNotFoundException ist aufgetreten!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}

		String[] list = (String[]) l.listItems.toArray();
		assertEquals(3, list.length);
	}

	@Test
	public void testAddLabel() {
		// testet das Hinzufügen eines Labels
		// Die drei Standard-Label sind hardcoded.
		Label l = new Label();
		try {
			l.loadCombo();
		} catch (ClassNotFoundException e) {
			fail("ClassNotFoundException ist aufgetreten!");
		} catch (IOException e) {
			// fail("IOException ist aufgetreten!");
        }

		l.addLabel("Arbeitslose");

		String[] list = (String[]) l.listItems.toArray();
		assertEquals(4, list.length);
	}

	@Test
	public void testSaveCombo() {
		/*
		 * Testet das Laden der Labels, indem erst eins hinzugefügt wird, dann
		 * gespeichert und wieder geladen wird. Die drei Standard-Label sind
		 * hardcoded.
		 */
		Label l = new Label();
		try {
			l.loadCombo();
		} catch (ClassNotFoundException e) {
			fail("ClassNotFoundException ist aufgetreten!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}

		l.addLabel("Arbeitslose");
		l.addLabel("Großverdiener");

		try {
			l.saveCombo();
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}

		try {
			l.loadCombo();
		} catch (ClassNotFoundException e) {
			fail("ClassNotFoundException ist aufgetreten!");
		} catch (IOException e) {
			fail("IOException ist aufgetreten!");
		}

		String[] list = (String[]) l.listItems.toArray();
		assertEquals(5, list.length);

	}

}
