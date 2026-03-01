package utils;

import java.util.regex.Pattern;

public class Validator {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private static final String PHONE_PATTERN = "^[0-9]{10}$";
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

    /**
     * Valider un email
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return Pattern.matches(EMAIL_PATTERN, email.trim());
    }

    /**
     * Valider un numéro de téléphone (10 chiffres)
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return true; // Optionnel
        }
        String cleaned = phone.replaceAll("\\s+", "");
        return Pattern.matches(PHONE_PATTERN, cleaned);
    }

    /**
     * Valider un mot de passe (min 6 caractères, 1 chiffre, 1 majuscule, 1 minuscule)
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 6) {
            return false;
        }
        return Pattern.matches(PASSWORD_PATTERN, password);
    }

    /**
     * Valider un champ obligatoire non vide
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Valider un nombre
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Valider un entier positif
     */
    public static boolean isPositiveInteger(String str) {
        if (!isNumeric(str)) {
            return false;
        }
        try {
            return Integer.parseInt(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Obtenir le message d'erreur pour un email invalide
     */
    public static String getEmailErrorMessage() {
        return "Format d'email invalide. Exemple: nom@domaine.com";
    }

    /**
     * Obtenir le message d'erreur pour un mot de passe invalide
     */
    public static String getPasswordErrorMessage() {
        return "Le mot de passe doit contenir au moins 6 caractères, dont une majuscule, une minuscule et un chiffre";
    }
}
