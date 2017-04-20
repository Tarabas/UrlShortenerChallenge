package net.rorarius.helper;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordGenerator {

    public static String generatePasswordHash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
