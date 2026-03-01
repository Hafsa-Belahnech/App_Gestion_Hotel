package vue;

import dao.ChambreDao;
import modele.Chambre;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionChambresFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private ChambreDao chambreDAO = new ChambreDao();

    private JTextField searchField;
    private JComboBox<String> typeFilter, statutFilter;

    public GestionChambresFrame() {
        setTitle("🏠 Gestion des Chambres");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel de contrôle
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Filtres & Recherche"));

        searchField = new JTextField(20);
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                chargerTableau();
            }
        });
        controlPanel.add(new JLabel("Recherche:"));
        controlPanel.add(searchField);

        typeFilter = new JComboBox<>(new String[]{"Tous", "simple", "double", "suite", "presidentielle"});
        typeFilter.addActionListener(e -> chargerTableau());
        controlPanel.add(new JLabel("Type:"));
        controlPanel.add(typeFilter);

        statutFilter = new JComboBox<>(new String[]{"Tous", "disponible", "occupee", "reservee", "maintenance"});
        statutFilter.addActionListener(e -> chargerTableau());
        controlPanel.add(new JLabel("Statut:"));
        controlPanel.add(statutFilter);

        JButton addButton = new JButton("➕ Ajouter");
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.addActionListener(e -> ajouterChambre());
        controlPanel.add(addButton);

        JButton editButton = new JButton("✏️ Modifier");
        editButton.setBackground(new Color(52, 152, 219));
        editButton.setForeground(Color.WHITE);
        editButton.addActionListener(e -> modifierChambre());
        controlPanel.add(editButton);

        JButton deleteButton = new JButton("🗑️ Supprimer");
        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> supprimerChambre());
        controlPanel.add(deleteButton);

        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // Tableau
        String[] columns = {"ID", "Numéro", "Type", "Prix/Nuit", "Capacité", "Étage", "Statut", "Description"};
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

        // Charger les données
        chargerTableau();

        add(mainPanel);
    }

    private void chargerTableau() {
        tableModel.setRowCount(0);

        List<Chambre> chambres = chambreDAO.findAll();

        String searchKeyword = searchField.getText().trim().toLowerCase();
        String typeSelected = (String) typeFilter.getSelectedItem();
        String statutSelected = (String) statutFilter.getSelectedItem();

        for (Chambre c : chambres) {
            // Filtres
            boolean matchSearch = searchKeyword.isEmpty() ||
                    c.getNumeroChambre().toLowerCase().contains(searchKeyword) ||
                    c.getDescription().toLowerCase().contains(searchKeyword);

            boolean matchType = typeSelected.equals("Tous") || c.getTypeChambre().equals(typeSelected);
            boolean matchStatut = statutSelected.equals("Tous") || c.getStatut().equals(statutSelected);

            if (matchSearch && matchType && matchStatut) {
                tableModel.addRow(new Object[]{
                        c.getId(),
                        c.getNumeroChambre(),
                        c.getTypeChambre(),
                        c.getPrixParNuit() + " DH",
                        c.getCapacite(),
                        c.getEtage(),
                        c.getStatut(),
                        c.getDescription()
                });
            }
        }
    }

    private void ajouterChambre() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField numeroField = new JTextField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"simple", "double", "suite", "presidentielle"});
        JTextField prixField = new JTextField();
        JTextField capaciteField = new JTextField();
        JTextField etageField = new JTextField();
        JComboBox<String> statutCombo = new JComboBox<>(new String[]{"disponible", "occupee", "maintenance"});
        JTextField descriptionField = new JTextField();
        JTextField equipementsField = new JTextField();

        panel.add(new JLabel("Numéro:"));
        panel.add(numeroField);
        panel.add(new JLabel("Type:"));
        panel.add(typeCombo);
        panel.add(new JLabel("Prix/Nuit (€):"));
        panel.add(prixField);
        panel.add(new JLabel("Capacité:"));
        panel.add(capaciteField);
        panel.add(new JLabel("Étage:"));
        panel.add(etageField);
        panel.add(new JLabel("Statut:"));
        panel.add(statutCombo);
        panel.add(new JLabel("Description:"));
        panel.add(descriptionField);
        panel.add(new JLabel("Équipements:"));
        panel.add(equipementsField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter une chambre",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                Chambre chambre = new Chambre(
                        numeroField.getText(),
                        (String) typeCombo.getSelectedItem(),
                        Double.parseDouble(prixField.getText()),
                        Integer.parseInt(capaciteField.getText()),
                        Integer.parseInt(etageField.getText()),
                        (String) statutCombo.getSelectedItem()
                );
                chambre.setDescription(descriptionField.getText());
                chambre.setEquipements(equipementsField.getText());

                if (chambreDAO.create(chambre)) {
                    JOptionPane.showMessageDialog(this, "Chambre ajoutée avec succès!");
                    chargerTableau();
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modifierChambre() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une chambre", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        Chambre chambre = chambreDAO.findById(id);

        if (chambre != null) {
            JPanel panel = new JPanel(new GridLayout(8, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JTextField numeroField = new JTextField(chambre.getNumeroChambre());
            JComboBox<String> typeCombo = new JComboBox<>(new String[]{"simple", "double", "suite", "presidentielle"});
            typeCombo.setSelectedItem(chambre.getTypeChambre());
            JTextField prixField = new JTextField(String.valueOf(chambre.getPrixParNuit()));
            JTextField capaciteField = new JTextField(String.valueOf(chambre.getCapacite()));
            JTextField etageField = new JTextField(String.valueOf(chambre.getEtage()));
            JComboBox<String> statutCombo = new JComboBox<>(new String[]{"disponible", "occupee", "maintenance", "reservee"});
            statutCombo.setSelectedItem(chambre.getStatut());
            JTextField descriptionField = new JTextField(chambre.getDescription());
            JTextField equipementsField = new JTextField(chambre.getEquipements());

            panel.add(new JLabel("Numéro:"));
            panel.add(numeroField);
            panel.add(new JLabel("Type:"));
            panel.add(typeCombo);
            panel.add(new JLabel("Prix/Nuit (DH):"));
            panel.add(prixField);
            panel.add(new JLabel("Capacité:"));
            panel.add(capaciteField);
            panel.add(new JLabel("Étage:"));
            panel.add(etageField);
            panel.add(new JLabel("Statut:"));
            panel.add(statutCombo);
            panel.add(new JLabel("Description:"));
            panel.add(descriptionField);
            panel.add(new JLabel("Équipements:"));
            panel.add(equipementsField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Modifier la chambre",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    chambre.setNumeroChambre(numeroField.getText());
                    chambre.setTypeChambre((String) typeCombo.getSelectedItem());
                    chambre.setPrixParNuit(Double.parseDouble(prixField.getText()));
                    chambre.setCapacite(Integer.parseInt(capaciteField.getText()));
                    chambre.setEtage(Integer.parseInt(etageField.getText()));
                    chambre.setStatut((String) statutCombo.getSelectedItem());
                    chambre.setDescription(descriptionField.getText());
                    chambre.setEquipements(equipementsField.getText());

                    if (chambreDAO.update(chambre)) {
                        JOptionPane.showMessageDialog(this, "Chambre modifiée avec succès!");
                        chargerTableau();
                    } else {
                        JOptionPane.showMessageDialog(this, "Erreur lors de la modification", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Veuillez remplir correctement tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void supprimerChambre() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une chambre", "Attention", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer cette chambre?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (chambreDAO.delete(id)) {
                JOptionPane.showMessageDialog(this, "Chambre supprimée avec succès!");
                chargerTableau();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
