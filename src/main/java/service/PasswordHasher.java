package service;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {
    private static final int STRENGTH = 10;

    /**
     * Hache un mot de passe avec BCrypt
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(STRENGTH));
    }

    /**
     * Vérifie si un mot de passe correspond au hash
     */
    public static boolean verify(String password, String hashedPassword) {
        try {
            return BCrypt.checkpw(password, hashedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Génère un code de vérification aléatoire (6 chiffres)
     */
    public static String generateVerificationCode() {
        return String.format("%06d", (int)(Math.random() * 1000000));
    }
}
