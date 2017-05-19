package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

public class Label {
	static File f = new File("");
	public Set<String> listItems = null;

	public Label() {

	}

	public void loadCombo() throws ClassNotFoundException, IOException {
		FileInputStream fileIn = new FileInputStream("combo.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		listItems = (Set<String>) in.readObject();
		in.close();
		fileIn.close();
		// listItems = new HashSet<String>();

		listItems.add("Hausfrauen");
		listItems.add("Studenten");
		listItems.add("Rentner");
	}

	public void addLabel(String l) {
		listItems.add(l);
	}

	public void saveCombo() throws IOException {
		FileOutputStream fileOut = new FileOutputStream("combo.ser");
		ObjectOutputStream out = new ObjectOutputStream(fileOut);
		out.writeObject(listItems);
		out.close();
		fileOut.close();
	}
}
