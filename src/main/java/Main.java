import dao.DBConnection;
import vue.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Configuration du Look and Feel
        System.out.println("=== Test de connexion ===");
        if (DBConnection.testConnection()) {
            System.out.println("✓ Connexion OK");
        } else {
            System.out.println("✗ Connexion ÉCHOUÉE");
            System.exit(1);
        }

        // Lancer l'application
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
}}