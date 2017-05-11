package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Authentifi {
	
	
		public static boolean valid(String user, String pass) throws IOException{

			FileWriter fw = new FileWriter("logins.txt");
		    BufferedWriter bw = new BufferedWriter(fw);

		    bw.write("user\n");
		    bw.write("pass\n");
		    bw.write("IT\n");
		    bw.write("passwort");

		    bw.close();
			
			FileReader fr = new FileReader("logins.txt");
		    BufferedReader br = new BufferedReader(fr);

		    String user1 = br.readLine();
		    String pass1 = br.readLine();
		    String user2 = br.readLine();
		    String pass2 = br.readLine();
		    br.close();
			
			
		    if(user == user1 && pass==pass1){
		    	return true;
		    }
		    else if(user==user2 && pass==pass2){
		    	return true;
		    }
		    
			return false;
		}
}
