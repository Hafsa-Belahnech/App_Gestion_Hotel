package dao;

import modele.Chambre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChambreDao {

    public boolean create(Chambre chambre) {
        String sql = "INSERT INTO chambres (numero_chambre, type_chambre, prix_par_nuit, capacite, etage, statut, description, equipements) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chambre.getNumeroChambre());
            stmt.setString(2, chambre.getTypeChambre());
            stmt.setDouble(3, chambre.getPrixParNuit());
            stmt.setInt(4, chambre.getCapacite());
            stmt.setInt(5, chambre.getEtage());
            stmt.setString(6, chambre.getStatut());
            stmt.setString(7, chambre.getDescription());
            stmt.setString(8, chambre.getEquipements());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Chambre findById(int id) {
        String sql = "SELECT * FROM chambres WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToChambre(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Chambre findByNumero(String numero) {
        String sql = "SELECT * FROM chambres WHERE numero_chambre = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToChambre(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Chambre chambre) {
        String sql = "UPDATE chambres SET type_chambre = ?, prix_par_nuit = ?, capacite = ?, etage = ?, statut = ?, description = ?, equipements = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chambre.getTypeChambre());
            stmt.setDouble(2, chambre.getPrixParNuit());
            stmt.setInt(3, chambre.getCapacite());
            stmt.setInt(4, chambre.getEtage());
            stmt.setString(5, chambre.getStatut());
            stmt.setString(6, chambre.getDescription());
            stmt.setString(7, chambre.getEquipements());
            stmt.setInt(8, chambre.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatut(int id, String statut) {
        String sql = "UPDATE chambres SET statut = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut);
            stmt.setInt(2, id);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM chambres WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Chambre> findAll() {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres ORDER BY numero_chambre";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                chambres.add(mapResultSetToChambre(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

    public List<Chambre> findByStatut(String statut) {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres WHERE statut = ? ORDER BY numero_chambre";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                chambres.add(mapResultSetToChambre(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

    public List<Chambre> findByType(String type) {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres WHERE type_chambre = ? ORDER BY numero_chambre";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                chambres.add(mapResultSetToChambre(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chambres;
    }

    public List<Chambre> findDisponibles() {
        return findByStatut("disponible");
    }

    private Chambre mapResultSetToChambre(ResultSet rs) throws SQLException {
        Chambre c = new Chambre();
        c.setId(rs.getInt("id"));
        c.setNumeroChambre(rs.getString("numero_chambre"));
        c.setTypeChambre(rs.getString("type_chambre"));
        c.setPrixParNuit(rs.getDouble("prix_par_nuit"));
        c.setCapacite(rs.getInt("capacite"));
        c.setEtage(rs.getInt("etage"));
        c.setStatut(rs.getString("statut"));
        c.setDescription(rs.getString("description"));
        c.setEquipements(rs.getString("equipements"));
        c.setDateCreation(rs.getTimestamp("date_creation"));
        return c;
    }

    public int countByStatut(String statut) {
        String sql = "SELECT COUNT(*) FROM chambres WHERE statut = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
