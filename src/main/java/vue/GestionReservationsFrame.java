package vue;

import dao.ChambreDao;
import dao.ClientDao;
import dao.ReservationDao;
import modele.Chambre;
import modele.Client;
import modele.Reservation;
import modele.Utilisateur;
import service.Session;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class GestionReservationsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ReservationDao reservationDAO = new ReservationDao();
    private ClientDao clientDAO = new ClientDao();
    private ChambreDao chambreDAO = new ChambreDao();

    private JTextField searchField;
    private JComboBox<String> statutFilter;

    public GestionReservationsFrame() {
        setTitle("📅 Gestion des Réservations");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Filtres & Actions"));

        searchField = new JTextField(20);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chargerTableau();
            }
        });
        controlPanel.add(new JLabel("Recherche:"));
        controlPanel.add(searchField);

        statutFilter = new JComboBox<>(new String[]{"Tous", "en_attente", "confirmee", "annulee", "terminee"});
        statutFilter.addActionListener(e -> chargerTableau());
        controlPanel.add(new JLabel("Statut:"));
        controlPanel.add(statutFilter);

        JButton addButton = new JButton("➕ Nouvelle Réservation");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> ajouterReservation());
        controlPanel.add(addButton);

        JButton confirmButton = new JButton("✓ Confirmer");
        confirmButton.setBackground(new Color(52, 152, 219));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.addActionListener(e -> changerStatut("confirmee"));
        controlPanel.add(confirmButton);

        JButton cancelButton = new JButton("✕ Annuler");
        cancelButton.setBackground(new Color(231, 76, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> changerStatut("annulee"));
        controlPanel.add(cancelButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Tableau
        String[] columns = {"ID", "Client", "Chambre", "Arrivée", "Départ", "Nuits", "Personnes", "Prix Total", "Statut", "Date Réservation"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        chargerTableau();
        add(mainPanel);
    }

    private void chargerTableau() {
        tableModel.setRowCount(0);
        Utilisateur u = service.Session.get(); // Récupère l'utilisateur
        if (u == null) return;

        String keyword = searchField.getText().trim();
        List<Reservation> reservations;

        if (keyword.isEmpty()) {
            reservations = reservationDAO.findAll(u);
        } else {
            reservations = reservationDAO.search(keyword, u);
        }

        for (Reservation r : reservations) {
            tableModel.addRow(new Object[]{
                    r.getId(),
                    r.getClient(),
                    r.getChambre() != null ? r.getChambre().getNumeroChambre() : "N/A",
                    r.getDateArrivee(),
                    r.getDateDepart(),
                    r.getPrixTotal() + " DH",
                    r.getStatut()
            });
        }
    }



    private void ajouterReservation() {
        // Sélectionner un client
        List<Client> clients = clientDAO.findAll();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun client disponible. Veuillez d'abord créer un client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client[] clientArray = clients.toArray(new Client[0]);
        Client selectedClient = (Client) JOptionPane.showInputDialog(this,
                "Sélectionnez un client:", "Client", JOptionPane.QUESTION_MESSAGE,
                null, clientArray, clientArray[0]);

        if (selectedClient == null) return;

        // Sélectionner une chambre disponible
        List<Chambre> chambres = chambreDAO.findDisponibles();
        if (chambres.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune chambre disponible.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Chambre[] chambreArray = chambres.toArray(new Chambre[0]);
        Chambre selectedChambre = (Chambre) JOptionPane.showInputDialog(this,
                "Sélectionnez une chambre:", "Chambre", JOptionPane.QUESTION_MESSAGE,
                null, chambreArray, chambreArray[0]);

        if (selectedChambre == null) return;

        // Dates
        String dateArriveeStr = JOptionPane.showInputDialog(this, "Date d'arrivée (YYYY-MM-DD):", LocalDate.now().toString());
        if (dateArriveeStr == null || dateArriveeStr.isEmpty()) return;

        String dateDepartStr = JOptionPane.showInputDialog(this, "Date de départ (YYYY-MM-DD):", LocalDate.now().plusDays(1).toString());
        if (dateDepartStr == null || dateDepartStr.isEmpty()) return;

        try {
            LocalDate dateArrivee = LocalDate.parse(dateArriveeStr);
            LocalDate dateDepart = LocalDate.parse(dateDepartStr);

            if (!dateDepart.isAfter(dateArrivee)) {
                JOptionPane.showMessageDialog(this, "La date de départ doit être après la date d'arrivée", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Vérifier disponibilité
            if (!reservationDAO.isChambreDisponible(selectedChambre.getId(), dateArrivee, dateDepart, null)) {
                JOptionPane.showMessageDialog(this, "Cette chambre n'est pas disponible pour cette période", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nombre de personnes
            String personnesStr = JOptionPane.showInputDialog(this, "Nombre de personnes:", "1");
            int nombrePersonnes = Integer.parseInt(personnesStr);

            // Calculer prix
            long nuits = java.time.temporal.ChronoUnit.DAYS.between(dateArrivee, dateDepart);
            double prixTotal = nuits * selectedChambre.getPrixParNuit();

            // Créer la réservation
            Reservation reservation = new Reservation(
                    selectedClient.getId(),
                    selectedChambre.getId(),
                    dateArrivee,
                    dateDepart,
                    nombrePersonnes,
                    prixTotal
            );

            if (reservationDAO.create(reservation)) {
                JOptionPane.showMessageDialog(this, "Réservation créée avec succès!\nPrix total: " + prixTotal + " DH");
                chargerTableau();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création", "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void changerStatut(String nouveauStatut) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une réservation", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        if (reservationDAO.updateStatut(id, nouveauStatut)) {
            JOptionPane.showMessageDialog(this, "Statut mis à jour avec succès!");
            chargerTableau();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
