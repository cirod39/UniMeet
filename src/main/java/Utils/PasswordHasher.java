package Utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
	
	//metodo per hashare la password
	 public static String hashPassword(String password) {
	        return BCrypt.hashpw(password, BCrypt.gensalt());
	    }

	    // metodo per verificare la password
	    public static boolean verifyPassword(String password, String hashedPassword) {
	        return BCrypt.checkpw(password, hashedPassword);
	    }

}
