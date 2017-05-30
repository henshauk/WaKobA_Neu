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
	// HashMap, welche die SessionID und einen Bool beinhaltet (true nach erfolgreichem Login)
	
	private static HashMap<String, String> userpass = new HashMap<String, String>(); 	
	// HashMap, welche User mit dem dazugehoerigen Passwort beinhaltet

	
	public static synchronized void writeLogins() throws IOException { 
		/**
		 *  Speichert die HashMap userpass ab.
		 * 
		 * @throws IOException
		 */
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
		/**
		 *  Lädt die gespeicherte HashMap userpass wieder in dieses Objekt
		 *  
		 * @throws IOException
		 */
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
		/**
		 *  Gibt jeden User mit dem dazugehörigen Passwort aus
		 *  
		 * @throws IOException
		 */
		readLogins();
		for (String user : userpass.keySet()) {
			System.out.println("user: " + user);
			System.out.println("password: " + userpass.get(user));
		}
	}

	public static void setFile(String dir) {	
		/**
		 *  Erstellt die login.txt, falls nicht vorhanden
		 * 
		 * @param dir - Pfad des WEB-INF Verzeichnisses
		 */
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

	public static boolean deleteFile(String dir) {		
		/**
		 * Loescht die login.txt, falls vorhanden
		 * 
		 * @param dir - Pfad des WEB-INF Verzeichnisses
		 * @return "true" wenn login.txt gelöscht wurde, sonst "false"
		 */
		f = new File(dir + File.separator + "login.txt");
		if (f.exists()) {
			f.delete();
			return true;
		} else
			return false;
	}

	public static String newUser(String user, String pass) throws IOException {	
		/**
		 *  Erstellt einen neuen User+Passwort (neuer Eintrag in der HashMap userpass)
		 * 
		 * @param user - Username
		 * @param pass - Passwort 
		 * @return String ("Benutzer XY erfolgreich angelegt", wenn erfolgreich, sonst "Benutzer XY konnte nicht angelegt werden! (Bereits vorhanden (newUser) )")
		 * @throws IOException
		 */
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
		/**
		 *  Loescht einen user, falls vorhanden, aus der Hashmap userpass und Speichert userpass daraufhin
		 * 
		 * @param user - Username vom zu löschenden User
		 * @return String ("Benutzer XY erfolgreich entfernt", wenn erfolgreich, sonst "Benutzer XY konnte nicht gelöscht werden (nicht vorhanden))
		 * @throws IOException
		 */
		readLogins();

		if (userpass.containsKey(user)) {
			userpass.remove(user);
			writeLogins();
			return "Benutzer " + user + " wurde erfolgreich entfernt!";
		} else
			return "Benutzer " + user + " konnte nicht gelöscht werden (nicht vorhanden)";
	}

	public static void rmAllUser() throws IOException {	
		/**
		 *  Loescht alle User aus der HashMap userpass, und speichert userpass daraufhin
		 * 
		 * @throws IOException
		 */
		readLogins();
		for (String user : userpass.keySet()) {
			userpass.remove(user);
		}
		writeLogins();
	}

	public static boolean valid(String user, String pass, String id) throws IOException {
		/**
		 *  Ueberprueft ob eingegebene Logindaten mit den Userdaten aus der HashMap userpass uebereinstimmen, wenn ja wird true zurückgegeben
		 *  und die SessionID wird zusammen mit dem Wert "true" in die berechtigt HashMap eingetragen
		 * 
		 * @param user - Username
		 * @param pass - Passwort
		 * @param id - SessionID
		 * @return bool - true wenn erfolgreich, sonst false
		 * @throws IOException
		 */
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
	

	public static boolean logout(String id){
		/**
		 * Die Session id des Nutzers wird aus der Map entfernt um die berechtigung zu entziehen
		 * 
		 * @param id - SessionId des Nutzers der sich ausloggen möchte
		 * @return bool - liefert true wenn logout erfolgreich, sonst false
		 */
		if(berechtigt.containsKey(id)){
		berechtigt.remove(id);
		return true;
		}else 
			return false;
	}

}
