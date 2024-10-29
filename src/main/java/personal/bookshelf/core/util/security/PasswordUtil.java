package personal.bookshelf.core.util.security;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility Class that performs encryption of plain text
 * passwords and checks the hashed and plain password using JBCrypt.
 */
public class PasswordUtil {
    private final static int LOG_ROUNDS = 12;

    private PasswordUtil() {}

    public static String encryptPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(LOG_ROUNDS));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
