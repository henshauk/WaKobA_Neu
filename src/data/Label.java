package data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

public class Label {
	static File f = new File("");
	private List<String> labelList;
	private List<String> marketingList;
	private String storeDir;

	public Label(String dir) {

		storeDir = dir + File.separator + "dropdown";
		File store = new File(storeDir);
		if (!store.exists()) {
			store.mkdir();
			setDefault();
		}

		labelList = new LinkedList<String>();
		marketingList = new LinkedList<String>();
		loadLists();
		printList(labelList);
		printList(marketingList);

	}
	
	public List<String> getLabelList(){
		return labelList;
	}
	public List<String> getMarketingList(){
		return marketingList;
	}

	private void setDefault() {
		List<String> def = new LinkedList<String>();
		def.add("Hausfrauen");
		def.add("Rentner");
		def.add("Studenten");

		String storedata = storeDir + File.separator + "label";
		OutputStream fos = null;
		ObjectOutputStream oo = null;
		try {
			fos = new FileOutputStream(storedata);
			oo = new ObjectOutputStream(fos);
			oo.writeObject(def);
			oo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		def.clear();
		def.add("Warenbundle anbieten");
		def.add("Preise anpassen");
		def.add("Saisonale Podukte ergänzend anbieten");
		
		String storedat = storeDir + File.separator + "marketing";
		OutputStream fos2 = null;
		try {
			fos2 = new FileOutputStream(storedat);
			oo = new ObjectOutputStream(fos2);
			oo.writeObject(def);
			oo.close();
		} catch (IOException e) {
			System.err.println("marketing1");
			e.printStackTrace();
		} finally {
			try {
				fos2.close();
			} catch (IOException e) {
				System.err.println("marketing2");
				e.printStackTrace();
			}
		}

	}

	public void save(String str, boolean Label) {
		
		String storedata="";
		if(Label){
		storedata = storeDir + File.separator + "label";
		labelList.add(str);
		
		} else { 
			storedata = storeDir + File.separator + "marketing";
			marketingList.add(str);
		}
		OutputStream fos = null;
		try {
			fos = new FileOutputStream(storedata);
			ObjectOutputStream oo = new ObjectOutputStream(fos);
			if(Label){
				oo.writeObject(labelList);
			} else {
				oo.writeObject(marketingList);
			}
			oo.close();
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void loadLists() {

		InputStream fis = null;
		ObjectInputStream oo = null;
		try {
			fis = new FileInputStream(storeDir + File.separator + "label");
			oo = new ObjectInputStream(fis);
			labelList = (LinkedList<String>) oo.readObject();
			oo.close();
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String storedat = storeDir + File.separator + "marketing";
		InputStream fis2 = null;
		try {
			fis2 = new FileInputStream(storedat);
			oo = new ObjectInputStream(fis2);
			marketingList = (LinkedList<String>) oo.readObject();
			oo.close();
		} catch (IOException e) {
			System.err.println("load marketing");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fis2.close();
			} catch (IOException e) {
				System.err.println("load marketing");
				e.printStackTrace();
			}
		}

	}

	public void printList(List<String> list) {
		Iterator<String> it = list.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}

	}

}
