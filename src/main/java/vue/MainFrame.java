package vue;

import dao.ChambreDao;
import dao.ClientDao;
import dao.ReservationDao;
import modele.Utilisateur;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Utilisateur utilisateurCourant;
    private ChambreDao chambreDAO = new ChambreDao();
    private ClientDao clientDAO = new ClientDao();
    private ReservationDao reservationDAO = new ReservationDao();

    public MainFrame(Utilisateur utilisateur) {

        this.utilisateurCourant = utilisateur;
        setTitle("🏨 Gestion Hôtel - Tableau de bord");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String role = (utilisateur.getRole() != null) ? utilisateur.getRole().trim().toUpperCase() : "CLIENT";

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        JMenu fichierMenu = new JMenu("Fichier");
        JMenuItem deconnexionItem = new JMenuItem("Déconnexion");
        deconnexionItem.addActionListener(e -> deconnexion());
        JMenuItem quitterItem = new JMenuItem("Quitter");
        quitterItem.addActionListener(e -> System.exit(0));
        fichierMenu.add(deconnexionItem);
        fichierMenu.addSeparator();
        fichierMenu.add(quitterItem);

        //String role = utilisateur.getRole().trim().toUpperCase();

        JMenu gestionMenu = new JMenu("Gestion");
        JMenuItem chambresItem = new JMenuItem("🏠 Chambres");
        JMenuItem clientsItem = new JMenuItem("👥 Clients");
        JMenuItem reservationsItem = new JMenuItem("📅 Réservations");

        // Permissions :
        // L'ADMIN voit tout.
        // Le RECEPTIONNISTE ne voit pas les Chambres (selon votre demande).
        // Le CLIENT ne voit que ses Réservations.

        chambresItem.addActionListener(e -> ouvrirGestionChambres());
        clientsItem.addActionListener(e -> ouvrirGestionClients());
        reservationsItem.addActionListener(e -> ouvrirGestionReservations());

        if (role.equals("ADMIN")) {
            gestionMenu.add(chambresItem);
            gestionMenu.add(clientsItem);
            gestionMenu.add(reservationsItem);
        } else if (role.equals("RECEPTIONNISTE")) {
            // Le réceptionniste gère clients et réservations mais pas les chambres
            gestionMenu.add(clientsItem);
            gestionMenu.add(reservationsItem);
        } else if (role.equals("CLIENT")) {
            // Le client ne voit que l'option réservations
            gestionMenu.add(reservationsItem);
        }

        JMenu statsMenu = new JMenu("Statistiques");
        JMenuItem statsItem = new JMenuItem("📊 Tableau de bord");
        statsItem.addActionListener(e -> ouvrirStatistiques());
        statsMenu.add(statsItem);

        // On cache le menu Statistiques pour le Client et le Réceptionniste (optionnel)
        //if (role.equals("CLIENT") || role.equals("RECEPTIONNISTE")) {
            //statsMenu.setVisible(false);
        //}

        // Masquage des stats pour les non-admin
        if (!role.equals("ADMIN")) {
            statsMenu.setVisible(false);
        }

        menuBar.add(fichierMenu);
        menuBar.add(gestionMenu);
        menuBar.add(statsMenu);
        setJMenuBar(menuBar);

        // Contenu principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setPreferredSize(new Dimension(0, 100));

        JLabel titleLabel = new JLabel("TABLEAU DE BORD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);

        JLabel userLabel = new JLabel("👤 " + utilisateur.getPrenom() + " " + utilisateur.getNom() +
                " (" + utilisateur.getRole() + ")", SwingConstants.RIGHT);
        userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        userLabel.setForeground(new Color(200, 200, 200));
        userLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 20));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(userLabel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);



        // Cartes de statistiques
        JPanel cardsPanel = new JPanel(new GridLayout(1, 4, 20, 20));
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        cardsPanel.add(createStatCard("Chambres Disponibles",
                String.valueOf(chambreDAO.countByStatut("disponible")),
                new Color(46, 204, 113)));

        // Carte 2 : Réservations (Différente selon le rôle)
        String labelReservations = role.equals("CLIENT") ? "Mes Réservations" : "Réservations Actives";
        cardsPanel.add(createStatCard(labelReservations,
                String.valueOf(reservationDAO.countByStatut("confirmee", utilisateur)), // <-- Ajout de 'utilisateur'
                new Color(52, 152, 219)));

       // MASQUER les cartes sensibles pour le CLIENT
            if (!role.equals("CLIENT")) {
                cardsPanel.add(createStatCard("Clients Totaux",
                        String.valueOf(clientDAO.count()),
                        new Color(155, 89, 182)));

            // Seul l'ADMIN voit les revenus
            if (role.equals("ADMIN")) {
                cardsPanel.add(createStatCard("Revenus Totaux",
                        String.format("%.2f DH", reservationDAO.getTotalRevenus()),
                        new Color(230, 126, 34)));
            }
        }

        mainPanel.add(cardsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(15));
        panel.add(valueLabel);

        return panel;
    }

    private void deconnexion() {
        int choice = JOptionPane.showConfirmDialog(this,
                "Voulez-vous vraiment vous déconnecter?",
                "Déconnexion",
                JOptionPane.YES_NO_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
            this.dispose();
        }
    }

    private void ouvrirGestionChambres() {
        GestionChambresFrame frame = new GestionChambresFrame();
        frame.setVisible(true);
    }

    private void ouvrirGestionClients() {
        GestionClientsFrame frame = new GestionClientsFrame();
        frame.setVisible(true);
    }

    private void ouvrirGestionReservations() {
        GestionReservationsFrame frame = new GestionReservationsFrame();
        frame.setVisible(true);
    }

    private void ouvrirStatistiques() {
        StatistiquesFrame frame = new StatistiquesFrame();
        frame.setVisible(true);
    }
}
