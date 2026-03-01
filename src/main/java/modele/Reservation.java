package modele;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int idClient;
    private int idChambre;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    private int nombrePersonnes;
    private String statut;
    private double prixTotal;
    private double acompte;
    private String notes;
    private Timestamp dateReservation;

    // Objets liés (pour l'affichage)
    private Client client;
    private Chambre chambre;

    public Reservation() {}

    public Reservation(int idClient, int idChambre, LocalDate dateArrivee,
                       LocalDate dateDepart, int nombrePersonnes, double prixTotal) {
        this.idClient = idClient;
        this.idChambre = idChambre;
        this.dateArrivee = dateArrivee;
        this.dateDepart = dateDepart;
        this.nombrePersonnes = nombrePersonnes;
        this.prixTotal = prixTotal;
        this.statut = "en_attente";
        this.acompte = 0;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdClient() { return idClient; }
    public void setIdClient(int idClient) { this.idClient = idClient; }

    public int getIdChambre() { return idChambre; }
    public void setIdChambre(int idChambre) { this.idChambre = idChambre; }

    public LocalDate getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(LocalDate dateArrivee) { this.dateArrivee = dateArrivee; }

    public LocalDate getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDate dateDepart) { this.dateDepart = dateDepart; }

    public int getNombrePersonnes() { return nombrePersonnes; }
    public void setNombrePersonnes(int nombrePersonnes) { this.nombrePersonnes = nombrePersonnes; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public double getPrixTotal() { return prixTotal; }
    public void setPrixTotal(double prixTotal) { this.prixTotal = prixTotal; }

    public double getAcompte() { return acompte; }
    public void setAcompte(double acompte) { this.acompte = acompte; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Timestamp getDateReservation() { return dateReservation; }
    public void setDateReservation(Timestamp dateReservation) { this.dateReservation = dateReservation; }

    public Client getClient() { return client; }
    public void setClient(Client client) { this.client = client; }

    public Chambre getChambre() { return chambre; }
    public void setChambre(Chambre chambre) { this.chambre = chambre; }

    /**
     * Calcule le nombre de nuits
     */
    public long getNombreNuits() {
        if (dateArrivee != null && dateDepart != null) {
            return java.time.temporal.ChronoUnit.DAYS.between(dateArrivee, dateDepart);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Réservation #" + id + " - " + (client != null ? client.toString() : "N/A");
    }
}
