package dao;

import modele.Reservation;
import modele.Utilisateur;
import service.Session;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao {
    private ClientDao clientDao = new ClientDao();
    private ChambreDao chambreDao = new ChambreDao();

    public boolean isChambreDisponible(int idChambre, java.time.LocalDate arrivee, java.time.LocalDate depart, Integer excludeId) {
        // Requête qui compte les réservations chevauchant les dates choisies
        String sql = "SELECT COUNT(*) FROM reservations WHERE id_chambre = ? " +
                "AND statut IN ('confirmee', 'en_attente') " +
                "AND NOT (date_depart <= ? OR date_arrivee >= ?)";

        if (excludeId != null) {
            sql += " AND id != ?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idChambre);
            ps.setDate(2, java.sql.Date.valueOf(arrivee)); // Conversion LocalDate -> SQL Date
            ps.setDate(3, java.sql.Date.valueOf(depart));

            if (excludeId != null) {
                ps.setInt(4, excludeId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // Retourne true si 0 conflit trouvé
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean create(Reservation r) {
        // Ordre des colonnes selon votre image (1 à 12)
        // id (AUTO_INCREMENT), id_client, id_chambre, date_arrivee, date_depart, nombre_personnes, statut, prix_total, acompte, notes, date_reservation (CURRENT_TIMESTAMP), nb_personnes

        String sql = "INSERT INTO reservations (id_client, id_chambre, date_arrivee, date_depart, nombre_personnes, statut, prix_total, acompte, notes, nb_personnes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, r.getIdClient());
            ps.setInt(2, r.getIdChambre());
            ps.setDate(3, java.sql.Date.valueOf(r.getDateArrivee()));
            ps.setDate(4, java.sql.Date.valueOf(r.getDateDepart()));
            ps.setInt(5, r.getNombrePersonnes()); // Correspond à 'nombre_personnes'
            ps.setString(6, r.getStatut());
            ps.setDouble(7, r.getPrixTotal());
            ps.setDouble(8, 0.0);                 // Acompte (valeur par défaut)
            ps.setString(9, "Aucune note");       // Notes (valeur par défaut)
            ps.setInt(10, r.getNombrePersonnes());// Correspond à 'nb_personnes'

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    //Mettre à jour le statut
    public boolean updateStatut(int id, String nouveauStatut) {
        String sql = "UPDATE reservations SET statut = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nouveauStatut);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode principale de récupération
    public List<Reservation> findAll(Utilisateur u) {
        String sql;
        if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
            sql = "SELECT * FROM reservations WHERE id_client = (SELECT id FROM clients WHERE email = ?)";
        } else {
            sql = "SELECT * FROM reservations";
        }
        return executerQuery(sql, u);
    }

    // Méthode de recherche
    public List<Reservation> search(String keyword, Utilisateur u) {
        String sql;
        if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
            sql = "SELECT * FROM reservations WHERE id_client = (SELECT id FROM clients WHERE email = ?) AND id LIKE ?";
        } else {
            sql = "SELECT * FROM reservations WHERE id LIKE ?";
        }
        return executerQueryParam(sql, u, "%" + keyword + "%");
    }

    // Exécution SQL pour findAll
    private List<Reservation> executerQuery(String sql, Utilisateur u) {
        List<Reservation> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
                ps.setString(1, u.getEmail());
            }
            // On ouvre le ResultSet à l'intérieur du try pour qu'il reste ouvert durant la boucle
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    //  ResultSet est ouvert
                    list.add(map(rs));
                }
            } // Le ResultSet se ferme automatiquement ici 'APRÈS' la boucle

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Exécution SQL pour search (avec mot-clé)
    private List<Reservation> executerQueryParam(String sql, Utilisateur u, String key) {
        List<Reservation> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
                ps.setString(1, u.getEmail());
                ps.setString(2, key);
            } else {
                ps.setString(1, key);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) { list.add(map(rs)); }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    private Reservation map(ResultSet rs) throws SQLException {
        Reservation r = new Reservation();
        r.setId(rs.getInt("id"));
        r.setIdClient(rs.getInt("id_client"));
        r.setIdChambre(rs.getInt("id_chambre"));
        r.setStatut(rs.getString("statut"));
        r.setDateArrivee(rs.getDate("date_arrivee").toLocalDate());
        r.setDateDepart(rs.getDate("date_depart").toLocalDate());
        r.setPrixTotal(rs.getDouble("prix_total"));
        r.setClient(clientDao.findById(r.getIdClient()));
        r.setChambre(chambreDao.findById(r.getIdChambre()));
        return r;
    }


    /**
     * Calcule le montant total de toutes les réservations confirmées
     */
    public double getTotalRevenus() {
        double total = 0;
        // On ne calcule les revenus que pour les réservations confirmées ou terminées
        String sql = "SELECT SUM(prix_total) FROM reservations WHERE statut IN ('confirmee', 'terminee')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du calcul des revenus : " + e.getMessage());
            e.printStackTrace();
        }
        return total;
    }


    // Emplacement : dao/ReservationDao.java
    public int countByStatut(String statut, modele.Utilisateur u) {
        int count = 0;
        String sql;

        // Si c'est un client, on ne compte que SES réservations
        if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
            sql = "SELECT COUNT(*) FROM reservations WHERE statut = ? AND id_client = (SELECT id FROM clients WHERE email = ?)";
        } else {
            // Pour Admin/Réceptionniste, on compte tout
            sql = "SELECT COUNT(*) FROM reservations WHERE statut = ?";
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, statut);
            if (u != null && u.getRole().equalsIgnoreCase("CLIENT")) {
                ps.setString(2, u.getEmail());
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}
