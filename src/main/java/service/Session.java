package service;

import modele.Utilisateur;

import javax.mail.Authenticator;
import java.util.Properties;

public class Session {
    // Stocke l'utilisateur qui vient de se connecter
    private static Utilisateur utilisateurConnecte = null;

    public static void ouvrir(Utilisateur u) {
        utilisateurConnecte = u;
    }

    public static Utilisateur get() {
        return utilisateurConnecte;
    }

    public static void fermer() {
        utilisateurConnecte = null;
    }

}

