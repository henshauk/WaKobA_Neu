package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Authentifi {
	static File f;
	public static HashMap<String, Boolean> berechtigt = new HashMap<String, Boolean>();
	private static HashMap<String, String> userpass = new HashMap<String, String>();

	public static void writeLogins() throws IOException {
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);

		for (String user : userpass.keySet()) {
			bw.write(user); // Schreibe Name
			bw.newLine();
			bw.write(userpass.get(user)); // Schreibe PW in nächste Zeile
			bw.newLine();
		}

		bw.flush();
		bw.close();
	}

	public static void readLogins() throws IOException {
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String user = "";

		user = br.readLine();
		while (user != null) {
			if (!userpass.containsKey(user))
				userpass.put(user, br.readLine());
			// else
				// System.out.println("Eingelesener User bereits vorhanden (readLogins())");
			user = br.readLine();
		}
		br.close();
	}

	public static void userAusgeben() throws IOException {
		readLogins();
		for (String user : userpass.keySet()) {
			System.out.println("user: " + user);
			System.out.println("password: " + userpass.get(user));
		}
	}

	public static void setFile(String dir) {
		f = new File(dir + File.separator + "login.txt");
		if (!f.exists()) {
			try {
				f.createNewFile();
				System.out.println("create login file");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String deleteFile(String dir) {
		f = new File(dir + File.separator + "login.txt");
		if (f.exists()) {
			f.delete();
			return "Login.txt wurde gelöscht.";
		} else
			return "Login.txt konnte nicht gelöscht werden - File nicht vorhanden";
	}

	public static String newUser(String user, String pass) throws IOException {
		readLogins();

		if (userpass.containsKey(user))
			return "Benutzer " + user + " konnte nicht angelegt werden! (Bereits vorhanden (newUser) )";
		else {
			userpass.put(user, pass);
			writeLogins();
			return "Benutzer " + user + " erfolgreich angelegt!";
		}
	}

	public static String rmUser(String user) throws IOException {
		readLogins();

		if (userpass.containsKey(user)) {
			userpass.remove(user);
			writeLogins();
			return "Benutzer " + user + " wurde erfolgreich entfernt!";
		} else
			return "Benutzer " + user + " konnte nicht gelöscht werden (nicht vorhanden)";

	}

	public static void rmAllUser() throws IOException {
		readLogins();
		for (String user : userpass.keySet()) {
			userpass.remove(user);
		}
		writeLogins();
	}

	public static boolean valid(String user, String pass, String id) throws IOException {
		readLogins();
		if (userpass.containsKey(user)) {
			if (userpass.get(user).equals(pass)){
				berechtigt.put(id, true);
				return true;
			}
			else
				return false;
		} else
			return false;
	}
	
	/**
	 * Die Session id des Nutzers wird aus der Map entfernt um die berechtigung zu entziehen
	 * 
	 * @param id - Session id des Nutzers der sich ausloggen möchte
	 * @return
	 */
	public static boolean logout(String id){
		if(berechtigt.containsKey(id)){
		berechtigt.remove(id);
		return true;
		}else 
			return false;
	}

}
