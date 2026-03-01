package vue;

import dao.ChambreDao;
import dao.ClientDao;
import dao.ReservationDao;
import modele.Utilisateur;
import service.Session;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class StatistiquesFrame extends JFrame {
    private ReservationDao reservationDAO = new ReservationDao();
    private ChambreDao chambreDAO = new ChambreDao();
    private ClientDao clientDAO = new ClientDao();

    public StatistiquesFrame() {
        modele.Utilisateur utilisateurConnecte = service.Session.get();
        setTitle("📊 Statistiques & Graphiques");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Panel supérieur avec statistiques
        JPanel statsPanel = new JPanel(new GridLayout(1, 5, 20, 0));
        statsPanel.setPreferredSize(new Dimension(0, 100));

        // CORRECTION ICI : Ajout de utilisateurConnecte dans findAll()
        statsPanel.add(createStatBox("Chambres Totales", String.valueOf(chambreDAO.findAll().size()), new Color(52, 152, 219)));
        statsPanel.add(createStatBox("Disponibles", String.valueOf(chambreDAO.countByStatut("disponible")), new Color(46, 204, 113)));

        // LIGNE CORRIGÉE :
        statsPanel.add(createStatBox("Réservations", String.valueOf(reservationDAO.findAll(utilisateurConnecte).size()), new Color(155, 89, 182)));

        statsPanel.add(createStatBox("Clients", String.valueOf(clientDAO.count()), new Color(230, 126, 34)));
        statsPanel.add(createStatBox("Revenus", String.format("%.2f DH", reservationDAO.getTotalRevenus()), new Color(231, 76, 60)));


        mainPanel.add(statsPanel, BorderLayout.NORTH);

        // Panel central avec graphiques
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));

        // Graphique 1: Répartition des chambres par type
        JFreeChart chart1 = createPieChart();
        ChartPanel chartPanel1 = new ChartPanel(chart1);
        chartPanel1.setPreferredSize(new Dimension(500, 350));

        // Graphique 2: Réservations par statut
        JFreeChart chart2 = createBarChart();
        ChartPanel chartPanel2 = new ChartPanel(chart2);
        chartPanel2.setPreferredSize(new Dimension(500, 350));

        chartsPanel.add(chartPanel1);
        chartsPanel.add(chartPanel2);

        mainPanel.add(chartsPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createStatBox(String title, String value, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.WHITE, 2),
                BorderFactory.createEmptyBorder(15, 10, 15, 10)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(valueLabel);

        return panel;
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        dataset.setValue("Simple", chambreDAO.findByType("simple").size());
        dataset.setValue("Double", chambreDAO.findByType("double").size());
        dataset.setValue("Suite", chambreDAO.findByType("suite").size());
        dataset.setValue("Présidentielle", chambreDAO.findByType("presidentielle").size());

        JFreeChart chart = ChartFactory.createPieChart(
                "Répartition des Chambres par Type",
                dataset,
                true,
                true,
                false
        );

        return chart;
    }

    private JFreeChart createBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        modele.Utilisateur u = service.Session.get();

        // 2. On passe 'u' en DEUXIÈME paramètre à chaque appel
        dataset.setValue(reservationDAO.countByStatut("en_attente", u), "Réservations", "En attente");
        dataset.setValue(reservationDAO.countByStatut("confirmee", u), "Réservations", "Confirmées");
        dataset.setValue(reservationDAO.countByStatut("annulee", u), "Réservations", "Annulées");
        dataset.setValue(reservationDAO.countByStatut("terminee", u), "Réservations", "Terminées");

        return ChartFactory.createBarChart("Statut des Réservations", "Statut", "Nombre", dataset);
    }
}

