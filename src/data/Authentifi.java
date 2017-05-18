package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Authentifi {
	static File f = new File("C:/Users/Hauke/workspace/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/WaKobA/logins.txt");
	private static String[] users = new String[100];
	private static String[] passwords = new String[100];

	public static boolean valid(String user, String pass) throws IOException {
		readLogins();

		for (int j = 0; j < 100; j++) {
			if (user.equals(users[j]) && pass.equals(passwords[j])) {
				return true;
			}
		}
		return false;
	}
	
	public static void userAusgeben() throws IOException{
		readLogins();
		boolean k = true;
		int i = 0;
		while(k){
			if(users[i] == null || users[i] == ""){
				k = false;
			}
			else if(users[i] != null) {
			System.out.println(users[i]);
			System.out.println(passwords[i]);
			}
			if(i == 99){
				k = false;
			}
			i++;
		}
	}
	
	public static String newUser(String user, String pass) throws IOException {
		readLogins();
		for(int i = 0; i < 100;i++){
			if(users[i] == user){
				writeLogins(users, passwords);
				return "Nutzername "+ user +" Bereits vergeben!";
			}
		}
		boolean x = true;
		int u = 0;
		String test = "null";
		while (x) {
			if (users[u] == "" || users[u] == null || users[u] == "null" || users[u].equals(test)) {
				users[u] = user;
				passwords[u] = pass;
				x = false;
				writeLogins(users, passwords);
				return "Benutzer "+user+" erfolgreich angelegt!";
			}
			if(u == 99){
				x = false;
			}
			else
				u++;
			
		}
		writeLogins(users, passwords);	
		return "Benutzer "+user+" konnte nicht angelegt werden!";
	}

	public static String rmUser(String user) throws IOException {
		readLogins();
		f.delete();
		
		for (int k = 0; k < 100; k++) {
			if (users[k].equals(user)) {
				for (int j = k; j < 99; j++) {
					if(users[j+1] != null){
					users[j] = users[j + 1];
					passwords[j] = passwords[j + 1];
					}
					else if(users[j+1] == null){
						users[j] = null;
						passwords[j] = null;
					}
				}
				writeLogins(users, passwords);
				rmUser(user);
				return"Benutzer "+user+" wurde erfolgreich entfernt!";
			}
		}
		writeLogins(users, passwords);
		return "Benutzer "+user+" konnte nicht gelöscht werden";
		
	}

	public static void writeLogins(String[] users, String[] passwords) throws IOException {

		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);

		for (int i = 0; i < 100; i++) {
			if(users[i]==null || users[i] == ""){
				bw.write("null");
				bw.newLine();
			}else{
				bw.write(users[i]);
				bw.newLine();
			}
			if(passwords[i]==null || passwords[i] == ""){
				bw.write("null");
				bw.newLine();
			}else{
			bw.write(passwords[i]);
			bw.newLine();
			}
		}
		bw.flush();
		bw.close();
	}

	public static void readLogins() throws IOException{
		
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		String u = "";
		String p = "";
		
		for(int i = 0; i<100; i++){
			u = br.readLine();
			users[i] = u;
			
			p = br.readLine();
			passwords[i] = p;
		}
		br.close();
		
	}
}
