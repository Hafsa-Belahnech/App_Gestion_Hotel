package vue;

import dao.ClientDao;
import modele.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionClientsFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ClientDao clientDAO = new ClientDao();

    private JTextField searchField;

    public GestionClientsFrame() {
        setTitle("👥 Gestion des Clients");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Recherche"));

        searchField = new JTextField(30);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chargerTableau();
            }
        });
        controlPanel.add(new JLabel("Rechercher (nom, email, ville):"));
        controlPanel.add(searchField);

        JButton addButton = new JButton("➕ Ajouter");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> ajouterClient());
        controlPanel.add(addButton);

        JButton editButton = new JButton("✏️ Modifier");
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.addActionListener(e -> modifierClient());
        controlPanel.add(editButton);

        JButton deleteButton = new JButton("🗑️ Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> supprimerClient());
        controlPanel.add(deleteButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Tableau
        String[] columns = {"ID", "Nom", "Prénom", "Email", "Téléphone", "Ville", "Adresse", "Date Inscription"};
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

        String keyword = searchField.getText().trim();
        List<Client> clients;

        if (keyword.isEmpty()) {
            clients = clientDAO.findAll();
        } else {
            clients = clientDAO.search(keyword);
        }

        for (Client c : clients) {
            tableModel.addRow(new Object[]{
                    c.getId(),
                    c.getNom(),
                    c.getPrenom(),
                    c.getEmail(),
                    c.getTelephone(),
                    c.getVille(),
                    c.getAdresse(),
                    c.getDateInscription() != null ? c.getDateInscription().toString() : "N/A"
            });
        }
    }

    private void ajouterClient() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telephoneField = new JTextField();
        JTextField villeField = new JTextField();
        JTextField adresseField = new JTextField();

        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Téléphone:"));
        panel.add(telephoneField);
        panel.add(new JLabel("Ville:"));
        panel.add(villeField);
        panel.add(new JLabel("Adresse:"));
        panel.add(adresseField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter un client",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Client client = new Client(
                        nomField.getText(),
                        prenomField.getText(),
                        emailField.getText(),
                        telephoneField.getText(),
                        villeField.getText(),
                        adresseField.getText()
                );

                if (clientDAO.create(client)) {
                    JOptionPane.showMessageDialog(this, "Client ajouté avec succès!");
                    chargerTableau();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Client client = clientDAO.findById(id);

        if (client != null) {
            JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JTextField nomField = new JTextField(client.getNom());
            JTextField prenomField = new JTextField(client.getPrenom());
            JTextField emailField = new JTextField(client.getEmail());
            JTextField telephoneField = new JTextField(client.getTelephone());
            JTextField villeField = new JTextField(client.getVille());
            JTextField adresseField = new JTextField(client.getAdresse());

            panel.add(new JLabel("Nom:"));
            panel.add(nomField);
            panel.add(new JLabel("Prénom:"));
            panel.add(prenomField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Téléphone:"));
            panel.add(telephoneField);
            panel.add(new JLabel("Ville:"));
            panel.add(villeField);
            panel.add(new JLabel("Adresse:"));
            panel.add(adresseField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Modifier le client",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                client.setNom(nomField.getText());
                client.setPrenom(prenomField.getText());
                client.setEmail(emailField.getText());
                client.setTelephone(telephoneField.getText());
                client.setVille(villeField.getText());
                client.setAdresse(adresseField.getText());

                if (clientDAO.update(client)) {
                    JOptionPane.showMessageDialog(this, "Client modifié avec succès!");
                    chargerTableau();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la modification", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void supprimerClient() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce client?\nToutes ses réservations seront aussi supprimées.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (clientDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Client supprimé avec succès!");
                chargerTableau();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
