package modele;

import java.sql.Timestamp;

public class Chambre {
    private int id;
    private String numeroChambre;
    private String typeChambre;
    private double prixParNuit;
    private int capacite;
    private int etage;
    private String statut;
    private String description;
    private String equipements;
    private Timestamp dateCreation;

    public Chambre() {}

    public Chambre(String numeroChambre, String typeChambre, double prixParNuit,
                   int capacite, int etage, String statut) {
        this.numeroChambre = numeroChambre;
        this.typeChambre = typeChambre;
        this.prixParNuit = prixParNuit;
        this.capacite = capacite;
        this.etage = etage;
        this.statut = statut;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNumeroChambre() { return numeroChambre; }
    public void setNumeroChambre(String numeroChambre) { this.numeroChambre = numeroChambre; }

    public String getTypeChambre() { return typeChambre; }
    public void setTypeChambre(String typeChambre) { this.typeChambre = typeChambre; }

    public double getPrixParNuit() { return prixParNuit; }
    public void setPrixParNuit(double prixParNuit) { this.prixParNuit = prixParNuit; }

    public int getCapacite() { return capacite; }
    public void setCapacite(int capacite) { this.capacite = capacite; }

    public int getEtage() { return etage; }
    public void setEtage(int etage) { this.etage = etage; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getEquipements() { return equipements; }
    public void setEquipements(String equipements) { this.equipements = equipements; }

    public Timestamp getDateCreation() { return dateCreation; }
    public void setDateCreation(Timestamp dateCreation) { this.dateCreation = dateCreation; }

    @Override
    public String toString() {
        return "Chambre " + numeroChambre + " - " + typeChambre;
    }
}
