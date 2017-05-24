package whiteBoxTests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Ignore;
import org.junit.Test;
import data.Label;

public class LabelTest {

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
		Iterator<String> it = l.listItems.iterator();
		boolean allIncluded = false;
		if (it.next().equals("Hausfrauen")) {
			allIncluded = true;
		}
		if (!it.next().equals("Studenten")) {
			allIncluded = false;
		}
		if (!it.next().equals("Rentner")) {
			allIncluded = false;
		}
		if (it.hasNext()) {
			allIncluded = false;
		}
		assertTrue(allIncluded);
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
			fail("IOException ist aufgetreten!");
		}

		l.addLabel("Arbeitslose");

		Iterator<String> it = l.listItems.iterator();
		boolean allIncluded = false;
		if (it.hasNext()) {
			if (it.next().equals("Hausfrauen")) {
				allIncluded = true;
			}
		} else {
			fail("Hausfrauen nicht gefunden.");
		}
		if (it.hasNext()) {
			if (!it.next().equals("Studenten")) {
				fail("Studenten nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			if (!it.next().equals("Rentner")) {
				fail("Rentner nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			if (!it.next().equals("Arbeitslose")) {
				fail("Arbeitslose nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			System.out.println("Nächstes Label: " + it.next());
			fail("Zu viele Label vorhanden.");
		}
		assertTrue(allIncluded);
	}

	@Test
	public void testSaveCombo() {
		/* Testet das Laden der Labels, indem erst eins hinzugefügt wird, dann
		 * gespeichert und wieder geladen wird.
		 * Die drei Standard-Label sind hardcoded.
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

		Iterator<String> it = l.listItems.iterator();
		boolean allIncluded = false;
		if (it.hasNext()) {
			if (it.next().equals("Hausfrauen")) {
				allIncluded = true;
			}
		} else {
			fail("Hausfrauen nicht gefunden.");
		}
		if (it.hasNext()) {
			if (!it.next().equals("Studenten")) {
				fail("Studenten nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			if (!it.next().equals("Rentner")) {
				fail("Rentner nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			if (!it.next().equals("Arbeitslose")) {
				fail("Arbeitslose nicht gefunden.");
			}
		}
		if (it.hasNext()) {
			System.out.println("Nächstes Label: " + it.next());
			fail("Zu viele Label vorhanden.");
		}
		assertTrue(allIncluded);
	}

}
