package dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static final Properties properties = new Properties();
    private static Connection connection = null;

    static {
        try {
            // Charger les propriétés
            InputStream input = DBConnection.class.getClassLoader()
                    .getResourceAsStream("app.properties");

            if (input == null) {
                System.err.println("Fichier database.properties non trouvé dans classpath!");
                System.err.println("Créez le fichier dans src/main/resources/");

            }

            properties.load(input);
            input.close();

            // Charger le driver MySQL
            Class.forName(properties.getProperty("db.driver"));

            System.out.println("✓ Driver MySQL chargé avec succès");

        } catch (Exception e) {
            System.err.println("✗ Erreur lors du chargement de la configuration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            // AJOUT DE LA VÉRIFICATION .isClosed()
            if (connection == null || connection.isClosed()) {
                String url = properties.getProperty("db.url");
                String user = properties.getProperty("db.user");
                String password = properties.getProperty("db.password");

                if (url == null || url.isEmpty()) {
                    throw new SQLException("URL de base de données non configurée");
                }

                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur de connexion : " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }



    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("✓ Connexion fermée");
            } catch (SQLException e) {
                System.err.println("✗ Erreur lors de la fermeture: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}